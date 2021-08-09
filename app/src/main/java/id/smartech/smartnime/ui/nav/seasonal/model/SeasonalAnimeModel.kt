package id.smartech.smartnime.ui.nav.seasonal.model

import com.google.gson.annotations.SerializedName
import id.smartech.smartnime.ui.nav.schedule.model.GenresModel

data class SeasonalAnimeModel (
        @SerializedName("mal_id")val malId: Int,
        @SerializedName("title")val title: String,
        @SerializedName("image_url")val imageUrl: String,
        @SerializedName("members")val members: Int,
        val type: String,
        val genres: ArrayList<GenresModel>
)