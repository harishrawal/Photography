package com.app.winklix.photography;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.winklix.photography.util.DbHelper;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Cart_Activity extends AppCompatActivity {
    private DbHelper mHelper;
    private SQLiteDatabase dataBase;
    private ArrayList<String> userId = new ArrayList<String>();
    private ArrayList<String> user_fName = new ArrayList<String>();
    private ArrayList<String> user_lName = new ArrayList<String>();
    private ArrayList<String> user_quantity = new ArrayList<String>();
    private ArrayList<String> product_image = new ArrayList<String>();
    private ArrayList<String> sum = new ArrayList<String>();
    private ArrayList<String> KEY_PRODUCT_ID = new ArrayList<String>();
    private ListView userList;
    private AlertDialog.Builder build;
    TextView total;
    DisplayAdapter disadpt;
    String s1 ,total_intent;
    Integer total1 =0;
    int cnt;
    Cursor cur;
    public static final String TAG = "Mess";
    Context context;
    CardView proceed_edit;
    String   cart_order;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //   setContentView(R.layout.activity_cart_);
        setContentView(R.layout.display_activity);
        context = Cart_Activity.this;

        proceed_edit = findViewById(R.id.proceed_edit);
        userList =  findViewById(R.id.List);
        total= findViewById(R.id.total);
        mHelper = new DbHelper(this);

        proceed_edit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getResults();
                Intent in  = (new Intent(context,Edit_Profile.class));
                in.putExtra("cart_order",cart_order);
                startActivity(in);
            }
        });
    }


    @Override
    protected void onResume() {
        displayData();
        totalData();
        getCartCount() ;
        super.onResume();
    }


    private int totalData() {
        cur = dataBase.rawQuery("SELECT SUM("+DbHelper.KEY_SUM+") FROM ("+DbHelper.TABLE_NAME+")", null);
        if(cur.moveToFirst())
        {
            System.out.println("total hari "+ cur.getInt(0));
            total.setText("Total Price of Cart :  "+"₹"+" "+ Integer.toString(cur.getInt(0)));
            total_intent =  Integer.toString(cur.getInt(0));
            SharedPreferences sp1=getSharedPreferences("pref",MODE_PRIVATE);
            SharedPreferences.Editor editor1=sp1.edit();
            editor1.putString("total", Integer.toString(cur.getInt(0)));
            editor1.commit();
            return cur.getInt(0);
        }
        return total1;

    }

    public int getCartCount() {
        Cursor cursor11 = mHelper.getReadableDatabase().rawQuery("SELECT  * FROM "+DbHelper.TABLE_NAME, null);
        cnt = cursor11.getCount();
        System.out.println("count"+ cnt);
        cursor11.close();
        return cnt;
    }

    /**
     * displays data from SQLite
     * @return
     */
    ArrayList<ArrayList<Object>> displayData() {
        ArrayList<ArrayList<Object>> dataArrays = new ArrayList();
        ArrayList<ArrayList<Object>> dataArrayName = new ArrayList();
        dataBase = mHelper.getWritableDatabase();
        Cursor mCursor = dataBase.rawQuery("SELECT * FROM " + DbHelper.TABLE_NAME, null);

        userId.clear();
        user_fName.clear();
        user_lName.clear();
        user_quantity.clear();
        product_image.clear();
        sum.clear();
        KEY_PRODUCT_ID.clear();


        if ( mCursor != null && mCursor.moveToFirst()) {
            do {
                ArrayList<Object> dataList = new ArrayList();
                ArrayList<Object> dataListName = new ArrayList();
                userId.add(mCursor.getString(mCursor.getColumnIndex(DbHelper.KEY_ID)));
                user_fName.add(mCursor.getString(mCursor.getColumnIndex(DbHelper.KEY_PNAME)));
                user_lName.add(mCursor.getString(mCursor.getColumnIndex(DbHelper.KEY_PRICE)));
                user_quantity.add(mCursor.getString(mCursor.getColumnIndex(DbHelper.KEY_QTY)));
                product_image.add(mCursor.getString(mCursor.getColumnIndex(DbHelper.KEY_IMAGE)));
                sum.add(mCursor.getString(mCursor.getColumnIndex(DbHelper.KEY_SUM)));


                dataList.add(mCursor.getString(0) + "userId");
                dataList.add(mCursor.getString(1) + " " + "PNAME");
                dataList.add(mCursor.getString(2) + " " + "KEY_PRICE");
                dataList.add(mCursor.getString(3) + " " + "KEY_QTY");
                dataList.add(mCursor.getString(4) + " " + "KEY_IMAGE");
                dataList.add(mCursor.getString(5) + " " + "KEY_SUM");


                dataArrays.add(dataList);
                dataArrayName.add(dataListName);

                StringBuilder builder = new StringBuilder();
                for (Object details : dataArrays) {
                    builder.append(details + "\n");
                }
                StringBuilder builder1 = new StringBuilder();
                for (Object details : dataArrays) {
                    builder1.append(details);
                }
                StringBuilder buildername1 = new StringBuilder();
                for (Object details : dataArrayName) {
                    buildername1.append(details);
                }
                //quantitys.setText(builder.toString().replace("[", "").replaceAll("]", ""));
                //quantitys1.setText(builder1.toString().replace("[", "").replaceAll("]", ""));
            }

            while (mCursor.moveToNext());
        }

        disadpt = new DisplayAdapter(Cart_Activity.this,userId, user_fName, user_lName,user_quantity,product_image,sum,KEY_PRODUCT_ID);
        userList.setAdapter(disadpt);
        mCursor.close();
        return null;

    }




    public class DisplayAdapter extends BaseAdapter {
        private Context mContext;
        private ArrayList<String> id;
        private ArrayList<String> productName;
        private ArrayList<String> price;
        private ArrayList<String> qty;
        private ArrayList<String> sumName;
        private ArrayList<String> product_image;
        private ArrayList<String> KEY_PRODUCT_ID;


        public DisplayAdapter(Context c, ArrayList<String> id, ArrayList<String> productName, ArrayList<String> price, ArrayList<String> qty, ArrayList<String> product_image,ArrayList<String> qsum,ArrayList<String> KEY_PRODUCT_IDs) {
            this.mContext = c;
            this.id = id;
            this.productName = productName;
            this.price = price;
            this.qty = qty;
            this.sumName = qsum;
            this.product_image = product_image;
            this.KEY_PRODUCT_ID = KEY_PRODUCT_IDs;
        }

        public int getCount() {
            // TODO Auto-generated method stub
            return id.size();
        }

        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return null;
        }

        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return 0;
        }

        public View getView(final int pos, View child, ViewGroup parent) {
            final Holder mHolder;
            LayoutInflater layoutInflater;
            if (child == null) {
                layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                child = layoutInflater.inflate(R.layout.cart_list_adapter_layout, null);
                mHolder = new Holder();
                mHolder.txt_id = (TextView) child.findViewById(R.id.txt_id);
                mHolder.productName = (TextView) child.findViewById(R.id.productName);
                mHolder.price = (TextView) child.findViewById(R.id.price);
                mHolder.txt_qName1 = (TextView) child.findViewById(R.id.txt_qName1);
                mHolder.txt_qName = (EditText) child.findViewById(R.id.txt_qName);
                mHolder.txt_sum = (TextView) child.findViewById(R.id.txt_sum);
                mHolder.product_image = (TextView) child.findViewById(R.id.product_image);
                mHolder.view_image = (ImageView) child.findViewById(R.id.confirmorder_itemimage_adapterpart);
                mHolder.cancel = (Button) child.findViewById(R.id.cancel);
                mHolder.refresh = (Button) child.findViewById(R.id.refresh);
                child.setTag(mHolder);
            } else {
                mHolder = (Holder) child.getTag();
            }
            mHolder.txt_id.setText(id.get(pos));
            mHolder.productName.setText(productName.get(pos));
            mHolder.price.setText("₹ "+price.get(pos));
            mHolder.txt_sum.setText("Sub-Total: "+"₹ "+sumName.get(pos));

            mHolder.txt_qName1.setText("Quantity: "+qty.get(pos));
            mHolder.txt_qName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mHolder.txt_qName1.setText(qty.get(pos));
                }
            });

            mHolder.product_image.setText(product_image.get(pos));
            mHolder.cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(), productName.get(pos) + " " + " is deleted..", Toast.LENGTH_LONG).show();
                    dataBase.delete(DbHelper.TABLE_NAME, DbHelper.KEY_ID + "=" + userId.get(pos), null);
                    userId.remove(id.get(pos));
                    disadpt.notifyDataSetChanged();
                    //   dataBase.execSQL("delete from "+ DbHelper.TABLE_NAME);
                }
            });

            mHolder.refresh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        if(!(mHolder.txt_qName.getText().toString()).equalsIgnoreCase("0")){
                            int pri = Integer.parseInt(price.get(pos));
                            int quantity = Integer.parseInt(mHolder.txt_qName.getText().toString());

                            int total = pri * quantity;
                            Toast.makeText(getApplicationContext(), total + " " + " is total.", Toast.LENGTH_LONG).show();
                            //  Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                            mHolder.txt_sum.setText((Integer.toString(total)));
                            mHolder.txt_qName1.setText("quantity: " + Integer.toString(quantity));
                            dataBase = mHelper.getWritableDatabase();
                            ContentValues values = new ContentValues();
                            values.put(DbHelper.KEY_SUM, (Integer.toString(total)));
                            values.put(DbHelper.KEY_QTY, (Integer.toString(quantity)));

                            dataBase.update(DbHelper.TABLE_NAME, values, DbHelper.KEY_ID + "=" + id.get(pos), null);

                            Intent i = new Intent(getApplicationContext(), Cart_Activity.class);
                            startActivity(i);
                            finish();
                        }
                        else    Toast.makeText(getApplicationContext(), "quantity is not set to 0", Toast.LENGTH_LONG).show();

                    }
                    catch(NumberFormatException ex){
                        Log.i(Cart_Activity.TAG,""+ex.getLocalizedMessage());
                    }
                }
            });

            Picasso.with(mContext).load(product_image.get(pos)).into(mHolder.view_image);
            return child;
        }

        public class Holder {
            public TextView txt_sum;
            public TextView txt_qName1;
            public EditText txt_qName;
            TextView txt_id;
            TextView productName;
            TextView price;
            TextView product_image;
            ImageView view_image;
            Button cancel;
            Button refresh;
        }
    }
    public JSONArray getResults() {
        dataBase = mHelper.getWritableDatabase();
        Cursor cursor = dataBase.rawQuery("SELECT * FROM " + DbHelper.TABLE_NAME, null);
        JSONArray resultSet = new JSONArray();
        JSONObject returnObj = new JSONObject();
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {

            int totalColumn = cursor.getColumnCount();
            JSONObject rowObject = new JSONObject();

            for (int i = 0; i < totalColumn; i++) {
                if (cursor.getColumnName(i) != null) {
                    try {
                        if (cursor.getString(i) != null) {
                            Log.d("TAG_NAME:12121", cursor.getString(i));
                            rowObject.put(cursor.getColumnName(i), cursor.getString(i));
                        } else {
                            rowObject.put(cursor.getColumnName(i), "");
                        }
                    } catch (Exception e) {
                        Log.d("TAG_NAME:88", e.getMessage());
                    }
                }
            }
            resultSet.put(rowObject);
            cursor.moveToNext();
        }
        cursor.close();
        Log.d("TAG_NAME","&{ \\\"NewDataSet\\\": { \\\"Cart\\\": "+resultSet.toString()+"}}");
        //cart_order = "&{ \\\"NewDataSet\\\": { \\\"Cart\\\": "+resultSet.toString()+"}}";
        cart_order = resultSet.toString();
        return resultSet;
    }
}
