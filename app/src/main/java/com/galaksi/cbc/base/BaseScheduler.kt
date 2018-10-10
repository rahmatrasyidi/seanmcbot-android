package com.galaksi.cbc.base

import io.reactivex.Scheduler

/**
 * Created by rahmatrasyidi on 12/08/18.
 */

interface BaseScheduler {

    fun computation(): Scheduler

    fun io(): Scheduler

    fun ui(): Scheduler
}
