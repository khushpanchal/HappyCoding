package com.khush.happycoding.kotlin

import kotlinx.coroutines.runBlocking

class IfOrLet {

    private var string1: String? = "some string 1"

    init {

        if(string1 != null) {
            makeString1Null()
            println(string1) // prints null
        }

        string1 = "some string 1"

        string1?.let {
            makeString1Null() // <----- even if this make string1 null, the actual value before entering the let block (it) will not change
            println(it) // prints "some string 1"
        }
    }

    private fun makeString1Null() {
        string1 = null
    }

}

fun main() {
    runBlocking {
        IfOrLet()
    }
}