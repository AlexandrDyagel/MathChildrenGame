package com.xelar.legayd.mathchildrengame.domain.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_info")
data class UserInfo(
    @PrimaryKey
    val uid: Int,
    val rating: Int,
    val countOfRightAnswers: Int,
    @Embedded(prefix = "status")
    val statusSettings: StatusSettings
)
