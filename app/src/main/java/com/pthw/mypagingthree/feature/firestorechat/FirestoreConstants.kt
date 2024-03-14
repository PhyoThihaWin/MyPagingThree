package com.pthw.mypagingthree.feature.firestorechat

/** Fire-store document */
const val PARENT_COLLECTION = "/teleconsultation_chats"
const val SUB_COLLECTION = "messages"
const val CHAT_PAGE_SIZE = 20

/** File types */
const val CHAT_IMAGE_TYPE = "image"
const val CHAT_FILE_TYPE = "application"

/** Fire-store key name */
const val TIME_KEY = "time"
const val USER_ID_KEY = "user_id"
const val APPOINTMENT_ID_KEY = "appointment_id"
const val READ_BY_KEY = "read_by"

/** Firebase */
const val PARENT_NODE = "users"
const val CONNECTION_NODE = ".info/connected"

const val STATE_ONLINE = "online"
const val STATE_OFFLINE = "offline"


/** Google Docs url */
const val GOOGLE_DOC = "https://docs.google.com/gview?embedded=true&url="