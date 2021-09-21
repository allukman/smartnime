package id.smartech.smartnime.ui.detail.character.model

import com.google.gson.annotations.SerializedName

data class DetailCharacterResponse (
    @SerializedName("request_hash")val requestHash: String,
    @SerializedName("request_cached")val requestCached: Boolean,
    @SerializedName("request_cache_expiry")val requestCacheExpiry: Int,
    @SerializedName("mal_id")val malId: Int,
    @SerializedName("url")val url: String,
    @SerializedName("name")val name: String,
    @SerializedName("name_kanji")val nameKanji: String?,
    @SerializedName("nicknames")val nicknames: List<String>?,
    @SerializedName("about")val about: String?,
    @SerializedName("image_url")val imageUrl: String?,
    @SerializedName("animeography")val animeography: ArrayList<AnimeographyModel>?,
    @SerializedName("mangaography")val mangaography: ArrayList<AnimeographyModel>?,
    @SerializedName("voice_actors")val voiceActor: ArrayList<VoiceActorModel>?
)