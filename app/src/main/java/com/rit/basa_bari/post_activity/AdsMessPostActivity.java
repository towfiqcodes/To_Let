package com.rit.basa_bari.post_activity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.rit.basa_bari.R;
import com.rit.basa_bari.models.MessUpload;

public class AdsMessPostActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView imageView;
    TextInputEditText rent, phone, location, description;
    Spinner month,gender;
    Button submit;
    private Uri imageUri;
    private static final int SELECT_PHOTO = 100;
    private DatabaseReference mDatabaseReference;
    private StorageReference mStorageReference;
    private StorageTask mUploadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_ads_mess_post );
        setTitle( "Post Ads For Mess" );

        imageView = findViewById( R.id.camera );
        rent = findViewById( R.id.messRentET );
        phone = findViewById( R.id.phoneET );
        location = findViewById( R.id.locationET );
        description = findViewById( R.id.descriptionET );
        month=findViewById(R.id.monthSpinnerID);
        gender=findViewById(R.id.genderSpinnerID);
        submit = findViewById( R.id.submitBTN );

        mDatabaseReference = FirebaseDatabase.getInstance().getReference( "mess" );
        mStorageReference = FirebaseStorage.getInstance().getReference( "mess" );


        imageView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openGallery();
                    }
                } );
        submit.setOnClickListener( this );
    }


    private void openGallery() {
        Intent galleryIntent = new Intent( Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI );
        startActivityForResult( galleryIntent, SELECT_PHOTO );

    }

    @Override
    protected void onActivityResult(int requestCode, int resultcode, Intent intent) {
        super.onActivityResult( requestCode, resultcode, intent );
        if (requestCode == SELECT_PHOTO && resultcode == RESULT_OK && intent != null && intent.getData() != null) {
            imageUri = intent.getData();
            // Saves image URI as string to Default Shared Preferences
            Glide.with( this ).load( imageUri ).into( imageView );

            //imageView.setImageURI( selectedImage );
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.submitBTN) {
            if(mUploadTask!=null){
                Toast.makeText(AdsMessPostActivity.this, "Image is progess", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(AdsMessPostActivity.this, "Successfully Saved", Toast.LENGTH_SHORT).show();
                submitPost();
            }
        }

    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType( cR.getType( uri ) );
    }

    private void submitPost() {

        if (imageUri != null) {
            StorageReference storageReference = mStorageReference.child(
                    System.currentTimeMillis()
                            + "." + getFileExtension( imageUri ) );
            mUploadTask=  storageReference.putFile( imageUri ).addOnSuccessListener(
                    new OnSuccessListener <UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    String messMonth=month.getSelectedItem().toString().trim();
                    String messGender=gender.getSelectedItem().toString().trim();
                    Integer messRent= Integer.valueOf( rent.getText().toString().trim() );
                    String messPhone=phone.getText().toString().trim();
                    String messLocation=location.getText().toString().trim();
                    String messDescription=description.getText().toString().trim();
                    MessUpload messUpload=new MessUpload( taskSnapshot.getStorage().getDownloadUrl().toString(),
                                                          messMonth, messGender, messRent,
                                                          messPhone, messLocation, messDescription);
                    String messId=mDatabaseReference.push().getKey();
                    mDatabaseReference.child( messId ).setValue( messUpload );

                }
            } ).addOnFailureListener( new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText( AdsMessPostActivity.this,e.getMessage(),Toast.LENGTH_SHORT ).show();
                }
            } );
        } else {
            Toast.makeText( this, "No file selected", Toast.LENGTH_SHORT ).show();
        }
    }
}


