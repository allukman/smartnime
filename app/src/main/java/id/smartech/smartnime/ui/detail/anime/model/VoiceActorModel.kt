package id.smartech.smartnime.ui.detail.anime.model

import com.google.gson.annotations.SerializedName

data class VoiceActorModel (
        @SerializedName("mal_id")val malId: Int,
        @SerializedName("name")val name: String?,
        @SerializedName("url")val url: String?,
        @SerializedName("image_url")val imageUrl: String?
)