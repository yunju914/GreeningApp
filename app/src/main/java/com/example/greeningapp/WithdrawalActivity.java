package com.example.greeningapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class WithdrawalActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth; // 파이어베이스 인증 처리
    private DatabaseReference mDatabaseRef; // 실시간 데이터베이스
    private RadioButton radioButton;
    private Button cancelButton, withdrawalButton;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdrawal);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_24);

        radioButton = findViewById(R.id.radioButton);
        cancelButton = findViewById(R.id.wbtn1);
        withdrawalButton = findViewById(R.id.wbtn2);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // RadioButton 상태 변경 시
        radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (radioButton.isChecked()) {
                   withdrawalButton.setEnabled(true);
                } else {
                    withdrawalButton.setEnabled(false);
                }
            }
        });

        // 탈퇴하기 버튼 클릭 시
        withdrawalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (radioButton.isChecked()) {
                    showConfirmationDialog();
                }
            }
        });
    }

    // 팝업 창 표시
    private void showConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("탈퇴 확인");
        builder.setMessage("정말로 이 앱을 탈퇴하시겠습니까?");
        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 사용자 계정 삭제
                deleteAccount();
            }
        });

        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }


    private void deleteAccount() {
        mFirebaseAuth = mFirebaseAuth.getInstance();
//        mFirebaseAuth = mFirebaseAuth.getCurrentUser();
        if (mFirebaseAuth != null) {
            mFirebaseAuth.getCurrentUser().delete()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(WithdrawalActivity.this);
                                builder.setTitle("탈퇴 성공");
                                builder.setMessage("탈퇴가 완료되었습니다. 로그인 화면으로 이동합니다");
                                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // 확인 버튼을 클릭하면 LoginActivity로 이동합니다.
                                        Intent intent = new Intent(WithdrawalActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                });
                                AlertDialog dialog = builder.create();
                                dialog.show();
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(WithdrawalActivity.this);
                                builder.setTitle("탈퇴 오류");
                                builder.setMessage("다시 시도해주세요.");
                                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // 확인 버튼을 클릭하면 LoginActivity로 이동합니다.
                                        Intent intent = new Intent(WithdrawalActivity.this, MyPageActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                });

                                AlertDialog dialog = builder.create();
                                dialog.show();
                            }
                        }
                    });
        }
    }
//    private void showSuccessDialog() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("계정삭제성공");
//        builder.setMessage("계정이 성공적으로 삭제됐습니다. 다시 회원가입 하시겠습니까?");
//        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                Intent intent = new Intent(WithdrawalActivity.this, RegisterActivity.class);
//                startActivity(intent);
//            }
//        });
//        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                finish();
//            }
//        });
//        builder.create().show();
//    }
        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int itemId = item.getItemId();
            if (itemId == android.R.id.home) { //뒤로가기
                onBackPressed();
                return true;
            } else {
                return super.onOptionsItemSelected(item);
            }
        }
}