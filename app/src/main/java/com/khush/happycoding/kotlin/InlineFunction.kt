package com.khush.happycoding.kotlin

/*
fun calculateTime() {
    println("Calculating Time")
}

fun main() {
    println("Timer Start")
    calculateTime()
    println("Timer End")
}
*/

/*
inline fun calculateTime() {
    println("Calculating Time")
}

fun main() {
    println("Timer Start")
    calculateTime()
    println("Timer End")
}
*/

/*
inline fun doSomething() {
    somePrivateFun() // Compiler will throw error
}
private fun somePrivateFun() {}
*/

inline fun calculateTime(block: ()->Unit): Long {
    val initialTime = System.currentTimeMillis()
    block.invoke()
    return System.currentTimeMillis() - initialTime
}

fun main() {
    val time = calculateTime {
        println("Executing something")
    }
    println(time)
}

/*
fun main() {
    val time = calculateTime({
        println("Executing something")
    }, {
        println("Executing something again")
    })
    println(time)
}

inline fun calculateTime(block1: () -> Unit, noinline block2: () -> Unit): Long {
    val initialTime = System.currentTimeMillis()
    block1.invoke()
    block2.invoke()
    return System.currentTimeMillis() - initialTime
}
*/

/*
inline fun calculateTime(crossinline block: () -> Unit): Long {
    val initialTime = System.currentTimeMillis()
    block.invoke()
    return System.currentTimeMillis() - initialTime
}

fun main() {
    val time = calculateTime {
        println("Executing something")
        return // This will give compile time error
    }
    println(time)
}
*/
