package com.example.roza.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.roza.data.CartViewModel
import com.example.roza.ui.components.WhatsAppFAB
import com.example.roza.ui.screens.AboutScreen
import com.example.roza.ui.screens.CartScreen
import com.example.roza.ui.screens.HomeScreen
import com.example.roza.ui.screens.PoliciesScreen
import com.example.roza.ui.screens.ProductDetailScreen
import com.example.roza.ui.viewmodel.ProductViewModel

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Home : Screen("home", "Home", Icons.Default.Home)
    object About : Screen("about", "About", Icons.Default.Info)
    object Cart : Screen("cart", "Cart", Icons.Default.ShoppingCart)
    object ProductDetail : Screen("product_detail/{productId}", "Product Detail", Icons.Default.Home) {
        fun createRoute(productId: String) = "product_detail/$productId"
    }
    object Policies : Screen("policies", "Policies", Icons.Default.Info)
}

val BottomNavItems = listOf(
    Screen.Home,
    Screen.About,
    Screen.Cart
)

@Composable
fun RozaNavGraph(
    cartViewModel: CartViewModel,
    productViewModel: ProductViewModel,
    navController: NavHostController = rememberNavController()
) {
    val cartItems by cartViewModel.cartItems.collectAsState()
    val totalCartQuantity = cartItems.sumOf { it.quantity }

    Scaffold(
        bottomBar = {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            
            if (currentRoute in BottomNavItems.map { it.route }) {
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.onBackground
                ) {
                    BottomNavItems.forEach { screen ->
                        NavigationBarItem(
                            icon = {
                                if (screen == Screen.Cart && totalCartQuantity > 0) {
                                    BadgedBox(badge = { Badge { Text(totalCartQuantity.toString()) } }) {
                                        Icon(screen.icon, contentDescription = screen.title)
                                    }
                                } else {
                                    Icon(screen.icon, contentDescription = screen.title)
                                }
                            },
                            label = { Text(screen.title) },
                            selected = currentRoute == screen.route,
                            onClick = {
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                                selectedTextColor = MaterialTheme.colorScheme.primary,
                                indicatorColor = MaterialTheme.colorScheme.primary,
                                unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        )
                    }
                }
            }
        },
        floatingActionButton = {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            // Hide FAB on ProductDetail since it has its own prominent WhatsApp button
            if (currentRoute?.startsWith("product_detail") != true) {
                WhatsAppFAB()
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                val uiState by productViewModel.uiState.collectAsState()
                HomeScreen(
                    uiState = uiState,
                    onProductClick = { productId ->
                        navController.navigate(Screen.ProductDetail.createRoute(productId))
                    },
                    onAddToCart = { product ->
                        cartViewModel.addToCart(product)
                    }
                )
            }
            composable(Screen.About.route) {
                AboutScreen(
                    onViewPolicies = { navController.navigate(Screen.Policies.route) }
                )
            }
            composable(Screen.Cart.route) {
                CartScreen(
                    cartViewModel = cartViewModel,
                    onExplore = {
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Home.route) { inclusive = true }
                        }
                    }
                )
            }
            composable(Screen.ProductDetail.route) { backStackEntry ->
                val productId = backStackEntry.arguments?.getString("productId")
                val product = productViewModel.getProductById(productId ?: "")
                if (product != null) {
                    ProductDetailScreen(
                        product = product,
                        onBack = { navController.popBackStack() },
                        onAddToCart = { p, qty -> cartViewModel.addToCart(p, qty) }
                    )
                }
            }
            composable(Screen.Policies.route) {
                PoliciesScreen(onBack = { navController.popBackStack() })
            }
        }
    }
}
