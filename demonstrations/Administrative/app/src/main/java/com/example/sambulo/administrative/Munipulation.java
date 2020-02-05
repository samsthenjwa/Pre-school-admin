package com.example.sambulo.administrative;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;

import java.util.List;

public class Munipulation extends AppCompatActivity {
    Button editButtb;
    EditText stdname, stdsurname, stdId, stdGender, stdDateOfbirt, stdRace, stdAddress, stdFathername, stdFatheremail,
            stdFatherphone, stdMothername, stdMotheremail, stdMotherphone, stdDoctorname, stdDoctorphone,
            stdMedicalname, stdMedicalnumber, stdAllergies;
    TextView confirm;
    int get;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_munipulation);

        stdname = (EditText) findViewById(R.id.stdname);
        stdsurname = (EditText) findViewById(R.id.stdsurname);
        stdId = (EditText) findViewById(R.id.stdId);
        stdGender = (EditText) findViewById(R.id.stdGender);
        stdDateOfbirt = (EditText) findViewById(R.id.stdDateOfbirt);
        stdRace = (EditText) findViewById(R.id.stdRace);
        stdAddress = (EditText) findViewById(R.id.stdAddress);
        stdFathername = (EditText) findViewById(R.id.stdFathername);
        stdFatheremail = (EditText) findViewById(R.id.stdFatheremail);
        stdFatherphone = (EditText) findViewById(R.id.stdFatherphone);
        stdMothername = (EditText) findViewById(R.id.stdMothername);
        stdMotheremail = (EditText) findViewById(R.id.stdMotheremail);
        stdMotherphone = (EditText) findViewById(R.id.stdMotherphone);
        stdDoctorname = (EditText) findViewById(R.id.stdDoctorname);
        stdDoctorphone = (EditText) findViewById(R.id.stdDoctorphone);
        stdMedicalname = (EditText) findViewById(R.id.stdMedicalname);
        stdMedicalnumber = (EditText) findViewById(R.id.stdMedicalnumber);
        stdAllergies = (EditText) findViewById(R.id.stdAllergies);

        editButtb = (Button) findViewById(R.id.editButtb);

        editButtb.setVisibility(View.INVISIBLE);

        String whereClause = "className = '" + getIntent().getStringExtra("class") + "'";
        DataQueryBuilder queryBuilder = DataQueryBuilder.create();
        queryBuilder.setWhereClause(whereClause);
        queryBuilder.setPageSize(100).setOffset(0);


        Backendless.Persistence.of(Learner.class).find(queryBuilder, new AsyncCallback<List<Learner>>() {
            @Override
            public void handleResponse(List<Learner> list) {
                for (int num = 0; num < list.size(); num++) {

                    get = num;
                }

                stdname.setText(list.get(get).getLearnerName());
                stdsurname.setText(list.get(get).getLearnerSurname());
                stdId.setText("ID " + list.get(get).getLearnerId());
                stdGender.setText(list.get(get).getGender());
                stdDateOfbirt.setText(list.get(get).getDateOfBirth());
                stdRace.setText(list.get(get).getRace());
                stdAddress.setText(list.get(get).getLearnerAddress());
                stdFathername.setText(list.get(get).getFatherName());
                stdFatheremail.setText(list.get(get).getFatherEmail());
                stdFatherphone.setText(list.get(get).getFatherPhone());
                stdMothername.setText(list.get(get).getMotherName());
                stdMotheremail.setText(list.get(get).getMotherEmail());
                stdMotherphone.setText(list.get(get).getMotherPhone());
                stdDoctorname.setText(list.get(get).getDocName());
                stdDoctorphone.setText(list.get(get).getDocPhone());
                stdMedicalname.setText(list.get(get).getMedName());
                stdMedicalnumber.setText(list.get(get).getMedNumber());
                stdAllergies.setText(list.get(get).getAllergies());

            }

            @Override
            public void handleFault(BackendlessFault backendlessFault) {
                showToast(backendlessFault.getMessage());
            }
        });


        stdname.setEnabled(false);
        stdsurname.setEnabled(false);
        stdId.setEnabled(false);
        stdGender.setEnabled(false);
        stdDateOfbirt.setEnabled(false);
        stdRace.setEnabled(false);
        stdAddress.setEnabled(false);
        stdFathername.setEnabled(false);
        stdFatheremail.setEnabled(false);
        stdFatherphone.setEnabled(false);
        stdMothername.setEnabled(false);
        stdMotheremail.setEnabled(false);
        stdMotherphone.setEnabled(false);
        stdDoctorname.setEnabled(false);
        stdDoctorphone.setEnabled(false);
        stdMedicalname.setEnabled(false);
        stdMedicalnumber.setEnabled(false);
        stdAllergies.setEnabled(false);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.edit_learna, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delet:
                LayoutInflater inflater = getLayoutInflater();
                final View v = inflater.inflate(R.layout.confirm, null);

                confirm = (TextView) v.findViewById(R.id.confirm);
                confirm.setText("Are you sure you want to delete? This cannot be undone");

                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                dialog.setTitle("Delete Student");
                dialog.setView(v);
                dialog.setIcon(R.mipmap.delete);

                dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        Backendless.Persistence.of(Learner.class).findById(getIntent().getStringExtra("objectId"), new AsyncCallback<Learner>() {
                            @Override
                            public void handleResponse(Learner learner) {
                                Backendless.Persistence.of(Learner.class).remove(learner, new AsyncCallback<Long>() {
                                    @Override
                                    public void handleResponse(Long aLong) {
                                        showToast("deleted.....");

                                    }

                                    @Override
                                    public void handleFault(BackendlessFault backendlessFault) {
                                        showToast(backendlessFault.getMessage());
                                    }
                                });
                            }

                            @Override
                            public void handleFault(BackendlessFault backendlessFault) {
                                showToast(backendlessFault.getMessage());
                            }
                        });


                    }
                });
                dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //   showToast("Student not deleted");
                    }
                });
                dialog.show();
                break;
            case R.id.edit:
                stdname.setEnabled(true);
                stdsurname.setEnabled(true);
                stdId.setEnabled(false);
                stdGender.setEnabled(true);
                stdDateOfbirt.setEnabled(true);
                stdRace.setEnabled(true);
                stdAddress.setEnabled(true);
                stdFathername.setEnabled(true);
                stdFatheremail.setEnabled(true);
                stdFatherphone.setEnabled(true);
                stdMothername.setEnabled(true);
                stdMotheremail.setEnabled(true);
                stdMotherphone.setEnabled(true);
                stdDoctorname.setEnabled(true);
                stdDoctorphone.setEnabled(true);
                stdMedicalname.setEnabled(true);
                stdMedicalnumber.setEnabled(true);
                stdAllergies.setEnabled(true);

                editButtb.setVisibility(View.VISIBLE);

                editButtb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        Backendless.Persistence.of(Learner.class).findById(getIntent().getStringExtra("objectId"), new AsyncCallback<Learner>() {
                            @Override
                            public void handleResponse(Learner learner) {
                                learner.setLearnerName(stdname.getText().toString());
                                learner.setLearnerSurname(stdsurname.getText().toString());
                                learner.setGender(stdGender.getText().toString());
                                learner.setDateOfBirth(stdDateOfbirt.getText().toString());
                                learner.setRace(stdRace.getText().toString());
                                learner.setLearnerAddress(stdAddress.getText().toString());
                                learner.setFatherName(stdFathername.getText().toString());
                                learner.setFatherEmail(stdFatheremail.getText().toString());
                                learner.setFatherPhone(stdFatherphone.getText().toString());
                                learner.setMotherName(stdMothername.getText().toString());
                                learner.setMotherEmail(stdMotheremail.getText().toString());
                                learner.setMotherName(stdMotherphone.getText().toString());
                                learner.setDocName(stdDoctorname.getText().toString());
                                learner.setMedName(stdMedicalname.getText().toString());
                                learner.setMedNumber(stdMedicalnumber.getText().toString());
                                learner.setAllergies(stdAllergies.getText().toString());

                                Backendless.Persistence.save(learner, new AsyncCallback<Learner>() {
                                    @Override
                                    public void handleResponse(Learner learner) {
                                        showToast("Leaner saved");
                                    }

                                    @Override
                                    public void handleFault(BackendlessFault backendlessFault) {
                                        showToast(backendlessFault.getMessage());
                                    }
                                });
                            }

                            @Override
                            public void handleFault(BackendlessFault backendlessFault) {
                                showToast(backendlessFault.getMessage());

                            }
                        });
                        showToast("edited");
                    }
                });
                Intent q = new Intent(this, Munipulation.class);
                startActivity(q);

        }
        return super.onOptionsItemSelected(item);
    }

    public void showToast(String msg) {
        View toastview = getLayoutInflater().inflate(R.layout.toast, (ViewGroup) findViewById(R.id.customLinear));
        TextView tvToast = (TextView) toastview.findViewById(R.id.tvToast);
        tvToast.setText(msg);

        Toast toast = new Toast(Munipulation.this);
        toast.setDuration(toast.LENGTH_LONG);
        toast.setView(toastview);
        toast.setGravity(Gravity.BOTTOM | Gravity.FILL_HORIZONTAL, 0, 0);
        toast.show();

    }

}
