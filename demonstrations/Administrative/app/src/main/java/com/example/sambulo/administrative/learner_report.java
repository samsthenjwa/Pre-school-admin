package com.example.sambulo.administrative;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;

import java.util.ArrayList;
import java.util.List;

import static com.example.sambulo.administrative.R.id.learner_reaport;
import static com.example.sambulo.administrative.R.id.view;

public class learner_report extends AppCompatActivity {
    ProgressDialog progressDialog;

    STUDENTS_REPORT student_report;
    TextView tvChoose, tvRecomendation;
    Spinner spinnerLearner;
    RadioButton rbJust_for_info, rbInform_parents, rbProHelp;
    EditText rptTitle, rptData;
    Button rptSbmBtn;
    String name;
    List<Learner> learners;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learner_report);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setIcon(R.mipmap.pic);
        actionBar.setTitle(" Leaner Report");
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        progressDialog = new ProgressDialog(learner_report.this);
        learners = new ArrayList<>();

        spinnerLearner = (Spinner) findViewById(R.id.spinnerLearner);
        tvChoose = (TextView) findViewById(R.id.tvChoose);
        tvChoose.setText("Choose a learner from " + getIntent().getStringExtra("class name"));
        spinnerLearner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                name = ((TextView) view.findViewById(R.id.txtLearner_user)).getText().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        tvRecomendation = (TextView) findViewById(R.id.tvRecomendation);

        rbJust_for_info = (RadioButton) findViewById(R.id.rbJust_for_info);
        rbInform_parents = (RadioButton) findViewById(R.id.rbInform_parents);
        rbProHelp = (RadioButton) findViewById(R.id.rbProHelp);
        rptTitle = (EditText) findViewById(R.id.rptTitle);
        rptData = (EditText) findViewById(R.id.rptData);
        rptSbmBtn = (Button) findViewById(R.id.rptSbmBtn);


        rptSbmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (rptData.getText().toString().isEmpty()) {

                    rptData.setError("Field can not be left empty");
                } else if (rptTitle.getText().toString().isEmpty()) {
                    rptTitle.setError("A title for the report is required");

                } else {
                    student_report = new STUDENTS_REPORT();
                    student_report.setReports(rptData.getText().toString());
                    student_report.setClassName(getIntent().getStringExtra("class name"));
                    student_report.setTitle(rptTitle.getText().toString());
                    student_report.setNames(name);
                    student_report.setStatus("NOT RESOLVED");

                    if (rbJust_for_info.isChecked()) {
                        student_report.setRecomendation("Just for information");
                    } else if (rbProHelp.isChecked()) {
                        student_report.setRecomendation("Get professional help");
                    } else if (rbInform_parents.isChecked()) {
                        student_report.setRecomendation("Inform parents");
                    }
                    progressDialog.setMessage("learner report is being captured..");
                    progressDialog.show();
                    Backendless.Persistence.save(student_report, new AsyncCallback<STUDENTS_REPORT>() {
                        @Override
                        public void handleResponse(STUDENTS_REPORT students_report) {
                            progressDialog.dismiss();
                            showToast("A report has ben created");
                        }

                        @Override
                        public void handleFault(BackendlessFault backendlessFault) {
                            progressDialog.dismiss();
                            showToast(backendlessFault.getMessage());

                        }
                    });

                }


            }
        });
        Intent i = new Intent(this, HomeScreen.class);
        startActivity(i);
    }

    @Override
    protected void onResume() {
        super.onResume();
        LoadData();

    }

    public void LoadData() {
        if (learners != null) {
            learners.clear();
        }

        String whereClause = "className = '" + getIntent().getStringExtra("class name") + "'";
        DataQueryBuilder dataQuery = DataQueryBuilder.create();
        dataQuery.setWhereClause(whereClause);
        dataQuery.setPageSize(100).setOffset(0);

        Backendless.Data.of(Learner.class).find(dataQuery, new AsyncCallback<List<Learner>>() {
            @Override
            public void handleResponse(List<Learner> list) {
                learners = list;
                LearnerAdapter learnerAdapter = new LearnerAdapter(learner_report.this, learners);
                spinnerLearner.setAdapter(learnerAdapter);
            }

            @Override
            public void handleFault(BackendlessFault backendlessFault) {


            }
        });


    }

    public void showToast(String msg) {
        View toastview = getLayoutInflater().inflate(R.layout.toast, (ViewGroup) findViewById(R.id.customLinear));
        TextView tvToast = (TextView) toastview.findViewById(R.id.tvToast);
        tvToast.setText(msg);

        Toast toast = new Toast(learner_report.this);
        toast.setDuration(toast.LENGTH_LONG);
        toast.setView(toastview);
        toast.setGravity(Gravity.BOTTOM | Gravity.FILL_HORIZONTAL, 0, 0);
        toast.show();

    }


}
