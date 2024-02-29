package fr.isen.boldeskul.androiderestaurant

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import fr.isen.boldeskul.androiderestaurant.ui.theme.AndroidERestaurantTheme

import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
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
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.GsonBuilder
import fr.isen.boldeskul.androiderestaurant.basket.Basket
import fr.isen.boldeskul.androiderestaurant.basket.BasketActivity


import fr.isen.boldeskul.androiderestaurant.network.Category
import fr.isen.boldeskul.androiderestaurant.network.Dish
import fr.isen.boldeskul.androiderestaurant.network.MenuResult
import fr.isen.boldeskul.androiderestaurant.network.NetworkConstants
import org.json.JSONObject


class CategoryActivity : ComponentActivity() {
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val type = (intent.getSerializableExtra(CATEGORYNAME) as? DishType) ?: DishType.STARTER
    setContent {
        AndroidERestaurantTheme {
            val basketItemCount = Basket.itemCount(this@CategoryActivity)
//            Scaffold(
//                topBar = {
//                    MexicanRestaurantTopApp(basketItemCount = basketItemCount, onBasketClick = {
//                        startActivity(Intent(this@CategoryActivity, BasketActivity::class.java))
//                    })
//                }
//            )
            Scaffold(
                topBar = {  MexicanRestaurantTopApp(basketItemCount = basketItemCount, onBasketClick = {
                        startActivity(Intent(this@CategoryActivity, BasketActivity::class.java))
                   })}
            ) { paddingValues ->
                // Apply the paddingValues to the MenuView to ensure it doesn't overlap with the TopAppBar
                MenuView(type = type, paddingValues = paddingValues)
            }
        }
    }
}


    companion object {
        val CATEGORYNAME = "CATEGORYNAME"
    }
}


@Composable
fun MenuView(type: DishType, paddingValues: PaddingValues) {
    val context = LocalContext.current
    val category = remember {
        mutableStateOf<Category?>(null)
    }

    Column(Modifier.fillMaxWidth()
        .padding(paddingValues),

        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(type.title())
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            category.value?.let {
                items(it.items) {
                    dishRow(it)
                }
            }
        }
        Button(onClick = {
            Toast.makeText(context, "Back to the dishes", Toast.LENGTH_SHORT).show()
            (context as? ComponentActivity)?.finish()
        },
            modifier = Modifier.padding(16.dp))
        {
            Text(text = "Back",
                style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp))
        }
    }
    postData(type, category)
}


@Composable fun dishRow(dish: Dish) {
    val context = LocalContext.current
    Card(
        border = BorderStroke(1.dp, Color.Black),
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable {
                val intent = Intent(context, DetailActivity::class.java).apply {
                    putExtra(DetailActivity.CATEGORY_EXTRA_KEY, dish)
                }
                context.startActivity(intent)
            }
    ) {
        Row(Modifier.padding(8.dp)) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(dish.images.first())
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
            Text(dish.name,
                Modifier
                    .align(alignment = Alignment.CenterVertically)
                    .padding(8.dp)
            )
            Spacer(Modifier.weight(1f))
            Text("${dish.prices.first().price} €",
                Modifier.align(alignment = Alignment.CenterVertically))
        }
    }
}
@Composable
fun postData(type: DishType, category: MutableState<Category?>) {
    val currentCategory = type.key()
    val context = LocalContext.current
    val queue = Volley.newRequestQueue(context)

    val params = JSONObject()
    params.put(NetworkConstants.ID_SHOP, "1")

    val request = JsonObjectRequest(
        Request.Method.POST,
        NetworkConstants.URL,
        params,
        { response ->
            Log.d("request", response.toString(2))
            val result = GsonBuilder().create().fromJson(response.toString(), MenuResult::class.java)
            val filteredResult = result.data.first { categroy -> categroy.name == currentCategory }
            category.value = filteredResult
        },
        {
            Log.e("request", it.toString())
        }
    )

    queue.add(request)

}
