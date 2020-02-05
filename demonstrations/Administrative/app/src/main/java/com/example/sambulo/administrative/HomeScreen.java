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
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.local.UserIdStorageFactory;

public class HomeScreen extends AppCompatActivity {
    Button btnClasses, btnTckShop, btnReport, btnAdmin;
    String role;
    TextView homeTv;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setIcon(R.mipmap.pic);
        actionBar.setTitle(" Welcome");
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);


        btnClasses = (Button) findViewById(R.id.btnClasses);
        btnTckShop = (Button) findViewById(R.id.btnTckShop);
        btnReport = (Button) findViewById(R.id.btnReport);
        btnAdmin = (Button) findViewById(R.id.btnAdmin);
        homeTv = (TextView) findViewById(R.id.homeTv);
        homeTv.setText("User: " + getIntent().getStringExtra("email"));

        role = getIntent().getStringExtra("Roles");
        switch (role) {
            case "Master":
                btnTckShop.setVisibility(View.INVISIBLE);
                break;

            case "Default":
                btnAdmin.setVisibility(View.INVISIBLE);
                break;
            case "Admin":
                btnTckShop.setVisibility(View.INVISIBLE);
                break;
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.home, menu);
        if (role.equalsIgnoreCase("Default")) {
            menu.findItem(R.id.add).setVisible(false);
        }
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {
            case R.id.exit:
                Backendless.UserService.logout(new AsyncCallback<Void>() {
                    @Override
                    public void handleResponse(Void aVoid) {
                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(i);
                        Toast.makeText(HomeScreen.this, "goodbye", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void handleFault(BackendlessFault backendlessFault) {

                        Toast.makeText(HomeScreen.this, backendlessFault.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case R.id.add:
                Intent i = new Intent(this, Personal_information.class);
                startActivity(i);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    public void setBtnClasses(View v) {
        final CharSequence selected[] = new CharSequence[]{"Pin Pops", "Big Boom", "Yoghetta", "Monster Pop", "Stumbo", "Watermellon"};
        AlertDialog.Builder alert = new AlertDialog.Builder(HomeScreen.this);
        alert.setTitle("Select your Class");
        alert.setItems(selected, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                MoveToList(selected[which].toString());
            }
        });
        alert.show();

    }

    public void setBtnTckShop(View view) {
        final CharSequence selecteed[] = new CharSequence[]{"Pin Pops", "Big Boom", "Yoghetta", "Monster Pop", "Stumbo", "Watermellon"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select your class");
        builder.setItems(selecteed, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                returnClass(selecteed[which].toString());

            }
        });
        builder.show();


    }

    public void setBtnReport(View view) {
        Intent i = new Intent(this, REPORTS.class);
        startActivity(i);
    }

    public void setBtnAdmin(View view) {
        switch (getIntent().getStringExtra("Roles")) {
            case "Admin":

                final CharSequence admin[] = new CharSequence[]{"Tuck shop", "Maintenance Reports"};

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Select any option");
                builder.setItems(admin, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        moveToNext(admin[which].toString());


                    }
                });
                builder.show();
                break;
            case "Master":
                final CharSequence master[] = new CharSequence[]{"Tuck shop", "Maintenance Reports", "Learner reports", "Set user roles"};
                AlertDialog.Builder builders = new AlertDialog.Builder(this);
                builders.setTitle("Select any option");
                builders.setItems(master, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        moveToNext(master[which].toString());


                    }
                });
                builders.show();

                break;
        }


    }

    public void returnClass(String room) {


        Intent intent = new Intent(this, tuckshopAdmin.class);
        intent.putExtra("className", room);
        startActivity(intent);

    }

    public void moveToNext(String choice) {
        switch (choice) {
            case "Tuck shop":
                Intent intent = new Intent(this, tuckShop.class);
                startActivity(intent);

                break;
            case "Maintenance Reports":
                Intent mainList = new Intent(this, maitenence_list.class);
                startActivity(mainList);
                break;
            case "Learner reports":
                Intent learn = new Intent(this, show_learner_report.class);
                startActivity(learn);
                break;
            case "Set user roles":
                Intent set = new Intent(this, changeRoles.class);
                startActivity(set);

                break;
        }//end switch

    }


    public void MoveToList(String classes) {
        Intent i = new Intent(this, Student_list.class);
        i.putExtra("class name", classes);
        startActivity(i);

    }

}
