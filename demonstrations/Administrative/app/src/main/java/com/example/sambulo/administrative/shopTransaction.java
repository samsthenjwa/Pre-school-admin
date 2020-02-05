package com.example.sambulo.administrative;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;

import java.util.ArrayList;
import java.util.List;

public class shopTransaction extends AppCompatActivity {

    Button btnSubmitTransaction, btnResetTransaction, button10c, button20c, button50c, buttonR1, buttonR2, buttonR5;
    TextView tvCurrentBalance, tvCurrentTransaction, tvAfterTransaction,balancetv;
  ProgressDialog progressDialog;
    LearnerTransaction learnerTransaction;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_transaction);

        Bundle extras=getIntent().getExtras();
        {
            if(extras!=null){
                learnerTransaction=(LearnerTransaction)getIntent().getSerializableExtra("transaction");

            }
        }
        ActionBar actionBar = getSupportActionBar();
        actionBar.setIcon(R.mipmap.pic);
        actionBar.setTitle(" Buy for "+learnerTransaction.getLearnerName()+ " "+ learnerTransaction.getLearnerSurname() );
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);




        buttonR5 = (Button) findViewById(R.id.buttonR5);
        buttonR2 = (Button) findViewById(R.id.buttonR2);
        buttonR1 = (Button) findViewById(R.id. buttonR1);
        button50c = (Button) findViewById(R.id.button50c);
        button20c = (Button) findViewById(R.id.button20c);
        button10c = (Button) findViewById(R.id.button10c);
        btnResetTransaction = (Button) findViewById(R.id.btnResetTransaction);
        btnSubmitTransaction = (Button) findViewById(R.id.btnSubmitTransaction);
        progressDialog= new ProgressDialog(shopTransaction.this);

        balancetv=(TextView)findViewById(R.id.balancetv);
        tvCurrentBalance=(TextView)findViewById(R.id.tvCurrentBalance);
        tvCurrentTransaction=(TextView)findViewById(R.id.tvCurrentTransaction);
        tvAfterTransaction=(TextView)findViewById(R.id.tvAfterTransaction);

        Adding(0.0);
        tvCurrentBalance.setText("Current balance R"+ learnerTransaction.getTransaction());
    }
    public void onClick(View view){
        switch (view.getId()){
            case R.id.buttonR5:
                Adding((int) 5.0);
                break;

            case R.id.buttonR2:
                Adding((int) 2.0);
                break;

            case R.id.buttonR1:
               Adding((int) 1.0);
                break;

            case R.id.button50c:
                Adding(0.50);
                break;

            case R.id.button20c:
                Adding(0.20);
                break;

            case R.id.button10c:
                Adding(0.10);
                break;

            case R.id.btnResetTransaction:
                learnerTransaction.setTransaction(0);
               learnerTransaction.setAfterTransaction(0);

                //amount=0;
                balancetv.setText("R"+learnerTransaction.getTransaction());
                tvCurrentTransaction.setText("Current transaction: R"+learnerTransaction.getTransaction());
                tvAfterTransaction.setText("After transaction: R"+learnerTransaction.getAfterTransaction());

                break;
            case R.id.btnSubmitTransaction:

                trialMethod();


                break;

        }
    }
    public void Adding(double value)
    {
       learnerTransaction.setCurrentBalance(learnerTransaction.getCurrentBalance()+value);


        balancetv.setText("R"+String.format("%.2f",learnerTransaction.getCurrentBalance()));
        tvCurrentTransaction.setText("Current transaction: R "+String.format("%.2f",learnerTransaction.getCurrentBalance()));

        learnerTransaction.setAfterTransaction(learnerTransaction.getTransaction()-learnerTransaction.getCurrentBalance());

        tvAfterTransaction.setText("After transaction: R"+String.format("%.2f",learnerTransaction.getAfterTransaction()));

    }
    public  void upDateBackendless()
    {
        progressDialog.setMessage("Tuck shop balance is being updated..");
        progressDialog.show();

        Backendless.Persistence.of(Learner.class).findById(getIntent().getStringExtra("birth"), new AsyncCallback<Learner>() {
            @Override
            public void handleResponse(Learner learner) {


                learner.setTuckBalance(Double.parseDouble(String.format("%.2f",learnerTransaction.getAfterTransaction())));
                
                Backendless.Persistence.save(learner, new AsyncCallback<Learner>() {
                    @Override
                    public void handleResponse(Learner learner) {
                        progressDialog.dismiss();
                        showToast("transaction has been recorded");
                        finishAndRemoveTask ();
                        
                    }

                    @Override
                    public void handleFault(BackendlessFault backendlessFault) {
                        progressDialog.dismiss();
                        showToast(backendlessFault.getMessage());


                    }
                });

            }

            @Override
            public void handleFault(BackendlessFault backendlessFault) {
                progressDialog.dismiss();
                showToast(backendlessFault.getMessage());

            }
        });

    }
    public void trialMethod(){
        progressDialog.setMessage("Transaction  is being recorded..");
        progressDialog.show();

        Backendless.Persistence.save(learnerTransaction, new AsyncCallback<LearnerTransaction>() {
            @Override
            public void handleResponse(LearnerTransaction learnerTransaction) {
                progressDialog.dismiss();
                showToast("saved");
                upDateBackendless();

            }

            @Override
            public void handleFault(BackendlessFault backendlessFault) {
                progressDialog.dismiss();
                showToast( backendlessFault.getMessage());


            }
        });

    }
    public void showToast(String msg) {
        View toastview = getLayoutInflater().inflate(R.layout.toast, (ViewGroup) findViewById(R.id.customLinear));
        TextView tvToast = (TextView) toastview.findViewById(R.id.tvToast);
        tvToast.setText(msg);

        Toast toast = new Toast(shopTransaction.this);
        toast.setDuration(toast.LENGTH_LONG);
        toast.setView(toastview);
        toast.setGravity(Gravity.BOTTOM | Gravity.FILL_HORIZONTAL, 0, 0);
        toast.show();

    }//end  showToast method


}