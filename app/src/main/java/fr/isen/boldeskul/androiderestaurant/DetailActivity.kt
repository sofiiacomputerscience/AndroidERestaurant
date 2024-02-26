package fr.isen.boldeskul.androiderestaurant

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material3.Text
import fr.isen.boldeskul.androiderestaurant.network.Dish
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

import com.google.gson.Gson
import fr.isen.boldeskul.androiderestaurant.basket.Basket
import fr.isen.boldeskul.androiderestaurant.basket.BasketActivity


class DetailActivity : ComponentActivity() {
    @OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dish = intent.getSerializableExtra(CATEGORY_EXTRA_KEY) as? Dish
        setContent {
            val context = LocalContext.current
         //   var quantity by remember { mutableStateOf(1) }
//            val pricePerUnit = dish?.prices?.firstOrNull()?.price?.toFloat() ?: Of
//            val totalPrice = pricePerUnit * quantity


            val pagerState = rememberPagerState(pageCount = {
                dish?.images?.count() ?: 0
            })


            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                TopAppBar(title = { Text(dish?.name ?: "") })

                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .height(250.dp)
                        .fillMaxWidth()) { page ->
                    AsyncImage(
                        model = ImageRequest.Builder(context)
                            .data(dish?.images?.get(page))
                            .build(),
                        contentDescription = "Dish Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    )
                }

                Text(
                    text = "Ingredients: ${dish?.ingredients?.joinToString { it.name } ?: "No ingredients available"}",
                    modifier = Modifier.padding(8.dp)
                )
                Button(onClick = {
                    Toast.makeText(context, "Back to the dishes", Toast.LENGTH_SHORT).show()
                    (context as? ComponentActivity)?.finish()
                },
                    modifier = Modifier.padding(16.dp))
                {
                    //QuantitySelector(dish = dish)
                    Text(text = "Back")
                }
                Spacer(modifier = Modifier.height(20.dp))
                QuantitySelector(dish = dish)
            }

            //Spacer(modifier = Modifier.height(1000.dp))
            
            //QuantitySelector(dish = dish)
        }
    }
    companion object {
        const val CATEGORY_EXTRA_KEY = "CATEGORY_EXTRA_KEY"
    }
}






@Composable
fun QuantitySelector(dish: Dish?) {
    var quantity by remember { mutableStateOf(1) }
    //dish price string to float
    val priceInt = dish?.prices?.first()?.price?.toFloat() ?: 0f
    var totalPrice = priceInt * quantity
    val context = LocalContext.current
    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Quantité: $quantity",
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Button(onClick = { if (quantity > 1) quantity-- }) {
            Text(text = "-")
        }
        Button(onClick = { quantity++ }) {
            Text(text = "+")
        }
        Button(onClick = {
            if (dish != null) {
                Basket.current(context).add(dish, quantity, context)
            }
        }) {
            Text("Commander")
        }
        Button(onClick = {
            val intent = Intent(context, BasketActivity::class.java)
            context.startActivity(intent)
        }) {
            Text("Voir mon panier")
        }
    }
    Text(
        text = "Total: $totalPrice €",
        fontSize = 16.sp,
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(8.dp)
    )
}





//
//class DetailActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//
//        super.onCreate(savedInstanceState)
//        val dish = intent.getSerializableExtra(CATEGORY_EXTRA_KEY) as? Dish
//        setContent {
//            val context = LocalContext.current
//            val count = remember {
//                mutableIntStateOf(1)
//            }
//            val ingredient = dish?.ingredients?.map { it.name }?.joinToString(", ") ?: ""
//            val pagerState = rememberPagerState(pageCount = {
//                dish?.images?.count() ?: 0
//            })
//    }
//    companion object {
//         val CATEGORY_EXTRA_KEY = "CATEGORY_EXTRA_KEY"
//    }
//}