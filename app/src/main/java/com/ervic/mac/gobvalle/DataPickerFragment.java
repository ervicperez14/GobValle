package com.ervic.mac.gobvalle;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;

import java.util.Calendar;

/**
 * Created by ervic on 11/25/17.
 */

public class DataPickerFragment extends DialogFragment {

    private DatePickerDialog.OnDateSetListener listener;

    public static DataPickerFragment newInstance(DatePickerDialog.OnDateSetListener listener) {
        DataPickerFragment fragment = new DataPickerFragment();
        fragment.setListener(listener);
        return fragment;
    }

    public void setListener(DatePickerDialog.OnDateSetListener listener) {
        this.listener = listener;
    }

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), listener, year, month, day);
    }

    /**
     * Created by ervic on 12/6/17.
     */

    public static class MyFirebaseMessagingService extends FirebaseMessagingService {
    }
}