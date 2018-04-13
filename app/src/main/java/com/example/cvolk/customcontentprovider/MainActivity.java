package com.example.cvolk.customcontentprovider;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.example.cvolk.customcontentprovider.contracts.EmployeeContract;
import com.example.cvolk.customcontentprovider.model.Employee;
import com.example.cvolk.data.EmployeeFactory;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();
    public static final int REQUEST_CODE = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void saveData(View view) {
        try {
            TextView nameTextview = (TextView) findViewById(R.id.name);
            TextView ageTextview = (TextView) findViewById(R.id.age);
            TextView emailTextview = (TextView) findViewById(R.id.email);
            TextView phoneTextview = (TextView) findViewById(R.id.phone);

            if (TextUtils.isEmpty(nameTextview.getText())) {
                showMessage("Name cannot be ampty.!", false);
                return;
            }

            ContentValues values = new ContentValues();
            values.put(EmployeeContract.COLUMN_NAME, nameTextview.getText().toString().trim());
            values.put(EmployeeContract.COLUMN_AGE, ageTextview.getText().toString().trim());
            values.put(EmployeeContract.COLUMN_EMAIL, emailTextview.getText().toString().trim());
            values.put(EmployeeContract.COLUMN_PHONE, phoneTextview.getText().toString().trim());

            //ContentResolver will access the Employee Content Provider
            Uri newUri = getContentResolver().insert(EmployeeContract.EMPLOYEE_URI, values);

            String newUserId = newUri.getLastPathSegment();

            showMessage("Successfully saved. New User ID is " + newUserId, true);


        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        TextView nameTextview = (TextView) findViewById(R.id.name);
        TextView ageTextview = (TextView) findViewById(R.id.age);
        TextView emailTextview = (TextView) findViewById(R.id.email);
        TextView phoneTextview = (TextView) findViewById(R.id.phone);

        nameTextview.setText("");
        ageTextview.setText("");
        emailTextview.setText("");
        phoneTextview.setText("");
    }

    private void showMessage(String message, final Boolean successfullySaved) {
        try {
            AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
            alertDialog.setTitle("Message");
            alertDialog.setMessage(message);
            alertDialog.setCancelable(false);
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();

                            if (successfullySaved) {
                                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                                startActivityForResult(intent, REQUEST_CODE);
                            }
                        }
                    });
            alertDialog.show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void saveDataToDB(View view) {

        for (Employee employee : EmployeeFactory.createEmployees()) {
            ContentValues values = new ContentValues();
            values.put(EmployeeContract.COLUMN_ID, employee.getId());
            values.put(EmployeeContract.COLUMN_NAME, employee.getName());
            values.put(EmployeeContract.COLUMN_AGE, employee.getAge());
            values.put(EmployeeContract.COLUMN_EMAIL, employee.getEmail());
            values.put(EmployeeContract.COLUMN_PHONE, employee.getPhone());

            //ContentResolver will access the Employee Content Provider
            Uri newUri = getContentResolver().insert(EmployeeContract.EMPLOYEE_URI, values);
        }

    }
}
