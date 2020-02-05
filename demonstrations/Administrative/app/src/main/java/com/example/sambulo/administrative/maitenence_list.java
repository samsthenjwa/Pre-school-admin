package com.example.sambulo.administrative;


import android.app.ProgressDialog;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class maitenence_list extends AppCompatActivity {
    ListView maintenenc_list;
    List<maintenance_report> maintenance;
    Spinner spinning;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maitenence_list);
        progressDialog = new ProgressDialog(maitenence_list.this);

        spinning = (Spinner) findViewById(R.id.spinning);
        maintenenc_list = (ListView) findViewById(R.id.maintenence_list);


        ArrayAdapter adaptercombo = ArrayAdapter.createFromResource(this, R.array.ListViewSelection, android.R.layout.simple_spinner_item);
        spinning.setAdapter(adaptercombo);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.send_pdf, menu);

        return super.onCreateOptionsMenu(menu);


    }

    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.pdfSend:
                createPDF();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        spinning.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String spinnar = parent.getItemAtPosition(position).toString();

                if (spinning.getSelectedItem().toString().equalsIgnoreCase("Select the reports you want to see..")) {
                    maintenenc_list.setVisibility(View.INVISIBLE);

                }
                switch (spinnar) {
                    case "Show all reports":
                        LoadData();
                        break;
                    case "Show resolved reports":
                        LoadQueryData("SOLVED");
                        break;
                    case "Show unresolved reports":
                        LoadQueryData("NOT RESOLVED");
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    public void LoadData() {

        maintenenc_list.setVisibility(View.VISIBLE);

        if (maintenance != null) {
            maintenance.clear();
        }
        progressDialog.setMessage("Loading selected reports..");
        progressDialog.show();


        Backendless.Persistence.of(maintenance_report.class).find(new AsyncCallback<List<maintenance_report>>() {
            @Override
            public void handleResponse(List<maintenance_report> maintenance_reports) {
                progressDialog.dismiss();
                maintenance = maintenance_reports;


                custom_maitenance custom = new custom_maitenance(maitenence_list.this, maintenance_reports);
                maintenenc_list.setAdapter(custom);
            }

            @Override
            public void handleFault(BackendlessFault backendlessFault) {
                progressDialog.dismiss();
                showToast(backendlessFault.getMessage());

            }
        });
    }

    public void LoadQueryData(String query) {
        progressDialog.setMessage("Loading selected reports..");
        progressDialog.show();

        maintenenc_list.setVisibility(View.VISIBLE);

        if (maintenance != null) {
            maintenance.clear();
        }

        String whereClause = "status = '" + query + "'";
        DataQueryBuilder queryBuilder = DataQueryBuilder.create();
        queryBuilder.setWhereClause(whereClause);
        queryBuilder.setPageSize(100).setOffset(0);


        Backendless.Persistence.of(maintenance_report.class).find(queryBuilder, new AsyncCallback<List<maintenance_report>>() {
            @Override
            public void handleResponse(List<maintenance_report> maintenance_reports) {
                progressDialog.dismiss();
                maintenance = maintenance_reports;
                custom_maitenance custom = new custom_maitenance(maitenence_list.this, maintenance);
                maintenenc_list.setAdapter(custom);

            }

            @Override
            public void handleFault(BackendlessFault backendlessFault) {
                progressDialog.dismiss();
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

            for (int j = 0; j < maintenance.size(); j++) {
                table.addCell(maintenance.get(j).getMaintitle());
                table.addCell(maintenance.get(j).getStatus());
                table.addCell(maintenance.get(j).getCreated());
                table.addCell(maintenance.get(j).getUpdated());
                table.addCell(maintenance.get(j).getDescription());
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

        Toast toast = new Toast(maitenence_list.this);
        toast.setDuration(toast.LENGTH_LONG);
        toast.setView(toastview);
        toast.setGravity(Gravity.BOTTOM | Gravity.FILL_HORIZONTAL, 0, 0);
        toast.show();

    }

    public boolean hideIcon(String text) {
        boolean key;

        if (text.equalsIgnoreCase("Select the reports you want to see..")) {
            key = false;
        } else {
            key = true;
        }
        invalidateOptionsMenu();
        return key;
    }


}
