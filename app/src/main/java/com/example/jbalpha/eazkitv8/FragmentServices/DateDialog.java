package com.example.jbalpha.eazkitv8.FragmentServices;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.DateFormatSymbols;

/**
 * Created by Ahsan on 10/25/2017.
 */

public class DateDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener{
    TextView tv_fromDate;
   public String formatted_date;

    @SuppressLint("ValidFragment")
    public DateDialog(View v){
        tv_fromDate=(TextView) v;

    }

    public DateDialog() {
    }

    public Dialog onCreateDialog(Bundle savedInstanceState){
        final Calendar cal;
        int year=0,month=0,day=01;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            cal = Calendar.getInstance();
             year=cal.get(Calendar.YEAR);
             month=cal.get(Calendar.MONTH);
             day=cal.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(),this,year,month,day);
        }
        else return new DatePickerDialog(getActivity(),this,year,month,day);


    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        int mon=month+1;
        String month_name = new DateFormatSymbols().getShortMonths()[mon - 1];
       // formatted_date=(month+1)+"/"+day+"/"+year;
        //setFormatted_date(formatted_date);
        //String date=year+" "+month_name+" "+day;
        String date=year+"-"+mon+"-"+day;
        tv_fromDate.setText(date);
    }

}