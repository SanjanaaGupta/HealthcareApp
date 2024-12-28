package com.example.healthio;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class DoctorDetailsActivity extends AppCompatActivity {
    private String[][] doctor_details1 =
            {
                    {"Doctor Name : Dhiren Kothari", "Hospital Address : Mumbai", "Exp : 30yrs", "Mobile No.:9098675432", "600"},
                    {"Doctor Name : Sanjana Gupta", "Hospital Address : Thane", "Exp : 5yrs", "Mobile No.:9134658866", "900"},
                    {"Doctor Name : Mehek Gupta", "Hospital Address : Lonavla", "Exp : 8yrs", "Mobile No.:9045367897", "300"},
                    {"Doctor Name : Ritu Kori", "Hospital Address : Ranchi", "Exp : 6yrs", "Mobile No.:9124365478", "500"},
                    {"Doctor Name : Dipak Tahilramani", "Hospital Address : Akola", "Exp : 2yrs", "Mobile No.:7124765389", "800"}
            };
    private String[][] doctor_details2 =
            {
                    {"Doctor Name : Shila Shetty", "Hospital Address :Jaipur", "Exp : 10yrs", "Mobile No.:9098675632", "800"},
                    {"Doctor Name :Bhavna Parmar", "Hospital Address : Malwan", "Exp : 6yrs", "Mobile No.:9135658866", "500"},
                    {"Doctor Name : Ajay Singh", "Hospital Address : Dongri", "Exp : 9yrs", "Mobile No.:9045368697", "900"},
                    {"Doctor Name : Jeevanram Shetty", "Hospital Address : Dharavi", "Exp : 3yrs", "Mobile No.:9154365478", "1000"},
                    {"Doctor Name : Dipak Tahilramani", "Hospital Address : Alibaugh", "Exp : 7yrs", "Mobile No.:7124755389", "700"}
            };
    private String[][] doctor_details3 =
            {
                    {"Doctor Name : Hetal Jadeja", "Hospital Address : Amravati", "Exp : 3yrs", "Mobile No.:8998675432", "640"},
                    {"Doctor Name : Bhakti Bhavsar", "Hospital Address : Beed", "Exp : 7yrs", "Mobile No.:9784658866", "920"},
                    {"Doctor Name : Chetna Patel", "Hospital Address :Chandrapur", "Exp : 2yrs", "Mobile No.:9096367897", "400"},
                    {"Doctor Name : Namrata Singh", "Hospital Address : Hingoli", "Exp : 1yrs", "Mobile No.:9126565478", "540"},
                    {"Doctor Name : Laxmi Gupta", "Hospital Address : Kolhapur", "Exp : 0yrs", "Mobile No.:7904765389", "950"}
            };
    private String[][] doctor_details4 =
            {
                    {"Doctor Name : Jonita Gandhi", "Hospital Address : Jalgaon", "Exp : 35yrs", "Mobile No.:9598675432", "30000"},
                    {"Doctor Name : Asees Kaur", "Hospital Address :Latur", "Exp : 9yrs", "Mobile No.:9134658366", "9000"},
                    {"Doctor Name : Rajesh Lakhani ", "Hospital Address : Nagpur", "Exp : 20yrs", "Mobile No.:9045366897", "8900"},
                    {"Doctor Name : Kiara Adnani", "Hospital Address :Nanded", "Exp : 23yrs", "Mobile No.:9124165478", "4900"},
                    {"Doctor Name : Kriti Sanon", "Hospital Address : Pune", "Exp : 12yrs", "Mobile No.:7124760389", "5000"}
            };
    private String[][] doctor_details5 =
            {
                    {"Doctor Name : Neha Ali", "Hospital Address : Raigad", "Exp : 30yrs", "Mobile No.:9098575432", "6000"},
                    {"Doctor Name : Fatima Sheikh", "Hospital Address : Ratnagiri", "Exp : 15yrs", "Mobile No.:9124658866", "800"},
                    {"Doctor Name : Tina Shah", "Hospital Address : Sangli", "Exp : 7yrs", "Mobile No.:9045360897", "500"},
                    {"Doctor Name : Naman Lote", "Hospital Address : Solapur", "Exp : 9yrs", "Mobile No.:9124565478", "1200"},
                    {"Doctor Name : Anshika Pawar", "Hospital Address : Satara", "Exp : 4yrs", "Mobile No.:7124765489", "1100"}
            };

    TextView tv;
    Button btn;
    String[][] doctor_details = {};
    HashMap<String, String> item;
    ArrayList<HashMap<String, String>> list;
    SimpleAdapter sa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_doctor_details);

        tv = findViewById(R.id.textViewDDTitle);
        btn = findViewById(R.id.buttonDDBack);

        final Intent it = getIntent();  // Declare Intent as final
        final String title = it.getStringExtra("title");  // Declare title as final

        if (title == null) {
            tv.setText("Default Title");  // Default value in case title is not passed
        } else {
            tv.setText(title);
        }

        // Update doctor_details based on title
        if ("Family Physician".equals(title))
            doctor_details = doctor_details1;
        else if ("Dietician".equals(title))
            doctor_details = doctor_details2;
        else if ("Dentist".equals(title))
            doctor_details = doctor_details3;
        else if ("Surgeon".equals(title))
            doctor_details = doctor_details4;
        else
            doctor_details = doctor_details5;

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DoctorDetailsActivity.this, FindDoctorActivity.class));
            }
        });

        list = new ArrayList<>();
        for (int i = 0; i < doctor_details.length; i++) {
            item = new HashMap<>();
            item.put("line1", doctor_details[i][0]);
            item.put("line2", doctor_details[i][1]);
            item.put("line3", doctor_details[i][2]);
            item.put("line4", doctor_details[i][3]);
            item.put("line5", "Cons Fees:" + doctor_details[i][4] + "/-");
            list.add(item);
        }

        sa = new SimpleAdapter(this, list,
                R.layout.multi_lines,
                new String[]{"line1", "line2", "line3", "line4", "line5"},
                new int[]{R.id.line_a, R.id.line_b, R.id.line_c, R.id.line_d, R.id.line_e});

        ListView lst = findViewById(R.id.listViewDD);
        lst.setAdapter(sa);

        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent it = new Intent(DoctorDetailsActivity.this, BookAppointmentActivity.class);
                it.putExtra("text1", title);
                it.putExtra("text2", doctor_details[i][0]);
                it.putExtra("text3", doctor_details[i][1]);
                it.putExtra("text4", doctor_details[i][3]);
                it.putExtra("text5", doctor_details[i][4]);
                startActivity(it);
            }
        });
    }
}
