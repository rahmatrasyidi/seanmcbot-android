package com.galaksi.cbc.data

import com.galaksi.cbc.data.source.remote.RetrofitHelper
import com.galaksi.cbc.data.source.remote.UsersService
import io.reactivex.Observable
import retrofit2.http.Body

/**
 * Created by rahmatrasyidi on 12/08/18.
 */

class Repository constructor(remote: RetrofitHelper) {

    private val remoteDb = remote.provideRetrofit().create(UsersService::class.java)

    fun getUser(@Body userRequest: UsersService.UserRequest): Observable<UserResponse> = remoteDb.getUser(userRequest)

    fun voteUser(@Body voteRequest: UsersService.VoteRequest): Observable<VoteResponse> = remoteDb.vote(voteRequest)

    companion object {
        private var INSTANCE: Repository? = null
        private val lock = Any()

        fun getInstance(): Repository {
            synchronized(lock) {
                if (INSTANCE == null) {
                    INSTANCE = Repository(RetrofitHelper.getInstance())
                }
            }
            return INSTANCE!!
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}