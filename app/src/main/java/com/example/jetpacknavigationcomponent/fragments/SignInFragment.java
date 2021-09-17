package com.example.jetpacknavigationcomponent.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.jetpacknavigationcomponent.R;
import com.example.jetpacknavigationcomponent.UserModel;
import com.example.jetpacknavigationcomponent.databinding.FragmentSignInBinding;


public class SignInFragment extends Fragment implements View.OnClickListener {
    private final String TAG = SignInFragment.class.getSimpleName();
    private FragmentSignInBinding binding;
    private Context context;
    private NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSignInBinding.inflate(inflater,container,false);
        initialize();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
    }

    private void initialize(){
        context = getContext();
        binding.btnSignIn.setOnClickListener(this);
        binding.btnSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSignIn:
                navController.navigate(R.id.action_signInFragment_to_homeFragment);
                /*String userName = binding.email.getText().toString().trim();
                String passWord = binding.password.getText().toString().trim();
                if (binding.email.getText().toString().trim().isEmpty()){
                    Toast.makeText(context, "Please Enter Email Address", Toast.LENGTH_SHORT).show();
                }else if (binding.password.getText().toString().trim().isEmpty()){
                    Toast.makeText(context, "Please Enter Password", Toast.LENGTH_SHORT).show();
                }else {
                    UserModel userModel = new UserModel(userName,passWord);
                    SignInFragmentDirections.ActionSignInFragmentToHomeFragment action = SignInFragmentDirections.actionSignInFragmentToHomeFragment(userModel);
                    navController.navigate(action);
                    // navController.navigate(R.id.action_signInFragment_to_homeFragment);
                }*/

                break;
            case R.id.btnSignUp:
                navController.navigate(R.id.action_signInFragment_to_signUpFragment);
                /*NavOptions navOptions = new NavOptions.Builder().setPopUpTo(R.id.action_signInFragment_to_homeFragment,true).build();
                navController.navigate(R.id.action_signInFragment_to_signUpFragment,null,navOptions);*/
                break;
        }
    }
}