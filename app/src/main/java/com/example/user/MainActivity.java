package com.example.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v4.os.IResultReceiver;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    LogIn frameLayout0;
    Main frameLayout1;
    FoodList frameLayout2;
    TestAgain frameLayout3;
    UserPage frameLayout4;
    EnrollRecipe frameLayout5;
    VoiceAlert  frameLayout6;
    RecipeView frameLayout7;
    SignUp frameLayout8;
    PwReset frameLayout9;
    String UID;

    int lastfood;

    String foodtaste;

    MyHandler handle = new MyHandler(this);

    private CountDownTimer countDownTimer;

    public boolean timerRunning;
    public boolean firstState;

    public long time = 0;
    public long tempTime = 0;

    public void startStop(){
        if(timerRunning){
            stopTimer();
        }else{
            startTimer(-1);
        }
    }

    public void startTimer(int sec) {
        if(firstState){
            time = ((long)(sec))*1000 + 1000;
        }else{
            time = tempTime;
        }

        countDownTimer = new CountDownTimer(time,1000) {
            @Override
            public void onTick(long l) {
                tempTime = l;
                updateTimer();
            }

            @Override
            public void onFinish() {
                frameLayout6.TimerEnd();
            }
        }.start();

        frameLayout6.setTimerButton("일시정지");
        timerRunning = true;
        firstState = false;
    }

    public void stopTimer() {
        countDownTimer.cancel();
        timerRunning = false;
        frameLayout6.setTimerButton("계속");
    }

    public void updateTimer(){
        int hour = (int) tempTime / 3600000;
        int minutes = (int) tempTime % 3600000 / 60000;
        int seconds = (int) tempTime % 3600000 % 60000 / 1000;

        String timeLeftText = "";
        timeLeftText = ""+ hour + ":";

        if(minutes < 10) timeLeftText +="0";
        timeLeftText += minutes + ":";

        if(seconds < 10) timeLeftText += "0";
        timeLeftText += seconds;

        frameLayout6.setTimerText(timeLeftText);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent user_info = getIntent();

        this.UID = user_info.getStringExtra("UID");
        this.lastfood = user_info.getIntExtra("lastfood",0);
        this.foodtaste = user_info.getStringExtra("foodtaste");

        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            Log.d("permission","checkSelfPermission");
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.ACCESS_COARSE_LOCATION)) {

                Log.d("permission","shouldShowRequestPermissionRationale");
                // 사용자에게 설명을 보여줍니다.
                // 권한 요청을 다시 시도합니다.

            } else {
                // 권한요청

                Log.d("permission","권한 요청");
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_WIFI_STATE,Manifest.permission.CHANGE_WIFI_STATE},
                        1000);

            }
        }

        frameLayout0 = new LogIn();
        frameLayout1 = new Main();
        frameLayout2 = new FoodList();
        frameLayout3 = new TestAgain();
        frameLayout4 = new UserPage();
        frameLayout5 = new EnrollRecipe();
        frameLayout6 = new VoiceAlert();
        frameLayout7 = new RecipeView();
        frameLayout8 = new SignUp();
        frameLayout9 = new PwReset();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.lgn, frameLayout1).commit();

    }

    @Override

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case 1000: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // 권한 획득 성공
                    Log.d("permission","권한 획득 성공");

                } else {

                    // 권한 획득 실패
                    Log.d("permission","권한 획득 실패");
                }
                return;
            }

        }
    }

    public void fragmentChange(int index,Bundle data){

        if(index == 0){//Main
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.lgn, frameLayout0).commit();
        }

        else if(index == 1){//Main
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.lgn, frameLayout1).commit();
        }
        else if(index == 2){//foodlist
            if(data != null)
                frameLayout2.setArguments(data);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.lgn, frameLayout2).commit();
        }
        else if(index == 3){//TestAgain
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.lgn, frameLayout3).commit();
        }
        else if(index == 4){//UserPage
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.lgn, frameLayout4).commit();
        }
        else if(index == 5){//EnrollRecipe
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.lgn, frameLayout5).commit();
        }
        else if(index == 6){//VoiceAlert
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.lgn, frameLayout6).commit();
        }
        else if(index == 7){//recipeview
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.lgn, frameLayout7).commit();
        }
        else if(index == 8 ){//signup
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.lgn, frameLayout8).commit();
        }

        else if(index == 9 ){//pwd reset
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.lgn, frameLayout9).commit();
        }
    }

    public void sendHttpApi(String data, String uri, int control,int ID){

        http_protocol http = new http_protocol(data,uri,this.handle,control,ID);

        http.start();
    }

    public void sendUpdateUserDetail(String user_added_item, String server_url) {

        JSONObject user_Detail = new JSONObject();

        JSONObject data = new JSONObject();

        try{
            user_Detail.put("UID",UID);
            user_Detail.put("lastfood",lastfood);
            user_Detail.put("foodtaste",foodtaste);

            JSONObject added_item = new JSONObject();
            added_item.put("ID",Integer.parseInt(user_added_item));

            JSONArray list = new JSONArray();
            list.put(added_item);

            user_Detail.put("userRecipeList",list);

            data.put("user_detail",user_Detail);

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        http_protocol http = new http_protocol(data.toString(),server_url,this.handle,1051,-1);

        http.start();

    }

    public static class MyHandler extends Handler {
        private final WeakReference<MainActivity> weakReference;

        public MyHandler(MainActivity Activity) {
            weakReference = new WeakReference<MainActivity>(Activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            MainActivity activity = weakReference.get();

            String result;


            if (activity != null) {
                if(msg.what == 101) {
                    // http 클래스에서 JSON 데이터를 넘겨받은 경우.

                    result = (String) msg.obj;
                    activity.frameLayout1.send_recommand_result(result);
                    // http 클래스에서 JSON 데이터를 넘겨받지 못한 경우.
                }else if(msg.what == 1010) {

                    result = (String) msg.obj;
                    activity.frameLayout1.send_result(result, 0);
                    // http 클래스에서 JSON 데이터를 넘겨받지 못한 경우.

                }else if(msg.what == 1011) {

                    result = (String) msg.obj;
                    activity.frameLayout1.send_result(result, 1);
                    // http 클래스에서 JSON 데이터를 넘겨받지 못한 경우.

                }else if(msg.what == 1012) {

                    result = (String) msg.obj;
                    activity.frameLayout1.send_result(result, 2);
                    // http 클래스에서 JSON 데이터를 넘겨받지 못한 경우.

                }else if(msg.what == 1013) {

                    result = (String) msg.obj;
                    activity.frameLayout1.send_result(result, 3);
                    // http 클래스에서 JSON 데이터를 넘겨받지 못한 경우.

                }else if(msg.what == 1014) {

                    result = (String) msg.obj;
                    activity.frameLayout1.send_result(result, 4);
                    // http 클래스에서 JSON 데이터를 넘겨받지 못한 경우.

                }else if(msg.what == 102 || msg.what == 1024) {

                    result = (String) msg.obj;
                    activity.frameLayout2.send_result(result, 0);
                    // http 클래스에서 JSON 데이터를 넘겨받지 못한 경우.

                }else if(msg.what == 1021) {

                    result = (String) msg.obj;
                    Log.d("con1",result);
                    activity.frameLayout2.send_result(result, 1);
                    // http 클래스에서 JSON 데이터를 넘겨받지 못한 경우.

                }else if(msg.what == 1022) {

                    result = (String) msg.obj;
                    Log.d("con2",result);
                    activity.frameLayout2.send_result(result, 2);

                    // http 클래스에서 JSON 데이터를 넘겨받지 못한 경우.
                }else if(msg.what == 1023) {

                    result = (String) msg.obj;
                    Log.d("con2",result);
                    activity.frameLayout2.send_result(result, 3);

                    // http 클래스에서 JSON 데이터를 넘겨받지 못한 경우.
                }else if(msg.what == 104){

                        result = (String) msg.obj;
                        //activity.frameLayout4.send_result(result);

                    // http 클래스에서 JSON 데이터를 넘겨받지 못한 경우.

                }else if(msg.what == 105){

                        result = (String) msg.obj;
                        int control = Integer.parseInt(result);
                        activity.frameLayout5.send_result(control,result);
                    // http 클래스에서 JSON 데이터를 넘겨받지 못한 경우.

                }else if(msg.what == 1051){

                    result = (String) msg.obj;
                    activity.frameLayout5.send_result(-1,result);
                    // http 클래스에서 JSON 데이터를 넘겨받지 못한 경우.

                }else if(msg.what == 106){

                        result = (String) msg.obj;
                        Log.d("JSON", "handleMessage: " + result);

                        int control = -1;
                        int append = -1;

                        try{
                            JSONObject json = new JSONObject(result);
                            String intent = json.getString("intent");
                            String NER = json.getString("append");
                            control = Integer.parseInt(intent);
                            append = Integer.parseInt(NER);
                            Log.d("intent","result : "+ control);

                        }catch (Exception e){
                            e.printStackTrace();
                        }

                        activity.frameLayout6.Chat_result(control,append);

                        //activity.frameLayout6.send_result(result);
                    // http 클래스에서 JSON 데이터를 넘겨받지 못한 경우.

                }else if(msg.what == 107) {
                    result = (String) msg.obj;
                    activity.frameLayout7.send_result(result);
                }else if(msg.what == 108) {
                    result = (String) msg.obj;
                    Log.d("idcheck", "idcheck : " + result);
                    int control = Integer.parseInt(result);
                    activity.frameLayout8.check_result(control);
                }
                else if(msg.what == 109) {
                    result = (String) msg.obj;
                    Log.d("result","Saved! : "+ result);
                    int control1 = Integer.parseInt(result);
                    activity.frameLayout8.check_result(control1);
                }
                    // http 클래스에서 JSON 데이터를 넘겨받지 못한 경우.

                }else if(msg.what == 404){

                        result = "Error!";
                        activity.frameLayout6.send_result(result);

                }

            }
        }


}


