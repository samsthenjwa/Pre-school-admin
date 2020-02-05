package com.example.sambulo.administrative;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


public class InCase_of_Emergency extends AppCompatActivity {

    TextView Police, Fire, Ambulance, Child_support, Emergency_line, p10111, p0573252222, f08000555, a082162,
            c084124;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_case_of__emergency);


        Police = (TextView) findViewById(R.id.Police);
        Fire = (TextView) findViewById(R.id.Fire);
        Ambulance = (TextView) findViewById(R.id.Ambulance);
        Child_support = (TextView) findViewById(R.id.Child_support);
        Emergency_line = (TextView) findViewById(R.id.Emergency_line);

        p10111 = (TextView) findViewById(R.id.polic);
        p0573252222 = (TextView) findViewById(R.id.fire);
        f08000555 = (TextView) findViewById(R.id.child);
        a082162 = (TextView) findViewById(R.id.ambulanc);
        c084124 = (TextView) findViewById(R.id.er24);


        Police.setText(R.string.police);
        Fire.setText(R.string.fire);
        Ambulance.setText(R.string.ambulance);
        Child_support.setText(R.string.child);
        Emergency_line.setText(R.string.emergency);

        p10111.setText("10 111");
        p0573252222.setText("057 325 2222");
        f08000555.setText("10777");
        a082162.setText("0800 055 555");
        c084124.setText("112");

        p10111.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_CALL);
                i.setData(Uri.parse("tel:" + p10111.getText().toString().trim()));

                if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CALL_PHONE) !=
                        PackageManager.PERMISSION_GRANTED) {
                    showToast("Please Grant Permission");

                    requestPermission();
                } else
                    startActivity(i);
            }
        });

        p0573252222.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_CALL);
                i.setData(Uri.parse("tel:" + p0573252222.getText().toString()));

                if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CALL_PHONE) !=
                        PackageManager.PERMISSION_GRANTED) {
                    showToast("Please Grant Permission");
                    requestPermission();
                } else
                    startActivity(i);
            }
        });

        f08000555.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_CALL);
                i.setData(Uri.parse("tel:" + f08000555.getText().toString()));

                if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CALL_PHONE) !=
                        PackageManager.PERMISSION_GRANTED) {
                    showToast("Please Grant Permission");
                    requestPermission();
                } else
                    startActivity(i);
            }
        });

        a082162.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_CALL);
                i.setData(Uri.parse("tel:" + a082162.getText().toString()));

                if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CALL_PHONE) !=
                        PackageManager.PERMISSION_GRANTED) {
                    showToast("Please Grant Permission");
                    requestPermission();
                } else
                    startActivity(i);
            }
        });

        c084124.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_CALL);
                i.setData(Uri.parse("tel:" + c084124.getText().toString()));

                if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CALL_PHONE) !=
                        PackageManager.PERMISSION_GRANTED) {
                    showToast("Please Grant Permission");
                    requestPermission();
                } else
                    startActivity(i);
            }
        });

    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CALL_PHONE}, 1);
    }

    public void showToast(String msg) {
        View toastview = getLayoutInflater().inflate(R.layout.toast, (ViewGroup) findViewById(R.id.customLinear));
        TextView tvToast = (TextView) toastview.findViewById(R.id.tvToast);
        tvToast.setText(msg);

        Toast toast = new Toast(InCase_of_Emergency.this);
        toast.setDuration(toast.LENGTH_LONG);
        toast.setView(toastview);
        toast.setGravity(Gravity.BOTTOM | Gravity.FILL_HORIZONTAL, 0, 0);
        toast.show();

    }
}
