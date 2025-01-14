package com.khush.happycoding.kotlin.generics

// Generics class
data class Question<T>(
    val questionText: String,
    val answer: T
)

// Generics Function
fun <T> printValue(value: T) {
    println(value)
}

fun <T> T.toCustomString(): String { // extension function
    return "Value: $this"
}

// Generics Constraint, upper bound
interface Movable {
    fun move()
}

class Car(private val make: String, private val model: String) : Movable {
    override fun move() {
        println("$make $model is moving.")
    }
}

fun <T : Movable> run(vehicle: T) {
    vehicle.move()
}

// Generics where clause
interface Flyable {
    fun fly()
}

class Plane(private val make: String, private val model: String) : Movable, Flyable {
    override fun move() {
        println("$make $model is moving.")
    }

    override fun fly() {
        println("$make $model is flying.")
    }
}

fun <T> operate(vehicle: T) where T : Movable, T : Flyable {
    vehicle.move()
    vehicle.fly()
}

// Generics reified
fun <T> printSomething(value: T) {
    println(value.toString())// OK
//    println("Doing something with type: ${T::class.simpleName}") // Error
}

//at compile time, the compiler removes the type argument from the function call. This is called type erasure
//The reified modifier before the type parameter enables the type information to be retained at runtime
//Reified let us use reflection on type parameter
//function must be inline to use reified
inline fun <reified T> doSomething(value: T) {
    println("Doing something with type: ${T::class.simpleName}") // OK
}

class Container<T>(val item: T)

open class Animal
class Dog : Animal()

class AnimalProducer<out T>(private val instance: T) {
    fun produce(): T = instance
    // fun consume(value: T) { /* ... */ } //This will show compile time error
}

class AnimalConsumer<in T> {
    fun consume(value: T) {
        println("Consumed: ${value.toString()}")
    }
    // fun produce(): T { /* ... */ } //This will show compile time error
}

fun main() {
    // Generic parameter type can be inferred without explicitly mentioning
    val questionString = Question("What's your favorite programming language?", "Kotlin")
    val questionBoolean = Question("Kotlin is statically typed?", true)
    val questionInt = Question("How many days are in a week?", 7)

    printValue("Hello, Kotlin!")
    printValue(42)
    println(3.14.toCustomString())

    run(Car("BMW", "X3 M"))
    operate(Plane("Boeing", "747"))

    val intContainer = Container(10)
//    val numberContainer: Container<Number> = intContainer // Error: Type mismatch

    val dogProducer: AnimalProducer<Animal> = AnimalProducer(Dog())
    println("Produced: ${dogProducer.produce()}") // Works because of `out`

    val animalConsumer: AnimalConsumer<Dog> = AnimalConsumer<Animal>()
    animalConsumer.consume(Dog()) // Works because of `in`

}
