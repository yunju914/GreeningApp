package com.example.greeningapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ManageUserOrderActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<MyOrder> arrayList;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    private Button btnManageMain;

    ImageView MGProductImg_order;
    TextView MGOrderID_order, MGOrderDate_order, MGProductName_order, MGOrderPrice_order, MGOrderStock_order, MGOrderState_order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_user_order);

        recyclerView = findViewById(R.id.recyclerView); //아디 연결
        recyclerView.setHasFixedSize(true); //리사이클러뷰 기존 성능 강화
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        arrayList = new ArrayList<>(); // Product 객체를 담을 어레이리스트(어댑터 쪽으로 날릴 거임)


        firebaseDatabase = FirebaseDatabase.getInstance(); // 파이어베이스 데이터베이스 연동
        databaseReference = firebaseDatabase.getReference("Admin"); //DB 연결 성공

//        arrayList.clear();
//        adapter.notifyDataSetChanged(); // 리스트 저장 및 새로고침
//        MyOrder myOrder = orderSnapshot.getValue(MyOrder.class);
//        arrayList.add(myOrder);

        databaseReference.child("UserOrder").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // 파이어베이스 데이터베이스의 데이터를 받아오는 곳
                arrayList.clear(); //기존 배열 리스트가 존재하지 않게 남아 있는 데이터 초기화
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    // 반복문으로 데이터 List를 추출해냄
                    MyOrder myOrder = snapshot.getValue(MyOrder.class); //  만들어 뒀던 Product 객체에 데이터를 담는다.
                    arrayList.add(myOrder); // 담은 데이터들을 배열 리스트에 넣고 리사이클러뷰로 보낼 준비
                    Log.d("ManageUserActivity", snapshot.getKey()+"");

                }
                adapter.notifyDataSetChanged(); // 리스트 저장 및 새로고침

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // 디비를 가져오던 중 에러 발생 시
                Log.e("ManageUserActivity", String.valueOf(databaseError.toException())); // 에러문 출력
            }
        });

        adapter = new ManageUserOrderAdapter(arrayList, this);
        recyclerView.setAdapter(adapter); //리사이클러뷰에 어댑터 연결



        btnManageMain = (Button) findViewById(R.id.btnManageMain_Order);
        btnManageMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManageUserOrderActivity.this, ManagerMainActivity.class);
                startActivity(intent);
            }
        });
    }
}