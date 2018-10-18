package com.rit.basa_bari.details_Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.rit.basa_bari.R;
import com.rit.basa_bari.models.MessUpload;

public class MessDetailsActivity extends AppCompatActivity {
    public static final String TAG = MessDetailsActivity.class.getSimpleName();
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;
    public static  final String PHONE_NUMBER_KEY="phoneNumber";
    private MessUpload messUpload;
    private TextView rentView, locationView, phoneNumber, description, month;
    private ImageView imageView1,phoneIcon;

    CallbackManager callbackManager;
    ShareDialog shareDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        FacebookSdk.sdkInitialize( this );
        setContentView( R.layout.activity_details );
        messUpload = new MessUpload();
        phoneIcon=findViewById( R.id.phoneIcon );
        initialization();
        setTitle( "Details" );
     //Init FB
        callbackManager=CallbackManager.Factory.create();
        shareDialog=new ShareDialog( this );
        phoneIcon.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission( MessDetailsActivity.this,
                                                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions( MessDetailsActivity.this,
                                                       new String[]{Manifest.permission.CALL_PHONE},
                                                       MY_PERMISSIONS_REQUEST_CALL_PHONE);


                }else{
                    startActivity(getCall());
                }

            }

        } );

    }
    public  Intent getCall(){
        return  new Intent( Intent.ACTION_CALL ,Uri.parse("tel:" + getIntent().getStringExtra(PHONE_NUMBER_KEY )));

    }


    public void initialization() {
        imageView1 = findViewById( R.id.imageViewMess );
        rentView = findViewById( R.id.rentTV );
        locationView = findViewById( R.id.locationTv );
        month = findViewById( R.id.monthId );
        phoneNumber = findViewById( R.id.phoneTv );
        description = findViewById( R.id.descriptionTv );

        String phoneNo = getIntent().getStringExtra( "phone" );
        String descrip = getIntent().getStringExtra( "description" );
        String mon = getIntent().getStringExtra( "month" );
        String rent = getIntent().getStringExtra( "rent" );
        String location = getIntent().getStringExtra( "location" );
        String image = getIntent().getStringExtra( "image" );
        Glide.with( this ).load( image ).into( imageView1 );

        rentView.setText( rent );
        locationView.setText( location );
        month.setText( mon );
        description.setText( descrip );
        phoneNumber.setText( phoneNo );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate( R.menu.share_menu, menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.nav_share) {
            ShareLinkContent linkContent=new ShareLinkContent.Builder()
                    .setQuote( "Share on FaceBook" )
                    .setContentUrl( Uri.parse( "https://www.facebook.com" ) )
                    .build();
              if(ShareDialog.canShow( ShareLinkContent.class )){
                  shareDialog.show( linkContent );

              }
        }
        return super.onOptionsItemSelected( item );
    }


}


