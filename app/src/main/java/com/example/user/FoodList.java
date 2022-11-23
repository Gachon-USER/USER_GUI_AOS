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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class FoodList extends Fragment {

    MainActivity mainActivity;

    JSONObject jsonObject = null;
    List<recipe_data> recipeList = null;   // 현재는 대충 List 형태로 때웠지만 추후 서버 구조가 확립되고 + UI 리스트 표시 형태도 정해지면 변동 해야함.
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

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.fragmentChange(1);
            }

        });

        Button button_ex = rootView.findViewById(R.id.toErl);

        button_ex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.frameLayout7.setData(recipeList.get(0)); // 지금은 그냥 0번 인덱스로 퉁쳐져 있으나 차후 해당 아이템의 recipe_data 인덱스를 찾아서 넘겨줘야 함.
                mainActivity.fragmentChange(7);
                //일단 지금은 방식 전검을 위해 이런식으로 아직 빈 버튼에 구현하였지만 추후 보여줄 아이템 별로 해당 아이템에 같은 구조로 로직 처리 해야함
            }

        });

        return rootView;

    }

    public void make_recipe_data(recipeCooking[] cookings, recipeIngredient[] ingredients,recipe_info info){

        String[] ingredient = new String[ingredients.length];
        String[] ingredient_cp = new String[ingredients.length];
        String[] recipe = new String[cookings.length];

        for (recipeCooking cooking : cookings) {
            int idx = cooking.getCooking_order_no();
            String tmp = cooking.getCooking_order();
            recipe[idx] = tmp;
        }

        for(int i=0;i<ingredients.length;i++){
            String name_tmp = ingredients[i].getIngredient_Name();
            String cp_tmp = ingredients[i].getGetIngredient_Cp();
            ingredient[i] = name_tmp;
            ingredient_cp[i] = cp_tmp;
        }

        recipe_data tmp = new recipe_data(ingredient,ingredient_cp,recipe, cookings.length, ingredients.length,info);

        recipeList.add(tmp);
    }

    public void get_recipe_data(){

        if(recipeList !=null){
            recipeList = null;
        }

        String Url = "http://10.0.2.2:8080/android";
        String JSON = "";
        mainActivity.sendHttpApi(JSON,Url,102);
    }

    public String[] getStringvalues(JSONObject jsonObject){//레시피 단계별 설명만 return
        Log.d("AG", "함수호출됨");
        List<String> recipeString = new ArrayList<String>();
        try {
            JSONArray arr = (JSONArray)jsonObject.get("recipe");
            List<JSONObject> copyList = new ArrayList<JSONObject>();

            for (int i=0; i<arr.length(); i++){
                copyList.add((JSONObject) arr.get(i)); // list 에 삽입 실시
            }

            for (JSONObject item:copyList) {
                recipeString.add(String.valueOf(item.get("COOKING_DC")));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        String[] recipespringarray = recipeString.toArray(new String[0]);
        Log.d("s", Arrays.toString(recipespringarray));
        return recipespringarray;
    } // 해당 함수 구조 자체가 처음 접근되는 0번 레시피 데이터에 국한되어 동작하는것으로 보임. => 차후 변경 작업 필요.

    public void send_result (String result){
        try {
            JSONObject jsonObject = new JSONObject(result);
        } catch (JSONException e) {
            Log.d("json_error","String to json Object fail");
            e.printStackTrace();
        }
        this.jsonObject = jsonObject;

        String[] tmp_recipe ;
        String[] tmp_item = {"1","2","3"};
        int tmp_maxRecipe = 5;
        int tmp_maxItem = 3;

        tmp_recipe = getStringvalues(jsonObject);
        //recipe_data tmp = new recipe_data(tmp_item,tmp_recipe,tmp_maxRecipe, tmp_maxItem);

        //recipeList.add(tmp);

    } // http_protocol 부분을 Httpjson 으로 변경 (Asynctask 관련 문제로 인한 미지원)

    public int howmanypages(JSONObject jsonObject){//레시피 단계가 몇단계인지
        int i=0;
        try {
            JSONArray arr = (JSONArray)jsonObject.get("recipe");
            for(i=0;i<arr.length();i++){
                Log.d("AG", String.valueOf(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return i;
    } // 이건 아직 미작동 이지만 일단 옴겨와둠.
}