package fr.isen.boldeskul.androiderestaurant.basket


import android.content.Intent

import android.os.Bundle

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import fr.isen.boldeskul.androiderestaurant.HomeActivity

import fr.isen.boldeskul.androiderestaurant.R
import fr.isen.boldeskul.androiderestaurant.ui.theme.AndroidERestaurantTheme



class BasketActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidERestaurantTheme {
                BasketView()
            }

        }
    }
}
@Composable
fun BasketView() {
    val context = LocalContext.current
    val basketItems = remember { mutableStateListOf<BasketItem>() }
    basketItems.addAll(Basket.current(context).items)

    LazyColumn {
        items(basketItems) { item ->
            BasketItemView(item, basketItems)
        }

        item {
            Button(
                onClick = {
                    context.startActivity(Intent(context, HomeActivity::class.java))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text("Back to Menu", style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp))
            }
        }
    }
}


@Composable fun BasketItemView(item: BasketItem, basketItems: MutableList<BasketItem>) {

    Card {
       val context = LocalContext.current
        Card(border =  BorderStroke(1.dp, Color.Black),
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            Row(Modifier.padding(8.dp)) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(item.dish.images.first())
                        .build(),
                    null,
                    placeholder = painterResource(R.drawable.ic_launcher_foreground),
                    error = painterResource(R.drawable.ic_launcher_foreground),
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .width(80.dp)
                        .height(80.dp)
                        .clip(RoundedCornerShape(10))
                        .padding(8.dp)
                )
                Column(
                    Modifier
                        .align(alignment = Alignment.CenterVertically)
                        .padding(8.dp)
                ) {
                    Text(item.dish.name,
                        style = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = 18.sp
                    ))
                    Text("${item.dish.prices.first().price} €",
                        style = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = 18.sp
                    ))
                }

                Spacer(Modifier.weight(1f))
                Text(item.count.toString(),
                    Modifier.align(alignment = Alignment.CenterVertically),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    ))
                Button(onClick = {
                    Basket.current(context).delete(item, context)
                    basketItems.clear()
                    basketItems.addAll(Basket.current(context).items)
                }) {
                    Text("X", style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    ))
                }

            }
        }

    }
}