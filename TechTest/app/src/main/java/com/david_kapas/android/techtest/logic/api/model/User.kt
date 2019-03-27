package com.david_kapas.android.techtest.logic.api.model

import android.arch.persistence.room.Entity

/**
 * Pojo class for User object.
 * Created by David Kapas on 2018.03.18.
 */
@Entity(primaryKeys = ["id"])
class User {
    var id: Int = 0
    var name: String? = null
    var username: String? = null
    var email: String? = null
}
