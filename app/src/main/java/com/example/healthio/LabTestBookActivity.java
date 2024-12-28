package com.example.healthio;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class LabTestBookActivity extends AppCompatActivity {
    EditText edname, edaddress, edcontact, edpincode;
    Button btnBooking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lab_test_book);

        edname = findViewById(R.id.editTextLTBFullName);
        edaddress = findViewById(R.id.editTextLTBAddress);
        edcontact = findViewById(R.id.editTextLTBContact);
        edpincode = findViewById(R.id.editTextLTBPincode);
        btnBooking = findViewById(R.id.buttonLTBBooking);

        Intent intent = getIntent();
        String priceExtra = intent.getStringExtra("price");
        String[] price = priceExtra != null ? priceExtra.split(java.util.regex.Pattern.quote(":")) : new String[]{};
        String date = intent.getStringExtra("date");
        String time = intent.getStringExtra("time");

        btnBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
                String username = sharedPreferences.getString("username", "");
                Database db = new Database(getApplicationContext(), "healthcare", null, 1);

                db.addOrder(username, edname.getText().toString(), edaddress.getText().toString(), edcontact.getText().toString(), Integer.parseInt(edpincode.getText().toString()), date, time, Float.parseFloat(price[1]), "lab");

                db.removeCart(username, "lab");

                Toast.makeText(getApplicationContext(), "Your Booking is done Successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LabTestBookActivity.this, HomeActivity.class));
            }
        });
    }
}
