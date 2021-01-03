package com.example.quixote;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class imagess extends AppCompatActivity {
    ImageView img, imgleft, imgright;
    int i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide(); // hide the title bar
        setContentView(R.layout.activity_imagess);
        byte[] cc = getIntent().getByteArrayExtra("img2");
        byte [] dd = getIntent().getByteArrayExtra("img3");
        byte[] bb = getIntent().getByteArrayExtra("img1");

        img = findViewById(R.id.centerimage);
        imgleft = findViewById(R.id.imageView34);
        imgright = findViewById(R.id.imageView44);

        Bitmap btm = BitmapFactory.decodeByteArray(bb,0,bb.length);
        Bitmap btm2 = BitmapFactory.decodeByteArray(cc,0,cc.length);
        Bitmap btm3 = BitmapFactory.decodeByteArray(dd,0,dd.length);

        List<Bitmap> bbbb = new ArrayList<Bitmap>();
        bbbb.add(btm);
        bbbb.add(btm2);
        bbbb.add(btm3);

        //Bitmap pro = resize(btm,80,90);
        img.setImageBitmap(btm);
        i = 1;
        imgright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = i + 1;
                if (i>=3){
                    i = 3 - i;
                }
                img.setImageBitmap(bbbb.get(i));
            }
        });
        imgleft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = i - 1;
                if (i<0){
                    i = 1;
                }
                img.setImageBitmap(bbbb.get(i));
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