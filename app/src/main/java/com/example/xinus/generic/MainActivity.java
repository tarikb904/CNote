package com.example.xinus.generic;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    EditText editTextForEmail,editTextForPassword;
    Button buttonForLogIn,buttonForCreateAccount;

    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextForEmail = (EditText)findViewById(R.id.loginemail);
        editTextForPassword = (EditText)findViewById(R.id.loginpassword);

        buttonForLogIn = (Button)findViewById(R.id.loginbutton);
        buttonForCreateAccount =(Button)findViewById(R.id.logincreateaccount) ;

        progressDialog = new ProgressDialog(this);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser()!= null)
        {
            startActivity(new Intent(this , HomeLayout.class));
        }


        Typeface typeface = Typeface.createFromAsset(getAssets() , "roboto.ttf");
        editTextForEmail.setTypeface(typeface);
        editTextForPassword.setTypeface(typeface);
        buttonForLogIn.setTypeface(typeface);
        buttonForCreateAccount.setTypeface(typeface);


        buttonForLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("Please wait sometimes. :)");
                progressDialog.show();

                if(editTextForEmail.getText().toString().isEmpty()||editTextForPassword.getText().toString().trim().isEmpty())
                {
                    Snackbar.make(v, "Username or Password Is Empty", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    progressDialog.cancel();
                }
                else {
                    logInVerification();
                }
            }
        });
    }

    private void logInVerification() {
        firebaseAuth.signInWithEmailAndPassword(editTextForEmail.getText().toString().trim() , editTextForPassword.getText().toString().trim())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            progressDialog.cancel();
                            Toast.makeText(MainActivity.this , "Login Successful" , Toast.LENGTH_LONG).show();
                            startActivity(new Intent(MainActivity.this , HomeLayout.class));
                        }
                        else
                        {
                            progressDialog.cancel();
                            Toast.makeText(MainActivity.this , "Login Failed" , Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    public void CreateAccount(View view) {
        startActivity(new Intent(this , CreateNewAccount.class));
    }
}
