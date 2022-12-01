package com.example.user;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class EnrollRecipe extends Fragment {

    MainActivity mainActivity;
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

        Button button = rootView.findViewById(R.id.bb);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.fragmentChange(1);
            }

        });

        return rootView;
    }

    public void upload_recipe(String Name, String Url, String[] recipe_cook, String[] recipe_ingredient, String[] ingredient_cp){

        JSONObject send_json = new JSONObject();

        try {

            send_json.put("recipeName",Name);
            send_json.put("recipeUrl",Url);

            JSONArray cook = new JSONArray();

            for(int i=0;i<recipe_cook.length;i++){
                JSONObject tmp_cook = new JSONObject();
                tmp_cook.put("recipe_String",recipe_cook[i]);
                tmp_cook.put("recipe_no",i);

                cook.put(tmp_cook);
            }

            send_json.put("recipe",cook);

            JSONArray ingredient = new JSONArray();

            for(int i=0;i<recipe_ingredient.length;i++){
                JSONObject tmp_ingredient = new JSONObject();
                tmp_ingredient.put("ingredient_Name",recipe_ingredient[i]);
                tmp_ingredient.put("ingredient_Cp",ingredient_cp[i]);
                tmp_ingredient.put("ingredient_no",i);

                ingredient.put(ingredient);
            }

            send_json.put("ingredient",ingredient);

        } catch (JSONException e) {
            Log.d("json_error", "String to json Object fail");
            e.printStackTrace();
        }

        String server_Url = "http://172.30.1.52:8080/android/recipeadd";
        mainActivity.sendHttpApi(send_json.toString(),server_Url,105,-1);

    }

    public void send_result(String result){
        Log.d("result",result);
    }
}