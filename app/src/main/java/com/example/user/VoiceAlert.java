package com.example.user;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import static android.speech.tts.TextToSpeech.ERROR;


public class VoiceAlert extends Fragment {
    MainActivity mainActivity;
    private TextToSpeech tts;

    boolean tts_init = false;

    Intent speech_intent;
    RecognitionListener listener;
    SpeechRecognizer speechRecognizer;
    final int PERMISSION = 1;

    boolean recording= false;

    Button buttonNext;
    Button buttonPrev;
    Button buttonStart;
    TextView recipeText;
    TextView pageText;
    ProgressBar bar;
    String result = null;
    // 메인 액티비티 위에 올린다.

    recipe_data data = null;
    String recipeNowString;

    TextToSpeech current_tts;

    int current_index = 0;
    int maxIndex = 0;

    int barCurrentValue = 0;
    int barMaxValue = 0;

    private Button stopButton;
    private Button cancelButton;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity) getActivity();
    }

    // 메인 액티비티에서 내려온다.
    @Override
    public void onDetach() {
        super.onDetach();
        mainActivity = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_voice_alert, container, false);

        CheckPermission();//한국어

        this.listener = new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle bundle) {

            }

            @Override
            public void onBeginningOfSpeech() {
                //사용자가 말하기 시작
            }

            @Override
            public void onRmsChanged(float v) {

            }

            @Override
            public void onBufferReceived(byte[] bytes) {

            }

            @Override
            public void onEndOfSpeech() {
                //사용자가 말을 멈추면 호출
                //인식 결과에 따라 onError나 onResults가 호출됨
            }

            @Override
            public void onError(int error) {    //토스트 메세지로 에러 출력
                String message;
                switch (error) {
                    case SpeechRecognizer.ERROR_AUDIO:
                        message = "오디오 에러";
                        break;
                    case SpeechRecognizer.ERROR_CLIENT:
                        //message = "클라이언트 에러";
                        //speechRecognizer.stopListening()을 호출하면 발생하는 에러
                        return; //토스트 메세지 출력 X
                    case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                        message = "퍼미션 없음";
                        break;
                    case SpeechRecognizer.ERROR_NETWORK:
                        message = "네트워크 에러";
                        break;
                    case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                        message = "네트웍 타임아웃";
                        break;
                    case SpeechRecognizer.ERROR_NO_MATCH:
                        //message = "찾을 수 없음";
                        //녹음을 오래하거나 speechRecognizer.stopListening()을 호출하면 발생하는 에러
                        //speechRecognizer를 다시 생성하여 녹음 재개
                        if (recording)
                            StartRecord();
                        return; //토스트 메세지 출력 X
                    case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                        message = "RECOGNIZER가 바쁨";
                        break;
                    case SpeechRecognizer.ERROR_SERVER:
                        message = "서버가 이상함";
                        break;
                    case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                        message = "말하는 시간초과";
                        break;
                    default:
                        message = "알 수 없는 오류임";
                        break;
                }
                Toast.makeText(mainActivity.getApplicationContext(), "에러가 발생하였습니다. : " + message, Toast.LENGTH_SHORT).show();
            }

            //인식 결과가 준비되면 호출
            @Override
            public void onResults(Bundle bundle) {

                ArrayList<String> matches = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);	//인식 결과를 담은 ArrayList
                //인식 결과
                String newText="";
                for (int i = 0; i < matches.size() ; i++) {
                    newText += matches.get(i);
                }
                // Chat_API 주소지 박아줄것.
                request_Chat(newText,"http://172.30.1.98:8084/chat_request");
            }

            @Override
            public void onPartialResults(Bundle bundle) {

            }

            @Override
            public void onEvent(int i, Bundle bundle) {

            }
        };

        this.speech_intent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        speech_intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,mainActivity.getApplicationContext().getPackageName());
        speech_intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,"ko-KR");

        bar = rootView.findViewById(R.id.progressBar);
        recipeText = rootView.findViewById(R.id.tmpTextView);
        pageText = rootView.findViewById(R.id.test);

        mainActivity.timerRunning = false;
        mainActivity.firstState = true;
        stopButton = rootView.findViewById(R.id.stop_btn);
        stopButton.setVisibility(stopButton.GONE);
        cancelButton = rootView.findViewById(R.id.cancel_btn);
        cancelButton.setVisibility(cancelButton.GONE);

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.startStop();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopButton.setVisibility(stopButton.GONE);
                cancelButton.setVisibility(cancelButton.GONE);
                mainActivity.firstState = true;
                mainActivity.stopTimer();
            }
        });

        recipeText.setText(recipeNowString);
        pageText.setText(Integer.toString(current_index));

        current_tts = speak_set();

        barCurrentValue = bar.getProgress();
        barMaxValue = bar.getMax();

        ImageButton button6 = rootView.findViewById(R.id.toL);

        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.fragmentChange(2,null);
            }

        });

        ImageButton button7 = rootView.findViewById(R.id.toM);

        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.fragmentChange(4,null);
            }

        });

        ImageButton button8 = rootView.findViewById(R.id.toH);

        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.fragmentChange(1,null);
            }

        });

        ImageButton button9 = rootView.findViewById(R.id.toT);

        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.fragmentChange(3,null);
            }

        });


        ImageButton button10 = rootView.findViewById(R.id.toE);

        button10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.fragmentChange(5,null);

            }

        });



        buttonNext = rootView.findViewById(R.id.nex);

        buttonNext.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                control(1);
                ResetText();

            }
        });

        buttonPrev = rootView.findViewById(R.id.baku);

        buttonPrev.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                control(-1);
                ResetText();

            }
        });

        buttonStart = rootView.findViewById(R.id.st);



        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!recording) {   //녹음 시작
                    StartRecord();
                    Toast.makeText(mainActivity.getApplicationContext(), "음성 안내를 시작합니다. 음성으로 기록합니다.", Toast.LENGTH_SHORT).show();
                    current_tts.speak("음성 안내 시작 " + recipeNowString, TextToSpeech.QUEUE_FLUSH, null, null);

                    while(current_tts.isSpeaking()){}

                    StartRecord();
                    buttonStart.setText("음성인식중");
                }
                else {  //이미 녹음 중이면 녹음 중지
                    buttonStart.setText("음성인식");
                    StopRecord();
                }
            }
        });

        return rootView;
    }

    void setTimerButton(String text){
        stopButton.setText(text);
    }

    void setTimerText(String text){
        recipeText.setText(text);
    }

    void TimerEnd(){
        current_tts.speak("타이머가 종료되었습니다.", TextToSpeech.QUEUE_FLUSH, null, null);
        stopButton.setVisibility(stopButton.GONE);
        cancelButton.setVisibility(cancelButton.GONE);
        mainActivity.firstState = true;
        while(current_tts.isSpeaking()){}
        StartRecord();
    }

    void StartRecord() {
        recording = true;

        speechRecognizer=SpeechRecognizer.createSpeechRecognizer(mainActivity.getApplicationContext());
        speechRecognizer.setRecognitionListener(listener);
        speechRecognizer.startListening(speech_intent);
    }

    //녹음 중지
    void StopRecord() {
        recording = false;
        speechRecognizer.destroy(); //녹음 중지
        Toast.makeText(mainActivity.getApplicationContext(), "음성 기록을 중지합니다.", Toast.LENGTH_SHORT).show();
    }

    public void control(int move){
        if(barMaxValue == barCurrentValue) {
            barCurrentValue = 0;
            current_index = 0;
        } else {
            bar.setVisibility(View.VISIBLE);
            if(move > 0){
                barCurrentValue += move * (barMaxValue / maxIndex);
                current_index += move;
                if(current_index >= maxIndex){
                    Toast.makeText(mainActivity.getApplicationContext(),"마지막 페이지 입니다.",Toast.LENGTH_SHORT).show();
                    current_index = maxIndex;
                    barCurrentValue = barMaxValue;
                }

                ResetText();
            }else if(move < 0){
                barCurrentValue += move * (barMaxValue / maxIndex);
                current_index += move;
                if(current_index < 0){
                    Toast.makeText(mainActivity.getApplicationContext(),"첫 페이지 입니다.",Toast.LENGTH_SHORT).show();
                    current_index = 0;
                    barCurrentValue = 0;
                }

                ResetText();
            }else if(move == 0){
                barCurrentValue = 0;
                current_index = 0;
                Toast.makeText(mainActivity.getApplicationContext(),"첫 페이지 입니다.",Toast.LENGTH_SHORT).show();

                ResetText();
            }
        }
        bar.setProgress(barCurrentValue);
        recipeText.setText(recipeNowString);
    }

    public void setTimer(int sec){
        stopButton.setVisibility(stopButton.VISIBLE);
        cancelButton.setVisibility(cancelButton.VISIBLE);
        mainActivity.startTimer(sec);
    }

    public void ResetText(){
        recipeNowString = data.getRecipeData(current_index);
    }
    public void TimerText(int sec){

        int min = sec/60;

        sec = sec%60;

        if (min !=0){
            recipeNowString = "Min : " + Integer.toString(min) + " Sec : " + Integer.toString(sec);
        }else if (min == 0){
            recipeNowString = " Sec : " + Integer.toString(sec);
        }
    }

    public void CheckPermission() {
        //안드로이드 버전이 6.0 이상
        if ( Build.VERSION.SDK_INT >= 23 ){
            //인터넷이나 녹음 권한이 없으면 권한 요청
            if (ContextCompat.checkSelfPermission(mainActivity.getApplicationContext(), Manifest.permission.INTERNET) == PackageManager.PERMISSION_DENIED
                    || ContextCompat.checkSelfPermission(mainActivity.getApplicationContext(), Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_DENIED ) {
                ActivityCompat.requestPermissions(mainActivity,
                        new String[]{Manifest.permission.INTERNET,
                                Manifest.permission.RECORD_AUDIO},PERMISSION);
            }
        }
    }

    public void request_Chat(String user_chat, String Url){

        String JSON = "{\"chatString\":\""+user_chat+"\"}";
        mainActivity.sendHttpApi(JSON,Url,106,-1);
    }

    public void Chat_result(int control,int append){
        /*
        control : Chat_result 에 따른 제어 처리.
            0: timer
            1: next
            2: before
            3: repeat
        */
        StopRecord();

        while(recording){}

        if (control == 0) {
            Toast.makeText(mainActivity.getApplicationContext(), "타이머 설정", Toast.LENGTH_SHORT).show();
            current_tts.speak("타이머를 설정합니다.", TextToSpeech.QUEUE_FLUSH, null, null);
            this.setTimer(append);
        } else if(control == 2){
            this.control(append);
            pageText.setText(Integer.toString(current_index));
            current_tts.speak(recipeNowString, TextToSpeech.QUEUE_FLUSH, null, null);
        } else if(control == 1){
            this.control(append);
            pageText.setText(Integer.toString(current_index));
            current_tts.speak(recipeNowString, TextToSpeech.QUEUE_FLUSH, null, null);
        } else if(control == 3){
            if(append == 0){
                this.control(0);
                pageText.setText(Integer.toString(current_index));
            }
            Toast.makeText(mainActivity.getApplicationContext(), "다시듣기 실행", Toast.LENGTH_SHORT).show();
            current_tts.speak("다시듣기 " + recipeNowString, TextToSpeech.QUEUE_FLUSH, null, null);
        }

        while(current_tts.isSpeaking()){}

        if(control == 0){
            StopRecord();
        }else{
            StartRecord();
        }

    }

    public void send_result (String result){
        this.result = result;
    }

    public void setData (recipe_data input) {
        this.data = input;
        current_index = 0;
        maxIndex = this.data.getMaxPage();
        recipeNowString = this.data.getRecipeData(current_index);
    }

    public TextToSpeech speak_set(){
        tts = new TextToSpeech(mainActivity.getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {

                tts_init = true;

                if (status != ERROR){
                    int result = tts.setLanguage(Locale.KOREA); // 언어 선택
                    if(result == TextToSpeech.LANG_NOT_SUPPORTED || result == TextToSpeech.LANG_MISSING_DATA){
                        Log.e("TTS", "This Language is not supported");
                    }
                }else{
                    Log.e("TTS", "Initialization Failed!");
                }
            }
        });

        return tts;

    }
}