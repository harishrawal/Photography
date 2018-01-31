package com.app.winklix.photography.Multi_Image_Picker;

import android.net.Uri;

/**
 * Created by Satnam on 11/24/2017.
 */

public class Spacecraft {
    String name;
    Uri uri;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Uri getUri() {
        return uri;
    }
    public void setUri(Uri uri) {
        this.uri = uri;
    }
}