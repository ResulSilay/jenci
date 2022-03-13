package com.x.presentation.scene.main.job.build.action

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.x.common.type.BuildActionType
import com.x.domain.model.JobActionModel
import com.x.presentation.R
import com.x.presentation.ui.composable.CustomCheckbox
import com.x.presentation.ui.composable.CustomRadioButton
import com.x.presentation.ui.composable.CustomTextField
import com.x.presentation.ui.composable.RowCenter
import com.x.presentation.ui.theme.Theme
import com.x.presentation.ui.theme.backgroundPrimary
import com.x.presentation.util.getString
import com.x.presentation.util.toFieldEmptyString

@ExperimentalMaterialApi
@Composable
fun JobBuildBottomSheet(
    jobActionModel: JobActionModel?,
    bottomSheetState: BottomSheetScaffoldState,
    content: @Composable () -> Unit,
    onDismiss: () -> Unit,
    onBuild: (fieldMap: Map<String, String>) -> Unit,
) {
    val fieldMap = HashMap<String, String>()
    val parameters = jobActionModel?.parameterDefinitions
    val fieldInitValues = mutableListOf<String>()
    val booleanFieldTrueValue = R.string.parameter_boolean_true.getString()
    val fieldValueStateList = remember {
        mutableStateListOf<String>()
    }

    fieldValueStateList.clear()
    fieldInitValues.clear()

    parameters?.forEachIndexed { _, jobActionParameterModel ->
        fieldInitValues.add(
            jobActionParameterModel.defaultParameterValue?.value.toString()
        )
    }
    fieldValueStateList.addAll(fieldInitValues)

    BottomSheetScaffold(
        scaffoldState = bottomSheetState,
        sheetContent = {
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .padding(
                        all = Theme.dimens.space.medium
                    )
                    .fillMaxWidth()
            ) {
                item {
                    Text(
                        text = R.string.build_sheet_title.getString(),
                        modifier = Modifier.padding(
                            bottom = Theme.dimens.space.medium
                        )
                    )

                    parameters?.forEachIndexed { index, parameterModel ->
                        Row(modifier = Modifier
                            .fillMaxSize()
                            .padding(
                                top = Theme.dimens.space.min,
                                bottom = Theme.dimens.space.min
                            )
                        ) {
                            when (parameterModel.type) {
                                BuildActionType.NONE -> {

                                }
                                BuildActionType.STRING_PARAMETER,
                                BuildActionType.TEXT_PARAMETER,
                                BuildActionType.PT_SINGLE_SELECT,
                                BuildActionType.PT_TEXTBOX,
                                BuildActionType.PT_BRANCH,
                                -> {
                                    CustomTextField(
                                        label = parameterModel.name.toFieldEmptyString(),
                                        value = fieldValueStateList[index],
                                        onValueChange = {
                                            fieldValueStateList[index] = it
                                        }
                                    )
                                }
                                BuildActionType.PASSWORD_PARAMETER -> {
                                    CustomTextField(
                                        label = parameterModel.name.toFieldEmptyString(),
                                        value = fieldValueStateList[index],
                                        visualTransformation = PasswordVisualTransformation(),
                                        onValueChange = {
                                            fieldValueStateList[index] = it
                                        }
                                    )
                                }
                                BuildActionType.BOOLEAN_PARAMETER,
                                BuildActionType.PT_CHECKBOX,
                                -> {
                                    RowCenter {
                                        CustomCheckbox(
                                            checked = fieldValueStateList[index] == booleanFieldTrueValue,
                                            onCheckedChange = {
                                                fieldValueStateList[index] = it.toString()
                                            }
                                        )
                                        Text(
                                            text = parameterModel.name.toFieldEmptyString()
                                        )
                                    }
                                }
                                BuildActionType.PT_RADIO -> {
                                    RowCenter {
                                        CustomRadioButton(
                                            selected = fieldValueStateList[index] == booleanFieldTrueValue,
                                            onClick = {
                                                fieldValueStateList[index] = booleanFieldTrueValue
                                            }
                                        )
                                        Text(
                                            text = parameterModel.name.toFieldEmptyString()
                                        )
                                    }
                                }
                                BuildActionType.PT_JSON -> {
                                    CustomTextField(
                                        label = parameterModel.name.toFieldEmptyString(),
                                        value = fieldValueStateList[index],
                                        onValueChange = {
                                            fieldValueStateList[index] = it
                                        },
                                        singleLine = true,
                                        maxLines = 3,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(80.dp)
                                    )
                                }
                                BuildActionType.PT_HIDDEN -> {

                                }
                                BuildActionType.PT_MULTI_SELECT -> {

                                }
                                BuildActionType.PT_MULTI_LEVEL_SINGLE_SELECT -> {

                                }
                                BuildActionType.PT_MULTI_LEVEL_MULTI_SELECT -> {

                                }
                                else -> {}
                            }
                        }
                    }

                    Button(
                        onClick = {
                            fieldMap.clear()
                            fieldValueStateList.forEachIndexed { index, value ->
                                fieldMap[parameters?.get(index)?.name.toString()] = value
                            }
                            onBuild(fieldMap)
                        },
                        modifier = Modifier
                            .padding(
                                top = Theme.dimens.space.large
                            )
                            .fillMaxSize()
                    ) {
                        Text(
                            text = R.string.build_sheet_action_btn_text.getString()
                        )
                    }

                    if (bottomSheetState.bottomSheetState.isExpanded) {
                        BackHandler(enabled = true) {
                            onDismiss()
                        }
                    }
                }
            }
        },
        sheetPeekHeight = Theme.dimens.none.default,
        sheetShape = RoundedCornerShape(
            topEnd = Theme.dimens.space.min,
            topStart = Theme.dimens.space.min
        ),
        sheetBackgroundColor = backgroundPrimary,
        backgroundColor = Color.Transparent
    ) {
        content()
    }
}