package com.example.service_novigrad_livrable2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ServiceAdapter.ServiceClickInterface {

    //creating variables for fab, firebase database, progress bar, list, adapter,firebase auth, recycler view and relative layout.
    private FloatingActionButton addServiceFAB;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private RecyclerView service;
    private FirebaseAuth mAuth;
    private ProgressBar loadingPB;
    private ArrayList<ServiceModal> serviceModalArrayList;
    private ServiceAdapter serviceAdapter;
    private RelativeLayout homeRL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //initializing all our variables.
        service = findViewById(R.id.idRVServices);
        //homeRL = findViewById(R.id.idRLBSheet);
        loadingPB = findViewById(R.id.idPBLoading);
        addServiceFAB = findViewById(R.id.idFABAddService);
        firebaseDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        serviceModalArrayList = new ArrayList<>();
        //on below line we are getting database reference.
        databaseReference = firebaseDatabase.getReference("Services");
        //on below line adding a click listener for our floating action button.
        addServiceFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //opening a new activity for adding a Service.
                Intent i = new Intent(MainActivity.this, AddServiceActivity.class);
                startActivity(i);
            }
        });
        //on below line initializing our adapter class.
        serviceAdapter = new ServiceAdapter(serviceModalArrayList, this, this::onServiceClick);
        //setting layout malinger to recycler view on below line.
        service.setLayoutManager(new LinearLayoutManager(this));
        //setting adapter to recycler view on below line.
        service.setAdapter(serviceAdapter);
        //on below line calling a method to fetch Services from database.
        getServices();
    }

    private void getServices() {
        //on below line clearing our list.
        serviceModalArrayList.clear();
        //on below line we are calling add child event listener method to read the data.
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                //on below line we are hiding our progress bar.
                loadingPB.setVisibility(View.GONE);
                //adding snapshot to our array list on below line.
                serviceModalArrayList.add(snapshot.getValue(ServiceModal.class));
                //notifying our adapter that data has changed.
                serviceAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                //this method is called when new child is added we are notifying our adapter and making progress bar visibility as gone.
                loadingPB.setVisibility(View.GONE);
                serviceAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                //notifying our adapter when child is removed.
                serviceAdapter.notifyDataSetChanged();
                loadingPB.setVisibility(View.GONE);

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                //notifying our adapter when child is moved.
                serviceAdapter.notifyDataSetChanged();
                loadingPB.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onServiceClick(int position) {
        //calling a method to display a bottom sheet on below line.
        displayBottomSheet(serviceModalArrayList.get(position));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //adding a click listner for option selected on below line.
        int id = item.getItemId();
        if (id == R.id.idLogOut) {
            Toast.makeText(getApplicationContext(), "User Logged Out", Toast.LENGTH_LONG).show();
            mAuth.signOut();
            Intent i = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(i);
            this.finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //on below line we are inflating our menu file for displaying our menu options.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void displayBottomSheet(ServiceModal modal) {
        //on below line we are creating our bottom sheet dialog.
        final BottomSheetDialog bottomSheetTeachersDialog = new BottomSheetDialog(this, R.style.BottomSheetDialogTheme);
        //on below line we are inflating our layout file for our bottom sheet.
        View layout = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_layout, null);

//        ViewGroup parent = (ViewGroup) layout.getParent();
//        if (parent != null) {
//            parent.removeView(layout);
//        }

        //setting content view for bottom sheet on below line.
        bottomSheetTeachersDialog.setContentView(layout);
        //on below line we are setting a cancelable
        bottomSheetTeachersDialog.setCancelable(false);
        bottomSheetTeachersDialog.setCanceledOnTouchOutside(true);
        //calling a method to display our bottom sheet.
        bottomSheetTeachersDialog.show();
        //on below line we are creating variables for our text view and image view inside bottom sheet
        //and initialing them with their ids.
        TextView ServiceNameTV = layout.findViewById(R.id.idTVServiceName);
        TextView ServiceDescTV = layout.findViewById(R.id.idTVServiceSuccursale);
        TextView suitedForTV = layout.findViewById(R.id.idTVExigences);
        TextView priceTV = layout.findViewById(R.id.idTVServicePrice);
        ImageView ServiceIV = layout.findViewById(R.id.idIVService);
        //on below line we are setting data to different views on below line.
        ServiceNameTV.setText(modal.getServiceName());
        ServiceDescTV.setText(modal.getSuccursales());
        suitedForTV.setText("Exigences " + modal.getExigences());
        Picasso.get().load(modal.getServiceImg()).into(ServiceIV);
        Button viewBtn = layout.findViewById(R.id.idBtnVIewDetails);
        Button editBtn = layout.findViewById(R.id.idBtnEditService);

        //adding on click listener for our edit button.
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //on below line we are opening our EditServiceActivity on below line.
                Intent i = new Intent(MainActivity.this, EditServiceActivity.class);
                //on below line we are passing our Service modal
                i.putExtra("Service", modal);
                startActivity(i);
            }
        });
        //adding click listener for our view button on below line.
        viewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //on below line we are navigating to browser for displaying Service details from its url
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(modal.getServiceLink()));
                startActivity(i);
            }
        });

    }


}