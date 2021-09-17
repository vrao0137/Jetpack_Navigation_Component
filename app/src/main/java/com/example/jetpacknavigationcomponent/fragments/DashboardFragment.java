package com.example.jetpacknavigationcomponent.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jetpacknavigationcomponent.databinding.FragmentDashboardBinding;
import com.example.jetpacknavigationcomponent.fragments.adapter.SectionPageAdapter;
import com.example.jetpacknavigationcomponent.fragments.tabfragments.TabOneFragment;
import com.example.jetpacknavigationcomponent.fragments.tabfragments.TabThreeFragment;
import com.example.jetpacknavigationcomponent.fragments.tabfragments.TabTwoFragment;
import com.google.android.material.tabs.TabLayout;

public class DashboardFragment extends Fragment {
    private final String TAG = DashboardFragment.class.getSimpleName();
    private FragmentDashboardBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDashboardBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    private void setUpNewViewPager(ViewPager viewPager){
        SectionPageAdapter sectionPageAdapter = new SectionPageAdapter(getChildFragmentManager());
        sectionPageAdapter.addFragmentsAdapter(new TabOneFragment(),"TAB 1");
        sectionPageAdapter.addFragmentsAdapter(new TabTwoFragment(),"TAB 2");
        sectionPageAdapter.addFragmentsAdapter(new TabThreeFragment(),"TAB 3");
        viewPager.setAdapter(sectionPageAdapter);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpNewViewPager(binding.viewPager);
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
    
}