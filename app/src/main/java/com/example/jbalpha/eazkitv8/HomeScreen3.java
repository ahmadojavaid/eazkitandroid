package com.example.jbalpha.eazkitv8;

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
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class HomeScreen3 extends AppCompatActivity implements GestureDetector.OnGestureListener {

    public static final int SWIPE_THRESHOLD = 100;
    public static final int SWIPE_VELOCITY_THRESHOLD = 100;
    private GestureDetector gestureDetector;
    ImageView smileBlinkImage;
    TextView homeScreen3_Number,homeScreen3_Subtitle,homeScreen3_Label,homeScreen3_SwipeUp,homeScreen3_Eazkit;
    Typeface firaSansMedium,firaSansBold,firaSansRegular,firaSansSemiBold,absoluteProReducedBlack;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen3);

        Window window=getWindow();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.setStatusBarColor(getColor(R.color.yellow));
        }
        else{
            window.setStatusBarColor(getResources().getColor(R.color.yellow));
        }

        //initializing typeface
        firaSansMedium=Typeface.createFromAsset(getAssets(),"fonts/FiraSans_Medium.ttf");
        firaSansBold=Typeface.createFromAsset(getAssets(),"fonts/FiraSans_Bold.ttf");
        firaSansRegular=Typeface.createFromAsset(getAssets(),"fonts/FiraSans_Regular.ttf");
        firaSansSemiBold=Typeface.createFromAsset(getAssets(),"fonts/FiraSans_SemiBold.ttf");
        absoluteProReducedBlack=Typeface.createFromAsset(getAssets(),"fonts/absolut_pro_black_reduced.ttf");

        //applying typeFace on textViews
        homeScreen3_Number=(TextView)findViewById(R.id.homeScreen3_Number);
        homeScreen3_Subtitle=(TextView)findViewById(R.id.homeScreen3_Subtitle);
        homeScreen3_Label=(TextView)findViewById(R.id.homeScreen3_Label);
        homeScreen3_SwipeUp=(TextView)findViewById(R.id.homeScreen3_SwipeUp);
        homeScreen3_Eazkit=(TextView)findViewById(R.id.homeScreen3_Eazkit);

        homeScreen3_Number.setTypeface(firaSansMedium);
        homeScreen3_Subtitle.setTypeface(firaSansBold);
        homeScreen3_Label.setTypeface(firaSansRegular);
        homeScreen3_SwipeUp.setTypeface(firaSansBold);
        homeScreen3_Eazkit.setTypeface(absoluteProReducedBlack);

        //adding tooth animation
        smileBlinkImage=(ImageView)findViewById(R.id.smileBlinkImage);
        smileBlinkImage.setBackgroundResource(R.drawable.smileblink_animation);
        AnimationDrawable animationDrawable=(AnimationDrawable)smileBlinkImage.getBackground();
        animationDrawable.start();
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
        result=false;

        float diffY= moveEvent.getY() -  downEvent.getY();
        float diffX= moveEvent.getX() -  downEvent.getX();
        //which was greater
        if(Math.abs(diffX)>Math.abs(diffY)){
            //left or right swipe
            if (Math.abs(diffX)> SWIPE_THRESHOLD && Math.abs(velocityX)> SWIPE_VELOCITY_THRESHOLD){
                if(diffX > 0){
                    onSwipeRight();
                }
                else{
                    onSwipeLeftt();
                }
                return true;
            }
        }
        else{
            //up or down swipe
            if(Math.abs(diffY) >SWIPE_THRESHOLD && Math.abs(velocityY)>SWIPE_VELOCITY_THRESHOLD){
                if(diffY>0){
                    onSwipeBottom();
                }
                else{
                    onSwipeTop();
                }
                return true;
            }
        }

        return result;
    }

    private void onSwipeBottom() {
//        Toast.makeText(this,"SwipeBottom",Toast.LENGTH_LONG).show();
    }

    private void onSwipeRight() {
//        Toast.makeText(this,"SwipeRight",Toast.LENGTH_LONG).show();
    }

    private void onSwipeLeftt(){
//        Toast.makeText(this,"SwipeLeftt",Toast.LENGTH_LONG).show();
    }

    private void onSwipeTop(){
        Intent intent=new Intent(HomeScreen3.this,HomeScreen4Aggrement.class);
        ActivityOptions options = ActivityOptions.makeCustomAnimation(HomeScreen3.this, R.anim.intro_slide_up, R.anim.no_animation);


        startActivity(intent,options.toBundle());
        //Toast.makeText(this,"SwipeTop",Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }
}
