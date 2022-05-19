package com.xelar.legayd.mathchildrengame.domain.usecases

import androidx.lifecycle.LiveData
import com.xelar.legayd.mathchildrengame.domain.models.UserInfo
import com.xelar.legayd.mathchildrengame.domain.repository.LocalRepository

class GetUserInfoUserCase(private val repository: LocalRepository) {

    operator fun invoke(): LiveData<UserInfo> {
        return repository.getUserInfo()
    }
}