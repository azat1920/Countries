package randomapps.countries;

import android.preference.PreferenceActivity;
import android.os.Bundle;
import android.preference.PreferenceFragment;

public class Preferences extends PreferenceActivity {

    public static int minValue = 10;
    public static int maxValue = 192;

    protected void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        MyPreferenceFragment mpf =  new MyPreferenceFragment();
        getFragmentManager().beginTransaction().replace(android.R.id.content, mpf).commit();
    }


    public static class MyPreferenceFragment extends PreferenceFragment
    {
        @Override
        public void onCreate(final Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref);
        }

    }
}
