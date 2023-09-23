package com.service.rare.recorder

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.Manifest.permission.MANAGE_EXTERNAL_STORAGE
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.RECORD_AUDIO
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.service.rare.recorder.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        setContentView(R.layout.activity_main)

        // Set Timer for 2s
        val timer = object: CountDownTimer(2000, 1000) {
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
        if (!checkPermission()) {
            // Start the service
            this.requestPermissions(arrayOf(android.Manifest.permission.RECORD_AUDIO,
                WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE, MANAGE_EXTERNAL_STORAGE), 0)
            onRequestPermissionsResult(
                0,
                arrayOf(android.Manifest.permission.RECORD_AUDIO,WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE, MANAGE_EXTERNAL_STORAGE),
                intArrayOf(0)
            )
        }
        else{
            timer.start()
        }
    }
    fun checkPermission(): Boolean {

        val result = ContextCompat.checkSelfPermission(this, android.Manifest.permission.RECORD_AUDIO)
        return result == PackageManager.PERMISSION_GRANTED
          }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 0) {
            if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {

                Toast.makeText(this,"Access to Microphone Denied",Toast.LENGTH_SHORT).show()

                if (shouldShowRequestPermissionRationale(RECORD_AUDIO)) {
                        Toast.makeText(this,"You need to access to the permissions",Toast.LENGTH_SHORT).show()
                        this.requestPermissions(arrayOf(RECORD_AUDIO,WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE, MANAGE_EXTERNAL_STORAGE), 0)
                        onRequestPermissionsResult(
                            0,
                            arrayOf(RECORD_AUDIO),
                            intArrayOf(0)
                        )
                } else {
                    this.requestPermissions(arrayOf(RECORD_AUDIO), 0)
                    onRequestPermissionsResult(
                        0,
                        arrayOf(RECORD_AUDIO),
                        intArrayOf(0)
                    )
                }

                }
            else if(checkPermission()) {
                val intent = Intent(this@MainActivity, MainActivity2::class.java)
                startActivity(intent)
            }
        }

    }
}