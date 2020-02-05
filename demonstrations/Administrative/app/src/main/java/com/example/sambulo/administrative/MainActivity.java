package com.example.sambulo.administrative;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;

import java.util.ArrayList;
import java.util.List;

import static com.example.sambulo.administrative.R.id.etEmail;
import static com.example.sambulo.administrative.R.id.view;


public class MainActivity extends AppCompatActivity {
    EditText etEmail, etPassword;
    Button btnLogin, btnRegister, btnResetP;
    boolean flag = false;
    String role;
    int found;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setIcon(R.mipmap.pic);
        actionBar.setTitle("Smart Wise Pre-School");
        actionBar.show();

        btnResetP = (Button) findViewById(R.id.btnResetP);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        boolean connected = ConnectionAvailable();
        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);


        if (connected != false) {
            final AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);

            alert.setTitle("Connection time out");

            alert.setMessage("Please fix your internet connection and try again");


            alert.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    showToast("Application is terminated ");
                    finish();
                }
            });
            alert.show();
        }


        //end on Create
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("busy loading please wait..");
                progressDialog.show();
                if (etEmail.getText().toString().trim().isEmpty() || etPassword.getText().toString().trim().isEmpty()) {
                    showToast("enter all fields...");
                } else {
                    Backendless.UserService.login(etEmail.getText().toString().trim(), etPassword.getText().toString().trim(), new AsyncCallback<BackendlessUser>() {
                        @Override
                        public void handleResponse(BackendlessUser response) {

                            String whereClause = "email = '" + etEmail.getText().toString().trim() + "'";
                            DataQueryBuilder queryBuilder = DataQueryBuilder.create();
                            queryBuilder.setWhereClause(whereClause);
                            queryBuilder.setPageSize(100).setOffset(0);

                            Backendless.Data.of(BackendlessUser.class).find(queryBuilder, new AsyncCallback<List<BackendlessUser>>() {
                                @Override
                                public void handleResponse(List<BackendlessUser> backendlessUsers) {
                                    progressDialog.dismiss();
                                    for (int i = 0; i < backendlessUsers.size(); i++) {
                                        found = i;
                                    }
                                    role = backendlessUsers.get(found).getProperty("role").toString();
                                    switch (role) {
                                        case "Default":
                                            role = "Default";
                                            break;
                                        case "Master":
                                            role = "Master";
                                            break;
                                        case "Admin":
                                            role = "Admin";
                                            break;
                                        case "None":
                                            showToast("Master has not yes assigned you a role, there for you can not go further");
                                            System.exit(0);
                                            break;

                                    }//end switch
                                    if (role.equalsIgnoreCase("master")) {
                                        RegisterDeviceForPush();

                                    }

                                    Intent i = new Intent(getApplicationContext(), HomeScreen.class);
                                    i.putExtra("Roles", role);
                                    i.putExtra("email", etEmail.getText().toString());
                                    startActivity(i);

                                }

                                @Override
                                public void handleFault(BackendlessFault backendlessFault) {
                                    progressDialog.dismiss();
                                    showToast(backendlessFault.getMessage());

                                }
                            });


                        }

                        @Override
                        public void handleFault(BackendlessFault fault) {
                            progressDialog.dismiss();
                            flag = false;
                            final AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                            alert.setMessage("loading...");
                            alert.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });
                            showToast(fault.getMessage());
                        }
                    });
                }
            }

        });


        btnResetP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etEmail.getText().toString().isEmpty()) {
                    etEmail.setError("enter a valid email address");
                } else {
                    progressDialog.setMessage("send you an email. please wait..");
                    progressDialog.show();
                    Backendless.UserService.restorePassword(etEmail.getText().toString().trim(), new AsyncCallback<Void>() {
                        @Override
                        public void handleResponse(Void response) {
                            progressDialog.dismiss();
                            showToast("Link to reset password has been sent to your email");
                        }

                        @Override
                        public void handleFault(BackendlessFault fault) {
                            progressDialog.dismiss();
                            showToast(fault.getMessage());
                        }
                    });

                }


            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_activity, menu);

        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {
            case R.id.weather:
                Intent intent = new Intent(getApplicationContext(), WeatherActivity.class);
                startActivity(intent);

                break;
            case R.id.location:
                Intent map = new Intent(this, Maps.class);
                startActivity(map);

                break;
            case R.id.emergency:
                Intent mergency = new Intent(this, InCase_of_Emergency.class);
                startActivity(mergency);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    public void onClick_btnRegister(View view) {
        Intent i = new Intent(this, Registration.class);
        startActivity(i);
    }


    public void showToast(String msg) {
        View toastview = getLayoutInflater().inflate(R.layout.toast, (ViewGroup) findViewById(R.id.customLinear));
        TextView tvToast = (TextView) toastview.findViewById(R.id.tvToast);
        tvToast.setText(msg);

        Toast toast = new Toast(MainActivity.this);
        toast.setDuration(toast.LENGTH_LONG);
        toast.setView(toastview);
        toast.setGravity(Gravity.BOTTOM | Gravity.FILL_HORIZONTAL, 0, 0);
        toast.show();

    }//end  showToast method

    private boolean ConnectionAvailable() {
        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        if (activeNetwork == null) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                connected = true;
            } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                connected = true;
            }
        } else {
            connected = false;
        }
        return connected;
    }

    private void RegisterDeviceForPush() {
        ArrayList<String> Channel = new ArrayList<>();
        Channel.add("Master");

        Backendless.Messaging.registerDevice("1090264818575", new AsyncCallback<Void>() {
            @Override
            public void handleResponse(Void aVoid) {
                Log.e("My App", "device has been registered");

            }

            @Override
            public void handleFault(BackendlessFault backendlessFault) {
                Log.e("error occured", backendlessFault.getMessage());


            }
        });
    }

}
