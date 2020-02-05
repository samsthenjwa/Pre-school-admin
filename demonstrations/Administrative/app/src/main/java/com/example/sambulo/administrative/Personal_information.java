package com.example.sambulo.administrative;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.icu.util.Calendar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

public class Personal_information extends AppCompatActivity {
    Learner newLearner;
    TextView ParentsInformation, dateOfbirthGetter;
    EditText personName, personLastName, postalAdd, enrollDate, idNum, language, docterName,
            medicalAid, medNumber, docNumber, medPlan, allergies, motherName, motherAddress, motherPhone,
            fatherName, fatherAddress, fatherPhone;
    Spinner spinnerClass, spinnerRace, spinnergender;
    // DatePicker datePicker;
    Button btnOk;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_information);


        ParentsInformation = (TextView) findViewById(R.id.parentsInfo);
        dateOfbirthGetter = (TextView) findViewById(R.id.dateOfbirthGetter);

        personName = (EditText) findViewById(R.id.personName);
        personLastName = (EditText) findViewById(R.id.personLastName);


        postalAdd = (EditText) findViewById(R.id.postalAdd);
        enrollDate = (EditText) findViewById(R.id.enrollDate);
        idNum = (EditText) findViewById(R.id.idNum);
        language = (EditText) findViewById(R.id.language);
        spinnerRace = (Spinner) findViewById(R.id.spinnerRace);
        spinnerClass = (Spinner) findViewById(R.id.spinnerClass);
        spinnergender = (Spinner) findViewById(R.id.spinnergender);
        docterName = (EditText) findViewById(R.id.docterName);
        medicalAid = (EditText) findViewById(R.id.medicalAid);
        medNumber = (EditText) findViewById(R.id.medNumber);
        docNumber = (EditText) findViewById(R.id.docNumber);
        medPlan = (EditText) findViewById(R.id.medPlan);
        allergies = (EditText) findViewById(R.id.allergies);
        motherName = (EditText) findViewById(R.id.motherName);
        fatherName = (EditText) findViewById(R.id.fatherName);
        motherAddress = (EditText) findViewById(R.id.motherAddress);
        fatherAddress = (EditText) findViewById(R.id.fatherAddress);
        motherPhone = (EditText) findViewById(R.id.motherPhone);
        fatherPhone = (EditText) findViewById(R.id.fatherPhone);

        btnOk = (Button) findViewById(R.id.btnOk);
        progressDialog= new ProgressDialog(Personal_information.this);



        ArrayAdapter adapterGender =  ArrayAdapter.createFromResource(this,R.array.Gender,android.R.layout.simple_spinner_item);
        spinnergender.setAdapter(adapterGender);


        ArrayAdapter adapterClasses = ArrayAdapter.createFromResource(this,R.array.Classes,android.R.layout.simple_spinner_item);
        spinnerClass.setAdapter(adapterClasses);


        ArrayAdapter adapterRace = ArrayAdapter.createFromResource(this,R.array.Race, android.R.layout.simple_spinner_item);
        spinnerRace.setAdapter(adapterRace);

    }

    public void buttonOk(View v) {
        if (personName.getText().toString().isEmpty()
                || personLastName.getText().toString().isEmpty() ||
                postalAdd.getText().toString().isEmpty() || idNum.getText().toString().isEmpty() ||
                language.getText().toString().isEmpty() || docterName.getText().toString().isEmpty() ||
                medicalAid.getText().toString().isEmpty() || medNumber.getText().toString().isEmpty() ||
                docNumber.getText().toString().isEmpty() || medPlan.getText().toString().isEmpty() || allergies.getText().toString().isEmpty() ||
                motherName.getText().toString().isEmpty() || motherAddress.getText().toString().isEmpty() ||
                motherPhone.getText().toString().isEmpty() || fatherName.getText().toString().isEmpty() || fatherAddress.getText().toString().isEmpty() ||
                fatherPhone.getText().toString().isEmpty()) {

            Toast.makeText(Personal_information.this, "Fill in all the details", Toast.LENGTH_SHORT).show();

        } else {

            newLearner = new Learner();

            newLearner.setLearnerName(personName.getText().toString());
            newLearner.setLearnerSurname(personLastName.getText().toString());
            newLearner.setRace(spinnerRace.getSelectedItem().toString());
            newLearner.setGender(spinnergender.getSelectedItem().toString());
            newLearner.setClassName(spinnerClass.getSelectedItem().toString());
            //  newLearner.setDateOfBirth(dateOfbirthGetter.getText().toString().trim());
            newLearner.setLearnerAddress(postalAdd.getText().toString().trim());
//            String id = idNum.getText().toString().trim();
//            int num = Integer.parseInt(id);
//            newLearner.setLearnerId(num);
            newLearner.setMotherName(motherName.getText().toString().trim());
            newLearner.setFatherName(fatherName.getText().toString().trim());
            newLearner.setMotherEmail(motherAddress.getText().toString().trim());
            newLearner.setFatherEmail(fatherAddress.getText().toString().trim());
            newLearner.setMotherPhone(motherPhone.getText().toString().trim());
            newLearner.setFatherPhone(fatherPhone.getText().toString().trim());
            newLearner.setDocName(docterName.getText().toString().trim());
            newLearner.setDocPhone(docNumber.getText().toString().trim());
            newLearner.setMedName(medicalAid.getText().toString().trim());
            newLearner.setMedNumber(medNumber.getText().toString().trim());
            newLearner.setMedPlan(medPlan.getText().toString().trim());
            newLearner.setAllergies(allergies.getText().toString().trim());
            progressDialog.setMessage("Adding a learner to the system. please wait..");
            progressDialog.show();


            Backendless.Persistence.save(newLearner, new AsyncCallback<Learner>() {
                @Override
                public void handleResponse(Learner learner) {
                    progressDialog.dismiss();
                    showToast("Learner successfully saved");
                }

                @Override
                public void handleFault(BackendlessFault backendlessFault) {
                    progressDialog.dismiss();
                    showToast(backendlessFault.getMessage());
                }
            });

        }
    }


    public void showToast(String msg) {
        View toastview = getLayoutInflater().inflate(R.layout.toast, (ViewGroup) findViewById(R.id.customLinear));
        TextView tvToast = (TextView) toastview.findViewById(R.id.tvToast);
        tvToast.setText(msg);

        Toast toast = new Toast(Personal_information.this);
        toast.setDuration(toast.LENGTH_LONG);
        toast.setView(toastview);
        toast.setGravity(Gravity.BOTTOM | Gravity.FILL_HORIZONTAL, 0, 0);
        toast.show();

    }//end  showToast method
}
