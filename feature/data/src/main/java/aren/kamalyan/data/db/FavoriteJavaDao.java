package aren.kamalyan.data.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface FavoriteJavaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOne(FavoriteDBEntity repo);

    @Query("DELETE FROM favorites WHERE id = :id")
    void deleteOneById(Long id);
}
