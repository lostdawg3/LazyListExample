package com.gsmooth.lazylistexample.presentation.userProfile

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.ui.PlayerView
import coil3.compose.AsyncImage
import coil3.request.CachePolicy
import coil3.request.ImageRequest

@Composable
fun UserPostView(
    post: MediaModel,
    state: UserProfileState,
    isInDarkMode: Boolean
) {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 2.dp)
            .background(
                color = if (isInDarkMode) Color.Black else Color.White,
                shape = RoundedCornerShape(size = 8.dp)
            )
    ) {
        val boxWithConstraintsScope = this
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Row(
                modifier = Modifier
                    .height(56.dp)
                    .padding(horizontal = 8.dp, vertical = 2.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier.padding(end = 12.dp)
                ) {

                }
            }
            when (post.mediaContentType) {
                MediaContentType.IMAGE -> {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(post.mediaKey)
                            .memoryCachePolicy(CachePolicy.ENABLED)
                            .diskCachePolicy(CachePolicy.ENABLED)
                            .build(),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
                MediaContentType.VIDEO -> {
                    post.mediaKey.let {
                        Column(modifier = Modifier.fillMaxSize()) {
                            Text(
                                text = "Playing: ${post.isPostVisible}"
                            )
                            VideoComponentView(post)
                        }
                    }
                }
                MediaContentType.UNKNOWN -> { }
            }
        }
    }
}

@Composable
fun VideoComponentView(
    post: MediaModel
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp)
    ) {
        AndroidView(
            factory = { ctx ->
                PlayerView(ctx).apply {
                    player = post.mediaPlayer
                    useController = false
                }
            },
            update = { playerView ->
                if (post.isPostVisible) {
                    val isPlaying = post.mediaPlayer?.isPlaying == true
                    if (!isPlaying) {
                        val lastPosition = post.mediaPlayer?.currentPosition ?: 0L
                        post.mediaPlayer?.seekTo(lastPosition)
                        post.mediaPlayer?.playWhenReady = true
                        post.mediaPlayer?.play()
                    }
                } else {
                    post.mediaPlayer?.playWhenReady = false
                    post.mediaPlayer?.pause()
                }
            }
        )
    }
}














