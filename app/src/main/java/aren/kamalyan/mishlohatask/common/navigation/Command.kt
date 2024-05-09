package aren.kamalyan.mishlohatask.common.navigation

import androidx.navigation.NavDirections

sealed interface Command {

    data object FinishAppCommand : Command

    class NavigateUpCommand(
        //NOTE: it depends how much is nested in the NavHost
        val nestedLevel: Int = 0
    ) : Command

    class NavCommand(
        val navDirections: NavDirections,
        //NOTE: it depends how much is nested in the NavHost
        val nestedLevel: Int = 0
    ) : Command
}