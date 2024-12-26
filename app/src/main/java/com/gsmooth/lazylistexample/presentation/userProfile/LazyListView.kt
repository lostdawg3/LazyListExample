package com.gsmooth.lazylistexample.presentation.userProfile

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.BoxWithConstraintsScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.gsmooth.lazylistexample.util.Logger
import kotlinx.coroutines.flow.map
import kotlin.math.absoluteValue
import kotlin.math.max
import kotlin.math.min

@Composable
fun LazyListView(
    state: UserProfileState,
    events: (UserProfileEvents) -> Unit
) {

    val isInDarkTheme = isSystemInDarkTheme()
    val listState = rememberLazyListState()
    val scrollThreshold = 10
    val minimumVisibilityPercentageRequiredToPlayVideo = 30

    /** Track the most visible item index **/
    var mostVisibleMediaItemId by remember { mutableStateOf<MediaModel?>(null) }
    // Track the current selected page in pager
    var lastScrollOffset by remember { mutableIntStateOf(scrollThreshold) }

    if (state.listOfPosts.isNotEmpty()) {

        LaunchedEffect(listState) {
            snapshotFlow { listState.firstVisibleItemScrollOffset }
                .map { currentOffset ->
                    val delta = currentOffset - lastScrollOffset
                    Logger.debug("UserProfileLazy Scroll Offset Delta: $delta")

                    if (delta.absoluteValue >= scrollThreshold) {
                        val visibleItems = listState.layoutInfo.visibleItemsInfo
                            .filter { it.index > 0 }
                        val viewportStart = listState.layoutInfo.viewportStartOffset
                        val viewportEnd = listState.layoutInfo.viewportEndOffset
                        Logger.debug("UserProfileLazy Viewport Start: $viewportStart, End: $viewportEnd")
                        Logger.debug("UserProfileLazy Visible Items: $visibleItems")

                        val visibilityPercentages = visibleItems.map { item ->
                            val itemStart = item.offset
                            val itemEnd = item.offset + item.size

                            val visibleStart = max(viewportStart, itemStart)
                            val visibleEnd = min(viewportEnd, itemEnd)
                            val visibleHeight = max(0, visibleEnd - visibleStart)
                            val totalHeight = item.size
                            var visiblePercentage = (visibleHeight / totalHeight.toFloat()) * 100

                            Logger.debug(
                                "UserProfileLazy Item Index: ${item.index}, " +
                                        "Item Start: $itemStart, Item End: $itemEnd, " +
                                        "Visible Start: $visibleStart, Visible End: $visibleEnd, " +
                                        "Visible Height: $visibleHeight, Total Height: $totalHeight, " +
                                        "Visibility Percentage: $visiblePercentage"
                            )

                            if (visiblePercentage <= minimumVisibilityPercentageRequiredToPlayVideo) {
                                Logger.debug("UserProfileLazy Item Index: ${item.index} visibility too low: $visiblePercentage")
                                visiblePercentage = 0f
                            }

                            item.index to visiblePercentage
                        }

                        Logger.debug("UserProfileLazy Visibility Percentages Before Sorting: $visibilityPercentages")

                        /** Determine the most visible item **/
                        /** Determine the most visible item **/
                        val sortedVisibility = visibilityPercentages
                            .sortedWith(compareByDescending<Pair<Int, Float>> { sorted ->
                                Logger.debug("UserProfileLazy Sorting by visibility percentage for index ${sorted.first}: ${sorted.second}")
                                sorted.second
                            }.thenBy { index ->
                                val itemOffset =
                                    visibleItems.find { visible -> visible.index == index.first }?.offset
                                        ?: 0
                                Logger.debug("UserProfileLazy Tiebreaker: Sorting by offset for index ${index.first}: Offset $itemOffset")
                                itemOffset
                            })

                        Logger.debug("UserProfileLazy Visibility Percentages After Sorting: $sortedVisibility")

                        val item = sortedVisibility
                            .firstOrNull()
                            ?.let { (index, visibility) ->
                                Logger.debug("UserProfileLazy Most Visible Item Selected: Index $index, Visibility $visibility")
                                state.listOfPosts.getOrNull(index - 1)
                            }
                        mostVisibleMediaItemId = item

                        Logger.debug("UserProfileLazy Most Visible Media Item ID: ${mostVisibleMediaItemId?.postBody}")
                    }

                    lastScrollOffset = currentOffset
                    mostVisibleMediaItemId
                }
//        .distinctUntilChanged() // Uncomment if needed
                .collect { postId ->
                    if (postId != null) {
                        Logger.debug("UserProfileLazy Update Most Visible Item: ${postId.postBody}")
                        events(UserProfileEvents.UpdateMostVisibleItem(postId.id))
                    }
                }
        }
    }

    LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxSize()
    ) {
        item(
            key = "ProfileHeader",
            contentType = null
        ) {
            UserProfileHeader()
        }

        items(
            state.listOfPosts,
            key = {it?.id!!}
        ) { post ->
            if (post != null) {
                UserPostView(
                    post = post,
                    state = state,
                    isInDarkMode = isInDarkTheme
                )
            }
        }

        item(
            key = "BottomView",
            contentType = null
        ) {
            UserProfileFooter()
        }
    }
}

@Composable
fun UserProfileHeader() {
    BoxWithConstraints(
        modifier = Modifier
            .height(280.dp)
            .fillMaxWidth()
            .clip(
                shape = RoundedCornerShape(
                    bottomStart = 12.dp, bottomEnd = 12.dp
                )
            )
            .padding(bottom = 2.dp)
    ) {
        val boxWithConstraintsScope = this
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(1.0f))
            Text(
                text = "ProfileHeader",
                style = TextStyle(

                )
            )
            Spacer(modifier = Modifier.weight(1.0f))
        }
    }
}

@Composable
fun UserProfileFooter() {
    Column(
        modifier = Modifier.height(160.dp)
    ) {

    }
}
