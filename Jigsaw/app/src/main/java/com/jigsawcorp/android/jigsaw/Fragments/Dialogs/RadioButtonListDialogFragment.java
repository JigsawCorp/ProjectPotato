package com.jigsawcorp.android.jigsaw.Fragments.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class RadioButtonListDialogFragment extends DialogFragment {
    private static final String ARGUMENT_TITLE = "title";
    private static final String ARGUMENT_CHOICES = "choices";
    private static final String ARGUMENT_SELECTED_ITEM = "selectedItem";

    private RadioButtonListDialogFragmentListener mListener;

    public interface RadioButtonListDialogFragmentListener {
        public void onChoiceConfirmed(int position);
    }

    public static RadioButtonListDialogFragment newInstance(String dialogTitle, ArrayList<String> choices, int selectedItem) {
        Bundle bundle = new Bundle();
        bundle.putString(ARGUMENT_TITLE,dialogTitle);
        bundle.putStringArrayList(ARGUMENT_CHOICES, choices);
        bundle.putInt(ARGUMENT_SELECTED_ITEM, selectedItem);
        RadioButtonListDialogFragment fragment = new RadioButtonListDialogFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle(bundle.getString(ARGUMENT_TITLE));

        int checkedPosition = bundle.getInt(ARGUMENT_SELECTED_ITEM);

        List<String> choicesList = (List<String>)bundle.get(ARGUMENT_CHOICES);
        CharSequence[] choicesCharSequence = choicesList.toArray(new CharSequence[choicesList.size()]);
        dialog.setSingleChoiceItems(choicesCharSequence, checkedPosition, null);
        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mListener.onChoiceConfirmed(((AlertDialog)dialogInterface).getListView().getCheckedItemPosition());
            }
        });
        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        return dialog.create();
    }

    public void setConfirmedChoiceListener(RadioButtonListDialogFragmentListener listener) {
        mListener = listener;
    }
}
