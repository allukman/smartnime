package id.smartech.smartnime.ui.detail.anime.episodes.model

import com.google.gson.annotations.SerializedName

data class EpisodesModel (
    @SerializedName("episode_id")val episodesId: Int,
    @SerializedName("title")val title: String?,
    @SerializedName("title_romanji")val titleRomanji: String?,
    @SerializedName("aired")val aired: String?,
    @SerializedName("video_url")val videoUrl: String?
)