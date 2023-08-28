package com.example.greeningapp;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class PwdChangeActivity extends AppCompatActivity {
    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabaseRef;
    private ImageButton backButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pwd_change);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        backButton = findViewById(R.id.back_ic);

        Button resetPwButton = findViewById(R.id.btnPassword);
        resetPwButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setUpdatePasswordBtn();
            }
        });


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
    }

    private void setUpdatePasswordBtn () { // 비밀번호 재설정 버튼 이벤트

        // 팝업 다이얼로그
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("비밀번호 재설정");
        builder.setMessage("비밀번호 재설정 이메일을 보내시겠습니까?");

        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                sendEmailForPasswordUpdate();
            }
        });

        builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // 아무 작업도 수행하지 않음
            }
        });
    }

    // 비밀번호 재설정 이메일 보내기
    private void sendEmailForPasswordUpdate () {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String email = getEmail();

        if (email != null) {
            auth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Snackbar.make(getWindow().getDecorView().getRootView(), "이메일이 전송되었습니다", Snackbar.LENGTH_LONG).show();
                } else {
                    Snackbar.make(getWindow().getDecorView().getRootView(), "이메일 전송 실패", Snackbar.LENGTH_LONG).show();
                }
            });
        } else {
            Snackbar.make(getWindow().getDecorView().getRootView(), "해당 이메일이 존재하지 않습니다", Snackbar.LENGTH_LONG).show();
        }
    }

    // 사용자 이메일 가져오기
    private String getEmail () {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            String email = user.getEmail();
            return email != null ? email.toString() : null;
        } else {
            // 사용자가 로그인하지 않음
            return null;
        }
    }
}