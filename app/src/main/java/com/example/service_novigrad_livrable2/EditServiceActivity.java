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

import java.util.HashMap;
import java.util.Map;

public class EditServiceActivity extends AppCompatActivity {

    // creating variables for our edit text, firebase database,
    // database reference, course rv modal,progress bar.
    private TextInputEditText serviceNameEdt, servicePriceEdt, serviceExigenceEdt, serviceSuccursaleEdt, serviceHoraireEdt;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ServiceModal serviceModal;
    private ProgressBar loadingPB;
    // creating a string for our course id.
    private String serviceID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_service);
        // initializing all our variables on below line.
        Button addCourseBtn = findViewById(R.id.idBtnAddCourse);
        serviceNameEdt = findViewById(R.id.idEdtCourseName);
        servicePriceEdt = findViewById(R.id.idEdtCoursePrice);
        serviceSuccursaleEdt = findViewById(R.id.idEdtSuitedFor);
        serviceHoraireEdt = findViewById(R.id.idEdtCourseImageLink);
        serviceExigenceEdt = findViewById(R.id.idEdtCourseLink);
        loadingPB = findViewById(R.id.idPBLoading);
        firebaseDatabase = FirebaseDatabase.getInstance();
        // on below line we are getting our modal class on which we have passed.
        serviceModal = getIntent().getParcelableExtra("services");
        Button deleteCourseBtn = findViewById(R.id.idBtnDeleteCourse);

        if (serviceModal != null) {
            // on below line we are setting data to our edit text from our modal class.
            serviceNameEdt.setText(serviceModal.getServiceName());
            servicePriceEdt.setText(serviceModal.getServicePrice());
            //serviceSuccursaleEdt.setText(serviceModal.get);
            serviceHoraireEdt.setText(serviceModal.getHoraires());
            serviceExigenceEdt.setText(serviceModal.getExigences());
            //courseDescEdt.setText(courseRVModal.getCourseDescription());
            serviceID = serviceModal.getServiceId();
        }

        // on below line we are initializing our database reference and we are adding a child as our course id.
        databaseReference = firebaseDatabase.getReference("Services").child(serviceID);
        // on below line we are adding click listener for our add course button.
        addCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on below line we are making our progress bar as visible.
                loadingPB.setVisibility(View.VISIBLE);
                // on below line we are getting data from our edit text.
                String serviceName = serviceNameEdt.getText().toString();
                //String courseDesc = courseDescEdt.getText().toString();
                String servicePrice = servicePriceEdt.getText().toString();
                String serviceHoraire = serviceHoraireEdt.getText().toString();
                //String courseImg = serviceExigenceEdt.getText().toString();
                String serviceExigences = serviceExigenceEdt.getText().toString();
                // on below line we are creating a map for
                // passing a data using key and value pair.
                Map<String, Object> map = new HashMap<>();
                map.put("serviceName", serviceName);
                //map.put("courseDescription", serviceDesc);
                map.put("servicePrice", servicePrice);
                map.put("serviceExigence", serviceExigences);
                map.put("serviceHoraire", serviceHoraire);
                map.put("serviceExigence", serviceExigences);
                map.put("serviceId", serviceID);

                // on below line we are calling a database reference on
                // add value event listener and on data change method
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        // making progress bar visibility as gone.
                        loadingPB.setVisibility(View.GONE);
                        // adding a map to our database.
                        databaseReference.updateChildren(map);
                        // on below line we are displaying a toast message.
                        Toast.makeText(EditServiceActivity.this, "Course Updated..", Toast.LENGTH_SHORT).show();
                        // opening a new activity after updating our course.
                        startActivity(new Intent(EditServiceActivity.this, MainActivity.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // displaying a failure message on toast.
                        Toast.makeText(EditServiceActivity.this, "Fail to update course..", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        // adding a click listener for our delete course button.
        deleteCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calling a method to delete a course.
                deleteCourse();
            }
        });

    }

    private void deleteCourse() {
        // on below line calling a method to delete the course.
        databaseReference.removeValue();
        // displaying a toast message on below line.
        Toast.makeText(this, "Course Deleted..", Toast.LENGTH_SHORT).show();
        // opening a main activity on below line.
        startActivity(new Intent(EditServiceActivity.this, MainActivity.class));
    }
}