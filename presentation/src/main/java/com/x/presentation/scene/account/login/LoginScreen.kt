package com.x.presentation.scene.account.login

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.*
import com.x.domain.model.AccountModel
import com.x.presentation.R
import com.x.presentation.scene.account.login.LoginContract.Event
import com.x.presentation.ui.composable.CircularLoading
import com.x.presentation.ui.composable.CustomAlertDialog
import com.x.presentation.ui.composable.CustomDivider
import com.x.presentation.ui.composable.EmptyStates.CustomEmptyState
import com.x.presentation.ui.composable.EmptyStates.FailureEmptyState
import com.x.presentation.ui.composable.EmptyStates.NetworkEmptyState
import com.x.presentation.ui.composable.EmptyStates.NotFoundEmptyState
import com.x.presentation.ui.composable.Resource
import com.x.presentation.ui.theme.Theme
import com.x.presentation.util.getString
import kotlin.math.absoluteValue

@ExperimentalMaterialApi
@ExperimentalMaterial3Api
@ExperimentalFoundationApi
@ExperimentalPagerApi
@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    navigateAccountRegister: () -> Unit,
    navigateAccountEditAction: (accountModel: AccountModel) -> Unit,
    startMainActivity: () -> Unit,
) {
    val state = viewModel.state.collectAsState()
    val accountResource = state.value.accountModel
    val accountListResource = state.value.accountList
    val accountRemoveResource = state.value.accountRemove
    val removeAccountId = remember { mutableStateOf(-1) }

    DisposableEffect(Unit) {
        viewModel.publish(event = Event.GetAccountListEvent)
        onDispose {}
    }

    Resource(
        resource = accountResource,
        onSuccess = { account ->
            LaunchedEffect(Unit) {
                val accountModel: AccountModel? = viewModel.cacheManager().getModel(AccountModel::class)
                accountModel?.authToken = account?.authToken
                accountModel?.let { account ->
                    viewModel.cacheManager().setModel(account)
                }
                startMainActivity()
            }
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
            CustomEmptyState(
                title = R.string.empty_states_login_failure_title,
                description = R.string.empty_states_login_failure_desc,
                image = R.drawable.ic_empty_states_error,
                actionText = R.string.empty_states_login_failure_action_text,
                onActionClick = {
                    viewModel.publish(event = Event.GetAccountListEvent)
                }
            )
        }
    )

    Resource(
        resource = accountListResource,
        onSuccess = { accountList ->
            LoginBody(
                accountList = accountList,
                onAccountClick = { accountItem ->
                    val accountModel = AccountModel().apply {
                        name = accountItem.name
                        host = accountItem.host
                        username = accountItem.username
                        password = accountItem.password
                    }
                    viewModel.cacheManager().setModel(accountItem)
                    viewModel.publish(event = Event.LoginEvent(account = accountModel))
                },
                onAccountEdit = { account ->
                    navigateAccountEditAction(account)
                },
                onAccountRemove = {
                    it.accountId?.let { id ->
                        removeAccountId.value = id
                    }
                },
                navigateAccountRegister = navigateAccountRegister
            )
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
        resource = accountRemoveResource,
        onSuccess = {
            DisposableEffect(key1 = viewModel) {
                viewModel.publish(event = Event.GetAccountListEvent)
                onDispose { }
            }
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

    if (removeAccountId.value > 0) {
        AccountRemoveDialog(
            onConfirm = {
                viewModel.publish(event = Event.AccountRemoveEvent(accountId = removeAccountId.value))
                removeAccountId.value = -1
            },
            onDismiss = {
                removeAccountId.value = -1
            }
        )
    }
}

@ExperimentalPagerApi
@ExperimentalMaterialApi
@ExperimentalMaterial3Api
@Composable
fun LoginBody(
    accountList: List<AccountModel>?,
    onAccountClick: (accountModel: AccountModel) -> Unit,
    onAccountEdit: (accountModel: AccountModel) -> Unit,
    onAccountRemove: (accountModel: AccountModel) -> Unit,
    navigateAccountRegister: () -> Unit,
) {
    Scaffold(
        topBar = {
            LoginHeader()
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier.padding(
                    paddingValues = paddingValues
                )
            ) {
                LoginContent(
                    accountList = accountList,
                    onAccountClick = onAccountClick,
                    onAccountEdit = onAccountEdit,
                    onAccountRemove = onAccountRemove,
                    navigateAccountRegister = navigateAccountRegister
                )
            }
        },
        bottomBar = {
            LoginFooter(
                navigateAccountRegister = navigateAccountRegister
            )
        }
    )
}

@ExperimentalPagerApi
@ExperimentalMaterialApi
@Composable
fun LoginContent(
    accountList: List<AccountModel>?,
    onAccountClick: (accountModel: AccountModel) -> Unit,
    onAccountEdit: (accountModel: AccountModel) -> Unit,
    onAccountRemove: (accountModel: AccountModel) -> Unit,
    navigateAccountRegister: () -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column {
            LazyColumn {
                item {
                    accountList?.let { accounts ->
                        if (accounts.isNotEmpty()) {
                            LoginAccountPager(
                                accountList = accounts,
                                onAccountClick = onAccountClick,
                                onAccountEdit = onAccountEdit,
                                onAccountRemove = onAccountRemove
                            )
                        } else {
                            LoginAccountEmptyState {
                                navigateAccountRegister()
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun LoginHeader() {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_jenci_logo),
            contentDescription = R.string.job_health_report_icon_desc.getString(),
            modifier = Modifier
                .size(100.dp)
                .padding(
                    top = Theme.dimens.space.xlarge
                )
        )
        Text(
            text = R.string.login_header_text.getString(),
            style = Theme.typography.titleLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(
                start = Theme.dimens.space.min,
                end = Theme.dimens.space.min,
                top = Theme.dimens.space.medium,
                bottom = Theme.dimens.space.min
            )
        )
    }
}

@Composable
fun LoginFooter(
    navigateAccountRegister: () -> Unit,
) {
    Column {
        CustomDivider(
            color = Theme.colors.outline
        )
        Card(
            shape = Theme.shapes.none,
            elevation = Theme.dimens.elevation.none,
            backgroundColor = Theme.colors.background,
            modifier = Modifier
                .fillMaxWidth()
                .paddingFromBaseline(
                    top = Theme.dimens.space.medium,
                )
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        all = Theme.dimens.space.medium
                    )
            ) {
                TextButton(
                    onClick = {
                        navigateAccountRegister()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = Theme.dimens.space.large,
                            end = Theme.dimens.space.large,
                        )
                ) {
                    Text(
                        text = R.string.login_footer_register.getString(),
                        color = Theme.colors.onSurface
                    )
                }
            }
        }
    }
}

@Composable
fun LoginAccountEmptyState(
    onAccountCreateClick: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = R.string.login_account_not_found_empty_state_text.getString(),
            modifier = Modifier.padding(
                all = Theme.dimens.space.medium
            )
        )
        OutlinedButton(
            onClick = { onAccountCreateClick() },
            modifier = Modifier.padding(
                all = Theme.dimens.space.medium
            )
        ) {
            Text(
                text = R.string.login_account_create_empty_state_btn_text.getString()
            )
        }
    }
}

@ExperimentalMaterialApi
@ExperimentalPagerApi
@Composable
fun LoginAccountPager(
    accountList: List<AccountModel>,
    onAccountClick: (accountModel: AccountModel) -> Unit,
    onAccountEdit: (accountModel: AccountModel) -> Unit,
    onAccountRemove: (accountModel: AccountModel) -> Unit,
) {
    val pagerState = rememberPagerState(
        initialPage = 0,
    )
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(
            bottom = Theme.dimens.space.xxlarge
        )
    ) {

        HorizontalPager(
            count = accountList.size,
            state = pagerState
        ) { page ->
            LoginAccountItem(
                accountModel = accountList[page],
                onAccountClick = onAccountClick,
                onAccountEdit = onAccountEdit,
                onAccountRemove = onAccountRemove,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = Theme.dimens.space.large,
                        end = Theme.dimens.space.large,
                        top = Theme.dimens.space.large,
                        bottom = Theme.dimens.space.large
                    )
                    .graphicsLayer {
                        val pageOffset = calculateCurrentOffsetForPage(page = page).absoluteValue
                        lerp(
                            start = 0.85f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        ).also { scale ->
                            scaleX = scale
                            scaleY = scale
                        }

                        alpha = lerp(
                            start = 0.5f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        )
                    }
            )
        }

        HorizontalPagerIndicator(
            pagerState = pagerState,
            activeColor = Theme.colors.primary,
            inactiveColor = Theme.colors.surfaceVariant,
            modifier = Modifier.padding(
                all = Theme.dimens.space.small
            ),
        )
    }
}

@ExperimentalMaterialApi
@Composable
fun LoginAccountItem(
    accountModel: AccountModel,
    onAccountClick: (accountModel: AccountModel) -> Unit,
    onAccountEdit: (accountModel: AccountModel) -> Unit,
    onAccountRemove: (accountModel: AccountModel) -> Unit,
    modifier: Modifier,
) {
    Card(
        shape = Theme.shapes.large,
        elevation = Theme.dimens.elevation.small,
        backgroundColor = Theme.colors.onSecondary,
        border = BorderStroke(
            width = 1.dp,
            color = Theme.colors.secondaryContainer
        ),
        modifier = modifier
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .paddingFromBaseline(
                    bottom = Theme.dimens.space.largeExtra
                )
                .clickable {
                    onAccountClick(accountModel)
                }
        ) {
            Column(
                modifier = Modifier
                    .padding(
                        all = Theme.dimens.space.small
                    )
            ) {
                Column {
                    Text(
                        text = accountModel.name.toString(),
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(
                                top = Theme.dimens.space.medium
                            )
                            .fillMaxWidth()
                    )
                }

                Card(
                    shape = Theme.shapes.xLargeExtra,
                    backgroundColor = Theme.colors.outline,
                    elevation = Theme.dimens.elevation.none,
                    border = BorderStroke(
                        width = 1.dp,
                        color = Theme.colors.secondaryContainer
                    ),
                    modifier = Modifier
                        .padding(
                            start = Theme.dimens.space.medium,
                            end = Theme.dimens.space.medium,
                            top = Theme.dimens.space.medium,
                            bottom = Theme.dimens.space.small,
                        )
                        .fillMaxWidth()
                ) {
                    Text(
                        text = accountModel.host.toString(),
                        color = Theme.colors.onSurfaceVariant,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(
                            all = Theme.dimens.space.medium
                        )
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    AccountCircleIconButton(
                        imageVector = Icons.Filled.Edit,
                        onClick = {
                            onAccountEdit(accountModel)
                        }
                    )
                    AccountCircleIconButton(
                        imageVector = Icons.Filled.Remove,
                        onClick = {
                            onAccountRemove(accountModel)
                        }
                    )
                    AccountCircleIconButton(
                        imageVector = Icons.Filled.Lock,
                        isVisible = accountModel.isSSL == true,
                        onClick = {

                        }
                    )
                }
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun AccountCircleIconButton(
    imageVector: ImageVector,
    isVisible: Boolean = true,
    onClick: () -> Unit,
) {
    if (isVisible) {
        Card(
            shape = Theme.shapes.xLargeExtra,
            backgroundColor = Theme.colors.outline,
            elevation = Theme.dimens.elevation.none,
            onClick = {
                onClick()
            },
            modifier = Modifier
                .padding(
                    start = Theme.dimens.space.small,
                    end = Theme.dimens.space.small,
                    top = Theme.dimens.space.small,
                    bottom = Theme.dimens.space.small,
                )
        ) {
            Icon(
                imageVector = imageVector,
                contentDescription = R.string.login_action_icon_content_description.getString(),
                tint = Theme.colors.onSecondaryContainer,
                modifier = Modifier
                    .padding(
                        all = Theme.dimens.space.smallExtra
                    )
                    .size(
                        size = Theme.dimens.vector.small
                    )
            )
        }
    }
}

@Composable
fun AccountRemoveDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
) {
    CustomAlertDialog(
        title = R.string.account_remove_dialog_title,
        message = R.string.account_remove_dialog_desc,
        confirmText = R.string.dialog_yes,
        dismissText = R.string.dialog_cancel,
        onConfirm = { onConfirm() },
        onDismiss = { onDismiss() }
    )
}

@ExperimentalPagerApi
@ExperimentalMaterialApi
@ExperimentalMaterial3Api
@Preview
@Composable
fun LoginContentPreview() {
    val accountList = ArrayList<AccountModel>().apply {
        this.add(
            AccountModel(
                accountId = 1,
                name = "Jenkins1",
                host = "http://localhost:8080",
                username = "Name",
                password = "123456"
            )
        )
    }
    LoginBody(
        accountList = accountList,
        onAccountClick = {

        },
        onAccountEdit = {

        },
        onAccountRemove = {

        },
        navigateAccountRegister = {

        }
    )
}