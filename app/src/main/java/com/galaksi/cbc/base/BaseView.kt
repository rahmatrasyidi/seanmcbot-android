package com.galaksi.cbc.base

/**
 * Created by rahmatrasyidi on 12/08/18.
 */

interface BaseView<out P> {

    fun initPresenter(): P
}

