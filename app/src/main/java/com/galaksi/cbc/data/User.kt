package com.galaksi.cbc.data

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import io.reactivex.annotations.NonNull
import java.io.Serializable

/**
 * Created by rahmatrasyidi on 12/08/18.
 */

@Entity(tableName = "user")
data class User(@PrimaryKey @NonNull var userId: Long = 0,
                var imageUrl: String? = null,
                var age: Int = 0,
                var name: String? = null,
                var location: String? = null,
                var source: String? = null) : Serializable