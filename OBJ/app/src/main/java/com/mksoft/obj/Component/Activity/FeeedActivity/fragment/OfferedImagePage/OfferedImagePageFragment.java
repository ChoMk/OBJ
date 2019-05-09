package com.mksoft.obj.Component.Activity.FeeedActivity.fragment.OfferedImagePage;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mksoft.obj.Component.Activity.FeeedActivity.FeedRootActivity;
import com.mksoft.obj.Component.Activity.MainActivity;
import com.mksoft.obj.R;
import com.mksoft.obj.Repository.APIRepo;
import com.mksoft.obj.Repository.Data.OfferedImageData;

import java.util.ArrayList;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import dagger.android.support.AndroidSupportInjection;

public class OfferedImagePageFragment extends Fragment implements FeedRootActivity.onKeyBackPressedListener{
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    OfferedImageAdapter offeredImageAdapter;

    String userName;
    OfferedImagePageFragment offeredImagePageFragment;


    @Inject
    APIRepo apiRepo;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((FeedRootActivity) context).setOnKeyBackPressedListener(null);
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
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.feed_page_offered_image_page_fragment, container, false);


        init(rootView);


        hideKeyboard();


        return rootView;
    }

    private void init(ViewGroup rootView){
        offeredImagePageFragment = this;
        userName = getArguments().getString("userName");
        recyclerView = (RecyclerView)rootView.findViewById(R.id.offeredImageRecyclerView);
        layoutManager = new LinearLayoutManager(rootView.getContext());

        initListView();

    }

    private void initListView(){

        recyclerView.setHasFixedSize(true);
        offeredImageAdapter = new OfferedImageAdapter(getContext(), offeredImagePageFragment);
        recyclerView.setAdapter(offeredImageAdapter);
        recyclerView.setLayoutManager(layoutManager);

        makeOfferedList();//어뎁터에 들어갈 이미지 초기화

    }


    private void hideKeyboard(){
        FeedRootActivity.feedRootActivity.getHideKeyboard().hideKeyboard();
    }

    private void makeOfferedList(){
        ArrayList<OfferedImageData> offeredImageDataList = new ArrayList<>();

        offeredImageDataList.add(new OfferedImageData(1, "커플댄스", R.drawable.test1));
        offeredImageDataList.add(new OfferedImageData(2, "흔들어~", R.drawable.test2));



        offeredImageAdapter.refreshItem(offeredImageDataList);
    }//어뎁터에 들어갈 이미지 초기화
    @Override
    public void onBackKey() {
        FeedRootActivity.feedRootActivity.setOnKeyBackPressedListener(null);
        FeedRootActivity.feedRootActivity.onBackPressed();
    }
    public String getUserName(){
        return userName;
    }
}
