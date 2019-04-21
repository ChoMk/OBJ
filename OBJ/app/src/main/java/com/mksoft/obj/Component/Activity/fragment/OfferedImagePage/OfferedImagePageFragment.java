package com.mksoft.obj.Component.Activity.fragment.OfferedImagePage;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.mksoft.obj.Component.Activity.MainActivity;
import com.mksoft.obj.Component.Activity.fragment.UserFriendPickPage.UserFriendPickPageFragment;
import com.mksoft.obj.R;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class OfferedImagePageFragment extends Fragment {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    OfferedImageAdapter offeredImageAdapter;
    FragmentTransaction fragmentTransaction;


    Button nextButton;
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
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.offered_image_page_fragment, container, false);

        init(rootView);

        clickAddButton();

        hideKeyboard();

        return rootView;
    }

    private void init(ViewGroup rootView){


        recyclerView = (RecyclerView)rootView.findViewById(R.id.offeredImageRecyclerView);
        layoutManager = new LinearLayoutManager(rootView.getContext());
        nextButton = rootView.findViewById(R.id.nextButton);
        initListView();
    }

    private void initListView(){

        recyclerView.setHasFixedSize(true);
        offeredImageAdapter = new OfferedImageAdapter(getContext());
        recyclerView.setAdapter(offeredImageAdapter);
        recyclerView.setLayoutManager(layoutManager);

    }

    private void clickAddButton(){

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentTransaction = getFragmentManager().beginTransaction();
                MainActivity.mainActivity.getSupportFragmentManager().popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                fragmentTransaction.replace(R.id.mainContainer, new UserFriendPickPageFragment(), null);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }
    private void hideKeyboard(){
        MainActivity.mainActivity.getHideKeyboard().hideKeyboard();
    }

}
