package com.stslex.core_navigation

enum class AppDestination(
    private vararg val argsNames: String
) {
    HOME("home");

    val navigationRoute: String
        get() = "$route${argsNames.argumentsRoute}"

    private val route: String
        get() = StringBuilder()
            .append(name, SEPARATOR_ROUTE, TAG_ROUTE)
            .toString()
            .lowercase()

    private val Array<out String>.argumentsRoute: String
        get() = if (isEmpty()) {
            String()
        } else {
            joinToString(separator = "}/{", prefix = "/{", postfix = "}")
        }

    companion object {
        private const val SEPARATOR_ROUTE = "_"
        private const val TAG_ROUTE = "route"
    }
}