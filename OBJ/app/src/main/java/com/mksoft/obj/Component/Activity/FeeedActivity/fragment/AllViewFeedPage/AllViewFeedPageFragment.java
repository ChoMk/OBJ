package com.mksoft.obj.Component.Activity.FeeedActivity.fragment.AllViewFeedPage;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mksoft.obj.Component.Activity.FeeedActivity.FeedRootActivity;
import com.mksoft.obj.Component.Activity.MainActivity;
import com.mksoft.obj.Component.Activity.FeeedActivity.fragment.OfferedImagePage.OfferedImagePageFragment;
import com.mksoft.obj.R;
import com.mksoft.obj.Repository.APIRepo;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import dagger.android.support.AndroidSupportInjection;

public class AllViewFeedPageFragment extends Fragment {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    FeedAdapter feedAdapter;


    FloatingActionButton fab;

    FragmentTransaction fragmentTransaction;

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
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.feed_page_all_view_feed_page_fragment, container, false);
        init(rootView);

        clickAddButton();

        hideKeyboard();

        return rootView;
    }

    private void init(ViewGroup rootView){

        fab = (FloatingActionButton) rootView.findViewById(R.id.fab_main);

        recyclerView = (RecyclerView)rootView.findViewById(R.id.feedRecyclerView);
        layoutManager = new LinearLayoutManager(rootView.getContext());

        initListView();
    }

    private void initListView(){

        recyclerView.setHasFixedSize(true);
        feedAdapter = new FeedAdapter(getContext());
        recyclerView.setAdapter(feedAdapter);
        recyclerView.setLayoutManager(layoutManager);

    }

    private void clickAddButton(){
        if(fab == null)
            return;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentTransaction = getFragmentManager().beginTransaction();
                FeedRootActivity.feedRootActivity.getSupportFragmentManager().popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                fragmentTransaction.replace(R.id.feedRootMainContainer, new OfferedImagePageFragment(), null);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }
    private void hideKeyboard(){
        FeedRootActivity.feedRootActivity.getHideKeyboard().hideKeyboard();
    }

}
