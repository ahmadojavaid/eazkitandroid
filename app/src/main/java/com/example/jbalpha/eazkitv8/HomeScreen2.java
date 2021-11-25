package com.example.jbalpha.eazkitv8;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class HomeScreen2 extends AppCompatActivity implements GestureDetector.OnGestureListener {

    //circular reveal variables
    public static final String EXTRA_CIRCULAR_REVEAL_X = "EXTRA_CIRCULAR_REVEAL_X";
    public static final String EXTRA_CIRCULAR_REVEAL_Y = "EXTRA_CIRCULAR_REVEAL_Y";
    View rootLayout;
    private int revealX;
    private int revealY;

    public static final int SWIPE_THRESHOLD = 100;
    public static final int SWIPE_VELOCITY_THRESHOLD = 100;
    private GestureDetector gestureDetector;
    ImageView toothFairyImage;
    AnimationDrawable toothFairyAnimation;
    TextView homeScreen2_Subtitle, homeScreen2_Label, homeScreen2_SwipeUp, homeScreen2_Number, someScreen2Eazkit;
    Typeface firaSansMedium, firaSansBold, firaSansRegular, firaSansSemiBold, absoluteProReducedBlack;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen2);

        Window window = getWindow();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.setStatusBarColor(getColor(R.color.green));
        } else {
            window.setStatusBarColor(getResources().getColor(R.color.green));
        }
        //circular reveal code
      /*  final Intent intent = getIntent();
        rootLayout = findViewById(R.id.home_screen2);

        if (savedInstanceState == null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP &&
                intent.hasExtra(EXTRA_CIRCULAR_REVEAL_X) &&
                intent.hasExtra(EXTRA_CIRCULAR_REVEAL_Y)) {
            rootLayout.setVisibility(View.INVISIBLE);

            revealX = intent.getIntExtra(EXTRA_CIRCULAR_REVEAL_X, 0);
            revealY = intent.getIntExtra(EXTRA_CIRCULAR_REVEAL_Y, 0);


            ViewTreeObserver viewTreeObserver = rootLayout.getViewTreeObserver();
            if (viewTreeObserver.isAlive()) {
                viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        revealActivity(revealX, revealY);
                        rootLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                });
            }
        } else {
            rootLayout.setVisibility(View.VISIBLE);
        } */


        //circular reveal code

        //initializing typefaces here
        firaSansMedium = Typeface.createFromAsset(getAssets(), "fonts/FiraSans_Medium.ttf");
        firaSansBold = Typeface.createFromAsset(getAssets(), "fonts/FiraSans_Bold.ttf");
        firaSansRegular = Typeface.createFromAsset(getAssets(), "fonts/FiraSans_Regular.ttf");
        firaSansSemiBold = Typeface.createFromAsset(getAssets(), "fonts/FiraSans_SemiBold.ttf");
        absoluteProReducedBlack = Typeface.createFromAsset(getAssets(), "fonts/absolut_pro_black_reduced.ttf");

        //applying typeface on textViews
        homeScreen2_Number = (TextView) findViewById(R.id.homeScreen2_Number);
        homeScreen2_Number.setTypeface(firaSansMedium);

        homeScreen2_Subtitle = (TextView) findViewById(R.id.homeScreen2_Subtitle);
        homeScreen2_Subtitle.setTypeface(firaSansBold);

        homeScreen2_Label = (TextView) findViewById(R.id.homeScreen2_Label);
        homeScreen2_Label.setTypeface(firaSansRegular);

        homeScreen2_SwipeUp = (TextView) findViewById(R.id.homeScreen2_SwipeUp);
        homeScreen2_SwipeUp.setTypeface(firaSansSemiBold);

        someScreen2Eazkit = (TextView) findViewById(R.id.someScreen2Eazkit);
        someScreen2Eazkit.setTypeface(absoluteProReducedBlack);

        //adding animation to tooth image
        toothFairyImage = (ImageView) findViewById(R.id.toothFairyImage);
        toothFairyImage.setBackgroundResource(R.drawable.toothfairy_animation);

        toothFairyAnimation = (AnimationDrawable) toothFairyImage.getBackground();
        toothFairyAnimation.start();

        gestureDetector = new GestureDetector(this);
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
        Intent intent = new Intent(HomeScreen2.this, HomeScreen3.class);
        ActivityOptions options = ActivityOptions.makeCustomAnimation(HomeScreen2.this, R.anim.intro_slide_up, R.anim.no_animation);


        startActivity(intent,options.toBundle());

        //Toast.makeText(this,"SwipeTop",Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }


    //applying transition on this ACTIVITY
    protected void revealActivity(int x, int y) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            float finalRadius = (float) (Math.max(rootLayout.getWidth(), rootLayout.getHeight()) * 1.1);

            // create the animator for this view (the start radius is zero)
            Animator circularReveal = ViewAnimationUtils.createCircularReveal(rootLayout, x, y, 0, finalRadius);
            circularReveal.setDuration(800);
            circularReveal.setInterpolator(new AccelerateInterpolator());

            // make the view visible and start the animation
            rootLayout.setVisibility(View.VISIBLE);
            circularReveal.start();
        } else {
            finish();
        }
    }

    protected void unRevealActivity() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            finish();
        } else {
            float finalRadius = (float) (Math.max(rootLayout.getWidth(), rootLayout.getHeight()) * 1.1);
            Animator circularReveal = ViewAnimationUtils.createCircularReveal(
                    rootLayout, revealX, revealY, finalRadius, 0);

            circularReveal.setDuration(400);
            circularReveal.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    rootLayout.setVisibility(View.INVISIBLE);
                    finish();
                }
            });


            circularReveal.start();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();


    }



}
