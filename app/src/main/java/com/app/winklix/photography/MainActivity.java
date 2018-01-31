package com.app.winklix.photography;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.winklix.photography.Multi_Image_Picker.CustomAdapter;
import com.app.winklix.photography.Multi_Image_Picker.Spacecraft;
import com.app.winklix.photography.util.DbHelper;
import com.app.winklix.photography.util.Utility;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import droidninja.filepicker.FilePickerBuilder;
import droidninja.filepicker.FilePickerConst;

public class MainActivity extends AppCompatActivity  {
    private static int LOAD_IMAGE_RESULTS = 2;
    private TextView add_photos;
    private ImageView image;
    private LinearLayout proceed;
    private Context context;
    static private SQLiteDatabase dataBase;
    static private DbHelper mHelper;
    ArrayList<String> filePaths=new ArrayList<String>();
    GridView gv;
    static private boolean isUpdate;
    static  String id;
    CallbackManager callbackManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this);
        setContentView(R.layout.activity_main);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());


        callbackManager = CallbackManager.Factory.create();
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if (accessToken != null) {if (!accessToken.isExpired()) {System.out.println("access_photo" +accessToken);}
        }

        mHelper=new DbHelper(this);
        add_photos = (TextView) findViewById(R.id.add_photos);
        image = (ImageView)findViewById(R.id.image);
        proceed = (LinearLayout) findViewById(R.id.proceed);
        context = MainActivity.this;

        gv= (GridView) findViewById(R.id.gv);

        add_photos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean result = Utility.checkPermission(MainActivity.this);
                if (result) {
                    filePaths.clear();
                    FilePickerBuilder.getInstance().setMaxCount(3)
                            .setSelectedFiles(filePaths)
                            .setActivityTheme(R.style.AppTheme)
                            .pickPhoto(MainActivity.this);
                }
                else  Toast.makeText(context,"permission required",Toast.LENGTH_SHORT).show();
            }
        });
        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context,Cart_Activity.class));}
        });

        ImageView loginButton = (ImageView) findViewById(R.id.fb);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logInWithReadPermissions(MainActivity.this, Arrays.asList("public_profile", "email"));
                LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.i("MainActivity", "@@@onSuccess");
                        GraphRequest request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(final JSONObject object, GraphResponse response) {

                                        new GraphRequest(
                                                AccessToken.getCurrentAccessToken(),
                                                "me/albums",
                                                null,
                                                HttpMethod.GET,
                                                new GraphRequest.Callback() {
                                                    public void onCompleted(GraphResponse response) {

                                                        System.out.println("12121211"+id+":::"+response);
                                                        /*try {
                                                            JSONObject   response12 = new JSONObject(String.valueOf(response));
                                                            String id = response12.get("id").toString();
                                                        } catch (JSONException e) {
                                                            e.printStackTrace();
                                                        }*/
                                                    }
                                                }
                                        ).executeAsync();


                                        Log.i("MainActivity", "@@@response: " + response.toString());
                                        try {
                                            String name = object.getString("name");
                                            Intent intent = new Intent(MainActivity.this, FBprofile.class);
                                            intent.putExtra("userProfile", object.toString());
                                            startActivity(intent);
                                            System.out.println("name_name"+name);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name,picture.width(160).height(160)");
                        request.setParameters(parameters);
                        request.executeAsync();
                    }
                    @Override
                    public void onCancel() {Log.i("MainActivity", "@@@onCancel");}
                    @Override
                    public void onError(FacebookException error) {Log.i("MainActivity", "@@@onError: "+ error.getMessage());}});}});

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        switch (requestCode)
        {
            case FilePickerConst.REQUEST_CODE:
                if(resultCode==RESULT_OK && data!=null)
                {
                    filePaths = data.getStringArrayListExtra(FilePickerConst.KEY_SELECTED_PHOTOS);
                    Spacecraft s;
                    ArrayList<Spacecraft> spacecrafts=new ArrayList<>();
                    try
                    {
                        for (String path:filePaths) {
                            s=new Spacecraft();
                            s.setName(path.substring(path.lastIndexOf("/")+1));
                            s.setUri(Uri.fromFile(new File(path)));
                            spacecrafts.add(s);
                            Log.e("images got from storage",spacecrafts.toString());

                            dataBase=mHelper.getWritableDatabase();
                            ContentValues values=new ContentValues();
                            values.put(DbHelper.KEY_PNAME,s.getName());
                            values.put(DbHelper.KEY_PRICE,"200" );
                            values.put(DbHelper.KEY_QTY,"1" );
                            values.put(DbHelper.KEY_IMAGE, String.valueOf(s.getUri()));
                            values.put(DbHelper.KEY_SUM,"200");
                            System.out.println("");
                            if(isUpdate)
                            {dataBase.update(DbHelper.TABLE_NAME, values, DbHelper.KEY_ID+"="+id, null);}
                            else {
                                dataBase.insert(DbHelper.TABLE_NAME, null, values);
                                Toast.makeText(getApplicationContext(),"Add", Toast.LENGTH_LONG).show();}
                                dataBase.close();
                        }

                        gv.setAdapter(new CustomAdapter(this,spacecrafts));
                        Toast.makeText(MainActivity.this, "Total = "+String.valueOf(spacecrafts.size()), Toast.LENGTH_SHORT).show();
                    }catch (Exception e)
                    {
                        e.printStackTrace();}
                }}}

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {}
}
