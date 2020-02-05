package com.example.sambulo.administrative;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Mokgako on 2017/10/15.
 */

public class LearnerAdapter extends ArrayAdapter<Learner> {
    private final Context context;
    private final List<Learner> learnerList;


    public LearnerAdapter(@NonNull Context context, List<Learner> resource) {
        super(context, R.layout.backendless_spinner_layout, resource);
        this.context = context;
        this.learnerList = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.backendless_spinner_layout, parent, false);

        TextView txtLearner = (TextView) rowView.findViewById(R.id.txtLearner_user);
        txtLearner.setText(" " + learnerList.get(position).getLearnerName());


        return rowView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getView(position, convertView, parent);
    }
}
