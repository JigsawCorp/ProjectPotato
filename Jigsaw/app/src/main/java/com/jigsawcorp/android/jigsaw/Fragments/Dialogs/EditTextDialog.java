package com.jigsawcorp.android.jigsaw.Fragments.Dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.widget.EditText;

public class EditTextDialog extends DialogFragment {
    private static final String ARGUMENT_TITLE = "title";
    private static final String ARGUMENT_TEXT = "text";
    private EditTextDialogListener mListener;

    public interface EditTextDialogListener {
            public void onTextConfirmed(String text);
    }

    public static EditTextDialog newInstance(String dialogTitle, String text) {
        Bundle bundle = new Bundle();
        bundle.putString(ARGUMENT_TITLE, dialogTitle);
        bundle.putString(ARGUMENT_TEXT, text);
        EditTextDialog dialog = new EditTextDialog();
        dialog.setArguments(bundle);
        return dialog;
    }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            Bundle bundle = getArguments();
            final EditText editText = new EditText(getContext());
            final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
            dialog.setTitle(bundle.getString(ARGUMENT_TITLE));

            dialog.setView(editText);
            dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    mListener.onTextConfirmed(editText.getText().toString());
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

        public void setConfirmedTextListener(EditTextDialogListener listener) {
            mListener = listener;
        }
}
