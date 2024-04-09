package com.khush.happycoding.kotlin

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.withContext

class School(private val case: Int) {

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        log("CoroutineExceptionHandler: " + exception.message)
    }

    init {

        //Case 1 - App crash, as error propagate to parent and there in no exception handler
        if(case == 1) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    launch { postSchoolData() }
                    launch { postTeacherData(true) } //this throws exception
                    launch { postStudentData() }
                } catch (e: Exception) {
                    log("Catch exception: " + e.message)
                }
            }
        }

        //Case 2 - App crash, as error propagate to parent and there in no exception handler
        if(case == 2) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val totalStudent = async { getStudentCount(true) } //this throws exception
                    val totalTeacher = async { getTeacherCount() }
                    totalStudent.await()
                    totalTeacher.await()
                } catch (e: Exception) {
                    log("Catch exception: " + e.message)
                }
            }
        }

        //Case 3 - App crash, as error propagate to parent and there in no exception handler
        if(case == 3) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val totalStudent = async { getStudentCount(true) } //this throws exception
                    val totalTeacher = async { getTeacherCount() }
                    try {
                        totalStudent.await()
                    } catch (e: Exception) {
                        log("Await Exception: " + e.message)
                    }
                    try {
                        totalTeacher.await()
                    } catch (e: Exception) {
                        log("Await Exception: " + e.message)
                    }
                } catch (e: Exception) {
                    log("Catch exception: " + e.message)
                }
            }
        }

        //Case 4 - Error is rethrown to outer scope (coroutineScope) and catch inside outer try-catch block
        if(case == 4) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    coroutineScope {
                        launch { postSchoolData() }
                        launch { postTeacherData(exception = true) } //this throws exception
                        launch { postStudentData() }
                    }
                } catch (e: Exception) {
                    log("Catch exception: " + e.message)
                }
            }
        }

        //Case 5 - Error is rethrown to outer scope (coroutineScope) and catch inside outer try catch block, parent is cancelled
        if(case == 5) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    coroutineScope {
                        val totalStudent = async { getStudentCount(true) } //this throws exception
                        val totalTeacher = async { getTeacherCount() }
                        totalStudent.await()
                        totalTeacher.await()
                    }
                } catch (e: Exception) {
                    log("Catch exception: " + e.message)
                }
            }
        }

        //Case 6 - Error is rethrown to outer scope (coroutineScope) and catch inside outer try catch block, parent is cancelled
        if(case == 6) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    coroutineScope {
                        val totalStudent = async { getStudentCount(true) } //this throws exception
                        val totalTeacher = async { getTeacherCount() }
                        try {
                            totalStudent.await()
                        } catch (e: Exception) {
                            log("Await Exception: " + e.message)
                        }
                        try {
                            totalTeacher.await()
                        } catch (e: Exception) {
                            log("Await Exception: " + e.message)
                        }
                    }
                } catch (e: Exception) {
                    log("Catch exception: " + e.message)
                }
            }
        }

        //Case 7 - App crash as supervisorScope (make children top level coroutine) could not find any exception handler
        if(case == 7) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    supervisorScope {
                        launch { postSchoolData() }
                        launch { postTeacherData(true) } //this throws exception
                        launch { postStudentData() }
                    }
                } catch (e: Exception) {
                    log("Catch exception: " + e.message)
                }
            }
        }

        //Case 8 - In case of async it will hold the error and error is rethrown to outer scope (supervisorScope) on calling await() and catch inside outer try-catch block
        if(case == 8) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    supervisorScope {
                        val totalStudent = async { getStudentCount(true) } //this throws exception
                        val totalTeacher = async { getTeacherCount() }
                        totalStudent.await() // this will make scope to re throw error, and get caught and cancel the scope
                        totalTeacher.await()
                    }
                } catch (e: Exception) {
                    log("Catch exception: " + e.message)
                }
            }
        }

        //Case 9 - Everything works well, as await is called inside try catch, so no error re throwing and crashing
        if(case == 9) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    supervisorScope {
                        val totalStudent = async { getStudentCount(true) } //this throws exception
                        val totalTeacher = async { getTeacherCount() }
                        try {
                            totalStudent.await()
                        } catch (e: Exception) {
                            log("Await Exception: " + e.message)
                        }
                        try {
                            totalTeacher.await()
                        } catch (e: Exception) {
                            log("Await Exception: " + e.message)
                        }
                    }
                } catch (e: Exception) {
                    log("Catch exception: " + e.message)
                }
            }
        }

        //Case 10 - error handled by coroutineExceptionHandler
        if(case == 10) {
            CoroutineScope(Dispatchers.IO + coroutineExceptionHandler).launch {
                try {
                    supervisorScope {
                        launch { postSchoolData() }
                        launch { postTeacherData(true) } //this throws exception
                        launch { postStudentData() }
                    }
                } catch (e: Exception) {
                    log("Catch exception: " + e.message)
                }
            }
        }

        //Case 11 - Error is rethrown to outer scope (supervisorScope) and catch inside outer try-catch block
        if(case == 11) {
            CoroutineScope(Dispatchers.IO + coroutineExceptionHandler).launch {
                try {
                    supervisorScope {
                        val totalStudent = async { getStudentCount(true) } //this throws exception
                        val totalTeacher = async { getTeacherCount() }
                        totalStudent.await() // this will rethrow the error, so no need to get handled by coroutineExceptionHandler
                        totalTeacher.await()
                    }
                } catch (e: Exception) {
                    log("Catch exception: " + e.message)
                }
            }
        }

        //Case 12 - Everything works well, as await is called inside try-catch, so no error re thrown, no crash
        if(case == 12) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    supervisorScope {
                        val totalStudent = async { getStudentCount(true) } //this throws exception
                        val totalTeacher = async { getTeacherCount() }
                        try {
                            totalStudent.await()
                        } catch (e: Exception) {
                            log("Await Exception: " + e.message)
                        }
                        try {
                            totalTeacher.await()
                        } catch (e: Exception) {
                            log("Await Exception: " + e.message)
                        }
                    }
                } catch (e: Exception) {
                    log("Catch exception: " + e.message)
                }
            }
        }

        //Case 13 - same as coroutineScope - error caught by catch block
        if(case == 13) {
            CoroutineScope(Dispatchers.Default).launch {
                try {
                    withContext(Dispatchers.IO) {
                        launch { postSchoolData() }
                        launch { postTeacherData(true) } //this throws exception
                        launch { postStudentData() }
                    }
                } catch (e: Exception) {
                    log("Catch exception: " + e.message)
                }
            }
        }

        //Case 14 - same as coroutineScope - error caught by catch block, just context change
        if(case == 14) {
            CoroutineScope(Dispatchers.Default).launch {
                try {
                    withContext(Dispatchers.IO) {
                        val totalStudent = async { getStudentCount(true) } //this throws exception
                        val totalTeacher = async { getTeacherCount() }
                        totalStudent.await()
                        totalTeacher.await()
                    }
                } catch (e: Exception) {
                    log("Catch exception: " + e.message)
                }
            }
        }

        //Case 15: same as coroutineScope, Error is rethrown to outer scope and catch inside outer try catch block, just context change
        if(case == 15) {
            CoroutineScope(Dispatchers.Default).launch {
                try {
                    withContext(Dispatchers.IO) {
                        val totalStudent = async { getStudentCount(true) } //this throws exception
                        val totalTeacher = async { getTeacherCount() }
                        try {
                            totalStudent.await()
                        } catch (e: Exception) {
                            log("Await Exception: " + e.message)
                        }
                        try {
                            totalTeacher.await()
                        } catch (e: Exception) {
                            log("Await Exception: " + e.message)
                        }
                    }
                } catch (e: Exception) {
                    log("Catch exception: " + e.message)
                }
            }
        }
    }

    private suspend fun postSchoolData(exception: Boolean = false) {
        delay(100)
        if(exception) throw Exception("Error in postSchoolData")
        else log("postSchoolData success")
    }

    private suspend fun postStudentData(exception: Boolean = false) {
        delay(100)
        if(exception) throw Exception("Error in postStudentData")
        else log("postStudentData success")
    }

    private suspend fun postTeacherData(exception: Boolean = false) {
        delay(100)
        if(exception) throw Exception("Error in postTeacherData")
        else log("postTeacherData success")
    }

    private suspend fun getStudentCount(exception: Boolean = false): Int {
        delay(100)
        if(exception) throw Exception("Error in getStudentCount")
        else log("getStudentCount success")
        return 100
    }

    private suspend fun getTeacherCount(exception: Boolean = false): Int {
        delay(100)
        if(exception) throw Exception("Error in getTeacherData")
        else log("getTeacherCount success")
        return 50
    }

    private fun log(msg: Any?) {
        println("Testing " +  msg.toString())
    }

}

fun main() {
    runBlocking {
        for(i in 1..15) {
            println("Case $i")
            School(i)
            delay(1000)
        }
    }
}
