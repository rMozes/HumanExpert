package com.example.user.humanexpert;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.InputStream;

/**
 * Created by User on 17.10.2014.
 */
public class CaseFragment extends Fragment {
    private CaseClass cs;
    private Scenario scenario;
    private ImageView imageView;
    private TextView text;
    private Button btn_1;
    private Button btn_2;
    private Answer answer;
    private DBController dbController;
    private ProgressDialog PD;
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
        dbController = new DBController(getActivity());
        cs = new CaseClass();
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
        JsonToArrayListPicture jsonToArrayListPicture = new JsonToArrayListPicture(scenario.getCaseId());
        jsonToArrayListPicture.execute(scenario);

    }


    class JsonToArrayListPicture extends AsyncTask<Scenario, Void, CaseClass> {

        private Gson mGson;
        private Bitmap bitmap = null;

        public JsonToArrayListPicture(int button) {
            mGson = new GsonBuilder()
                    .registerTypeAdapter(CaseClass.class, new JsonCaseDeserializer())
                    .create();
        }

        protected CaseClass doInBackground(Scenario... params) {
            Scenario item = params[0];
            int caseId = item.getCaseId();
            CaseClass cas = new CaseClass();
            cas = dbController
                    .open()
                    .getCaseClass(caseId);
            //Answers поставити в касекласс

            if (cas != null && cas.getList() != null) {
                return cas;
            }
            HttpRequest request = HttpRequest.get("http://expert-system.internal.shinyshark.com/cases/" + caseId);
            if (request.code() == 200) {
                String response = request.body();
                cas = mGson.fromJson(response, CaseClass.class);
                dbController
                        .open()
                        .insertCase(cas);
                for (int i = 0; i < cas.getList().size(); i++) {
                    dbController.insertAnswer(cas.getList().get(i));
                }
                if (cas.getImageUrl() != null) {
                    bitmap = loadPicture(cas.getImageUrl());
                }
            }
            return cas;
        }

        protected void onPostExecute(final CaseClass result) {
            super.onPostExecute(result);
            cs = result;
            btn_1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        int mess = cs.getList().get(0).getNewCaseId();
                        final Fragment fragment = CaseFragment.newInstance(mess);
                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.replace(R.id.fragmentContainer, fragment);
                        //ft.addToBackStack("tag");
                        ft.commit();
                }
            });
            btn_2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        int mess = cs.getList().get(1).getNewCaseId();
                        final Fragment fragment = CaseFragment.newInstance(mess);
                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.replace(R.id.fragmentContainer, fragment);
                        //ft.addToBackStack("tag");
                        ft.commit();
                }
            });
            if (result.getList().size() == 0) {
                btn_1.setVisibility(View.GONE);
                btn_2.setVisibility(View.GONE);
            } else {
                btn_1.setText(result.getList().get(0).getNewText());
                btn_2.setText(result.getList().get(1).getNewText());
            }
            text.setText(result.getText());
            if (bitmap != null) {
                imageView.setImageBitmap(bitmap);
            }
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
    private class DataBaseAsync extends AsyncTask<CaseClass, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            PD = new ProgressDialog(getActivity());
            PD.setTitle("Please Wait..");
            PD.setMessage("Loading...");
            PD.setCancelable(false);
            PD.show();
        }
        @Override
        protected Void doInBackground(CaseClass... params) {
            CaseClass item = params[0];
            dbController.open();
            dbController.insertScen(scenario);
            dbController.insertCase(item);
            dbController.insertAnswer(answer);
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            dbController.close();
            PD.dismiss();
        }
    }
}
