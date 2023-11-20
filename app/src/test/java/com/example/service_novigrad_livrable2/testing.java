package com.example.service_novigrad_livrable2;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class testing {

    @Mock    private FirebaseAuth mockAuth;
    @Mock    private FirebaseDatabase mockDatabase;
    @Mock    private DatabaseReference mockReference;
    private MainActivity mainActivity;
    private ArrayList<ServiceModal> serviceModalArrayList;
    private FirebaseAuth mAuth;
    private ServiceAdapter serviceAdapter;
    @Before    public void setUp() {
        mainActivity = new MainActivity();
        serviceAdapter=mainActivity.getServiceAdapter();
        mAuth=mainActivity.getFirebaseAuth();
        serviceModalArrayList=mainActivity.getServiceModalArrayList();
        mainActivity.firebaseDatabase = mockDatabase;
        mainActivity.databaseReference = mockReference;


    }

    @Test    public void testGetServices() {

        // Mock database behavior
        DataSnapshot mockSnapshot = Mockito.mock(DataSnapshot.class);

        when(mockSnapshot.getValue(ServiceModal.class)).thenReturn(new ServiceModal("ServiceName","ServiceName","Formulaire", "Documents"));


        // Call the method to be tested
        mainActivity.getServices();

        // Verify that the method updates the list and notifies the adapter
        assertEquals(1, serviceModalArrayList.size());

        verify(serviceAdapter).notifyDataSetChanged();    }

    @Test    public void testOnServiceClick() {

        // Mock data

        ServiceModal mockServiceModal = new ServiceModal("ServiceName", "ServiceName", "Formulaire", "Documents");
        serviceModalArrayList.add(mockServiceModal);


        // Call the method to be tested

        mainActivity.onServiceClick(0);


        // Verify that the method displays the bottom sheet

        verify(mainActivity).displayBottomSheet(mockServiceModal);

    }


    // Add more tests as needed for other methods and functionalities in MainActivity

}

