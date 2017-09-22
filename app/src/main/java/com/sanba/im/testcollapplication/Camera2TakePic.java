package com.sanba.im.testcollapplication;


import android.Manifest;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Surface;
import android.view.TextureView;

import java.util.Arrays;

/**
 * 作者:Created by 简玉锋 on 2017/2/21 16:14
 * 邮箱: jianyufeng@38.hn
 */

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class Camera2TakePic extends AppCompatActivity implements TextureView.SurfaceTextureListener {
    private TextureView mPreviewView;
    private HandlerThread mThreadHandler;
    private Handler mHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.camer2_take_pic);
        mThreadHandler = new HandlerThread("CAMERA2");
        mThreadHandler.start();
        mHandler = new Handler(mThreadHandler.getLooper());
        mPreviewView = (TextureView) findViewById(R.id.textureview);
        mPreviewView.setSurfaceTextureListener(this);
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        CameraManager cameraManager = (CameraManager) getSystemService(CAMERA_SERVICE);
        try {
            String[] cameraIdList = cameraManager.getCameraIdList();
            //获取可用相机设备列表
            CameraCharacteristics characteristics = cameraManager.getCameraCharacteristics(cameraIdList[1]);
            //在这里可以通过CameraCharacteristics设置相机的功能,当然必须检查是否支持
            characteristics.get(CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL);
            //就像这样
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            cameraManager.openCamera(cameraIdList[1], mCameraDeviceStateCallback, mHandler);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private CameraDevice.StateCallback mCameraDeviceStateCallback = new CameraDevice.StateCallback() {
        @Override
        public void onOpened(CameraDevice camera) {
            try {
                startPreview(camera);
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onDisconnected(CameraDevice camera) {
        }

        @Override
        public void onError(CameraDevice camera, int error) {
        }
    };


    private CaptureRequest.Builder mPreviewBuilder;

    private void startPreview(CameraDevice camera) throws CameraAccessException {
        SurfaceTexture texture = mPreviewView.getSurfaceTexture();
        texture.setDefaultBufferSize(mPreviewView.getWidth(), mPreviewView.getHeight());
        Surface surface = new Surface(texture);
        try {
            mPreviewBuilder = camera.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
        mPreviewBuilder.addTarget(surface);
        camera.createCaptureSession(Arrays.asList(surface), mSessionStateCallback, mHandler);
    }

    private CameraCaptureSession.StateCallback mSessionStateCallback = new CameraCaptureSession.StateCallback() {
        @Override
        public void onConfigured(CameraCaptureSession session) {
            try {
                //session.capture(mPreviewBuilder.build(), mSessionCaptureCallback, mHandler);
                session.setRepeatingRequest(mPreviewBuilder.build(), mSessionCaptureCallback, mHandler);
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onConfigureFailed(CameraCaptureSession session) {
        }
    };
    private CameraCaptureSession.CaptureCallback mSessionCaptureCallback =
            new CameraCaptureSession.CaptureCallback() {

                @Override
                public void onCaptureCompleted(CameraCaptureSession session, CaptureRequest request,
                                               TotalCaptureResult result) {
                    Log.e("sdfds","fsfsd");

                }

                @Override
                public void onCaptureProgressed(CameraCaptureSession session, CaptureRequest request,
                                                CaptureResult partialResult) {
                    Log.e("sdfds","fsfsd");
                }
            };

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }
}
