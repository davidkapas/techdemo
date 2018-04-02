package com.david_kapas.android.techtest.di.components;

import com.david_kapas.android.techtest.di.modules.ListActivityModule;
import com.david_kapas.android.techtest.di.scopes.PerActivity;
import com.david_kapas.android.techtest.presentation.posts.router.ListActivity;

import dagger.Component;

/**
 * Class for satisfying the ListActivity-element of graph.
 * Created by David Kapas on 2018.03.17.
 */
@Component(dependencies = ApplicationComponent.class, modules = {ListActivityModule.class})
@PerActivity
public interface ListActivityComponent {

    void inject(ListActivity listActivity);

    /**
     * Injector class for injecting the component into the activity.
     * The class help to avoid the boiler-plate dagger coding in injected class.
     */

    final class Injector {
        private Injector() {
        }

        public static ListActivityComponent buildComponent(ListActivity activity) {
            return DaggerListActivityComponent.builder()
                    .applicationComponent(ApplicationComponent.Injector.getComponent())
                    .listActivityModule(new ListActivityModule(activity))
                    .build();
        }
    }
}
