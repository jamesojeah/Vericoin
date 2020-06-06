package com.example.vericoin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import com.example.vericoin.utils.Tools;
import com.example.vericoin.utils.ViewAnimation;
import com.spark.submitbutton.SubmitButton;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private View parent_view;

    private volatile boolean stopThread = false;

    float count = 0.000000000000f;

    Thread t;



    private NestedScrollView nested_scroll_view;
    private ImageButton bt_toggle_text, bt_toggle_input;
    private Button bt_hide_text;
    private Button bt_save_input;
    private Button bt_hide_input;
    private View lyt_expand_text;
    private View lyt_expand_input;
    private TextView textView;
    private SubmitButton startBtn;
    private SubmitButton stopBtn;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState==null){
            count=0.000000000000f;
        } else {
            count = savedInstanceState.getFloat("counter", 0.000000000000f);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        bt_toggle_text = (ImageButton) v.findViewById(R.id.bt_toggle_text);
        bt_hide_text = (Button) v.findViewById(R.id.bt_hide_text);
        lyt_expand_text = (View) v.findViewById(R.id.lyt_expand_text);

        startBtn = (SubmitButton) v.findViewById(R.id.startBtn);


        nested_scroll_view = (NestedScrollView) v.findViewById(R.id.nested_scroll_view);


        bt_toggle_text.setOnClickListener(this);
        bt_hide_text.setOnClickListener(this);

        startBtn.setOnClickListener(this);




        textView = (TextView) v.findViewById(R.id.textView);

         t = new Thread(){
             @Override
            public void run(){
              while (!isInterrupted()){

                  try {
                      Thread.sleep(1000);


                     if (getActivity()==null)
                         return;
                      getActivity().runOnUiThread(new Runnable() {
                          @Override
                          public void run() {
                              count = count+0.000000000008f;

                              String countAsString = String.format("%.12f", count);
                              textView.setText(String.valueOf(countAsString));
                          }
                      });

                  }

                  catch (InterruptedException e) {
                      e.printStackTrace();
//                      t.interrupt();

                  }



                  }

              }
        };



//        t.start();

        return v;
    }

    public void stopThread (View v){
        stopThread = true;
    }





    private void toggleSectionText(View view) {
        boolean show = toggleArrow(view);
        if (!show) {
            ViewAnimation.expand(lyt_expand_text, new ViewAnimation.AnimListener() {
                @Override
                public void onFinish() {
                    Tools.nestedScrollTo(nested_scroll_view, lyt_expand_text);
                }
            });
        } else {
            ViewAnimation.collapse(lyt_expand_text);
        }
    }

    public boolean toggleArrow(View view) {
        if (view.getRotation() == 0) {
            view.animate().setDuration(200).rotation(180);
            return true;
        } else {
            view.animate().setDuration(200).rotation(0);
            return false;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_toggle_text:
                toggleSectionText(bt_toggle_text);
                break;
            case R.id.bt_hide_text:
                toggleSectionText(bt_toggle_text);
                break;
            case R.id.startBtn:

                t.start();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putFloat("counter", count);
    }

}