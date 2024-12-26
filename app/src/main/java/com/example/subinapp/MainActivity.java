package com.example.subinapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.subinapp.helper.DatabaseHelper;

public class MainActivity extends AppCompatActivity {

    // 변수선언
    private EditText editTextUserid, editTextPasswd, editTextName, editTextEmail;
    private Button buttonJoin, ButtonUserlist;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 뷰 초기화
        editTextUserid = findViewById(R.id.editTextUserid);
        editTextPasswd = findViewById(R.id.editTextTextPasswd);
        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextTextEmailAddress);
        buttonJoin = findViewById(R.id.button);
        ButtonUserlist = findViewById(R.id.button2);

        // db 초기화
        databaseHelper = new DatabaseHelper(this);

        // 회원가입 이벤트 처리
        buttonJoin.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        registerUser();
                    }
                }


        );

        ButtonUserlist.setOnClickListener(
             new View.OnClickListener(){
                 @Override
                 public void onClick(View v){
                     Intent intent = new Intent(MainActivity.this, UserlistActivity.class);
                     startActivity(intent);
                 }
             }
        );
    }

    private void registerUser() {
        // 변수 초기화
        String userid = editTextUserid.getText().toString().trim();
        String passwd = editTextPasswd.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String name = editTextName.getText().toString().trim();

        // 입력값 검증
        if (userid.isEmpty() || passwd.isEmpty()
                || name.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, "모든 필드를 입력하세요!", Toast.LENGTH_SHORT).show();
            return;  // 여기서 중지
        }

        // 중복 아이디 체크
        if (databaseHelper.useridCheck(userid)){
            Toast.makeText(this, "이미 사용중인 아이디 입니다.", Toast.LENGTH_SHORT).show();
            return;
        }


        // 회원 저장
        boolean success =
                databaseHelper.insertMember(userid, passwd, name, email);
        if (success) {
            Toast.makeText(this, "✨회원 가입 성공!✨", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "😱회원 가입 실패!!😱 다시 시도하세요!!", Toast.LENGTH_SHORT).show();
        }
    }
}