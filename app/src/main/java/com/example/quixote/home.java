package com.example.quixote;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.scalified.fab.ActionButton;

import java.util.ArrayList;
import java.util.List;

public class home extends AppCompatActivity {
    ActionButton ab;
    noteMaker nm ;
    noteMaker nm2;
    int nos;
    boolean bln =false;
    int i = 0;
    Button bbtn;
    LinearLayout llout;
    List<String> titles;
    List<String> detailss;
    List<byte[]> photos;
    int k =0;
    byte[] c,d;
    SwipeRefreshLayout  swipeRefreshLayout;
    @Override
    public void onResume() {
        super.onResume();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getSupportActionBar().hide(); // hide the title bar

        String uid = getIntent().getStringExtra("uid");
        Log.v("uid",uid);
        ab = findViewById(R.id.action_button);
        nm = new noteMaker(this);
        llout = findViewById(R.id.llayout);

        swipeRefreshLayout = findViewById(R.id.swipeup);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });







        ab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addnote = new Intent(getApplicationContext(), com.example.quixote.addnote.class);
                addnote.putExtra("userid",uid);
                startActivity(addnote);
            }
        });

        Cursor data = nm.getdatabase();
        nos = 0;
        titles = new ArrayList<String>();
        detailss = new ArrayList<String>();
        photos = new ArrayList<byte[]>();

        while (data.moveToNext()){

            //Log.v("retriving597", String.valueOf(data.getString(0))+String.valueOf(data.getString(1)));
            if((String.valueOf(data.getString(1))).equalsIgnoreCase(uid)){
                //Log.v("retriving597","-->"+String.valueOf(data.getString(1)));

                View crick = getLayoutInflater().inflate(R.layout.homeview,null,false);
                LinearLayout linearLayout = findViewById(R.id.dynamic_view);

                ImageView img = (ImageView) crick.findViewById(R.id.imageView2);
                TextView tvTitle = (TextView) crick.findViewById(R.id.textView3);
                tvTitle.setText(String.valueOf(data.getString(2)));
                TextView tv = (TextView) crick.findViewById(R.id.textView2);
                tv.setId(nos);
                nos+=1;
                if (String.valueOf(data.getString(3)).length()>=40*3){
                    tv.setText(String.valueOf(data.getString(3)).substring(0,40*3)+".....");
                }else {
                    tv.setText(String.valueOf(data.getString(3))+".....");
                }
                byte[] b =data.getBlob(4);
                Bitmap btm =BitmapFactory.decodeByteArray(b,0,b.length);
                Bitmap pro = resize(btm,80,90);
                img.setImageBitmap(pro);
                llout.addView(crick);

                img.setTag(k);
                tvTitle.setTag(k);
                tv.setTag(k);


                titles.add(data.getString(2));
                detailss.add(String.valueOf(data.getString(3)));
                photos.add(b);

                 c = data.getBlob(5);
                 d = data.getBlob(6);


                k = k+1;

            }
        }

    }

    public static void restartActivity(Activity activity){
        if (Build.VERSION.SDK_INT >= 11) {
            activity.recreate();
        } else {
            activity.finish();
            activity.startActivity(activity.getIntent());
        }
    }
    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
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

    public void ouce(View view) {
            Log.v("recharge",String.valueOf(view.getTag()));
    }
    public void ouce3(View view) {
        //Log.v("recharge",titles.get((Integer) view.getTag()));
        Log.v("recharge",String.valueOf(view.getTag()));
        Log.v("recharge",titles.get((Integer) view.getTag()));
        Intent indetail = new Intent(getApplicationContext(), com.example.quixote.indetail.class);
        indetail.putExtra("title",titles.get((Integer) view.getTag()));
        indetail.putExtra("detail",detailss.get((Integer) view.getTag()));
        indetail.putExtra("photo",photos.get((Integer) view.getTag()));
        indetail.putExtra("c",c);
        indetail.putExtra("d",d);
        startActivity(indetail);
    }




}