package com.example.jsonparsing;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private List<UserModel> userModelList;

    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        linearLayout = findViewById(R.id.linearLayout);

        String jsonResult = loadJSONFromAsset();

        try {
            //  JSONObject jsonObject = new JSONObject(loadJSONFromAsset());
            JSONObject jsonObject = new JSONObject(jsonResult);
            String perPage = jsonObject.getString("per_page");

            //fetching array from mu_file.json
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            //int length = jsonArray.length();
            userModelList = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObj = jsonArray.getJSONObject(i);
                String id = jsonObj.getString("id");
                String email = jsonObj.getString("email");
                String firstName = jsonObj.getString("first_name");
                String lastName = jsonObj.getString("last_name");
                String image = jsonObj.getString("avatar");

               /* Gson gson = new Gson();
                UserModel model = gson.fromJson(gson, UserModel.class);*/

                UserModel userModel = new UserModel();
                userModel.setId(id);
                userModel.setEmail(email);
                userModel.setFirstName(firstName);
                userModel.setLastName(lastName);
                userModel.setAvatar(image);

                userModelList.add(userModel);


                String values = String.format("%s %s", "Id " + id, "Email " + email);
                Log.e(TAG, values);
            }


            Log.e(TAG, perPage);


        } catch (JSONException e) {
            e.printStackTrace();
        }


        for (int i = 0; i < userModelList.size(); i++) {
            View linearView = getLayoutInflater().inflate(R.layout.row_list, linearLayout, false);
            TextView tvId = linearView.findViewById(R.id.tv_id);
            TextView tvName = linearView.findViewById(R.id.tv_full_name);
            TextView tvEmail = linearView.findViewById(R.id.tv_email);

            tvId.setText(userModelList.get(i).getId());
            tvName.setText(userModelList.get(i).getFirstName() + " " + userModelList.get(i).getLastName());
            tvEmail.setText(userModelList.get(i).getEmail());
            // linearLayout.removeView(linearView);
            linearLayout.addView(linearView);

        }

    }

    private String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream inputStream = getAssets().open("my_file.json");
            int size = inputStream.available();


            byte[] buffer = new byte[size];

            inputStream.read(buffer);
            inputStream.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return json;
    }
}
