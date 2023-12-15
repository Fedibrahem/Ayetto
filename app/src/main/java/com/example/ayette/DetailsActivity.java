package com.example.ayette;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class DetailsActivity extends AppCompatActivity {

    private EditText editTextName;
    private EditText editTextAge;
    private EditText editTextInitialDate;
    private EditText editTextFinalDate;
    private EditText editTextBudget;

    private int itemPosition;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        editTextName = findViewById(R.id.editTextName);
        editTextAge = findViewById(R.id.editTextAge);
        editTextInitialDate = findViewById(R.id.editTextInitialDate);
        editTextFinalDate = findViewById(R.id.editTextFinalDate);
        editTextBudget = findViewById(R.id.editTextBudget);

        Button buttonSaveDetails = findViewById(R.id.buttonSaveDetails);
        buttonSaveDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString();
                String age = editTextAge.getText().toString();
                String initialDate = editTextInitialDate.getText().toString();
                String finalDate = editTextFinalDate.getText().toString();
                String budget = editTextBudget.getText().toString();

                FormDetails updatedFormDetails = new FormDetails(name, age, initialDate, finalDate, budget);

                Intent resultIntent = new Intent();
                resultIntent.putExtra("updatedFormDetails", updatedFormDetails);
                resultIntent.putExtra("position", itemPosition);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("formDetails") && intent.hasExtra("position")) {
            FormDetails formDetails = (FormDetails) intent.getSerializableExtra("formDetails");
            itemPosition = intent.getIntExtra("position", -1);

            editTextName.setText(formDetails.getName());
            editTextAge.setText(formDetails.getAge());
            editTextInitialDate.setText(formDetails.getInitialDate());
            editTextFinalDate.setText(formDetails.getFinalDate());
            editTextBudget.setText(formDetails.getBudget());
        }
    }
}
