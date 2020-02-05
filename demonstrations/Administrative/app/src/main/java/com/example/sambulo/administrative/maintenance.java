package com.example.sambulo.administrative;

import android.content.DialogInterface;
import android.os.Environment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class maintenance extends AppCompatActivity {
    Spinner comboSpinner;

    TextView maitenanceTitle, maintenanceDescription;
    maintenance_report reports;
    Button buttonMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setIcon(R.mipmap.pic);
        actionBar.setTitle(" Maintenance Reports");
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);


        comboSpinner = (Spinner) findViewById(R.id.comboSpinner);
        maintenanceDescription = (TextView) findViewById(R.id.maintenanceDescription);
        maitenanceTitle = (TextView) findViewById(R.id.maintenanceTitle);
        buttonMain = (Button) findViewById(R.id.buttonMain);
        buttonMain.setVisibility(View.GONE);

        ArrayAdapter adaptercombo = ArrayAdapter.createFromResource(this, R.array.Maitenance, android.R.layout.simple_spinner_item);
        comboSpinner.setAdapter(adaptercombo);


    }

    @Override
    protected void onResume() {
        super.onResume();

        comboSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String spinnar = parent.getItemAtPosition(position).toString();

                if (comboSpinner.getSelectedItem().toString().equalsIgnoreCase("Select maintenance problem")) {

                    maintenanceDescription.setVisibility(View.INVISIBLE);
                    maitenanceTitle.setVisibility(View.INVISIBLE);
                    buttonMain.setVisibility(View.INVISIBLE);

                }

                switch (spinnar) {

                    case "Pipe Blockage":
                        maintenanceDescription.setVisibility(View.VISIBLE);
                        maitenanceTitle.setVisibility(View.VISIBLE);
                        buttonMain.setVisibility(View.VISIBLE);
                        maitenanceTitle.setText("Pipe blocked at the toilets");
                        maintenanceDescription.setText("The pipes seems to be blocked at the toilets because if you flush, the suerage leaks and " +
                                "its starting to stink the whole yard." +
                                "Please fix the pipes as soon as possible ");

                        break;
                    case "Broken windows":
                        maintenanceDescription.setVisibility(View.VISIBLE);
                        maitenanceTitle.setVisibility(View.VISIBLE);
                        buttonMain.setVisibility(View.VISIBLE);
                        maitenanceTitle.setText("Broken windows in classes");
                        maintenanceDescription.setText("The weather is not so good lately and we have some of the classes with broken windows " +
                                "please fix them as soon as possible.");


                    case "Leaking taps":
                        maintenanceDescription.setVisibility(View.VISIBLE);
                        maitenanceTitle.setVisibility(View.VISIBLE);
                        buttonMain.setVisibility(View.VISIBLE);
                        maitenanceTitle.setText("Taps are leaking.....");
                        maintenanceDescription.setText("Because of the high rates of water, can you please fix all the leaking taps " +
                                "we are enough of paying.....");

                        break;
                    case "Electricity triping":
                        maintenanceDescription.setVisibility(View.VISIBLE);
                        maitenanceTitle.setVisibility(View.VISIBLE);
                        buttonMain.setVisibility(View.VISIBLE);
                        maitenanceTitle.setText("Pipe blocked at the toilets");
                        maintenanceDescription.setText("The pipes seems to be blocked at the toilets because if you flush, the suerage leaks and " +
                                "its starting to stink the whole yard." +
                                "Please fix the pipes as soon as possible ");

                        break;
                    case "Yard cleaning":
                        maintenanceDescription.setVisibility(View.VISIBLE);
                        maitenanceTitle.setVisibility(View.VISIBLE);
                        buttonMain.setVisibility(View.VISIBLE);
                        maitenanceTitle.setText("The yard is dirty");
                        maintenanceDescription.setText("Please find someone to clean the yard.....");
                        break;

                }//end switch
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    public void onClickBtnMain(View view) {
        reports = new maintenance_report();
        reports.setProblem(comboSpinner.getSelectedItem().toString());
        reports.setMaintitle(maitenanceTitle.getText().toString());
        reports.setDescription(maintenanceDescription.getText().toString());
        reports.setStatus("NOT RESOLVED");

        Backendless.Persistence.save(reports, new AsyncCallback<maintenance_report>() {
            @Override
            public void handleResponse(maintenance_report maintenance_report) {
                showToast("Maintenance report set");
                RegisterDeviceForPush();
                finishAndRemoveTask();


            }

            @Override
            public void handleFault(BackendlessFault backendlessFault) {
                showToast(backendlessFault.getMessage());
            }
        });
    }

    public void RegisterDeviceForPush() {
        List<String> channel = new ArrayList<>();
        channel.add("maintenance");
        Backendless.Messaging.registerDevice("1090264818575", channel, null, new AsyncCallback<Void>() {
            @Override
            public void handleResponse(Void aVoid) {
                Log.d("New device added", "a new device has been registered under maintenance");

            }

            @Override
            public void handleFault(BackendlessFault backendlessFault) {
                backendlessFault.getMessage();
            }
        });

    }

    public void showToast(String msg) {
        View toastview = getLayoutInflater().inflate(R.layout.toast, (ViewGroup) findViewById(R.id.customLinear));
        TextView tvToast = (TextView) toastview.findViewById(R.id.tvToast);
        tvToast.setText(msg);

        Toast toast = new Toast(maintenance.this);
        toast.setDuration(toast.LENGTH_LONG);
        toast.setView(toastview);
        toast.setGravity(Gravity.BOTTOM | Gravity.FILL_HORIZONTAL, 0, 0);
        toast.show();

    }


}