package gloryhunter.mylocation;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText etName;
    private EditText etEmail;
    private EditText etPass;
    private EditText etPhone;
    private Button btLogin;
    private Button btRegister;
    private TextView tvDetail;
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
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_register:{
                firebaseAuth.signInWithEmailAndPassword(
                        etEmail.getText().toString(),
                        etPass.getText().toString()
                ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(LoginActivity.this,"Register successful",
                                    Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(LoginActivity.this,
                                    task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
            }
            case R.id.bt_login:{

                break;
            }
        }
    }
}
