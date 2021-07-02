package com.example.covid_test3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class AreaActivity extends AppCompatActivity {
    ArrayAdapter<String> arrayAdapter;
    Spinner spinner1, spinner2;
    String SMP = ""; // 광역시도
    String CCD = ""; // 시군구

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area);

        TextView textView1=(TextView)findViewById(R.id.tv_region); // 지역을 선택하세요

        spinner1=(Spinner)findViewById(R.id.spinner_SMP); // 광역시도 spinner
        spinner2=(Spinner)findViewById(R.id.spinner_CCD); // 시군구 spinner

        Button button_call1 = (Button) findViewById(R.id.button_call1); // 1339전화
        Button button_call2 = (Button) findViewById(R.id.button_call2); // 119전화
        button_call1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:1339"));
                startActivity(intent);
            }
        });
        button_call2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:119"));
                startActivity(intent);
            }
        });

        //어탭터 선언 및 객체를 스피너에 적용
        arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, (String[])getResources().getStringArray(R.array.광역시도));
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(arrayAdapter);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {   //첫 번째 스피너에서 선택하면 두 번째 스피터로 위치 넘김
                SMP = parent.getItemAtPosition(position).toString();    // 선택된 광역시도
//                Log.d("로그하나",SMP);
                switch (position) {
                    case 0: spinner2.setAdapter(null); break;
                    case 1: setSMPSpinnerAdapterItem(R.array.강원도); break;
                    case 2: setSMPSpinnerAdapterItem(R.array.경기도); break;
                    case 3: setSMPSpinnerAdapterItem(R.array.경상남도); break;
                    case 4: setSMPSpinnerAdapterItem(R.array.경상북도); break;
                    case 5: setSMPSpinnerAdapterItem(R.array.광주광역시); break;
                    case 6: setSMPSpinnerAdapterItem(R.array.대구광역시); break;
                    case 7: setSMPSpinnerAdapterItem(R.array.대전광역시); break;
                    case 8: setSMPSpinnerAdapterItem(R.array.부산광역시); break;
                    case 9: setSMPSpinnerAdapterItem(R.array.서울특별시); break;
                    case 10: setSMPSpinnerAdapterItem(R.array.세종특별자치시); break;
                    case 11: setSMPSpinnerAdapterItem(R.array.울산광역시); break;
                    case 12: setSMPSpinnerAdapterItem(R.array.인천광역시); break;
                    case 13: setSMPSpinnerAdapterItem(R.array.전라남도); break;
                    case 14: setSMPSpinnerAdapterItem(R.array.전라북도); break;
                    case 15: setSMPSpinnerAdapterItem(R.array.제주특별자치도); break;
                    case 16: setSMPSpinnerAdapterItem(R.array.충청남도); break;
                    case 17: setSMPSpinnerAdapterItem(R.array.충청북도); break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
    }

    void setSMPSpinnerAdapterItem(int array_resource) { // 두 번째 스피너
        if (arrayAdapter != null) { // 시군구 초기화
            spinner2.setAdapter(null);
            arrayAdapter = null;
        }

        TextView textView2=(TextView)findViewById(R.id.tv_region2); // 선택한 주소 저장
        Button button_url = (Button) findViewById(R.id.button_url); // 선택한 주소의 코로나19 안내 홈페이지 안내

        arrayAdapter = new ArrayAdapter<String>(AreaActivity.this, android.R.layout.simple_spinner_item, (String[])getResources().getStringArray(array_resource));
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(arrayAdapter);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CCD = parent.getItemAtPosition(position).toString();    // 선택된 시군구

                textView2.setText(String.format("주소: %s %s", SMP, CCD)); // 선택된 시군구 보여주기
                button_url.setOnClickListener(new View.OnClickListener() {  // 선택한 주소의 코로나19 안내 홈페이지 안내
                    @Override
                    public void onClick(View v) {
                        Intent urlintent = new Intent(Intent.ACTION_VIEW);
                        if((SMP + CCD).equals("강원도강릉시")) { urlintent.setData(Uri.parse(getResources().getString(R.string.강원도강릉시))); }
                        else if((SMP + CCD).equals("강원도고성군")) { urlintent.setData(Uri.parse(getResources().getString(R.string.강원도고성군))); }
                        else if((SMP + CCD).equals("강원도동해시")) { urlintent.setData(Uri.parse(getResources().getString(R.string.강원도동해시))); }
                        else if((SMP + CCD).equals("강원도삼척시")) { urlintent.setData(Uri.parse(getResources().getString(R.string.강원도삼척시))); }
                        else if((SMP + CCD).equals("강원도속초시")) { urlintent.setData(Uri.parse(getResources().getString(R.string.강원도속초시))); }
                        else if((SMP + CCD).equals("강원도양구군")) { urlintent.setData(Uri.parse(getResources().getString(R.string.강원도양구군))); }
                        else if((SMP + CCD).equals("강원도양양군")) { urlintent.setData(Uri.parse(getResources().getString(R.string.강원도양양군))); }
                        else if((SMP + CCD).equals("강원도영월군")) { urlintent.setData(Uri.parse(getResources().getString(R.string.강원도영월군))); }
                        else if((SMP + CCD).equals("강원도원주시")) { urlintent.setData(Uri.parse(getResources().getString(R.string.강원도원주시))); }
                        else if((SMP + CCD).equals("강원도인제군")) { urlintent.setData(Uri.parse(getResources().getString(R.string.강원도인제군))); }
                        else if((SMP + CCD).equals("강원도정선군")) { urlintent.setData(Uri.parse(getResources().getString(R.string.강원도정선군))); }
                        else if((SMP + CCD).equals("강원도철원군")) { urlintent.setData(Uri.parse(getResources().getString(R.string.강원도철원군))); }
                        else if((SMP + CCD).equals("강원도춘천시")) { urlintent.setData(Uri.parse(getResources().getString(R.string.강원도춘천시))); }
                        else if((SMP + CCD).equals("강원도태백시")) { urlintent.setData(Uri.parse(getResources().getString(R.string.강원도태백시))); }
                        else if((SMP + CCD).equals("강원도평창군")) { urlintent.setData(Uri.parse(getResources().getString(R.string.강원도평창군))); }
                        else if((SMP + CCD).equals("강원도홍천군")) { urlintent.setData(Uri.parse(getResources().getString(R.string.강원도홍천군))); }
                        else if((SMP + CCD).equals("강원도화천군")) { urlintent.setData(Uri.parse(getResources().getString(R.string.강원도화천군))); }
                        else if((SMP + CCD).equals("강원도횡성군")) { urlintent.setData(Uri.parse(getResources().getString(R.string.강원도횡성군))); }
                        startActivity(urlintent);
                    }
                });
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
    }
}