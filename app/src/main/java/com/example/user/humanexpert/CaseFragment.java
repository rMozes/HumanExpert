package com.example.user.humanexpert;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.kevinsawicki.http.HttpRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by User on 17.10.2014.
 */
public class CaseFragment extends Fragment {
    private Case cs;
    private Scenario scenario;
    private ImageView imageView;
    private TextView text;
    private Button btn_1;
    private Button btn_2;

    public static CaseFragment newInstance(int caseId) {
        Bundle args = new Bundle();
        args.putInt("caseId", caseId);
        CaseFragment caseFragment = new CaseFragment();
        caseFragment.setArguments(args);
        return caseFragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        cs = new Case();
        scenario = new Scenario();
        scenario.setCaseId(getArguments().getInt("caseId"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_case, container, false);
        imageView = (ImageView) v.findViewById(R.id.big_img);
        text = (TextView) v.findViewById(R.id.tv_text);
        btn_1 = (Button) v.findViewById(R.id.btn_1);
        btn_2 = (Button) v.findViewById(R.id.btn_2);

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        JsonToArrayListPicture jsonToArrayListPicture = new JsonToArrayListPicture();
        jsonToArrayListPicture.execute(scenario);
    }


    class JsonToArrayListPicture extends AsyncTask<Scenario, Void, Case> {
        Bitmap bitmap = null;

        protected Case doInBackground(Scenario... params) {
            ArrayList<Answer> list = new  ArrayList<Answer>();
            Answer answer;
            Scenario item = params[0];
            int caseId = item.getCaseId();
            Case cs = new Case();
            HttpRequest request = HttpRequest.get("http://expert-system.internal.shinyshark.com/cases/" + caseId);
            if (request.code() == 200) {
                String response = request.body();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject j = jsonObject.getJSONObject("case");
                    String text = j.getString("text");
                    String url = j.getString("image");
                    String id = j.getString("id");
                    cs = downloadInfo(text, url, id);
                    if(j.has("answers")) {
                        JSONArray jsonArray = j.optJSONArray("answers");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject answers = jsonArray.getJSONObject(i);
                            String newText = answers.getString("text");
                            int newId = answers.getInt("id");
                            int newCaseId = answers.getInt("caseId");
                            answer = downloadAnswer(newText, newId, newCaseId);
                            list.add(answer);
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            cs.setList(list);
            bitmap = loadPicture(cs.getImageUrl());
            return cs;
        }

        protected void onPostExecute(Case result) {
            super.onPostExecute(result);
            cs = result;
            scenario.setaCase(cs);
            //btn_1.setText(result.getList().get(0).getNewText());
           // btn_2.setText(result.getList().get(1).getNewText());
            text.setText(result.getTextQuestion());
            imageView.setImageBitmap(bitmap);
            btn_1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent =  new Intent("SendScenarioObject");
                    intent.putExtra("scenatioObject", scenario.getaCase().getList().get(0).getNewCaseId());
                    getActivity().sendBroadcast(intent);
                }
            });
            btn_2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(scenario.getaCase().getList().get(1).getNewId() == 8) {
                        Intent intent = new Intent("SendScenarioObject");
                        intent.putExtra("scenatioObject", scenario.getaCase().getList().get(1).getNewId()+1);
                        getActivity().sendBroadcast(intent);
                    }
                    else{
                        Intent intent = new Intent("SendScenarioObject");
                        intent.putExtra("scenatioObject", scenario.getaCase().getList().get(1).getNewId());
                        getActivity().sendBroadcast(intent);}
                        }
            });
        }

        private Case downloadInfo(String text, String url, String id) {
            Case item = new Case();
            item.setTextQuestion(text);
            item.setImageUrl(url);
            item.setId(id);
            return item;
        }
        private Answer downloadAnswer(String newText, int newId, int caseId) {
            Answer item = new Answer();
            item.setNewText(newText);
            item.setNewId(newId);
            item.setNewCaseId(caseId);
            return item;
        }

        private Bitmap loadPicture(String url) {
            String urldisplay = url;
            Bitmap mIcon = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon;
        }
  }
}
