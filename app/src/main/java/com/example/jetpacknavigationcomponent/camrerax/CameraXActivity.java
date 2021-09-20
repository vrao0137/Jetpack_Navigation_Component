package com.example.jetpacknavigationcomponent.camrerax;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraX;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureConfig;
import androidx.camera.core.Preview;
import androidx.camera.core.PreviewConfig;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.util.Rational;
import android.util.Size;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.example.jetpacknavigationcomponent.databinding.ActivityCameraXactivityBinding;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CameraXActivity extends AppCompatActivity {
    private final String TAG = CameraXActivity.class.getSimpleName();
    private ActivityCameraXactivityBinding binding;
    Context context;
    private int REQUEST_CODE_PERMISSION = 101;
    private String[] REQUIRED_PERMISSION = new String[]{"android.permission.CAMERA","android.permission.WRITE_EXTERNAL_STORAGE"};
    TextureView textureView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCameraXactivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initialize();
    }

    private void initialize(){
        context = this;
        textureView = binding.textureView;

        if (allPermissionGranted()){
            startCamera();
        }else {
            ActivityCompat.requestPermissions(this,REQUIRED_PERMISSION,REQUEST_CODE_PERMISSION);
        }

        binding.ivGotoImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AllShowImagesActivity.class);
                startActivity(intent);
            }
        });
    }

    private void startCamera(){
        CameraX.unbindAll();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            Log.e(TAG,"Work If condition");
            Rational aspectRatio = new Rational(textureView.getWidth(),textureView.getHeight());
            Size screen = new Size(textureView.getWidth(),textureView.getHeight());
            PreviewConfig pConfig = new PreviewConfig.Builder().setTargetAspectRatio(aspectRatio).setTargetResolution(screen).build();
            Preview preview = new Preview(pConfig);
            preview.setOnPreviewOutputUpdateListener(new Preview.OnPreviewOutputUpdateListener() {
                @Override
                public void onUpdated(Preview.PreviewOutput output) {
                    ViewGroup parent = (ViewGroup) textureView.getParent();
                   /* Bitmap bitmap = BitmapFactory.decodeResource(getResources(), parent.addView(textureView));

                    // Set image as bitmap for fourth ImageView
                    iv4.setImageBitmap(bitmap);*/
                    // parent.removeView(textureView);
                   // parent.addView(textureView);
                    textureView.setSurfaceTexture(output.getSurfaceTexture());
                    updateTransform();
                }
            });

            ImageCaptureConfig imageCaptureConfig = new ImageCaptureConfig.Builder().setCaptureMode(ImageCapture.CaptureMode.MIN_LATENCY)
                    .setTargetRotation(getWindowManager().getDefaultDisplay().getRotation()).build();

            final ImageCapture imgCap = new ImageCapture(imageCaptureConfig);
            binding.imgCature.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyyMMddHHmmss", Locale.US);
                    File file = new File(getBatchDirectoryName(), mDateFormat.format(new Date())+ ".jpg");
                   // File file = new File(Environment.getExternalStorageDirectory()+"/"+ UUID.randomUUID().toString()+".jpg");
                    imgCap.takePicture(file, new ImageCapture.OnImageSavedListener() {
                        @Override
                        public void onImageSaved(@NonNull File file) {
                            String msg = "Pic captured at " + file.getAbsolutePath();
                            Toast.makeText(CameraXActivity.this, msg, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onError(@NonNull ImageCapture.UseCaseError useCaseError, @NonNull String message, Throwable cause) {
                            String msg = "Pic captured failed: " + message;
                            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                            if (cause != null){
                                cause.printStackTrace();
                            }
                        }
                    });
                }
            });

            CameraX.bindToLifecycle(this, preview, imgCap);
        }
    }

    public String getBatchDirectoryName() {
        String app_folder_path = "";
        app_folder_path = Environment.getExternalStorageDirectory() + "/camerax";
        File dir = new File(app_folder_path);
        if (!dir.exists() && !dir.mkdirs()) {
            Toast.makeText(context, "Trip", Toast.LENGTH_SHORT).show();
        }
        return app_folder_path;
    }

    private void updateTransform(){
        Matrix mx = new Matrix();
        float w = textureView.getMeasuredWidth();
        float h = textureView.getMeasuredHeight();

        float cX = w/2f;
        float cY = h/2f;

        int rotationDgr;
        int rotation = (int)textureView.getRotation();

        switch (rotation){
            case Surface.ROTATION_0:
                rotationDgr = 0;
                break;
            case Surface.ROTATION_90:
                rotationDgr = 90;
                break;
            case Surface.ROTATION_180:
                rotationDgr = 180;
                break;
            case Surface.ROTATION_270:
                rotationDgr = 270;
                break;
            default:
                return;
        }
        mx.postRotate((float)rotationDgr,cX,cY);
        textureView.setTransform(mx);
    }

    private boolean allPermissionGranted(){
        for (String permission : REQUIRED_PERMISSION){
            if (ContextCompat.checkSelfPermission(this,permission) != PackageManager.PERMISSION_GRANTED){
                return false;
            }
        }
        return true;
    }
}