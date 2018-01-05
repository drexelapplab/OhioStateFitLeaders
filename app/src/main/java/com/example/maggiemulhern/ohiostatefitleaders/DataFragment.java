package com.example.maggiemulhern.ohiostatefitleaders;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.maggiemulhern.ohiostatefitleaders.R;

/**
 * Created by Brandon on 12/12/17.
 */

public class DataFragment extends Fragment {

    private static final String TAG = "DataFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_data, container, false);
        return rootView;
    }
}
