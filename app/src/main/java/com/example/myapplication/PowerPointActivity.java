package com.example.myapplication;

import androidx.viewpager2.widget.ViewPager2;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import timber.log.Timber;

public class PowerPointActivity extends Activity {
    Powerpoint mSelectedPowerpoint;
    private ViewPager2 viewPager2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_pager_main);
        Intent intent = getIntent();
        mSelectedPowerpoint = (Powerpoint) intent.getSerializableExtra("powerpoint");
        TextView mTextTitle = findViewById(R.id.fragment_pager_main_title);
        mTextTitle.setText(mSelectedPowerpoint.getTitle());
        TextView mTextDescription = findViewById(R.id.fragment_pager_main_about);
        mTextDescription.setText(mSelectedPowerpoint.getDescription());
        LinearLayout mLayout = findViewById(R.id.fragment_pager_main_child);
        mLayout.setBackgroundResource(R.color.Transparent_black);
        viewPager2 = findViewById(R.id.fragment_pager_viewPager2);
        setUpPagerAdapter();

    }
    /**
     * this method sets up the adapter
     */
    private void setUpPagerAdapter() {
        List<String> imagesUrlsList = Arrays.asList(mSelectedPowerpoint.getImagesURLs());
        MyPowerpointAdapter pagerAdapter = new MyPowerpointAdapter(imagesUrlsList);
        viewPager2.setAdapter(pagerAdapter);
        viewPager2.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
    }
    /**
     * this method handles slideshow navigation
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_LEFT: {
                viewPager2.setCurrentItem(viewPager2.getCurrentItem() - 1);
                break;
            }
            case KeyEvent.KEYCODE_DPAD_RIGHT: {

                viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
                break;
            }
            case KeyEvent.KEYCODE_BACK: {
                super.onBackPressed();
                break;
            }
            case KeyEvent.KEYCODE_ENTER: {
                if (findViewById(R.id.fragment_pager_main_child).isShown()) {
                    findViewById(R.id.fragment_pager_main_child).setVisibility(View.INVISIBLE);
                    LinearLayout mLayout = findViewById(R.id.fragment_pager_main_child);
                    Animation outFade = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_out);
                    mLayout.startAnimation(outFade);
                    mLayout.startAnimation(outFade);
                    break;
                } else {
                    LinearLayout mLayout = findViewById(R.id.fragment_pager_main_child);
                    Animation aniFade = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in);
                    mLayout.startAnimation(aniFade);
                    mLayout.startAnimation(aniFade);
                    findViewById(R.id.fragment_pager_main_child).setVisibility(View.VISIBLE);
                    break;
                }
            }
        }
        return false;
    }
}


