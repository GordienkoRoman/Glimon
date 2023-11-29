package stud.gilmon.presentation.ui.main

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.dog_observer.viewModelFactory.ViewModelFactory
import com.google.gson.Gson
import stud.gilmon.data.local.entities.UsersEntity
import stud.gilmon.data.remote.UnsplashImages
import stud.gilmon.presentation.ui.Screen
import stud.gilmon.presentation.ui.Screen.Companion.KEY_AUTH_USER
import stud.gilmon.presentation.ui.Screen.Companion.KEY_FEED_ITEM_INDEX
import stud.gilmon.presentation.ui.feed.FeedItemScreen.FeedItemScreen
import stud.gilmon.presentation.ui.feed.FeedScreen
import stud.gilmon.presentation.ui.feed.FeedSearchScreen
import stud.gilmon.presentation.ui.profile.ProfileScreen
import stud.gilmon.presentation.ui.support.ContactSupportScreen
import stud.gilmon.presentation.ui.support.SupportScreen

@Composable
fun MainScreenNavGraph(
    darkTheme: Boolean,
    photos: List<UnsplashImages>,
    navController: NavHostController,
    paddingValues: PaddingValues,
    user: MutableState<UsersEntity>,
    toggleTheme: () -> Unit,
    viewModelFactory: ViewModelFactory
) {
    NavHost(
        modifier = Modifier,
        navController = navController,
        startDestination = Screen.Feed.route,
        route = Screen.Home.route
    ) {
        navigation(route = Screen.Feed.route,
            startDestination = Screen.FeedMain.route){
            composable(route = Screen.FeedMain.route)
            {
                FeedScreen(photos,viewModelFactory,
                    user.value,
                    onSearckClick = {navController.navigate(Screen.FeedSearch.route) }
                ) { navController.navigate(Screen.FeedItem.route + "/" + it) }
            }
            composable(route = Screen.FeedSearch.route,
                enterTransition = {
                    fadeIn(
                        animationSpec = tween(
                            300, easing = LinearEasing
                        )
                    ) + slideIntoContainer(
                        animationSpec = tween(300, easing = EaseIn),
                        towards = AnimatedContentTransitionScope.SlideDirection.Start
                    )
                },
                exitTransition = {
                    fadeOut(
                        animationSpec = tween(
                            300, easing = LinearEasing
                        )
                    ) + slideOutOfContainer(
                        animationSpec = tween(300, easing = EaseOut),
                        towards = AnimatedContentTransitionScope.SlideDirection.End
                    )
                }
            )
            {
                FeedSearchScreen(viewModelFactory,photos)
            }
            composable(route ="${Screen.FeedItem.route}/{$KEY_FEED_ITEM_INDEX}",
                enterTransition = {
                    fadeIn(
                        animationSpec = tween(
                            300, easing = LinearEasing
                        )
                    ) + slideIntoContainer(
                        animationSpec = tween(300, easing = EaseIn),
                        towards = AnimatedContentTransitionScope.SlideDirection.Start
                    )
                },
                exitTransition = {
                    fadeOut(
                        animationSpec = tween(
                            300, easing = LinearEasing
                        )
                    ) + slideOutOfContainer(
                        animationSpec = tween(300, easing = EaseOut),
                        towards = AnimatedContentTransitionScope.SlideDirection.End
                    )
                }
            )
            {
                val index = it.arguments?.getString(KEY_FEED_ITEM_INDEX) ?: ""
                FeedItemScreen(user.value,viewModelFactory,photos[index.toInt()])
            }
        }

        navigation(route = Screen.Support.route,
        startDestination = Screen.SupportMain.route){
            composable(route = Screen.SupportMain.route) {
                SupportScreen() {navController.navigate(Screen.ContactSupport.route) }
            }
            composable(route = Screen.ContactSupport.route,
                enterTransition = {
                    fadeIn(
                        animationSpec = tween(
                            300, easing = LinearEasing
                        )
                    ) + slideIntoContainer(
                        animationSpec = tween(300, easing = EaseIn),
                        towards = AnimatedContentTransitionScope.SlideDirection.Start
                    )
                }) {
                ContactSupportScreen(user.value) { navController.navigate(Screen.SupportMain.route) }
            }
        }

        composable(route = "${Screen.Profile.route}/{$KEY_AUTH_USER}") {
            val userJson = it.arguments?.getString(KEY_AUTH_USER) ?: "404"
            user.value = Gson().fromJson(userJson, UsersEntity::class.java)
            //val user = remember { mutableStateOf(Gson().fromJson(userJson, UsersEntity::class.java)) }
            ProfileScreen(
                darkTheme, user = user,
                toggleTheme = toggleTheme,
            ) { navController.navigate(Screen.FeedMain.route) }
        }
    }
}

