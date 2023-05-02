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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;

public class SignUpActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextPasswordCheck;
    private Button buttonJoin;
    private Button buttonBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();

        editTextEmail = (EditText) findViewById(R.id.editText_email);
        editTextPassword = (EditText) findViewById(R.id.editText_passWord);
        editTextPasswordCheck = (EditText) findViewById(R.id.editText_passWordCheck);

        buttonBack = (Button) findViewById(R.id.btn_back);
        buttonBack.setOnClickListener(new View.OnClickListener() {//회원가입 버튼을 누르면 회원가입 액티비티로
            @Override
            public void onClick(View v) {
                // SignUpActivity 연결
                Intent intent = new Intent(SignUpActivity.this, LogInActivity.class);
                startActivity(intent);
            }
        });





        buttonJoin = (Button) findViewById(R.id.btn_join);
        buttonJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editTextEmail.getText().toString().equals("") && !editTextPassword.getText().toString().equals(""))
                {
                    // 이메일과 비밀번호가 공백이 아닌 경우
//                    if(editTextPassword.getText().toString().equals(editTextPasswordCheck.getText().toString()))
                    createUser(editTextEmail.getText().toString(), editTextPassword.getText().toString());
//                    else
//                        StartToast("비밀번호가 일치 하지 않습니다");
                }
                else
                {
                    // 이메일과 비밀번호가 공백인 경우
                    Toast.makeText(SignUpActivity.this, "계정과 비밀번호를 입력하세요.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    private void StartToast(String msg)
    {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    private void createUser(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // 회원가입 성공시
                            Toast.makeText(SignUpActivity.this, "회원가입 성공", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            addFireBaseUser(user.getUid(),user.getEmail());
                            finish();
                        } else {
                            // 계정이 중복된 경우
                            //Log.d("TEST", String.valueOf(task.getException()));
                            Toast.makeText(SignUpActivity.this, "이미 존재하는 계정입니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void addFireBaseUser(String UID,String email){

        JSONObject user_info = new JSONObject();
        JSONObject data = new JSONObject();

        try {
            user_info.put("UID",UID);
            user_info.put("email",email);
            user_info.put("password","made_by_firebase");
            user_info.put("auth","user");

            data.put("user_info",user_info);

        } catch (JSONException e) {
            Log.d("recipe_info_json","recipe_info_json make error");
            e.printStackTrace();
        }

        String Uri = "http://10.0.2.2:8080/android/saveNewUser";

        SignUpHandler handler = new SignUpHandler(this);

        http_protocol http = new http_protocol(data.toString(),Uri,handler,103,-1);

        http.start();
    }

    public static class SignUpHandler extends Handler {
        private final WeakReference<SignUpActivity> weakReference;

        public SignUpHandler(SignUpActivity Activity) {
            weakReference = new WeakReference<SignUpActivity>(Activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            SignUpActivity activity = weakReference.get();

            String result;


            if (activity != null) {

                if(msg.what == 103) {

                    result = (String) msg.obj;

                    if(result == "-1"){
                        Toast.makeText(activity.getApplicationContext(), "make new firebase user failed.", Toast.LENGTH_SHORT).show();
                    }

                }else if(msg.what == 404){

                    result = "Error!";

                }

            }

        }
    }
}