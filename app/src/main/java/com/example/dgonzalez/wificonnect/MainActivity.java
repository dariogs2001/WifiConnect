package com.example.dgonzalez.wificonnect;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Vibrator;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.format.Time;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.otto.Subscribe;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {
    static final String STATE_BUZZ_LEVEL = "buzzLevel";
    static final String STATE_IS_PING = "isPing";
    static final String STATE_IS_BUZZ = "isBuzz";

    private static final int MY_PERMISSIONS_REQUEST = 1;

    Button mStartButton;
    Spinner mWifiSpinner;
    NumberPicker mSignalBuzzPicker;
    Button mStartPingButton;
    Button mEndPingButton;
    TextView mSiganlTextView;
    TextView mDateTextView;
    CheckBox mVibrateCheckBox;
//    Boolean mVibrateBoolean;
//    Vibrator mVibratorService;

    ArrayList<String> mWifiArray;
//    int mBuzzValue;
    List<ScanResult> wifiList;
    AlarmReceiver mAlarmReceiver;
    boolean isPing= false;
    CardView option1;
    CardView option2;
    CardView option3;
    CardView option4;
    CardView option5;
    CardView option6;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Utils.AppWifiManger = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        Utils.VibratorService = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        mStartButton = (Button) findViewById(R.id.search_button);
        mWifiSpinner = (Spinner) findViewById(R.id.wifi_spinner);
        mSignalBuzzPicker = (NumberPicker) findViewById(R.id.buzz_picker);
        mStartPingButton = (Button) findViewById(R.id.start_ping_button);
        mEndPingButton = (Button) findViewById(R.id.stop_ping_button);
        mSiganlTextView = (TextView) findViewById(R.id.signal_text_view);
        mDateTextView = (TextView) findViewById(R.id.date_text_view);
        mDateTextView = (TextView) findViewById(R.id.date_text_view);
        mVibrateCheckBox = (CheckBox)findViewById(R.id.vibrate_check_box);
        mAlarmReceiver = new AlarmReceiver();
        Utils.VibrateBoolean = true;

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST);
        }

        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                fillSpinner();
            }
        });

        mWifiSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(final AdapterView<?> parent, final View view, final int position, final long id) {
                String selectedValue = mWifiSpinner.getItemAtPosition(position).toString();
                String[] values = selectedValue.split("@@@");
                Utils.SSID = values[0];
                Utils.BSSID = values[1];
            }

            @Override
            public void onNothingSelected(final AdapterView<?> parent) {
                Utils.SSID = "";
                Utils.BSSID = "";
            }
        });

        Utils.BuzzValue = mSignalBuzzPicker.getValue();

        mSignalBuzzPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(final NumberPicker picker, final int oldVal, final int newVal) {
                Utils.BuzzValue = newVal;
            }
        });

        mStartPingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                mAlarmReceiver.setAlarm(MainActivity.this);
                mStartPingButton.setEnabled(false);
                mEndPingButton.setEnabled(true);
                isPing = true;
            }
        });

        mEndPingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                mAlarmReceiver.cancelAlarm();
                mStartPingButton.setEnabled(true);
                mEndPingButton.setEnabled(false);
                isPing = false;
            }
        });

        mVibrateCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(final CompoundButton buttonView, final boolean isChecked) {
                Utils.VibrateBoolean = isChecked;
            }
        });


        option1 = (CardView)findViewById(R.id.option1);
        option2 = (CardView)findViewById(R.id.option2);
        option3 = (CardView)findViewById(R.id.option3);
        option4 = (CardView)findViewById(R.id.option4);
        option5 = (CardView)findViewById(R.id.option5);
        option6 = (CardView)findViewById(R.id.option6);

        option1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                AsyncHttpClient client = new AsyncHttpClient();
                client.get("http://api.openweathermap.org/data/2.5/weather?q=London,uk&appid=2de143494c0b295cca9337e1e96b00e0", new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(final int statusCode, final Header[] headers, final byte[] responseBody) {
                        String value = new String(responseBody);
                        String value2 = value;
                    }

                    @Override
                    public void onFailure(final int statusCode, final Header[] headers, final byte[] responseBody, final Throwable error) {

                    }
                });

            }
        });

        option2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                AsyncHttpClient client = new AsyncHttpClient();
                client.post("https://www.google.com", null, new JsonHttpResponseHandler(){

                });
            }
        });

        option3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

            }
        });

        option4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

            }
        });

        option5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

            }
        });

        option6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

            }
        });

        if (savedInstanceState != null) {
            // Restore value of members from saved state
            savedInstanceState.putInt(STATE_BUZZ_LEVEL, Utils.BuzzValue);
            savedInstanceState.putBoolean(STATE_IS_PING, isPing);
            savedInstanceState.putBoolean(STATE_IS_BUZZ, Utils.VibrateBoolean);

            Utils.BuzzValue = savedInstanceState.getInt(STATE_BUZZ_LEVEL);
            isPing = savedInstanceState.getBoolean(STATE_IS_PING);
            Utils.VibrateBoolean = savedInstanceState.getBoolean(STATE_IS_BUZZ);

            mStartPingButton.setEnabled(!isPing);
            mEndPingButton.setEnabled(isPing);

            mSignalBuzzPicker.setValue(Utils.BuzzValue);
            mVibrateCheckBox.setChecked(Utils.VibrateBoolean);
        }
    }

    void fillSpinner()
    {
        int state = Utils.AppWifiManger.getWifiState();
//        int maxLevel = 100;
//        String data = "";
        mWifiArray = new ArrayList<String>();

        if (state == WifiManager.WIFI_STATE_ENABLED) {


            // Get Scanned results in an array List
            wifiList = Utils.AppWifiManger.getScanResults();
            // Iterate on the list
            for (ScanResult result : wifiList) {
                //The level of each wifiNetwork from 0-5
//                int level = WifiManager.calculateSignalLevel(
//                        result.level, maxLevel);
                String SSID = result.SSID + "@@@" +result.BSSID;
//                String capabilities = result.capabilities;
                if (!result.SSID.isEmpty()) {
                    mWifiArray.add(SSID);
                }
            }
        }
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, mWifiArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mWifiSpinner.setAdapter(adapter);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Subscribe
    public void sendMessageEvent(SendMessageEvent event)
    {
        int state = Utils.AppWifiManger.getWifiState();
        int maxLevel = 100;
        if (state == WifiManager.WIFI_STATE_ENABLED)
        {
            // Get Scanned results in an array List
            wifiList = Utils.AppWifiManger.getScanResults();
            // Iterate on the list
            for (ScanResult result : wifiList)
            {
                if (result.BSSID.equals(Utils.BSSID))
                {
                    int signal = Utils.AppWifiManger.calculateSignalLevel(result.level, maxLevel);
                    mSiganlTextView.setText(String.valueOf(signal));
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    String currentDateandTime = sdf.format(new Date());
                    mDateTextView.setText(currentDateandTime);

//                    if (signal >= mBuzzValue && mVibrateBoolean) {
//
//                        if (mVibratorService != null) {
//                            // Vibrate for 500 milliseconds
//                            mVibratorService.vibrate(500);
//                        }
//                    }
//                    break;
                }
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        BusProvider.getInstance().unregister(this);
    }

    @Override
    protected void onResume() {
        BusProvider.getInstance().register(this);
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);

        // Save the user's current game state
        savedInstanceState.putInt(STATE_BUZZ_LEVEL, Utils.BuzzValue);
        savedInstanceState.putBoolean(STATE_IS_PING, isPing);
        savedInstanceState.putBoolean(STATE_IS_BUZZ, Utils.VibrateBoolean);

    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        // Always call the superclass so it can restore the view hierarchy
        super.onRestoreInstanceState(savedInstanceState);

        // Restore state members from saved instance
        savedInstanceState.putInt(STATE_BUZZ_LEVEL, Utils.BuzzValue);
        savedInstanceState.putBoolean(STATE_IS_PING, isPing);
        savedInstanceState.putBoolean(STATE_IS_BUZZ, Utils.VibrateBoolean);

        Utils.BuzzValue = savedInstanceState.getInt(STATE_BUZZ_LEVEL);
        isPing = savedInstanceState.getBoolean(STATE_IS_PING);
        Utils.VibrateBoolean = savedInstanceState.getBoolean(STATE_IS_BUZZ);

        mStartPingButton.setEnabled(!isPing);
        mEndPingButton.setEnabled(isPing);

        mSignalBuzzPicker.setValue(Utils.BuzzValue);
        mVibrateCheckBox.setChecked(Utils.VibrateBoolean);
    }

    //Testing more stuff
}
