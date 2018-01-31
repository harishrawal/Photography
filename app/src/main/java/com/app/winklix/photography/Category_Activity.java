package com.app.winklix.photography;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.app.winklix.photography.Fragment.CompanyProFragment;
import com.app.winklix.photography.Fragment.HomeFragment;
import com.app.winklix.photography.Fragment.MenuFragment;
import com.app.winklix.photography.Fragment.NotificationFragment;

import java.util.Calendar;


public class Category_Activity extends AppCompatActivity  {
    static String Home_Tab = "Home";
    static String Company_Pro_Tab = "Contact us";
    static String Notify_Tab = "Help";
    static String Menu_Tab = "Order";
    TabHost mTabHost;

    HomeFragment homeFragment;
    CompanyProFragment company_proFragment;
    NotificationFragment notificationFragment;
    MenuFragment menuFragment;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_);


        context = Category_Activity.this;
        homeFragment = new HomeFragment();
        company_proFragment = new CompanyProFragment();
        notificationFragment = new NotificationFragment();
        menuFragment = new MenuFragment();

        mTabHost = (TabHost)findViewById(android.R.id.tabhost);
        mTabHost.setOnTabChangedListener(listener);
        mTabHost.setup();

        initializeTab();
    }


    public void initializeTab() {
        TabHost.TabSpec spec = mTabHost.newTabSpec(Home_Tab);
        mTabHost.setCurrentTab(-3);
        spec.setContent(new TabHost.TabContentFactory()
        {
            public View createTabContent(String tag) {
                return findViewById(android.R.id.tabcontent);
            }
        });

        spec.setIndicator(createTabView(Home_Tab, R.drawable.ic_home_black_24dp));
        mTabHost.addTab(spec);

        spec = mTabHost.newTabSpec(Company_Pro_Tab);
        spec.setContent(new TabHost.TabContentFactory() {
            public View createTabContent(String tag) {
                return findViewById(android.R.id.tabcontent);}});

        spec.setIndicator(createTabView(Company_Pro_Tab, R.drawable.ic_contact_phone_black_24dp));
        mTabHost.addTab(spec);

        spec = mTabHost.newTabSpec(Notify_Tab);
        spec.setContent(new TabHost.TabContentFactory() {
            public View createTabContent(String tag) {
                return findViewById(android.R.id.tabcontent);}});
        spec.setIndicator(createTabView(Notify_Tab, R.drawable.ic_help_black_24dp));
        mTabHost.addTab(spec);

        spec = mTabHost.newTabSpec(Menu_Tab);
        spec.setContent(new TabHost.TabContentFactory() {
            public View createTabContent(String tag) {
                return findViewById(android.R.id.tabcontent);}});
        spec.setIndicator(createTabView(Menu_Tab, R.drawable.ic_present_to_all_black_24dp));
        mTabHost.addTab(spec);
    }

    TabHost.OnTabChangeListener listener = new TabHost.OnTabChangeListener() {
        public void onTabChanged(String tabId) {

            if(tabId.equals(Home_Tab)){
                pushFragments(tabId, homeFragment);
            }
            else if(tabId.equals(Company_Pro_Tab)){
                pushFragments(tabId, company_proFragment);
            }
            else if(tabId.equals(Notify_Tab)){
                pushFragments(tabId, notificationFragment);
            }
            else if(tabId.equals(Menu_Tab)){
                pushFragments(tabId, menuFragment);
            }}};

    public void pushFragments(String tag, Fragment fragment){
        FragmentManager manager =  getSupportFragmentManager();
        FragmentTransaction ft  =  manager.beginTransaction();
        ft.setCustomAnimations(R.anim.push_up_in, R.anim.push_up_out);
        ft.replace(android.R.id.tabcontent, fragment);
        ft.commit();
    }

    private View createTabView(final String text, final int id) {
        View view = LayoutInflater.from(this).inflate(R.layout.tabs_icon, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.tab_icon);
        imageView.setImageDrawable(getResources().getDrawable(id));
        ((TextView) view.findViewById(R.id.tab_text)).setText(text);
        return view;
    }



    public void showDialog() {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.layout_custom_dialog, null);
        final EditText etName = (EditText) alertLayout.findViewById(R.id.etName);
        final EditText etDate = (EditText) alertLayout.findViewById(R.id.etDate);
        final CheckBox cbHungry = (CheckBox) alertLayout.findViewById(R.id.cbHungry);
      //  final Spinner spSex = (Spinner) alertLayout.findViewById(R.id.spSex);
        final ImageButton btnDate = (ImageButton) alertLayout.findViewById(R.id.btnDate);

        @SuppressLint("ValidFragment")
        class SelectDateFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
            @Override
            public Dialog onCreateDialog(Bundle savedInstanceState) {
                final Calendar calendar = Calendar.getInstance();
                int yy = calendar.get(Calendar.YEAR);
                int mm = calendar.get(Calendar.MONTH);
                int dd = calendar.get(Calendar.DAY_OF_MONTH);
                return new DatePickerDialog(getActivity(), this, yy, mm, dd);
            }

            public void onDateSet(DatePicker view, int yy, int mm, int dd) {
                etDate.setText((mm + 1) + "/" + dd + "/" + yy);
            }
        }

        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new SelectDateFragment();
                newFragment.show(getSupportFragmentManager(), "DatePicker");
            }
        });

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Form");
        alert.setView(alertLayout);
        alert.setCancelable(false);
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getBaseContext(), "Cancel clicked", Toast.LENGTH_SHORT).show();
            }
        });

        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String user = etName.getText().toString();
                String bday = etDate.getText().toString();
              //  String sex = String.valueOf(spSex.getSelectedItem());
                String hungry = (cbHungry.isChecked()) ? "Yes" : "No";
               // Toast.makeText(getBaseContext(), "Name: " + user + "\nBirthday: " + bday + "\nSex: " + sex + "\nHungry: " + hungry, Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();
    }
}
