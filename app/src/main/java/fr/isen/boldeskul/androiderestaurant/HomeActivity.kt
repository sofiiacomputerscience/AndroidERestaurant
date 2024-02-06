package fr.isen.boldeskul.androiderestaurant

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import fr.isen.boldeskul.androiderestaurant.ui.theme.AndroidERestaurantTheme
import androidx.compose.ui.res.painterResource
import androidx.compose.material3.Button
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material3.*
import androidx.compose.foundation.layout.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

import androidx.compose.material3.Scaffold

import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import fr.isen.boldeskul.androiderestaurant.ui.theme.ralewayFontFamily

enum class DishType{
    STARTER, MAIN, DESSERT
}

interface MenuInterface{
    fun dishPressed(dishType: DishType)
}



class HomeActivity : ComponentActivity(), MenuInterface {
    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidERestaurantTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                )
                {
                    Scaffold(
                        topBar = { MexicanRestaurantTopApp() },
                    ) {
                        SetupView(this)
                    }
                }
            }
        }
    }
    override fun dishPressed(dishType: DishType){
        Toast.makeText(this, "Here my toast",  Toast.LENGTH_LONG). show()

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MexicanRestaurantTopApp(){
    SmallTopAppBar(
        title = {

            Text(
                text = "Mexican Restaurant",
                color = Color.White,
                style = TextStyle(
                    fontSize = 24.sp, // Adjust the font size as needed
                    fontFamily = ralewayFontFamily, // Apply your custom font family
                    fontWeight = FontWeight.Bold, // Apply bold style if desired
                    letterSpacing = 0.5.sp // Adjust letter spacing if needed
                )
            )
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = Color(0xFF039BE5)
        )
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AndroidERestaurantTheme {
        SetupView(HomeActivity())
    }
}
@Composable
fun SetupView(menu: MenuInterface){
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(

            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        )
        {
//        Image(painterResource(R.drawable.ic_launcher_foreground), null)

            Button(onClick = { menu.dishPressed(DishType.STARTER) }, modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(50.dp),
                shape = MaterialTheme.shapes.small) {
                Text(text = stringResource(R.string.menu_starters),
                    fontSize = 18.sp,
                    fontFamily = ralewayFontFamily)
            }
            Button(onClick = { menu.dishPressed(DishType.MAIN) }) {
                Text(stringResource(R.string.menu_main),
                    fontSize = 18.sp,
                    fontFamily = ralewayFontFamily)
            }
            Button(onClick = { menu.dishPressed(DishType.DESSERT)}) {
                Text(stringResource(R.string.menu_dessert),
                    fontSize = 18.sp,
                    fontFamily = ralewayFontFamily)
            }
        }
    }
}

