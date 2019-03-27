package com.david_kapas.android.techtest.presentation.posts.model

import com.david_kapas.android.techtest.logic.api.model.PostAndUser

/**
 * Wrapper class for PostAndUser list.
 * Created by David Kapas on 2018.03.18.
 */

class PostAndUserEntity {

    var postAndUsers: List<PostAndUser>? = null;
    var isError: Boolean = false

    constructor() {}

    constructor(postAndUsers: List<PostAndUser>) {
        this.postAndUsers = postAndUsers
    }
}
