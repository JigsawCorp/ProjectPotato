package com.jigsawcorp.android.jigsaw.Fragments.Dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.widget.NumberPicker;

public class NumberPickerDialog extends DialogFragment {
    private static final String ARGUMENT_TITLE = "title";
    private static final String ARGUMENT_NUMBER = "number";
    private NumberPickerDialogListener mListener;

    public interface NumberPickerDialogListener {
        public void onNumberConfirmed(int number);
    }

    public static NumberPickerDialog newInstance(String dialogTitle, int number) {
        Bundle bundle = new Bundle();
        bundle.putString(ARGUMENT_TITLE, dialogTitle);
        bundle.putInt(ARGUMENT_NUMBER, number);
        NumberPickerDialog dialog = new NumberPickerDialog();
        dialog.setArguments(bundle);
        return dialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        final NumberPicker numberPicker = new NumberPicker(getContext());
        final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle(bundle.getString(ARGUMENT_TITLE));
        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(100);
        numberPicker.setValue(bundle.getInt(ARGUMENT_NUMBER));

        dialog.setView(numberPicker);
        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mListener.onNumberConfirmed(numberPicker.getValue());
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

    public void setConfirmedNumberListener(NumberPickerDialogListener listener) {
        mListener = listener;
    }
}
