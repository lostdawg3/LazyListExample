package com.gsmooth.lazylistexample.presentation.userProfile

sealed class UserProfileEvents {
    data class UpdateMostVisibleItem(val postId: String): UserProfileEvents()
}