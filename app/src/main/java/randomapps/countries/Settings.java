package randomapps.countries;

import android.app.Activity;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.zip.CheckedInputStream;

public class Settings extends Activity {

    TextView tv1;
    SeekBar sb1;
    CheckBox chb1, chb2, chb3, chb4, chb5,chb6;

    static int count = 10;
    final String REGIONS = "";
    final Boolean[] B_Regions = {true, true, true, true, true, true};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        Initialize();


        SeekBar.OnSeekBarChangeListener osbchlist = new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                count = seekBar.getProgress() + 10;
                tv1.setText("Количество вопросов в викторине: " + ((Integer) count).toString());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        };

        sb1.setOnSeekBarChangeListener(osbchlist);


    }

    private void Initialize(){
        tv1 = (TextView) findViewById(R.id.s_tv1);
        tv1.setText("Количество вопросов в викторине: " + ((Integer) count).toString());
        sb1 = (SeekBar) findViewById(R.id.s_sb1);
        sb1.setProgress(count-10);
        chb1 = (CheckBox) findViewById(R.id.s_chb_1);   chb4 = (CheckBox) findViewById(R.id.s_chb_4);
        chb2 = (CheckBox) findViewById(R.id.s_chb_2);   chb5 = (CheckBox) findViewById(R.id.s_chb_5);
        chb3 = (CheckBox) findViewById(R.id.s_chb_3);   chb6 = (CheckBox) findViewById(R.id.s_chb_6);

        chb1.setChecked(B_Regions[0]);  chb4.setChecked(B_Regions[3]);
        chb2.setChecked(B_Regions[1]);  chb5.setChecked(B_Regions[4]);
        chb3.setChecked(B_Regions[2]);  chb6.setChecked(B_Regions[5]);



    }


}
