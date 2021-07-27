package mo.zain.food_recipe.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.jetbrains.annotations.NotNull;

import mo.zain.food_recipe.MainActivity;
import mo.zain.food_recipe.R;

public class LoginActivity extends AppCompatActivity {

    private TextView toRegister,forget;
    private FirebaseUser firebaseUser;
    private FirebaseAuth mAuth;
    private TextInputEditText email,password;
    private Button SignIn;
    private ProgressBar progressBar;
    private AlertDialog dialogForgetPassword;
    private ProgressDialog mDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth=FirebaseAuth.getInstance();
        firebaseUser=mAuth.getCurrentUser();
        mDialog = new ProgressDialog(LoginActivity.this);
        email=findViewById(R.id.email_login);
        password=findViewById(R.id.password_login);
        SignIn=findViewById(R.id.signIn);
        forget=findViewById(R.id.forget);

        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showForgetDialog();
            }
        });

        progressBar=findViewById(R.id.progress);



        toRegister=findViewById(R.id.toRegister);
        toRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegistrationActivity.class));
            }
        });

        SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (email.getText().toString().isEmpty())
                {
                    email.setError("Enter Your Email");
                    return;
                }else if (password.getText().toString().isEmpty()){
                    password.setError("Enter Your Password");
                    return;
                }else {
                    progressBar.setVisibility(View.VISIBLE);
                    SignIN(email.getText().toString(),password.getText().toString());
                }
            }
        });
    }

    private void SignIN(String email,String password){
        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            progressBar.setVisibility(View.GONE);
                            startActivity(new Intent(LoginActivity.this,MainActivity.class));
                            finish();
                        }else
                        {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(LoginActivity.this, ""+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(LoginActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void showForgetDialog() {
        if (dialogForgetPassword == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
            View view = LayoutInflater.from(this).inflate(
                    R.layout.layout_forget_password,
                    (ViewGroup) findViewById(R.id.layoutAddUrlContainer)
            );
            builder.setView(view);
            dialogForgetPassword = builder.create();
            if (dialogForgetPassword.getWindow() != null) {
                dialogForgetPassword.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            }
            final EditText inputLink = view.findViewById(R.id.layout);
            inputLink.requestFocus();
            view.findViewById(R.id.textAdd).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (inputLink.getText().toString().trim().equals("")) {
                        //FancyToast.makeText(LoginActivity.this, "Enter Your Email", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                    } else if (!Patterns.EMAIL_ADDRESS.matcher(inputLink.getText().toString().trim()).matches()) {
                        //FancyToast.makeText(LoginActivity.this, "Incorrect Email", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                    } else {
                        beginRecovery(inputLink.getText().toString());
                        dialogForgetPassword.dismiss();
                    }
                }
            });
            view.findViewById(R.id.textCancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogForgetPassword.dismiss();
                }
            });
        }
        dialogForgetPassword.show();
    }

    private void beginRecovery(String email) {
        mDialog.setMessage("Sending Email");
        mDialog.show();
        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            //FancyToast.makeText(LoginActivity.this, "Send Email Done!!", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                            mDialog.dismiss();
                        } else {
                            //FancyToast.makeText(LoginActivity.this, "Password setting failed, please try again", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                            mDialog.dismiss();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                mDialog.dismiss();
                //FancyToast.makeText(LoginActivity.this, "" + e.getMessage(),FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();

        if (firebaseUser!=null)
        {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
    }
}