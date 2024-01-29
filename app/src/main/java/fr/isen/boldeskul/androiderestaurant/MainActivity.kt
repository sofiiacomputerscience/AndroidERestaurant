package fr.isen.boldeskul.androiderestaurant

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidERestaurantTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")

                }
                SetupView()
                GreetingPreview()
            }
        }
    }
    @Composable
    fun Greeting(name: String, modifier: Modifier = Modifier) {
        Column {
                Text(
                text = "Hello $name!",
                modifier = modifier
                )
            Text(
                    text = "Hello test",
                    modifier = modifier
                )
        }

    }
    @Preview
    @Composable
    fun SetupView(){
        Column{
            Image(painterResource(R.drawable.ic_launcher_foreground), null)
             Button(onClick = { /*TODO*/ }){
                 Text(stringResource(R.string.menu_starters))
             }
             Button(onClick = { /*TODO*/ }){
                 Text(stringResource(R.string.menu_main))
             }
             Button(onClick = { /*TODO*/ }){
                    Text(stringResource(R.string.menu_dessert))
                }
        }
    }
    @Composable
    fun GreetingPreview() {
        AndroidERestaurantTheme {
            Greeting("Android")
        }
    }
}