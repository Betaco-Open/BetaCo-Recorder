package com.service.rare.recorder

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity
import com.service.rare.recorder.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)

        // Set Timer for 2s
        val timer = object: CountDownTimer(5000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                // Do nothing
            }
            override fun onFinish() {
                // Start the service
//                setContentView(R.layout.activity_main2)
                // Create an Intent to start MainActivity2
                val intent = Intent(this@MainActivity, MainActivity2::class.java)
                startActivity(intent)

            }
        }
        timer.start()
    }
}