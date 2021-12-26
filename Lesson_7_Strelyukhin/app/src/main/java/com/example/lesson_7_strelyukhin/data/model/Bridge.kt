package com.example.lesson_7_strelyukhin.data.model

import android.os.Parcelable
import com.example.lesson_7_strelyukhin.R
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.util.Calendar

@Parcelize
data class Bridge(
    @SerializedName("id") val id: Int?,
    @SerializedName("name") val name: String?,
    @SerializedName("description") val info: String?,
    @SerializedName("divorces") val divorces: List<Divorce>?,
    @SerializedName("photo_close_url") val photoCloseUrl: String?,
    @SerializedName("photo_open_url") val photoOpenUrl: String?,
    val isBell: Boolean = false,
    ) : Parcelable {

    fun getImageId(): Int {
        val calendar = Calendar.getInstance()
        val time = calendar.get(Calendar.HOUR_OF_DAY) * 60 + calendar.get(Calendar.MINUTE)
        divorces?.forEach { divorce ->
            val timeStart: Int = divorce.start?.split(":").let {
                if (it != null) {
                    it[0].toInt() * 60 + it[1].toInt()
                } else 0
            }
            val timeEnd: Int = divorce.end?.split(":").let {
                if (it != null) {
                    it[0].toInt() * 60 + it[1].toInt()
                } else 0
            }
            if (time in timeStart..timeEnd) {
                return R.drawable.ic_bridge_late
            }
            if (time in (timeStart - 60)..timeStart) {
                return R.drawable.ic_bridge_soon
            }
        }
        return R.drawable.ic_bridge_normal
    }
}

