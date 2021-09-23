package com.example.jetpacknavigationcomponent.camrerax.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jetpacknavigationcomponent.camrerax.adapter.ShowCameraXAllCapturedImagesAdapter;
import com.example.jetpacknavigationcomponent.databinding.FragmentPrivateImagesShowBinding;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ShowPrivateImagesFragment extends Fragment {
    private final String TAG = ShowPrivateImagesFragment.class.getSimpleName();
    private FragmentPrivateImagesShowBinding binding;
    private Context context;
    private final List<String> listOfImages = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPrivateImagesShowBinding.inflate(inflater,container,false);
        initialize();
        return binding.getRoot();
    }

    private void initialize(){
        context = getActivity();

        listOfImages.clear();
        File pathOfImages = new File(context.getFilesDir() + "/CameraX/");
        if (!pathOfImages.exists()) pathOfImages.mkdir();
        for (File files : pathOfImages.listFiles()) {
            String filenames = files.getName().toLowerCase();
            if (filenames.endsWith(".jpg") || filenames.endsWith("jpeg")) {
                listOfImages.add(filenames);
            }
        }

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String ABC = gson.toJson(listOfImages);
        Log.e(TAG,"listOfImages:- "+ABC);

        initializeAdapter();
    }

    private void initializeAdapter(){
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 3);
        binding.rcvShowAllImages.setLayoutManager(gridLayoutManager);
        ShowCameraXAllCapturedImagesAdapter showAllCapturedImagesAdapter = new ShowCameraXAllCapturedImagesAdapter(context, listOfImages);
        binding.rcvShowAllImages.setAdapter(showAllCapturedImagesAdapter);
    }
}