package com.x.common.type

import androidx.annotation.Keep

@Keep
enum class JobType(val value: String?) {
    NONE(null),
    FOLDER("com.cloudbees.hudson.plugins.folder.Folder"),
    WORKFLOW("org.jenkinsci.plugins.workflow.job.WorkflowJob"),
    FREE("hudson.model.FreeStyleProject");

    companion object {
        fun get(value: String?): JobType? = values().firstOrNull { it.value == value }
    }
}