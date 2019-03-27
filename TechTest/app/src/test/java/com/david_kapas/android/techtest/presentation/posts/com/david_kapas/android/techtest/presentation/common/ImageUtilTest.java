package com.david_kapas.android.techtest.presentation.posts.com.david_kapas.android.techtest.presentation.common;

import com.david_kapas.android.techtest.presentation.common.util.ImageUtil;

import junit.framework.Assert;

import org.junit.Test;

/**
 * Unit test for {@link ImageUtil}.
 * Created by David_Kapas on 3/19/2018.
 */

public class ImageUtilTest {

    private static final String AVATAR_BASE_URL = "https://api.adorable.io/avatars/150/";
    private static final String IMAGE_FORMAT = ".png";
    private static final String TEST_EMAIL = "user@test.com";

    @Test
    public void tesEmail() {
        String avatarUrl = ImageUtil.INSTANCE.createAvatarImageUrl(TEST_EMAIL);
        Assert.assertEquals(AVATAR_BASE_URL + TEST_EMAIL + IMAGE_FORMAT, avatarUrl);
    }
}
