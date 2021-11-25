package com.example.jbalpha.eazkitv8.FragmentServices;

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

import com.example.jbalpha.eazkitv8.SettingActivity;
import com.example.jbalpha.eazkitv8.Models.SharedPreManager;
import com.example.jbalpha.eazkitv8.Models.Utils;
import com.example.jbalpha.eazkitv8.R;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class WhiteningSessionSideMenu extends Fragment implements View.OnTouchListener {

    GestureDetector gestureDetector;
    boolean isFragmentLoaded;
    Fragment menuFragment;
    RelativeLayout logOutContainer;
    TextView sideMenuSettings;
    TextView sideMenuUsername;
    CircleImageView sideMenuImage;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.side_menu, container, false);
        RelativeLayout root = (RelativeLayout) rootView.findViewById(R.id.root);
        ImageButton close = (ImageButton) rootView.findViewById(R.id.closeBtn);
        TextView sideMenuSessions=(TextView)rootView.findViewById(R.id.sideMenuSessions);
        sideMenuImage=(CircleImageView)rootView.findViewById(R.id.sideMenuImage);
        sideMenuUsername=(TextView)rootView.findViewById(R.id.sideMenuUsername);
        sideMenuUsername.setText(SharedPreManager.getInstance(getActivity()).getUsername());
        Picasso.with(getActivity()).load(Utils.BASE_URL_IMAGE+SharedPreManager.getInstance(getActivity())).into(sideMenuImage);
        sideMenuSettings=(TextView)rootView.findViewById(R.id.sideMenuSettings);
        sideMenuSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),SettingActivity.class);
                startActivity(intent);
            }
        });
//        sideMenuSessions.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(getActivity(),AllSessions.class);
//                startActivity(intent);
//            }
//        });
//
//        logOutContainer=(RelativeLayout)rootView.findViewById(R.id.logOutContainer);
//        logOutContainer.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                SharedPreManager.getInstance(getActivity()).isLoggedOut();
//                UserProfileSharedPreferences.getInstance(getActivity()).isLoggedOut();
//                Intent intent=new Intent(getActivity(),SignUp.class);
//                startActivity(intent);
//            }
//        });
//
//        close.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                hideFragment();
//            }
//        });

        return root;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return true;
    }


//    public void loadFragment(){
//        FragmentManager fm = getFragmentManager();
//        menuFragment = fm.findFragmentById(R.id.whiteningContentContainer);
//        // menu.setImageResource(R.drawable.ic_arrow_upward_black_24dp);
//        if(menuFragment == null){
//            Toast.makeText(getActivity(),"IF",Toast.LENGTH_LONG).show();
//            menuFragment = new MenuFragment();
//            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//            fragmentTransaction.setCustomAnimations(R.anim.slide_down, R.anim.slide_up);
//            fragmentTransaction.add(R.id.whiteningContentContainer,menuFragment);
//            fragmentTransaction.commit();
//        }
//
//        isFragmentLoaded = true;
//    }
//
//    public void hideFragment(){
//        Intent intent=new Intent(getActivity(),AllSessions.class);
//        Bundle bundle=ActivityOptions.makeCustomAnimation(getActivity(),R.anim.slide_down,R.anim.slide_up).toBundle();
//        getActivity().startActivity(intent,bundle);
//       /* FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//        fragmentTransaction.setCustomAnimations(R.anim.slide_down, R.anim.slide_up);
//        fragmentTransaction.remove(menuFragment);
//        fragmentTransaction.commit();
//        Toast.makeText(getActivity(),"Hide Frag Menu Frag",Toast.LENGTH_LONG).show();
//        //menu.setImageResource(R.drawable.ic_menu_black_24dp);
//        isFragmentLoaded = false;*/
//    }

}

