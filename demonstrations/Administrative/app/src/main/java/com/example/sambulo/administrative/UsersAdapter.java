package com.example.sambulo.administrative;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.backendless.BackendlessUser;

import java.util.List;

/**
 * Created by Mokgako on 2017/10/15.
 */

public class UsersAdapter extends ArrayAdapter<BackendlessUser> {
    private final Context context;
    private final List<BackendlessUser> backendlessUsers;


    public UsersAdapter(Context context, List<BackendlessUser> resource) {
        super(context, R.layout.backendless_spinner_layout, resource);
        this.context = context;
        backendlessUsers = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.backendless_spinner_layout, parent, false);

        TextView txtUser = (TextView) rowView.findViewById(R.id.txtLearner_user);
        txtUser.setText(" " + backendlessUsers.get(position).getProperty("surname"));


        return rowView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getView(position, convertView, parent);
    }
}
