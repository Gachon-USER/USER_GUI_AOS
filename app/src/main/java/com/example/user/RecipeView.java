package com.example.user;

import android.content.Context;
import android.os.AsyncTask;
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

public class RecipeView extends Fragment {


    MainActivity mainActivity;
    String result;
    recipe_data data;
    int index;

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
        // 이 하단 블럭은 테스트위해 임시로 제작한 레시피 데이터셋 더미


        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_recipe_view, container, false);

        Button button = rootView.findViewById(R.id.tobc);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.fragmentChange(2);
            }

        });

        Button button2 = rootView.findViewById(R.id.strt);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mainActivity.frameLayout6.setData(data);
                mainActivity.fragmentChange(6);

            }

        });

        return rootView;

    }

    public void setData(recipe_data tmp){
        this.data = tmp;
    }

    public void send_result (String result){
        this.result = result;
    }

}

