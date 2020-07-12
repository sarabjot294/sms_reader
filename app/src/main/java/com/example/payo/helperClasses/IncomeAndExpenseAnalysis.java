package com.example.payo.helperClasses;

import android.util.Log;

import com.example.payo.helperClasses.model.SmsDetails;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IncomeAndExpenseAnalysis {
    List<SmsDetails> smsDetailsList = new ArrayList<>();
    Map<String, Integer> pieChartData  = new HashMap<>();


    public Map<String, Integer> getPieChartData() {
        return pieChartData;
    }

    public IncomeAndExpenseAnalysis(List<SmsDetails> smsDetailsList) {
        this.smsDetailsList = smsDetailsList;
    }

    public void CalculateIncomeAndExpense()
    {
        setUpPieData();
        for(int i=0;i<smsDetailsList.size();i++) {
            SmsDetails smsDetails = smsDetailsList.get(i);
            String smsText = smsDetails.getSmsText().toLowerCase();
            boolean search = true;
            if(smsText.contains("credit") || smsText.contains("credited"))
                smsDetailsList.get(i).setIncome(true);
            else if(smsText.contains("debit") || smsText.contains("debited"))
                smsDetailsList.get(i).setExpense(true);
            else{
                search = false;
            }
            if(!search)
                continue;
            Pattern pattern = Pattern.compile("(?i)(?:(?:RS|INR|MRP)\\.?\\s?)(\\d+(:?\\,\\d+)?(\\,\\d+)?(\\.\\d{1,2})?)");

            // Find instance of pattern matches
            Matcher m = pattern.matcher(smsText);
            if (m.find()) {
                try {
                    Log.d("TAG", "CalculateIncomeAndExpense: Message " + smsText);
                    Log.e("TAG", "" + m.group(0));
                    String amount = (m.group(0).replaceAll("inr", ""));
                    amount = amount.replaceAll("rs", "");
                    amount = amount.replaceAll("inr", "");
                    amount = amount.replaceAll(" ", "");
                    amount = amount.replaceAll(",", "");
                    Log.d("TAG", "CalculateIncomeAndExpense: value of amount : " + amount);
                    Double value = Double.valueOf(amount);
                    if(smsDetails.isExpense())
                        {
                            int expense = pieChartData.get("Expense");
                            expense += Math.round(value);
                            pieChartData.remove("Expense");
                            pieChartData.put("Expense",expense);
                        }
                        else if(smsDetails.isIncome())
                    {
                        int income = pieChartData.get("Income");
                        income += Math.round(value);
                        pieChartData.remove("Income");
                        pieChartData.put("Income",income);
                    }
                }
                catch (Exception e)
                {  Log.e("TAG", "ERROR " + e.toString());
                }
            }
        }
    }

    void setUpPieData()
    {
        pieChartData.put("Income",0);
        pieChartData.put("Expense",0);
    }
}
