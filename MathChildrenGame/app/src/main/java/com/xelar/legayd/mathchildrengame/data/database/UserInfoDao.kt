package com.xelar.legayd.mathchildrengame.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.xelar.legayd.mathchildrengame.domain.models.UserInfo
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
interface UserInfoDao {
    @Query("SELECT * FROM user_info")
    fun getUserInfo(): LiveData<UserInfo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUserInfo(userInfo: UserInfo): Completable

    @Query("SELECT * FROM user_info")
    fun getUserInfoFromDb(): Single<UserInfo>

}