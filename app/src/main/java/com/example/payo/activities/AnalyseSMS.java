package com.example.payo.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.payo.R;
import com.example.payo.helperClasses.IncomeAndExpenseAnalysis;
import com.example.payo.helperClasses.SmsAdapter;
import com.example.payo.helperClasses.SmsReciver;
import com.example.payo.helperClasses.TagsAnalysis;
import com.example.payo.helperClasses.model.SmsDetails;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnalyseSMS extends AppCompatActivity {

    RecyclerView mRecycler;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager layoutManager;
    Button barChartBtn, pieChartBtn, refreshSMS, tagSearchBtn;
    ProgressDialog progressDialog;
    EditText editText;
    List<SmsDetails> smsDetailsList = new ArrayList<>();
    boolean buttonClicked = false;
    boolean searchClicked = false;
    SmsAdapter smsAdapterAdapter;
    static Map<String, Integer>  barGraphData = new HashMap<>();
    static Map<String, Integer>  pieChartData = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analyse_s_m_s);
        mRecycler = findViewById(R.id.sms_recycler);
        barChartBtn = findViewById(R.id.barChartBtn);
        pieChartBtn = findViewById(R.id.pieChartBtn);
        tagSearchBtn = findViewById(R.id.tagSearchBtn);
        editText = findViewById(R.id.editText);
        refreshSMS = findViewById(R.id.refreshSMS);

        refreshSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshSmsBtn();
            }
        });

        barChartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                barChartBtn();
            }
        });

        tagSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tagSearch();
            }
        });

        pieChartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pieChartBtn();
            }
        });


    }

    public void tagSearch()
    {
        if(smsDetailsList.size() == 0 || editText.getText().toString().equals("") || editText.getText() == null)
        {
            Toast.makeText(AnalyseSMS.this, "Please enter a tag first", Toast.LENGTH_SHORT).show();
        }
        searchTag(editText.getText().toString().toLowerCase());
        showSmsList(AnalyseSMS.this, smsDetailsList);
    }

    public void barChartBtn()
    {
        if(smsDetailsList.size() == 0 || searchClicked)
        {
            Toast.makeText(AnalyseSMS.this,"Please click on refresh SMS first and enter Tags", Toast.LENGTH_SHORT).show();
            return;
        }
        TagsAnalysis tagsAnalysis = new TagsAnalysis(smsAdapterAdapter.getSmsTags(), smsAdapterAdapter.getSmsDetailsList());
        tagsAnalysis.calculateTags();
        barGraphData = tagsAnalysis.getBarGraphData();
        goToNextActivity(AnalyseSMS.this, TagBarChartActivity.class);
    }

    public void pieChartBtn()
    {
        if(smsDetailsList.size() == 0 || searchClicked)
        {
            Toast.makeText(AnalyseSMS.this,"Please click on refresh SMS first", Toast.LENGTH_SHORT).show();
            return;
        }
        IncomeAndExpenseAnalysis incomeAndExpenseAnalysis = new IncomeAndExpenseAnalysis(smsAdapterAdapter.getSmsDetailsList());
        incomeAndExpenseAnalysis.CalculateIncomeAndExpense();
        pieChartData = incomeAndExpenseAnalysis.getPieChartData();
        goToNextActivity(AnalyseSMS.this, PieCharts.class);
    }

    public void refreshSmsBtn()
    {
        Toast.makeText(AnalyseSMS.this," Please wait, fetching SMS...", Toast.LENGTH_SHORT).show();
        if(buttonClicked)
        {
            return;
        }
        buttonClicked = true;
        SmsReciver smsReciver = new SmsReciver(AnalyseSMS.this);
        smsDetailsList = smsReciver.getSms();
        showSmsList(AnalyseSMS.this, smsDetailsList);
        buttonClicked = false;
        searchClicked = false;
    }

    private void goToNextActivity(Context context, Class c)
    {
        Intent intent = new Intent(context,c);
        context.startActivity(intent);
    }

    public void showSmsList(Context context, List<SmsDetails> smsDetailsList) {
        mRecycler.setHasFixedSize(true);
        mRecycler.setNestedScrollingEnabled(false);
        layoutManager = new LinearLayoutManager(context);
        mRecycler.setLayoutManager(layoutManager);
        smsAdapterAdapter = new SmsAdapter(context, smsDetailsList);
        mAdapter = smsAdapterAdapter;
        mRecycler.setAdapter(mAdapter);
        mRecycler.setVisibility(View.VISIBLE);
    }

    public void searchTag(String tag)
    {
        List<SmsDetails> smsDetailsListSearched = new ArrayList<>();
        for(int i=0;i<smsDetailsList.size();i++)
        {
            SmsDetails smsDetails = smsDetailsList.get(i);
            if(smsDetails.getTagsList().size()>0)
            {
                List<String> tags = smsDetails.getTagsList();
                for(int j=0;j<tags.size();j++)
                {
                    if(tags.get(j).equals(tag))
                        smsDetailsListSearched.add(smsDetails);
                }
            }
        }

        smsDetailsList = smsDetailsListSearched;
    }

}
