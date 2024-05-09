package aren.kamalyan.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.util.Date

@Entity(tableName = "favorites")
@TypeConverters(DateConverter::class)
class FavoriteDBEntity(
    @PrimaryKey
    val id: Long,
    val name: String,
    val description: String?,
    val starsCount: Int,
    val ownerAvatarUrl: String?,
    val ownerName: String?,

    val language: String?,
    val forksCount: Int,
    val createdAt: Date,
    val htmlUrl: String?,
)