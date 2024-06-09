package com.example.bmi_calc_assignment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.DecimalFormat;
import java.util.Objects;

public class ResultPage extends AppCompatActivity {
    // Text View to display BMI, Status, Height, Weight and Age.
    private TextView displayBmi, displayUserStatus, displayUserHeight, displayUserWeight, displayUserAge;
    // Button to back to Calculate Page.
    private Button calculateAgainButton;
    // BMI of the user.
    private double bmi;
    // Status of the user.
    private String userStatus;
    // Array to store the type of Status.
    private final String[] typesOfStatus = {"Underweight!", "Healthy!", "Overweight!", "Obesity!"};
    // Double type of weight and height.
    private double weight, height;
    // Integer type of Age.
    private int age;
    // Decimal Format to Format the Weight
    private final DecimalFormat decimalFormatForWeight = new DecimalFormat("###.##");

    @SuppressLint("MissingInflatedId")
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
        displayUserHeight = findViewById(R.id.display_height);
        displayUserWeight = findViewById(R.id.display_weight);
        displayUserAge = findViewById(R.id.display_age);
        calculateAgainButton = findViewById(R.id.calculate_again);

        // Retrieve the passed data from the Intent
        Intent intent = getIntent();
        String weightStr = intent.getStringExtra("weight");
        String heightStr = intent.getStringExtra("height");
        String ageStr = intent.getStringExtra("age");

        try {
            // Parse the data
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
            displayUserHeight.setText(heightStr);
            displayUserWeight.setText(weightStr);
            displayUserAge.setText(ageStr);

        } catch(NumberFormatException | NullPointerException e) {
                displayBmi.setText("N/A");
                displayUserStatus.setText("Error calculating BMI, please try again.");
        }

        // Adds onClickListener to the button Calculate Again and brings user backs to the Calculate Page.
        calculateAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ResultPage.this, CalculatorPage.class));
            }
        });
    }
}