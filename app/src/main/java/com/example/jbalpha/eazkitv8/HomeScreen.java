package com.example.jbalpha.eazkitv8;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jbalpha.eazkitv8.Models.SharedPreManager;

public class HomeScreen extends AppCompatActivity implements GestureDetector.OnGestureListener {

    private TextView eazkitHeading1, tv_helloHuman, description1, tv_SwipeUp;
    private Typeface tf1, tf2, tf3, tf4;
    private ImageView imageView;

    public static final int SWIPE_THRESHOLD = 100;
    public static final int SWIPE_VELOCITY_THRESHOLD = 100;
    AnimationDrawable blinkAnimation;
    private GestureDetector gestureDetector;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.setStatusBarColor(getColor(R.color.blue));
        } else {
            window.setStatusBarColor(getResources().getColor(R.color.blue));
        }

        if (SharedPreManager.getInstance(this).isLoggedIn()) {
            finish();

            SharedPreferences sharedpreferences = getSharedPreferences("currentUserProfile", Context.MODE_PRIVATE);
            String age = sharedpreferences.getString("ageSet", "null");
            String prefsValue = sharedpreferences.getString("PREF_USER", "0");

            if ( age.equalsIgnoreCase("0")) {

                Intent intent = new Intent(HomeScreen.this, CreateProfile1.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                return;
            } else if (age.equalsIgnoreCase("1")) {
                if (prefsValue.equals("1")) {

                    Intent intent = new Intent(HomeScreen.this, AllSessions.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    return;
                } else if (prefsValue.equals("0")) {
                    Intent intent = new Intent(HomeScreen.this, CreateProfile2.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    return;
                }

            }

        }

        gestureDetector = new GestureDetector(this);

        eazkitHeading1 = (TextView) findViewById(R.id.eazkitHeading1);
        tv_helloHuman = (TextView) findViewById(R.id.tv_helloHuman);
        description1 = (TextView) findViewById(R.id.description1);
        tv_SwipeUp = (TextView) findViewById(R.id.tv_SwipeUp);
        imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setBackgroundResource(R.drawable.teeth_blink_animation);

        blinkAnimation = (AnimationDrawable) imageView.getBackground();

        tf1 = Typeface.createFromAsset(getAssets(), "fonts/absolut_pro_black_reduced.ttf");
        tf2 = Typeface.createFromAsset(getAssets(), "fonts/FiraSans_Bold.ttf");
        tf3 = Typeface.createFromAsset(getAssets(), "fonts/FiraSans_Regular.ttf");
        tf4 = Typeface.createFromAsset(getAssets(), "fonts/FiraSans_SemiBold.ttf");
        eazkitHeading1.setTypeface(tf1);
        tv_helloHuman.setTypeface(tf2);
        description1.setTypeface(tf2);
        tv_SwipeUp.setTypeface(tf4);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        blinkAnimation.start();
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent downEvent, MotionEvent moveEvent, float velocityX, float velocityY) {
        boolean result;
        result = false;

        float diffY = moveEvent.getY() - downEvent.getY();
        float diffX = moveEvent.getX() - downEvent.getX();
        //which was greater
        if (Math.abs(diffX) > Math.abs(diffY)) {
            //left or right swipe
            if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                if (diffX > 0) {
                    onSwipeRight();
                } else {
                    onSwipeLeftt();
                }
                return true;
            }
        } else {
            //up or down swipe
            if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                if (diffY > 0) {
                    onSwipeBottom();
                } else {
                    onSwipeTop();
                }
                return true;
            }
        }

        return result;
    }

    private void onSwipeBottom() {
//        Toast.makeText(this, "SwipeBottom", Toast.LENGTH_LONG).show();
    }

    private void onSwipeRight() {
//        Toast.makeText(this, "SwipeRight", Toast.LENGTH_LONG).show();
    }

    private void onSwipeLeftt() {
//        Toast.makeText(this, "SwipeLeftt", Toast.LENGTH_LONG).show();
    }

    private void onSwipeTop() {
        Intent intent = new Intent(HomeScreen.this, HomeScreen2.class);
        ActivityOptions options = ActivityOptions.makeCustomAnimation(HomeScreen.this, R.anim.intro_slide_up, R.anim.no_animation);


        startActivity(intent, options.toBundle());
//
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    public void presentActivity(View view) {
        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(this, view, "transition");
        int revealX = (int) (view.getX() + view.getWidth() / 2);
        int revealY = (int) (view.getY() + view.getHeight() / 2);

        Intent intent = new Intent(this, HomeScreen2.class);
        intent.putExtra(HomeScreen2.EXTRA_CIRCULAR_REVEAL_X, revealX);
        intent.putExtra(HomeScreen2.EXTRA_CIRCULAR_REVEAL_Y, revealY);

        ActivityCompat.startActivity(this, intent, options.toBundle());
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

}
