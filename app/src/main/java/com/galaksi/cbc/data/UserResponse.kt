package com.galaksi.cbc.data

import com.google.gson.annotations.SerializedName

data class UserResponse(@SerializedName("thumbnail_src")
                        var thubmnail: String?,
                        var id: Long = 0,
                        var date: Long = 0,
                        var caption: String?,
                        var account: String?)