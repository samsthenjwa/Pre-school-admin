package com.example.sambulo.administrative;

import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;

import java.util.List;

public class Student_list extends AppCompatActivity {
    ListView studentList;
    List<Learner> learnerList;
    String nameclass;
    String mother,fatherphone;
    int mine;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setIcon(R.mipmap.pic);
        actionBar.setTitle("Students List");
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        studentList = (ListView) findViewById(R.id.studentList);


    }

    @Override
    protected void onResume() {
        super.onResume();
        LoadData();

        studentList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent,
                                    View view, int position, long id) {

                Intent i = new Intent(getApplicationContext(), Munipulation.class);

                i.putExtra("class", nameclass);
                i.putExtra("objectId", learnerList.get(position).getObjectId());
                startActivity(i);
            }
        });

        studentList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {


                final CharSequence lis[] = new CharSequence[]{"email parents", "call parents"};
                AlertDialog.Builder builders = new AlertDialog.Builder(getApplicationContext());
                builders.setTitle("Select any option");
                builders.setItems(lis, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                            mine = which;
                    }
                });
                builders.show();


//                if(mine == 0)
//                {
                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setData(Uri.parse("email"));
                    String[] s = {learnerList.get(position).getFatherEmail(), learnerList.get(position).getMotherEmail()};
                    i.putExtra(Intent.EXTRA_EMAIL, s);
                    i.putExtra(Intent.EXTRA_SUBJECT, "THE KID WAS PICKED UP");
                    i.putExtra(Intent.EXTRA_TEXT, "Hi\n" + learnerList.get(position).getLearnerName() + " was picked up.");
                    i.setType("message/rfc822");
                    Intent chooser = Intent.createChooser(i, "Launch Email");
                    startActivity(chooser);

//                }else if (mine == 1)
//                {
//
//                    mother = learnerList.get(position).getMotherPhone();
//                    fatherphone = learnerList.get(position).getFatherPhone();
//                    Intent i = new Intent(Intent.ACTION_CALL);
//                    i.setData(Uri.parse(mother));
//
//                    if(ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CALL_PHONE)!=
//                            PackageManager.PERMISSION_GRANTED){
//                        showToast( "Please Grant Permission");
//                        requestPermission();
////                    }else
//                        startActivity(i);
//                }
                return false;
            }
        });
    }

    public void LoadData() {
        if (learnerList != null) {
            learnerList.clear();
        }

        nameclass = getIntent().getStringExtra("class name");

        String whereClause = "className = '" + nameclass + "'";
        DataQueryBuilder queryBuilder = DataQueryBuilder.create();
        queryBuilder.setWhereClause(whereClause);
        queryBuilder.setPageSize(100).setOffset(0);


        Backendless.Persistence.of(Learner.class).find(queryBuilder, new AsyncCallback<List<Learner>>() {
            @Override
            public void handleResponse(final List<Learner> feedback) {
                learnerList = feedback;
                final customList custom = new customList(Student_list.this, learnerList);
                studentList.setAdapter(custom);

            }

            @Override
            public void handleFault(BackendlessFault backendlessFault) {
                showToast(backendlessFault.getMessage());
            }
        });
    }



    private void requestPermission() {
        ActivityCompat.requestPermissions(this,new String[]{android.Manifest.permission.CALL_PHONE},1);
    }

    public void showToast(String msg) {
        View toastview = getLayoutInflater().inflate(R.layout.toast, (ViewGroup) findViewById(R.id.customLinear));
        TextView tvToast = (TextView) toastview.findViewById(R.id.tvToast);
        tvToast.setText(msg);

        Toast toast = new Toast(Student_list.this);
        toast.setDuration(toast.LENGTH_LONG);
        toast.setView(toastview);
        toast.setGravity(Gravity.BOTTOM | Gravity.FILL_HORIZONTAL, 0, 0);
        toast.show();

    }//end  showToast method

}
