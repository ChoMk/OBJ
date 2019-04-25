package com.mksoft.obj.DI;



import com.mksoft.obj.Component.Activity.fragment.AllViewFeedPage.AllViewFeedPageFragment;
import com.mksoft.obj.Component.Activity.fragment.OfferedImagePage.OfferedImagePageFragment;
import com.mksoft.obj.Component.Activity.fragment.UserFriendPickPage.UserFriendPickPageFragment;

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


}
