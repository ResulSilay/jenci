package com.x.presentation.scene.main.people

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.x.domain.model.PeopleModel
import com.x.domain.model.PeopleUserItemModel
import com.x.presentation.R
import com.x.presentation.scene.main.people.PeopleContract.Event
import com.x.presentation.ui.composable.CircularLoading
import com.x.presentation.ui.composable.CustomDivider
import com.x.presentation.ui.composable.EmptyStates.FailureEmptyState
import com.x.presentation.ui.composable.EmptyStates.NetworkEmptyState
import com.x.presentation.ui.composable.EmptyStates.NotFoundEmptyState
import com.x.presentation.ui.composable.MainAppBar
import com.x.presentation.ui.composable.Resource
import com.x.presentation.ui.theme.Theme
import com.x.presentation.util.getString
import com.x.presentation.util.toFieldEmptyString

@ExperimentalMaterial3Api
@ExperimentalPagerApi
@Composable
fun PeopleScreen(
    viewModel: PeopleViewModel = hiltViewModel(),
    navigateUp: () -> Unit,
) {
    val state = viewModel.state.collectAsState()
    val peopleModelResource = state.value.peopleModel

    Resource(
        resource = peopleModelResource,
        onSuccess = { peopleModel ->
            PeopleBody(
                peopleModel = peopleModel,
                navigateUp = navigateUp
            )
        },
        notFound = {
            NotFoundEmptyState()
        },
        onLoading = {
            CircularLoading()
        },
        onNetwork = {
            NetworkEmptyState(
                onTryAgainActionClick = {
                    viewModel.publish(event = Event.GetPeople)
                }
            )
        },
        onFailure = {
            FailureEmptyState(
                onTryAgainActionClick = {
                    viewModel.publish(event = Event.GetPeople)
                }
            )
        }
    )
}

@ExperimentalMaterial3Api
@Composable
fun PeopleBody(
    peopleModel: PeopleModel?,
    navigateUp: () -> Unit,
) {
    Scaffold(
        topBar = {
            MainAppBar(
                title = R.string.people_header_text.getString(),
                backgroundColor = Theme.colors.onSecondary,
                navigateUp = navigateUp
            )
        },
        content = {
            peopleModel?.users?.let { peopleList ->
                PeopleContent(
                    peopleList = peopleList
                )
            }
        }
    )
}

@Composable
fun PeopleContent(
    peopleList: List<PeopleUserItemModel>,
) {
    Card(
        shape = Theme.shapes.none,
        elevation = Theme.dimens.elevation.medium,
        backgroundColor = Theme.colors.onSecondary,
        modifier = Modifier.paddingFromBaseline(
            bottom = Theme.dimens.space.medium
        )
    ) {
        LazyColumn {
            items(peopleList) { item ->
                PeopleItem(
                    label = item.fullName.toFieldEmptyString(),
                    value = item.project?.name.toFieldEmptyString()
                )
            }
        }
    }
}

@Composable
fun PeopleItem(
    label: String,
    value: String?,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { }
    ) {
        Column(
            modifier = Modifier
                .padding(
                    start = Theme.dimens.space.medium,
                    end = Theme.dimens.space.medium,
                    top = Theme.dimens.space.small,
                    bottom = Theme.dimens.space.medium
                )
        ) {
            Text(
                text = "$label:",
                color = Theme.colors.secondary
            )
            Text(
                text = value.toFieldEmptyString()
            )
        }
        CustomDivider()
    }
}