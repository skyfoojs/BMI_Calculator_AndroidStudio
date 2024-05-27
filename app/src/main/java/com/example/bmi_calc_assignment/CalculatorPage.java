package com.example.bmi_calc_assignment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.slider.Slider;

import java.text.DecimalFormat;

public class CalculatorPage extends AppCompatActivity {

    // Text View to Display User's Weight, Age and Height.
    private TextView userWeight, userAge, userHeight;
    // To check whether user is male or female.
    private CardView male, female;
    // To store user's gender.
    private String gender = "";
    // The Age Slider.
    private Slider slider;
    // Add and Minus Image View to adjust the user's information.
    private ImageView buttonForAddWeight, buttonForMinusWeight, buttonForAddAge, buttonForMinusAge;
    // Button to Calculate the Result
    private Button calculateButton;

    // Decimal Format to Format the Weight
    private final DecimalFormat decimalFormatForWeight = new DecimalFormat("###.##");

    // Get the user Height from Text View.
    public String getUserHeight() {
        return userHeight.getText().toString();
    }
    // Get the user Weight from Text View.
    public String getUserWeight() {
        return userWeight.getText().toString();
    }

    // Get the user Age from Text View
    public String getUserAge() {
        return userAge.getText().toString();
    }

    // Sets the User Height
    public void setUserHeight(String height) {
        userHeight.setText(height);
    }

    // Sets the User Weight
    public void setUserWeight(String weight) {
        userWeight.setText(weight);
    }

    // Sets the user Age
    public void setUserAge(String age) {
        userAge.setText(age);
    }

    // When user presses the Add button on Weight, the Weight will not exceed 200.
    public void addWeight() {
        double currentWeight = Double.parseDouble(getUserWeight());
        if (currentWeight < 200) {
            double newUserWeight = currentWeight + 0.1;
            setUserWeight(decimalFormatForWeight.format(newUserWeight));
        } else {
            setUserWeight("200");
        }
    }

    // When user presses the Minus button on Weight, the weight will not falls below 20.
    public void minusWeight() {
        double currentWeight = Double.parseDouble(getUserWeight());
        if (currentWeight > 20) {
            double newUserWeight = currentWeight - 0.1;
            setUserWeight(decimalFormatForWeight.format(newUserWeight));
        } else {
            setUserWeight("20");
        }
    }

    // When user presses the Add button on Age, the age will not exceed 100.
    public void addAge() {
        int currentAge = Integer.parseInt(getUserAge());
        if (currentAge < 100) {
            int newUserAge = currentAge + 1;
            setUserAge(String.valueOf(newUserAge));
        } else {
            setUserAge("100");
        }
    }

    // When user presses the Minus button on Age, the age will not falls below 1.
    public void minusAge() {
        int currentAge = Integer.parseInt(getUserAge());
        if (currentAge > 1) {
            int newUserAge = currentAge - 1;
            setUserAge(String.valueOf(newUserAge));
        } else {
            setUserAge("1");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_calculator_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Assigning Values
        slider = findViewById(R.id.slider);
        buttonForAddWeight = findViewById(R.id.plusSignForWeight);
        buttonForMinusWeight = findViewById(R.id.minusSignForWeight);
        buttonForAddAge = findViewById(R.id.plusSignForAge);
        buttonForMinusAge = findViewById(R.id.minusSignForAge);
        userHeight = findViewById(R.id.sliderHeightText);
        userWeight = findViewById(R.id.user_weight);
        userAge = findViewById(R.id.user_age);
        calculateButton = (Button) findViewById(R.id.calculate_button);
        male = findViewById(R.id.maleCard);
        female = findViewById(R.id.femaleCard);

        // To Check if the user choose Male
        male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                male.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.male_female_selected));
                gender = "Male";
                female.setBackground(null);
            }
        });

        // To Check if the user choose Female
        female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                female.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.male_female_selected));
                gender = "Female";
                male.setBackground(null);
            }
        });

        // To change the value if the slider moves
        slider.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                DecimalFormat df = new DecimalFormat("###.#");
                setUserHeight(df.format(value));
            }
        });

        // Calls the addWeight() method when user clicks the add button.
        buttonForAddWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addWeight();
            }
        });

        // Calls the minusWeight() method when user clicks the minus button.
        buttonForMinusWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                minusWeight();
            }
        });

        // Calls the addAge() method when user clicks the add button.
        buttonForAddAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAge();
            }
        });

        // Calls the minusAge() method when user clicks the minus button.
        buttonForMinusAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                minusAge();
            }
        });

        /* Adds onClickListener to the button Calculate and brings user to the result page.
         * putExtra() will bring the extra data that needed and pass it to the intent object*/
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(gender.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please Select a Gender", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(CalculatorPage.this, ResultPage.class);
                    intent.putExtra("weight", getUserWeight());
                    intent.putExtra("height", getUserHeight());
                    intent.putExtra("age", getUserAge());
                    intent.putExtra("gender", gender);
                    startActivity(intent);
                }
            }
        });

    }
}
