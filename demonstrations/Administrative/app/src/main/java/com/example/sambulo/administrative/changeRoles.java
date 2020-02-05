package com.example.sambulo.administrative;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;

import java.util.ArrayList;
import java.util.List;

public class changeRoles extends AppCompatActivity {
    RadioGroup rbAnyUser;
    Spinner userNames;
    List<BackendlessUser> userList;

    Button btnSubmitChange;
    int gotUser;
    String changedRole, oriEmail;
    RadioButton noneUserRB, educatorRB, adminRB, MasterRB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_roles);

        userList = new ArrayList<>();

        userNames = (Spinner) findViewById(R.id.userNames);

        rbAnyUser = (RadioGroup) findViewById(R.id.rbAnyUser);

        btnSubmitChange = (Button) findViewById(R.id.btnSubmitChange);

        noneUserRB = (RadioButton) findViewById(R.id.noneUserRB);
        educatorRB = (RadioButton) findViewById(R.id.educatorRB);
        adminRB = (RadioButton) findViewById(R.id.adminRB);
        MasterRB = (RadioButton) findViewById(R.id.MasterRB);
    }

    @Override
    protected void onResume() {
        super.onResume();
        DisplayUsers();
        userNames.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                oriEmail = userList.get(position).getEmail();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void DisplayUsers() {
        if (userList != null) {
            userList.clear();
        }
        String whereClause = "surname !=null";
        DataQueryBuilder dataQuery = DataQueryBuilder.create();
        dataQuery.setWhereClause(whereClause);
        dataQuery.setPageSize(100).setOffset(0);


        Backendless.Data.of(BackendlessUser.class).find(dataQuery, new AsyncCallback<List<BackendlessUser>>() {
            @Override
            public void handleResponse(List<BackendlessUser> backendlessUsers) {
                userList = backendlessUsers;
                UsersAdapter usersAdapter = new UsersAdapter(changeRoles.this, backendlessUsers);
                userNames.setAdapter(usersAdapter);
            }

            @Override
            public void handleFault(BackendlessFault backendlessFault) {
                showToast(backendlessFault.getMessage());


            }
        });
    }

    public void btnSubmitChange(View view) {
        switch (rbAnyUser.getCheckedRadioButtonId()) {
            case R.id.noneUserRB:
                changedRole = "None";
                break;
            case R.id.educatorRB:
                changedRole = "Default";
                break;
            case R.id.adminRB:
                changedRole = "Admin";
                break;
            case R.id.MasterRB:
                changedRole = "Master";
                break;
        }

        String whereClause = "email ='" + oriEmail + "'";
        DataQueryBuilder dataQuery = DataQueryBuilder.create();
        dataQuery.setWhereClause(whereClause);
        dataQuery.setPageSize(100).setOffset(0);

        Backendless.Data.of(BackendlessUser.class).find(dataQuery, new AsyncCallback<List<BackendlessUser>>() {
            @Override
            public void handleResponse(List<BackendlessUser> backendlessUsers) {
                for (int i = 0; i < backendlessUsers.size(); i++) {
                    gotUser = i;
                }
                backendlessUsers.get(gotUser).setProperty("role", changedRole);
                Backendless.Persistence.save(backendlessUsers.get(gotUser), new AsyncCallback<BackendlessUser>() {
                    @Override
                    public void handleResponse(BackendlessUser backendlessUser) {
                        showToast("Successfully Assigned A Role");
                    }

                    @Override
                    public void handleFault(BackendlessFault backendlessFault) {
                        Toast.makeText(changeRoles.this, backendlessFault.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

            }

            @Override
            public void handleFault(BackendlessFault backendlessFault) {
                Toast.makeText(changeRoles.this, backendlessFault.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void showToast(String msg) {
        View toastview = getLayoutInflater().inflate(R.layout.toast, (ViewGroup) findViewById(R.id.customLinear));
        TextView tvToast = (TextView) toastview.findViewById(R.id.tvToast);
        tvToast.setText(msg);

        Toast toast = new Toast(changeRoles.this);
        toast.setDuration(toast.LENGTH_LONG);
        toast.setView(toastview);
        toast.setGravity(Gravity.BOTTOM | Gravity.FILL_HORIZONTAL, 0, 0);
        toast.show();

    }

}
