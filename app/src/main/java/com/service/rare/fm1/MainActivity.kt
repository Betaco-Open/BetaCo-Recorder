package com.service.rare.fm1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Set Timer for 2s
        val timer = object: CountDownTimer(5000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                // Do nothing
            }
            override fun onFinish() {
                // Start the service
                setContentView(R.layout.activity_main2)
            }
        }
        timer.start()
    }
}