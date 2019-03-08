package com.david_kapas.android.techtest.di.components;

import com.david_kapas.android.techtest.di.modules.ListActivityModule;
import com.david_kapas.android.techtest.di.modules.PostDetailsActivityModule;
import com.david_kapas.android.techtest.di.scopes.PerActivity;
import com.david_kapas.android.techtest.presentation.details.router.PostDetailsActivity;
import com.david_kapas.android.techtest.presentation.posts.router.ListActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by David_Kapas on 3/23/2018.
 */
@Module
public abstract class ActivityBuilder {

    @PerActivity
    @ContributesAndroidInjector(modules = ListActivityModule.class)
    abstract ListActivity bindListActivity();

    @PerActivity
    @ContributesAndroidInjector(modules = {PostDetailsActivityModule.class})
    abstract PostDetailsActivity bindPostDetailsActivity();
}
