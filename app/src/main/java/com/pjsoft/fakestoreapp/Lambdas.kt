package com.pjsoft.fakestoreapp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

// Callback
// Hell Callbacks

fun main(){
    val a = 1
    val b = 2
}
//FUNCION QUE RECIBA DOS NUMEROS Y QUE REGRESE DOS NUMEROS
fun operacionDeNumeros(a:Int,b:Int, operacion : (Int,Int) -> Int){
    println("El numero a vale: $a")
    println("El numero b vale: $b")
    val result = operacion(a,b)
    println(result)
}

fun suma(a: Int, b : Int) : Int{
    return a + b
}

fun resta(a : Int, b : Int) : Int{
    return a - b
}

@Composable
fun BookCard(onClick : () -> Unit){
    Column(
        modifier = Modifier
            .clickable{
                onClick()
            }
    ) {
        // BLA BLA BLA BLA
    }
}

@Composable
fun HomeScreen(){
    Column {
        BookCard(onClick = {
            println("Navegando a otro lugar")
        })
    }
}