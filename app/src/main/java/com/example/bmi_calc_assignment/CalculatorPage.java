package com.example.bmi_calc_assignment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.slider.Slider;

import java.text.DecimalFormat;

public class CalculatorPage extends AppCompatActivity {

    // Text View to Display User's Weight, Age and Height.
    private TextView userWeight, userAge, userHeight;
    // This will stores whether the user is Male or Female.
    private String usersGender;
    // Radio Group to set onCheckedChangeListener.
    private RadioGroup radioGroupOfUserGender;
    // Index to Calculate the BMI Result to Display.
    private int genderIndex;
    // The Age Slider.
    private Slider slider;
    // BMI is the formula to calculate, resultDependsOnGender is to calculate the result depends on the gender.
    public double bmi, resultDependsOnGender;
    // Add and Minus Image View to adjust the user's information.
    private ImageView buttonForAddWeight, buttonForMinusWeight, buttonForAddAge, buttonForMinusAge;
    // Button to Calculate the Result
    private Button calculateButton;
    // Weight status of user and the array of types of status.
    public String userStatus, typesOfStatus[];
    // Decimal Format to Format the Weight
    private DecimalFormat decimalFormatForWeight = new DecimalFormat("##.##");

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

    // Calculations to find out the BMI and Results.
    public void calculations() {
        bmi = Double.parseDouble(getUserWeight()) / Math.pow(Double.parseDouble(getUserHeight()), 2);
        resultDependsOnGender = 1.20 * bmi + (0.23 * Integer.parseInt(getUserAge()) - 5.4 - 10.8 * genderIndex);

        if(resultDependsOnGender < 18.5) {
            userStatus = typesOfStatus[0];
        } else if (resultDependsOnGender < 25) {
            userStatus = typesOfStatus[1];
        } else if (resultDependsOnGender < 30) {
            userStatus = typesOfStatus[2];
        } else {
            userStatus = typesOfStatus[3];
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
        radioGroupOfUserGender = (RadioGroup) findViewById(R.id.radioGroup);
        calculateButton = (Button) findViewById(R.id.calculate_button);
        typesOfStatus = new String[]{"Underweight!", "Healthy!", "Overweight!", "Obesity!"};

        // To Check user choose Male or Female
        radioGroupOfUserGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.maleRadioButton) {
                    usersGender = "Male";
                    genderIndex = 0;
                } else {
                    usersGender = "Female";
                    genderIndex = 1;
                }
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

        // Adds onClickListener to the button Calculate and brings user to the result page.
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Brings the calculations to ResultPage.class
                calculations();

                Intent intent = new Intent(CalculatorPage.this, ResultPage.class);

                // Put the BMI and user status into the Intent
                intent.putExtra("bmi", resultDependsOnGender);
                intent.putExtra("userStatus", userStatus);

                startActivity(intent);
            }
        });
    }
}
