package com.gsmooth.lazylistexample.presentation.userProfile

import androidx.media3.exoplayer.ExoPlayer

data class MediaModel(
    val id: String,
    val postBody: String,
    val mediaKey: String,
    val mediaContentType: MediaContentType,
    val postSize: MediaSize?,
    val mediaPlayer: ExoPlayer?,
    val isPostVisible: Boolean
) {
    companion object {
        fun createMediaList(): List<MediaModel> = listOf(
            MediaModel(
                id = "1",
                postBody = "Elephant Dreams",
                mediaKey = "https://storage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4",
                mediaContentType = MediaContentType.VIDEO,
                postSize = null,
                mediaPlayer = null,
                isPostVisible = false,
            ),
            MediaModel(
                id = "2",
                postBody = "Big Buck Bunny",
                mediaKey = "https://storage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",
                mediaContentType = MediaContentType.VIDEO,
                postSize = null,
                mediaPlayer = null,
                isPostVisible = false,
            ),
            MediaModel(
                id = "3",
                postBody = "Charlie",
                mediaKey = "https://storage.googleapis.com/gtv-videos-bucket/sample/Sintel.mp4",
                mediaContentType = MediaContentType.VIDEO,
                postSize = null,
                mediaPlayer = null,
                isPostVisible = false,
            ),
            MediaModel(
                id = "4",
                postBody = "Eve",
                mediaKey = "https://storage.googleapis.com/gtv-videos-bucket/sample/ForBiggerEscapes.mp4",
                mediaContentType = MediaContentType.VIDEO,
                postSize = null,
                mediaPlayer = null,
                isPostVisible = false,
            ),
            MediaModel(
                id = "5",
                postBody = "Eve",
                mediaKey = "https://storage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",
                mediaContentType = MediaContentType.VIDEO,
                postSize = null,
                mediaPlayer = null,
                isPostVisible = false,
            ),
            MediaModel(
                id = "6",
                postBody = "Eve",
                mediaKey = "https://storage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4",
                mediaContentType = MediaContentType.VIDEO,
                postSize = null,
                mediaPlayer = null,
                isPostVisible = false,
            ),
            MediaModel(
                id = "7",
                postBody = "Rally",
                mediaKey = "https://storage.googleapis.com/gtv-videos-bucket/sample/WeAreGoingOnBullrun.mp4",
                mediaContentType = MediaContentType.VIDEO,
                postSize = null,
                mediaPlayer = null,
                isPostVisible = false,
            ),
            MediaModel(
                id = "8",
                postBody = "Ocean",
                mediaKey = "https://storage.googleapis.com/gtv-videos-bucket/sample/ForBiggerJoyrides.mp4",
                mediaContentType = MediaContentType.VIDEO,
                postSize = null,
                mediaPlayer = null,
                isPostVisible = false,
            ),
            MediaModel(
                id = "9",
                postBody = "Sintel",
                mediaKey = "https://storage.googleapis.com/gtv-videos-bucket/sample/Sintel.mp4",
                mediaContentType = MediaContentType.VIDEO,
                postSize = null,
                mediaPlayer = null,
                isPostVisible = false,
            ),
            MediaModel(
                id = "10",
                postBody = "Subaru",
                mediaKey = "https://storage.googleapis.com/gtv-videos-bucket/sample/TearsOfSteel.mp4",
                mediaContentType = MediaContentType.VIDEO,
                postSize = null,
                mediaPlayer = null,
                isPostVisible = false,
            ),
            MediaModel(
                id = "11",
                postBody = "Cars for a grand",
                mediaKey = "https://storage.googleapis.com/gtv-videos-bucket/sample/WhatCarCanYouGetForAGrand.mp4",
                mediaContentType = MediaContentType.VIDEO,
                postSize = null,
                mediaPlayer = null,
                isPostVisible = false,
            ),
            MediaModel(
                id = "12",
                postBody = "VW GTI",
                mediaKey = "https://storage.googleapis.com/gtv-videos-bucket/sample/VolkswagenGTIReview.mp4",
                mediaContentType = MediaContentType.VIDEO,
                postSize = null,
                mediaPlayer = null,
                isPostVisible = false,
            ),
            MediaModel(
                id = "13",
                postBody = "Bull run live rally",
                mediaKey = "https://storage.googleapis.com/gtv-videos-bucket/sample/WeAreGoingOnBullrun.mp4",
                mediaContentType = MediaContentType.VIDEO,
                postSize = null,
                mediaPlayer = null,
                isPostVisible = false,
            ),
            MediaModel(
                id = "14",
                postBody = "Dream",
                mediaKey = "https://storage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4",
                mediaContentType = MediaContentType.VIDEO,
                postSize = null,
                mediaPlayer = null,
                isPostVisible = false,
            ),
        )
    }
}

data class MediaSize(
    val width: Int,
    val height: Int
)

enum class MediaContentType {
    IMAGE,
    VIDEO,
    UNKNOWN
}

