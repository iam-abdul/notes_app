package com.example.quixote;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class addnote extends AppCompatActivity {
    Button addnote, addphoto;
    EditText title, detail;
    noteMaker noteMaker;
    final List<Bitmap> bitmaps = new ArrayList<>();
    private static final int PICK_IMAGE = 100;
    private static final int SELECT_PICTURE = 100;
    Uri imageUri;
    int noofimg = 0;
    ImageView img;
    String uid;
    private Uri selectedImageUri;
    byte[] inputData;
    String ntitle, ndetail;
    List<InputStream> pics;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnote);
        getSupportActionBar().hide(); // hide the title bar
        uid = getIntent().getStringExtra("userid");
        noteMaker = new noteMaker(getApplicationContext());
        //Toast.makeText(getApplicationContext(),uid,Toast.LENGTH_LONG).show();
        addnote = findViewById(R.id.addnote);
        addphoto = findViewById(R.id.addphoto);
        title = findViewById(R.id.titleofnote);
        detail =findViewById(R.id.details);
        img = findViewById(R.id.imageView);
        ///////////////////////////////////

        //////////////////////////////////

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);
        addphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // openGallery();
                openImageChooser();
            }
        });


        addnote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                noteMaker = new noteMaker(getApplicationContext());
                ntitle = title.getText().toString();
                ndetail = detail.getText().toString();

                InputStream iStream = null;
                List<byte[]> images = new ArrayList<byte[]>();

                for (int i =0; i<noofimg;i++){
                    try {
                        inputData = getBytes(pics.get(i));
                        images.add(inputData);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                Boolean bb = null;
                if (noofimg==1){

                  bb= noteMaker.insertData(uid,ntitle,ndetail,images.get(0), images.get(0),images.get(0));
                }else if (noofimg==2){
                    bb = noteMaker.insertData(uid,ntitle,ndetail,images.get(0),images.get(1),images.get(0));
                }else{
                    bb = noteMaker.insertData(uid,ntitle,ndetail,images.get(0),images.get(1),images.get(2));
                }



                if (bb){
                    Toast.makeText(getApplicationContext(),"Sucessfull",Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    void openImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                selectedImageUri = data.getData();

                pics = new ArrayList<>();

                if (null != selectedImageUri) {
                    img.setImageURI(selectedImageUri);
                }
                ///////////////////////////////////////////////////////
                ClipData clipData = data.getClipData();
                if (clipData != null) {
                    //multiple images selecetd

                    selectedImageUri = data.getClipData().getItemAt(1).getUri();
                    noofimg = clipData.getItemCount();
                    for (int i = 0; i < clipData.getItemCount(); i++) {
                        Uri imageUri = clipData.getItemAt(i).getUri();
                        Log.d("URI", String.valueOf(i));
                        try {
                            if (i < 3) {
                                InputStream inputStream = getContentResolver().openInputStream(imageUri);
                                pics.add(inputStream);
                            }
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                    Log.v("testtt", "working");
                }

//                ///////////////////////////////////////////////////////
//                InputStream iStream = null;
//                try {
//                    iStream = getContentResolver().openInputStream(selectedImageUri);
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }
//                ////////////////////////////////////////////////////////
//                try {
//                    inputData = getBytes(iStream);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
                /////////////////////////////////////////////////////////

            }

        }
    }

    public static byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }



}