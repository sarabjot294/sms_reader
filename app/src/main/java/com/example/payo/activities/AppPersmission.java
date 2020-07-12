package com.example.payo.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.payo.MainActivity;
import com.example.payo.R;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class AppPersmission extends AppCompatActivity {
    Button appPermissionButton;
    private static final String TAG = "app_permission";
    boolean permissionGranted = false;
    String[] PERMISSIONS = {Manifest.permission.READ_SMS};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_permissions);
        appPermissionButton =findViewById(R.id.button);

        //To ask permission if not granted
        askPermission(AppPersmission.this, PERMISSIONS);

        //If permission graned, move to next activity
        if(permissionGranted)
            goToNextActivity(AppPersmission.this);

        //OnClick listenter to ask permissions again, if denied earlier
        appPermissionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPressClick();
            }
        });
    }

    private void onPressClick()
    {
        if(!permissionGranted) {
            appPermissionButton.setText("Click to provide App Permissions");
            Toast.makeText(AppPersmission.this, "Please provide All the Neccessary Permission for the app to work ", Toast.LENGTH_LONG).show();
            askPermission(AppPersmission.this, PERMISSIONS);
        }
        else
            goToNextActivity(AppPersmission.this);
    }

    private void goToNextActivity(Context context)
    {
        Intent intent = new Intent(context, AnalyseSMS.class);
        context.startActivity(intent);
        finish();
    }

    private void askPermission(final Context context, String[] PERMISSIONS) {
        for (String permission : PERMISSIONS) {
            if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                Log.e(TAG, "onRequestPermissionsResult: Permission : " + ActivityCompat.checkSelfPermission(context,permission));
                ActivityCompat.requestPermissions(AppPersmission.this, PERMISSIONS, 1);
                Log.e(TAG, "askPermission: We do not have permission for " + permission);
                return;
            } else {
                Log.d(TAG, "Permission granted for " + permission);
            }
        }
        permissionGranted = true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: requestCode: " + requestCode);

        for(int i=0;i<permissions.length;i++)
        {
            Log.e(TAG, "onRequestPermissionsResult: Permission : " + permissions[i] );
            Log.e(TAG, "onRequestPermissionsResult:value  : " + grantResults[i] );
            if(grantResults[i] == PackageManager.PERMISSION_DENIED)
            {
                appPermissionButton.setText("Click to provide App Permissions");
                return;
            }
        }

        Log.d(TAG, "onRequestPermissionsResult: All Permission Granted");
        appPermissionButton.setText("Proceed");
        permissionGranted = true;
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
