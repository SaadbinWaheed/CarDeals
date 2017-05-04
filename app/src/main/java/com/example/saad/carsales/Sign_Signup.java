package com.example.saad.carsales;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Sign_Signup extends AppCompatActivity {

    EditText Email,Password;
    Button Sign_in,Sign_up;
    CheckBox Show_Pass;
    FirebaseAuth firebaseAuth;
    String name,contact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_signup);

        firebaseAuth= FirebaseAuth.getInstance();
        Firebase.setAndroidContext(this);
        if (firebaseAuth.getCurrentUser() != null)
            Toast.makeText(Sign_Signup.this,firebaseAuth.getCurrentUser().getEmail().toString(),Toast.LENGTH_LONG).show();


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
                Intent i = new Intent(Sign_Signup.this, Signup_Form.class);
                startActivity(i);
            }
        });
        Sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent signin=new Intent(Sign_Signup.this,MainActivity.class);
//                startActivity(signin);
                Toast.makeText(Sign_Signup.this,"Checking",Toast.LENGTH_LONG).show();
                userLogin();
            }
        });
    }


    private void userLogin() {
        final String username = Email.getText().toString();
        String password = Password.getText().toString();

        final Firebase ref = new Firebase("https://car-sales-f4f9c.firebaseio.com/").child("Users");

        if (!(username.equals("") || password.equals(""))) {


            firebaseAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(Task<AuthResult> task) {
                    if (task.isSuccessful()) {

                        Toast.makeText(Sign_Signup.this, username, Toast.LENGTH_SHORT).show();
                        if (firebaseAuth.getCurrentUser() != null) {

                            ref.addChildEventListener(new ChildEventListener() {
                                @Override
                                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                    Toast.makeText(Sign_Signup.this, dataSnapshot.child("Email").getValue().toString(), Toast.LENGTH_SHORT).show();
                                    if (dataSnapshot.child("Email").getValue().toString().equals(username)) {
                                        name = dataSnapshot.child("Name").getValue().toString();
                                        contact = dataSnapshot.child("Contact info").getValue().toString();
                                    } else
                                    {name = "Default";
                                    contact = "000";}


                                    Toast.makeText(Sign_Signup.this, "Logged in", Toast.LENGTH_LONG).show();
                                    Intent signin = new Intent(Sign_Signup.this, MainActivity.class);
                                    Bundle b = new Bundle();
                                    b.putString("Name", name);
                                    b.putString("Contact Info", contact);
                                    signin.putExtras(b);

                                    startActivity(signin);
                                }

                                @Override
                                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                                }

                                @Override
                                public void onChildRemoved(DataSnapshot dataSnapshot) {

                                }

                                @Override
                                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                                }

                                @Override
                                public void onCancelled(FirebaseError firebaseError) {

                                }
                            });
                        }



                    }

                }
            });
        }
        else
        {
            Toast.makeText(Sign_Signup.this, "Fill in the Username and Password", Toast.LENGTH_LONG).show();
        }



    }


}
