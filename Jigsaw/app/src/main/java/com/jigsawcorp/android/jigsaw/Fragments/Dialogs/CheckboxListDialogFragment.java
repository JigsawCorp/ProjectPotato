package com.jigsawcorp.android.jigsaw.Fragments.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class CheckboxListDialogFragment extends DialogFragment{
    private static final String ARGUMENT_TITLE = "title";
    private static final String ARGUMENT_CHOICES = "choices";
    private static final String ARGUMENT_SELECTED_ITEMS = "selectedItems";

    private CheckboxListDialogFragmentListener mListener;

    public interface CheckboxListDialogFragmentListener {
        public void onChoiceConfirmed(boolean[] selections);
    }

    public static CheckboxListDialogFragment newInstance(String dialogTitle, ArrayList<String> choices, boolean[] selectedItems) {
        Bundle bundle = new Bundle();
        bundle.putString(ARGUMENT_TITLE,dialogTitle);
        bundle.putStringArrayList(ARGUMENT_CHOICES, choices);
        bundle.putBooleanArray(ARGUMENT_SELECTED_ITEMS, selectedItems);
        CheckboxListDialogFragment fragment = new CheckboxListDialogFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle(bundle.getString(ARGUMENT_TITLE));
        final boolean[] previousSelection = bundle.getBooleanArray(ARGUMENT_SELECTED_ITEMS);

        List<String> choicesList = (List<String>)bundle.get(ARGUMENT_CHOICES);
        CharSequence[] choicesCharSequence = choicesList.toArray(new CharSequence[choicesList.size()]);
        dialog.setMultiChoiceItems(choicesCharSequence, previousSelection, null);
        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ListView dialogList = ((AlertDialog)dialogInterface).getListView();
                boolean[] newSelection = new boolean[dialogList.getCount()];
                for (int j = 0; j < dialogList.getCount(); ++j) {
                        newSelection[j] = dialogList.isItemChecked(j);
                }
                mListener.onChoiceConfirmed(newSelection);
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

    public void setConfirmedChoiceListener(CheckboxListDialogFragmentListener listener) {
        mListener = listener;
    }
}
