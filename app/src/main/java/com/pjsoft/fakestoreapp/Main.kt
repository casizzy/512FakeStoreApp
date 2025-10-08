package com.pjsoft.fakestoreapp

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.*

fun main(){
    //hilo
    println("Inicio en hilo: ${Thread.currentThread().name}")
    cWithContext()
    println("Fin en hilo: ${Thread.currentThread().name}")
    // Corrutinas
}

fun MyGlobal(){
    GlobalScope.launch {
        SaludoAsincrono()
    }
}

// suspend fun
suspend fun SaludoAsincrono(){
    println("Hola desde asincrono")
    delay(2000)
    println("Ya termine de saludarte")
}
// Dispatchers
// Edificio 1. App - UI
// Edificios 2.
fun cAsync(){
    runBlocking {
        val result = async(Dispatchers.IO) {
            println("Consultado datos de una API")
            delay(5000)
            println("Resultados traidos")
            "Datos"
        }
        println("El resultado es: ${result.await()}")
    }
}

fun cWithContext(){
    runBlocking {
        val result = withContext(Dispatchers.IO){
            println("WithContext en hilo: ${Thread.currentThread().name}")
            println("Consultando info de API")
            delay(2000)
            println("Datos obtenidos")
            "{ age: 17 }"
        }
        println("El resultado es: $result")
    }
}