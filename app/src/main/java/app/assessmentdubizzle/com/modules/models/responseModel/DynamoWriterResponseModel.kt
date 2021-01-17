package app.assessmentdubizzle.com.modules.models.responseModel

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DynamoWriterResponseModel(
	val pagination: Pagination? = null,
	val results: List<ResultsItem?>? = null
) : Parcelable

@Parcelize
data class Pagination(
	val key: Int? = null
) : Parcelable

@Parcelize
data class ResultsItem(
	val uid: String? = null,
	val price: String? = null,
	val name: String? = null,
	val created_at: String? = null,
	val image_ids: List<String?>? = null,
	val image_urls: List<String?>? = null,
	val image_urls_thumbnails: List<String?>? = null,

	val imageUrl: String? = null

) : Parcelable
