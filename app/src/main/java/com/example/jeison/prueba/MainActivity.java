package com.example.jeison.prueba;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.JavaCameraView;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

public class MainActivity extends AppCompatActivity implements CameraBridgeViewBase.CvCameraViewListener2 {


    private static final String TAG = "MainActivity";
    JavaCameraView javaCameraView;
    Mat newMat;
    BaseLoaderCallback loaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status){
                case BaseLoaderCallback.SUCCESS:{
                    javaCameraView.enableView();
                    break;
                }
                default:{
                    super.onManagerConnected(status);
                    break;
                }
            }

        }
    };
    static {

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        javaCameraView = (JavaCameraView)findViewById(R.id.java_camara_view);
        javaCameraView.setVisibility(SurfaceView.VISIBLE);
        javaCameraView.setCvCameraViewListener(this);

    }
    @Override
    protected void onPause(){
        super.onPause();
        if(javaCameraView != null){
            javaCameraView.disableView();
        }
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        if(javaCameraView != null){
            javaCameraView.disableView();
        }
    }
    @Override
    protected void onResume(){
        super.onResume();
        if (OpenCVLoader.initDebug()){
            Log.d(TAG,"OpenCV successfylly loaded");
            loaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);

        }else {
            Log.d(TAG,"OpenCV not loaded");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_2_0,this,loaderCallback);
        }
    }

    @Override
    public void onCameraViewStarted(int width, int height) {
        newMat = new Mat(height,width, CvType.CV_8UC4);

    }

    @Override
    public void onCameraViewStopped() {
        newMat.release();
    }

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        newMat = inputFrame.rgba();
        return newMat;
    }
}
