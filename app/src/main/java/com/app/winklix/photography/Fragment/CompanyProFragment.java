package com.app.winklix.photography.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.winklix.photography.R;

public class CompanyProFragment extends Fragment implements View.OnClickListener {

    public CompanyProFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_company_pro, container, false);


        return view;
    }

    @Override
    public void onClick(View view) {

    }
}
