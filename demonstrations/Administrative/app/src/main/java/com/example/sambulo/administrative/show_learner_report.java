package com.example.sambulo.administrative;

import android.app.ProgressDialog;
import android.os.Environment;
import android.support.v7.app.ActionBar;
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

public class show_learner_report extends AppCompatActivity {
    ListView learner_report;
    List<STUDENTS_REPORT> learner;
    Spinner spinning;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_learner_report);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setIcon(R.mipmap.pic);
        actionBar.setTitle(" Leaner Report");
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        progressDialog = new ProgressDialog(show_learner_report.this);

        spinning = (Spinner) findViewById(R.id.spinning);
        learner_report = (ListView) findViewById(R.id.learner_reaport);

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
                    learner_report.setVisibility(View.INVISIBLE);

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
        learner_report.setVisibility(View.VISIBLE);
        if (learner != null) {
            learner.clear();
        }
        progressDialog.setMessage("Loading selected reports..");
        progressDialog.show();
        Backendless.Persistence.of(STUDENTS_REPORT.class).find(new AsyncCallback<List<STUDENTS_REPORT>>() {
            @Override
            public void handleResponse(List<STUDENTS_REPORT> learner_reports) {
                progressDialog.dismiss();
                learner = learner_reports;

                custom_student_list custom = new custom_student_list(show_learner_report.this, learner);
                learner_report.setAdapter(custom);
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

            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/Student Report.pdf");
            PdfWriter.getInstance(document, new FileOutputStream(file));


            //open
            document.open();

            Font f = new Font();
            f.setStyle(Font.BOLD);
            f.setSize(15);
            Paragraph p = new Paragraph("Student Report  " + date, f);
            p.setAlignment(Element.ALIGN_LEFT);

            document.add(p);

            PdfPTable table = new PdfPTable(new float[]{2, 2, 2, 2, 2});
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell("Class Name");
            table.addCell("Title");
            table.addCell("Recomendation");
            table.addCell("Report");
            table.addCell("Student name");
            table.setHeaderRows(1);
            PdfPCell[] cells = table.getRow(0).getCells();
            for (int j = 0; j < cells.length; j++) {
                cells[j].setBackgroundColor(BaseColor.GRAY);
            }

            for (int j = 0; j < learner.size(); j++) {
                table.addCell(learner.get(j).getClassName());
                table.addCell(learner.get(j).getTitle());
                table.addCell(learner.get(j).getRecomendation());
                table.addCell(learner.get(j).getReports());
                table.addCell(learner.get(j).getNames());
            }
            document.add(table);


            //close
            document.close();

            showToast("pdf successfully created");


        } catch (Exception ex) {
            ex.printStackTrace();
            showToast("PDF NOT CREATED!! /n" + ex.getMessage());

        }

    }

    public void showToast(String msg) {
        View toastview = getLayoutInflater().inflate(R.layout.toast, (ViewGroup) findViewById(R.id.customLinear));
        TextView tvToast = (TextView) toastview.findViewById(R.id.tvToast);
        tvToast.setText(msg);

        Toast toast = new Toast(show_learner_report.this);
        toast.setDuration(toast.LENGTH_LONG);
        toast.setView(toastview);
        toast.setGravity(Gravity.BOTTOM | Gravity.FILL_HORIZONTAL, 0, 0);
        toast.show();

    }

    public void LoadQueryData(String query) {
        progressDialog.setMessage("Loading selected reports..");
        progressDialog.show();

        learner_report.setVisibility(View.VISIBLE);

        if (learner != null) {
            learner.clear();
        }

        String whereClause = "status = '" + query + "'";
        DataQueryBuilder queryBuilder = DataQueryBuilder.create();
        queryBuilder.setWhereClause(whereClause);
        queryBuilder.setPageSize(100).setOffset(0);


        Backendless.Persistence.of(STUDENTS_REPORT.class).find(queryBuilder, new AsyncCallback<List<STUDENTS_REPORT>>() {
            @Override
            public void handleResponse(List<STUDENTS_REPORT> students_reports) {
                progressDialog.dismiss();
                learner = students_reports;
                custom_student_list custom = new custom_student_list(show_learner_report.this, learner);
                learner_report.setAdapter(custom);
            }

            @Override
            public void handleFault(BackendlessFault backendlessFault) {
                progressDialog.dismiss();
                showToast(backendlessFault.getMessage());

            }
        });

    }

}
