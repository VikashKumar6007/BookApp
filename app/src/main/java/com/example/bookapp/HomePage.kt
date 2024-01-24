package com.example.bookapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.cardview.widget.CardView

class HomePage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)

      val myCardView = findViewById<CardView>(R.id.card_view1)

        myCardView.setOnClickListener{
            val intent = Intent(this,CDS::class.java)
            startActivity(intent)
        }

    }
}