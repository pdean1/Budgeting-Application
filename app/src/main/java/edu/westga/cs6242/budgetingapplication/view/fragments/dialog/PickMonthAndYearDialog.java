package edu.westga.cs6242.budgetingapplication.view.fragments.dialog;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;

import java.util.Calendar;

import edu.westga.cs6242.budgetingapplication.R;

/**
 * Allows us to create date picker dialogs w/o the date, only the month and the year
 * @author  Patrick Dean
 * @version 2016
 */
public class PickMonthAndYearDialog extends DialogFragment {
    public static final int MAX_YEAR = 2099;
    private DatePickerDialog.OnDateSetListener dateSetListener;

    public void setOnDateSetListener(DatePickerDialog.OnDateSetListener listener) {
        this.dateSetListener = listener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        Calendar cal = Calendar.getInstance();

        View dialog = inflater.inflate(R.layout.dialog_date_picker_month_and_year, null);

        final NumberPicker yearPicker = (NumberPicker) dialog.findViewById(R.id.picker_year);
        final NumberPicker monthPicker = (NumberPicker) dialog.findViewById(R.id.picker_month);

        monthPicker.setMinValue(1);
        monthPicker.setMaxValue(12);
        monthPicker.setValue(cal.get(Calendar.MONTH) + 1);

        yearPicker.setMinValue(cal.get(Calendar.YEAR));
        yearPicker.setMaxValue(MAX_YEAR);
        yearPicker.setValue(cal.get(Calendar.YEAR));

        builder.setView(dialog).setPositiveButton(R.string.txt_set_date, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dateSetListener.onDateSet(null, yearPicker.getValue(), monthPicker.getValue(), 1);
            }
        }).setNegativeButton(R.string.txt_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                PickMonthAndYearDialog.this.getDialog().cancel();
            }
        });
        return builder.create();
    }
}
