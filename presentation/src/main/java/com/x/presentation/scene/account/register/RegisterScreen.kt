package com.x.presentation.scene.account.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.hilt.navigation.compose.hiltViewModel
import com.x.domain.model.AccountModel
import com.x.presentation.R
import com.x.presentation.scene.account.register.RegisterContract.Event
import com.x.presentation.ui.composable.*
import com.x.presentation.ui.composable.EmptyStates.FailureEmptyState
import com.x.presentation.ui.composable.EmptyStates.NetworkEmptyState
import com.x.presentation.ui.composable.EmptyStates.NotFoundEmptyState
import com.x.presentation.ui.theme.Theme
import com.x.presentation.util.getString
import com.x.presentation.util.validationParams

@ExperimentalMaterial3Api
@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel = hiltViewModel(),
    accountId: Int? = null,
    navigateUp: () -> Unit,
) {
    val state = viewModel.state.collectAsState()
    val isSaveResource = state.value.isSave
    val accountModelResource = state.value.accountModel

    val name = remember { mutableStateOf(TextFieldValue()) }
    val host = remember { mutableStateOf(TextFieldValue()) }
    val username = remember { mutableStateOf(TextFieldValue()) }
    val password = remember { mutableStateOf(TextFieldValue()) }
    val passwordVisibility = remember { mutableStateOf(false) }
    val nameError = remember { mutableStateOf(false) }
    val hostError = remember { mutableStateOf(false) }
    val usernameError = remember { mutableStateOf(false) }
    val passwordError = remember { mutableStateOf(false) }

    DisposableEffect(key1 = accountId) {
        if (accountId != null) {
            viewModel.publish(event = Event.GetAccount(accountId = accountId))
        }
        onDispose { }
    }

    RegisterBody(
        navigateUp = navigateUp,
        content = {
            RegisterContent(
                name = name,
                nameError = nameError,
                host = host,
                hostError = hostError,
                username = username,
                usernameError = usernameError,
                password = password,
                passwordError = passwordError,
                passwordVisibility = passwordVisibility
            )
        },
        footer = {
            RegisterFooter {
                nameError.value = name.value.text.isEmpty()
                hostError.value = host.value.text.isEmpty()
                usernameError.value = username.value.text.isEmpty()
                passwordError.value = password.value.text.isEmpty()

                if (!validationParams(nameError, hostError, usernameError, passwordError))
                    return@RegisterFooter

                viewModel.publish(
                    event = Event.SaveAccount(
                        accountModel = AccountModel().apply {
                            this.accountId = accountId
                            this.name = name.value.text
                            this.host = host.value.text
                            this.username = username.value.text
                            this.password = password.value.text
                        }
                    )
                )
            }
        }
    )

    Resource(
        resource = isSaveResource,
        onSuccess = {
            navigateUp()
        },
        notFound = {
            NotFoundEmptyState()
        },
        onLoading = {
            CircularLoading()
        },
        onNetwork = {
            NetworkEmptyState()
        },
        onFailure = {
            FailureEmptyState()
        }
    )

    Resource(
        resource = accountModelResource,
        onSuccess = { accountModel ->
            name.value = TextFieldValue(accountModel?.name.toString())
            host.value = TextFieldValue(accountModel?.host.toString())
            username.value = TextFieldValue(accountModel?.username.toString())
            password.value = TextFieldValue(accountModel?.password.toString())
        },
        notFound = {
            NotFoundEmptyState()
        },
        onLoading = {
            CircularLoading()
        },
        onNetwork = {
            NetworkEmptyState()
        },
        onFailure = {
            FailureEmptyState(
                onTryAgainActionClick = {
                    if (accountId != null) {
                        viewModel.publish(event = Event.GetAccount(accountId = accountId))
                    }
                }
            )
        }
    )
}

@ExperimentalMaterial3Api
@Composable
fun RegisterBody(
    navigateUp: () -> Unit,
    content: @Composable () -> Unit,
    footer: @Composable () -> Unit,
) {
    Scaffold(
        topBar = {
            MainAppBar(
                navigateUp = {
                    navigateUp()
                }
            )
        },
        content = {
            content()
        },
        bottomBar = {
            footer()
        }
    )
}

@Composable
fun RegisterContent(
    name: MutableState<TextFieldValue>,
    nameError: MutableState<Boolean>,
    host: MutableState<TextFieldValue>,
    hostError: MutableState<Boolean>,
    username: MutableState<TextFieldValue>,
    usernameError: MutableState<Boolean>,
    password: MutableState<TextFieldValue>,
    passwordError: MutableState<Boolean>,
    passwordVisibility: MutableState<Boolean>,
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(Theme.dimens.space.small),
        modifier = Modifier
            .padding(all = Theme.dimens.space.medium)
    ) {
        item {
            Column(
                modifier = Modifier.padding(
                    start = Theme.dimens.space.min,
                    end = Theme.dimens.space.min,
                    bottom = Theme.dimens.space.large
                )
            ) {
                Text(
                    text = R.string.register_welcome.getString(),
                    style = Theme.typography.headlineMedium
                )
                Text(
                    text = R.string.register_desc.getString()
                )
            }

            CustomTextField(
                text = name,
                label = R.string.register_input_name,
                validation = Validation(
                    isError = nameError,
                    errorMessage = R.string.validation_register_name_error_message.getString()
                ),
                trailingIcon = { },
                keyboardActions = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        bottom = Theme.dimens.space.medium
                    )
            )

            CustomTextField(
                text = host,
                label = R.string.register_input_host,
                validation = Validation(
                    isError = hostError,
                    errorMessage = R.string.validation_register_host_error_message.getString()
                ),
                trailingIcon = {},
                keyboardActions = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        bottom = Theme.dimens.space.medium
                    )
            )

            CustomTextField(
                text = username,
                label = R.string.register_input_username,
                validation = Validation(
                    isError = usernameError,
                    errorMessage = R.string.validation_register_username_error_message.getString(),
                ),
                trailingIcon = {},
                keyboardActions = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        bottom = Theme.dimens.space.medium
                    )
            )

            CustomTextField(
                text = password,
                label = R.string.register_input_password,
                validation = Validation(
                    isError = passwordError,
                    errorMessage = R.string.validation_register_password_error_message.getString()
                ),
                visualTransformation = if (passwordVisibility.value) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val image = if (passwordVisibility.value) Icons.Filled.Visibility else Icons.Filled.VisibilityOff

                    IconButton(
                        onClick = {
                            passwordVisibility.value = !passwordVisibility.value
                        }
                    ) {
                        Icon(
                            imageVector = image,
                            contentDescription = R.string.register_input_visibility_password_desc.getString()
                        )
                    }
                },
                keyboardActions = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        bottom = Theme.dimens.space.medium
                    )
            )
        }
    }
}

@Composable
fun RegisterFooter(
    onSaveClick: () -> Unit,
) {
    Button(
        onClick = { onSaveClick() },
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                all = Theme.dimens.space.medium
            )
    ) {
        Text(
            text = R.string.register_account_save.getString()
        )
    }
}