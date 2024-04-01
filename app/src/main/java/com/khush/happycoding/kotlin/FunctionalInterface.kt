package com.khush.happycoding.kotlin

import android.view.View

// Normal Interface
interface ClickListener1 {
    fun onClick(view: View)
}

// Functional Interface or SAM (Single Abstract Method)
fun interface ClickListener2 {
    fun onClick(view: View)
    fun clicked() { //adding print statement (non abstract method)
        println("Clicked")
    }
}

class ClickUtil {

    private val view = View(null) //dummy view class just for the example

    private var clickListener1: ClickListener1? = null //normal interface
    private var clickListener2: ClickListener2? = null //functional interface
    private var clickListener3: ((View)->Unit)? = null //Higher order function

    fun setClickListener1(clickLis: ClickListener1) {
        clickListener1 = clickLis
    }

    fun setClickListener2(clickLis: ClickListener2) {
        clickListener2 = clickLis
    }

    fun setClickListener3(clickLis: (View)->Unit) {
        clickListener3 = clickLis
    }

    fun click1() {
        clickListener1?.onClick(view)
    }

    fun click2() {
        clickListener2?.onClick(view)
        clickListener2?.clicked() // calling non abstract method, extending the functionality of Functional interface.
    }

    fun click3() {
        clickListener3?.invoke(view) //or clickListener(view), these are the only way to call the function
    }

}

fun main() {
    val clickUtil = ClickUtil()

    // Using anonymous object while passing to the function
    clickUtil.setClickListener1(object : ClickListener1 {
        override fun onClick(view: View) {
            // some click action
            view.setOnClickListener {

            }
        }
    })

    // Using lambda while passing to the function
    clickUtil.setClickListener2 {view ->
        // some click action
    }

    // Using lambda while passing to the function
    clickUtil.setClickListener3 {view ->
        // some click action
    }

    clickUtil.click1()
    clickUtil.click2()
    clickUtil.click3()

}