package com.example.sambulo.administrative;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Mokgako on 2017/10/22.
 */

public class MaintenanceHolder implements View.OnLongClickListener {
    TextView Title, status, reporter, report, dateCreated, dateEdited;
    ImageView yesNo;
    ItemClickListener itemClickListener;

    public MaintenanceHolder(View v) {

        Title = (TextView) v.findViewById(R.id.RptTit);
        status = (TextView) v.findViewById(R.id.RpSolved);
        reporter = (TextView) v.findViewById(R.id.reporter);
        report = (TextView) v.findViewById(R.id.reporting);
        dateCreated = (TextView) v.findViewById(R.id.created);
        dateEdited = (TextView) v.findViewById(R.id.updated);
        yesNo = (ImageView) v.findViewById(R.id.yes_or_no);
        v.setOnLongClickListener(this);

    }

    public void setItemLongClickListner(ItemClickListener ic) {
        this.itemClickListener = ic;
    }

    @Override
    public boolean onLongClick(View v) {
        this.itemClickListener.onLongItemClick(v);
        return false;
    }
}
