package com.david_kapas.android.techtest.presentation.details.viewmodel

import android.databinding.BaseObservable
import android.databinding.Bindable
import com.android.databinding.library.baseAdapters.BR
import com.annimon.stream.Stream
import com.david_kapas.android.techtest.logic.api.model.PostDetails
import com.david_kapas.android.techtest.presentation.common.extension.nonNull
import com.david_kapas.android.techtest.presentation.common.extension.observe
import com.david_kapas.android.techtest.presentation.common.util.ImageUtil
import com.david_kapas.android.techtest.presentation.details.model.CommentListEntity
import com.david_kapas.android.techtest.presentation.details.model.PostDetailsEntity
import com.david_kapas.android.techtest.presentation.details.model.PostDetailsModel
import com.david_kapas.android.techtest.presentation.details.router.PostDetailsRouter

/**
 * ViewModel class for PostDetails.
 * Created by David Kapas on 3/26/2019.
 */
class PostDetailsViewModel(val postDetailsModel: PostDetailsModel, val router: PostDetailsRouter) : BaseObservable() {

    @get: Bindable
    var postDetails: PostDetails? = null
    private var postId: Int = 0
    @get: Bindable
    var numberOfComments: Int = 0
        set(value) {
            field = value;
            notifyPropertyChanged(BR.numberOfComments)
        }
    @get: Bindable
    var avatarUrl: String? = null

    init {
        observeModel()
    }

    fun showPostDetails(postId: Int) {
        this.postId = postId
        postDetailsModel.showPostOverView(postId)
        postDetailsModel.getComments()
    }


    private fun observeModel() {
        postDetailsModel.postDetailsEntityMutableLiveData.nonNull().observe(router, this::onPostOverview)
        postDetailsModel.commentListEntityMutableLiveData.nonNull().observe(router, this::onPostDetailsModel)
    }


    private fun onPostDetailsModel(commentListEntity: CommentListEntity) {
        if (commentListEntity.isError) {
            // the extended error handling not implemented due to short time frame
            router.showGeneralErrorDialog()
        } else {
            val list = commentListEntity.commentList
            numberOfComments = Stream.ofNullable(list).filter { comment -> comment.postId == postId }.toList().size
        }

    }

    private fun onPostOverview(postDetailsEntity: PostDetailsEntity) {
        if (postDetailsEntity.isError) {
            // the extended error handling not implemented due to short time frame
            router.showGeneralErrorDialog()
        } else {
            setPostDetails(postDetailsEntity)
        }
    }

    private fun setPostDetails(postDetailsEntity: PostDetailsEntity) {
        this.postDetails = postDetailsEntity.postDetails
        avatarUrl = ImageUtil.createAvatarImageUrl(postDetails?.userEmail!!)
        notifyPropertyChanged(BR.postDetails)
        notifyPropertyChanged(BR.avatarUrl)
    }
}