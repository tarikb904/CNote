package com.example.xinus.generic;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class ImageUpload extends AppCompatActivity {

    private static final int SELECTED_PICTURE=1;
    ImageView imageViewToShowSelectedImage;
    Button uploadButton;
    TextView imageChooserTextView;
    String filePath;
    Uri uri;

    StorageReference storageReference;
    private String type;
    private String descripton;
    private String courseTitle;
    private String chapterName;
    private String courseCode;
    private String semister;
    private String year;

    String s;

    FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_upload);

        Bundle bundle = getIntent().getExtras();

        type =bundle.getString("Type").toString();
        descripton = bundle.getString("Description").toString() ;
        courseTitle = bundle.getString("CourseTitle").toString() ;
        chapterName = bundle.getString("ChapterName").toString() ;
        courseCode = bundle.getString("CourseCode").toString();
        semister =  bundle.getString("Semister").toString();
        year = bundle.getString("Year").toString() ;

        imageChooserTextView= (TextView)findViewById(R.id.imagePickerForImageUpload);
        uploadButton = (Button)findViewById(R.id.buttonUploadImageUpload);
        imageViewToShowSelectedImage = (ImageView)findViewById(R.id.imageViewToShow);




        imageChooserTextView.setOnClickListener(new View.OnClickListener() {
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

                    imageViewToShowSelectedImage.setImageBitmap(yourSelectedImage);

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

            storageReference = FirebaseStorage.getInstance().getReference();
            StorageReference reference = storageReference.child("images/*"+ UUID.randomUUID().toString());
            s= reference.toString();
            Toast.makeText(ImageUpload.this , storageReference.toString() , Toast.LENGTH_SHORT).show();
            reference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progressDialog.dismiss();

                    Task<Uri> result = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                    result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String s = uri.toString();
                            uploadInDataBase(s);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext() , "DownloadURL Failed"+e.toString() , Toast.LENGTH_LONG).show();
                        }
                    });

                    Toast.makeText(ImageUpload.this , "Uploaded Successfully" , Toast.LENGTH_SHORT).show();
//                    uploadInDataBase(taskSnapshot.getMetadata().getReference().getDownloadUrl().toString());
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(ImageUpload.this , "Failed" , Toast.LENGTH_LONG).show();
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

    private void uploadInDataBase(String s) {



        MaterialUpdateModel materialUpdateModel =new  MaterialUpdateModel(type ,s,descripton , chapterName , courseTitle ,courseCode ,semister,year);
        firebaseDatabase = FirebaseDatabase.getInstance();
            DatabaseReference databaseReference = firebaseDatabase.getReference().child("Users").child(type).child(descripton);
            Toast.makeText(getApplicationContext() , databaseReference.toString() , Toast.LENGTH_SHORT).show();
            databaseReference.setValue(materialUpdateModel);



        Toast.makeText(ImageUpload.this , "Successfully stored in Database" , Toast.LENGTH_SHORT);

        startActivity(new Intent(this , HomeLayout.class));


    }


}
