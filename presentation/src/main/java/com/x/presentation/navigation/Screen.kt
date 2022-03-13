package com.x.presentation.navigation

sealed class Screen(
    val route: String,
) {
    object Account {
        object Action {
            object AccountRegisterScreen : Screen(route = "account/action")
            object AccountEditScreen : Screen(route = "account/action/${Arguments.accountId}")
            object Arguments {
                const val accountId = "{accountId}"
            }
        }

        object Login {
            object LoginScreen : Screen(route = "account/login")
        }
    }

    object Main {
        object DashboardScreen : Screen(route = "dashboard")

        object Node {
            object NodeScreen : Screen(route = "node")
            object NodeDetailScreen : Screen(route = "node/detail/${Arguments.nodeLabel}")
            object Arguments {
                const val nodeLabel = "{nodeLabel}"
            }

            object Values {
                const val master = "master"
            }
        }

        object ViewScreen : Screen(route = "view")
        object Job {
            object JobListScreen : Screen(route = "jobs/${Arguments.jobUrl}")
            object JobDetailScreen : Screen(route = "job/${Arguments.jobUrl}")
            object JobBuildScreen : Screen(route = "job/${Arguments.jobUrl}/${Arguments.buildId}")
            object Arguments {
                const val jobUrl = "{jobUrl}"
                const val buildId = "{buildId}"
            }
        }

        object QueueScreen : Screen(route = "queue")
        object SettingsScreen : Screen(route = "settings")
        object ProfileScreen : Screen(route = "profile")
        object PeopleScreen : Screen(route = "people")
    }
}