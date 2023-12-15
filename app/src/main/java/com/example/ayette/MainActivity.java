package com.example.ayette;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;
import android.widget.AdapterView;

public class MainActivity extends AppCompatActivity {

    private List<FormDetails> formDetailsList;
    private FormDetailsAdapter formDetailsAdapter;

    private static final int DETAILS_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        formDetailsList = new ArrayList<>();
        formDetailsAdapter = new FormDetailsAdapter(this, formDetailsList);

        ListView listViewItems = findViewById(R.id.listViewItems);
        listViewItems.setAdapter(formDetailsAdapter);

        // Click listener for item clicks in the list view
        listViewItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FormDetails selectedFormDetails = formDetailsList.get(position);

                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                intent.putExtra("formDetails", selectedFormDetails);
                intent.putExtra("position", position);
                startActivityForResult(intent, DETAILS_REQUEST_CODE);
            }
        });

        Button buttonHiStarting = findViewById(R.id.buttonHiStarting);
        buttonHiStarting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FormActivity.class);
                startActivityForResult(intent, DETAILS_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == DETAILS_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            FormDetails updatedFormDetails = (FormDetails) data.getSerializableExtra("updatedFormDetails");
            int position = data.getIntExtra("position", -1);

            if (position != -1 && updatedFormDetails != null) {
                formDetailsList.set(position, updatedFormDetails);
                formDetailsAdapter.notifyDataSetChanged();
            } else {
                FormDetails formDetails = (FormDetails) data.getSerializableExtra("formDetails");
                if (formDetails != null) {
                    formDetailsList.add(formDetails);
                    formDetailsAdapter.notifyDataSetChanged();
                }
            }
        }
    }
}
