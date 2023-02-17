package com.pthw.mypagingthree.feature.firestorechat.activity


data class ChatExtra(
    val doctorName: String,
    val chatId: String,
    val activate: Boolean,//show keyboard if it's true or hide keyboard and show show status message if it's false
    val appointmentId: Long? = null//appointmentId will be inserted same level as message in firebase if appointment is not null or already there.
) : java.io.Serializable