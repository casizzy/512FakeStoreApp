package com.pjsoft.fakestoreapp.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.pjsoft.fakestoreapp.models.Product
import com.pjsoft.fakestoreapp.services.ProductService
import com.pjsoft.fakestoreapp.ui.theme.HomeScreenRoute
import com.pjsoft.fakestoreapp.ui.theme.ProductDetailScreenRoute
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@Composable
fun ProductDetailScreen(navController: NavController, id: Int) {
    var product by remember { mutableStateOf<Product?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }
    var quantity by remember { mutableStateOf(1) }

    LaunchedEffect(id) {
        try {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://fakestoreapi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val service = retrofit.create(ProductService::class.java)
            val result = withContext(Dispatchers.IO) { service.getProductById(id) }
            product = result
        } catch (e: Exception) {
            error = e.message
            Log.e("ProductDetailScreen", "Error: ${e.message}")
        } finally {
            isLoading = false
        }
    }

    when {
        isLoading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = Color(0xFFFFC107))
            }
        }
        error != null -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Error: $error", color = Color.Red)
            }
        }
        product != null -> {
            val p = product!!

            Scaffold(
                containerColor = Color.Black,
                bottomBar = {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White)
                            .padding(horizontal = 16.dp, vertical = 12.dp)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                IconButton(
                                    onClick = { if (quantity > 1) quantity-- },
                                    modifier = Modifier
                                        .size(40.dp)
                                        .clip(CircleShape)
                                        .background(Color(0xFFE0E0E0))
                                ) {
                                    Text("-", fontSize = 22.sp, fontWeight = FontWeight.Bold)
                                }

                                Text(quantity.toString(), fontSize = 18.sp, fontWeight = FontWeight.Bold)

                                IconButton(
                                    onClick = { quantity++ },
                                    modifier = Modifier
                                        .size(40.dp)
                                        .clip(CircleShape)
                                        .background(Color(0xFFE0E0E0))
                                ) {
                                    Text("+", fontSize = 22.sp, fontWeight = FontWeight.Bold)
                                }
                            }

                            Spacer(Modifier.height(12.dp))

                            Button(
                                onClick = { /* TODO */ },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(52.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2F2F2F)),
                                shape = RoundedCornerShape(14.dp)
                            ) {
                                Text(
                                    "Añadir a carrito",
                                    color = Color(0xFFFFC107),
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            Spacer(Modifier.height(12.dp))
                        }
                    }
                }
            ) { padding ->

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp)
                            .background(Color.Black)
                    ) {
                        Box(
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .clip(RoundedCornerShape(topStart = 80.dp))
                                .fillMaxWidth()
                                .height(50.dp)
                                .background(Color.White)
                        )
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Volver",
                            tint = Color.White,
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .padding(start = (1).dp, top = 20.dp)
                                .size(28.dp)
                                .clickable { navController.navigate(HomeScreenRoute) }
                        )
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.White)
                            .padding(horizontal = 20.dp)
                            .padding(top = 10.dp)
                    ) {
                        AsyncImage(
                            model = p.image,
                            contentDescription = p.title,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(250.dp)
                                .clip(RoundedCornerShape(16.dp)),
                            contentScale = ContentScale.Crop
                        )

                        Spacer(Modifier.height(16.dp))

                        Text(
                            p.title,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        Text(
                            "$${p.price}",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFFFC107)
                        )

                        Spacer(Modifier.height(12.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text("⭐ ${p.rating.rate}", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                            Text("(${p.rating.count} reseñas)", color = Color.Gray, fontSize = 14.sp)
                        }

                        Spacer(Modifier.height(16.dp))

                        Text(
                            "Descripción",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp,
                            color = Color.Black
                        )
                        Text(
                            p.description,
                            fontSize = 14.sp,
                            color = Color.Gray,
                            lineHeight = 18.sp
                        )
                    }
                }
            }
        }
    }
}