package com.jigsawcorp.android.jigsaw.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.jigsawcorp.android.jigsaw.Model.Exercise;
import com.jigsawcorp.android.jigsaw.R;
import com.jigsawcorp.android.jigsaw.View.Calendar.CategoryIndicatorCircle;

import java.util.List;

public class HomeFragment extends Fragment {
    @Override
    public void onCreate(Bundle savecInstanceState) {
        super.onCreate(savecInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle onSavedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        LinearLayout layout = v.findViewById(R.id.home_line);
        View view = new CategoryIndicatorCircle(getContext());
        //view.setLayoutParams(new LinearLayout.LayoutParams(400, 400));
        layout.addView(view);
        Log.i("Potato", "height " + view.getMeasuredHeight());
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("Home");
    }
}
