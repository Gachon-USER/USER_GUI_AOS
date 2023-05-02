package com.example.user;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;

public class LogInActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonLogIn;
    private Button buttonSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        editTextEmail = (EditText) findViewById(R.id.edittext_email);
        editTextPassword = (EditText) findViewById(R.id.edittext_password);

        buttonSignUp = (Button) findViewById(R.id.btn_signup);
        buttonSignUp.setOnClickListener(new View.OnClickListener() {//회원가입 버튼을 누르면 회원가입 액티비티로
            @Override
            public void onClick(View v) {
                // SignUpActivity 연결
                Intent intent = new Intent(LogInActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        buttonLogIn = (Button) findViewById(R.id.btn_login);
        buttonLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editTextEmail.getText().toString().equals("") && !editTextPassword.getText().toString().equals("")) {
                    loginUser(editTextEmail.getText().toString(), editTextPassword.getText().toString());
                } else {
                    Toast.makeText(LogInActivity.this, "계정과 비밀번호를 입력하세요.", Toast.LENGTH_LONG).show();
                }
            }
        });

        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    getUser("http://10.0.2.2:8080/android/getUserDetail",user.getUid());
                } else {

                }
            }
        };
    }

    public void loginUser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // 로그인 성공
                            Toast.makeText(LogInActivity.this, "로그인 성공", Toast.LENGTH_SHORT).show();
                            mAuth.addAuthStateListener(firebaseAuthListener);
                        } else {
                            // 로그인 실패
                            Toast.makeText(LogInActivity.this, "아이디 또는 비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (firebaseAuthListener != null) {
            mAuth.removeAuthStateListener(firebaseAuthListener);
        }
    }

    public void getUser(String server_url,String UID) {

        logInHandler handler = new logInHandler(this);

        server_url = server_url + "?UID=" + UID;

        http_protocol http = new http_protocol("",server_url,handler,1031,-1);

        http.start();

    }

    public static class logInHandler extends Handler {
        private final WeakReference<LogInActivity> weakReference;

        public logInHandler(LogInActivity Activity) {
            weakReference = new WeakReference<LogInActivity>(Activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            LogInActivity activity = weakReference.get();

            String result;

            if (activity != null) {

                if(msg.what == 1031) {

                    result = (String) msg.obj;

                    if(result == "-1"){
                        Toast.makeText(activity.getApplicationContext(), "get firebase user failed.", Toast.LENGTH_SHORT).show();
                    }else{

                        String UID = null;
                        int lastfood = 0;
                        String foodtaste = null;

                        try{
                            JSONObject json = new JSONObject(result);
                            UID = json.getString("UID");
                            lastfood = json.getInt("lastfood");
                            foodtaste = json.getString("foodtaste");

                        }catch (Exception e){
                            e.printStackTrace();
                        }

                        Intent intent = new Intent(activity, MainActivity.class);
                        intent.putExtra("UID",UID);
                        intent.putExtra("lastfood",lastfood);
                        intent.putExtra("foodtaste",foodtaste);
                        activity.startActivity(intent);
                        activity.finish();
                    }

                }else if(msg.what == 404){

                    result = "Error!";

                }

            }

        }
    }
}