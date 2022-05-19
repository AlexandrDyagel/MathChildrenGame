package com.xelar.legayd.mathchildrengame.data.database

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.xelar.legayd.mathchildrengame.data.entities.GameCustomSettings
import com.xelar.legayd.mathchildrengame.data.GameRepositoryImpl.getStatusSettings
import com.xelar.legayd.mathchildrengame.domain.models.UserInfo
import java.util.concurrent.Executors

@Database(
    entities = [UserInfo::class, GameCustomSettings::class],
    version = 1,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        private const val DB_NAME = "math_children_games.db"
        private var db: AppDatabase? = null
        private val LOCK = Any()

        /*private val MIGRATION_1_2 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE game_custom_settings ADD COLUMN title TEXT")
            }
        }*/

        fun getInstance(context: Context): AppDatabase {
            synchronized(LOCK) {
                db?.let { return it }
                val instance =
                    Room.databaseBuilder(
                        context,
                        AppDatabase::class.java,
                        DB_NAME
                    )
                        //.addMigrations(MIGRATION_1_2)
                        .build()
                db = instance
                return instance
            }
        }
    }

    abstract fun userInfoDao(): UserInfoDao

    abstract fun gameSettingsDao(): GameSettingsDao
}