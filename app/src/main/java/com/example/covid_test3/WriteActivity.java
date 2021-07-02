package com.example.covid_test3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class WriteActivity extends AppCompatActivity {

    long now = System.currentTimeMillis();
    Date mDate = new Date(now);
    SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    String getTime = simpleDate.format(mDate);

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        Button check = (Button) findViewById(R.id.check);
        check.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                profileUpdate();
                Intent intent = new Intent(getApplicationContext(), GesipanActivity.class);
                startActivity(intent);
            }
        });

    }

    private void profileUpdate() {
        mAuth = FirebaseAuth.getInstance();
        final String title = ((EditText) findViewById(R.id.boardtitle)).getText().toString();
        final String contents = ((EditText) findViewById(R.id.boardcontents)).getText().toString();
        final String address = mAuth.getUid();

        if (title.length() > 0 && contents.length() > 0) {
            Contents writeinfo = new Contents(title, contents, getTime, address);
            uploader(writeinfo);
        } else {
            Toast.makeText(WriteActivity.this, "내용 등록 실패", Toast.LENGTH_SHORT).show();
        }
    }

    private void uploader(Contents writeinfo){
        database = FirebaseDatabase.getInstance(); // 파이어베이스 데이터베이스 연동
        databaseReference = database.getReference("Contents").child(writeinfo.getTitle());

        databaseReference.setValue(writeinfo);

    }
}