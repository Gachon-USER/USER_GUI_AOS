package com.example.user;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class FoodList extends Fragment {


    ArrayList<recipe_info> recipeDataList;

    MainActivity mainActivity;

    JSONObject jsonObject = null;
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


        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_food_list, container, false);

        Button button = rootView.findViewById(R.id.tob);

        ListView listView = rootView.findViewById(R.id.listView1);

        this.InitializeRecipeData();

        this.get_recipe_data(102,-1);

        final RecipeAdapter recipeAdapter = new RecipeAdapter(getActivity(), recipeDataList);

        listView.setAdapter(recipeAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id){
                int ID = recipeDataList.get(position).getID();
                mainActivity.frameLayout7.setData(load_recipe(ID));
                //mainActivity.frameLayout7.setData(load_recipe(ID));
                mainActivity.fragmentChange(7);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.fragmentChange(1);
            }

        });

        /*ImageButton button_ex = rootView.findViewById(R.id.toE);

        button_ex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int ID = 0;
                mainActivity.frameLayout7.setData(tmp_load_recipe(ID)); // 지금은 그냥 0번 인덱스로 퉁쳐져 있으나 차후 해당 아이템의 recipe_data 인덱스를 찾아서 넘겨줘야 함.
                mainActivity.fragmentChange(7);
                //일단 지금은 방식 전검을 위해 이런식으로 아직 빈 버튼에 구현하였지만 추후 보여줄 아이템 별로 해당 아이템에 같은 구조로 로직 처리 해야함
            }

        });*/

        return rootView;

    }

    public recipe_data load_recipe(int ID){
        // 추가 개발 필요 (스프링 서버 http 요청, ID 값에 해당하는 레시피 추가 데이터 호출 및 데이터화.)
        // 필요 데이터를 어캐 끌어다 가져오는가에 대해선 위에 tmp 함수 형태처럼 만들면 됨.

        ArrayList<recipeCooking> cooking_list;
        ArrayList<recipeIngredient> ingredient_list;

        get_recipe_data(1021,ID);

        recipe_data ptr = new recipe_data();

        return ptr;
    }

    public void InitializeRecipeData()
    {
        recipeDataList = new ArrayList<recipe_info>();
    }

    public void get_recipe_data(int con, int ID){
        String Url = "http://172.30.1.52:8080/android/recipeList";
        String JSON = "";
        mainActivity.sendHttpApi(JSON,Url,con,ID);
    }

    public void set_recipe_info_list(JSONObject input){

        try {
            JSONArray list = input.getJSONArray("recipelist");

            for(int i=0;i< list.length();i++){
                JSONObject tmp = list.getJSONObject(i);

                int tmp_ID = tmp.getInt("ID");
                String tmp_image_url = tmp.getString("imgsrc");
                String tmp_Name = tmp.getString("Name");

                recipe_info tmp_info = new recipe_info(tmp_ID,tmp_image_url,tmp_Name);

                recipeDataList.add(tmp_info);
            }

        } catch (JSONException e) {
            Log.d("json_error", "String to json Object fail");
            e.printStackTrace();
        }
    }

    public ArrayList<recipeIngredient> set_recipeIngredient_list(JSONObject input){

        ArrayList<recipeIngredient> ingredient_list = new ArrayList<recipeIngredient>();

        try {
            JSONArray list = input.getJSONArray("recipeIngredient");

            for(int i=0;i< list.length();i++){
                JSONObject tmp = list.getJSONObject(i);

                int tmp_idx = tmp.getInt("idx_ing");
                String tmp_Name = tmp.getString("ingredient_name");
                String tmp_CP = tmp.getString("ingredient_Cp");

                recipeIngredient tmp_ingredient = new recipeIngredient(tmp_idx,tmp_Name,tmp_CP,-1);

                ingredient_list.add(tmp_ingredient);
            }

        } catch (JSONException e) {
            Log.d("json_error", "String to json Object fail");
            e.printStackTrace();
        }

        return ingredient_list;
    }

    public ArrayList<recipeCooking> set_recipeCooking_list(JSONObject input){

        ArrayList<recipeCooking> cooking_list = new ArrayList<recipeCooking>();

        try {
            JSONArray list = input.getJSONArray("recipe");

            for(int i=0;i< list.length();i++){
                JSONObject tmp = list.getJSONObject(i);

                int tmp_idx = tmp.getInt("idx");
                String tmp_order = tmp.getString("cooking_order");
                int tmp_no = tmp.getInt("cooking_order_no");

                recipeCooking tmp_cook = new recipeCooking(tmp_idx,tmp_order,tmp_no,-1);

                cooking_list.add(tmp_cook);
            }

        } catch (JSONException e) {
            Log.d("json_error", "String to json Object fail");
            e.printStackTrace();
        }

        return cooking_list;
    }

    public void send_result (String result, int con){

        JSONObject jsonObject = null;

        try {
            jsonObject = new JSONObject(result);
        } catch (JSONException e) {
            Log.d("json_error","String to json Object fail");
            e.printStackTrace();
        }

        switch (con){
            case 0:
                set_recipe_info_list(jsonObject);
                break;
            case 1:
                ArrayList<recipeIngredient> ingredient_list = set_recipeIngredient_list(jsonObject);
                break;
            case 2:
                ArrayList<recipeCooking> cooking_list = set_recipeCooking_list(jsonObject);
                break;
        }

    } // http_protocol 부분을 Httpjson 으로 변경 (Asynctask 관련 문제로 인한 미지원)

}