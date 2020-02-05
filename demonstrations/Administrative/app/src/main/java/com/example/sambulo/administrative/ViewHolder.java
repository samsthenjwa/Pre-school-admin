package com.example.sambulo.administrative;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Mokgako on 2017/10/13.
 */

public class ViewHolder implements View.OnLongClickListener {

    TextView tvnameAndsurname;
    TextView tvClasseee;
    TextView tv;
    ImageView imageView4;
    ItemClickListener itemClickListener;


    public ViewHolder(View v) {

        tvnameAndsurname = (TextView) v.findViewById(R.id.tvnameAndsurname);
        tvClasseee = (TextView) v.findViewById(R.id.tvClasseee);
        tv = (TextView) v.findViewById(R.id.tv);
        imageView4 = (ImageView) v.findViewById(R.id.imageView4);
        v.setOnLongClickListener(this);
    }


    public void setItemClickListner(ItemClickListener itemClickListner) {

        this.itemClickListener = itemClickListner;

    }

    @Override
    public boolean onLongClick(View v) {
        this.itemClickListener.onLongItemClick(v);

        return false;
    }
}
