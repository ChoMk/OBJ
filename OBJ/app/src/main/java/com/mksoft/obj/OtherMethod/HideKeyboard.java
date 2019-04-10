package com.mksoft.obj.OtherMethod;

import android.app.Activity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class HideKeyboard {
    Activity activity;
    InputMethodManager imm;
    public HideKeyboard(Activity activity) {
        this.activity = activity;
        imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
    }
    public void hideKeyboard() {
    //Find the currently focused view, so we can grab the correct window token from it.
    View view = activity.getCurrentFocus();
    //If no view currently has focus, create a new one, just so we can grab a window token from it
    if (view == null) {
        view = new View(activity);
    }
    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
