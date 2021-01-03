package com.example.quixote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {
    Button login, signup;
    EditText mail,pass;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide(); // hide the title bar
        login = (Button) findViewById(R.id.login);
        signup = (Button) findViewById(R.id.signup);
        mail = (EditText) findViewById(R.id.email);
        pass = (EditText) findViewById(R.id.password);
        DB = new DBHelper(this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userid = mail.getText().toString();
                String password = pass.getText().toString();
                if (userid.equals("")||password.equals("")){
                    Toast.makeText(getApplicationContext(),"Empty fields",Toast.LENGTH_LONG).show();
                    
                }else {
                    password = md5(password);
                    Boolean isitcorrect = DB.checkUsernamePassword(userid,password);
                    if (isitcorrect){
                        Toast.makeText(getApplicationContext(),"welcome",Toast.LENGTH_LONG).show();
                        Intent next = new Intent(getApplicationContext(),home.class);
                        next.putExtra("uid",userid);
                        startActivity(next);
                    }else {
                        Toast.makeText(getApplicationContext(),"who the hell",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),login.class);
                startActivity(intent);
            }
        });

    }
    public String md5(String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i=0; i<messageDigest.length; i++)
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));

            return hexString.toString();
        }catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }


}