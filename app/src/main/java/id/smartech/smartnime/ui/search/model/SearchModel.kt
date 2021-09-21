package id.smartech.smartnime.ui.search.model

import com.google.gson.annotations.SerializedName

data class SearchModel (
        @SerializedName("mal_id")val malId: Int,
        @SerializedName("image_url")val imageUrl: String,
        @SerializedName("title")val title: String?,
        @SerializedName("name")val name: String?,
        @SerializedName("url")val url: String
)