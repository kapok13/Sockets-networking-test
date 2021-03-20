package com.vb.htest.ui.login;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.vb.htest.R;

public class WrongUserDataDialog extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setMessage(R.string.wrong_data);
        dialog.setPositiveButton(R.string.retry, (dialog1, which) -> dialog1.dismiss());
        return dialog.create();
    }
}
