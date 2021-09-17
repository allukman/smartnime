package id.smartech.smartnime.model

import com.google.gson.annotations.SerializedName

data class RecommendationsModel (
        @SerializedName("mal_id")val malId: Int,
        @SerializedName("url")val url: String,
        @SerializedName("image_url")val imageUrl: String,
        @SerializedName("recommendation_url")val recommendationUrl: String,
        @SerializedName("title")val title: String
)