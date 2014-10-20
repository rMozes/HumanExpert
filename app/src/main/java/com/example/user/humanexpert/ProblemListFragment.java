package com.example.user.humanexpert;

import android.app.ListFragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.github.kevinsawicki.http.HttpRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by User on 16.10.2014.
 */
public class ProblemListFragment extends ListFragment {
    ArrayList<Scenario> scenarios;
    private Scenario scenario;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        getActivity().setTitle(R.string.scenarios_title);
        scenarios = new ArrayList<Scenario>();
        scenario = new Scenario();
        setupAdapter();
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
            JSONObject jsonObject = new JSONObject();
            HttpRequest request = HttpRequest.get("http://expert-system.internal.shinyshark.com/scenarios/");
            if (request.code() == 200) {
                String response = request.body();
                try {
                    jsonObject = new JSONObject(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    JSONArray jsonArray = jsonObject.getJSONArray("scenarios");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject c = jsonArray.getJSONObject(i);
                        String text = c.getString("text");
                        int id = c.getInt("id");
                        int caseId = c.getInt("caseId");
                        item = downloadInfo(text, id,caseId);
                        list.add(item);
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "JSON Exception: " + e);
                }
            }
            return list;
        }

        private Scenario downloadInfo (String text, int id, int caseId) {
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
            setupAdapter();
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Scenario sc = (Scenario)(getListAdapter()).getItem(position);
        Intent intent =  new Intent("SendScenarioObject");
        intent.putExtra("scenatioObject", sc.getCaseId());
        getActivity().sendBroadcast(intent);
    }

    public void setupAdapter () {
        ArrayAdapter<Scenario> adapter = new ArrayAdapter<Scenario>(getActivity(), android.R.layout.simple_list_item_1, scenarios);
        setListAdapter(adapter);
    }

}
