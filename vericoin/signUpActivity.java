package com.example.vericoin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;


public class signUpActivity extends AppCompatActivity {

    private View parent_view;

    private static final String TAG = "SignUpActivity";

    private FirebaseAuth mAuth;

    //widgets
    private EditText mEmail, mPassword, mVerizonUserId, mVerizonPassword;
    private Button mSignUpBtn;
    private ProgressBar mProgressBar;


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Animatoo.animateFade(signUpActivity.this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_activity);

        mEmail = (EditText) findViewById(R.id.input_email_address);
        mPassword = (EditText) findViewById(R.id.input_password);
        mVerizonUserId = (EditText) findViewById(R.id.input_verizonuserid);
        mVerizonPassword = (EditText) findViewById(R.id.input_verizonpassword);
        mSignUpBtn = (Button) findViewById(R.id.signupbtn);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        mAuth = FirebaseAuth.getInstance();

        parent_view = findViewById(android.R.id.content);

        ((View) findViewById(R.id.signupbtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //check for null valued EditText fields
                if(!isEmpty(mEmail.getText().toString())
                        && !isEmpty(mPassword.getText().toString())
                        && !isEmpty(mVerizonUserId.getText().toString())
                        && !isEmpty(mVerizonPassword.getText().toString())){



                            //Initiate registration task


                    registerNewEmail();


                }else{
                    Toast.makeText(signUpActivity.this, "You must fill out all the fields", Toast.LENGTH_SHORT).show();
                }



            }
        });

        hideSoftKeyboard();

//        Tools.setSystemBarColor(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mAuth.getCurrentUser() != null){
            redirectLoginScreen();
        }
    }



    public void registerNewEmail(){

        showDialog();

        final String email = mEmail.getText().toString().trim();
        String password = mPassword.getText().toString().trim();
        final String verizonId = mVerizonUserId.getText().toString().trim();
        final String verizonPassword = mVerizonPassword.getText().toString().trim();


        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        if (task.isSuccessful()){
                            Log.d(TAG, "onComplete: AuthState: " + FirebaseAuth.getInstance().getCurrentUser().getUid());

                            User user = new User(
                              email,
                              verizonId,
                              verizonPassword
                            );

                            FirebaseDatabase.getInstance().getReference("Users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(signUpActivity.this, "Registration Successful",
                                                Toast.LENGTH_SHORT).show();
                                    }else {
                                        //display a failure message
                                    }
                                }
                            });

                            //send email verificaiton
                            sendVerificationEmail();

                            FirebaseAuth.getInstance().signOut();

                            //redirect the user to the login screen
                            redirectLoginScreen();
                        }
                        if (!task.isSuccessful()) {
                            Toast.makeText(signUpActivity.this, "Unable to Register",
                                    Toast.LENGTH_SHORT).show();
                        }
                        hideDialog();

                        // ...
                    }
                });
    }

    private boolean isEmpty(String string){
        return string.equals("");
    }


    private void showDialog(){
        mProgressBar.setVisibility(View.VISIBLE);

    }

    private void hideDialog(){
        if(mProgressBar.getVisibility() == View.VISIBLE){
            mProgressBar.setVisibility(View.INVISIBLE);
        }
    }

    private void hideSoftKeyboard(){
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    /**
     * Redirects the user to the login screen
     */
    private void redirectLoginScreen(){
        Log.d(TAG, "redirectLoginScreen: redirecting to login screen.");

        Intent intent = new Intent(signUpActivity.this, signInActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * sends an email verification link to the user
     */
    private void sendVerificationEmail() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            user.sendEmailVerification()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(signUpActivity.this, "Sent Verification Email", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(signUpActivity.this, "Couldn't Send Verification Email", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

    }


}
