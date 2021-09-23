package com.example.jetpacknavigationcomponent.camrerax.activity;

import androidx.annotation.NonNull;
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
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.Rational;
import android.util.Size;
import android.view.Surface;
import android.view.TextureView;
import android.widget.Toast;

import com.example.jetpacknavigationcomponent.databinding.ActivityCameraXactivityBinding;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

public class CameraXActivity extends AppCompatActivity {
    private final String TAG = CameraXActivity.class.getSimpleName();
    private ActivityCameraXactivityBinding binding;
    Context context;
    private final String[] REQUIRED_PERMISSION = new String[]{"android.permission.CAMERA","android.permission.WRITE_EXTERNAL_STORAGE"};
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
            int REQUEST_CODE_PERMISSION = 101;
            ActivityCompat.requestPermissions(this,REQUIRED_PERMISSION, REQUEST_CODE_PERMISSION);
        }

        binding.ivGotoImages.setOnClickListener(v -> {
            Intent intent = new Intent(context, ShowALLImagesActivity.class);
            startActivity(intent);
        });
    }

    private void startCamera(){
        CameraX.unbindAll();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            Rational aspectRatio = new Rational(textureView.getWidth(),textureView.getHeight());
            Size screen = new Size(textureView.getWidth(),textureView.getHeight());
            PreviewConfig pConfig = new PreviewConfig.Builder().setTargetAspectRatio(aspectRatio).setTargetResolution(screen).build();
            Preview preview = new Preview(pConfig);
            preview.setOnPreviewOutputUpdateListener(output -> {
                textureView.setSurfaceTexture(output.getSurfaceTexture());
                updateTransform();
            });

            ImageCaptureConfig imageCaptureConfig = new ImageCaptureConfig.Builder().setCaptureMode(ImageCapture.CaptureMode.MIN_LATENCY)
                    .setTargetRotation(context.getDisplay().getRotation()).build();

            final ImageCapture imgCap = new ImageCapture(imageCaptureConfig);
            binding.imgCature.setOnClickListener(v -> {
                File path = new File(getFilesDir() + "/CameraX/");
                String filename = UUID.randomUUID().toString()+".jpg";
                path.mkdirs();
                FileOutputStream  fileOutputStream = null;
                try {
                    fileOutputStream = openFileOutput(filename,MODE_PRIVATE);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                finally {
                    try {
                        assert fileOutputStream != null;
                        fileOutputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                File file = new File(path,filename);
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }

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
            });

            CameraX.bindToLifecycle(this, preview, imgCap);
        }
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