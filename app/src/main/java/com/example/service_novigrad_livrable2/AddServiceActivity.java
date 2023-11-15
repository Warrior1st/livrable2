package com.example.service_novigrad_livrable2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddServiceActivity extends AppCompatActivity {

    // creating variables for our button, edit text,
    // firebase database, database reference, progress bar.
    private Button addCourseBtn;
    private TextInputEditText serviceNameEdt, servicePriceEdt, serviceSuccursalesEdt, serviceHoraireEdt, serviceExigenceEdt;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private ProgressBar loadingPB;
    private String courseID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_service);
        // initializing all our variables.
        addCourseBtn = findViewById(R.id.idBtnAddCourse);
        serviceNameEdt = findViewById(R.id.idEdtServiceName);
        servicePriceEdt = findViewById(R.id.idEdtServicePrice);
        serviceHoraireEdt = findViewById(R.id.idEdtServiceHoraire);
        serviceExigenceEdt = findViewById(R.id.idEdtServiceExigence);
        serviceSuccursalesEdt = findViewById(R.id.idEdtServiceSuccursales);
        loadingPB = findViewById(R.id.idPBLoading);
        firebaseDatabase = FirebaseDatabase.getInstance();
        // on below line creating our database reference.
        databaseReference = firebaseDatabase.getReference("Courses");
        // adding click listener for our add course button.
        addCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingPB.setVisibility(View.VISIBLE);
                // getting data from our edit text.
                String serviceName = serviceNameEdt.getText().toString();
                String servicePrice = servicePriceEdt.getText().toString();
                String serviceHoraire = serviceHoraireEdt.getText().toString();
                String serviceExigence = serviceExigenceEdt.getText().toString();
                String serviceSuccursale = serviceSuccursalesEdt.getText().toString();
                courseID = serviceName;
                // on below line we are passing all data to our modal class.
                ServiceModal serviceModal = new ServiceModal(courseID,serviceName,serviceSuccursale, serviceHoraire, servicePrice, serviceExigence);
                // on below line we are calling a add value event
                // to pass data to firebase database.
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        // on below line we are setting data in our firebase database.
                        databaseReference.child(courseID).setValue(serviceModal);
                        // displaying a toast message.
                        Toast.makeText(AddServiceActivity.this, "Course Added..", Toast.LENGTH_SHORT).show();
                        // starting a main activity.
                        startActivity(new Intent(AddServiceActivity.this, MainActivity.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // displaying a failure message on below line.
                        Toast.makeText(AddServiceActivity.this, "Fail to add Course..", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}