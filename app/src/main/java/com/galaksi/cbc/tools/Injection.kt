package com.galaksi.cbc.tools

import com.galaksi.cbc.base.BaseScheduler
import com.galaksi.cbc.data.Repository

/**
 * Created by rahmatrasyidi on 12/08/18.
 */

// todo change with dagger
object Injection {

    fun provideRepository(): Repository {
        return Repository.getInstance()
    }

    fun provideSchedulerProvider(): BaseScheduler {
        return BaseSchedulerProvider.getInstance()
    }

    fun destroyRepositoryInstance() {
        Repository.destroyInstance()
        BaseSchedulerProvider.destroyInstance()
    }
}
