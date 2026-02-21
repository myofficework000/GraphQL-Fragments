package com.code4galaxy.graphqlspacex.presentation.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.code4galaxy.graphqlspacex.presentation.theme.GraphQLSpaceXTheme
import com.code4galaxy.graphqlspacex.presentation.viewmodel.AddUserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddUserActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GraphQLSpaceXTheme {
                AddUserData()
            }
        }
    }
}
@Composable
fun AddUserData(viewModel: AddUserViewModel = viewModel()) {
    var name by remember { mutableStateOf("") }
    val responseObj by viewModel.graphQLResponseModel.collectAsState()

    Column(
        modifier = Modifier
            .padding(WindowInsets.systemBars.asPaddingValues())
            .padding(16.dp)
            .fillMaxSize()
            .imePadding(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth(),
        )
        Button(
            onClick = {
                viewModel.addNewUser(name)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Add User")
        }

        responseObj?.let { users ->
            if (users == 200) {
                AlertDialog(
                    onDismissRequest = { viewModel.clearResponse() },
                    confirmButton = {
                        TextButton(onClick = { viewModel.clearResponse() }) {
                            Text("OK")
                        }
                    },
                    title = { Text("Server Response") },
                    text = {
                        Text(text = "Added user")
                    }
                )
            }
        }
    }

}

