package com.example.jetpacknavigationcomponent.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jetpacknavigationcomponent.R;
import com.example.jetpacknavigationcomponent.UserModel;
import com.example.jetpacknavigationcomponent.databinding.FragmentHomeBinding;
import com.example.jetpacknavigationcomponent.databinding.FragmentSignInBinding;

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
    }
}