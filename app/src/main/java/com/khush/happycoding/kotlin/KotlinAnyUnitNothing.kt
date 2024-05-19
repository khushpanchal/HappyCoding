package com.khush.happycoding.kotlin

fun main() {
    printAny("Hello, World!")  // Prints: Hello, World!
    printAny(123)  // Prints: 123
    printMessage("Hello, Unit!")  // Prints: Hello, Unit!
    runBlock { println("Here") } // Prints: Here
    fail("This is an error!") // This will throw an exception and never return normally
    println("Hello") // Compiler gives warning "Unreachable code"
}

fun printAny(value: Any?) {
    println(value.toString())
}

fun printMessage(message: String): Unit {
    println(message)
}

fun runBlock(block: ()->Unit) {
    block()
}

fun fail(message: String): Nothing {
    throw IllegalArgumentException(message)
}