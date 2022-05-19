package com.xelar.legayd.mathchildrengame.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.xelar.legayd.mathchildrengame.data.entities.GameCustomSettings
import io.reactivex.rxjava3.core.Completable

@Dao
interface GameSettingsDao {

    @Query("SELECT * FROM game_custom_settings ORDER BY id DESC")
    fun getSettings(): LiveData<List<GameCustomSettings>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSettings(gameCustomSettings: GameCustomSettings): Completable

    @Delete
    fun deleteSettings(gameCustomSettings: GameCustomSettings): Completable

}