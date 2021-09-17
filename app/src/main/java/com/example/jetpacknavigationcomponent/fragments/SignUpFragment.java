package com.example.jetpacknavigationcomponent.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jetpacknavigationcomponent.R;
import com.example.jetpacknavigationcomponent.databinding.FragmentSignInBinding;
import com.example.jetpacknavigationcomponent.databinding.FragmentSignUpBinding;

public class SignUpFragment extends Fragment {
    private final String TAG = SignUpFragment.class.getSimpleName();
    private FragmentSignUpBinding binding;
    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSignUpBinding.inflate(inflater,container,false);
        initialize();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NavController navController = Navigation.findNavController(view);

        binding.btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_signUpFragment_to_homeFragment);
            }
        });

        binding.btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_signUpFragment_to_signInFragment);
            }
        });
    }

    private void initialize(){
        context = getContext();
    }
}