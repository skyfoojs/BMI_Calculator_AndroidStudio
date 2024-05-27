package com.example.bmi_calc_assignment;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ResultPage extends AppCompatActivity {
    // TextView to Display user BMI.
    private TextView displayBmi;
    // Text View to Display user Status.
    private TextView displayUserStatus;

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

        // Assigning values
        displayBmi = findViewById(R.id.displayBmi);
        displayUserStatus = findViewById(R.id.displayStatus);

        // Retrieve the data from the Intent
        double bmi = getIntent().getDoubleExtra("bmi", 0.0);
        String userStatus = getIntent().getStringExtra("userStatus");

        // Display the retrieved data
        displayBmi.setText(String.format("%.2f", bmi));
        displayUserStatus.setText(userStatus);
    }
}
