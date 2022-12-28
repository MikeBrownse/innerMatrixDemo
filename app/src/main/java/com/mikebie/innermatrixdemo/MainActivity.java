package com.mikebie.innermatrixdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.util.Log;
import android.util.SizeF;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView preParaText = findViewById(R.id.prePara);
        TextView camInfoText = findViewById(R.id.camInfo);
        TextView camInnerMatText = findViewById(R.id.camInnerMat);

        final int pixelX = 640, pixelY = 480, camNum = 0;
        preParaText.setText("pixelX = " + pixelX + ", pixelY = " + pixelY + ", camID = " + camNum);

        CameraManager manager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            String[] cameraIds = manager.getCameraIdList();
            for (String nums : cameraIds){
                Log.d("TAG", "onCreate: nums = " + nums);
                CameraCharacteristics character = manager.getCameraCharacteristics(nums);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    Log.d("TAG", "onCreate: physicalCamIDs = " + character.getPhysicalCameraIds());
                }
                SizeF size = character.get(CameraCharacteristics.SENSOR_INFO_PHYSICAL_SIZE);
                float[] focalLengths = character.get(CameraCharacteristics.LENS_INFO_AVAILABLE_FOCAL_LENGTHS);
                String camInfo = "sensor width = " + size.getWidth() + "mm, sensor height = " + size.getHeight() + "mm, available Focal length = " + focalLengths[0] + "mm";
                camInfoText.setText(camInfo);
                Log.e("TAG", "getCameraResolution: " + camInfo);

                double dx = size.getWidth() / (double)pixelX, dy = size.getHeight() / (double)pixelY;
                String camInnerMat = "u0 = " + pixelX / 2.0 + ", v0 = " + pixelY / 2.0 + "; fx = " + focalLengths[0] / dx + ", fy = " + focalLengths[0] / dy;
                camInnerMatText.setText(camInnerMat);
                Log.e("TAG", "getCameraResolution: " + camInnerMat);
            }
//            if (cameraIds.length > camNum) {
//                CameraCharacteristics character = manager.getCameraCharacteristics(cameraIds[camNum]);
//                SizeF size = character.get(CameraCharacteristics.SENSOR_INFO_PHYSICAL_SIZE);
//                float[] focalLengths = character.get(CameraCharacteristics.LENS_INFO_AVAILABLE_FOCAL_LENGTHS);
//                String camInfo = "sensor width = " + size.getWidth() + "mm, sensor height = " + size.getHeight() + "mm, available Focal length = " + focalLengths[0] + "mm";
//                camInfoText.setText(camInfo);
//                Log.e("TAG", "getCameraResolution: " + camInfo);
//
//                double dx = size.getWidth() / (double)pixelX, dy = size.getHeight() / (double)pixelY;
//                String camInnerMat = "u0 = " + pixelX / 2.0 + ", v0 = " + pixelY / 2.0 + "; fx = " + focalLengths[0] / dx + ", fy = " + focalLengths[0] / dy;
//                camInnerMatText.setText(camInnerMat);
//                Log.e("TAG", "getCameraResolution: " + camInnerMat);
//            }
        } catch (CameraAccessException e) {
            Log.e("YourLogString", e.getMessage(), e);
        }
    }
}