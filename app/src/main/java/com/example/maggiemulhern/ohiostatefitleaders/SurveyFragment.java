package com.example.maggiemulhern.ohiostatefitleaders;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.Wearable;

/**
 * Created by Brandon on 12/12/17.
 */

public class SurveyFragment extends Fragment {

    private static final String TAG = "SurveyFragment";

    public static SurveyFragment newInstance(int surveyType) {
        SurveyFragment newFragment = new SurveyFragment();

        Bundle args = new Bundle();
        args.putInt("surveyType", surveyType);
        newFragment.setArguments(args);

        return newFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_survey, container, false);

        WebView myWebView = rootView.findViewById(R.id.Survey);
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // Set WebView client
        myWebView.setWebChromeClient(new WebChromeClient());

        myWebView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        Button btn = rootView.findViewById(R.id.SurveyContinue);

        switch (getArguments().getInt("surveyType")) {
            case 0:
                myWebView.loadUrl("https://osu.az1.qualtrics.com/jfe/form/SV_6KKyV9Fkrm0tygl");
                btn.setText("Press To Start Workout");

                break;
            case 1:
                myWebView.loadUrl("https://osu.az1.qualtrics.com/jfe/form/SV_cAdKvfNTIrjQIpD");
                btn.setText("Press To End Workout");
                break;
        }

        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                FragmentTransaction trans = getFragmentManager().beginTransaction();

                switch(getArguments().getInt("surveyType")) {
                    case 0:

        				/*
        				 * IMPORTANT: We use the "root frame" defined in
		        		 * "root_fragment.xml" as the reference to replace fragment
				         */
                        trans.replace(R.id.root_frame, new WorkoutFragment());

				        /*
				        * IMPORTANT: The following lines allow us to add the fragment
				        * to the stack and return to it later, by pressing back
				        */
                        trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                        trans.addToBackStack(null);

                        trans.commit();
                        break;

                    case 1:

                        Log.d(TAG, "Loading final fragment");
                        /*
        				 * IMPORTANT: We use the "root frame" defined in
		        		 * "root_fragment.xml" as the reference to replace fragment
				         */
                        trans.replace(R.id.root_frame, new FinalFragment());

				        /*
				        * IMPORTANT: The following lines allow us to add the fragment
				        * to the stack and return to it later, by pressing back
				        */
                        trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                        trans.addToBackStack(null);

                        trans.commit();
                        break;
                }
            }
        });

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }
}
