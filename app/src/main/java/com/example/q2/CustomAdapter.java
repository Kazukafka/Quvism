package com.example.q2;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<String> {

    static class ViewHolder {
        TextView labelText;
    }

    private LayoutInflater inflater;

    //Constractor
    public CustomAdapter(Context context, int textViewResourceId, ArrayList<String> labelList) {
        super(context, textViewResourceId, labelList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        View view = convertView;
        //If you use the view again, do not make it again
        if (view == null) {
            inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_layout, null);
            TextView label = (TextView) view.findViewById(R.id.tv);
            holder = new ViewHolder();
            holder.labelText = label;
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        //Get the specific Row of Data
        String str = getItem(position);

        if (!TextUtils.isEmpty(str)) {
            //Set the label on the textView
            holder.labelText.setText(str);
        }
        //Change the BackGrounf Coloe
        if (position % 2 == 0) {
            holder.labelText.setBackgroundColor(Color.parseColor("#aa0000"));
        } else {
            holder.labelText.setBackgroundColor(Color.parseColor("#880000"));
        }
        //Get the Animation from XML file
        Animation anim = AnimationUtils.loadAnimation(getContext(), R.anim.item_motion);
        //Start The Anime
        view.startAnimation(anim);

        return view;
    }
}