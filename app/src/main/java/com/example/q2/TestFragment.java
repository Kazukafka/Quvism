package com.example.q2;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class TestFragment extends Fragment {

    public static TestFragment newInstance(String str){

        TestFragment fragment = new TestFragment();

        Bundle barg = new Bundle();
        barg.putString("Message", str);
        fragment.setArguments(barg);

        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_main,
                container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle args = getArguments();
        if(args != null ){
            String str = args.getString("Message");
        }

        Button button01 = view.findViewById(R.id.button222);
        button01.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                FragmentManager fragmentManager = getFragmentManager();

                if(fragmentManager != null) {
                    fragmentManager.popBackStack();
                }
            }
        });
    }
}