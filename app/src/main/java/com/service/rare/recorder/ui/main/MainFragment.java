package com.service.rare.recorder.ui.main;


import static com.service.rare.recorder.MainActivity3.recordingComplete;

import androidx.lifecycle.ViewModelProvider;

import android.icu.text.DecimalFormat;
import android.icu.text.NumberFormat;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.button.MaterialButton;
import com.service.rare.recorder.MainActivity3;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.service.rare.recorder.R;

import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadPoolExecutor;

import kotlin.coroutines.CoroutineContext;

public class MainFragment extends Fragment {

    private MainViewModel mViewModel;
    public static long milisnow;
    public static Timer timer2;
    public static TimerTask testing;

    public static CountDownTimer countDownTimer;

    public static long milistillnow;

    public static String timeString;
    public static long secondsPassed;

    public static MainFragment newInstance() {
        return new MainFragment();
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        // TODO: Use the ViewModel


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        view.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(recordingComplete){
                    MainActivity3.startRecording();
                }
                MainActivity3.getRecorder().start();
                final Handler handler = new Handler();
                 timer2 = new Timer();
                 testing = new TimerTask() {
                    public void run() {
                        handler.post(new Runnable() {
                            public void run() {
                            TextView textView = (TextView) view.findViewById(R.id.clock);

                             countDownTimer= new CountDownTimer(86400000, 1000) {

                                    public void onTick(long millisUntilFinished) {
//                                        milisnow=86400000-millisUntilFinished;
                                        ;
                                        // Used for formatting digit to be in 2 digits only
//                                        NumberFormat f = new DecimalFormat("00");
//                                        long hour = (milisnow / 3600000) % 24;
//                                        long min = (milisnow / 60000) % 60;
//                                        long sec = (milisnow / 1000) % 60;

//                                        textView.setText(f.format(hour) + "." + f.format(min) + "." + f.format(sec));
                                        //                                        long hour = (milisnow / 3600000) % 24;
//                                        long min = (milisnow / 60000) % 60;
//                                        long sec = (milisnow / 1000) % 60;
//
//                                        timeString="00.00.00";
//                                        Integer hh=Integer.valueOf(timeString.substring(0,1));
//                                        Integer mm=Integer.valueOf(timeString.substring(3,4));
//                                        Integer ss=Integer.valueOf(timeString.substring(6,7));

                                        secondsPassed++;

                                        NumberFormat f = new DecimalFormat("00");
                                        long hour = (secondsPassed / 3600) % 24;
                                        long min = (secondsPassed / 60) % 60;
                                        long sec = (secondsPassed ) % 60;

                                        textView.setText(f.format(hour) + "." + f.format(min) + "." + f.format(sec));
//                                        textView.setText(String.valueOf(secondsPassed));


                                    }
                                    // When the task is over it will print 00:00:00 there
                                    public void onFinish() {
                                        textView.setText("00.00.00");
                                    }
                                }.start();

//                                Toast.makeText(view.getContext(), "test", Toast.LENGTH_SHORT).show();
                            }


                        });


                    }
                };

                timer2.schedule(testing,1000);
                Toast.makeText(getContext(), "Recording started", Toast.LENGTH_SHORT).show();
                MainActivity3.isRecording= true;

            }
        });
        view.findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity3.isPaused){
                    MainActivity3.getRecorder().resume();
                    Toast.makeText(getContext(), "Recording resumed", Toast.LENGTH_SHORT).show();
                    MaterialButton button = (MaterialButton) v;
                    ((Button) v).setText("Pause");
                    button.setIcon(getResources().getDrawable(R.drawable.baseline_pause_24));
                    MainActivity3.isPaused = false;
                    MainActivity3.isRecording = true;
//                    timer2.schedule(testing,100);
                    countDownTimer.start();
                }else{
                MainActivity3.getRecorder().pause();
                MaterialButton button = (MaterialButton) v;
                ((Button) v).setText("Resume");
                button.setIcon(getResources().getDrawable(R.drawable.baseline_play_arrow_24));
                Toast.makeText(getContext(), "Recording paused", Toast.LENGTH_SHORT).show();
                MainActivity3.isPaused = true;
                MainActivity3.isRecording = true;
                // Stop Timer Execution

                    countDownTimer.cancel();

                }
            }
        });
        view.findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity3.getRecorder().stop();
                MainActivity3.getRecorder().release();
                Toast.makeText(getContext(), "Recording stopped", Toast.LENGTH_SHORT).show();
                MainActivity3.isRecording    = false;
                MainActivity3.isPaused = false;
                recordingComplete = true;
                timer2.cancel();
                TextView textView = (TextView) view.findViewById(R.id.clock);
                textView.setText("00.00.00");
                countDownTimer.cancel();
                secondsPassed=0;
            }
        });
        return view;
    }





}