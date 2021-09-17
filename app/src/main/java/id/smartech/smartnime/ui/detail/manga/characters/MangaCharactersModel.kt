package id.smartech.smartnime.ui.detail.manga.characters

import com.google.gson.annotations.SerializedName

data class MangaCharactersModel (
    @SerializedName("mal_id")val malId: Int,
    @SerializedName("url")val url: String,
    @SerializedName("image_url")val imageUrl: String,
    @SerializedName("name")val name: String,
    @SerializedName("role")val role: String
)