package com.example.sambulo.administrative;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;

import java.util.List;

public class REPORTS extends AppCompatActivity {
    Button btnReportMaintein, btnReportLearner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setIcon(R.mipmap.pic);
        actionBar.setTitle(" Reports");
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        btnReportMaintein = (Button) findViewById(R.id.btnReportMaintein);
        btnReportLearner = (Button) findViewById(R.id.btnReportLearner);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.report, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.contact:
                break;
            case R.id.maintenance:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setBtnReportLearner(View v) {

        final CharSequence selected[] = new CharSequence[]{"Pin Pops", "Big Boom", "Yoghetta", "Monster Pop", "Stumbo", "Watermellon"};
        AlertDialog.Builder alert = new AlertDialog.Builder(REPORTS.this);
        alert.setTitle("Select your Class");
        alert.setItems(selected, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                MoveToList(selected[which].toString());
            }
        });
        alert.show();

    }

    public void setBtnReportMaintein(View view) {
        Intent i = new Intent(this, maintenance.class);
        startActivity(i);
    }

    public void MoveToList(String classes) {
        Intent i = new Intent(this, learner_report.class);
        i.putExtra("class name", classes);
        startActivity(i);

    }
}
