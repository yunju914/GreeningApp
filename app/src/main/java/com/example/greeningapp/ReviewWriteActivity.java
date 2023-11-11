package com.example.greeningapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class ReviewWriteActivity extends AppCompatActivity {

    String fn;
    String reviewImage;

    private static final int Gallery_Code=1;
    FirebaseUser firebaseUser;
    FirebaseDatabase mDatabase;
    DatabaseReference mRef;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    private DatabaseReference databaseReference2;
    private DatabaseReference databaseReference3;
    //DatabaseReference databaseReference2; //잠시 추가 -> //MyOrder

    private BottomNavigationView bottomNavigationView;

    private ImageButton navMain, navCategory, navDonation, navMypage;

    StorageReference storageReference;
    ImageView uploadImage;
    Button uploadBtn;
    RatingBar RatingBarEt;
    Uri imageUri=null;
    //ImageButton cancelBtn;
    EditText reviewEt;
    MyOrder product = null;
    TextView Pname;
    //TextView Pprice;
    ImageView Pimg;
    TextView mDate;  //날짜

    Dialog Reviewdialog;

    private String orderId,myOrderId, eachOrderedId;
    private int userSPoint;

    Toolbar rtoolbar;
    Dialog dialog;
    Dialog dialog2;
    String reviewId;

    long mNow;
    Date mDate2;
    SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_write);



        rtoolbar = findViewById(R.id.toolbar_reviewwrite);
        setSupportActionBar(rtoolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true); //커스터마이징 하기 위해 필요
        actionBar.setDisplayShowTitleEnabled(false);//기본 제목 삭제.
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_24);



        dialog = new Dialog(ReviewWriteActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_confirm2);

        dialog2 = new Dialog(ReviewWriteActivity.this);
        dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog2.setContentView(R.layout.dialog_confirm);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("CurrentUser").child(firebaseUser.getUid()).child("MyOrder");
        databaseReference2 = FirebaseDatabase.getInstance().getReference("User");
        databaseReference3 = FirebaseDatabase.getInstance().getReference("CurrentUser");

        uploadBtn = findViewById(R.id.writeUploadBtn);
        //Button uploadBtn = findViewById(R.id.writeUploadBtn);
        uploadImage = findViewById(R.id.writeUploadImage);
        reviewEt = findViewById(R.id.writeReviewEt);
        //cancelBtn = findViewById(R.id.writeCancelBtn);
        RatingBarEt = findViewById(R.id.writeRatingBar);

        mDatabase=FirebaseDatabase.getInstance();
        mRef=mDatabase.getReference().child("Review");
        storageReference=FirebaseStorage.getInstance().getReference();
        //날짜 표시
        mDate = findViewById(R.id.reviewDate);

        uploadBtn = findViewById(R.id.writeUploadBtn);


        Pname = findViewById(R.id.writePname);
        Pimg = (ImageView) findViewById(R.id.writePImg);





        final Object object = getIntent().getSerializableExtra("product");

        if(object instanceof MyOrder){
            product = (MyOrder) object;
            Log.d("ReviewWriteActivity", product+"");
        }

        if (product != null) {
            Pname.setText(product.getProductName());
            Glide.with(getApplicationContext()).load(product.getOrderImg()).into(Pimg);

        }

        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,Gallery_Code);
            }
        });


        // 하단바 구현
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigation_ReviewWrite);
        // 초기 선택 항목 설정
        bottomNavigationView.setSelectedItemId(R.id.tab_mypage);

        // BottomNavigationView의 아이템 클릭 리스너 설정
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.tab_home) {
                    // Home 액티비티로 이동
                    startActivity(new Intent(ReviewWriteActivity.this, MainActivity.class));
                    finish();
                    return true;
                } else if (item.getItemId() == R.id.tab_shopping) {
                    // Category 액티비티로 이동
                    startActivity(new Intent(ReviewWriteActivity.this, CategoryActivity.class));
                    finish();
                    return true;
                } else if (item.getItemId() == R.id.tab_donation) {
                    // Donation 액티비티로 이동
                    startActivity(new Intent(ReviewWriteActivity.this, DonationMainActivity.class));
                    finish();
                    return true;
                } else if (item.getItemId() == R.id.tab_mypage) {
                    // My Page 액티비티로 이동
                    startActivity(new Intent(ReviewWriteActivity.this, MyPageActivity.class));
                    finish();
                    return true;
                }
                return false;
            }
        });

        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fn = reviewEt.getText().toString().trim();

                if (!fn.isEmpty()) {
                    // 이미지 업로드 및 리뷰 데이터 저장 로직
                    uploadImagesAndSaveData();
                } else {
                    showReivewDialog();
                }

                // 구매후기 작성 시 포인트 적립
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                databaseReference2.child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User user = snapshot.getValue(User.class);
                        userSPoint = user.getSpoint();
                        double changePoint = userSPoint + 50;
                        databaseReference2.child(firebaseUser.getUid()).child("spoint").setValue(changePoint).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
//                                Toast.makeText(ReviewWriteActivity.this, "포인트 지급 성공", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                // 구매후기 작성 시 포인트 데이터 저장
                databaseReference2.child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User user = snapshot.getValue(User.class);
                        final HashMap<String, Object> pointMap = new HashMap<>();
                        pointMap.put("pointName", "씨드 적립 - 구매후기 작성");
                        pointMap.put("pointDate", getTime());
                        pointMap.put("type", "savepoint");
                        pointMap.put("point", 50);
                        pointMap.put("userName", user.getUsername());

                        String pointID = databaseReference3.child(firebaseUser.getUid()).child("MyPoint").push().getKey();
                        databaseReference3.child(firebaseUser.getUid()).child("MyPoint").child(pointID).setValue(pointMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
//                                Toast.makeText(ReviewWriteActivity.this, "포인트 데이터 저장 성공", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                databaseReference.child(product.getOrderId()).child(product.getEachOrderedId()).child("doReview").setValue("Yes").addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Myreview", "myOrderId: " + myOrderId);
                        Log.d("eachorderid", "eachOrderedId: " + eachOrderedId);

                    }
                });

                showDialog();
            }
        });

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @NonNull Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==Gallery_Code && resultCode == RESULT_OK)
        {
            imageUri =data.getData();
            uploadImage.setImageURI(imageUri);
        }
    }

    private void uploadImagesAndSaveData() {
        if (!fn.isEmpty()) {
            if (imageUri != null) {
                // 이미지 업로드 로직
                StorageReference filePath1 = storageReference.child("image").child(imageUri.getLastPathSegment());
                filePath1.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if (task.isSuccessful()) {
                            float rating = RatingBarEt.getRating();
                            reviewImage = imageUri.toString();
                            saveReviewData(rating);
                        } else {
//                            Toast.makeText(ReviewWriteActivity.this, "이미지 업로드 실패", Toast.LENGTH_SHORT).show();
                            Log.e("Image Upload", "Image upload failed: " + task.getException().getMessage());
                        }
                    }
                });
            } else {
                float rating = RatingBarEt.getRating();
                reviewImage = "";
                saveReviewData(rating);
            }
        } else {
            showReivewDialog();
        }
    }

    private void saveReviewData(float rating) {
        // 리뷰 데이터 저장 로직
        reviewId =  mRef.push().getKey();
        DatabaseReference productRef = mRef.child(String.valueOf(reviewId));
        productRef.child("pid").setValue(product.getProductId());
        productRef.child("pname").setValue(product.getProductName());
        productRef.child("pimg").setValue(product.getOrderImg());
        productRef.child("username").setValue(product.getUserName());
        productRef.child("pprice").setValue(product.getProductPrice());
        productRef.child("totalquantity").setValue(product.getTotalQuantity());
        productRef.child("rimage").setValue(reviewImage);
        productRef.child("rcontent").setValue(fn);
        productRef.child("rscore").setValue(rating);
        productRef.child("rdatetime").setValue(getTime());
        productRef.child("reviewid").setValue(reviewId);
    }

    private String getTime(){
        mNow = System.currentTimeMillis();
        mDate2 = new Date(mNow);
        return mFormat.format(mDate2);
    }
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


    public void showDialog() {
        dialog.show();

        TextView confirmTextView = dialog.findViewById(R.id.confirmTextView);
        confirmTextView.setText("후기 작성을 완료했습니다.\n감사합니다.");

        Button btnleft = dialog.findViewById(R.id.btn_left);
        btnleft.setText("후기 확인");
        btnleft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReviewWriteActivity.this, ReviewHistoryActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Button btnright = dialog.findViewById(R.id.btn_right);
        btnright.setText("홈 이동");
        btnright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReviewWriteActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void showReivewDialog() {
        dialog2.show();

        TextView confirmTextView = dialog2.findViewById(R.id.confirmTextView);
        confirmTextView.setText("후기를 작성해주세요.");

        Button btnOk = dialog2.findViewById(R.id.btn_ok);
        btnOk.setText("확인");


        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog2.dismiss();
//                Intent intent = new Intent(ProductDetailActivity.this, MainActivity.class);
//                startActivity(intent);
            }
        });
    }
}