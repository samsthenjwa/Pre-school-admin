package com.example.sambulo.administrative;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.messaging.MessageStatus;
import com.backendless.messaging.PublishOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SAMBULO on 14-Oct-17.
 */

public class custom_maitenance extends ArrayAdapter<maintenance_report> {
    static Context context;
    private final List<maintenance_report> values;
    String name, surname, title, nameSender;

    public custom_maitenance(Context context, List<maintenance_report> list) {
        super(context, R.layout.custom_maintenance_list, list);
        this.context = context;
        this.values = list;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View Viewrow, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        if (Viewrow == null) {

            Viewrow = inflater.inflate(R.layout.custom_maintenance_list, parent, false);
        }
        MaintenanceHolder holder = new MaintenanceHolder(Viewrow);


        holder.Title.setText(values.get(position).getMaintitle());
        holder.status.setText("Issue resolve : ");
        holder.reporter.setText("" + ReporterName(values.get(position).getOwnerId()));
        holder.report.setText("Report : \n" + values.get(position).getDescription());
        holder.dateCreated.setText("created :" + values.get(position).getCreated());
        holder.dateEdited.setText("updated :" + values.get(position).getUpdated());

        if (values.get(position).getStatus().equalsIgnoreCase("solved")) {
            holder.yesNo.setImageResource(R.mipmap.correct);
        } else {
            holder.yesNo.setImageResource(R.mipmap.bad);
        }

        holder.setItemLongClickListner(new ItemClickListener() {
            @Override
            public void onLongItemClick(View v) {
                if (values.get(position).getStatus().equalsIgnoreCase("NOT RESOLVED")) {
                    ActionDialog(values.get(position).getObjectId().toString(), values.get(position).getMaintitle());
                } else {
                    Toast.makeText(context, "click not available for this option as the maintenance issue has been resolved", Toast.LENGTH_LONG).show();
                }

            }
        });
        nameSender = holder.reporter.getText().toString().trim();


        return Viewrow;
    }

    public String ReporterName(String reporterId) {


        Backendless.Persistence.of(BackendlessUser.class).findById(reporterId, new AsyncCallback<BackendlessUser>() {
            @Override
            public void handleResponse(BackendlessUser backendlessUser) {
                name = backendlessUser.getProperty("surname").toString();


            }

            @Override
            public void handleFault(BackendlessFault backendlessFault) {

            }
        });
        return name;
    }

    public void ActionDialog(final String objId, String title) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setMessage(" Are you sure that " + title + " is resolved?");
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                handleBackend(objId);
            }
        });
        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();

            }
        });
        AlertDialog alertDialogb = alertDialogBuilder.create();
        alertDialogb.show();

    }

    public void handleBackend(final String code) {

        Backendless.Persistence.of(maintenance_report.class).findById(code, new AsyncCallback<maintenance_report>() {
            @Override
            public void handleResponse(maintenance_report maintenance_report) {
                maintenance_report.setStatus("SOLVED");
                title = maintenance_report.getMaintitle();


                Backendless.Persistence.save(maintenance_report, new AsyncCallback<com.example.sambulo.administrative.maintenance_report>() {
                    @Override
                    public void handleResponse(maintenance_report maintenance_report) {
                        SendPush(title);

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

    public void SendPush(String title) {

        String message = "new maintenance problem resolved..";
        PublishOptions publishOptions = new PublishOptions();

        publishOptions.putHeader("android-ticker-text", nameSender + " a problem you reported has been fixed");
        publishOptions.putHeader("android-content-title", title + " is the problem we attended to");
        publishOptions.putHeader("android-content-text", "Kindly please assign a role to: ");

        Backendless.Messaging.publish("maintenance", message, publishOptions, new AsyncCallback<MessageStatus>() {
            @Override
            public void handleResponse(MessageStatus messageStatus) {

            }

            @Override
            public void handleFault(BackendlessFault backendlessFault) {

            }
        });


    }


}
