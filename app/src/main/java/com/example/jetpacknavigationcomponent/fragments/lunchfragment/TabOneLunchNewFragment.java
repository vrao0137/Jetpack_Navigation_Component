package com.example.jetpacknavigationcomponent.fragments.lunchfragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jetpacknavigationcomponent.R;
import com.example.jetpacknavigationcomponent.UserModel;
import com.example.jetpacknavigationcomponent.databinding.FragmentTabOneBinding;
import com.example.jetpacknavigationcomponent.databinding.FragmentTabOneLunchNewBinding;
import com.example.jetpacknavigationcomponent.fragments.tabfragments.TabOneFragment;

public class TabOneLunchNewFragment extends Fragment {
    private final String TAG = TabOneLunchNewFragment.class.getSimpleName();
    private FragmentTabOneLunchNewBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentTabOneLunchNewBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null){
            TabOneLunchNewFragmentArgs args = TabOneLunchNewFragmentArgs.fromBundle(getArguments());
            UserModel userModel = args.getUser();
            binding.txvName.setText(userModel.getUserName());
            binding.txvPassword.setText(userModel.getPassWord());
        }
    }
}