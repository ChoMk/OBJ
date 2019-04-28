package com.mksoft.obj.Component.Activity.LoginActivity.Fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.mksoft.obj.Component.Activity.LoginActivity.LoginRootActivity;
import com.mksoft.obj.Component.Activity.MainActivity;
import com.mksoft.obj.R;
import com.mksoft.obj.Repository.APIRepo;
import com.mksoft.obj.Repository.Data.UserData;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import dagger.android.support.AndroidSupportInjection;

public class JoinPageFragment extends Fragment implements LoginRootActivity.onKeyBackPressedListener{


    JoinPageFragment joinPageFragment;
    RelativeLayout joinPageRelativeLayout;

    EditText joinPageIDEditText;
    TextView joinPageIDstate;

    EditText joinpageNameText;

    Button joinPageJoinButton;

    boolean idState = false;

    public void setIdState(boolean idState) {
        this.idState = idState;
    }

    @Inject
    APIRepo apiRepo;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.configureDagger();
    }
    private void configureDagger(){
        AndroidSupportInjection.inject(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((LoginRootActivity) context).setOnKeyBackPressedListener(this);
    }
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.login_page_join_fragment, container, false);
        initUI(rootView);
        hideKeyboard();
        clickHideKeyboard();

        clickJoin();
        focusEditView();
        return rootView;
    }

    private void initUI(ViewGroup rootView){
        joinPageFragment = this;
        joinPageRelativeLayout = rootView.findViewById(R.id.loginProjectMakeIdRelativeLayout);

        joinPageIDEditText = rootView.findViewById(R.id.loginProjectMakeIdIDEditText);
        joinPageIDstate = rootView.findViewById(R.id.loginProjectMakeIdIDstate);

        joinpageNameText = rootView.findViewById(R.id.loginProjectMakeIdNameEditText);

        joinPageJoinButton = rootView.findViewById(R.id.loginProjectMakeIdJoinButton);

    }
    private void hideKeyboard(){
        LoginRootActivity.loginRootActivity.getHideKeyboard().hideKeyboard();
    }
    private void clickHideKeyboard(){
        joinPageRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard();
                IDCheckState();
            }
        });
    }
    private void IDCheckState(){
        String tempID = joinPageIDEditText.getText().toString();
        if(tempID!=null&&tempID.length() != 0){
            if(tempID.length()<6){
                joinPageIDstate.setText("아이디는 6자 이상으로 설정하세요.");
                joinPageIDstate.setTextColor(Color.parseColor("#FFF35757"));
                return;
            }
            apiRepo.checkUser(tempID, this, joinPageIDstate);
            return;
        }
    }
    private void clickJoin(){
        joinPageJoinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(idState && joinpageNameText.getText().toString()!=null
                &&joinpageNameText.getText().toString().length() != 0){

                    UserData userData = new UserData();
                    userData.setName(joinpageNameText.getText().toString());
                    userData.setId(joinPageIDEditText.getText().toString());
                    apiRepo.postUser(joinPageIDEditText.getText().toString(), userData, joinPageFragment);


                    //서버에 아이디 넣기
                }else{
                    Toast.makeText(LoginRootActivity.loginRootActivity.getApplicationContext(),
                            "입력창을 확인하세요.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void focusEditView(){
        joinPageIDEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                IDCheckState();
            }
        });
    }//포커스 변경시 마다 적절한 인풋이 들어 갔는지 체크
    //이미 가입된 정보인지를 checkstate()에서 검토하자

    @Override
    public void onBackKey() {

        LoginRootActivity.loginRootActivity.setOnKeyBackPressedListener(null);
        LoginRootActivity.loginRootActivity.onBackPressed();
    }
}
