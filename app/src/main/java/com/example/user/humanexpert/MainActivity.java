package com.example.user.humanexpert;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.Toast;


public class MainActivity extends Activity {
    private FragmentMessBroadcastReceiver fragmentMessBroadcastReceiver;
    private Scenario scenario;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        fragmentMessBroadcastReceiver = new FragmentMessBroadcastReceiver();
           scenario = new Scenario();

            //Portret
            final Fragment fragment = new ProblemListFragment();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.fragmentContainer, fragment);
            ft.commitAllowingStateLoss();

    }
    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter();
        filter.addAction("SendScenarioObject");
        registerReceiver(fragmentMessBroadcastReceiver, filter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(fragmentMessBroadcastReceiver);
    }

    class FragmentMessBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("SendScenarioObject")) {
                int mess = intent.getIntExtra("scenatioObject", scenario.getCaseId());

                Toast.makeText(context, "" + mess, Toast.LENGTH_LONG).show();
                final Fragment fragment = CaseFragment.newInstance(mess);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fragmentContainer, fragment);
                ft.commit();
            }
        }
    }
}
