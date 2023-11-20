package com.example.service_novigrad_livrable2;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {

    //creating variable for edit text, textview, button, progress bar and firebase auth.
    private TextInputEditText userNameEdt, passwordEdt;
    private Button loginBtn;
    private TextView newUserTV;
    private FirebaseAuth mAuth;
    private ProgressBar loadingPB;

    FirebaseFirestore fStore;

    public enum Role {
        ADMIN,
        EMPLOYE,
        CLIENT
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //initializing all our variables.
        userNameEdt = findViewById(R.id.idEdtUserName);
        passwordEdt = findViewById(R.id.idEdtPassword);
        loginBtn = findViewById(R.id.idBtnLogin);
        newUserTV = findViewById(R.id.idTVNewUser);
        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        loadingPB = findViewById(R.id.idPBLoading);
        //adding click listner for our new user tv.
        newUserTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //on below line opening a login activity.
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });

        //adding on click listener for our login button.
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //hiding our progress bar.
                loadingPB.setVisibility(View.VISIBLE);
                //getting data from our edit text on below line.
                String email = userNameEdt.getText().toString();
                String password = passwordEdt.getText().toString();

                String role = String.valueOf(Role.CLIENT);

                //on below line validating the text input.
                if (TextUtils.isEmpty(email) && TextUtils.isEmpty(password)) {
                    Toast.makeText(LoginActivity.this, "Please enter your credentials..", Toast.LENGTH_SHORT).show();
                    return;
                }
                //on below line we are calling a sign in method and passing email and password to it.
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //on below line we are checking if the task is succes or not.
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();

                            DocumentReference df = fStore.collection("Users").document(user.getUid());

                            //on below line we are hiding our progress bar.
                            loadingPB.setVisibility(View.GONE);
                            Toast.makeText(LoginActivity.this, "Login Successful..", Toast.LENGTH_SHORT).show();

                            df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    if (documentSnapshot.getString("isAdmin") != null) {
                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(intent);
                                    } else if (documentSnapshot.getString("isEmploye") != null) {
                                        Intent intent = new Intent(getApplicationContext(), MainActivityEmploye.class);
                                        startActivity(intent);
                                    } else {
                                        Intent intent = new Intent(getApplicationContext(), MainActivityClient.class);
                                        startActivity(intent);
                                    }
                                    finish();
                                }
                            });

                        } else {
                            //hiding our progress bar and displaying a toast message.
                            loadingPB.setVisibility(View.GONE);
                            Toast.makeText(LoginActivity.this, "Please enter valid user credentials..", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        //in on start method checking if the user is already sign in.
        FirebaseUser user = mAuth.getCurrentUser();
//        if (user != null) {
//            //if the user is not null then we are opening a main activity on below line.
//            Intent i = new Intent(LoginActivity.this, MainActivity.class);
//            startActivity(i);
//            this.finish();
 //       }

    }
}

//on below line we are opening our mainactivity.
//Intent i = new Intent(LoginActivity.this, MainActivity.class);
//startActivity(i);
//finish();