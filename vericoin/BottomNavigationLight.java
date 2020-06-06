package com.example.vericoin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.vericoin.utils.Tools;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class BottomNavigationLight extends AppCompatActivity {

    private static final String TAG = "SignedInActivity";

    //Firebase
    private FirebaseAuth.AuthStateListener mAuthListener;


    private TextView mTextMessage;
    private BottomNavigationView navigation;
    private View search_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation_light);

        Log.d(TAG, "onCreate: started.");

        setupFirebaseAuth();

        initComponent();

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new HomeFragment()).commit();
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_sign_out:
                signOut();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Sign out the current user
     */
    private void signOut(){
        Log.d(TAG, "signOut: signing out");
        FirebaseAuth.getInstance().signOut();
    }

    /*
           ----------------------------- Firebase setup ---------------------------------
        */
    private void setupFirebaseAuth(){
        Log.d(TAG, "setupFirebaseAuth: started.");

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {

                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());

                } else {
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                    Intent intent = new Intent(BottomNavigationLight.this, signInActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
                // ...
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkAuthenticationState();
    }

    private void checkAuthenticationState(){
        Log.d(TAG, "checkAuthenticationState: checking authentication state.");

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(user == null){
            Log.d(TAG, "checkAuthenticationState: user is null, navigating back to login screen.");

            Intent intent = new Intent(BottomNavigationLight.this, signInActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }else{
            Log.d(TAG, "checkAuthenticationState: user is authenticated.");
        }
    }






//        private boolean isConnected(){
//        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
//        NetworkInfo[] networkCallback = connectivityManager.getAllNetworkInfo();
//
//
//        return networkCallback != null;
//    }

//    public boolean isConnected(Context context) {
//
//        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo netinfo = cm.getActiveNetworkInfo();
//
//
//        if (netinfo != null &amp;&amp; netinfo.isConnectedOrConnecting()) {
//            android.net.NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
//            android.net.NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
//
//            if((mobile != null &amp;&amp; mobile.isConnectedOrConnecting()) || (wifi != null &amp;&amp; wifi.isConnectedOrConnecting())) return true;
//        else return false;
//        } else
//        return false;
//    }
//
//    public AlertDialog.Builder buildDialog(Context c) {
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(c);
//        builder.setTitle("No Internet Connection");
//        builder.setMessage("You need to have Mobile Data or wifi to access this. Press ok to Exit");
//
//        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//                finish();
//            }
//        });
//
//        return builder;
//    }

    private void initComponent() {
        search_bar = (View) findViewById(R.id.search_bar);
        mTextMessage = findViewById(R.id.search_text);

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;

                switch (item.getItemId()) {
                    case R.id.navigation_home:
//                        mTextMessage.setText(item.getTitle());
                        Animatoo.animateZoom(BottomNavigationLight.this);
                        selectedFragment = new HomeFragment();
                        break;
//                        return true;
                    case R.id.navigation_withdrawal:
//                        mTextMessage.setText(item.getTitle());
                        Animatoo.animateZoom(BottomNavigationLight.this);
                        selectedFragment = new WithdrawalFragment();

                        break;
//                        return true;
                    case R.id.navigation_settings:
//                        mTextMessage.setText(item.getTitle());
                        Animatoo.animateZoom(BottomNavigationLight.this);
                        selectedFragment = new AboutFragment();
                        break;
//                        return true;
                }
                assert selectedFragment != null;
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        selectedFragment).commit();

                return true;
            }
        });

        NestedScrollView nested_content = (NestedScrollView) findViewById(R.id.nested_scroll_view);
        nested_content.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY < oldScrollY) { // up
                    animateNavigation(false);
//                    animateSearchBar(false);
                }
                if (scrollY > oldScrollY) { // down
                    animateNavigation(true);
//                    animateSearchBar(true);
                }
            }
        });

//        // display image
//        Tools.displayImageOriginal(this, (ImageView) findViewById(R.id.image_1), R.drawable.image_8);
//        Tools.displayImageOriginal(this, (ImageView) findViewById(R.id.image_2), R.drawable.image_9);
//        Tools.displayImageOriginal(this, (ImageView) findViewById(R.id.image_3), R.drawable.image_15);
//        Tools.displayImageOriginal(this, (ImageView) findViewById(R.id.image_4), R.drawable.image_14);
//        Tools.displayImageOriginal(this, (ImageView) findViewById(R.id.image_5), R.drawable.image_12);
//        Tools.displayImageOriginal(this, (ImageView) findViewById(R.id.image_6), R.drawable.image_2);
//        Tools.displayImageOriginal(this, (ImageView) findViewById(R.id.image_7), R.drawable.image_5);
//
//
//        ((ImageButton) findViewById(R.id.bt_menu)).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });

        Tools.setSystemBarColor(this, R.color.grey_5);
        Tools.setSystemBarLight(this);
    }


    boolean isNavigationHide = false;

    private void animateNavigation(final boolean hide) {
        if (isNavigationHide && hide || !isNavigationHide && !hide) return;
        isNavigationHide = hide;
        int moveY = hide ? (2 * navigation.getHeight()) : 0;
        navigation.animate().translationY(moveY).setStartDelay(100).setDuration(300).start();
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseAuth.getInstance().addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            FirebaseAuth.getInstance().removeAuthStateListener(mAuthListener);
        }
    }


//    private void animateSearchBar(final boolean hide) {
//        if (isSearchBarHide && hide || !isSearchBarHide && !hide) return;
//        isSearchBarHide = hide;
//        int moveY = hide ? -(2 * search_bar.getHeight()) : 0;
//        search_bar.animate().translationY(moveY).setStartDelay(100).setDuration(300).start();
    }

