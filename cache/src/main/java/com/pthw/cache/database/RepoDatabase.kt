package com.pthw.cache.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.pthw.cache.database.remotekey.RemoteKeys
import com.pthw.cache.database.remotekey.RemoteKeysDao
import com.pthw.cache.feature.githubrepo.dao.RepoSourceDao
import com.pthw.cache.feature.githubrepo.entity.RepoDbEntity

@Database(
    entities = [RepoDbEntity::class, RemoteKeys::class],
    version = 1,
    exportSchema = false
)
abstract class RepoDatabase : RoomDatabase() {
    abstract fun reposDao(): RepoSourceDao
    abstract fun remoteKeysDao(): RemoteKeysDao

    companion object {

        @Volatile
        private var INSTANCE: RepoDatabase? = null

        fun getInstance(context: Context): RepoDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                RepoDatabase::class.java, "Github.db"
            )
                .build()
    }
}