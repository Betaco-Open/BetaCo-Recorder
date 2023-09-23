package com.service.rare.recorder;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.provider.Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.icu.text.DecimalFormat;
import android.icu.text.NumberFormat;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.service.rare.recorder.ui.main.MainFragment;

import java.io.File;
import java.io.IOException;
import java.net.InterfaceAddress;
import java.net.URI;

public class MainActivity3 extends AppCompatActivity {
    public static File file;
    public static MediaRecorder recorder;
    public static boolean isRecording = false;
    public static boolean isPaused = false;
    public static Integer ITERATION = 0;
    public static boolean recordingComplete = false;
    public static boolean reopened = false;
    public static MediaRecorder getRecorder() {
        return recorder;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        isRecording = false;
        isPaused = false;
        recordingComplete = false;
        getSupportActionBar().hide();
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_WB);
//        //create empty file first

        file = new File(Environment.getExternalStorageDirectory() + "/Recordings");
        if(!file.isDirectory()){
            file.mkdirs();
        }
        if (checkPermission(WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
        } else {
            requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE}, 1);
        }

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            if(checkPermission(ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION)){
                Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
            }else{
                Intent intent = new Intent(ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                startActivity(intent);
            }

        }

        while (file.exists()) {
            ITERATION++;
            file = new File(Environment.getExternalStorageDirectory() + "/Recordings/recording_" + ITERATION.toString() + ".mp3");
        }
//        Toast.makeText(this, file.getAbsolutePath().toString(), Toast.LENGTH_SHORT).show();
//        onRequestPermissionsResult(1, new String[]{WRITE_EXTERNAL_STORAGE}, new int[]{PackageManager.PERMISSION_GRANTED});
//        enforcePermission(WRITE_EXTERNAL_STORAGE, 1, 1, "TODO: message if thrown");

        if(!recordingComplete){
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (file.exists()) {
                Toast.makeText(this, file.getAbsolutePath().toString(), Toast.LENGTH_LONG).show();
            }
        recorder.setOutputFile(file.getAbsolutePath());
        try {
            recorder.prepare();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }}
        else{
            reopened = true;
            startRecording();
        }
//        try {
//            recorder.prepare();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//


        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow();
        }
    }
    public static void startRecording() {
        //create empty filefirst
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_WB);

        Log.d("file", file.getAbsolutePath());
//
//        if(MainActivity3.checkPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//            Toast.makeText(getContext(), "Permission granted", Toast.LENGTH_SHORT).show();
//        }else {
//            requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
//        }


        while (file.exists()) {
            ITERATION++;
            file = new File(Environment.getExternalStorageDirectory() +"/Recordings"+ "/recording_" + ITERATION.toString() + ".mp3");
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        recorder.setOutputFile(file.getAbsolutePath());
        try {
            recorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

        recordingComplete = false;
    }

    public static boolean checkPermission(String writeExternalStorage) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            return Environment.isExternalStorageManager();
        }
        return false;


    }

    @Override
    public void onBackPressed() {

            if (isRecording){
                recorder.stop();
                recorder.release();
            }else if (isPaused){
                recorder.stop();
                recorder.release();
            }else {
                if(recordingComplete){

            }else {

                recorder.release();
                    ITERATION--;
                    file.delete();
            }}

        super.onBackPressed();
    }
//    public static void createNewRec(Context context) {
//        File file = new File(Environment.getExternalStorageDirectory() + "/new_" + ITERATION.toString() + ".mp3");
////
////        while (file.exists()) {
////            ITERATION++;
////            file = new File(Environment.getExternalStorageDirectory() + "/recording_" + ITERATION.toString() + ".mp3");
////        }
//////        Toast.makeText(this, file.getAbsolutePath().toString(), Toast.LENGTH_SHORT).show();
//////        onRequestPermissionsResult(1, new String[]{WRITE_EXTERNAL_STORAGE}, new int[]{PackageManager.PERMISSION_GRANTED});
//////        enforcePermission(WRITE_EXTERNAL_STORAGE, 1, 1, "TODO: message if thrown");
////        try {
////            file.createNewFile();
////        } catch (IOException e) {
////            throw new RuntimeException(e);
////        }
////        if (file.exists()) {
////            Toast.makeText(context, file.getAbsolutePath().toString(), Toast.LENGTH_LONG).show();
////        }
////        recorder.setOutputFile(file.getAbsolutePath());
////        try {
////            recorder.prepare();
////        } catch (IOException e) {
////            throw new RuntimeException(e);
////        }
//    }
public static void startTimer(View v) {
    TextView textView= v.findViewById(R.id.clock);

    //Run Timer in background
    new CountDownTimer(30000, 1000) {

        public void onTick(long millisUntilFinished) {// Used for formatting digit to be in 2 digits only
            NumberFormat f = new DecimalFormat("00");
            long hour = (millisUntilFinished / 3600000) % 24;
            long min = (millisUntilFinished / 60000) % 60;
            long sec = (millisUntilFinished / 1000) % 60;
            textView.setText(f.format(hour) + ":" + f.format(min) + ":" + f.format(sec));

        }

        public void onFinish() {
            textView.setText("done!");
        }
    }.start();

}

}
