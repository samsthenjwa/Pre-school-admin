package com.example.sambulo.administrative;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.animation.ValueAnimatorCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Mokgako on 2017/09/30.
 */

public class CustomShopAdapter extends ArrayAdapter<Learner> {
    private final Context context;
    private final List<Learner> values;

    public CustomShopAdapter(Context context, List<Learner> list) {
        super(context, R.layout.custom_shop, list);
        this.context = context;
        this.values = list;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.custom_shop, parent, false);

        TextView tvnameAndsurname = (TextView) rowView.findViewById(R.id.tvnameAndsurname);
        TextView tvClasseee = (TextView) rowView.findViewById(R.id.tvClasseee);
        TextView tv = (TextView) rowView.findViewById(R.id.tv);
        ImageView imageView4 = (ImageView) rowView.findViewById(R.id.imageView4);


        tvnameAndsurname.setText(values.get(position).getLearnerName() + " " + values.get(position).getLearnerSurname());
        tvClasseee.setText(values.get(position).getClassName());
        tv.setText("Balance: \n" + values.get(position).getTuckBalance());
        if (values.get(position).getTuckBalance() <= 0.0) {
            tv.setTextColor(Color.parseColor("#FF0000"));

        } else {
            tv.setTextColor(Color.parseColor("#00FF66"));
            imageView4.setImageResource(R.mipmap.correct);
        }


        return rowView;
    }
}
