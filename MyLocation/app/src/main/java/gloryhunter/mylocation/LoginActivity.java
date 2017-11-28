package gloryhunter.mylocation;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText etName;
    private EditText etEmail;
    private EditText etPass;
    private EditText etPhone;
    private Button btLogin;
    private Button btRegister;
    private TextView tvDetail;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etName = findViewById(R.id.et_name);
        etEmail = findViewById(R.id.et_email);
        etPass = findViewById(R.id.et_pass);
        etPhone = findViewById(R.id.et_phone);
        btLogin = findViewById(R.id.bt_login);
        btRegister = findViewById(R.id.bt_register);
        tvDetail = findViewById(R.id.tv_details);

        btLogin.setOnClickListener(this);
        btRegister.setOnClickListener(this);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
      
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_register:{
                firebaseAuth.createUserWithEmailAndPassword(
                        etEmail.getText().toString(),
                        etPass.getText().toString()
                ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            firebaseAuth.getCurrentUser().sendEmailVerification()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){
                                                Toast.makeText(LoginActivity.this,
                                                        "thanhcongCMNR",
                                                        Toast.LENGTH_SHORT).show();

                                                UserProfileChangeRequest userProfileChangeRequest = new UserProfileChangeRequest.Builder()
                                                        .setDisplayName(etName.getText().toString())
                                                        .setPhotoUri(Uri.parse(""))
                                                        .build();
                                                firebaseAuth.getCurrentUser().updateProfile(userProfileChangeRequest);
                                            }else{
                                                Toast.makeText(LoginActivity.this,
                                                        task.getException().getMessage(),
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                            UserMode userMode = new UserMode(
                                    etName.getText().toString(),
                                    etEmail.getText().toString(),
                                    etPhone.getText().toString()
                            );
                            databaseReference.push().setValue(userMode);

                        }else{
                            Toast.makeText(LoginActivity.this, task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                break;
            }
            case R.id.bt_login:{
                        firebaseAuth.signInWithEmailAndPassword(
                                etEmail.getText().toString(),
                                etPass.getText().toString()
                        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(LoginActivity.this,"Login successful",
                                            Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                                }else {
                                    Toast.makeText(LoginActivity.this,
                                            task.getException().getMessage(),
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                break;
            }
        }
    }
