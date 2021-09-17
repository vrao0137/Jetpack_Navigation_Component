package com.example.jetpacknavigationcomponent.fragments.tabfragments;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.jetpacknavigationcomponent.R;
import com.example.jetpacknavigationcomponent.UserModel;
import com.example.jetpacknavigationcomponent.databinding.FragmentTabOneBinding;
import com.example.jetpacknavigationcomponent.fragments.DashboardFragmentDirections;
import com.example.jetpacknavigationcomponent.fragments.SignInFragmentDirections;

public class TabOneFragment extends Fragment {
    private final String TAG = TabOneFragment.class.getSimpleName();
    private FragmentTabOneBinding binding;
    private NavController navController;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentTabOneBinding.inflate(inflater,container,false);
        initialize();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
    }

    private void initialize(){

        binding.tbnNewFragmentLunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = binding.edtName.getText().toString().trim();
                String passWord = binding.edtPassword.getText().toString().trim();
                UserModel userModel = new UserModel(userName,passWord);
                DashboardFragmentDirections.ActionDashboardFragmentToTabOneLunchNewFragment action = DashboardFragmentDirections.actionDashboardFragmentToTabOneLunchNewFragment(userModel);
                navController.navigate(action);
               // navController.navigate(R.id.action_tabOneFragment_to_tabOneLunchNewFragment);
            }
        });
    }
}