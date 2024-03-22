package com.khush.happycoding.kotlin

import kotlin.reflect.KProperty

/**
 * Delegation in Kotlin: Passing the responsibility to other class or method
 */
//interface
interface Action {
    fun eat()
    fun breathe()
}
//Animal implements action
class Animal(val name: String): Action {
    override fun eat() {
        println("$name eats")
    }
    override fun breathe() {
        println("$name breathes")
    }
}
//Bird implements action, but implementation is same as other animals, delegate to implementation details to Animal
class Bird(private val name: String): Action by Animal(name){
    fun fly() {
        println("$name flies")
    }
}
//Same as Bird, have to implement Action, but same as other animals, so delegate
class AquaticAnimal(private val name: String): Action by Animal(name){
    fun swim() {
        println("$name swims")
    }
}

//Other example
interface ApplyOnce {
    fun getAppliedOnce()
}
class IapplyInterface(): ApplyOnce {
    override fun getAppliedOnce() {
        println("I have applied once")
    }
}
class LetsTryApplyInterface: ApplyOnce by IapplyInterface(){
}



/**
 * Property Delegation: Delegate Property (pass responsibility of getter and setter) to other class
 */
class Name {
    var firstName: String by NameDelegate()
    var lastName: String by NameDelegate()
    constructor(firstName: String, lastName: String) {
        this.firstName = firstName
        this.lastName = lastName
    }
}

class NameDelegate {
    private lateinit var name: String
    operator fun getValue(thisRef: Any?, property: KProperty<*>): String {
        return name
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
        if(value.isNotEmpty() && value.all { it.isLetter() }) {
            name = value
        } else {
            throw IllegalArgumentException("Name must not be empty and should only contains letter")
        }
    }
}

fun main() {
    /**
     * Delegation
     */
    val parrot = Bird("Parrot")
    parrot.eat()
    parrot.breathe()
    parrot.fly()

    val fish = AquaticAnimal("Fish")
    fish.eat()
    fish.breathe()
    fish.swim()

    LetsTryApplyInterface().getAppliedOnce()

    /**
     * Property Delegation
     */
    val name = Name("Khush", "Panchal")
    val firstName = name.firstName
    val lastName = name.lastName
    println("$firstName $lastName")

}