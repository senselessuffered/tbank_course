package com.example.bestlibrary.infoscreen

import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.bestlibrary.R


class InfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        val infoTextView: TextView = findViewById(R.id.info_text)
        val imageView: ImageView = findViewById(R.id.info_image)
        val backButton: ImageButton = findViewById(R.id.button_back)

        val info = intent.getStringExtra("info")
        val id = intent.getIntExtra("id", -1)

        infoTextView.text = info

        val resId = resources.getIdentifier("o$id", "drawable", packageName)
        if (resId != 0) {
            imageView.setImageResource(resId)
        }

        backButton.setOnClickListener {
            finish()
        }
    }
}
