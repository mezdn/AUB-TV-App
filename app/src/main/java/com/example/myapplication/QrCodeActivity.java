package com.example.myapplication;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

    public class QrCodeActivity extends Activity {
        TextView descriptionText;
        ImageView qrImageView;

        protected void onCreate (Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_qr_code);
            descriptionText = findViewById(R.id.qr_code_activity_textView);
            descriptionText.setText(R.string.filler_text_short);
            qrImageView = findViewById(R.id.qr_code_activity_imageView);

            String website = "https://secureca.imodules.com/s/1716/interior.aspx?sid=1716&gid=2&pgid=618&cid=2462";
            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
            try {
                BitMatrix bitMatrix = multiFormatWriter.encode(website, BarcodeFormat.QR_CODE,qrImageView.getWidth(),qrImageView.getHeight());
                BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                qrImageView.setImageBitmap(bitmap);

            } catch (WriterException e) {
                e.printStackTrace();
            }
        }

    }