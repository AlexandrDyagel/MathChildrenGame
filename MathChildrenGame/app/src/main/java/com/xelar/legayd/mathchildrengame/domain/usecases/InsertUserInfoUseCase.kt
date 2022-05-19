package com.xelar.legayd.mathchildrengame.domain.usecases

import com.xelar.legayd.mathchildrengame.domain.models.UserInfo
import com.xelar.legayd.mathchildrengame.domain.repository.LocalRepository

class InsertUserInfoUseCase(private val repository: LocalRepository) {

    operator fun invoke(userInfo: UserInfo){
        repository.insertUserInfo(userInfo)
    }
}