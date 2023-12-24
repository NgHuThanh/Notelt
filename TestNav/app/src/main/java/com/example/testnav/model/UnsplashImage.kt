package com.example.testnav.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.testnav.util.Constants.UNSPLASH_IMAGE_TABLE
import kotlinx.serialization.Serializable
import java.net.URLStreamHandler
@Entity(tableName=UNSPLASH_IMAGE_TABLE)
@Serializable
data class UnsplashImage(
    @PrimaryKey(autoGenerate = false)
    val id:String,
    @Embedded
    val urls:Urls,
    @Embedded
    val user:User
)