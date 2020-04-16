package com.example.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

    public class QrCodeActivity extends Activity {
        TextView descriptionText;
        ImageView qrImageView;

        protected void onCreate (Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_qr_code);
            descriptionText = findViewById(R.id.qr_code_activity_textView);
            descriptionText.setText(R.string.donate_to_aub_text);
            qrImageView = findViewById(R.id.qr_code_activity_imageView);
            qrImageView.setImageDrawable(getResources().getDrawable(R.drawable.qr_code));
        }

    }