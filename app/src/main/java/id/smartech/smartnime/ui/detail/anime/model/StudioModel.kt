package id.smartech.smartnime.ui.detail.anime.model

import com.google.gson.annotations.SerializedName

data class StudioModel (
        @SerializedName("mal_id")val malId: Int,
        val type: String,
        val name: String,
        val url: String
)