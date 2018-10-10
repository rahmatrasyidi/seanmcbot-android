package com.galaksi.cbc.tools

import android.support.annotation.VisibleForTesting
import com.galaksi.cbc.base.BaseScheduler
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by mobs-rasyidi on 12/08/18.
 */

class BaseSchedulerProvider : BaseScheduler {

    companion object {

        private var INSTANCE: BaseSchedulerProvider? = null
        private val lock = Any()

        fun getInstance(): BaseSchedulerProvider {
            synchronized(lock) {
                if (INSTANCE == null) {
                    INSTANCE = BaseSchedulerProvider()
                }
            }
            return INSTANCE!!
        }

        @VisibleForTesting
        fun destroyInstance() {
            INSTANCE = null
        }
    }

    override fun computation(): Scheduler = Schedulers.computation()

    override fun io(): Scheduler = Schedulers.io()

    override fun ui(): Scheduler = AndroidSchedulers.mainThread()
}
