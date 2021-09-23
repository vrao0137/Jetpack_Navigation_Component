package com.example.jetpacknavigationcomponent.camrerax.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ShareActionProvider;
import androidx.core.view.MenuItemCompat;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.jetpacknavigationcomponent.R;
import com.example.jetpacknavigationcomponent.databinding.ActivityFullScreenImageBinding;

public class SingleImageShowFullScreenActivity extends AppCompatActivity implements View.OnLongClickListener{
    private final String TAG = SingleImageShowFullScreenActivity.class.getSimpleName();
    private ActivityFullScreenImageBinding binding;
    private Context context;
    private Uri mImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFullScreenImageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initialize();
    }

    private void initialize(){
        context = this;
        binding.fullScreenImageView.setOnLongClickListener(this);
       // Calling on Adapter ShowAllCapturedImagesAdapter
        String imageUri = getIntent().getStringExtra("ImageUri");
        Glide.with(this)
                .load(imageUri)
                .into(binding.fullScreenImageView);

        // Calling on Adapter MediaStoreAdapter
        Intent callingActivityIntent = getIntent();
        if(callingActivityIntent != null) {
            mImageUri = callingActivityIntent.getData();
            if(mImageUri != null && binding.fullScreenImageView != null) {
                Glide.with(this)
                        .load(mImageUri)
                        .into(binding.fullScreenImageView);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.full_image_share, menu);

        MenuItem menuItem = menu.findItem(R.id.image_share_menu);
        ShareActionProvider shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);
        shareActionProvider.setShareIntent(createShareIntent());
        return true;
    }

    private Intent createShareIntent() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("image/*");
        shareIntent.putExtra(Intent.EXTRA_STREAM, mImageUri);
        return shareIntent;
    }

    @Override
    public boolean onLongClick(View v) {
        Intent shareIntent = createShareIntent();
        startActivity(Intent.createChooser(shareIntent, "send to"));
        return true;
    }
}