package fr.isen.boldeskul.androiderestaurant

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import coil.request.ImageRequest
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

import fr.isen.boldeskul.androiderestaurant.basket.Basket
import fr.isen.boldeskul.androiderestaurant.basket.BasketActivity
import fr.isen.boldeskul.androiderestaurant.ui.theme.AndroidERestaurantTheme


//
//
//class DetailActivity : ComponentActivity() {
//    @OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        val dish = intent.getSerializableExtra(CATEGORY_EXTRA_KEY) as? Dish
//        setContent {
//            val context = LocalContext.current
//            val pagerState = rememberPagerState(pageCount = {
//                dish?.images?.count() ?: 0
//            })
//            Column(horizontalAlignment = Alignment.CenterHorizontally) {
//                TopAppBar(title = { Text(dish?.name ?: "") })
//
//                HorizontalPager(
//                    state = pagerState,
//                    modifier = Modifier
//                        .height(250.dp)
//                        .fillMaxWidth()) { page ->
//                    AsyncImage(
//                        model = ImageRequest.Builder(context)
//                            .data(dish?.images?.get(page))
//                            .build(),
//                        placeholder = painterResource(R.drawable.ic_launcher_foreground),
//                        error = painterResource(R.drawable.ic_launcher_foreground),
//                        contentDescription = "Dish Image",
//                        contentScale = ContentScale.Crop,
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(8.dp)
//                    )
//                }
//                Text(
//                    text = "Ingredients: ${dish?.ingredients?.joinToString { it.name } ?: "No ingredients available"}",
//                    modifier = Modifier.padding(8.dp)
//                )
//                Button(
//                    onClick = {
//                        Toast.makeText(context, "Back to the dishes", Toast.LENGTH_SHORT).show()
//                        (context as? ComponentActivity)?.finish()
//                    },

//                    modifier = Modifier.padding(16.dp)
//                )
//                {
//                    Text(text = "Back")
//                }
//                Spacer(modifier = Modifier.height(20.dp))
//                QuantitySelector(dish = dish)
//
//            }
//        }
//    }
//    companion object {
//        const val CATEGORY_EXTRA_KEY = "CATEGORY_EXTRA_KEY"
//    }
//}

class DetailActivity : ComponentActivity() {
    @OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dish = intent.getSerializableExtra(CATEGORY_EXTRA_KEY) as? Dish
        setContent {
            AndroidERestaurantTheme {
                val basketItemCount = Basket.itemCount(this@DetailActivity)
                Scaffold(
                 topBar = {
                       MexicanRestaurantTopApp(basketItemCount = basketItemCount, onBasketClick = {
                           // Navigate to BasketActivity
                           startActivity(Intent(this@DetailActivity, BasketActivity::class.java))
                       })
                   })
                    { paddingValues ->
                        DetailContentView(dish, paddingValues)
                    }
            }
        }
    }

    companion object {
        const val CATEGORY_EXTRA_KEY = "CATEGORY_EXTRA_KEY"
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DetailContentView(dish: Dish?, paddingValues: PaddingValues) {
    val context = LocalContext.current
    val cartItemCount = remember { mutableStateOf(Basket.itemCount(context)) }

    val pagerState = rememberPagerState(pageCount = {
        dish?.images?.count() ?: 0
    })
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .height(250.dp)
                .fillMaxWidth()
        ) { page ->
            dish?.images?.get(page)?.let { imageUrl ->
                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(imageUrl)
                        .build(),
                    contentDescription = "Dish Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
        Text(
            text = "Ingredients: ${dish?.ingredients?.joinToString { it.name } ?: "No ingredients available"}",
            modifier = Modifier.padding(top = 16.dp),
            style = MaterialTheme.typography.bodyLarge.copy(
                color = Color(0xFFFB8C00),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        )
        Spacer(modifier = Modifier.height(16.dp))


        QuantitySelector(dish = dish, cartItemCount = cartItemCount, onItemAdded = {
            Toast.makeText(context, "Cart updated. Total items now: ${cartItemCount.value}", Toast.LENGTH_SHORT).show()
        })


        Button(
            onClick = {
                Toast.makeText(context, "Back to the dishes", Toast.LENGTH_SHORT).show()
                (context as? ComponentActivity)?.finish()
            },
            modifier = Modifier.padding(16.dp)
        )
        {
            Text(text = "Back",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                ))
        }
    }
}

@Composable
fun QuantitySelector(dish: Dish?,  cartItemCount: MutableState<Int>, onItemAdded: () -> Unit) {
    var quantity by remember { mutableStateOf(1) }
    val priceInt = dish?.prices?.first()?.price?.toFloat() ?: 0f
    var totalPrice = priceInt * quantity
    val context = LocalContext.current
    Column(modifier = Modifier.padding(16.dp)) {

        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically) {

            Button(onClick = { quantity++ }) {
                Text(text = "+",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp))
            }
            Text(
                text = "Quantité: $quantity",
                fontSize = 16.sp,
                modifier = Modifier.padding(horizontal = 8.dp),
            )
            Button(onClick = { if (quantity > 1) quantity-- }) {
                Text(text = "-",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp))
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
            Button(onClick = {
                if (dish != null) {
                    Basket.current(context).add(dish, quantity, context)
                    cartItemCount.value = Basket.itemCount(context)
                    Toast.makeText(context, "Ajoute a mon panier", Toast.LENGTH_SHORT).show()
                    onItemAdded()
                    Log.d("DetailActivity", "Item added to cart. Quantity: $quantity")


                }
            }) {
                Text("Commander",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp))
            }

//            Button(onClick = {
//                Toast.makeText(context, "Ajoute a mon panier", Toast.LENGTH_SHORT).show()
//                val intent = Intent(context, BasketActivity::class.java)
//                context.startActivity(intent)
//            }) {
//                Text("Voir mon panier",
//                    style = MaterialTheme.typography.bodyLarge.copy(
//                        fontWeight = FontWeight.Bold,
//                        fontSize = 18.sp))
//            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Total: $totalPrice €",
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}



