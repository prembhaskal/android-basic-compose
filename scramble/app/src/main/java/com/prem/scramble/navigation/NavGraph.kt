package com.prem.scramble.navigation
//
//import androidx.navigation.NavGraphBuilder
//import androidx.navigation.NavHostController
//import androidx.navigation.NavType
//import androidx.navigation.compose.composable
//import androidx.navigation.navArgument
//import com.prem.scramble.LevelSelectionScreen
//
//sealed class Screen(val route: String) {
//    object LevelSelection : Screen("level_selection")
//    object Game : Screen("game/{levelId}") {
//        fun createRoute(levelId: Int) = "game/$levelId"
//    }
//}
//
//fun NavGraphBuilder.addGameNavigation(
//    navController: NavHostController
//) {
//    composable(Screen.LevelSelection.route) {
//        LevelSelectionScreen(
//            onLevelSelected = { levelId ->
//                navController.navigate(Screen.Game.createRoute(levelId))
//            }
//        )
//    }
//
//    composable(
//        route = Screen.Game.route,
//        arguments = listOf(navArgument("levelId") { type = NavType.IntType })
//    ) { backStackEntry ->
//        val levelId = backStackEntry.arguments?.getInt("levelId") ?: 1
//        GameScreen(
//            levelId = levelId,
//            onNavigateBack = { navController.popBackStack() },
//            onLevelComplete = {
//                navController.popBackStack()
//            }
//        )
//    }
//}