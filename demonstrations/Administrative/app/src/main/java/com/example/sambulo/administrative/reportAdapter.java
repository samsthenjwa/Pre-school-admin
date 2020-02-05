package com.example.sambulo.administrative;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by SAMBULO on 07-Oct-17.
 */

public class reportAdapter extends ArrayAdapter<maintenance_report> {
    private final Context context;
    private final List<maintenance_report> values;

    public reportAdapter(Context context, List<maintenance_report> list) {
        super(context, R.layout.custom_maintenance_list, list);
        this.context = context;
        this.values = list;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.custom_maintenance_list, parent, false);

        TextView title = (TextView) rowView.findViewById(R.id.RptTit);
        TextView solved = (TextView) rowView.findViewById(R.id.RpSolved);
        TextView reporter = (TextView) rowView.findViewById(R.id.reporter);
        TextView created = (TextView) rowView.findViewById(R.id.created);
        TextView updated = (TextView) rowView.findViewById(R.id.updated);
        TextView reporting = (TextView) rowView.findViewById(R.id.reporting);

        ImageView yes_or_no = (ImageView) rowView.findViewById(R.id.yes_or_no);

        title.setText("Title :" + values.get(position).getMaintitle());
        solved.setText("Issue resolved ?");


        return rowView;
    }
}
