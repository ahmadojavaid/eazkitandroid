package com.example.jbalpha.eazkitv8.Models;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.jbalpha.eazkitv8.AllSessions;
import com.example.jbalpha.eazkitv8.AnalyticsActivity;
import com.example.jbalpha.eazkitv8.BroadCastReceiverMine.AlarmReceiver;
import com.example.jbalpha.eazkitv8.SettingActivity;
import com.example.jbalpha.eazkitv8.EventModels.MenuModel;
import com.example.jbalpha.eazkitv8.R;
import com.example.jbalpha.eazkitv8.SignIn;
import com.example.jbalpha.eazkitv8.SignUp;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import de.hdodenhof.circleimageview.CircleImageView;

public class MenuFragment extends Fragment implements View.OnTouchListener {

    GestureDetector gestureDetector;
    boolean isFragmentLoaded;
    private Fragment menuFragment;
    private RelativeLayout logOutContainer;
    private TextView sideMenuSettings;
    private TextView sideMenuUsername;
    private CircleImageView sideMenuImage;
    private String profileImage;
    private ImageButton close;
    private RelativeLayout root;
    private TextView sideMenuAnalytics;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.side_menu, container, false);
        root = (RelativeLayout) rootView.findViewById(R.id.root);
        close = (ImageButton) rootView.findViewById(R.id.closeBtn);
        sideMenuImage = (CircleImageView) rootView.findViewById(R.id.sideMenuImage);
        sideMenuUsername = (TextView) rootView.findViewById(R.id.sideMenuUsername);
        TextView sideMenuSessions = (TextView) rootView.findViewById(R.id.sideMenuSessions);


        String userName = SharedPreManager.getInstance(getActivity()).getUsername();


        sideMenuUsername.setText(userName + "");

        String userImage = Utils.BASE_URL_IMAGE + SharedPreManager.getInstance(getActivity().getApplicationContext()).getProfileImage();

        Picasso.with(getActivity().getApplicationContext()).load(userImage).into(sideMenuImage);
        sideMenuSettings = (TextView) rootView.findViewById(R.id.sideMenuSettings);
        sideMenuSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SettingActivity.class);
                startActivity(intent);
            }
        });

        logOutContainer = (RelativeLayout) rootView.findViewById(R.id.logOutContainer);
        logOutContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                try {

                    AlarmManager AlarmsInWeekAlarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
                    Intent alarmIntent = new Intent(getActivity(), AlarmReceiver.class);
                    PendingIntent pendingIntent = PendingIntent.getActivity(getActivity(), 0, alarmIntent, PendingIntent.FLAG_CANCEL_CURRENT);
                    AlarmsInWeekAlarmManager.cancel(pendingIntent);

                } catch (Exception e) {
                    e.printStackTrace();
                }


                SharedPreManager.getInstance(getActivity()).isLoggedOut();
                UserProfileSharedPreferences.getInstance(getActivity()).isLoggedOut();
                Intent intent = new Intent(getActivity(), SignIn.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

                getActivity().getSharedPreferences("mysharedpref12", Context.MODE_PRIVATE).edit().clear().commit();
                getActivity().getSharedPreferences("currentUserProfile", Context.MODE_PRIVATE).edit().clear().commit();


            }
        });
        sideMenuSessions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AllSessions.class);
                startActivity(intent);
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideFragment();
            }
        });

        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        sideMenuAnalytics = rootView.findViewById(R.id.sideMenuAnalytics);
        sideMenuAnalytics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent analyticIntent = new Intent(getActivity(), AnalyticsActivity.class);
                getActivity().startActivity(analyticIntent);


            }
        });

        return rootView;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return true;
    }


//    public void loadFragment() {
//        FragmentManager fm = getFragmentManager();
//        menuFragment = fm.findFragmentById(R.id.letStartWhiteningContainer);
//        // menu.setImageResource(R.drawable.ic_arrow_upward_black_24dp);
//        if (menuFragment == null) {
//            Toast.makeText(getActivity(), "IF", Toast.LENGTH_LONG).show();
//            menuFragment = new MenuFragment();
//            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//            fragmentTransaction.setCustomAnimations(R.anim.slide_down, R.anim.slide_up);
//            fragmentTransaction.add(R.id.letStartWhiteningContainer, menuFragment);
//            fragmentTransaction.commit();
//        }
//
//        isFragmentLoaded = true;
//    }

    public void hideFragment() {


        EventBus.getDefault().post(new MenuModel());

//        getFragmentManager().popBackStack();
//
//
//        MenuFragment menuFragment = new MenuFragment();
//        getFragmentManager()
//                .beginTransaction()
//                .setCustomAnimations(R.anim.slide_down, R.anim.slide_up)
//
//                .remove(menuFragment)
//                .commit();


//        Intent intent=new Intent(getActivity(),LetsStartWhitening.class);
//        Bundle bundle=ActivityOptions.makeCustomAnimation(getActivity(),R.anim.slide_down,R.anim.slide_up).toBundle();
//        getActivity().startActivity(intent,bundle);
//        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//        fragmentTransaction.setCustomAnimations(R.anim.slide_down, R.anim.slide_up);
//        fragmentTransaction.remove(menuFragment);
//        fragmentTransaction.commit();
//        Toast.makeText(getActivity(),"Hide Frag Menu Frag",Toast.LENGTH_LONG).show();
//        //menu.setImageResource(R.drawable.ic_menu_black_24dp);
//        isFragmentLoaded = false;
    }


}