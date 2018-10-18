package com.rit.basa_bari.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rit.basa_bari.MainActivity;
import com.rit.basa_bari.R;
import com.rit.basa_bari.models.OwnerProfile;
import com.rit.basa_bari.models.RenterProfile;
import com.rit.basa_bari.show_post_activity.Show_Mess_Activity;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    TextInputEditText email,password;
    Button login;
    List<OwnerProfile> ownerProfileList;
    List<RenterProfile> renterProfileList;
    DatabaseReference oDatabaseReference,rDatabaseReference;
    OwnerProfile ownerProfile;
    RenterProfile renterProfile;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Sign In");
        initializationView();
        clickButton();
        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent( LoginActivity.this, MainActivity.class));
            finish();
        }

        ownerProfileList=new ArrayList<>();
        renterProfileList=new ArrayList<>();
        oDatabaseReference= FirebaseDatabase.getInstance().getReference("OwnerProfile");
        rDatabaseReference=FirebaseDatabase.getInstance().getReference("RenterProfile");


        oDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot setData:dataSnapshot.getChildren()){
                    OwnerProfile ownerProfile=setData.getValue(OwnerProfile.class);
                    ownerProfileList.add(ownerProfile);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText( LoginActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        rDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot setData:dataSnapshot.getChildren()){
                    RenterProfile renterProfile=setData.getValue(RenterProfile.class);
                    renterProfileList.add(renterProfile);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    public void initializationView(){
        email=findViewById(R.id.emailEt);
        password=findViewById(R.id.passwordEt);
        login=findViewById(R.id.loginBtn);

    }
    public void clickButton(){
        login.setOnClickListener(this);

    }

    public boolean checkValidity() {
        View focusView = null;
        boolean cancel = false;
        String uEmail = email.getText().toString();
        String uPassword = password.getText().toString();

        if (TextUtils.isEmpty(uEmail)) {
            // focusView=userName;
            cancel = true;
            email.setError("Enter a valid email");
        } else if (TextUtils.isEmpty(uPassword)) {
            // focusView = pass;
            cancel = true;
            password.setError("Enter a valid password");
        }
        return cancel;
    }

    @Override
    public void onClick(View view) {
        String uEmail=email.getText().toString().trim();
        String uPassword=password.getText().toString().trim();

        if(view.getId()==R.id.loginBtn){

            if(checkValidity()){
                Toast.makeText( this,"Ooopss!! you might have left something",Toast.LENGTH_LONG ).show();

            }else {


                int i;
                for(i=0;i<ownerProfileList.size();i++){
                    ownerProfile=ownerProfileList.get(i) ;
                }

                mAuth.signInWithEmailAndPassword(uEmail, uPassword)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.

                                if (!task.isSuccessful()) {
                                    // there was an error
                                    if (password.length() < 6) {
                                        password.setError("minimum 6 ");
                                    } else {
                                        Toast.makeText(LoginActivity.this,"authentication failed", Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });
            }

            String oEmail=ownerProfile.getEmail();
            String oPass=ownerProfile.getPassword();
                if(oEmail.equals(uEmail)&& oPass.equals(uPassword)){
                    Toast.makeText( LoginActivity.this,"Owner Matched!!!",Toast.LENGTH_LONG ).show();

                }else {
                    int j;
                    for(j=0;j<renterProfileList.size();j++){
                        renterProfile=renterProfileList.get(j);
                    }
                    String rEmail=renterProfile.getEmail();
                    String rPass=renterProfile.getPassword();

                    if(rEmail.equals(uEmail)&& rPass.equals(uPassword)){
                        Toast.makeText( LoginActivity.this,"Renter Matched!!!",Toast.LENGTH_LONG ).show();

                    }else{
                        Toast.makeText( this,"Ooopss!! your given credentials didn't matched",Toast.LENGTH_LONG ).show();
                    }

                }


            }


        }

    }

