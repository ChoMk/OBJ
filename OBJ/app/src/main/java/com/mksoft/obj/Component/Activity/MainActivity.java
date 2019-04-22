package com.mksoft.obj.Component.Activity;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mksoft.obj.Component.Activity.fragment.AllViewFeedPage.AllViewFeedPageFragment;
import com.mksoft.obj.OtherMethod.HideKeyboard;
import com.mksoft.obj.R;
import com.mksoft.obj.Repository.FriendData;
import com.mksoft.obj.Repository.UserData;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Database;

public class MainActivity extends AppCompatActivity {



    public static MainActivity mainActivity;
    HideKeyboard hideKeyboard;
    BackPressCloseHandler backPressCloseHandler;

    AllViewFeedPageFragment allViewFeedPageFragment;
    FriendData temp = null;

    //private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    //private DatabaseReference databaseReference = firebaseDatabase.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainActivity = this;
        init();
        /*databaseReference.child("friend").child("1").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                temp = dataSnapshot.getValue(FriendData.class);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });  // 기본 database 하위 message라는 child에 chatData를 list로 만들기
        while(true){
            if(temp != null){
                Toast.makeText(this, temp.getFriendID().get(0), Toast.LENGTH_LONG).show();
            }
        }
        *///파베 테스트

    }
    private void init(){
        hideKeyboard = new HideKeyboard(this);
        allViewFeedPageFragment = new AllViewFeedPageFragment();
        backPressCloseHandler = new BackPressCloseHandler(this);
        getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer, allViewFeedPageFragment).commit();
    }

    //hide keyboard
    public HideKeyboard getHideKeyboard(){
        return hideKeyboard;
    }

    // back key

    public interface onKeyBackPressedListener{
        void onBackKey();
    }
    private onKeyBackPressedListener mOnKeyBackPressedListener;
    public void setOnKeyBackPressedListener(onKeyBackPressedListener listener){
        mOnKeyBackPressedListener = listener;
    }

    @Override
    public void onBackPressed() {
        if(mOnKeyBackPressedListener != null) {
            mOnKeyBackPressedListener.onBackKey();
        }else{
            if(getSupportFragmentManager().getBackStackEntryCount() == 0){
                backPressCloseHandler.onBackPressed();
            }
            else{
                super.onBackPressed();
            }
        }
    }

}
