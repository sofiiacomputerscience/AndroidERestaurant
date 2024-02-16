package fr.isen.boldeskul.androiderestaurant

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
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
import androidx.compose.ui.platform.rememberNestedScrollInteropConnection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.android.volley.Request.Method
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject


import fr.isen.boldeskul.androiderestaurant.network.Category
import fr.isen.boldeskul.androiderestaurant.network.Dish
import fr.isen.boldeskul.androiderestaurant.network.MenuResult
import fr.isen.boldeskul.androiderestaurant.network.NetworkConstants
import org.json.JSONObject


class CategoryActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val extra = intent.getStringExtra(CATEGORYNAME) ?: ""
        setContent {
            MenuView(convertExtra(extra))
        }
    }

    fun convertExtra(extra: String): String {
        return when(extra) {
            DishType.STARTER.name -> "Entrées"
            DishType.MAIN.name -> "Plats"
            DishType.DESSERT.name -> "Desserts"
            else -> { "" }
        }
    }

    override fun onDestroy() {
        Log.d("lifecycle", "category ondestroy")
        super.onDestroy()
    }

    override fun onResume() {
        super.onResume()
        Log.d("lifecycle", "category onresume")
    }

    override fun onPause() {
        Log.d("lifecycle", "category onpause")
        super.onPause()
    }

    companion object {
        val CATEGORYNAME = "CATEGORYNAME"
    }
}


@Composable
fun MenuView(selectedCategory:String) {
    val category = remember {
        mutableStateOf<Category?>(null)
    }
    Column(
        Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally)
    {
        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            category.value?.let {
                items(it.items) {
                    Text(it.name)
                }
            }
        }
        PostData(selectedCategory, category)
    }
}

@Composable fun dishRow(dish: Dish) {
    val context = LocalContext.current
    Card(border =  BorderStroke(1.dp, Color.Black),
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable {
                val intent = Intent(context, CategoryActivity::class.java)
                intent.putExtra(CategoryActivity.CATEGORYNAME, dish)
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
fun PostData (selectedCategory:String, category: MutableState<Category?>) {
    //creating request view
   //val currentCategory = selectedCategory.title()
    val context = LocalContext.current
    val queue = Volley.newRequestQueue(context)

    val parameters = JSONObject()
    parameters.put(NetworkConstants.ID_SHOP, "1")

    val request = JsonObjectRequest(
        Method.POST,
        NetworkConstants.URL,
        parameters,
        {
            Log.d("request", it.toString(2))

            val result = GsonBuilder().create().fromJson(it.toString(), MenuResult::class.java)
            val filteredResult = result.data.first {
                it.name == selectedCategory
            }
            category.value = filteredResult
        },
        {
            Log.e("request", it.toString())
        }
        )
    queue.add(request)
}


