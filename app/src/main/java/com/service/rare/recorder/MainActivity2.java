package com.service.rare.recorder;

import static com.service.rare.recorder.CustomAdapter.LastAdapterPos;
import static java.lang.Integer.valueOf;

import android.content.Intent;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Objects;


public class MainActivity2 extends AppCompatActivity {
    public static String[] files;
    public static boolean hasMPInstance=false;
    public static MediaPlayer mediaPlayer;
    public static String path;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main2);

        Objects.requireNonNull(getSupportActionBar()).hide();
        // Initialize the dataset of the Adapter
        String[] dataset = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9" , "10" , "11" , "12" , "13" , "14" , "15" , "16" , "17" , "18" , "19" , "20" , "21","22","23","24","25","26","27","28","29","30","31","32","33","34","35","36"};
        String[] dataset2 = new String[]{"1/2/1", "2", "3", "4", "5", "6", "7", "8", "9" , "10" , "11" , "12" , "13" , "14" , "15" , "16" , "17" , "18" , "19" , "20" , "21","22","23","24","25","26","27","28","29","30","31","32","33","34","35","36"};
        String[] dataset3 = new String[]{"Sample Title 1", "2", "3", "4", "5", "6", "7", "8", "9" , "10" , "11" , "12" , "13" , "14" , "15" , "16" , "17" , "18" , "19" , "20" , "21","22","23","24","25","26","27","28","29","30","31","32","33","34","35","36"};
//        String[] listOfFiles = Environment.getExternalStoragePublicDirectory(Environment.getExternalStorageState()).list();
//        for (String file : listOfFiles) {
//            Log.d("TAG: Files", "file : " + file);
//        }
         path = Environment.getExternalStorageDirectory().toString()+"/Recordings";
        Log.d("Files", "Path: " + path);
        File directory = new File(path);

        files = directory.list();
        // invert list
        for (int i = 0; i < files.length / 2; i++) {
            String temp = files[i];
            files[i] = files[files.length - 1 - i];
            files[files.length - 1 - i] = temp;
        }
        File[] fileListing = directory.listFiles();
        String[] lastModified = new String[files.length];
        // invert list

        String[] numbering = new String[files.length];
        Log.d("Files", "Size: "+ files.length);

        for (int i = 0; i < files.length; i++)
        {
            long lastMod = fileListing[i].lastModified();
            Date date = new Date(lastMod);
            lastModified[files.length-i-1] = date.getDay() + "/" + date.getMonth() + "/" + valueOf(date.getYear()).toString().substring(1) + " " + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds();
            files[i] = files[i].replace(".mp3","");
            numbering[i]= String.valueOf(i + 1);
            Log.d("Files", "FileName:" + files[i].replace(".mp3",""));
        }

        dataset=numbering.clone();
        dataset2=lastModified.clone();
        dataset3=files.clone();
        // Create a new adapter
        CustomAdapter adapter = new CustomAdapter(dataset,dataset2,dataset3);
        // Create recycler view layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        // Set layout manager.
        RecyclerView recyclerView = findViewById(R.id.recycle);
        recyclerView.setLayoutManager(layoutManager);
        // Set adapter.
        recyclerView.setAdapter(adapter);

        // Show the row item view
//        setContentView(R.layout.text_row_item);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
//        setSupportActionBar(toolbar);

//        DynamicColors.applyToActivitiesIfAvailable(this.getApplication());
//        toolbar.setTitleTextAppearance(this,R.style.BottomNavigationViewTextStyle);
//        NavigationBarView bottomNavigationView = findViewById(R.id.bottom_navigation);
//        NavigationBarView.OnItemSelectedListener listener = new NavigationBarView.OnItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(MenuItem item) {
//                return false;
//            }
//        };
//        bottomNavigationView.setOnItemSelectedListener( listener );
//    }

// Create onClick listener for the button
        Button button = findViewById(R.id.extended_fab);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create a new intent to open the {@link NumbersActivity}
                Intent numbersIntent = new Intent(MainActivity2.this, MainActivity3.class);
                // Start the new activity
                startActivity(numbersIntent);
            }
        });
    }
    public void showSearchDialog(MenuItem menuItem) {
        Log.d("TAG: Click", "onClick: Accepted ");

//        // Create a new intent to open the {@link NumbersActivity}
//        Intent numbersIntent = new Intent(MainActivity2.this, MainActivity.class);
//        // Start the new activity
//        startActivity(numbersIntent);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_app_bar, menu);
        return true;
    }

    @Override
    protected void onRestart() {
        Toast.makeText(this, "Restarted", Toast.LENGTH_SHORT).show();
        super.onRestart();
    }

    @Override
    protected void onResume() {
        String path = Environment.getExternalStorageDirectory().toString()+"/Recordings";
        Log.d("Files", "Path: " + path);
        File directory = new File(path);

        String[] files = directory.list();
        // invert list
        for (int i = 0; i < files.length / 2; i++) {
            String temp = files[i];
            files[i] = files[files.length - 1 - i];
            files[files.length - 1 - i] = temp;
        }
        File[] fileListing = directory.listFiles();
        String[] lastModified = new String[files.length];
        // invert list

        String[] numbering = new String[files.length];
        Log.d("Files", "Size: "+ files.length);

        for (int i = 0; i < files.length; i++)
        {
            long lastMod = fileListing[i].lastModified();
            Date date = new Date(lastMod);
            lastModified[files.length-i-1] = date.getDay() + "/" + date.getMonth() + "/" + valueOf(date.getYear()).toString().substring(1) + " " + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds();
            files[i] = files[i].replace(".mp3","");
            numbering[i]= String.valueOf(i + 1);
            Log.d("Files", "FileName:" + files[i].replace(".mp3",""));
        }

        String[] dataset=numbering.clone();
        String[]  dataset2=lastModified.clone();
        String[] dataset3=files.clone();
        // Create a new adapter
        CustomAdapter adapter = new CustomAdapter(dataset,dataset2,dataset3);
        // Create recycler view layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        // Set layout manager.
        RecyclerView recyclerView = findViewById(R.id.recycle);
        recyclerView.setLayoutManager(layoutManager);
        // Set adapter.
        recyclerView.setAdapter(adapter);
        Toast.makeText(this, "Resumed", Toast.LENGTH_SHORT).show();
        super.onResume();
    }

    public static boolean playRecording(String filePath, View v, int adapterPosition, ImageView prev_image, ImageView play_image) throws IOException {
        //----- ToDo: Convert to a switch/case scenario or faster processing----
        // Create MediaPlayer Instance
        if (!hasMPInstance) {
//            Toast.makeText(v.getContext(), "Situation 0",Toast.LENGTH_SHORT).show();

            mediaPlayer = new MediaPlayer();
            Uri myUri = Uri.parse(filePath); // initialize Uri here
            mediaPlayer.setAudioAttributes(
                    new AudioAttributes.Builder()
                            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                            .setUsage(AudioAttributes.USAGE_MEDIA)
                            .build()
            );
            mediaPlayer.setDataSource(v.getContext(), myUri);
            mediaPlayer.prepare();
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
                    play_image.setImageResource(R.drawable.baseline_play_arrow_24);
                }
            });
            mediaPlayer.start();

            hasMPInstance = true;
            return true;
        } else {
            if(mediaPlayer.isPlaying()) {

                mediaPlayer.stop();
                mediaPlayer.release();
//                prev_image.setImageResource(R.drawable.baseline_play_arrow_24);
                if(adapterPosition==LastAdapterPos){
//                    Toast.makeText(v.getContext(), "Sit 1",Toast.LENGTH_SHORT).show();

                    hasMPInstance=false;
                    return false;

                }else{
                    if(prev_image!=null){
                        prev_image.setImageResource(R.drawable.baseline_play_arrow_24);
//                        Toast.makeText(v.getContext(), "NotNull ",Toast.LENGTH_SHORT).show();
                    }
//                    Toast.makeText(v.getContext(), "Situation 2",Toast.LENGTH_SHORT).show();

                    mediaPlayer = new MediaPlayer();
                    Uri myUri = Uri.parse(filePath); // initialize Uri here
                    mediaPlayer.setAudioAttributes(
                            new AudioAttributes.Builder()
                                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                                    .setUsage(AudioAttributes.USAGE_MEDIA)
                                    .build()
                    );
                    mediaPlayer.setDataSource(v.getContext(), myUri);
                    mediaPlayer.prepare();
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        public void onCompletion(MediaPlayer mp) {
                            play_image.setImageResource(R.drawable.baseline_play_arrow_24);
                        }
                    });
                    mediaPlayer.start();

                    hasMPInstance = true;
                    return true;
                }
            }else {
                if(adapterPosition==LastAdapterPos) {
                    mediaPlayer.release();
                    hasMPInstance=false;
                    return false;
                }
                else{
                if(prev_image!=null){
                    prev_image.setImageResource(R.drawable.baseline_play_arrow_24);
//                    Toast.makeText(v.getContext(), "NotNull ",Toast.LENGTH_SHORT).show();
                }
                mediaPlayer.release();
//                Toast.makeText(v.getContext(), "Situation 3 - "+ LastAdapterPos+"/"+adapterPosition,Toast.LENGTH_SHORT).show();
                    mediaPlayer = new MediaPlayer();
                    Uri myUri = Uri.parse(filePath); // initialize Uri here
                    mediaPlayer.setAudioAttributes(
                            new AudioAttributes.Builder()
                                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                                    .setUsage(AudioAttributes.USAGE_MEDIA)
                                    .build()
                    );
                    mediaPlayer.setDataSource(v.getContext(), myUri);
                    mediaPlayer.prepare();
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        public void onCompletion(MediaPlayer mp) {
                            play_image.setImageResource(R.drawable.baseline_play_arrow_24);
                        }
                    });
                    mediaPlayer.start();

                    hasMPInstance = true;
                    return true;
                }

            }

//            return false;
        }

    }


}

