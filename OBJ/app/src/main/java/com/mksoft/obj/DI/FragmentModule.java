package com.mksoft.obj.DI;



import com.mksoft.obj.Component.Activity.FeeedActivity.fragment.AllViewFeedPage.AllViewFeedPageFragment;
import com.mksoft.obj.Component.Activity.FeeedActivity.fragment.OfferedImagePage.OfferedImagePageFragment;
import com.mksoft.obj.Component.Activity.FeeedActivity.fragment.UserFriendPickPage.UserFriendPickPageFragment;
import com.mksoft.obj.Component.Activity.LoginActivity.Fragment.JoinPageFragment;
import com.mksoft.obj.Component.Activity.LoginActivity.Fragment.LoginPageFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by Philippe on 02/03/2018.
 */

@Module
public abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract AllViewFeedPageFragment contributeAllViewFeedPageFragment();

    @ContributesAndroidInjector
    abstract OfferedImagePageFragment contributeOfferedImagePageFragment();

    @ContributesAndroidInjector
    abstract UserFriendPickPageFragment contributeUserFriendPickPageFragment();

    @ContributesAndroidInjector
    abstract LoginPageFragment contributeLoginPageFragment();

    @ContributesAndroidInjector
    abstract JoinPageFragment contributeUserJoinPageFragment();



}
