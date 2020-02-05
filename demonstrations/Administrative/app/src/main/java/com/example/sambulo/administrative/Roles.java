package com.example.sambulo.administrative;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;
import com.backendless.persistence.local.UserIdStorageFactory;

import java.util.List;

public class Roles extends AppCompatActivity {
    TextView tvnewUserName;
    String[] splitText;
    String role, email, space, fEmail;
    Spinner spinnerRole;
    int found;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roles);
        splitText = getIntent().getStringExtra("content_text").split(" ");


        ActionBar actionBar = getSupportActionBar();
        actionBar.setIcon(R.mipmap.pic);
        actionBar.setTitle("Assign role to: " + splitText[6]);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        tvnewUserName = (TextView) findViewById(R.id.tvnewUserName);
        tvnewUserName.setText("" + getIntent().getStringExtra("content_title"));


        spinnerRole = (Spinner) findViewById(R.id.spinnerRole);

        spinnerRole.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getSelectedItem().toString().equalsIgnoreCase("Select a Role")) {

                    showToast("An invalid role has been selected");
                } else {
                    role = parent.getSelectedItem().toString();
                    String whereClause = "email='" + fEmail + "'";
                    DataQueryBuilder queryBuilder = DataQueryBuilder.create();
                    queryBuilder.setWhereClause(whereClause);
                    queryBuilder.setPageSize(100).setOffset(0);

                    Backendless.Data.of(BackendlessUser.class).find(queryBuilder, new AsyncCallback<List<BackendlessUser>>() {
                        @Override
                        public void handleResponse(List<BackendlessUser> backendlessUsers) {
                            for (int i = 0; i < backendlessUsers.size(); i++) {
                                found = i;

                            }
                            backendlessUsers.get(found).setProperty("role", role);

                            Backendless.Persistence.save(backendlessUsers.get(found), new AsyncCallback<BackendlessUser>() {
                                @Override
                                public void handleResponse(BackendlessUser backendlessUser) {
                                    showToast("role changed successfully");
                                    finish();
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

                }//end else
            }//onItemSelected

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });//spinnerRole.setOnItemSelectedListener


        email = getIntent().getStringExtra("content_title");
        space = " ";
        String[] token = email.split(" ");
        fEmail = token[0];


    }//end on create

    public void showToast(String msg) {
        View toastview = getLayoutInflater().inflate(R.layout.toast, (ViewGroup) findViewById(R.id.customLinear));
        TextView tvToast = (TextView) toastview.findViewById(R.id.tvToast);
        tvToast.setText(msg);

        Toast toast = new Toast(Roles.this);
        toast.setDuration(toast.LENGTH_LONG);
        toast.setView(toastview);
        toast.setGravity(Gravity.BOTTOM | Gravity.FILL_HORIZONTAL, 0, 0);
        toast.show();

    }
}
