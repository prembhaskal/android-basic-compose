package com.prem.scramble.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "game_progress")
data class GameProgressEntity(
    @PrimaryKey val levelId: Int,
    val completed: Boolean,
    val solvedWords: String  // Stored as comma-separated values
)

@Dao
interface GameProgressDao {
    @Query("SELECT * FROM game_progress")
    suspend fun getAllProgress(): List<GameProgressEntity>

    @Query("SELECT * FROM game_progress WHERE levelId = :levelId")
    suspend fun getProgressForLevel(levelId: Int): GameProgressEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveProgress(progress: GameProgressEntity)
}

@Database(entities = [GameProgressEntity::class], version = 1)
abstract class GameDatabase : RoomDatabase() {
    abstract fun gameProgressDao(): GameProgressDao
}
