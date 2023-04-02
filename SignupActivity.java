package com.example.savethebunnyapp;

// import android packages
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

// import ActivityLoginBinding from same package
import com.example.savethebunnyapp.databinding.ActivitySignupBinding;

// class SignupActivity inherits from class AppCompatActivity
public class SignupActivity extends AppCompatActivity {

    // public attributes
    public ActivitySignupBinding binding;
    public DatabaseHelper databaseHelper;

    @Override
    // function onCreate that receives savedInstanceState of type Bundle as parameter
    protected void onCreate(Bundle savedInstanceState) {

        // WILL BE EXPLAINED MORE CLEARLY IN LATER CLASSES

        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        databaseHelper = new DatabaseHelper(this);
        binding.signupButton.setOnClickListener(new View.OnClickListener() {
            @Override

            // function onClick receiving view of type View as paramter
            public void onClick(View view) {

                // these 3 lines of codes are used to convert email, password and confirmPassword input from user to String format
                String email = binding.signupEmail.getText().toString();
                String password = binding.signupPassword.getText().toString();
                String confirmPassword = binding.signupConfirm.getText().toString();

                // this block of code is to check and validate data input from user
                // if email input or password input is not given, and is left blank, the program will display the text "All fields are mandatory"
                if(email.equals("")||password.equals("")||confirmPassword.equals(""))
                    Toast.makeText(SignupActivity.this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
                else{
                    if(password.equals(confirmPassword)){
                        Boolean checkUserEmail = databaseHelper.checkEmail(email);

                        // check from the database if the same email has been used to signup before for another account
                        if(checkUserEmail == false){
                            Boolean insert = databaseHelper.insertData(email, password);

                            if(insert == true){

                                // when signup is a success or a failure, suitable texts will be display
                                Toast.makeText(SignupActivity.this, "Signup Successfully!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                                startActivity(intent);
                            }else{
                                Toast.makeText(SignupActivity.this, "Signup Failed!", Toast.LENGTH_SHORT).show();
                            }
                        }

                        // after confirming the user is an existing user, displays text "User already exists! Please login"
                        // and will direct them to the login page
                        else{
                            Toast.makeText(SignupActivity.this, "User already exists! Please login", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(SignupActivity.this, "Invalid Password!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        binding.loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
            }
    });

}
}