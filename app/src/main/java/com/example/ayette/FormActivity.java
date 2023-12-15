package com.example.ayette;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;

public class FormActivity extends AppCompatActivity {

    private EditText editTextName;
    private EditText editTextAge;
    private EditText editTextInitialDate;
    private EditText editTextFinalDate;
    private EditText editTextBudget;

    private Calendar initialCalendar;
    private Calendar finalCalendar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        editTextName = findViewById(R.id.editTextName1);
        editTextAge = findViewById(R.id.editTextAge1);
        editTextInitialDate = findViewById(R.id.editTextInitialDate1);
        editTextFinalDate = findViewById(R.id.editTextFinalDate1);
        editTextBudget = findViewById(R.id.editTextBudget1);

        editTextInitialDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(true);
            }
        });

        editTextFinalDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(false);
            }
        });

        Button buttonSave = findViewById(R.id.buttonSave1);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString();
                String age = editTextAge.getText().toString();
                String initialDate = editTextInitialDate.getText().toString();
                String finalDate = editTextFinalDate.getText().toString();
                String budget = editTextBudget.getText().toString();

                FormDetails formDetails = new FormDetails(name, age, initialDate, finalDate, budget);

                Intent intent = new Intent();
                intent.putExtra("formDetails", formDetails);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        initialCalendar = Calendar.getInstance();
        finalCalendar = Calendar.getInstance();
    }

    private void showDatePickerDialog(final boolean isInitialDate) {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar selectedDate = Calendar.getInstance();
                selectedDate.set(Calendar.YEAR, year);
                selectedDate.set(Calendar.MONTH, month);
                selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                if (isInitialDate) {
                    initialCalendar = selectedDate;
                } else {
                    finalCalendar = selectedDate;
                }

                updateEditText(isInitialDate);
            }
        };

        Calendar calendar = isInitialDate ? initialCalendar : finalCalendar;
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    private void updateEditText(boolean isInitialDate) {
        EditText editText = isInitialDate ? editTextInitialDate : editTextFinalDate;
        Calendar calendar = isInitialDate ? initialCalendar : finalCalendar;

        String dateString = calendar.get(Calendar.DAY_OF_MONTH) + "/" +
                (calendar.get(Calendar.MONTH) + 1) + "/" +  // Month starts from 0
                calendar.get(Calendar.YEAR);

        editText.setText(dateString);
    }
}
