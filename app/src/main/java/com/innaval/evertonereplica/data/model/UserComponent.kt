package com.innaval.evertonereplica.data.model

import dagger.Component

@Component
interface UserComponent {

    fun getUser(): User
}