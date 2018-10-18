package com.rit.basa_bari.post_activity;

import android.content.ContentResolver;
import android.content.DialogInterface;
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
import com.rit.basa_bari.models.HostelUpload;


public class AdsHostelPostActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView hoimageView;
    TextInputEditText horent, hophone, holocation, hodescription;
    Spinner homonth,hogender;
    Button hosubmit;
    private Uri imageUri;
    private static final int SELECT_PHOTO = 101;
    private StorageReference mStorageReference;
    private DatabaseReference databaseReference;
    private StorageTask mUploadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_ads_hostel_post );
        setTitle( "Post Ads For Hostel" );
        hoimageView = findViewById( R.id.hostelCamera );
        horent = findViewById( R.id.hostelRent );
        hophone = findViewById( R.id.phone );
        holocation = findViewById( R.id.location );
        hodescription = findViewById( R.id.description );
        hosubmit = findViewById( R.id.submit );
        mStorageReference = FirebaseStorage.getInstance().getReference( "hostel" );
        databaseReference = FirebaseDatabase.getInstance().getReference( "hostels" );
        hoimageView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openGallery();
                    }
                } );
        hosubmit.setOnClickListener( this );
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
            Glide.with( this ).load( imageUri ).into( hoimageView );

            //imageView.setImageURI( selectedImage );
        }
    }
    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType( cR.getType( uri ) );
    }
    @Override
    public void onClick(View v) {
       if (v.getId() == R.id.submit) {
            if(mUploadTask!=null){
                Toast.makeText( AdsHostelPostActivity.this, "Image is progess", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(AdsHostelPostActivity.this, "Successfully Saved", Toast.LENGTH_SHORT).show();

                submitPost();
           }
        }

    }
    private void submitPost() {

        if (imageUri != null) {
            StorageReference storageReference = mStorageReference.child(
                    System.currentTimeMillis()
                            + "." + getFileExtension( imageUri ) );
            mUploadTask=  storageReference.putFile( imageUri ).addOnSuccessListener(
                    new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    String hostelMonth=homonth.getSelectedItem().toString();
                    String hostelGender=hogender.getSelectedItem().toString();
                    Integer hostelRent= Integer.valueOf( horent.getText().toString() );
                    String hostelPhone=hophone.getText().toString();
                    String hostelLocation=holocation.getText().toString();
                    String hostelDescription=hodescription.getText().toString();

                    HostelUpload hostelUpload=new HostelUpload( mStorageReference.getDownloadUrl().toString(),
                                                                hostelMonth, hostelGender,
                                                  hostelRent, hostelPhone, hostelLocation, hostelDescription);
                    String hostelId=databaseReference.push().getKey();
                    databaseReference.child( hostelId ).setValue( hostelUpload );

                }
            } ).addOnFailureListener( new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText( AdsHostelPostActivity.this,e.getMessage(),Toast.LENGTH_SHORT ).show();
                }
            } );
        } else {
            Toast.makeText( this, "No file selected", Toast.LENGTH_SHORT ).show();
        }
    }
}
