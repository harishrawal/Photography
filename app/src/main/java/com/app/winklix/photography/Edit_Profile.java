package com.app.winklix.photography;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Edit_Profile extends AppCompatActivity {
    private Context context;
    private LinearLayout proceed;
    TextView json_result, name, mobile,email_id,address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__profile);
        context = Edit_Profile.this;

        name = findViewById(R.id.name);
        mobile= findViewById(R.id.mobile);
        email_id= findViewById(R.id.email_id);
        address= findViewById(R.id.address);

        json_result= findViewById(R.id.json_result);
        String cart_order = getIntent().getStringExtra("cart_order");
    //  Toast.makeText(context, cart_order, Toast.LENGTH_SHORT).show();
        System.out.println("cart_order"+cart_order);

    //  json_result.setText(cart_order);
        proceed = findViewById(R.id.proceed);
        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Payment .... ",Toast.LENGTH_SHORT).show();
            }
        });

    }

}
