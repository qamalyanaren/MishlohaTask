package aren.kamalyan.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


@Database(
    version = 1,
    entities = [FavoriteDBEntity::class],
    exportSchema = true //NOTE: need for migration
)
@TypeConverters(DateConverter::class)
abstract class MishlohaDatabase : RoomDatabase() {

    abstract fun favoriteDao(): FavoriteDao

    abstract fun favoriteJavaDao(): FavoriteJavaDao

}