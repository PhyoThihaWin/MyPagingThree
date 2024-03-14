package com.pthw.mypagingthree.feature.firestorechat.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestoreSettings
import com.google.firebase.ktx.Firebase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import com.pthw.mypagingthree.feature.firestorechat.ChatNotifySetting
import com.pthw.mypagingthree.feature.firestorechat.repository.FirestoreRepository
import com.pthw.mypagingthree.feature.firestorechat.repository.FirestoreRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirestoreModule {

    @Provides
    @Singleton
    fun provideFirestore(): FirebaseFirestore {
        val settings = firestoreSettings {
            isPersistenceEnabled = true
        }
        return FirebaseFirestore.getInstance().apply { firestoreSettings = settings }
    }

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return Firebase.auth
    }

    @Provides
    @Singleton
    fun provideChatNotifySetting(): ChatNotifySetting {
        return ChatNotifySetting(enabledNotification = true)
    }
}

@Module
@InstallIn(ViewModelComponent::class)
abstract class FirestoreRepositoryModule {
    @Binds
    abstract fun bindChatRepository(chattingRepositoryImpl: FirestoreRepositoryImpl): FirestoreRepository
}
