package com.example.payo.helperClasses.model;

import java.util.List;

public class SmsDetails {
    String smsText;
    List<String> tagsList;
    boolean income = false;
    boolean expense = false;

    public String getSmsText() {
        return smsText;
    }

    public void setSmsText(String smsText) {
        this.smsText = smsText;
    }

    public List<String> getTagsList() {
        return tagsList;
    }

    public void setTagsList(List<String> tagsList) {
        this.tagsList = tagsList;
    }

    public boolean isIncome() {
        return income;
    }

    public void setIncome(boolean income) {
        this.income = income;
    }

    public boolean isExpense() {
        return expense;
    }

    public void setExpense(boolean expense) {
        this.expense = expense;
    }
}
