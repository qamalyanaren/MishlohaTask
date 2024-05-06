package aren.kamalyan.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favorites: List<FavoriteDBEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favorite: FavoriteDBEntity)

    @Query("SELECT * FROM favorites")
    fun getAll(): Flow<List<FavoriteDBEntity>>

    @Query("DELETE FROM favorites")
    fun deleteAll()


}