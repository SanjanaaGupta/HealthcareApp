package com.example.healthio;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    EditText edUsername, edPassword;
    Button btn;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize UI components
        edUsername = findViewById(R.id.editTextLoginUsername);
        edPassword = findViewById(R.id.editTextLoginPassword);
        btn = findViewById(R.id.buttonLogin);
        tv = findViewById(R.id.textViewNewUser);

        // Set onClickListener for login button
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = edUsername.getText().toString();
                String password = edPassword.getText().toString();

                // Check if fields are empty
                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please Enter All Details", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Initialize database and check login
                Database db = new Database(getApplicationContext(), "healthcare", null, 1);
                if (db.login(username, password) == 1) {
                    // Successful login
                    Toast.makeText(getApplicationContext(), "Login Success", Toast.LENGTH_SHORT).show();

                    // Save username in SharedPreferences
                    SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("username", username);
                    editor.apply();

                    // Navigate to HomeActivity
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                } else {
                    // Invalid credentials
                    Toast.makeText(getApplicationContext(), "Invalid Username and Password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Set onClickListener for new user TextView
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }
}
