package com.pthw.mypagingthree.feature.firestorechat.model

import com.google.firebase.firestore.DocumentId

data class ChatDocumentRef(
    @DocumentId val id: String? = null,
    val appointment_id: String? = null
)