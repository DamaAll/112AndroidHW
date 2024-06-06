package com.demo.firebasefirsttry;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthenticationActivity extends AppCompatActivity {

    private EditText mEmail;
    private EditText mPassword;
    private Button mRegister;
    private Button mLogin;
    private TextView mLoginResult;
    private String TAG = "AuthenticationActivity";

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String email = mEmail.getEditableText().toString();
            String password = mPassword.getEditableText().toString();

            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                return;
            }

            if (view.getId() == R.id.login) {
                login(email, password);
            } else if (view.getId() == R.id.register) {
                register(email, password);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Firebase Authentication");
        setContentView(R.layout.activity_authentication);

        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);
        mRegister = findViewById(R.id.register);
        mLogin = findViewById(R.id.login);
        mLoginResult = findViewById(R.id.login_result);

        mLogin.setOnClickListener(mOnClickListener);
        mRegister.setOnClickListener(mOnClickListener);
    }

    private void register(String email, String password) {
        final FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = auth.getCurrentUser();
                            mLoginResult.setText("註冊結果UserId: " + user.getUid());
                            return;
                        }
                        String errorMessage = task.getException().getMessage();
                        mLoginResult.setText("註冊錯誤: " + errorMessage);
                    }
                });
    }

    private void login(String email, String password) {
        final FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = auth.getCurrentUser();
                            mLoginResult.setText("登入UserId: " + user.getUid());
                            return;
                        }
                        String errorMessage = task.getException().getMessage();
                        mLoginResult.setText("登入錯誤: " + errorMessage);
                    }
                });
    }
}