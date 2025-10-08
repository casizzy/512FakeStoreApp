package com.pjsoft.fakestoreapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.pjsoft.fakestoreapp.screens.HomeScreen
import com.pjsoft.fakestoreapp.screens.ProductDetailScreen
import com.pjsoft.fakestoreapp.ui.theme.FakeStoreAppTheme
import com.pjsoft.fakestoreapp.ui.theme.HomeScreenRoute
import com.pjsoft.fakestoreapp.ui.theme.ProductDetailScreenRoute

/*
* 1. Crear Modelos
* 2. Crear Servicios fetch, axios, httpClient -> Retrofit
* 3. Consumir Servicios
* 4. Refrescar UI
* */


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FakeStoreAppTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = HomeScreenRoute
                    ){
                        composable<HomeScreenRoute> {
                            HomeScreen(
                                navController
                            )
                        }
                        composable<ProductDetailScreenRoute> { backStack ->
                            val args = backStack.toRoute<ProductDetailScreenRoute>()
                            ProductDetailScreen(args.id)
                        }
                    }
                }
            }
        }
    }
}