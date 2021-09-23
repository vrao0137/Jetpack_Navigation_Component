package com.example.jetpacknavigationcomponent.camrerax.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.example.jetpacknavigationcomponent.camrerax.adapter.MediaStoreAdapter;
import com.example.jetpacknavigationcomponent.camrerax.adapter.ShowAllCapturedImagesAdapter;
import com.example.jetpacknavigationcomponent.databinding.ActivityShowAllCapturedImagesBinding;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ShowAllCapturedImagesActivity extends AppCompatActivity{
    private final String TAG = ShowAllCapturedImagesActivity.class.getSimpleName();
    private ActivityShowAllCapturedImagesBinding binding;
    Context context;
    private List<String> listOfImages = new ArrayList<>();
    private ShowAllCapturedImagesAdapter showAllCapturedImagesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityShowAllCapturedImagesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initialize();
    }

    private void initialize(){
        context = this;

        listOfImages.clear();
        File pathOfImages = new File(getFilesDir() + "/CameraX/");
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
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        binding.rcvShowAllImages.setLayoutManager(gridLayoutManager);
        showAllCapturedImagesAdapter = new ShowAllCapturedImagesAdapter(this,listOfImages);
        binding.rcvShowAllImages.setAdapter(showAllCapturedImagesAdapter);
    }
}