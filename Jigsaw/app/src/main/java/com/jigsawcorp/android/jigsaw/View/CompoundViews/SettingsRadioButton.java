package com.jigsawcorp.android.jigsaw.View.CompoundViews;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;

import com.jigsawcorp.android.jigsaw.R;

public class SettingsRadioButton extends ConstraintLayout {

    public SettingsRadioButton(Context context) {
        super(context);
        setupView(context);
    }

    public SettingsRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupView(context);
    }

    public SettingsRadioButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setupView(context);
    }

    private void setupView(Context context) {
        inflate(context, R.layout.compound_settings_radio_button_list, this);
    }
}
