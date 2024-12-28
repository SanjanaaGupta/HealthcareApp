package com.example.healthio;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.LabTestActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class CartLabActivity extends AppCompatActivity {
    HashMap<String, String> item;
    ArrayList<HashMap<String, String>> list;
    SimpleAdapter sa;
    TextView tvTotal;
    ListView lst;
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    private Button dateButton, timeButton, btnCheckout, btnBack;
    private String[][] packages={};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_lab);

        dateButton = findViewById(R.id.buttonCartDate);
        timeButton = findViewById(R.id.buttonCartTime);
        btnCheckout = findViewById(R.id.buttonCartCheckout);
        btnBack = findViewById(R.id.buttonCartBack);
        tvTotal = findViewById(R.id.textViewCartTotalCost);
        lst = findViewById(R.id.listViewCart);

        SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");

        Database db = new Database(getApplicationContext(), "healthcare", null, 1);
        float totalAmount = 0;
        ArrayList<String> dbData = db.getCartData(username, "lab");

        if (dbData == null || dbData.isEmpty()) {
            Toast.makeText(this, "Your cart is empty!", Toast.LENGTH_SHORT).show();
            return;
        }
        // Initialize packages array correctly
        packages = new String[dbData.size()][];
        for (int i = 0; i < packages.length; i++) {
            packages[i] = new String[5];
        }


        for (int i = 0; i < dbData.size(); i++) {
            String arrData = dbData.get(i);  // Use dbData.get(i) directly
            String[] strData = arrData.split(java.util.regex.Pattern.quote("$"));
            if (strData.length > 1) {
                packages[i][0] = strData[0];  // Package Name
                packages[i][4] = "Cost: " + strData[1] + "/-";  // Price
                try {
                    totalAmount += Float.parseFloat(strData[1]);
                } catch (NumberFormatException e) {
                    Log.e("CartLabActivity", "Invalid cost value: " + strData[1]);
                }
            } else {
                Log.e("CartLabActivity", "Invalid data: " + arrData);
            }
        }

// Update the total cost
        tvTotal.setText("Total Cost: " + totalAmount);

// Populate the list view
        list = new ArrayList<>();
        for (int i = 0; i < packages.length; i++) {
            item = new HashMap<String, String>();
            item.put("line1", packages[i][0]);
            item.put("line2", packages[i][1]);
            item.put("line3", packages[i][2]);
            item.put("line4", packages[i][2]);
            item.put("line5", packages[i][4]);  // Only populate price as line 5
            list.add(item);
        }

// Notify the adapter to refresh the list
        sa = new SimpleAdapter(this, list,
                R.layout.multi_lines,
                new String[]{"line1","line2","line3","line4", "line5"},  // Adjust this if you need more fields
                new int[]{R.id.line_a,R.id.line_b,R.id.line_c,R.id.line_d,R.id.line_e});  // Ensure the layout ids match
        lst.setAdapter(sa);
        sa.notifyDataSetChanged();


        btnBack.setOnClickListener(v -> startActivity(new Intent(CartLabActivity.this, LabTestActivity.class)));

        initDatePicker();
        initTimePicker();

        dateButton.setOnClickListener(v -> datePickerDialog.show());
        timeButton.setOnClickListener(v -> timePickerDialog.show());

        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(CartLabActivity.this,LabTestBookActivity.class);
                it.putExtra("price",tvTotal.getText());
                it.putExtra("Date",dateButton.getText());
                it.putExtra("time",timeButton.getText());

            }
        });
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = (datePicker, year, month, day) -> {
            month += 1;
            dateButton.setText(day + "/" + month + "/" + year);
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(this, AlertDialog.THEME_HOLO_DARK, dateSetListener, year, month, day);
        datePickerDialog.getDatePicker().setMinDate(cal.getTimeInMillis() + 86400000);
    }

    private void initTimePicker() {
        TimePickerDialog.OnTimeSetListener timeSetListener = (timePicker, hour, minute) ->
                timeButton.setText(hour + ":" + String.format("%02d", minute));

        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);

        timePickerDialog = new TimePickerDialog(this, AlertDialog.THEME_HOLO_DARK, timeSetListener, hour, minute, true);
    }
}
