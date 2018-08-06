package com.jigsawcorp.android.jigsaw.View.CompoundViews;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.widget.TextView;

import com.jigsawcorp.android.jigsaw.R;

public class SettingsChoicePicker extends ConstraintLayout {
    private TextView mTitleTextView, mChoiceTextView;

    public SettingsChoicePicker(Context context) {
        super(context);
        setupView(context);
    }

    public SettingsChoicePicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupView(context);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.compound_settings_choice_picker);
        setTitle((typedArray.getText(R.styleable.compound_settings_choice_picker_title)).toString());
    }

    public SettingsChoicePicker(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setupView(context);
    }

    private void setupView(Context context) {
        inflate(context, R.layout.compound_settings_choice_picker, this);

        mTitleTextView = (TextView) findViewById(R.id.compound_settings_choice_picker_title);
        mChoiceTextView = (TextView) findViewById(R.id.compound_settings_choice_picker_choice);
    }

    public void setTitle(String title) {
        mTitleTextView.setText(title);
    }

    public void setChoice(String choice) {
        mChoiceTextView.setText(choice);
    }

    public String getChoice() {
        return mChoiceTextView.getText().toString();
    }
}
