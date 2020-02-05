package com.example.sambulo.administrative;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.BackendlessDataQuery;
import com.backendless.persistence.DataQueryBuilder;

import java.util.ArrayList;
import java.util.List;

public class tuckshopAdmin extends AppCompatActivity {
    ListView view;
    List<Learner> learners;
    LearnerTransaction learnerTransaction;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setIcon(R.mipmap.pic);
        actionBar.setTitle(getIntent().getStringExtra("className") + " learners");
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_tuckshop_admin);

        view = (ListView) findViewById(R.id.view);
        progressDialog = new ProgressDialog(tuckshopAdmin.this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        LoadData();

        view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                String name = ((TextView) view.findViewById(R.id.tvnameAndsurname)).getText().toString();
                String balance = ((TextView) view.findViewById(R.id.tv)).getText().toString();
                String selectedClass = ((TextView) view.findViewById(R.id.tvClasseee)).getText().toString();
                String[] finalBalance = balance.split("\n");
                String[] finalInitials = name.split(" ");


                learnerTransaction = new LearnerTransaction();


                learnerTransaction.setClassName(selectedClass);
                learnerTransaction.setLearnerName(finalInitials[0]);
                learnerTransaction.setLearnerSurname(finalInitials[1]);
                learnerTransaction.setTransaction(Double.parseDouble(finalBalance[1]));

                learnerTransaction.setCurrentBalance(0.0);
                learnerTransaction.setAfterTransaction(0.0);


                Intent intent = new Intent(getApplicationContext(), shopTransaction.class);
                intent.putExtra("transaction", learnerTransaction);
                intent.putExtra("birth", learners.get(position).getObjectId());
                startActivity(intent);

            }
        });
    }

    public void LoadData() {
        if (learners != null) {
            learners.clear();
        }
        progressDialog.setMessage("Busy loading learner data..");
        progressDialog.show();
        String whereClause = "className = '" + getIntent().getStringExtra("className") + "'";
        DataQueryBuilder dataQuery = DataQueryBuilder.create();
        dataQuery.setWhereClause(whereClause);
        dataQuery.setPageSize(100).setOffset(0);


        Backendless.Persistence.of(Learner.class).find(dataQuery, new AsyncCallback<List<Learner>>() {
            @Override
            public void handleResponse(List<Learner> list) {
                progressDialog.dismiss();
                learners = list;
                CustomShopAdapter adapter = new CustomShopAdapter(tuckshopAdmin.this, learners);
                view.setAdapter(adapter);

            }

            @Override
            public void handleFault(BackendlessFault backendlessFault) {
                progressDialog.dismiss();


            }
        });


    }

}
