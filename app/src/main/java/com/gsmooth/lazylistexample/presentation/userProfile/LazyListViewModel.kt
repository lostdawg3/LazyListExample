package com.gsmooth.lazylistexample.presentation.userProfile

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

data class UserProfileState(
    val listOfPosts: List<MediaModel?> = listOf()
)

sealed class UserProfileEvents {
    data class UpdateMostVisibleItem(val postId: String): UserProfileEvents()
}

@HiltViewModel
class LazyListViewModel
@Inject
constructor(
    @ApplicationContext val context: Context
): ViewModel() {

    private val _state: MutableStateFlow<UserProfileState> = MutableStateFlow(UserProfileState())
    val state = _state.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = _state.value
    )

    private val _mostVisiblePostId = MutableStateFlow<String?>(null)
    val mostVisibleItem: StateFlow<String?> get() = _mostVisiblePostId

    init {
        setupPosts()
        observePostState()
    }

    fun onTriggerEvent(event: UserProfileEvents) {
        when (event) {
            is UserProfileEvents.UpdateMostVisibleItem -> {
                _mostVisiblePostId.value = event.postId
            }
        }
    }

    private fun setupPosts() {
        viewModelScope.launch {
            val mediaList = MediaModel.createMediaList()
            if (mediaList.isNotEmpty()) {
                _state.update { currentState ->
                    currentState.copy(
                        listOfPosts = mediaList.map { post ->
                            post.copy(
                                mediaPlayer = createExoPlayer(post.mediaKey),
                            )
                        }
                    )
                }
            }
        }
    }

    private suspend fun createExoPlayer(url: String): ExoPlayer {
        return withContext(Dispatchers.Main) {
            ExoPlayer.Builder(context).build().apply {
                setMediaItem(MediaItem.fromUri(Uri.parse(url)))
                prepare()
                repeatMode = ExoPlayer.REPEAT_MODE_OFF

                addListener(object: Player.Listener {
                    override fun onPlaybackStateChanged(playbackState: Int) {
                        if (playbackState == Player.STATE_ENDED) {
                            pause()
                            seekTo(0)
                        }
                    }
                })
            }
        }
    }

    private fun observePostState() {
        viewModelScope.launch {
            _mostVisiblePostId.collect { postId ->
                val post = state.value.listOfPosts.find { it?.id == postId }
                    _state.update { currentState ->
                        currentState.copy(
                            listOfPosts = currentState.listOfPosts.map { post ->
                                val isVisible = post?.id == postId
                                post?.copy(isPostVisible = isVisible)
                            }
                        )
                    }
            }
        }
    }
}