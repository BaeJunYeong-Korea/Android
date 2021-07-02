package com.example.covid_test3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

public class VaccinationActivity extends AppCompatActivity {
    private Context mContext;
    private MultiAutoCompleteTextView multiAutoCompleteTextView;    // 자동완성 텍스트 뷰
    String[] items = { "AstraZeneca", "Pfizer", "Janssen", "Moderna", "Novavax" };  // 뷰 내용물

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaccination);

        TextView textView3 = findViewById(R.id.textView3);   // 저장되는 백신 이름
        TextView textView2 = findViewById(R.id.textView2);  // 날짜를 선택하면 저장됨
        Button button5 = findViewById(R.id.button5);    // 누르면 저장

        mContext = this;
        multiAutoCompleteTextView = findViewById(R.id.multiAutoCompleteTextView);
        multiAutoCompleteTextView.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        multiAutoCompleteTextView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, items));

        textView3.setText(PreferenceManager.getString(mContext, "save"));

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = multiAutoCompleteTextView.getText().toString();
                PreferenceManager.setString(mContext, "save", text);    // 간단한 데이터 저장
                Toast.makeText(getApplicationContext(), "백신명이 저장되었습니다. ", Toast.LENGTH_SHORT).show();
                textView3.setText(PreferenceManager.getString(mContext, "save"));
            }
        });

        textView2.setText(PreferenceManager.getString(mContext, "date_show"));
    }


    public void showDatePicker(View view) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(),"datePicker");
    }

    public void processDatePickerResult(int year, int month, int day){
        String year_string = Integer.toString(year);
        String month_string = Integer.toString(month+1);
        String day_string = Integer.toString(day);
        String date_show = ("접종일: " + year_string + "년 " + month_string + "월" + day_string + "일");
        String date_save = (year_string + "-" + month_string + "-" + day_string + " 00:00:00");

        mContext = this;

        multiAutoCompleteTextView = findViewById(R.id.multiAutoCompleteTextView);
        multiAutoCompleteTextView.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        multiAutoCompleteTextView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, items));

        PreferenceManager.setString(mContext, "date_show", date_show);
        PreferenceManager.setString(mContext, "date_save", date_save);
        Toast.makeText(getApplicationContext(), "접종일이 저장되었습니다. ", Toast.LENGTH_SHORT).show();

        TextView textView2 = findViewById(R.id.textView2);
        textView2.setText(PreferenceManager.getString(mContext, "date_show"));
    }
}