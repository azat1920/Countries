package randomapps.countries;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.Window;


public class MultiSelectListPref extends DialogPreference {


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MultiSelectListPref(Context context) {
        super(context);

    }

    public MultiSelectListPref(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    @Override
    protected void showDialog(Bundle state) {
        super.showDialog(state);
        Window w = getDialog().getWindow();
    }

}

