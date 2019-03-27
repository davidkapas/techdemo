package com.david_kapas.android.techtest.presentation.common.util

/**
 * Util class for image handling.
 * Created by David Kapas on 2018.03.18.
 */

object ImageUtil {

    private val AVATAR_BASE_URL = "https://api.adorable.io/avatars/150/"
    private val IMAGE_FORMAT = ".png"

    fun createAvatarImageUrl(email: String): String {
        return AVATAR_BASE_URL + email + IMAGE_FORMAT
    }
}
