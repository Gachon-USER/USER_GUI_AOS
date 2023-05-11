package com.example.user;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Main extends Fragment {

    MainActivity mainActivity;
    ArrayList<user_info> contentRecipeList;
    ArrayList<user_info> userRecipeList;
    user_info selected_info;
    ArrayList<recipeIngredient> ingredient_list;
    ArrayList<recipeCooking> cooking_list;
    RecipeAdapter contentRecipeAdapter;
    RecipeAdapter userRecipeAdapter;
    String user_id;

   @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity) getActivity();

        int[] ptr = new int[1];
        ptr[0] = mainActivity.lastfood;

        get_recipe_data(1010,-1,ptr);
    }

    // 메인 액티비티에서 내려 온다.
    @Override
    public void onDetach() {
        super.onDetach();
        mainActivity = null;
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_main, container, false);

        TextView Last_info = rootView.findViewById(R.id.Last_info);

        TextView Last_tag = rootView.findViewById(R.id.Last_tag);

//        Last_info.setText(selected_info.getName());

//        Last_tag.setText(selected_info.getTag());

        ImageButton button2 = rootView.findViewById(R.id.toL);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.fragmentChange(2,null);
            }

        });

        ImageButton button3 = rootView.findViewById(R.id.toM);

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.fragmentChange(4,null);
            }

        });

        ImageButton button4 = rootView.findViewById(R.id.toH);

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.fragmentChange(1,null);
            }

        });

        ImageButton button15 = rootView.findViewById(R.id.toT);

        button15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.fragmentChange(3,null);
            }

        });


        ImageButton button16 = rootView.findViewById(R.id.toE);

        button16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.fragmentChange(5,null);

            }

        });


        Button button5 = rootView.findViewById(R.id.vsrc);

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.fragmentChange(6,null);
            }

        });


        Button button6 = rootView.findViewById(R.id.src);

        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.fragmentChange(2,null);

            }

        });



        ImageButton korCategory = rootView.findViewById(R.id.kor);

        korCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle data = new Bundle(1);
                data.putString("Category","kor");
                mainActivity.fragmentChange(2,data);

            }

        });




        ImageButton japCategory = rootView.findViewById(R.id.jap);

        japCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle data = new Bundle(1);
                data.putString("Category","jap");
                mainActivity.fragmentChange(2,data);

            }

        });


        ImageButton chinaCategory = rootView.findViewById(R.id.chi);

        chinaCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle data = new Bundle(1);
                data.putString("Category","chi");
                mainActivity.fragmentChange(2,data);
            }

        });




        ImageButton westCategory = rootView.findViewById(R.id.wes);

        westCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle data = new Bundle(1);
                data.putString("Category","wes");
                mainActivity.fragmentChange(2,data);

            }

        });




        ImageButton chickenCategory = rootView.findViewById(R.id.ckn);

        chickenCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle data = new Bundle(1);
                data.putString("Category","ckn");
                mainActivity.fragmentChange(2,data);

            }

        });




        ImageButton cowCategory = rootView.findViewById(R.id.cow);

        cowCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle data = new Bundle(1);
                data.putString("Category","cow");
                mainActivity.fragmentChange(2,data);

            }

        });


        ImageButton pigCategory = rootView.findViewById(R.id.pig);

        pigCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle data = new Bundle(1);
                data.putString("Category","pig");
                mainActivity.fragmentChange(2,data);

            }

        });




        ImageButton sheepCategory = rootView.findViewById(R.id.she);

        sheepCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle data = new Bundle(1);
                data.putString("Category","she");
                mainActivity.fragmentChange(2,data);

            }

        });





        ImageButton spicyCategory = rootView.findViewById(R.id.spi);

        spicyCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle data = new Bundle(1);
                data.putString("Category","spi");
                mainActivity.fragmentChange(2,data);

            }

        });




        ImageButton saltCategory = rootView.findViewById(R.id.sal);

        saltCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle data = new Bundle(1);
                data.putString("Category","sal");
                mainActivity.fragmentChange(2,data);

            }

        });


        ImageButton sweetCategory = rootView.findViewById(R.id.swe);

        sweetCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle data = new Bundle(1);
                data.putString("Category","swe");
                mainActivity.fragmentChange(2,data);

            }

        });




        ImageButton souCategory = rootView.findViewById(R.id.sou);

        souCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle data = new Bundle(1);
                data.putString("Category","sou");
                mainActivity.fragmentChange(2,data);

            }

        });

        Button button55 = rootView.findViewById(R.id.logout);

        button55.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.fragmentChange(0,null);

            }

        });



        ListView contentlistView = rootView.findViewById(R.id.recommend_content);
        ListView userlistView = rootView.findViewById(R.id.recommend_user);

        this.InitializeRecipeData();

//        this.get_recommend(selected_info.getID(),user_id,0,2);

        contentRecipeAdapter = new RecipeAdapter(getActivity(), contentRecipeList);
        userRecipeAdapter = new RecipeAdapter(getActivity(), userRecipeList);

        contentlistView.setAdapter(contentRecipeAdapter);
        userlistView.setAdapter(userRecipeAdapter);

        contentlistView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id){

                selected_info = contentRecipeList.get(position);
                Log.d("sel_info",Integer.toString(selected_info.getID()));
                int ID = selected_info.getID();
                load_recipe(ID);

            }
        });

        userlistView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id){

                selected_info = userRecipeList.get(position);
                Log.d("sel_info",Integer.toString(selected_info.getID()));
                int ID = selected_info.getID();
                load_recipe(ID);

            }
        });

        return rootView;
    }

    public void load_recipe(int ID){
        // 추가 개발 필요 (스프링 서버 http 요청, ID 값에 해당하는 레시피 추가 데이터 호출 및 데이터화.)
        // 필요 데이터를 어캐 끌어다 가져오는가에 대해선 위에 tmp 함수 형태처럼 만들면 됨.

        get_recipe_data(1012,ID,null);

        get_recipe_data(1013,ID,null);

    }

    public void InitializeRecipeData()
    {
        this.contentRecipeList = new ArrayList<user_info>();
        this.userRecipeList = new ArrayList<user_info>();
    }

    public void get_recommend(int recipe_id,String user_id,int type, int num){

        String Url = "http://9059-35-237-67-214.ngrok.io/recommend";
        mainActivity.sendHttpApi("{\"last_ID\" : "+ Integer.toString(recipe_id) +",\"user_ID\" : "+ user_id +",\"type\" : "+ Integer.toString(type) +",\"num\" : "+ Integer.toString(num) +"}",Url,101,recipe_id);

    }

    public void get_recipe_data(int con, int ID,int[] input){
        String Url = "http://10.0.2.2:8080/android";
        String JSON = null;
        try{
            switch (con){
                case 1010:
                    Url = Url + "/recipeListFind";

                    JSONObject tmp = new JSONObject();
                    JSONArray tmp_arr = new JSONArray();
                    for(int i : input){
                        JSONObject input_tmp = new JSONObject();
                        input_tmp.put("ID",i);
                        tmp_arr.put(input_tmp);
                    }
                    tmp.put("find",tmp_arr);
                    JSON = tmp.toString();
                    break;
                case 1011:
                    Url = Url + "/recipeListFind";

                    JSONObject ptr = new JSONObject();
                    JSONArray ptr_arr = new JSONArray();
                    for(int i : input){
                        JSONObject input_tmp = new JSONObject();
                        input_tmp.put("ID",i);
                        ptr_arr.put(input_tmp);
                    }
                    ptr.put("find",ptr_arr);
                    JSON = ptr.toString();
                    break;
                case 1012:
                    Url = Url + "/recipeIngredient";
                    JSON = "";
                    break;
                case 1013:
                    Url = Url + "/recipeCooking";
                    JSON = "";
                    break;
                case 1014:
                    Url = Url + "/recipeListFind";

                    JSONObject ptr_2 = new JSONObject();
                    JSONArray ptr_arr_2 = new JSONArray();
                    for(int i : input){
                        JSONObject input_tmp = new JSONObject();
                        input_tmp.put("ID",i);
                        ptr_arr_2.put(input_tmp);
                    }
                    ptr_2.put("find",ptr_arr_2);
                    JSON = ptr_2.toString();
                    break;
            }
        }catch (JSONException e){
            e.printStackTrace();
        }

        mainActivity.sendHttpApi(JSON,Url,con,ID);
    }

    public void set_recipe_info_list(JSONObject input, int con){

        try {
            JSONArray list = input.getJSONArray("recipelist");

            for(int i=0;i< list.length();i++){
                JSONObject tmp = list.getJSONObject(i);

                int tmp_ID = tmp.getInt("ID");
                String tmp_image_url = tmp.getString("imgsrc");
                String tmp_Name = tmp.getString("Name");
                String tmp_tag = tmp.getString("recipe_tag");

                user_info tmp_info = new user_info(tmp_ID,tmp_image_url,tmp_Name,tmp_tag);
                if(con == 0){
                    contentRecipeList.add(tmp_info);
                }else if(con == 1){
                    userRecipeList.add(tmp_info);
                }else if(con == 4){
                    selected_info = tmp_info;
                }
            }

        } catch (JSONException e) {
            Log.d("json_error", "String to json Object fail");
            e.printStackTrace();
        }
        this.contentRecipeAdapter.notifyDataSetChanged();
        this.userRecipeAdapter.notifyDataSetChanged();

    }

    public ArrayList<recipeIngredient> set_recipeIngredient_list(JSONObject input){

        ArrayList<recipeIngredient> ingredient_list = new ArrayList<recipeIngredient>();

        try {
            JSONArray list = input.getJSONArray("recipeIngredient");

            for(int i=0;i< list.length();i++){
                JSONObject tmp = list.getJSONObject(i);

                int tmp_idx = tmp.getInt("idx_ing");
                String tmp_Name = tmp.getString("ingredient_name");

                recipeIngredient tmp_ingredient = new recipeIngredient(tmp_idx,tmp_Name,-1);

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
            Log.d("result_json",result);
            jsonObject = new JSONObject(result);
        } catch (JSONException e) {
            Log.d("json_error","String to json Object fail1");
            e.printStackTrace();
        }

        if(con == 0){
            set_recipe_info_list(jsonObject,con);
        }else if (con == 1){
            set_recipe_info_list(jsonObject,con);
        }else if (con == 2){
            this.ingredient_list = set_recipeIngredient_list(jsonObject);
        }else if (con == 3){
            this.cooking_list = set_recipeCooking_list(jsonObject);
        }else if (con == 4){
            set_recipe_info_list(jsonObject,con);
        }

        if(ingredient_list != null && cooking_list !=null){
            recipe_data ptr = new recipe_data(cooking_list,ingredient_list,selected_info);

            mainActivity.frameLayout7.setData(ptr);
            mainActivity.fragmentChange(7,null);
        }

    }

    public void send_recommand_result(String result){

        JSONObject recommand;

        int[] content_base_list = null;

        int[] user_base_list = null;

       try{
           recommand = new JSONObject(result);

           int num = recommand.getInt("num");

           JSONArray content = recommand.getJSONArray("content_base");
           JSONArray user = recommand.getJSONArray("user_base");

           content_base_list = new int[num];
           user_base_list = new int[num];

           for(int i=0;i<num;i++){
               content_base_list[i] = content.getInt(i);
               user_base_list[i] = user.getInt(i);
           }


       }catch (JSONException e){
           Log.d("json_error","err in recommand json");
           e.printStackTrace();
       }

       get_recipe_data(1010,-1,content_base_list);
       get_recipe_data(1011,-1,user_base_list);

    }


}