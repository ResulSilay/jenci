package com.x.common.type

import androidx.annotation.Keep

@Keep
enum class BuildActionType(val value: String?) {
    NONE(null),
    STRING_PARAMETER("StringParameterDefinition"),
    BOOLEAN_PARAMETER("BooleanParameterDefinition"),
    TEXT_PARAMETER("TextParameterDefinition"),
    PASSWORD_PARAMETER("PasswordParameterDefinition"),
    PT_SINGLE_SELECT("PT_SINGLE_SELECT"),
    PT_MULTI_SELECT("PT_MULTI_SELECT"),
    PT_TEXTBOX("PT_TEXTBOX"),
    PT_CHECKBOX("PT_CHECKBOX"),
    PT_RADIO("PT_RADIO"),
    PT_JSON("PT_JSON"),
    PT_HIDDEN("PT_HIDDEN"),
    PT_MULTI_LEVEL_SINGLE_SELECT("PT_MULTI_LEVEL_SINGLE_SELECT"),
    PT_MULTI_LEVEL_MULTI_SELECT("PT_MULTI_LEVEL_MULTI_SELECT"),
    PT_BRANCH("PT_BRANCH");

    companion object {
        fun get(value: String?): BuildActionType? = values().firstOrNull { it.value == value }
    }
}