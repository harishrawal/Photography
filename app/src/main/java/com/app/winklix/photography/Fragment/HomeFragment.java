package com.app.winklix.photography.Fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.app.winklix.photography.MainActivity;
import com.app.winklix.photography.R;
import com.app.winklix.photography.util.Methods;
import com.app.winklix.photography.util.NetWorkCheck;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class HomeFragment extends Fragment implements View.OnClickListener {
    private static final String TAG ="TAG" ;
    CardView print_category, calender_category, cup_category;
    Context context;
    Button proceed_calenderr;
    ProgressBar _proProgressBar;
    ArrayList<String> paper_type;
    ArrayList<String> size_type;
    Spinner size_spinner,size_spinner1;
    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        context = getActivity();

        paper_type = new ArrayList<String>();
        size_type = new ArrayList<String>();

        print_category = (CardView) view.findViewById(R.id.print_category);
        calender_category = (CardView) view.findViewById(R.id.calender_category);
        cup_category = (CardView) view.findViewById(R.id.cup_category);


        print_category.setOnClickListener(this);
        calender_category.setOnClickListener(this);
        cup_category.setOnClickListener(this);



        return view;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.print_category:
                //startActivity(new Intent(context, MainActivity.class));

                LayoutInflater inflaterL = getLayoutInflater();
                View alertLayoutL = inflaterL.inflate(R.layout.print_size_dailog, null);
                size_spinner= (Spinner) alertLayoutL.findViewById(R.id.size_spinner);
                _proProgressBar = alertLayoutL.findViewById(R.id.progressBar);
              //  ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.sizelist));
              //  adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                if (NetWorkCheck.checkConnection(getActivity())) {PrintInfo();} else {Toast.makeText(getActivity(),"Internet connection is disable", Toast.LENGTH_LONG).show();}

                AlertDialog.Builder alertL = new AlertDialog.Builder(context);
                alertL.setView(alertLayoutL);
                proceed_calenderr = (Button) alertLayoutL.findViewById(R.id.proceed_printt);
                proceed_calenderr.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(context, MainActivity.class));
                        Toast.makeText(context, "Print", Toast.LENGTH_SHORT).show();
                    }
                });
                // disallow cancel of AlertDialog on click of back button and outside touch
                alertL.setCancelable(true);
                AlertDialog dialogL = alertL.create();
                dialogL.show();

                break;
            case R.id.calender_category:
                LayoutInflater inflaterC = getLayoutInflater();
                View alertLayoutC = inflaterC.inflate(R.layout.calendar_size_dailog, null);
                 size_spinner1 = (Spinner) alertLayoutC.findViewById(R.id.size_spinner1);
              //  ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.sizelist));
              //  adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


                _proProgressBar = alertLayoutC.findViewById(R.id.progressBar);
                if (NetWorkCheck.checkConnection(getActivity())) {CalenderInfo();} else {Toast.makeText(getActivity(),"Internet connection is disable", Toast.LENGTH_LONG).show();}

                AlertDialog.Builder alertC = new AlertDialog.Builder(context);

                alertC.setView(alertLayoutC);
                proceed_calenderr = (Button) alertLayoutC.findViewById(R.id.proceed_calenderr);
                proceed_calenderr.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(context, MainActivity.class));
                        Toast.makeText(context, "Calender", Toast.LENGTH_SHORT).show();
                    }
                });
                // disallow cancel of AlertDialog on click of back button and outside touch
                alertC.setCancelable(true);

                AlertDialog dialogC = alertC.create();
                dialogC.show();

                break;
            case R.id.cup_category:
                LayoutInflater inflaterCu = getLayoutInflater();
                View alertLayoutCu = inflaterCu.inflate(R.layout.cup_size_dailog, null);

                AlertDialog.Builder alertCu = new AlertDialog.Builder(context);

                alertCu.setView(alertLayoutCu);
                proceed_calenderr = (Button) alertLayoutCu.findViewById(R.id.proceed_calenderr);
                proceed_calenderr.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(context, MainActivity.class));
                        Toast.makeText(context, "Cup", Toast.LENGTH_SHORT).show();
                    }
                });
                // disallow cancel of AlertDialog on click of back button and outside touch
                alertCu.setCancelable(true);

                AlertDialog dialogCu = alertCu.create();
                dialogCu.show();
                break;
        }
    }




    public void PrintInfo(){
        final String url = "https://quarkbackend.com/getfile/rawalhar91/photo-print";
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject json = new JSONObject(response.toString());
                            JSONArray print = json.getJSONArray("print");

                            for(int i=0;i<print.length();i++){
                                JSONObject jsonnew=print.getJSONObject(i);
                                JSONArray papertype = jsonnew.getJSONArray("papertype");
                                for(int j=0;j<papertype.length();j++){
                                    JSONObject jsonnewthree=papertype.getJSONObject(j);
                                    String str_data=jsonnewthree.getString("type");
                                 //   paper_type.add(str_data);
                                }
                              //  size_spinner.setAdapter(new ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, paper_type));
                            }

                            for(int i=0;i<print.length();i++){
                                JSONObject jsonnew=print.getJSONObject(i);
                                JSONArray papertype = jsonnew.getJSONArray("size");
                                for(int j=0;j<papertype.length();j++){
                                    JSONObject jsonnewthree=papertype.getJSONObject(j);
                                    String str_data=jsonnewthree.getString("size");
                                    paper_type.add(str_data);
                                }
                                size_spinner.setAdapter(new ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, paper_type));
                              // size_spinner1.setAdapter(new ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, paper_type));
                            }

                        } catch (JSONException e) {
                            Log.i(TAG, "" + e.getLocalizedMessage());
                        }
                        Log.d("Response", response.toString());
                        _proProgressBar.setVisibility(View.GONE);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error != null && error.getMessage() != null) {
                            Log.e("Response error", error.getMessage());
                        }

                        NetworkResponse networkResponse = error.networkResponse;
                        String errorMessage = "Unknown error";
                        if (networkResponse == null) {
                            if (error.getClass().equals(TimeoutError.class)) {
                                errorMessage = "Request timeout";
                            } else if (error.getClass().equals(NoConnectionError.class)) {
                                errorMessage = "Failed to connect server";
                            }
                            } else {
                            String result = new String(networkResponse.data);
                            try {
                                JSONObject response = new JSONObject(result);
                                String status = response.getString("status");
                                String message = response.getString("message");

                                Log.e("Error Status", status);
                                Log.e("Error Message", message);
                                if (networkResponse.statusCode == 404) {
                                    errorMessage = "Resource not found";
                                } else if (networkResponse.statusCode == 401) {
                                    errorMessage = message + " Please login again";
                                } else if (networkResponse.statusCode == 400) {
                                    errorMessage = message + " Check your inputs";
                                } else if (networkResponse.statusCode == 500) {
                                    errorMessage = message + " Something is getting wrong";
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            Log.i("Error", errorMessage);
                            error.printStackTrace();
                           }
                        _proProgressBar.setVisibility(View.GONE);
                    }})
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {HashMap<String, String> headers = new HashMap<String, String>();headers.put("Accept", "application/json");return headers;}
        };
        queue.add(getRequest);
        _proProgressBar.setVisibility(View.VISIBLE);

    }

    public void CalenderInfo(){
        final String url = "https://quarkbackend.com/getfile/rawalhar91/photo-print";
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject json = new JSONObject(response.toString());
                            JSONArray print = json.getJSONArray("print");

                            for(int i=0;i<print.length();i++){
                                JSONObject jsonnew=print.getJSONObject(i);
                                JSONArray papertype = jsonnew.getJSONArray("papertype");
                                for(int j=0;j<papertype.length();j++){
                                    JSONObject jsonnewthree=papertype.getJSONObject(j);
                                    String str_data=jsonnewthree.getString("type");
                                    //   paper_type.add(str_data);
                                }
                                //  size_spinner.setAdapter(new ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, paper_type));
                            }

                            for(int i=0;i<print.length();i++){
                                JSONObject jsonnew=print.getJSONObject(i);
                                JSONArray papertype = jsonnew.getJSONArray("size");
                                for(int j=0;j<papertype.length();j++){
                                    JSONObject jsonnewthree=papertype.getJSONObject(j);
                                    String str_data=jsonnewthree.getString("size");
                                    size_type.add(str_data);
                                }
                               // size_spinner.setAdapter(new ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, paper_type));
                                 size_spinner1.setAdapter(new ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, size_type));
                            }

                        } catch (JSONException e) {
                            Log.i(TAG, "" + e.getLocalizedMessage());
                        }
                        Log.d("Response", response.toString());
                        _proProgressBar.setVisibility(View.GONE);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();
                        Log.e("error", error.toString());
                        _proProgressBar.setVisibility(View.GONE);
                    }
                })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {HashMap<String, String> headers = new HashMap<String, String>();headers.put("Accept", "application/json");return headers;}
        };
        queue.add(getRequest);
        _proProgressBar.setVisibility(View.VISIBLE);

    }
}
