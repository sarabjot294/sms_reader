package com.example.payo.helperClasses;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

public class SmsReciver{

    private Context ctx;
    Cursor cursor;
    ProgressDialog progressDialog;


    public SmsReciver(Context context){
        this.ctx = context;

    }

    public void getSms()
    {
        showProgressDialog();

        Log.d("TAG", "getSms: is progress dialog showing " + progressDialog.isShowing());

        cursor = ctx.getContentResolver().query(Uri.parse("content://sms/inbox"), null, null, null, null);

       /* if (cursor.moveToFirst()) { // must check the result to prevent exception
            do {
                String msgData = "";
                for(int i=0;i<cursor.getColumnCount();i++)
                {
                    msgData += " " + cursor.getColumnName(i) + ":" + cursor.getString(i);
                    Log.d("TAG", "getSms: " + msgData + "\n");
                }

                // use msgData
            } while (cursor.moveToNext());
        }
        else
        {
            Log.e("TAG", "getSms: No SMS found");
        }*/

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        hideProgressDialog();

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
