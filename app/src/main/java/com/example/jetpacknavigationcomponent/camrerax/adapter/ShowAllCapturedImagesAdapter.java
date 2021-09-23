package com.example.jetpacknavigationcomponent.camrerax.adapter;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.jetpacknavigationcomponent.camrerax.activity.ShowAllCapturedImagesActivity;
import com.example.jetpacknavigationcomponent.camrerax.activity.SingleImageShowFullScreenActivity;
import com.example.jetpacknavigationcomponent.databinding.MediaImageViewBinding;

import java.util.List;

public class ShowAllCapturedImagesAdapter  extends RecyclerView.Adapter<ShowAllCapturedImagesAdapter.ViewHolder>{
    private Activity mActivity;
    private List<String> listOfImages;


    public ShowAllCapturedImagesAdapter(ShowAllCapturedImagesActivity showAllCapturedImagesActivity, List<String> listOfImages) {
        this.mActivity = showAllCapturedImagesActivity;
        this.listOfImages = listOfImages;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(MediaImageViewBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ShowAllCapturedImagesAdapter.ViewHolder holder, int position) {
        Glide.with(mActivity)
                .load(getUriFromMediaStore(position))
                .into(holder.binding.mediastoreImageView);

        holder.binding.mediastoreImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fullScreenIntent = new Intent(v.getContext(), SingleImageShowFullScreenActivity.class);
                fullScreenIntent.putExtra("ImageUri",getUriFromMediaStore(position));
                mActivity.startActivity(fullScreenIntent);
            }
        });

    }

    private String getUriFromMediaStore(int position) {
        String dataString = listOfImages.get(position);
    //    Log.e("FilePath","ShowAllCapturedImagesAdapter:- "+"/data/user/0/com.example.jetpacknavigationcomponent/files/CameraX/" + dataString);
        return "/data/user/0/com.example.jetpacknavigationcomponent/files/CameraX/" + dataString;
    }

    @Override
    public int getItemCount() {
        return listOfImages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        MediaImageViewBinding binding;
        public ViewHolder(MediaImageViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
