package mo.zain.food_recipe.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

import mo.zain.food_recipe.MainActivity;
import mo.zain.food_recipe.R;

public class RegistrationActivity extends AppCompatActivity {

    private TextInputEditText userName,email,password;
    private Button register;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mAuth = FirebaseAuth.getInstance();

        userName=findViewById(R.id.user_register);
        email=findViewById(R.id.email_register);
        password=findViewById(R.id.password_register);
        register=findViewById(R.id.signUp);
        progressBar=findViewById(R.id.progress);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userName.getText().toString().isEmpty())
                {
                    userName.setError("Enter Your Name");
                    return;
                }else if (email.getText().toString().isEmpty()){
                    email.setError("Enter Your Email");
                    return;
                }else if (password.getText().toString().isEmpty()){
                    email.setError("Enter Your Password");
                    return;
                }else
                {
                    progressBar.setVisibility(View.VISIBLE);
                    SignUp(email.getText().toString(),password.getText().toString());
                }
            }
        });
    }
    private void SignUp(String email,String password){
        mAuth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                    if (!task.isSuccessful())
                    {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(RegistrationActivity.this, ""+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }else
                    {
                        FirebaseUser firebaseUser=mAuth.getCurrentUser();
                        DatabaseReference reference;
                        reference= FirebaseDatabase.getInstance().getReference("Users")
                        .child(firebaseUser.getUid().toString());
                        HashMap<String,String> hashMap=new HashMap<>();
                        hashMap.put("email",email);
                        hashMap.put("username",userName.getText().toString());
                        reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull @NotNull Task<Void> task) {
                                progressBar.setVisibility(View.GONE);
                                startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull @NotNull Exception e) {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(RegistrationActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(RegistrationActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}