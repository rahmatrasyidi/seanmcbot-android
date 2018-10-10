package com.galaksi.cbc.screen

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.galaksi.cbc.R
import com.galaksi.cbc.base.BasePresenter
import com.galaksi.cbc.base.BaseScheduler
import com.galaksi.cbc.base.BaseView
import com.galaksi.cbc.data.Repository
import com.galaksi.cbc.tools.Injection
import android.view.Gravity
import android.graphics.Point
import android.os.Handler
import android.widget.Toast
import com.galaksi.cbc.data.UserResponse
import com.galaksi.cbc.data.source.remote.UsersService
import com.galaksi.cbc.item.CbcCard
import com.galaksi.cbc.ktx.gone
import com.galaksi.cbc.ktx.toDp
import com.galaksi.cbc.ktx.visible
import com.galaksi.cbc.tools.Utils
import com.galaksi.cbc.util.AppUtils
import com.mindorks.placeholderview.SwipeDecor
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by rahmatrasyidi on 12/08/18.
 */

class CbcScreen {

    class Contract {
        interface View : BaseView<Presenter> {
            fun showUser(user: UserResponse)
            fun showLoading()
            fun hideLoading()
            fun showToast(message: String)
        }

        interface Presenter : BasePresenter<View> {
            fun getUser()
            fun voteUser(rating: Long)
        }
    }

    class Presenter constructor(private val repository: Repository,
                                private val scheduler: BaseScheduler) : Contract.Presenter {
        private var view: Contract.View? = null
        private var user: UserResponse? = null

        override fun bind(view: Contract.View) {
            this.view = view
        }

        override fun unbind() {
            view = null
        }

        override fun getUser() {
            view?.showLoading()
            repository.getUser(UsersService.UserRequest(123, "PAWAS"))
                    .observeOn(scheduler.ui())
                    .subscribeOn(scheduler.io())
                    .subscribe {
                        user = it
                        view?.showUser(it)
                        view?.hideLoading()
                    }
        }

        override fun voteUser(rating: Long) {
            user?.let {
                view?.showLoading()
                repository.voteUser(UsersService.VoteRequest(it.id, 123, rating))
                        .observeOn(scheduler.ui())
                        .subscribeOn(scheduler.io())
                        .subscribe {
                            view?.showToast("Thanks for your vote :)")
                            view?.hideLoading()
                            getUser()
                        }
            } ?: view?.showToast("User not found!")
        }
    }

    class Activity : AppCompatActivity(), Contract.View, CbcCard.Callback {

        private lateinit var presenter: Contract.Presenter
        private val cardViewHolderSize get() = Point(windowSize.x, windowSize.y - bottomMargin)
        private val animationDuration = 300
        private var isToUndo = false
        private val bottomMargin = 120.toDp()
        private val windowSize get() = Utils.getDisplaySize(windowManager)

        override fun initPresenter(): Contract.Presenter = Presenter(Injection.provideRepository(), Injection.provideSchedulerProvider())

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)
            presenter = initPresenter()
            presenter.bind(this)
            presenter.getUser()

            swipeView.builder
                    .setDisplayViewCount(3)
                    .setIsUndoEnabled(true)
                    .setSwipeVerticalThreshold(50.toDp())
                    .setSwipeHorizontalThreshold(50.toDp())
                    .setHeightSwipeDistFactor(10f)
                    .setWidthSwipeDistFactor(5f)
                    .setSwipeDecor(SwipeDecor()
                            .setViewWidth(windowSize.x)
                            .setViewHeight(windowSize.y - bottomMargin)
                            .setViewGravity(Gravity.TOP)
                            .setPaddingTop(20)
                            .setSwipeAnimTime(animationDuration)
                            .setRelativeScale(0.01f)
                            .setSwipeInMsgLayoutId(R.layout.cbc_swipe_in_msg_view)
                            .setSwipeOutMsgLayoutId(R.layout.cbc_swipe_out_msg_view))

            tvOne.setOnClickListener {
                presenter.voteUser(1)
            }

            tvTwo.setOnClickListener {
                presenter.voteUser(2)
            }

            tvThree.setOnClickListener {
                presenter.voteUser(3)
            }

            tvFour.setOnClickListener {
                presenter.voteUser(4)
            }

            tvFive.setOnClickListener {
                presenter.voteUser(5)
            }

            swipeView.addItemRemoveListener {
                if (isToUndo) {
                    isToUndo = false
                    swipeView.undoLastSwipe()
                }
            }
        }

        override fun showLoading() {
            pbLoading.visible()
            llFooter.visible()
            swipeView.gone()
        }

        override fun hideLoading() {
            pbLoading.gone()
            swipeView.visible()
            llFooter.visible()
        }

        override fun showUser(user: UserResponse) {
            swipeView.addView(CbcCard(this, user, cardViewHolderSize, this))
        }

        override fun onSwipeUp() {
            Toast.makeText(this, "SUPER LIKE!", Toast.LENGTH_SHORT).show()
            isToUndo = true
        }

        override fun onSwipeRight() {
            Handler().postDelayed({ presenter.getUser() }, 500L)
        }

        override fun onSwipeLeft() {
            Handler().postDelayed({ presenter.getUser() }, 500L)
        }

        override fun showToast(message: String) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }
}