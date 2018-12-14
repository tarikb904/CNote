package com.example.xinus.generic;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;


public class CreateNewAccount extends AppCompatActivity {

    EditText editTextName,editTextUserName,editTextPassword,editTextEmail,editTextPhoneNumber,editTextUniversityName;
    TextView textViewLogIn,imagePckerCreateAccount;
    Button buttonRegister;
    ProgressDialog progressDialog;
    private static final int SELECTED_PICTURE=1;
    String filePath,s;
    Uri uri;


    FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_account);

        firebaseAuth=FirebaseAuth.getInstance();

        editTextName = (EditText)findViewById(R.id.userragistrationName);
        editTextPassword = (EditText)findViewById(R.id.userragistrationPassword);
        editTextUserName = (EditText)findViewById(R.id.userragistrationUserName);
        editTextEmail =(EditText)findViewById(R.id.userragistrationEmail);
        editTextPhoneNumber =(EditText)findViewById(R.id.userragistrationPhoneNumber);
        editTextUniversityName = (EditText)findViewById(R.id.userragistrationUnivarsityName);

        progressDialog = new ProgressDialog(this);

        buttonRegister = (Button)findViewById(R.id.buttonToRegister);
        textViewLogIn =(TextView) findViewById(R.id.textviewtologin) ;
        imagePckerCreateAccount = (TextView)findViewById(R.id.textviewtoPickImage);




        Typeface typeface = Typeface.createFromAsset(getAssets() , "impact.ttf");
        editTextName.setTypeface(typeface);
        editTextUserName.setTypeface(typeface);
        editTextPassword.setTypeface(typeface);
        editTextEmail.setTypeface(typeface);
        editTextPhoneNumber.setTypeface(typeface);
        editTextUniversityName.setTypeface(typeface);
        buttonRegister.setTypeface(typeface);
        textViewLogIn.setTypeface(typeface);
        imagePckerCreateAccount.setTypeface(typeface);


        imagePckerCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, SELECTED_PICTURE);
            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case SELECTED_PICTURE:
                if(resultCode==RESULT_OK){
                    uri=data.getData();
                    String[]projection={MediaStore.Images.Media.DATA};

                    Cursor cursor=getContentResolver().query(uri, projection, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex=cursor.getColumnIndex(projection[0]);
                    filePath=cursor.getString(columnIndex);
                    cursor.close();

                    final Bitmap yourSelectedImage=BitmapFactory.decodeFile(filePath);


                    uploadImage();

                }
                break;

            default:
                break;
        }

    }



    private void uploadImage() {
        if(uri!=null)
        {

            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading");
            progressDialog.show();

            StorageReference storageReference = FirebaseStorage.getInstance().getReference();
            StorageReference reference = storageReference.child("images/*"+ UUID.randomUUID().toString());
            reference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progressDialog.dismiss();

                    Task<Uri> result = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                    result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String s = uri.toString();
                            setImageUri(s);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext() , "DownloadURL Failed"+e.toString() , Toast.LENGTH_LONG).show();
                        }
                    });

                    Toast.makeText(CreateNewAccount.this , "Uploaded Successfully" , Toast.LENGTH_SHORT).show();
//                    uploadInDataBase(taskSnapshot.getMetadata().getReference().getDownloadUrl().toString());
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(CreateNewAccount.this , "Failed" , Toast.LENGTH_LONG).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                    progressDialog.setMessage("Uploading"+(int)progress + "%");
                }
            });
        }

    }

    private void setImageUri(String s) {
        this.s = s;
    }


    public void LoginAgain(View view) {
        startActivity(new Intent(this , MainActivity.class));
    }

    public void RegisterUser(View view) {


        editTextName = (EditText)findViewById(R.id.userragistrationName);
        editTextPassword = (EditText)findViewById(R.id.userragistrationPassword);
        editTextUserName = (EditText)findViewById(R.id.userragistrationUserName);
        editTextEmail =(EditText)findViewById(R.id.userragistrationEmail);
        editTextPhoneNumber =(EditText)findViewById(R.id.userragistrationPhoneNumber);
        editTextUniversityName = (EditText)findViewById(R.id.userragistrationUnivarsityName);

        final String name = editTextName.getText().toString().trim();
        final String username =editTextUserName.getText().toString().trim();
        final String phonenumber = editTextPhoneNumber.getText().toString().trim();
        final String university = editTextUniversityName.getText().toString().trim();
        final String email = editTextEmail.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();


        if(email.isEmpty()||password.isEmpty()||name.isEmpty()||username.isEmpty()||phonenumber.isEmpty()||university.isEmpty()||s.isEmpty())
        {
            Snackbar.make(view, "Please Fill Up All The nformation. And choose image :)", Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();
            progressDialog.cancel();
        }
        else {

            progressDialog.setMessage("Please wait for sometimes...");
            progressDialog.show();


            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                updateUserdata(name , username,email,phonenumber,university,s);
                                startActivity(new Intent(CreateNewAccount.this , HomeLayout.class));
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext() , "Please Try Again. :)"+task.getException().toString() , Toast.LENGTH_SHORT).show();
                                progressDialog.cancel();
                            }
                        }
                    });

        }

    }

    private void updateUserdata(String name,String username,String email,String phonenumber,String university, String s) {
        progressDialog.cancel();
        String user_id = firebaseAuth.getCurrentUser().getUid();
        firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference current_user_db = firebaseDatabase.getReference().child("Users").child(user_id);

        Users user = new Users(user_id , name , username , email , phonenumber , university , s);

        current_user_db.setValue(user);
        Toast.makeText(getApplicationContext() , "Registration Successful :)" , Toast.LENGTH_SHORT).show();
    }
}
