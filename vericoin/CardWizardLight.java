package com.example.vericoin;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.vericoin.utils.Tools;

public class CardWizardLight extends AppCompatActivity {

    private static final int MAX_STEP = 4;

    private ViewPager viewPager;
    private MyViewPagerAdapter myViewPagerAdapter;
    private String about_title_array[] = {
            "Earning made Easier",
            "Easy and Fast Withdrawal",
            "Zero Limits",
            "Postpaid Verizon"
    };
    private String about_description_array[] = {
            "Earn crypto while you make calls, receive text, and browse the internet" +
                    " with Verizon network provider",
            "Payouts are done via Bitcoin after placing withdrawal requests. Etherum, Stellar XLM, Litecoin and Dogecoin coming soon",
            "No limits to how much you earn. Consistency is key!",
            "Link your Verizon account on the registration section to verify you use the network provider. This offer is currently only available to Postpaid Verizon users but coming soon to non-postpaid users, Sprint mobile, and AT&T",
    };
    private int about_images_array[] = {
            R.drawable.btc_icon,
            R.drawable.img_wizard_2,
            R.drawable.infinity_loop,
            R.drawable.verizon_intro_img
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Animatoo.animateZoom(CardWizardLight.this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_wizard_light);

        viewPager = (ViewPager) findViewById(R.id.view_pager);

        // adding bottom dots
        bottomProgressDots(0);

        myViewPagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);

        Tools.setSystemBarColor(this, R.color.overlay_light_80);
        Tools.setSystemBarLight(this);
    }


    private void bottomProgressDots(int current_index) {
        LinearLayout dotsLayout = (LinearLayout) findViewById(R.id.layoutDots);
        ImageView[] dots = new ImageView[MAX_STEP];

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new ImageView(this);
            int width_height = 15;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(width_height, width_height));
            params.setMargins(10, 10, 10, 10);
            dots[i].setLayoutParams(params);
            dots[i].setImageResource(R.drawable.shape_circle);
            dots[i].setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0) {
            dots[current_index].setImageResource(R.drawable.shape_circle);
            dots[current_index].setColorFilter(getResources().getColor(R.color.light_green_600), PorterDuff.Mode.SRC_IN);
        }
    }

    //  viewpager change listener
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(final int position) {
            bottomProgressDots(position);
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };

    /**
     * View pager adapter
     */
    public class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;
        private Button btnNext;

        public MyViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(R.layout.item_card_wizard_light, container, false);
            ((TextView) view.findViewById(R.id.title)).setText(about_title_array[position]);
            ((TextView) view.findViewById(R.id.description)).setText(about_description_array[position]);
            ((ImageView) view.findViewById(R.id.image)).setImageResource(about_images_array[position]);

            btnNext = (Button) view.findViewById(R.id.btn_next);

            if (position == about_title_array.length - 1) {
                btnNext.setText("Get Started");
            } else {
                btnNext.setText("Next");
            }


            btnNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int current = viewPager.getCurrentItem() + 1;
                    if (current < MAX_STEP) {
                        // move to next screen
                        viewPager.setCurrentItem(current);
                    } else {
                    // navigate to login/signup screen
                        openSignInActivity();
                        Animatoo.animateZoom(CardWizardLight.this);
                    }
                }
            });




            container.addView(view);
            return view;
        }

        public void fadeIn(View view){
            AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
            anim.setDuration(1000);
            view.startAnimation(anim);
            view.setVisibility(View.VISIBLE);
        }

        public void openSignInActivity(){
            Intent intent = new Intent(CardWizardLight.this, signInActivity.class);
            startActivity(intent);
        }

        @Override
        public int getCount() {
            return about_title_array.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }
}