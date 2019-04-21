package com.mksoft.obj.Component.Activity.fragment.AllViewFeedPage;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mksoft.obj.Component.Activity.MainActivity;
import com.mksoft.obj.Component.Activity.fragment.OfferedImagePage.OfferedImageAdapter;
import com.mksoft.obj.Component.Activity.fragment.OfferedImagePage.OfferedImagePageFragment;
import com.mksoft.obj.R;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class AllViewFeedPageFragment extends Fragment {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    FeedAdapter feedAdapter;


    FloatingActionButton fab;

    FragmentTransaction fragmentTransaction;


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
        //this.configureDagger();
    }
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.all_view_feed_page_fragment, container, false);

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
                MainActivity.mainActivity.getSupportFragmentManager().popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                fragmentTransaction.replace(R.id.mainContainer, new OfferedImagePageFragment(), null);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }
    private void hideKeyboard(){
        MainActivity.mainActivity.getHideKeyboard().hideKeyboard();
    }

}
