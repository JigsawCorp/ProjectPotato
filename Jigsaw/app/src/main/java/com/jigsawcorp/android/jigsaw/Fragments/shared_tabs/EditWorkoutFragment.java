package com.jigsawcorp.android.jigsaw.Fragments.shared_tabs;

import android.os.Bundle;
import android.support.v4.app.Fragment;

public class EditWorkoutFragment extends Fragment {

    public static EditWorkoutFragment newInstance() {
        Bundle bundle = new Bundle();
        EditWorkoutFragment fragment = new EditWorkoutFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
}
