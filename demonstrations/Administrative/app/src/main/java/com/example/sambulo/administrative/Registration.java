package com.example.sambulo.administrative;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.messaging.MessageStatus;
import com.backendless.messaging.PublishOptions;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

public class Registration extends AppCompatActivity {
    EditText etName, etRegEmail, etRegPassword, etRegPasswordConf, etUserSurname;
    ImageView profilePic;
    static int progress;
    Bitmap pictureTesting;

    String imageName;
    ProgressBar progressBar;
    Handler handler = new Handler();
    public String check;
    private Uri imageUri;
    ProgressDialog progressDialog;
    // final ProgressDialog progressDialog =new ProgressDialog(MainActivity.this);

    File photo;
    Date date;
    int num = 0;

    private static final int UNIQUE_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        ActionBar actionBar = getSupportActionBar();
        actionBar.show();
        progress = 0;
        progressBar = (ProgressBar) findViewById(R.id.progressbar);


        etName = (EditText) findViewById(R.id.etName);
        etRegEmail = (EditText) findViewById(R.id.etRegEmail);
        etRegPassword = (EditText) findViewById(R.id.etRegPassword);
        profilePic = (ImageView) findViewById(R.id.profilePic);
        etRegPasswordConf = (EditText) findViewById(R.id.etRegPasswordConf);
        etUserSurname = (EditText) findViewById(R.id.etUserSurname);
        pictureTesting = null;
        progressDialog = new ProgressDialog(Registration.this);


    }

    public void onClick_btnCapture(View view) {

        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        if ("profile " + num + ".jpg" == null) {
            showToast("Haibo");

        } else {
            num++;
            photo = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "profile " + num + ".jpg");
            imageUri = Uri.fromFile(photo);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(intent, UNIQUE_REQUEST_CODE);
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            Uri selectedImage = imageUri;
            getContentResolver().notifyChange(selectedImage, null);

            ContentResolver contentResolver = getContentResolver();
            Bitmap bitmap;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedImage);
                profilePic.setImageBitmap(bitmap);
                showToast(selectedImage.toString());

            } catch (Exception e) {
                showToast(e.getMessage());
            }

        }//end if
    }//end onActivityResult


    public void onClick_btnComplete(View view) {
        progressDialog.setMessage("loading data please wait..");
        progressDialog.show();

        if (etName.getText().toString().trim().isEmpty() || etRegEmail.getText().toString().trim().isEmpty() || etRegPassword.getText().toString().trim().isEmpty()
                || etRegPasswordConf.getText().toString().trim().isEmpty() || etUserSurname.getText().toString().trim().isEmpty()) {
            showToast("Enter all fields");
        } else if (!etRegPassword.getText().toString().trim().equals(etRegPasswordConf.getText().toString().trim())) {
            showToast("Make sure passwords match");
        } else {
            if (etRegPassword.getText().toString().trim().length() < 7) {
                showToast("password should be at least 7 characters long");

            } else {


                BackendlessUser newUser = new BackendlessUser();
                newUser.setProperty("name", etName.getText().toString().trim());
                newUser.setProperty("surname", etUserSurname.getText().toString().trim());
                newUser.setProperty("email", etRegEmail.getText().toString().trim());
                newUser.setPassword(etRegPassword.getText().toString().trim());

                Backendless.UserService.register(newUser, new AsyncCallback<BackendlessUser>() {
                    @Override
                    public void handleResponse(BackendlessUser backendlessUser) {
                        progressDialog.dismiss();

                        showToast(etName.getText().toString().trim() + " successfully saved \n" +
                                "please confirm your email before logging in...");
                        sendPush();
                        check = "success";
                        finishAndRemoveTask();
                    }

                    @Override
                    public void handleFault(BackendlessFault backendlessFault) {
                        progressDialog.dismiss();

                        if (backendlessFault.getCode() == "3033") {
                            showToast("User is already registered");
                            check = "User is already registered";
                        } else
                            showToast(backendlessFault.getMessage());

                    }
                });

            }//end else
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == UNIQUE_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // user clicks on allow
            } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(Registration.this, Manifest.permission.CAMERA)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage("").setTitle("");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            ActivityCompat.requestPermissions(Registration.this, new String[]{Manifest.permission.CAMERA}, UNIQUE_REQUEST_CODE);

                        }
                    });
                    builder.setNegativeButton("no Thanks", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.show();
                }//end if(ActivityCompat.shouldShowRequestPermissionRationale(Registration.this,Manifest.permission.CAMERA))
                else {
                    Toast.makeText(this, "my mum", Toast.LENGTH_LONG).show();
                    //user clicked on never ask
                }

            }//end else if(grantResults[0]== PackageManager.PERMISSION_DENIED)

        }//end outer if(requestCode==UNIQUE_REQUEST_CODE)

    }//end method onRequestPermissionsResult

    public void showToast(String msg) {
        View toastview = getLayoutInflater().inflate(R.layout.toast, (ViewGroup) findViewById(R.id.customLinear));
        TextView tvToast = (TextView) toastview.findViewById(R.id.tvToast);
        tvToast.setText(msg);

        Toast toast = new Toast(Registration.this);
        toast.setDuration(toast.LENGTH_LONG);
        toast.setView(toastview);
        toast.setGravity(Gravity.BOTTOM | Gravity.FILL_HORIZONTAL, 0, 0);
        toast.show();

    }

    private void sendPush() {
        String message = "Possible roles to be assigned:" +
                "# 1. Admin" +
                "# 2. Default " +
                "# 3. Master ";

        PublishOptions po = new PublishOptions();

        po.putHeader("android-ticker-text", "new registered user");
        po.putHeader("android-content-title", etRegEmail.getText().toString().trim() + " has registered");
        po.putHeader("android-content-text", "Kindly please assign a role to: " + etName.getText().toString().trim());

        Backendless.Messaging.publish("Master", message, po, new AsyncCallback<MessageStatus>() {
            @Override
            public void handleResponse(MessageStatus messageStatus) {

            }

            @Override
            public void handleFault(BackendlessFault backendlessFault) {
                showToast(backendlessFault.getMessage());
            }
        });
    }
}
