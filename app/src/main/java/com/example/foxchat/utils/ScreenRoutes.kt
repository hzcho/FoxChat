package com.example.foxchat.utils

object ScreenRoutes {
    //Screen arguments
    const val PERSONS_ARG="persons_arg"
    const val CHAT_ARG="chat_arg"

    //Screens
    const val SPLASH_SCREEN="splash_screen"
    const val SIGN_IN_SCREEN="sign_in_screen"
    const val SIGN_UP_SCREEN="sign_up_screen"
    const val MAIN_SCREEN="main_screen"
    const val SELECT_PERSON_SCREEN="select_person_screen"
    const val CREATE_CHAT_SCREEN="create_chat_screen?$PERSONS_ARG={$PERSONS_ARG}"
    const val CHAT_SCREEN="chat_screen?$CHAT_ARG={$CHAT_ARG}"
    const val CHATS_SCREEN="chats_screen"
    const val PEOPLE_SCREEN="people_screen"
    const val PROFILE_SCREEN="profile_screen"

    //NavGraph
    const val AUTH_NAV_GRAPH="auth_nav"
    const val CHAT_GRAPH="chat_graph"

    fun String.addArguments(args:Map<String, String>):String{
        return args.map { pair->
            this.replace(oldValue = "{${pair.key}}", newValue = pair.value)
        }.joinToString()
    }
}
