package com.example.sambulo.administrative;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mokgako on 2017/10/04.
 */

public class CustomAdminShopAdapter extends BaseAdapter implements Filterable {

    Context context;
    List<Learner> learner;
    CustomFilter customFilter;
    List<Learner> filteredLearners;
    double currentLoaded, newBalance;


    public CustomAdminShopAdapter(Context con, List<Learner> learner) {
        this.context = con;
        this.learner = learner;
        this.filteredLearners = learner;
    }

    @Override
    public int getCount() {
        return learner.size();
    }

    @Override
    public Object getItem(int position) {
        return learner.get(position);
    }

    @Override
    public long getItemId(int position) {
        return learner.indexOf(getItem(position));
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.custom_shop, parent, false);
        }
        ViewHolder holder = new ViewHolder(convertView);

        holder.tvnameAndsurname.setText(learner.get(position).getLearnerName() + " " + learner.get(position).getLearnerSurname());
        holder.tvClasseee.setText(learner.get(position).getClassName());
        holder.tv.setText("Balance: \n" + learner.get(position).getTuckBalance());

        if (learner.get(position).getTuckBalance() <= 0.0) {
            holder.tv.setTextColor(Color.parseColor("#FF0000"));
            holder.imageView4.setImageResource(R.mipmap.bad);

        } else {
            holder.tv.setTextColor(Color.parseColor("#00FF66"));
            holder.imageView4.setImageResource(R.mipmap.correct);
        }
        holder.setItemClickListner(new ItemClickListener() {
            @Override
            public void onLongItemClick(View v) {
                actionDialogs(learner.get(position).getObjectId(), learner.get(position).getTuckBalance());
            }
        });


        return convertView;
    }


    @Override
    public Filter getFilter() {

        if (customFilter == null) {
            customFilter = new CustomFilter(filteredLearners, this);
        }
        return customFilter;
    }

    public void actionDialogs(final String number, final double outdated) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View promptsView = inflater.inflate(R.layout.prompt, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setView(promptsView);
        final EditText userInput = (EditText) promptsView.findViewById(R.id.editTextDialogUserInput);
        alertDialogBuilder.setCancelable(false)

                .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (userInput.getText().toString().isEmpty()) {

                            currentLoaded = 0.0;
                        } else {
                            currentLoaded = Double.parseDouble(userInput.getText().toString());
                            newBalance = currentLoaded + outdated;
                            longClicked(number, newBalance);
                        }

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();

                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();

        alertDialog.show();

    }

    public void longClicked(String number, final double current) {

        Backendless.Persistence.of(Learner.class).findById(number, new AsyncCallback<Learner>() {
            @Override
            public void handleResponse(Learner learner) {
                learner.setTuckBalance(current);

                Backendless.Persistence.save(learner, new AsyncCallback<Learner>() {
                    @Override
                    public void handleResponse(Learner learner) {
                        Toast.makeText(context, "Successfully updated", Toast.LENGTH_SHORT).show();
                        //LoadData();
                    }

                    @Override
                    public void handleFault(BackendlessFault backendlessFault) {
                        Toast.makeText(context, backendlessFault.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
            }

            @Override
            public void handleFault(BackendlessFault backendlessFault) {
                Toast.makeText(context, backendlessFault.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }


}
