package com.app.winklix.photography.Sheet.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.winklix.photography.R;

public class ViewHolder {
    public TextView title;

    public ImageView icon;

    public ViewHolder(View view) {
        title = (TextView) view.findViewById(R.id.title);
        icon = (ImageView) view.findViewById(R.id.icon);
        view.setTag(this);
    }
}
