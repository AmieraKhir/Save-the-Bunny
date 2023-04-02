package com.example.savethebunnyapp;

// import android packages
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

// import ActivityLoginBinding from same package
import com.example.savethebunnyapp.databinding.ActivityLoginBinding;

// class LoginActivity inherits from class AppCompatActivity
public class LoginActivity extends AppCompatActivity {

    // public attributes
    public ActivityLoginBinding binding;
    public DatabaseHelper databaseHelper;
    @Override

    // function onCreate that receives savedInstanceState of type Bundle as parameter
    protected void onCreate(Bundle savedInstanceState) {

        // WILL BE EXPLAINED MORE CLEARLY IN LATER CLASSES

        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        databaseHelper = new DatabaseHelper(this);
        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override

            // function onClick receiving view of type View as paramter
            public void onClick(View view) {

                // these 2 lines of codes are used to convert email input and password input from user to String format
                String email = binding.loginEmail.getText().toString();
                String password = binding.loginPassword.getText().toString();

                // this block of code is to check and validate data input from user
                // if email input or password input is not given, and is left blank, the program will display the text "All fields are mandatory"
                if(email.equals("")||password.equals(""))
                    Toast.makeText(LoginActivity.this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
                else{

                    // otherwise, check if email and password matches with those stored in the database
                    Boolean checkCredentials = databaseHelper.checkEmailPassword(email, password);

                    // if it matches, it means that the user has input the correct information
                    if(checkCredentials == true){

                        // making it a successful login
                        Toast.makeText(LoginActivity.this, "Login Successfully!", Toast.LENGTH_SHORT).show();
                        Intent intent  = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }else{

                        // otherwise, display "Invalid Credentials" for wrong input of data
                        Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        binding.signupRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            // function onClick receives view of type View as parameter
            public void onClick(View view) {

                // create a new object of type Intent named intent
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
   });
}
}