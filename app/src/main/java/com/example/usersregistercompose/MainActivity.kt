package com.example.usersregistercompose

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.usersregistercompose.data.UserViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: UserViewModel = viewModel(
                factory = ViewModelProvider.AndroidViewModelFactory(application)
            )
            UserApp(viewModel)
        }
    }
}

@Composable
fun UserApp(viewModel: UserViewModel) {
    val inputName = viewModel.inputName
    val inputEmail = viewModel.inputEmail
    val users = viewModel.ListofUsers
    val selectedUser = viewModel.selectedUser
    val context = LocalContext.current

    Box(modifier = Modifier.fillMaxSize()) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
                .padding(top = 25.dp)
                .padding(bottom = 70.dp)
        ) {
            // User name input
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 5.dp)
            ) {
                OutlinedTextField(
                    value = inputName,
                    onValueChange = { text ->
                        viewModel.onNameChange(text)
                    },
                    label = {
                        Text(
                            "User's name",
                            fontSize = 20.sp
                        )
                    },
                    modifier = Modifier.weight(1f)
                )
            }

            // User email input

            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = inputEmail,
                    onValueChange = { text ->
                        viewModel.onEmailChange(text)
                    },
                    label = {
                        Text(
                            "User's email",
                            fontSize = 20.sp
                        )
                    },
                    modifier = Modifier.weight(1f)
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, bottom = 10.dp)
            ) {
                // Save/Update buttons

                Button(
                    onClick = {
                        if (selectedUser == null) {
                            if (inputName.isNotBlank() && inputEmail.isNotBlank()) {
                                viewModel.addUser()
                                Toast.makeText(context, "New User Added", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(context, "Please enter user name & email", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            viewModel.updateUser()
                            Toast.makeText(context, "User Updated", Toast.LENGTH_SHORT).show()
                        }
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text(if (selectedUser == null) "Save" else "Update", fontSize = 21.sp)
                }

                Spacer(modifier = Modifier.width(16.dp))

                // Clear/Delete buttons

                Button(
                    onClick = {
                        if (selectedUser == null) {
                            viewModel.clearFields()
                        } else {
                            viewModel.deleteUser()
                            Toast.makeText(context, "User Deleted", Toast.LENGTH_SHORT).show()
                        }
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text(if (selectedUser == null) "Clear" else "Delete", fontSize = 21.sp)
                }
            }

            // Scrollable User List

            LazyColumn(modifier = Modifier.fillMaxSize().weight(1f)) {
                items(users) { currentUser ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(7.dp)
                            .clickable { viewModel.selectUser(currentUser) },
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF0F0F0))
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = currentUser.name,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = currentUser.email,
                                fontSize = 17.sp
                            )
                        }
                    }
                }
            }
        }

        // Cancel button (fixed at the bottom of the screen)
        if (selectedUser != null) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(15.dp)
            ) {
                Button(
                    onClick = {
                        viewModel.clearSelection()
                    },
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp)
                ) {
                    Text(
                        "Cancel",
                        fontSize = 21.sp
                    )
                }
            }
        }
    }
}