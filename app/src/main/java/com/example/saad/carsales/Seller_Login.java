package com.example.saad.carsales;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Seller_Login extends AppCompatActivity {
    EditText mail,name,pass,c_pass,contact;
    Button Proceed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seller_login);
        mail = (EditText) findViewById(R.id.s_mail);
        name = (EditText) findViewById(R.id.s_name);
        pass = (EditText) findViewById(R.id.s_pass);
        c_pass = (EditText) findViewById(R.id.s_c_pass);
        contact = (EditText) findViewById(R.id.s_contact);
        Proceed = (Button) findViewById(R.id.btn_proceed);

        Proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!mail.getText().toString().equals("") &&
                        !mail.getText().toString().equals("") &&
                        !name.getText().toString().equals("") &&
                        !pass.getText().toString().equals("") &&
                        !c_pass.getText().toString().equals("") &&
                        !contact.getText().toString().equals("") )
                {
                    if(pass.getText().toString().equals(c_pass.getText().toString())){
                        //Toast.makeText(Seller_Login.this, "PROCEED", Toast.LENGTH_SHORT).show();
                        Check();
                        Intent i = new Intent(Seller_Login.this,AD_details.class);
                        startActivity(i);
                    }
                    else{
                        Toast.makeText(Seller_Login.this, "Password Confirmation Error ", Toast.LENGTH_SHORT).show();
                        Check();
                    }
                }
                else{
                    Toast.makeText(Seller_Login.this, "Please Fill Information", Toast.LENGTH_SHORT).show();
                    Check();
                }
            }
        });
    }

    void Check(){
        if(mail.getText().toString().equals(""))
            mail.setBackgroundColor(Color.parseColor("#ffa0ab"));
        if(name.getText().toString().equals(""))
            name.setBackgroundColor(Color.parseColor("#ffa0ab"));
        if(pass.getText().toString().equals(""))
            pass.setBackgroundColor(Color.parseColor("#ffa0ab"));
        if(c_pass.getText().toString().equals(""))
            c_pass.setBackgroundColor(Color.parseColor("#ffa0ab"));
        if(contact.getText().toString().equals(""))
            contact.setBackgroundColor(Color.parseColor("#ffa0ab"));

        if(!mail.getText().toString().equals(""))
            mail.setBackgroundColor(Color.parseColor("#ffffff"));
        if(!name.getText().toString().equals(""))
            name.setBackgroundColor(Color.parseColor("#ffffff"));
        if(!pass.getText().toString().equals(""))
            pass.setBackgroundColor(Color.parseColor("#ffffff"));
        if(!c_pass.getText().toString().equals(""))
            c_pass.setBackgroundColor(Color.parseColor("#ffffff"));
        if(!contact.getText().toString().equals(""))
            contact.setBackgroundColor(Color.parseColor("#ffffff"));

        if(!pass.getText().toString().equals(c_pass.getText().toString()))
            c_pass.setBackgroundColor(Color.parseColor("#ffa0ab"));
    }
}
