package com.pthw.mypagingthree.feature.firestore_chat.di

import com.google.firebase.firestore.FirebaseFirestore
import com.pthw.mypagingthree.feature.firestore_chat.ChattingRepository
import com.pthw.mypagingthree.feature.firestore_chat.ChattingRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirestoreModule {

    @Provides
    @Singleton
    fun provideFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }
}

@Module
@InstallIn(ViewModelComponent::class)
abstract class FirestoreRepositoryModule {
    @Binds
    abstract fun bindChatRepository(chattingRepositoryImpl: ChattingRepositoryImpl): ChattingRepository
}
