package stud.gilmon.data.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import retrofit2.HttpException
import stud.gilmon.data.model.FeedItem
import stud.gilmon.data.remote.UnsplashApi
import stud.gilmon.data.remote.UnsplashDto
import stud.gilmon.data.remote.toFeedItem
import stud.gilmon.presentation.ui.Screen

class UnsplashPhotosPagingSource @AssistedInject constructor(
    private val unsplashApi: UnsplashApi
) : PagingSource<Int, FeedItem>() {
    override fun getRefreshKey(state: PagingState<Int, FeedItem>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1)?:page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, FeedItem> {
        val page: Int = params.key ?: 1
        val pageSize: Int = params.loadSize
        val response =
            unsplashApi.getPhotos("cfU1iXa1PBM5G1SfHlT7YhUOSDy9cwXF4GSQ1lDCsAM", page, pageSize)
        if (response.isSuccessful) {
            val photos = checkNotNull(response.body()).map { it.toFeedItem() }
            val nextKey = if (photos.size < pageSize) null else page + 1
            val prevKey = if (page == 1) null else page - 1
            return LoadResult.Page(photos, prevKey, nextKey)
        } else {
            return LoadResult.Error(HttpException(response))
        }
    }
    @AssistedFactory
    interface Factory {

        fun create(): UnsplashPhotosPagingSource
    }
}