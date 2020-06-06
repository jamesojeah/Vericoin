package com.example.vericoin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import com.example.vericoin.utils.Tools;
import com.example.vericoin.utils.ViewAnimation;
import com.google.android.material.snackbar.Snackbar;

public class WithdrawalFragment extends Fragment implements View.OnClickListener {


    private View parent_view;

    private NestedScrollView nested_scroll_view;
    private ImageButton bt_toggle_text, bt_toggle_input;
    private Button bt_hide_text;
    private Button bt_save_input;
    private Button bt_hide_input;
    private View lyt_expand_text;
    private View lyt_expand_input;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_withdrawal, container, false);




        bt_toggle_text = v.findViewById(R.id.bt_toggle_text);
        bt_hide_text = v.findViewById(R.id.bt_hide_text);
        lyt_expand_text = v.findViewById(R.id.lyt_expand_text);

        bt_toggle_input = v.findViewById(R.id.bt_toggle_input);
        bt_hide_input = v.findViewById(R.id.bt_hide_input);
        bt_save_input = v.findViewById(R.id.bt_save_input);
        lyt_expand_input = v.findViewById(R.id.lyt_expand_input);
        lyt_expand_input.setVisibility(View.GONE);

        // nested scrollview
        nested_scroll_view = v.findViewById(R.id.nested_scroll_view);

        bt_toggle_text.setOnClickListener(this);
        bt_hide_text.setOnClickListener(this);
        bt_toggle_input.setOnClickListener(this);
        bt_hide_input.setOnClickListener(this);
        bt_save_input.setOnClickListener(this);

        return v;

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


    private void toggleSectionInput(View view) {
        boolean show = toggleArrow(view);
        if (show) {
            ViewAnimation.expand(lyt_expand_input, new ViewAnimation.AnimListener() {
                @Override
                public void onFinish() {
                    Tools.nestedScrollTo(nested_scroll_view, lyt_expand_input);
                }
            });
        } else {
            ViewAnimation.collapse(lyt_expand_input);
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
        switch (v.getId()){
            case R.id.bt_toggle_text:
                toggleSectionText(bt_toggle_text);
                break;
            case R.id.bt_hide_text:
                toggleSectionText(bt_toggle_text);
                break;
            case R.id.bt_toggle_input:
                toggleSectionInput(bt_toggle_input);
                break;
            case R.id.bt_hide_input:
                toggleSectionInput(bt_toggle_input);
                break;
            case R.id.bt_save_input:
                Snackbar.make(getView(), "Data saved to server", Snackbar.LENGTH_SHORT).show();
                toggleSectionInput(bt_toggle_input);

                break;
        }
    }
}