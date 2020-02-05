package com.example.sambulo.administrative;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;


public class custom_student_list extends ArrayAdapter<STUDENTS_REPORT> {
    static Context context;
    private final List<STUDENTS_REPORT> values;

    public custom_student_list(Context context, List<STUDENTS_REPORT> list) {
        super(context, R.layout.custom_student_list, list);
        this.context = context;
        this.values = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View Viewrow, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.custom_student_list, parent, false);

        TextView classes = (TextView) row.findViewById(R.id.tv1);
        TextView name = (TextView) row.findViewById(R.id.tv2);
        TextView recomendation = (TextView) row.findViewById(R.id.tv3);
        TextView report = (TextView) row.findViewById(R.id.tv4);
        TextView tit = (TextView) row.findViewById(R.id.tv5);


        classes.setText("Class Name" + values.get(position).getClassName());
        name.setText("Learner Name " + values.get(position).getNames());
        recomendation.setText("Recomendation " + values.get(position).getRecomendation());
        tit.setText("Report Title " + values.get(position).getTitle());
        report.setText("Report " + values.get(position).getReports());

        return row;
    }

}
