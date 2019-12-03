package com.example.izbela.shared

import com.example.izbela.api.response.UserResponse
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

class UserCache @Inject constructor() : IUserCache {

    override fun newUser(userResponse: UserResponse) {
        user.onNext(userResponse)
    }

    override val user = BehaviorSubject.create<UserResponse>()

}

interface IUserCache {

    fun newUser(userResponse: UserResponse)

    val user: Observable<UserResponse>
}