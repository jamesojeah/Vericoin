package com.example.vericoin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class SettingsFragment extends Fragment implements View.OnClickListener{

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_out:
                signOut();

                break;
        }
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()){
//            case R.id.sign_out:
//                signOut();
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }

    /**
     * Sign out the current user
     */
    private void signOut(){
        Log.d(TAG, "signOut: signing out");
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(getActivity(), signInActivity.class);
        startActivity(intent);
    }


}
