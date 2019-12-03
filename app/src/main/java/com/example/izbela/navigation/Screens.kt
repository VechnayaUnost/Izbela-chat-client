package com.example.izbela.navigation

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.example.izbela.api.response.ChatResponse
import com.example.izbela.presentation.auth.login.LoginFragment
import com.example.izbela.presentation.auth.registration.RegistrationFragment
import com.example.izbela.presentation.chat.chat.ChatFragment
import com.example.izbela.presentation.chat.chatlist.ChatListFragment
import com.example.izbela.presentation.chat.createchat.CreateChatFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

class Screens {

    class LoginScreen : SupportAppScreen() {
        override fun getFragment(): Fragment {
            return LoginFragment()
        }
    }

    class RegistrationScreen : SupportAppScreen() {
        override fun getFragment(): Fragment {
            return RegistrationFragment()
        }
    }

    class ChatScreen(private val chatResponse: ChatResponse) : SupportAppScreen() {
        override fun getFragment(): Fragment {
            val fragment = ChatFragment()
            fragment.arguments = bundleOf(
                ChatResponse::class.java.name to chatResponse
            )
            return fragment
        }
    }

    class ChatListScreen : SupportAppScreen() {
        override fun getFragment(): Fragment {
            return ChatListFragment()
        }
    }

    class CreateChatScreen : SupportAppScreen() {
        override fun getFragment(): Fragment {
            return CreateChatFragment()
        }
    }
}