package com.example.bookapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonStart :Button = findViewById(R.id.loginBtn)
        val ContinueBtn : Button =findViewById(R.id.skipBtn)
        buttonStart.setOnClickListener {
            val intent= Intent( this, PhoneAuth::class.java)
            startActivity(intent)
        }


        ContinueBtn.setOnClickListener {
            val intent =Intent(this, HomePage::class.java)
            startActivity(intent)
        }

    }
}