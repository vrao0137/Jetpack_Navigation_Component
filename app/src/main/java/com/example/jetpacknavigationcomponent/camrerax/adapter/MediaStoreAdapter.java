package com.example.jetpacknavigationcomponent.camrerax.adapter;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.jetpacknavigationcomponent.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by nigelhenshaw on 1/06/2016.
 */
public class MediaStoreAdapter extends RecyclerView.Adapter<MediaStoreAdapter.ViewHolder> {

    private Cursor mMediaStoreCursor;
    private final Activity mActivity;
    private final OnClickThumbListener mOnClickThumbListener;
    File[] allFiles ;

    public interface OnClickThumbListener {
        void OnClickImage(Uri imageUri);
        void OnClickVideo(Uri videoUri);
    }
    public MediaStoreAdapter(Activity activity) {
        this.mActivity = activity;
        this.mOnClickThumbListener = (OnClickThumbListener)activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.media_image_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        /*Bitmap bitmap = getBitmapFromMediaStore(position);
        if (bitmap != null) {
            holder.getImageView().setImageBitmap(bitmap);
        }*/
        Glide.with(mActivity)
                .load(getUriFromMediaStore(position))
                .into(holder.getImageView());
    }

    @Override
    public int getItemCount() {
        return (mMediaStoreCursor == null) ? 0 : mMediaStoreCursor.getCount();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final ImageView mImageView;

        public ViewHolder(View itemView) {
            super(itemView);

            mImageView = (ImageView) itemView.findViewById(R.id.mediastoreImageView);
            mImageView.setOnClickListener(this);
        }

        public ImageView getImageView() {
            return mImageView;
        }

        @Override
        public void onClick(View v) {
            getOnClickUri(getAdapterPosition());
        }
    }

    private Cursor swapCursor(Cursor cursor) {
        if (mMediaStoreCursor == cursor) {
            return null;
        }
        Cursor oldCursor = mMediaStoreCursor;
        this.mMediaStoreCursor = cursor;
        if (cursor != null) {
            this.notifyDataSetChanged();
        }
        return oldCursor;
    }

    public void changeCursor(Cursor cursor) {
        Cursor oldCursor = swapCursor(cursor);
        if (oldCursor != null) {
            oldCursor.close();
        }
    }

    private Bitmap getBitmapFromMediaStore(int position) {
        int idIndex = mMediaStoreCursor.getColumnIndex(MediaStore.Files.FileColumns._ID);
        int mediaTypeIndex = mMediaStoreCursor.getColumnIndex(MediaStore.Files.FileColumns.MEDIA_TYPE);

        mMediaStoreCursor.moveToPosition(position);
        switch (mMediaStoreCursor.getInt(mediaTypeIndex)) {
            case MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE:
                return MediaStore.Images.Thumbnails.getThumbnail(
                        mActivity.getContentResolver(),
                        mMediaStoreCursor.getLong(idIndex),
                        MediaStore.Images.Thumbnails.MICRO_KIND,
                        null
                );
            case MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO:
                return MediaStore.Video.Thumbnails.getThumbnail(
                    mActivity.getContentResolver(),
                        mMediaStoreCursor.getLong(idIndex),
                        MediaStore.Video.Thumbnails.MICRO_KIND,
                        null
                );
            default:
                return null;
        }
    }

    private Uri getUriFromMediaStore(int position) {
        int dataIndex = mMediaStoreCursor.getColumnIndex(MediaStore.Files.FileColumns.DATA);

        mMediaStoreCursor.moveToPosition(position);

        String dataString = mMediaStoreCursor.getString(dataIndex);
        Log.e("","dataString:- "+dataString);
        return Uri.parse("file://" + dataString);
        /*StringBuffer buffer = new StringBuffer();
        int read = -1;
        try {
            FileInputStream fileInputStream = mActivity.openFileInput(String.valueOf(position));
            while ((read=fileInputStream.read()) != -1){
                buffer.append(read);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        File folder = new File(mActivity.getFilesDir() + "/CameraX/");
        folder.mkdirs();

        int dataIndex = mMediaStoreCursor.getColumnIndex(String.valueOf(buffer));
        Log.e("","dataIndex:- "+dataIndex);

        mMediaStoreCursor.moveToPosition(position);
        String dataString = null;
        while (dataIndex != -1){
            dataString  = mMediaStoreCursor.getString(dataIndex);
        }
        Log.e("","dataString:- "+dataString);
        return Uri.parse("file://" + dataString);*/
    }

    private void getOnClickUri(int position) {
        int mediaTypeIndex = mMediaStoreCursor.getColumnIndex(MediaStore.Files.FileColumns.MEDIA_TYPE);
        int dataIndex = mMediaStoreCursor.getColumnIndex(MediaStore.Files.FileColumns.DATA);

        mMediaStoreCursor.moveToPosition(position);
        String dataString = mMediaStoreCursor.getString(dataIndex);
        String authorities = mActivity.getPackageName() + ".fileprovider";
        Uri mediaUri = FileProvider.getUriForFile(mActivity, authorities, new File(dataString));
//        Uri mediaUri = Uri.parse("file://" + dataString);

        switch (mMediaStoreCursor.getInt(mediaTypeIndex)) {
            case MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE:
                mOnClickThumbListener.OnClickImage(mediaUri);
                break;
            case MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO:
                mOnClickThumbListener.OnClickVideo(mediaUri);
                break;
            default:
        }

    }
}
