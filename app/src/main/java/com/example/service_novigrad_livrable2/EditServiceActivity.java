package com.example.service_novigrad_livrable2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class EditServiceActivity extends AppCompatActivity {

    //creating variables for our edit text, firebase database, database reference, Service rv modal,progress bar.
    private TextInputEditText serviceNameEdt, serviceSuccursaleEdt, servicePriceEdt, exigencesEdt, serviceImgEdt, serviceLinkEdt,serviceHoraireEdt;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ServiceModal serviceModal;
    private ProgressBar loadingPB;
    //creating a string for our Service id.
    private String serviceID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_service);
        //initializing all our variables on below line.
        Button addServiceBtn = findViewById(R.id.idBtnAddService);
        serviceNameEdt = findViewById(R.id.idEdtServiceName);
        serviceSuccursaleEdt = findViewById(R.id.idEdtServiceSuccursales);
        servicePriceEdt = findViewById(R.id.idEdtServicePrice);
        exigencesEdt = findViewById(R.id.idEdtServiceExigences);
        serviceImgEdt = findViewById(R.id.idEdtServiceImageLink);
        serviceLinkEdt = findViewById(R.id.idEdtServiceLink);
        serviceHoraireEdt= findViewById(R.id.idEdtServiceHoraire);
        loadingPB = findViewById(R.id.idPBLoading);
        firebaseDatabase = FirebaseDatabase.getInstance();
        //on below line we are getting our modal class on which we have passed.
        serviceModal = getIntent().getParcelableExtra("Service");
        Button deleteServiceBtn = findViewById(R.id.idBtnDeleteService);

        if (serviceModal != null) {
            //on below line we are setting data to our edit text from our modal class.
            serviceNameEdt.setText(serviceModal.getServiceName());
            exigencesEdt.setText(serviceModal.getExigences());
            serviceImgEdt.setText(serviceModal.getServiceImg());
            serviceLinkEdt.setText(serviceModal.getServiceLink());
            serviceSuccursaleEdt.setText(serviceModal.getSuccursales());
            serviceID = serviceModal.getServiceId();
            serviceHoraireEdt.setText(serviceModal.getHoraires());
        }

        //on below line we are initialing our database reference and we are adding a child as our Service id.
        databaseReference = firebaseDatabase.getReference("Services").child(serviceID);
        //on below line we are adding click listener for our add Service button.
        addServiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //on below line we are making our progress bar as visible.
                loadingPB.setVisibility(View.VISIBLE);
                //on below line we are getting data from our edit text.
                String serviceName = serviceNameEdt.getText().toString();
                String serviceSuccursale = serviceSuccursaleEdt.getText().toString();
                String servicePrice = servicePriceEdt.getText().toString();
                String exigences = exigencesEdt.getText().toString();
                String serviceImg = serviceImgEdt.getText().toString();
                String serviceLink = serviceLinkEdt.getText().toString();
                String serviceHoraire = serviceHoraireEdt.getText().toString();
                //on below line we are creating a map for passing a data using key and value pair.
                Map<String, Object> map = new HashMap<>();
                map.put("serviceName", serviceName);
                map.put("succursales", serviceSuccursale);
                map.put("servicePrice", servicePrice);
                map.put("exigences", exigences);
                map.put("serviceImg", serviceImg);
                map.put("serviceLink", serviceLink);
                map.put("serviceId", serviceID);
                map.put("horaires", serviceHoraire);

                //on below line we are calling a database reference on add value event listener and on data change method
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //making progress bar visibility as gone.
                        loadingPB.setVisibility(View.GONE);
                        //adding a map to our database.
                        databaseReference.updateChildren(map);
                        //on below line we are displaying a toast message.
                        Toast.makeText(EditServiceActivity.this, "Service Updated..", Toast.LENGTH_SHORT).show();
                        //opening a new activity after updating our coarse.
                        startActivity(new Intent(EditServiceActivity.this, MainActivity.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        //displaying a failure message on toast.
                        Toast.makeText(EditServiceActivity.this, "Fail to update Service..", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        //adding a click listener for our delete Service button.
        deleteServiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //calling a method to delete a Service.
                deleteService();
            }
        });

    }

    private void deleteService() {
        //on below line calling a method to delete the Service.
        databaseReference.removeValue();
        //displaying a toast message on below line.
        Toast.makeText(this, "Service Deleted..", Toast.LENGTH_SHORT).show();
        //opening a main activity on below line.
        startActivity(new Intent(EditServiceActivity.this, MainActivity.class));
    }
}