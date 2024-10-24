package com.videoplayer.videoplayer;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class AllowAccessActivity extends AppCompatActivity {

    public static final int STORAGE_PERMISSION = 1;
    public static final int REQUEST_PERMISSION_SETTING = 12;

    Button allow_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allow_access);

        allow_btn = findViewById(R.id.allow_access_btn);

        // Check if the user has already allowed access
        SharedPreferences preferences = getSharedPreferences("AllowAccess", MODE_PRIVATE);
        String value = preferences.getString("Allow", "");
        if (value.equals("OK")) {
            startActivity(new Intent(AllowAccessActivity.this, MainActivity.class));
            finish();
        } else {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("Allow", "OK");
            editor.apply();
        }

        // Button click listener
        allow_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                    // For Android 9 and below, request WRITE_EXTERNAL_STORAGE permission
                    if (ContextCompat.checkSelfPermission(getApplicationContext(),
                            Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        startActivity(new Intent(AllowAccessActivity.this, MainActivity.class));
                        finish();
                    } else {
                        ActivityCompat.requestPermissions(AllowAccessActivity.this,
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION);
                    }
                } else {
                    // For Android 10 and above, no need for WRITE_EXTERNAL_STORAGE permission
                    startActivity(new Intent(AllowAccessActivity.this, MainActivity.class));
                    finish();
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == STORAGE_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted
                startActivity(new Intent(AllowAccessActivity.this, MainActivity.class));
                finish();
            } else {
                // Permission denied
                boolean showRationale = ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if (!showRationale) {
                    // User checked "Don't ask again"
                    showSettingsAlert();
                } else {
                    Toast.makeText(this, "Storage permission is required to access files.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void showSettingsAlert() {
        // Alert dialog to open settings for permission
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("App Permission")
                .setMessage("For playing videos, you must allow the app to access storage. Please enable this in Settings.")
                .setPositiveButton("Open Settings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivityForResult(intent, REQUEST_PERMISSION_SETTING);
                    }
                }).create().show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                startActivity(new Intent(AllowAccessActivity.this, MainActivity.class));
                finish();
            }
        } else {
            // No need to check storage permission for Android 10 and above
            startActivity(new Intent(AllowAccessActivity.this, MainActivity.class));
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PERMISSION_SETTING) {
            // Check again if the permission is granted after returning from settings
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    startActivity(new Intent(AllowAccessActivity.this, MainActivity.class));
                    finish();
                }
            } else {
                startActivity(new Intent(AllowAccessActivity.this, MainActivity.class));
                finish();
            }
        }
    }
}
