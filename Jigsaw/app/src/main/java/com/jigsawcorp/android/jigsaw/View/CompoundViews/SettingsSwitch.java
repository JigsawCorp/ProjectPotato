package com.jigsawcorp.android.jigsaw.View.CompoundViews;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.jigsawcorp.android.jigsaw.R;

public class SettingsSwitch extends ConstraintLayout {
    private TextView mTitleTextView, mDescriptionTextView;
    private Switch mSwitch;

    private SettingsSwitchListener mListener;

    public interface SettingsSwitchListener {
        public void onSwitchChanged(boolean value);
    }

    public SettingsSwitch(Context context) {
        super(context);
        setupView(context);
    }

    public SettingsSwitch(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupView(context);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.compound_settings_switch);
        setTitle((typedArray.getText(R.styleable.compound_settings_switch_title)).toString());
        setDescription((typedArray.getText(R.styleable.compound_settings_switch_description)).toString());
    }

    public SettingsSwitch(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setupView(context);
    }

    private void setupView(Context context) {
        inflate(context, R.layout.compound_settings_switch, this);

        mTitleTextView = (TextView) findViewById(R.id.compound_settings_switch_title);
        mDescriptionTextView = (TextView) findViewById(R.id.compound_settings_switch_description);
        mSwitch = (Switch) findViewById(R.id.compound_settings_switch_switch);
        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mListener.onSwitchChanged(b);
            }
        });
    }

    public void setTitle(String title) {
        mTitleTextView.setText(title);
    }

    public void setDescription(String description) {
        mDescriptionTextView.setText(description);
    }

    public void setSwitch(boolean isChecked) {
        mSwitch.setChecked(isChecked);
    }

    public void setSettingsSwitchListener(SettingsSwitchListener listener) {
        mListener = listener;
    }
}
