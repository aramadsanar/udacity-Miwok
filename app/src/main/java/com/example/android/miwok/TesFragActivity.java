package com.example.android.miwok;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class TesFragActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tes_frag);




        ViewPager vp = (ViewPager) findViewById(R.id.viewpager);
        CategoryFragmentPagerAdapter cfpa = new CategoryFragmentPagerAdapter(this, getSupportFragmentManager());
        vp.setAdapter(cfpa);

        TabLayout tl = (TabLayout) findViewById(R.id.sliding_tabs);
        tl.setupWithViewPager(vp);

    }
}
