package com.example.saad.carsales;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class Sign_Signup extends AppCompatActivity {

    EditText Email,Password;
    Button Sign_in,Sign_up;
    CheckBox Show_Pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_signup);

        Sign_in = (Button) findViewById(R.id.signin);
        Show_Pass = (CheckBox) findViewById(R.id.show_pass);
        Email = (EditText) findViewById(R.id.email);
        Password = (EditText) findViewById(R.id.password);

        final int inputtype = Password.getInputType();
        Show_Pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Show_Pass.isChecked()){
                    Password.setInputType(1);
                }
                else{
                    Password.setInputType(inputtype);
                }
            }
        });
        Sign_up = (Button) findViewById(R.id.signup);

        Sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Sign_Signup.this, Seller_Login.class);
                startActivity(i);
            }
        });
        Sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signin=new Intent(Sign_Signup.this,AD_details.class);
                startActivity(signin);
            }
        });
    }
}
