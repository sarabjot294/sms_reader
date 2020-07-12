package com.example.payo.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.payo.R;
import com.example.payo.helperClasses.SmsAdapter;
import com.example.payo.helperClasses.SmsReciver;

import java.util.List;

public class AnalyseSMS extends AppCompatActivity {

    RecyclerView mRecycler;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager layoutManager;
    Button aalyseSms;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analyse_s_m_s);
        mRecycler = findViewById(R.id.sms_recycler);
        aalyseSms = findViewById(R.id.analyseSMS);
        showSmsList(AnalyseSMS.this);
        aalyseSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SmsReciver smsReciver = new SmsReciver(AnalyseSMS.this);
                //showProgressDialog();
                smsReciver.getSms();
                //hideProgressDialog();
            }
        });
    }

    void showProgressDialog()
    {
        try {
            progressDialog = new ProgressDialog(AnalyseSMS.this);
            progressDialog.setTitle("Fetching Data");
            progressDialog.setMessage("Please Wait");
            progressDialog.show();
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            Log.d("TAG", "hideProgressDialog: Progress dialogue Shown");
        }
        catch (Exception e) {
            Log.e("TAG", "showProgressDialog: Error Showing Progress dialoge");
        }

    }

    void hideProgressDialog() {
        try {
            if (progressDialog == null)
                return;
            progressDialog.hide();
            progressDialog = null;
            Log.d("TAG", "hideProgressDialog: Progress dialogue hidden");
        } catch (Exception e) {
            Log.e("TAG", "Exception unable to get progressDialog");
        }
    }

    public void showSmsList(Context context) {
        mRecycler.setHasFixedSize(true);
        mRecycler.setNestedScrollingEnabled(false);
        layoutManager = new LinearLayoutManager(context);
        mRecycler.setLayoutManager(layoutManager);
        mAdapter = new SmsAdapter();
        mRecycler.setAdapter(mAdapter);
        mRecycler.setVisibility(View.VISIBLE);
    }

}
