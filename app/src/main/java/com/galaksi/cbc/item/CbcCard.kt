package com.galaksi.cbc.item

import android.content.Context
import android.graphics.Point
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide
import android.widget.TextView
import com.bumptech.glide.request.RequestOptions.bitmapTransform
import com.galaksi.cbc.R
import com.galaksi.cbc.data.User
import com.galaksi.cbc.data.UserResponse
import com.galaksi.cbc.ktx.toDp
import com.mindorks.placeholderview.SwipeDirection
import com.mindorks.placeholderview.annotations.*
import com.mindorks.placeholderview.annotations.swipe.*
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import java.lang.Math.sqrt
import java.util.logging.Handler

/**
 * Created by rahmatrasyidi on 12/08/18.
 */

@Layout(R.layout.cbc_card_view)
class CbcCard(private val context: Context,
              private val user: UserResponse,
              private val cardViewHolderSize: Point,
              private val callback: Callback) {

    @View(R.id.ivProfile)
    lateinit var ivProfile: ImageView

    @View(R.id.tvName)
    lateinit var tvName: TextView

    @View(R.id.tvLocation)
    lateinit var tvLocation: TextView

    @SwipeView
    lateinit var swipeView: android.view.View

    @JvmField
    @Position
    var position: Int = 0

    @Resolve
    fun onResolved() {
        Glide.with(context).load(user.thubmnail)
                .apply(bitmapTransform(RoundedCornersTransformation(7.toDp(), 0,
                        RoundedCornersTransformation.CornerType.TOP)))
                .into(ivProfile)
        tvName.text = user.caption
        tvLocation.text = user.account
        swipeView.alpha = 1f
    }

    @Click(R.id.ivProfile)
    fun onClick() {
        Log.d("EVENT", "profileImageView click")
    }

    @SwipeOutDirectional
    fun onSwipeOutDirectional(direction: SwipeDirection) {
        Log.d("DEBUG", "SwipeOutDirectional " + direction.name)
        when {
            direction.direction == SwipeDirection.TOP.direction -> callback.onSwipeUp()
        }
    }

    @SwipeCancelState
    fun onSwipeCancelState() {
        Log.d("DEBUG", "onSwipeCancelState")
        swipeView.alpha = 1f
    }

    @SwipeInDirectional
    fun onSwipeInDirectional(direction: SwipeDirection) {
        Log.d("DEBUG", "SwipeInDirectional " + direction.name)
    }

    @SwipingDirection
    fun onSwipingDirection(direction: SwipeDirection) {
        Log.d("DEBUG", "SwipingDirection " + direction.name)
        if (direction.direction == SwipeDirection.RIGHT.direction || direction.direction == SwipeDirection.RIGHT_BOTTOM.direction ||
                direction.direction == SwipeDirection.RIGHT_TOP.direction) {
            callback.onSwipeRight()
        } else if (direction.direction == SwipeDirection.LEFT.direction || direction.direction == SwipeDirection.LEFT_BOTTOM.direction ||
                direction.direction == SwipeDirection.LEFT_TOP.direction) {
            callback.onSwipeLeft()
        }
    }

    @SwipeTouch
    fun onSwipeTouch(xStart: Float, yStart: Float, xCurrent: Float, yCurrent: Float) {

        val cardHolderDiagonalLength =
                sqrt(Math.pow(cardViewHolderSize.x.toDouble(), 2.0)
                        + (Math.pow(cardViewHolderSize.y.toDouble(), 2.0)))
        val distance = sqrt(Math.pow(xCurrent.toDouble() - xStart.toDouble(), 2.0)
                + (Math.pow(yCurrent.toDouble() - yStart, 2.0)))

        val alpha = 1 - distance / cardHolderDiagonalLength

        Log.d("DEBUG", "onSwipeTouch "
                + " xStart : " + xStart
                + " yStart : " + yStart
                + " xCurrent : " + xCurrent
                + " yCurrent : " + yCurrent
                + " distance : " + distance
                + " TotalLength : " + cardHolderDiagonalLength
                + " alpha : " + alpha
        )

        swipeView.alpha = alpha.toFloat()
    }

    interface Callback {
        fun onSwipeUp()
        fun onSwipeRight()
        fun onSwipeLeft()
    }
}