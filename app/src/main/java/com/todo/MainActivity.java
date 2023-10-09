package com.todo;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Generate a random number between 1 and 100
        Random random = new Random();
        random.setSeed(System.currentTimeMillis());
        AtomicInteger randomNumber = new AtomicInteger(random.nextInt(100) + 1);
        // Initialize the attempts counter
        AtomicInteger counter = new AtomicInteger(7);
        // Screen Items
        TextView attempts = findViewById(R.id.attemptsCounter);
        TextView error = findViewById(R.id.errorView);
        EditText input = findViewById(R.id.decimalInput);
        Button guessButton = findViewById(R.id.guessButton);
        Button resetButton = findViewById(R.id.resetButton);
        // Initial Values
        error.setText("");
        attempts.setText(counter + " Attempts left");

        // Reset Button Functionality
        resetButton.setOnClickListener(v -> {
            input.setText("");
            counter.set(7);
            attempts.setText(counter + " Attempts left");
            error.setText("");
            randomNumber.set(random.nextInt(100) + 1);
        });

        // Guess Button Functionality
        guessButton.setOnClickListener(v -> {
            String inputText = input.getText().toString();
            if (inputText.equals("")) {
                error.setText("Please enter a number");
                return;
            }
            if (Integer.parseInt(inputText) == randomNumber.get()) {
                error.setText("You won! the number was " + randomNumber.get() + "");
                counter.set(7);
                randomNumber.set(random.nextInt(100) + 1);
                input.setText("");
            } else if (Integer.parseInt(inputText) > 100 || Integer.parseInt(inputText) < 1) {
                error.setText("Number should be between 1 and 100");
                input.setText("");
            } else if (Integer.parseInt(inputText) > randomNumber.get()) {
                error.setText("Your guess is too high");
                counter.set((counter.get() - 1 ));
                input.setText("");
            } else if (Integer.parseInt(inputText) < randomNumber.get()) {
                error.setText("Your guess is too low");
                counter.set((counter.get() - 1 ));
                input.setText("");
            }
            if (counter.get() == 0) {
                error.setText("You lost!, the number was " + randomNumber.get());
                counter.set(7);
                randomNumber.set(random.nextInt(100) + 1);
            }
            attempts.setText(counter + " Attempts left");
        });

        // Enter Key Functionality
        input.setOnEditorActionListener((v, actionId, event) -> {
            guessButton.performClick();
            return false;
        });
    }
}