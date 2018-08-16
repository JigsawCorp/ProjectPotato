package com.jigsawcorp.android.jigsaw.View.CompoundViews;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jigsawcorp.android.jigsaw.R;

import java.util.ArrayList;

public class SettingsMultipleChoicePicker extends ConstraintLayout{
    private TextView mTitleTextView;
    private LinearLayout mLinearLayout;
    private Context mContext;

    public SettingsMultipleChoicePicker(Context context) {
        super(context);
        mContext = context;
        setupView(context);
    }

    public SettingsMultipleChoicePicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        setupView(context);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.compound_settings_multiple_choice_picker);
        setTitle((typedArray.getText(R.styleable.compound_settings_multiple_choice_picker_title)).toString());
    }

    public SettingsMultipleChoicePicker(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        setupView(context);
    }

    private void setupView(Context context) {
        inflate(context, R.layout.compound_settings_multiple_choice_picker, this);

        mTitleTextView = (TextView) findViewById(R.id.compound_settings_multiple_choice_picker_title);
        mLinearLayout = (LinearLayout) findViewById(R.id.compound_settings_multiple_choice_picker_linear_layout);
    }

    public void setTitle(String title) {
        mTitleTextView.setText(title);
    }

    public void setChoices(ArrayList<String> choices) {
        mLinearLayout.removeAllViews();
        for (int i = 0; i < choices.size(); ++i) {
            // Gets the specified value in DP in pixels
            int marginInPx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, mContext.getResources().getDisplayMetrics());
            // Sets the margin for the textView
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            param.setMargins(0,marginInPx,0,8);
            // Creates the new textView matching constraints
            TextView textView = new TextView(mContext);
            textView.setText(choices.get(i));
            textView.setTextColor(ContextCompat.getColor(mContext, R.color.settingsLightBlue));
            textView.setLayoutParams(param);
            // Add new textView
            mLinearLayout.addView(textView);
        };
    }

    public ArrayList<String> getChoices() {
        ArrayList<String> choices = new ArrayList<>();
        for (int i = 0; i < mLinearLayout.getChildCount(); ++i) {
            choices.add(((TextView)mLinearLayout.getChildAt(i)).getText().toString());
        }
        return choices;
    }
}
