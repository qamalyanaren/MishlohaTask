package aren.kamalyan.data.db

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(
    version = 1,
    entities = [FavoriteDBEntity::class],
    exportSchema = true //NOTE: need for migration
)
abstract class MishlohaDatabase : RoomDatabase() {

    abstract fun favoriteDao(): FavoriteDao

}