package com.mksoft.obj.Component.Activity.fragment.UserFriendPickPage;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.mksoft.obj.Component.Activity.MainActivity;
import com.mksoft.obj.Component.Activity.fragment.AllViewFeedPage.AllViewFeedPageFragment;
import com.mksoft.obj.Component.Activity.fragment.OfferedImagePage.OfferedImageAdapter;
import com.mksoft.obj.R;
import com.mksoft.obj.Repository.APIRepo;
import com.mksoft.obj.Repository.Data.FriendData;
import com.mksoft.obj.Repository.Data.OfferedImageData;

import java.util.ArrayList;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import dagger.android.support.AndroidSupportInjection;

public class UserFriendPickPageFragment extends Fragment {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    UserFriendAdapter userFriendAdapter;
    FragmentTransaction fragmentTransaction;
    String selectedOfferedImageName;

    Button submitButton;
    @Inject
    APIRepo apiRepo;

    AlertDialog dialog;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((MainActivity) context).setOnKeyBackPressedListener(null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //this.configureViewModel();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.configureDagger();

    }
    private void configureDagger(){
        AndroidSupportInjection.inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.user_friend_pick_page_fragment, container, false);

        init(rootView);

        clickAddButton();

        hideKeyboard();

        return rootView;
    }

    private void init(ViewGroup rootView){

        selectedOfferedImageName = getArguments().getString("imageName");
        Log.d("argu", selectedOfferedImageName);
        recyclerView = (RecyclerView)rootView.findViewById(R.id.userFriendRecyclerView);
        layoutManager = new LinearLayoutManager(rootView.getContext());
        submitButton = rootView.findViewById(R.id.submitButton);
        initListView();
        testMakeFriend();
    }

    private void initListView(){

        recyclerView.setHasFixedSize(true);
        userFriendAdapter = new UserFriendAdapter(getContext());
        recyclerView.setAdapter(userFriendAdapter);
        recyclerView.setLayoutManager(layoutManager);

    }

    private void clickAddButton(){

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogShow();
            }
        });
    }
    private void hideKeyboard(){
        MainActivity.mainActivity.getHideKeyboard().hideKeyboard();
    }
    private void testMakeFriend(){
        ArrayList<FriendData> friendDataList = new ArrayList<>();

        friendDataList.add(new FriendData(1, "규택"));
        friendDataList.add(new FriendData(2, "동민"));



        userFriendAdapter.refreshItem(friendDataList);
    }
    private void dialogShow()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(userFriendAdapter.getItem(userFriendAdapter.getLastSelectedPosition()).getName()+"과 "
        +selectedOfferedImageName+"을 추시겠습니까?");
        builder.setMessage("한번 더 확인하세요 ! ");
        builder.setPositiveButton("예",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        fragmentTransaction = MainActivity.mainActivity.getSupportFragmentManager().beginTransaction();
                        MainActivity.mainActivity.getSupportFragmentManager().popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        fragmentTransaction.replace(R.id.mainContainer, new AllViewFeedPageFragment(), null);
                        fragmentTransaction.commit();
                    }
                });
        builder.setNegativeButton("아니오",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.show();
    }

}
