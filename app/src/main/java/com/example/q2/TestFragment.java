package com.example.q2;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class TestFragment extends Fragment {

    public SeekBar mSeekBarPitch;
    private SeekBar mSeekBarSpeed;

    public static TestFragment newInstance(String str){

        //Make an instance
        TestFragment fragment = new TestFragment();
        //Setting Para to BUndle
        Bundle barg = new Bundle();
        barg.putString("Message", str);
        fragment.setArguments(barg);

        return fragment;
    }

    //Make Fragment and back it
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle args = getArguments();

        //One Back by BackStack
        Button pop01 = view.findViewById(R.id.button2);
        pop01.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                FragmentManager fragmentManager = getFragmentManager();
                if(fragmentManager != null) {
                    

                    fragmentManager.popBackStack();
                }
            }
        });



        /*
        //////////////////////////////////////////////////////////////////////////////////////////
        mSeekBarPitch = view.findViewById(R.id.seek_bar_pitch);
        mSeekBarSpeed = view.findViewById(R.id.seek_bar_speed);

        //////////////////////////////////////////////////////////////////////////////////////////
        float pitch = (float) mSeekBarPitch.getProgress() / 50;
        if (pitch < 0.1) pitch = 0.1f;
        float PITCH_LOG = pitch;
        float speed = (float) mSeekBarSpeed.getProgress() / 50;
        if (speed < 0.1) speed = 0.1f;
        float SPEED_LOG = speed;
        SharedPreferences data_pitch = this.getActivity().getSharedPreferences("pref_pitch", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor_pitch = data_pitch.edit();
        editor_pitch.putFloat("DataFloat1", PITCH_LOG);
        editor_pitch.apply();

        SharedPreferences data_speed = this.getActivity().getSharedPreferences("pref_speed", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor_speed = data_speed.edit();
        editor_speed.putFloat("DataFloat2", SPEED_LOG);
        editor_speed.apply();

        //////////////////////////////////////////////////////////////////////////////////////////

         */



    }
}