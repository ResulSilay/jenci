package com.x.presentation.scene.main.people

import com.x.presentation.scene.main.people.PeopleContract.*
import com.x.presentation.scene.main.people.PeopleContract.Event.*

fun PeopleViewModel.reduceEvent(
    event: Event,
) {
    when (event) {
        is GetPeople -> {
            getPeople()
        }
    }
}