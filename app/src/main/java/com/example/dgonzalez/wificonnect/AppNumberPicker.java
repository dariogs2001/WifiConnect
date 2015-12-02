package com.example.dgonzalez.wificonnect;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.NumberPicker;

/**
 * Created by dgonzalez on 11/5/15.
 */
public class AppNumberPicker extends NumberPicker {

    public AppNumberPicker(final Context context) {
        super(context);
    }

    public AppNumberPicker(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        processAttributeSet(attrs);
    }

    public AppNumberPicker(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        processAttributeSet(attrs);
    }

    private void processAttributeSet(AttributeSet attrs) {
        //This method reads the parameters given in the xml file and sets the properties according to it
        this.setMinValue(attrs.getAttributeIntValue(null, "minValue", 0));
        this.setMaxValue(attrs.getAttributeIntValue(null, "maxValue", 0));
        this.setValue(attrs.getAttributeIntValue(null, "setValue", 0));
    }
}

