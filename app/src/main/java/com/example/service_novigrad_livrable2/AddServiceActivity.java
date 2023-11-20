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

    //creating variables for our button, edit text,firebase database, database refrence, progress bar.
    private Button addServiceBtn;
    private TextInputEditText ServiceNameEdt, formulaireEdt, documentsEdt;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private ProgressBar loadingPB;
    private String serviceID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_service);
        //initializing all our variables.
        addServiceBtn = findViewById(R.id.idBtnAddService);
        ServiceNameEdt = findViewById(R.id.idEdtServiceName);
        documentsEdt = findViewById(R.id.idEdtServiceDocuments);
        formulaireEdt = findViewById(R.id.idEdtServiceFormulaire);

        loadingPB = findViewById(R.id.idPBLoading);
        firebaseDatabase = FirebaseDatabase.getInstance();
        //on below line creating our database reference.
        databaseReference = firebaseDatabase.getReference("Services");
        //adding click listener for our add Service button.
        addServiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingPB.setVisibility(View.VISIBLE);
                //getting data from our edit text.
                String serviceName = ServiceNameEdt.getText().toString();
                String documents = documentsEdt.getText().toString();
                String formulaire = formulaireEdt.getText().toString();
                serviceID = serviceName;
                //on below line we are passing all data to our modal class.
                ServiceModal serviceModal = new ServiceModal(serviceID, serviceName, formulaire, documents);
                //on below line we are calling a add value event to pass data to firebase database.
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //on below line we are setting data in our firebase database.
                        databaseReference.child(serviceID).setValue(serviceModal);
                        //displaying a toast message.
                        Toast.makeText(AddServiceActivity.this, "Service Added..", Toast.LENGTH_SHORT).show();
                        //starting a main activity.
                        startActivity(new Intent(AddServiceActivity.this, MainActivity.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        //displaying a failure message on below line.
                        Toast.makeText(AddServiceActivity.this, "Fail to add Service..", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
}