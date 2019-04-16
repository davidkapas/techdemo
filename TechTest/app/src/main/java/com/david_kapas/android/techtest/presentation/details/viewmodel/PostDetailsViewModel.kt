package com.david_kapas.android.techtest.presentation.details.viewmodel

import android.databinding.Bindable
import com.aidanvii.toolbox.databinding.ObservableArchViewModel
import com.android.databinding.library.baseAdapters.BR
import com.annimon.stream.Stream
import com.david_kapas.android.techtest.logic.api.model.Comment
import com.david_kapas.android.techtest.logic.api.model.PostDetails
import com.david_kapas.android.techtest.logic.liveevent.SingleLiveEvent
import com.david_kapas.android.techtest.presentation.common.util.ImageUtil
import com.david_kapas.android.techtest.presentation.details.model.PostDetailsModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * ViewModel class for PostDetails.
 * Created by David Kapas on 3/26/2019.
 */
class PostDetailsViewModel(val postDetailsModel: PostDetailsModel) : ObservableArchViewModel() {

    val showErrorMessage = SingleLiveEvent<Any>()

    @get: Bindable
    var postDetails: PostDetails? = null
        set(value) {
            field = value;
            avatarUrl = ImageUtil.createAvatarImageUrl(postDetails?.userEmail!!)
            notifyPropertyChanged(BR.postDetails)
            notifyPropertyChanged(BR.avatarUrl)
        }
    private var postId: Int = 0
    @get: Bindable
    var numberOfComments: Int = 0
        set(value) {
            field = value;
            notifyPropertyChanged(BR.numberOfComments)
        }
    @get: Bindable
    var avatarUrl: String? = null

    fun showPostDetails(postId: Int) {
        this.postId = postId
        postDetailsModel.showPostOverView(postId)?.subscribeOn(Schedulers.io())?.observeOn(AndroidSchedulers.mainThread())?.subscribe({ this.onPostAndUserData(it) }, { this.onPostAndUserError(it) });
        postDetailsModel.getComments()?.subscribe({ this.onCommentResult(it) }, { this.onPostAndUserError(it) });
    }

    private fun onPostAndUserData(postDetails: PostDetails) {
        this.postDetails = postDetails;
    }

    private fun onPostAndUserError(throwable: Throwable) {
        showErrorMessage.call();
    }

    private fun onCommentResult(comments: List<Comment>) {
        val list = comments
        numberOfComments = Stream.ofNullable(list).filter { comment -> comment.postId == postId }.toList().size
    }

}