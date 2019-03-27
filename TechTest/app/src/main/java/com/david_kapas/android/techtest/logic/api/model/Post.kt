package com.david_kapas.android.techtest.logic.api.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Pojo class Post object.
 * Created by David Kapas on 3/8/2019.
 */
@Entity
data class Post(@PrimaryKey var id: Int?,
                @ColumnInfo(name = "userId") var userId: Int,
                @ColumnInfo(name = "title") var title: String?,
                @ColumnInfo(name = "body") var body: String?) {
    constructor() : this(null, 0, "", "");
}