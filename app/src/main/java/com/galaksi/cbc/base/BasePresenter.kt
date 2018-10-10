package com.galaksi.cbc.base

/**
 * Created by rahmatrasyidi on 12/08/18.
 */

interface BasePresenter<in V> {
    fun bind(view: V)
    fun unbind()
}
