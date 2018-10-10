package com.galaksi.cbc.data.source.remote

import com.galaksi.cbc.data.UserResponse
import com.galaksi.cbc.data.VoteResponse
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Created by rahmatrasyidi on 12/08/18.
 */

interface UsersService {

    @POST("/api/random")
    fun getUser(@Body body: UserRequest): Observable<UserResponse>

    @POST("/api/vote")
    fun vote(@Body body: VoteRequest): Observable<VoteResponse>

    data class UserRequest(var id: Long = 0,
                           var name: String = "pawas",
                           var platform: String = "android")

    data class VoteRequest(var photos_id: Long = 0,
                           var customer_id: Long = 0,
                           var rating: Long = 1)
}