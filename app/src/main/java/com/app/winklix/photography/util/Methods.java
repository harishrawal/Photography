package com.app.winklix.photography.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

/**
 * Created by coderzlab on 16/7/15.
 */
public class Methods {


    public static boolean saveNAME(Context context, String cif){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.edit().putString("NAME",cif).commit();
    }
    public static String getNAME(Context context){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.getString("NAME", null);
    }

    public static boolean saveMOBILE(Context context, String cif){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.edit().putString("MOBILE",cif).commit();
    }
    public static String getMOBILE(Context context){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.getString("MOBILE", null);
    }
    public static boolean saveUSERID(Context context, String cif){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.edit().putString("userid",cif).commit();
    }
    public static String getUSERID(Context context){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.getString("userid", null);
    }

    public static boolean saveEMAIL(Context context, String cif){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.edit().putString("EMAIL",cif).commit();
    }
    public static String getEMAIL(Context context){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.getString("EMAIL", null);
    }


    public  static SharedPreferences getPreferances(Context context){
       return context.getSharedPreferences(context.getPackageName(),Context.MODE_PRIVATE);

    }

    public static void showToast(String msg, Context ctx) {
        Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show();
    }

}
