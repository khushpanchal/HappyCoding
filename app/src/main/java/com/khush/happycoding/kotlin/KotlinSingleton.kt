package com.khush.happycoding.kotlin

class Singleton private constructor() {
    companion object {
        @Volatile
        private var instance: Singleton? = null

        fun getInstance(): Singleton {
            if(instance ==null) {
                synchronized(this) {
                    if(instance ==null) {
                        instance = Singleton()
                    }
                }
            }
            return instance!!
        }
    }
}

object Config {
    const val TAG = "Config"
}

class Demo {
    companion object DemoConfig {
        const val TAG = "DemoConfig"
    }
}

fun main() {
    val nameConfig = Config.javaClass.simpleName //instance of Config will be created
    val nameDemo = Demo //instance of DemoConfig will be created, Demo instance will not be created
}