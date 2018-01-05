package com.example.maggiemulhern.ohiostatefitleaders;

import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;

//public class TabbedActivity extends AppCompatActivity implements OnFragmentInteractionListener{

public class TabbedActivity extends AppCompatActivity {

    public static final String TAG = "TabbedActivity";
    private SectionsPageAdapter mSectionPageAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabbed);
        Log.d(TAG, "onCreate: Starting.");

        mSectionPageAdapter = new SectionsPageAdapter((getSupportFragmentManager()));
        mViewPager = findViewById(R.id.container);
        setupViewPager(mViewPager);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    private void setupViewPager(ViewPager viewPager){
        mSectionPageAdapter.addFragment(new RootFragment(), "Start New Session");
        mSectionPageAdapter.addFragment(new DataFragment(), "Previous Sessions");
        viewPager.setAdapter(mSectionPageAdapter);
    }



}