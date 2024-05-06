package aren.kamalyan.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "favorites")
class FavoriteDBEntity(
    @PrimaryKey val id: UUID,
)