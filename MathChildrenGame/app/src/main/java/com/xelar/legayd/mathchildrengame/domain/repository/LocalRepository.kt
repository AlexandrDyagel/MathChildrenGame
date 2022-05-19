package com.xelar.legayd.mathchildrengame.domain.repository

import androidx.lifecycle.LiveData
import com.xelar.legayd.mathchildrengame.data.entities.GameCustomSettings
import com.xelar.legayd.mathchildrengame.domain.models.UserInfo

interface LocalRepository {

    fun getUserInfo(): LiveData<UserInfo>

    fun insertUserInfo(userInfo: UserInfo)

    fun insertCustomSettings(gameCustomSettings: GameCustomSettings)

    fun deleteCustomSettings(gameCustomSettings: GameCustomSettings)

    fun getCustomSettings(): LiveData<List<GameCustomSettings>>
}
