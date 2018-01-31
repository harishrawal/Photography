package com.app.winklix.photography;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONObject;

/**
 * Created by Administrator on 1/12/2018.
 */

public class FBprofile extends AppCompatActivity {
    JSONObject response, profile_pic_data, profile_pic_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fb_user_profile);
        Intent intent = getIntent();
        String jsondata = intent.getStringExtra("userProfile");
        Log.w("Jsondata", jsondata);
        TextView user_name = (TextView) findViewById(R.id.UserName);
        ImageView user_picture = (ImageView) findViewById(R.id.profilePic);
        // TextView user_email = (TextView) findViewById(R.id.email);
        try {
            response = new JSONObject(jsondata);
            //user_email.setText(response.get("email").toString());

            String fb_id = response.get("id").toString();
            Toast.makeText(getApplicationContext(),"fb_id : "+fb_id,Toast.LENGTH_LONG).show();
            System.out.println("fb_id"+fb_id);

            user_name.setText(response.get("name").toString());
            profile_pic_data = new JSONObject(response.get("picture").toString());
            profile_pic_url = new JSONObject(profile_pic_data.getString("data"));
            Picasso.with(this).load(profile_pic_url.getString("url")).into(user_picture);

        } catch(Exception e){
            e.printStackTrace();
        }
    }


}
