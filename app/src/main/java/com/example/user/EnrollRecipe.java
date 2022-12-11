package com.example.user;

import static android.app.Activity.RESULT_OK;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;


public class EnrollRecipe extends Fragment {

    Button button2;
    ImageView imageView;
    EditText recipe_name;
    EditText recipe_Ingredients;
    EditText recipe_Cooking;
    Uri selectedImageUri;

    private static final int REQUEST_CODE = 0;

    MainActivity mainActivity;
    recipe_info recipeInfo;
    ArrayList<recipeIngredient> recipeIngredients;
    ArrayList<recipeCooking> recipeCookings;
    String name;
    String url;
    JSONArray jsonArray = new JSONArray();
    JsonParser jsonParser = new JsonParser();
    JSONObject jsoned= new JSONObject();
    String saveRecipe;
    // 메인 액티비티 위에 올린다.
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

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_enroll_recipe, container, false);

        recipe_name = rootView.findViewById(R.id.name);
        recipe_Ingredients = rootView.findViewById(R.id.id1);
        recipe_Cooking = rootView.findViewById(R.id.id2);

        Button button = rootView.findViewById(R.id.bb);//뒤로가기 버튼
//        name = nameText.get
//        Button saveButton = rootView.findViewById(R.id.save);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mainActivity.fragmentChange(1);
            }

        });

        imageView = rootView.findViewById(R.id.poster);//이미지띄우는 뷰

        Button button2 = rootView.findViewById(R.id.selectim);

        button2.setOnClickListener(new View.OnClickListener() { //갤러리에 요청코드 보내기
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.setType("image/*");
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//                startActivityForResult(intent, REQUEST_CODE);
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, 2);
            }
        });



        ImageButton button6 = rootView.findViewById(R.id.toList);

        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.fragmentChange(2);
            }

        });

        ImageButton button7 = rootView.findViewById(R.id.toM);

        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.fragmentChange(4);
            }

        });

        ImageButton button8 = rootView.findViewById(R.id.toH);

        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.fragmentChange(1);
            }

        });

        ImageButton button9 = rootView.findViewById(R.id.toT);

        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.fragmentChange(3);
            }
        });


        ImageButton button10 = rootView.findViewById(R.id.toE);

        button10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.fragmentChange(5);

            }

        });

        Button saveButton = rootView.findViewById(R.id.submit);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = recipe_name.getText().toString();
                String Url =" www/";//임시데이터(사진주소)

                String ingredient = recipe_Ingredients.getText().toString();
                String[] ing_tmp = ingredient.split(",");

                String cooking = recipe_Cooking.getText().toString();
                String[] cook_tmp = cooking.split(",");
                Log.d("haha", Arrays.toString(cook_tmp));

                StringToObj(name,Url,ing_tmp,cook_tmp);
                JSONObject jsonedrecipedata = null;
                try {
                    jsonedrecipedata = jsonParse(recipeInfo,recipeIngredients,recipeCookings);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                String server_Url = " http://10.0.2.2:8080/android/saveRecipe";
                Log.d("jsoned data",jsonedrecipedata.toString());

                upload_recipe(jsonedrecipedata,server_Url);
                clickUpload();
                selectedImageUri = null;
            }
        });

        return rootView;
    }


    public void upload_recipe(JSONObject send_json,String server_Url) {

        mainActivity.sendHttpApi(send_json.toString(),server_Url,105,-1);

    }

    public void send_result(int control){

        if(control==0)//저장이 안되었을때
        {
            this.control(false);
            Toast.makeText(mainActivity.getApplicationContext(), "저장이 안됨", Toast.LENGTH_SHORT).show();
        }
        if(control==1)//저장이 잘되었을때
        {
            this.control(true);
            Toast.makeText(mainActivity.getApplicationContext(), "recipe saved!", Toast.LENGTH_SHORT).show();
        }
    }

    public void control(boolean tmp){

        if(tmp){//저장이 되었을때
            Log.d("saved","저장이 잘 되었음");
            recipe_name.setText("");
            recipe_Ingredients.setText("");
            recipe_Cooking.setText("");
            mainActivity.fragmentChange(1);//메인프레그먼트로 이동
        }
        else{//저장이 안되었을때
            recipe_name.setText("");
            recipe_Ingredients.setText("");
            recipe_Cooking.setText("");
        }
    }

    public void StringToObj(String name, String Url,String[] recipe_ingredient,String[] recipe_cooking){

        recipeInfo = new recipe_info(name,Url);
        recipeIngredients = new ArrayList<recipeIngredient>();
        recipeCookings = new ArrayList<recipeCooking>();

        for(String ingredient : recipe_ingredient){
            String[] tmp = ingredient.split("/");
//            Log.d("haha", Arrays.toString(tmp));
            recipeIngredient ri=new recipeIngredient(tmp[0],tmp[1]);
            recipeIngredients.add(ri);
        }

        for(String cooking : recipe_cooking){
            String[] tmp1 = cooking.split("/");
            Log.d("haha", Arrays.toString(tmp1));

            recipeCooking rc=new recipeCooking(tmp1[1],Integer.parseInt(tmp1[0]));
            recipeCookings.add(rc);
        }
    }
    public JSONObject jsonParse(recipe_info recipeInfo,ArrayList<recipeIngredient> recipeIngredients,
                                 ArrayList<recipeCooking> recipeCookings) throws JSONException {
        JSONArray jsonArray = new JSONArray();
        JSONArray jsonArray1 = new JSONArray();

        JsonParser jsonParser = new JsonParser();
        JSONObject recipejson= new JSONObject();
        Gson gson = new Gson();

        String recipeinfoString = gson.toJson(recipeInfo);
//        JSONObject json=new JSONObject();
//        json = (JSONObject)jsonParser.parse(recipeinfoString)
        recipejson.put("recipe_info",recipeinfoString);

        for(recipeIngredient ingredient:recipeIngredients){
            String jsonString = gson.toJson(ingredient);

//            JSONObject recipeingredientjson=new JSONObject();
//            recipeingredientjson = (JSONObject)jsonParser.parse(jsonString);
////                System.out.println(json instanceof JSONObject);
            jsonArray.put(jsonString);
        }
        recipejson.put("recipe_ingredient",jsonArray);

        for(recipeCooking cooking:recipeCookings){
            String jsonString = gson.toJson(cooking);

//            JSONObject recipecookingjson=new JSONObject();
//            recipecookingjson = (JSONObject)jsonParser.parse(jsonString);
////                System.out.println(json instanceof JSONObject);
            jsonArray1.put(jsonString);
        }
        recipejson.put("recipe_cooking",jsonArray1);


        return recipejson;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            if(resultCode == RESULT_OK && data.getData() != null) {
                selectedImageUri = data.getData();
                Log.d("img", "MainActivity - onActivityResult() called" + selectedImageUri);
//                Log.d(TAG, "MainActivity - onActivityResult() called" + getRealPathFromURI(selectedImageUri));

                Glide.with(getActivity())
//                        .load(getRealPathFromURI(selectedImageUri))
                        .load(selectedImageUri)
                        .into(imageView);

            }
            try {
                Uri uri = data.getData();
                //Glide.with(getActivity()).load(uri).into(imageView); //다이얼로그 이미지사진에 넣기

            } catch (Exception e) {


            }
        }
    }

//    public String getRealPathFromURI(Uri contentUri) {//절대경로로 변경
//
//        String[] proj = { MediaStore.Images.Media.DATA };
//
//        Cursor cursor = getActivity().getContentResolver().query(contentUri, proj, null, null, null);
//        cursor.moveToNext();
//        String path = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA));
//        Uri uri = Uri.fromFile(new File(path));
//
//        cursor.close();
//        return path;
//    }
    public void clickUpload() {

        // 1. FirebaseStorage을 관리하는 객체 얻어오기
        FirebaseStorage firebaseStorage= FirebaseStorage.getInstance();

        // 2. 업로드할 파일의 node를 참조하는 객체
        // 파일 명이 중복되지 않도록 날짜를 이용
        SimpleDateFormat sdf= new SimpleDateFormat("yyyyMMddhhmmss");
        String filename= sdf.format(new Date())+ ".png";
        // 현재 시간으로 파일명 지정 20191023142634
        // 원래 확장자는 파일의 실제 확장자를 얻어와서 사용해야함. 그러려면 이미지의 절대 주소를 구해야함.

        StorageReference imgRef= firebaseStorage.getReference("uploads/"+filename);
        // uploads라는 폴더가 없으면 자동 생성

        // 참조 객체를 통해 이미지 파일 업로드
        // imgRef.putFile(imgUri);
        // 업로드 결과를 받고 싶다면 아래와 같이 UploadTask를 사용하면 된다.
        UploadTask uploadTask =imgRef.putFile(selectedImageUri);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(mainActivity.getApplicationContext(), "success upload", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("img", "MainActivity - onFailure() called");
                    }
                });
    }
}


