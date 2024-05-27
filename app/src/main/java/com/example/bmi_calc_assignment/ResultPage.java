package com.example.bmi_calc_assignment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.annotation.PluralsRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.DecimalFormat;
import java.util.Objects;

public class ResultPage extends AppCompatActivity {
    private TextView displayBmi;
    private TextView displayUserStatus;
    private double bmi, resultDependsOnGender;
    private String userStatus;
    private final String[] typesOfStatus = {"Underweight!", "Healthy!", "Overweight!", "Obesity!"};

    private double weight, height;

    private int age;
    // Decimal Format to Format the Weight
    private final DecimalFormat decimalFormatForWeight = new DecimalFormat("###.##");

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_result_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Assign the views
        displayBmi = findViewById(R.id.displayBmi);
        displayUserStatus = findViewById(R.id.displayStatus);

        // Retrieve the passed data from the Intent
        Intent intent = getIntent();
        String weightStr = intent.getStringExtra("weight");
        String heightStr = intent.getStringExtra("height");
        String ageStr = intent.getStringExtra("age");

        // Parse the data
        try {
            weight = Double.parseDouble(Objects.requireNonNull(weightStr));
            height = Double.parseDouble(Objects.requireNonNull(heightStr)) / 100;
            age = Integer.parseInt(Objects.requireNonNull(ageStr));

            // Perform the calculations
            bmi = weight / Math.pow(height, 2);

            // Determine the user status based on the bmi
            if (bmi < 18.5) {
                userStatus = typesOfStatus[0];
            } else if (bmi < 25) {
                userStatus = typesOfStatus[1];
            } else if (bmi < 30) {
                userStatus = typesOfStatus[2];
            } else {
                userStatus = typesOfStatus[3];
            }

            // Display the results
            displayBmi.setText(decimalFormatForWeight.format(bmi));
            displayUserStatus.setText(userStatus);

        } catch(NumberFormatException | NullPointerException e) {
            Log.e("ResultPage", "Error parsing intent data", e);
            displayBmi.setText("N/A");
            displayUserStatus.setText("Error calculating BMI, please try again.");
        }
    }
}