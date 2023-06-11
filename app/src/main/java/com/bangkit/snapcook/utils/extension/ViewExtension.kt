package com.bangkit.snapcook.utils.extension

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import com.bangkit.snapcook.R
import com.bangkit.snapcook.utils.constant.AnimationConstants.POP_ANIMATION
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.snackbar.Snackbar

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

private val shimmer = Shimmer.AlphaHighlightBuilder()
    .setDuration(1800)
    .setBaseAlpha(0.7f)
    .setHighlightAlpha(0.6f)
    .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
    .setAutoStart(true)
    .build()

private val shimmerDrawable = ShimmerDrawable().apply {
    setShimmer(shimmer)
}


fun ImageView.setImageUrl(url: String?) {
    Glide.with(this.rootView)
        .load(url)
        .placeholder(shimmerDrawable)
        .apply(
            RequestOptions().error(
                R.drawable.img_placeholder_food
            )
        )
        .into(this)
}

fun ImageView.setGlideImageUri(uri: Uri?) {
    Glide.with(this.rootView)
        .load(uri)
        .apply(RequestOptions())
        .into(this)
}

infix fun View.popClick(click: () -> Unit) {
    setOnClickListener {
        it.popTap()
        Handler(Looper.getMainLooper()).postDelayed({
            click()
        }, POP_ANIMATION)
    }
}

fun EditText.showError(message: String) {
    error = message
    requestFocus()
}

fun ShimmerFrameLayout.showAndStart() {
    this.startShimmer()
    this.showShimmer(true)
    this.show()
}

fun ShimmerFrameLayout.hideAndStop() {
    this.stopShimmer()
    this.showShimmer(false)
    this.gone()
}

fun View.showSnackBar(message: String) {
    Snackbar.make(
        this,
        message,
        Snackbar.LENGTH_LONG
    ).also { snackbar ->
        snackbar.setAction("OK") {
            snackbar.dismiss()
        }
    }.show()
}

fun View.slowShow(duration: Long = 750L) {
    this.show()
    this.alpha = 0F
    val animation = ObjectAnimator.ofFloat(this, View.ALPHA, 1f)
        .setDuration(duration)
    animation.start()
}

fun View.popTap() {
    this.visibility = View.VISIBLE
    this.alpha = 1.0f

    this.pivotX = (this.width / 2).toFloat()
    this.pivotY = (this.height / 2).toFloat()

    val scaleDownX = ObjectAnimator.ofFloat(this, "scaleX", 0.7f)
    val scaleDownY = ObjectAnimator.ofFloat(this, "scaleY", 0.7f)

    scaleDownX.duration = 100
    scaleDownY.duration = 100

    val scaleUpX = ObjectAnimator.ofFloat(this, "scaleX", 1.0f)
    val scaleUpY = ObjectAnimator.ofFloat(this, "scaleY", 1.0f)

    scaleUpX.duration = 100
    scaleUpY.duration = 100

    val scaleDown = AnimatorSet()
    scaleDown.play(scaleDownX).with(scaleDownY)
    scaleDown.start()

    val scaleUp = AnimatorSet()
    scaleUp.play(scaleUpX).with(scaleUpY).after(scaleDown)
    scaleUp.start()
}

