package com.example.quixote;

import androidx.appcompat.app.AppCompatActivity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class login extends AppCompatActivity {
    EditText name, userid, password, cpassword, phoneno;
    Button signupp;
    DBHelper DB;
    Pattern patterne, patternp, phonepattern;
    Matcher matcher, pmatcher, phonematcher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide(); // hide the title bar

        name = (EditText) findViewById(R.id.name);
        userid = (EditText) findViewById(R.id.semail);
        password = (EditText) findViewById(R.id.lpassword);
        cpassword = (EditText) findViewById(R.id.cpassword);
        phoneno = (EditText) findViewById(R.id.phoneno);
        signupp = (Button) findViewById(R.id.signupp);

        DB = new DBHelper(this);

        signupp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nam = name.getText().toString();
                String uid = userid.getText().toString();
                String pass = password.getText().toString();
                String cpass = cpassword.getText().toString();
                String pno = phoneno.getText().toString();

                String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]{4,25}+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

                String PASS_REGEX = "^([a-z])"
                        + "(?=.*?[0-9].*?[0-9])"

                        + "(?=.*?[A-Z])"
                        + "(?=.*[a-z])(?=.*[A-Z])"
                        + "(?=.*[@#$%^&+*=])"
                        + "(?=\\S+$).{8,15}$";

                String PHONE_NO = "^([^0-5])"
                                    +"([0-9]){9}";



                if (nam.equals("")||uid.equals("")||pass.equals("")||cpass.equals("")||pno.equals("")){
                    Toast.makeText(getApplicationContext(),"Fill all the data",Toast.LENGTH_LONG).show();
                }else {
                    if (pass.equals(cpass)){
                        Boolean exists = DB.checkusername(uid);
                        if (exists){
                            Toast.makeText(getApplicationContext(),"User exists, please login",Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                            startActivity(intent);
                        }else {
                            patterne = Pattern.compile(EMAIL_REGEX);
                            patternp = Pattern.compile(PASS_REGEX);
                            phonepattern = Pattern.compile(PHONE_NO);


                            if (patterne.matcher(uid).matches()){
                                if (patternp.matcher(pass).matches()) {

                                    if (pass.contains(nam)) {
                                        password.setError("Don't use name in password");
                                    } else {



                                    if (phonepattern.matcher(pno).matches()) {
                                        pass = md5(pass);
                                        Boolean isit = DB.insertData(uid, pass, nam, pno);
                                        Toast.makeText(getApplicationContext(),"Account created, login now",Toast.LENGTH_SHORT).show();
                                    } else {
                                        phoneno.setError("Invalid phone number");
                                    }
                                }

                                }else {
                                    password.setError("Must start with lower case, should contain two upper case letters, one special char @#$%*^&+= and two digits");
                                }
                            }else {
                                userid.setError("must be 4-25 char long with valid domain name");
                            }
                        }
                    }else {
                        cpassword.setError("Password's didn't match");
                    }
                }

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