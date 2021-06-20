package com.example.hydrangeahacks;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.Manifest.permission.CAMERA;

public class DonationCheck extends AppCompatActivity {

    private TextView textView;
    private SurfaceView surfaceView;
    private CameraSource cameraSource;
    private TextRecognizer textRecognizer;
    private String stringResult = null;
    @Override
    protected void onDestroy() {
        super.onDestroy();
        cameraSource.release();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation_check);
 
     
            ActivityCompat.requestPermissions(this,new String[]{CAMERA}, PackageManager.PERMISSION_GRANTED);
        }
        public void textRecognizer(){
            textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();
            cameraSource = new CameraSource.Builder(getApplicationContext(), textRecognizer)
                    .setRequestedPreviewSize(1280, 1024)
                    .build();
            surfaceView = findViewById(R.id.surfaceView);
            surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
                @SuppressLint("MissingPermission")
                @Override
                public void surfaceCreated(SurfaceHolder holder) {
                    try {
                        cameraSource.start(surfaceView.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

                }

                @Override
                public void surfaceDestroyed(SurfaceHolder holder) {
                    cameraSource.stop();
                }
            });

            textRecognizer.setProcessor(new Detector.Processor<TextBlock>() {
                @Override
                public void release() {

                }

                @Override
                public void receiveDetections(Detector.Detections<TextBlock> detections) {
                    SparseArray<TextBlock> sparseArray = detections.getDetectedItems();
                    StringBuilder stringBuilder = new StringBuilder();
                    Format f = new SimpleDateFormat("MM/dd/yy");
                    Calendar cal = Calendar.getInstance();
                    Date date1 = new Date();
                    String strDate = f.format(new Date());
                    cal.setTime(date1);
                    cal.add(Calendar.DAY_OF_YEAR, 7);
                    Date date = cal.getTime();
                    String strDate2 = f.format(date);

                    for (int i = 0; i<sparseArray.size(); ++i){
                        TextBlock textBlock = sparseArray.valueAt(i);
                        if (textBlock != null && textBlock.getValue() != null){



                            stringBuilder.append(textBlock.getValue() + " ");
                        }

                    }

                    final String stringText = stringBuilder.toString();

                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {

                            String mystring = stringText.replaceAll("[\\n\\t ]", " ");
                            //stringResult = mystring;

                            Pattern pattern = Pattern.compile("\\d{2}/\\d{2}/\\d{2}");
                            Matcher matcher = pattern.matcher(mystring);
                            List<String> thing = new ArrayList<>();

                            while (matcher.find()) {
                                //  finalthing.append(matcher.group());
                                thing.add(matcher.group());
                            }
                            stringResult = thing.toString();
                            if (thing.size() == 0) {
                                stringResult = "NO DATE FOUND!";
                            }
                            else {
                                for (int j = 0; j< thing.size(); ++j){

                                    if (thing.get(j).compareTo(strDate2) > 0){
                                        stringResult = "You Can Donate It!";
                                    }
                                    else {
                                        stringResult = "This item cannot be donated";
                                    }


                                }

                            }



                            //String[] tokensPM = mystring.split(" ");
                            resultantObtained();
                        }
                    });

                }
            });
        }
        private void resultantObtained(){
            setContentView(R.layout.activity_donation_check);
            textView = findViewById(R.id.textView);
            textView.setText(stringResult);

        }


        public void buttonStart(View view){
            setContentView(R.layout.activity_surface_view);
            textRecognizer();
    }
}