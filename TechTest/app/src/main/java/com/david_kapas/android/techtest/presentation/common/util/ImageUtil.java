package com.david_kapas.android.techtest.presentation.common.util;

/**
 * Util class for image handling.
 * Created by David Kapas on 2018.03.18.
 */

public class ImageUtil {

    private static final String AVATAR_BASE_URL = "https://api.adorable.io/avatars/150/";
    private static final String IMAGE_FORMAT = ".png";

    private ImageUtil() {
    }

    public static String createAvatarImageUrl(String email) {
        return AVATAR_BASE_URL + email + IMAGE_FORMAT;
    }
}
