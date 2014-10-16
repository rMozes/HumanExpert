package com.example.user.humanexpert;

import android.app.ListFragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.github.kevinsawicki.http.HttpRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by User on 16.10.2014.
 */
public class ProblemListFragment extends ListFragment {
    ArrayList<Scenario> scenarios;
    private Scenario scenario;
    private View view;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        getActivity().setTitle(R.string.scenarios_title);
        scenarios = new ArrayList<Scenario>();
        scenario = new Scenario();
        //setupAdapter(scenarios);
    }


    @Override
    public void onResume() {
        super.onResume();
        ProblemDownloadFromJSonAsynkTask problemDownloadFromJSonAsynkTask = new ProblemDownloadFromJSonAsynkTask();
        problemDownloadFromJSonAsynkTask.execute();
    }
    private class ProblemDownloadFromJSonAsynkTask extends AsyncTask<Void, Void, ArrayList<Scenario>> {
        private static final String TAG = "getList";


        @Override
        protected ArrayList<Scenario> doInBackground(Void... params) {
            ArrayList<Scenario> list = new ArrayList<Scenario>();
            Scenario item;
            JSONArray jsonArray = new JSONArray();
            HttpRequest request = HttpRequest.get("http://expert-system.internal.shinyshark.com/scenarios/");
            if (request.code() == 200) {
                String response = request.body();
                try {
                    jsonArray = new JSONArray(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String text = (String) jsonObject.get("text");
                        String id = (String) jsonObject.get("id");
                        String caseId = (String) jsonObject.get("caseId");
                        item = downloadInfo(text, id, caseId);
                        list.add(item);
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "JSON Exception: " + e);
                }
            }
            return list;
        }

        private Scenario downloadInfo (String text, String id, String caseId) {
            Scenario item = new Scenario();
            item.setProblemTitle(text);
            item.setId(id);
            item.setCaseId(caseId);
            return item;
        }
        @Override
        protected void onPostExecute(ArrayList<Scenario> list) {
            super.onPostExecute(list);
            scenarios = list;
            setupAdapter(list);
        }
    }
    public void setupAdapter (ArrayList<Scenario> scenarios) {
        ScenariosAdapter adapter = new ScenariosAdapter(getActivity(), android.R.layout.simple_list_item_1, scenarios);
        setListAdapter(adapter);
    }
    public class ScenariosAdapter extends ArrayAdapter<Scenario> implements Serializable {
        private Context context;
        private int resource;

        public ScenariosAdapter(Context context,int resource,ArrayList<Scenario> scenarios) {
            super(context, resource, scenarios);
            this.context = context;
            this.resource = resource;
        }

        class ViewHolder {
            TextView nameTextView;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if(convertView==null){
                LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = mInflater.inflate(resource, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.nameTextView = (TextView) convertView.findViewById(R.id.problem_title);
                convertView.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolder) convertView.getTag();
            }
            final Scenario item = getItem(position);
            viewHolder.nameTextView.setText(item.getProblemTitle());
            return convertView;
        }

    }
}
