package com.example.service_novigrad_livrable2;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity implements  AdapterView.OnItemSelectedListener{

    //cretaing variables for edit text and textview, firebase auth, button and progress bar.
    private TextInputEditText userNameEdt, passwordEdt, confirmPwdEdt;
    private TextView loginTV;
    private Button registerBtn;
    private FirebaseAuth mAuth;
    private ProgressBar loadingPB;
    FirebaseFirestore fStore;

    String[] UsersRoles = { "Client", "Employe"};
    String userUserSelected = "Client";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //initializing all our variables.
        userNameEdt = findViewById(R.id.idEdtUserName);
        passwordEdt = findViewById(R.id.idEdtPassword);
        loadingPB = findViewById(R.id.idPBLoading);
        confirmPwdEdt = findViewById(R.id.idEdtConfirmPassword);
        loginTV = findViewById(R.id.idTVLoginUser);
        registerBtn = findViewById(R.id.idBtnRegister);
        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        //adding on click for login tv.
        loginTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //opening a login activity on clicking login text.
                Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });
        //adding click listener for register button.
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //hiding our progress bar.
                loadingPB.setVisibility(View.VISIBLE);
                //getting data fro =m our edit text.
                String userName = userNameEdt.getText().toString();
                String pwd = passwordEdt.getText().toString();
                String cnfPwd = confirmPwdEdt.getText().toString();
                //checking if the password and confirm password is equal or not.
                if (!pwd.equals(cnfPwd)) {
                    Toast.makeText(RegisterActivity.this, "Please check both having same password..", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(userName) && TextUtils.isEmpty(pwd) && TextUtils.isEmpty(cnfPwd)) {
                    //checking if the text fields are empty or not.
                    Toast.makeText(RegisterActivity.this, "Please enter your credentials..", Toast.LENGTH_SHORT).show();
                } else {
                    //on below line we are creating a new user by passing email and password.
                    mAuth.createUserWithEmailAndPassword(userName, pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            //on below line we are checking if the task is success or not.
                            if (task.isSuccessful()) {
                                //in on success method we are hiding our progress bar and opening a login activity.
                                FirebaseUser user = mAuth.getCurrentUser();
                                loadingPB.setVisibility(View.GONE);
                                Toast.makeText(RegisterActivity.this, "User Registered..", Toast.LENGTH_SHORT).show();

                                DocumentReference df = fStore.collection("Users").document(user.getUid());
                                Map<String, Object> userInfo = new HashMap<>();

                                if(userUserSelected.equals("Client")){
                                    userInfo.put("isClient", "1");
                                } else if (userUserSelected.equals("Employe")){
                                    userInfo.put("isEmploye", "1");
                                }
                                df.set(userInfo);

                                Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(i);
                                finish();
                            } else {
                                //in else condition we are displaying a failure toast message.
                                loadingPB.setVisibility(View.GONE);
                                Toast.makeText(RegisterActivity.this, "Fail to register user..", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        //Getting the instance of Spinner and applying OnItemSelectedListener on it
        Spinner spin = (Spinner) findViewById(R.id.spinner);
        spin.setOnItemSelectedListener(this);

        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,UsersRoles);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);
    }
    //@Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getApplicationContext(),UsersRoles[position] , Toast.LENGTH_LONG).show();
        userUserSelected = UsersRoles[position];
    }

    //@Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}