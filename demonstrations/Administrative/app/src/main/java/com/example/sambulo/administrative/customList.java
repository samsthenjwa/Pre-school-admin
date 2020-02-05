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

/**
 * Created by SAMBULO on 01-Oct-17.
 */

public class customList extends ArrayAdapter<Learner> {
    static Context context;
    private final List<Learner> values;

    public customList(Context context, List<Learner> list) {
        super(context, R.layout.custom_layout, list);
        this.context = context;
        this.values = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.custom_layout, parent, false);

        TextView Namesurname = (TextView) row.findViewById(R.id.Namesurname);
        TextView identity = (TextView) row.findViewById(R.id.Identity);
        TextView gender = (TextView) row.findViewById(R.id.gender);

        Namesurname.setText("Name : " + values.get(position).getLearnerName() + "  Surname : " + values.get(position).getLearnerSurname());
        identity.setText("ID : " + values.get(position).getLearnerId());
        gender.setText("Gender : " + values.get(position).getGender());

        return row;
    }
}

