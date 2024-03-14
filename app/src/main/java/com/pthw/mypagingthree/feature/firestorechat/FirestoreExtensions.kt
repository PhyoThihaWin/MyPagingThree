package com.pthw.mypagingthree.feature.firestorechat

import android.os.Handler
import android.os.Looper
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.DocumentSnapshot
import com.pthw.domain.utils.AppConstants
import com.pthw.mypagingthree.feature.firestorechat.model.ChatMessage
import com.pthw.mypagingthree.feature.firestorechat.model.MessageData
import java.text.SimpleDateFormat
import java.util.*

fun RecyclerView.smoothScrollToPos(position: Int) {
    Handler(Looper.getMainLooper()).postDelayed({
        this.smoothScrollToPosition(position)
    }, 300)
}

fun Long.epochFormatter(format: String): String {
    val date = Date(this * 1000)
    val formatter = SimpleDateFormat(format, Locale.getDefault())
    return formatter.format(date)
}

fun Long.epochChatFormatter(format: String): String {
    val date = Date(this * 1000)
    val formatter = SimpleDateFormat(format, Locale.getDefault())
    val chatDay = formatter.format(date)
    val calendar = Calendar.getInstance()
    val today = formatter.format(calendar.time)
    calendar.set(Calendar.DATE, -1)
    val yesterday = formatter.format(calendar.time)

    return when (chatDay) {
        today -> {
            val dateFormatter =
                SimpleDateFormat(AppConstants.LOCAL_TIME_FORMAT, Locale.getDefault())
            dateFormatter.format(date)
        }
        yesterday -> "Yesterday"
        else -> chatDay
    }
}

fun ChatMessage.leftRight(): MessageData? {
    return when (this) {
        is ChatMessage.ChatLeft -> this.data
        is ChatMessage.ChatRight -> this.data
        else -> null
    }
}

fun List<DocumentSnapshot>.findDocument(id: String): DocumentSnapshot? {
    return this.find { it.id == id }
}

fun DocumentSnapshot.getTime(): Long? {
    return this.getTimestamp("time")?.seconds
}

fun List<DocumentSnapshot>.indexOfDocument(id: String) = this.indexOfFirst { it.id == id }

fun <Item> MutableList<Item>.removeAtSafely(index: Int) {
    if (index < 0) return
    this.removeAt(index)
}