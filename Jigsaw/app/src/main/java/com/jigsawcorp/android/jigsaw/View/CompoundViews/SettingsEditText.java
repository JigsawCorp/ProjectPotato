package com.jigsawcorp.android.jigsaw.View.CompoundViews;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.widget.TextView;

import com.jigsawcorp.android.jigsaw.R;

public class SettingsEditText extends ConstraintLayout{
    private TextView mTitleTextView, mDescriptionTextView;

    public SettingsEditText(Context context) {
        super(context);
        setupView(context);
    }

    public SettingsEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupView(context);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.compound_settings_switch);
        setTitle((typedArray.getText(R.styleable.compound_settings_edit_text_title)).toString());
        setDescription((typedArray.getText(R.styleable.compound_settings_edit_text_description)).toString());
    }

    public SettingsEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setupView(context);
    }

    private void setupView(Context context) {
        inflate(context, R.layout.compound_settings_edit_text, this);

        mTitleTextView = (TextView) findViewById(R.id.compound_settings_edit_text_title);
        mDescriptionTextView = (TextView) findViewById(R.id.compound_settings_edit_text_description);
    }

    public void setTitle(String title) {
        mTitleTextView.setText(title);
    }

    public void setDescription(String description) {
        mDescriptionTextView.setText(description);
    }

    public String getDescription() {
        return mDescriptionTextView.getText().toString();
    }

}
