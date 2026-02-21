package com.code4galaxy.graphqlspacex.presentation.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.code4galaxy.graphqlspacex.presentation.theme.GraphQLSpaceXTheme
import com.code4galaxy.graphqlspacex.presentation.viewmodel.SpaceXViewModel
import dagger.hilt.android.AndroidEntryPoint
import androidx.core.net.toUri

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GraphQLSpaceXTheme {
                ShowSpaceXData()
            }
        }
    }
}

//use case should fetch data from repository
@Composable
fun ShowSpaceXData(viewModel: SpaceXViewModel = viewModel()) {
    val context = LocalContext.current
    val satellites by viewModel.graphQLResponseModel.collectAsState()
    satellites?.let { satellitesList ->
        Column(Modifier.fillMaxSize().padding(WindowInsets.systemBars.asPaddingValues())) {
            LazyColumn(modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 8.dp)) {
                items(satellitesList) { satellite ->
                    satellite?.let {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(text = "Mission Name: ${it.missionName}", style = MaterialTheme.typography.titleMedium)
                                Text(text = "Launch Date: ${it.launchDate}", style = MaterialTheme.typography.bodyMedium)
                                Text(text = "Launch Site: ${it.launchSite}", style = MaterialTheme.typography.bodyMedium)
                                Text(text = "Rocket Name: ${it.rocketName}", style = MaterialTheme.typography.bodyMedium)

                                Spacer(modifier = Modifier.height(8.dp))

                                it.articleLink?.let { link ->
                                    Text(
                                        text = "Read Article",
                                        color = Color.Blue,
                                        style = MaterialTheme.typography.bodyMedium,
                                        modifier = Modifier.clickable {
                                            val intent = Intent(Intent.ACTION_VIEW, link.toUri())
                                            context.startActivity(intent)
                                        }
                                    )
                                }

                                it.videoLink?.let { link ->
                                    Text(
                                        text = "Watch Video",
                                        color = Color.Blue,
                                        style = MaterialTheme.typography.bodyMedium,
                                        modifier = Modifier
                                            .padding(top = 4.dp)
                                            .clickable {
                                                val intent = Intent(Intent.ACTION_VIEW, link.toUri())
                                                context.startActivity(intent)
                                            }
                                    )
                                }
                            }
                        }
                    }
                }
            }
            Button(
                onClick = {
                    val intent = Intent(context, AddUserActivity::class.java)
                    context.startActivity(intent)
                },
                modifier = Modifier.fillMaxWidth().wrapContentSize()
            ) {
                Text(text = "Click here to add new user")
            }
        }

    }
}