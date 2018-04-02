package com.david_kapas.android.techtest.di.components;

import com.david_kapas.android.techtest.di.modules.PostDetailsActivityModule;
import com.david_kapas.android.techtest.di.scopes.PerActivity;
import com.david_kapas.android.techtest.presentation.details.router.PostDetailsActivity;

import dagger.Component;

/**
 * Class for satisfying the PostDetailsActivity-element of graph.
 * Created by David Kapas on 2018.03.18.
 */
@Component(dependencies = ApplicationComponent.class, modules = {PostDetailsActivityModule.class})
@PerActivity
public interface PostDetailsActivityComponent {

    void inject(PostDetailsActivity postDetailsActivity);

    /**
     * Injector class for injecting the component into the activity.
     * The class help to avoid the boiler-plate dagger coding in injected class.
     */

    final class Injector {
        private Injector() {
        }

        public static PostDetailsActivityComponent buildComponent(PostDetailsActivity activity) {
            return DaggerPostDetailsActivityComponent.builder()
                    .applicationComponent(ApplicationComponent.Injector.getComponent())
                    .postDetailsActivityModule(new PostDetailsActivityModule(activity))
                    .build();
        }
    }
}
