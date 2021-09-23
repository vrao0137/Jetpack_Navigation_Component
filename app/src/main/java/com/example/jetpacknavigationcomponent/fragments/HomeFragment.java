package com.example.jetpacknavigationcomponent.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jetpacknavigationcomponent.camrerax.activity.CameraXActivity;
import com.example.jetpacknavigationcomponent.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {
    private final String TAG = HomeFragment.class.getSimpleName();
    private FragmentHomeBinding binding;
    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater,container,false);
        initialize();
        return binding.getRoot();
    }

    private void initialize(){
        context = getContext();
        binding.btnOpenCameraX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CameraXActivity.class);
                startActivity(intent);
            }
        });
    }

}