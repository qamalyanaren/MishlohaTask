package aren.kamalyan.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {

    @Query("SELECT * FROM favorites")
    suspend fun findAll(): List<FavoriteDBEntity>

    @Query("SELECT * FROM favorites ORDER BY createdAt DESC")
    fun findAllAsFlow(): Flow<List<FavoriteDBEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(repo: FavoriteDBEntity)

    @Query("DELETE FROM favorites WHERE id = :id")
    suspend fun deleteById(id: Long)
}