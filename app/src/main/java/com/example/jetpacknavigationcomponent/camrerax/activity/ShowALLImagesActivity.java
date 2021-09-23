package com.example.jetpacknavigationcomponent.camrerax.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.os.Bundle;

import com.example.jetpacknavigationcomponent.camrerax.fragments.ShowPrivateImagesFragment;
import com.example.jetpacknavigationcomponent.camrerax.fragments.ShowALLImagesFragment;
import com.example.jetpacknavigationcomponent.databinding.ActivityShowAllCapturedImagesBinding;
import com.example.jetpacknavigationcomponent.fragments.adapter.SectionPageAdapter;
import com.google.android.material.tabs.TabLayout;

public class ShowALLImagesActivity extends AppCompatActivity{
    private final String TAG = ShowALLImagesActivity.class.getSimpleName();
    private ActivityShowAllCapturedImagesBinding binding;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityShowAllCapturedImagesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initialize();
    }

    private void setUpNewViewPager(ViewPager viewPager){
        SectionPageAdapter sectionPageAdapter = new SectionPageAdapter(getSupportFragmentManager());
        sectionPageAdapter.addFragmentsAdapter(new ShowPrivateImagesFragment(),"PRIVATE IMAGES");
        sectionPageAdapter.addFragmentsAdapter(new ShowALLImagesFragment(),"ALL IMAGES");
        viewPager.setAdapter(sectionPageAdapter);
    }

    private void initializeTabLayout(){
        binding.tabLayout.setupWithViewPager(binding.viewPager);

        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void initialize(){
        context = this;
        setUpNewViewPager(binding.viewPager);
        initializeTabLayout();
    }

}