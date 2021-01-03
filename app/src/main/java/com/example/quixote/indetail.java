package com.example.quixote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.Layout;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class indetail extends AppCompatActivity {
    TextView detaile, titlee;
    ImageView be;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indetail);
        getSupportActionBar().hide(); // hide the title bar
        String title = getIntent().getStringExtra("title");
        String detail = getIntent().getStringExtra("detail");
        byte[] cc = getIntent().getByteArrayExtra("c");
        byte [] dd = getIntent().getByteArrayExtra("d");


        byte[] b = getIntent().getByteArrayExtra("photo");
        Bitmap btm =BitmapFactory.decodeByteArray(b,0,b.length);
        Bitmap pro = resize(btm,250,250);

        detaile = findViewById(R.id.ddetail);
        titlee = findViewById(R.id.dtitle);
        be = findViewById(R.id.dimageView);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            detaile.setJustificationMode(Layout.JUSTIFICATION_MODE_INTER_WORD);
        }
        detaile.setText(detail);
        titlee.setText(title);
        be.setImageBitmap(pro);

        be.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentk = new Intent(getApplicationContext(),imagess.class);
                intentk.putExtra("img1",b);
                intentk.putExtra("img2", cc);
                intentk.putExtra("img3", dd);
                startActivity(intentk);
            }
        });


    }

    private static Bitmap resize(Bitmap image, int maxWidth, int maxHeight) {
        if (maxHeight > 0 && maxWidth > 0) {
            int width = image.getWidth();
            int height = image.getHeight();
            float ratioBitmap = (float) width / (float) height;
            float ratioMax = (float) maxWidth / (float) maxHeight;

            int finalWidth = maxWidth;
            int finalHeight = maxHeight;
            if (ratioMax > ratioBitmap) {
                finalWidth = (int) ((float)maxHeight * ratioBitmap);
            } else {
                finalHeight = (int) ((float)maxWidth / ratioBitmap);
            }
            image = Bitmap.createScaledBitmap(image, finalWidth, finalHeight, true);
            return image;
        } else {
            return image;
        }
    }
}