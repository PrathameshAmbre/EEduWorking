package com.example.eedu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class ProfileActivity extends AppCompatActivity {

    TextView first_name,email,myLearning,wishList,editProfile,help,logout;
    DatabaseReference databaseReference;
    ProgressDialog progressDialog;
    FirebaseAuth mAuth;
    String firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        first_name=findViewById(R.id.name);
        email=findViewById(R.id.email_id);
        myLearning=findViewById(R.id.my_learning);
        wishList=findViewById(R.id.wishlist);
        editProfile=findViewById(R.id.edit_profile);
        help=findViewById(R.id.help);
        logout=findViewById(R.id.log_out);

        mAuth = FirebaseAuth.getInstance();
        firebaseUser=mAuth.getCurrentUser().getUid();
        //firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("users");

        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Loading user data");
        progressDialog.setMessage("Please wait while we load the user data");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        myLearning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mycourseIntent=new Intent(ProfileActivity.this,MyCourseActivity.class);
                mycourseIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(mycourseIntent);
                finish();
            }
        });

        wishList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent favIntent=new Intent(ProfileActivity.this,FavouriteActivity.class);
                favIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(favIntent);
                finish();
            }
        });

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editIntent=new Intent(ProfileActivity.this,EditProfileActivity.class);
                editIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(editIntent);
                finish();
            }
        });

        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent helpIntent=new Intent(ProfileActivity.this,ContactUsActivity.class);
                helpIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(helpIntent);
                finish();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                SendUserToLoginActivity();
            }
        });

        LoadInformation();

    }

    private void SendUserToLoginActivity() {
        Intent loginIntent=new Intent(ProfileActivity.this,LogIn.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginIntent);
        finish();
    }

    public void LoadInformation(){
        databaseReference.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                UserInformation userInformation=snapshot.getValue(UserInformation.class);
                first_name.setText(userInformation.name);
                email.setText(userInformation.email);
                progressDialog.dismiss();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


}