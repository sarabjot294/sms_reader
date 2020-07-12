package com.example.payo.helperClasses;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.example.payo.helperClasses.model.SmsDetails;

import java.util.ArrayList;
import java.util.List;

public class SmsReciver{

    private Context ctx;
    Cursor cursor;
    ProgressDialog progressDialog;
    List<SmsDetails> detailsList = new ArrayList<>();


    public SmsReciver(Context context){
        this.ctx = context;

    }

    public List<SmsDetails> getSms()
    {
        cursor = ctx.getContentResolver().query(Uri.parse("content://sms/inbox"), null, null, null, null);

        if (cursor.moveToFirst()) { // must check the result to prevent exception
            do {
                String msgData = "";
                SmsDetails smsDetails = new SmsDetails();
                for(int i=0;i<cursor.getColumnCount();i++)
                {
                    msgData += " " + cursor.getColumnName(i) + ":" + cursor.getString(i);
                    if(cursor.getColumnName(i).equals("body")) {
                        smsDetails.setSmsText(cursor.getString(i));
                        detailsList.add(smsDetails);
                        Log.d("TAG", "getSms: added: " + cursor.getString(i));
                    }
                }
            } while (cursor.moveToNext());
        }
        else
        {
            Log.e("TAG", "getSms: No SMS found");
        }

        Log.d("TAG", "getSms: Size: " + detailsList.size());
        return detailsList;

    }

    void showProgressDialog()
    {
        try {
            progressDialog = new ProgressDialog(ctx);
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
}
