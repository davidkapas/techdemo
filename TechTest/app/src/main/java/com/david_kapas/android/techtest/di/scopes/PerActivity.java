package com.david_kapas.android.techtest.di.scopes;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Annotation class to grant only one working-instance in the application.
 * Created by David Kapas on 2018.03.17.
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface PerActivity {
}
