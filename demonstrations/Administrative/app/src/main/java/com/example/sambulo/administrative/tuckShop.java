package com.example.sambulo.administrative;


import android.os.AsyncTask;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class tuckShop extends AppCompatActivity {

    ListView listAll;
    List<Learner> learnersAll;
    CustomAdminShopAdapter adapter;
    SearchView filterSearchView;
    List<LearnerTransaction> learnerTransactionList;
    private PdfPCell cell;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tuck_shop);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setIcon(R.mipmap.pic);
        actionBar.setTitle(" Tuck shop");
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);


        listAll = (ListView) findViewById(R.id.list);
        filterSearchView = (SearchView) findViewById(R.id.filterSearchView);
        filterSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                adapter.getFilter().filter(newText);


                return false;
            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();
        LoadData();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.tuckshop_search, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {
            case R.id.mesage:
                createPDF();
                break;
            case R.id.bin:
                new DelelteTable().execute();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    public void LoadData() {
        if (learnersAll != null) {
            learnersAll.clear();
        }

        Backendless.Persistence.of(Learner.class).find(new AsyncCallback<List<Learner>>() {
            @Override
            public void handleResponse(List<Learner> list) {

                learnersAll = list;
                adapter = new CustomAdminShopAdapter(tuckShop.this, learnersAll);
                listAll.setAdapter(adapter);
            }

            @Override
            public void handleFault(BackendlessFault backendlessFault) {
                showToast(backendlessFault.getMessage());


            }
        });
    }

    private class DelelteTable extends AsyncTask<Void, Void, Exception> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //progress bar
        }

        @Override
        protected Exception doInBackground(Void... params) {
            HttpURLConnection urlConnection = null;
            Exception exception = null;
            try {
                URL url = new URL("https://api.backendless.com/53696F5E-2136-5C06-FFD9-CEE6A1D59C00/F57240F5-55E9-11DC-FF84-FC1F412F2C00/data/bulk/LearnerTransaction?pageSize=100&offset=0&where=objectId!%3Dnull");
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestProperty("application-id", "53696F5E-2136-5C06-FFD9-CEE6A1D59C00");
                urlConnection.setRequestProperty("secret-key", "F57240F5-55E9-11DC-FF84-FC1F412F2C00");
                urlConnection.setRequestProperty("application-type", "REST");
                urlConnection.setRequestMethod("DELETE");
                urlConnection.getInputStream();
                urlConnection.connect();

            } catch (MalformedURLException e) {
                exception = e;
            } catch (Exception e) {
                exception = e;

            } finally {
                urlConnection.disconnect();

            }
            return exception;
        }

        @Override
        protected void onPostExecute(Exception e) {
            super.onPostExecute(e);
            if (e != null) {
                showToast(e.getMessage());

            } else {
                showToast("Successfully deleted");

            }
        }
    }

    public void listTransactions() {

        Backendless.Persistence.of(LearnerTransaction.class).find(new AsyncCallback<List<LearnerTransaction>>() {
            @Override
            public void handleResponse(List<LearnerTransaction> learnerTransactions) {
                learnerTransactionList = learnerTransactions;

            }

            @Override
            public void handleFault(BackendlessFault backendlessFault) {
                showToast(backendlessFault.getMessage());

            }
        });
    }


    public void createPDF() {

        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        Document document = new Document();

        try {

            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/Maintenance.pdf");
            PdfWriter.getInstance(document, new FileOutputStream(file));


            //open
            document.open();

            Font f = new Font();
            f.setStyle(Font.BOLD);
            f.setSize(15);
            Paragraph p = new Paragraph("Maintenance Report  " + date, f);
            p.setAlignment(Element.ALIGN_LEFT);

            document.add(p);

            PdfPTable table = new PdfPTable(new float[]{2, 2, 2, 2, 2});
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell("Title");
            table.addCell("Status");
            table.addCell("Created");
            table.addCell("Updated");
            table.addCell("Report");
            table.setHeaderRows(1);
            PdfPCell[] cells = table.getRow(0).getCells();
            for (int j = 0; j < cells.length; j++) {
                cells[j].setBackgroundColor(BaseColor.GRAY);
            }

            for (int i = 1; i <= 1; i++) {
                table.addCell(String.valueOf(i));
                table.addCell(learnerTransactionList.get(i).getClassName());
                table.addCell(String.valueOf(learnerTransactionList.get(i).getTransaction()));
                table.addCell(String.valueOf(learnerTransactionList.get(i).getAfterTransaction()));
                table.addCell(String.valueOf(learnerTransactionList.get(i).getCurrentBalance()));
            }
            document.add(table);

            //close
            document.close();
            showToast("PDF CREATED");

        } catch (Exception ex) {
            ex.printStackTrace();
            showToast("PDF NOT CREATED!! /n" + ex.getMessage());
        }
    }

    public void showToast(String msg) {
        View toastview = getLayoutInflater().inflate(R.layout.toast, (ViewGroup) findViewById(R.id.customLinear));
        TextView tvToast = (TextView) toastview.findViewById(R.id.tvToast);
        tvToast.setText(msg);

        Toast toast = new Toast(tuckShop.this);
        toast.setDuration(toast.LENGTH_LONG);
        toast.setView(toastview);
        toast.setGravity(Gravity.BOTTOM | Gravity.FILL_HORIZONTAL, 0, 0);
        toast.show();

    }


}
