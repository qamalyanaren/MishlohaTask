package aren.kamalyan.data.di

import android.app.Application
import androidx.room.Room
import aren.kamalyan.data.db.FavoriteDao
import aren.kamalyan.data.db.FavoriteJavaDao
import aren.kamalyan.data.db.MishlohaDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun providesDatabase(context: Application): MishlohaDatabase {
        return Room.databaseBuilder(context, MishlohaDatabase::class.java, "mishloha.db").build()
    }

    @Provides
    @Singleton
    fun providesFavoriteDao(database: MishlohaDatabase): FavoriteDao {
        return database.favoriteDao()
    }

    @Provides
    @Singleton
    fun providesFavoriteJavaDao(database: MishlohaDatabase): FavoriteJavaDao {
        return database.favoriteJavaDao()
    }

}