package com.jigsawcorp.android.jigsaw.Fragments.Dialogs;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class HistoryCalendarCaseDialog extends DialogFragment {

    public static HistoryCalendarCaseDialog newInstance() {
        Bundle bundle = new Bundle();
        HistoryCalendarCaseDialog fragment = new HistoryCalendarCaseDialog();
        fragment.setArguments(bundle);
        return fragment;
    }
}
