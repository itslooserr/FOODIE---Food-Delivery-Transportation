package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.FoodieMockData
import com.example.model.*
import com.example.ui.components.*
import com.example.ui.screens.*
import com.example.ui.theme.*
import com.example.viewmodel.FoodieViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                FoodieApp()
            }
        }
    }
}

@Composable
fun FoodieApp(viewModel: FoodieViewModel = viewModel()) {
    val currentTab by viewModel.currentTab.collectAsStateWithLifecycle()
    val activeScreen by viewModel.activeScreen.collectAsStateWithLifecycle()
    val currentLocation by viewModel.currentLocation.collectAsStateWithLifecycle()
    val isLocationPickerVisible by viewModel.isLocationPickerVisible.collectAsStateWithLifecycle()

    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val selectedFoodCategory by viewModel.selectedFoodCategory.collectAsStateWithLifecycle()
    val isVegOnly by viewModel.isVegOnly.collectAsStateWithLifecycle()
    val selectedGroceryCategory by viewModel.selectedGroceryCategory.collectAsStateWithLifecycle()
    val grocerySortOption by viewModel.grocerySortOption.collectAsStateWithLifecycle()

    val selectedRestaurant by viewModel.selectedRestaurant.collectAsStateWithLifecycle()
    val selectedProduct by viewModel.selectedProduct.collectAsStateWithLifecycle()

    val cartItems by viewModel.cartItems.collectAsStateWithLifecycle()
    val appliedCoupon by viewModel.appliedCoupon.collectAsStateWithLifecycle()

    val pickupLocation by viewModel.pickupLocation.collectAsStateWithLifecycle()
    val dropLocation by viewModel.dropLocation.collectAsStateWithLifecycle()
    val selectedRideType by viewModel.selectedRideType.collectAsStateWithLifecycle()
    val activeRide by viewModel.activeRide.collectAsStateWithLifecycle()
    val rideHistory by viewModel.rideHistory.collectAsStateWithLifecycle()
    val isCancelRideDialogVisible by viewModel.isCancelRideDialogVisible.collectAsStateWithLifecycle()

    val ordersList by viewModel.ordersList.collectAsStateWithLifecycle()
    val currentOrder by viewModel.currentOrder.collectAsStateWithLifecycle()

    val userProfile by viewModel.userProfile.collectAsStateWithLifecycle()
    val isAuthModalVisible by viewModel.isAuthModalVisible.collectAsStateWithLifecycle()
    val phoneInput by viewModel.phoneNumberInput.collectAsStateWithLifecycle()
    val otpInput by viewModel.otpInput.collectAsStateWithLifecycle()

    val toastMessage by viewModel.toastMessage.collectAsStateWithLifecycle()

    val cartCount = remember(cartItems) { cartItems.sumOf { it.quantity } }
    val cartTotal = remember(cartItems, appliedCoupon) { viewModel.getCartTotal() }

    // Intercept hardware back button
    BackHandler(enabled = true) {
        viewModel.handleBack()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                if (activeScreen == ActiveScreen.MAIN_TAB) {
                    FoodieTopHeader(
                        currentLocation = currentLocation,
                        onLocationClick = { viewModel.toggleLocationPicker(true) },
                        onSearchClick = { viewModel.navigateTo(ActiveScreen.SEARCH) },
                        onNotificationClick = { viewModel.showToast("No new notifications") },
                        onProfileClick = { viewModel.navigateTo(ActiveScreen.PROFILE) },
                        modifier = Modifier.statusBarsPadding()
                    )
                }
            },
            bottomBar = {
                if (activeScreen == ActiveScreen.MAIN_TAB) {
                    Column {
                        // Floating cart banner appears above bottom bar when items are in cart
                        FloatingCartBanner(
                            cartCount = cartCount,
                            cartTotal = cartTotal,
                            onViewCartClick = { viewModel.navigateTo(ActiveScreen.CART) }
                        )

                        FoodieBottomBar(
                            currentTab = currentTab,
                            onTabSelected = { tab -> viewModel.selectTab(tab) },
                            cartCount = cartCount
                        )
                    }
                }
            },
            contentWindowInsets = WindowInsets(0, 0, 0, 0)
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                when (activeScreen) {
                    ActiveScreen.MAIN_TAB -> {
                        when (currentTab) {
                            ScreenTab.HOME -> {
                                HomeScreen(
                                    onNavigateToTab = { tab -> viewModel.selectTab(tab) },
                                    onRestaurantClick = { rest -> viewModel.openRestaurant(rest) },
                                    onGroceryClick = { prod -> viewModel.openGroceryProduct(prod) },
                                    onAddToCart = { item -> viewModel.addToCart(item) },
                                    onIncrementCart = { id -> viewModel.incrementCartItem(id) },
                                    onDecrementCart = { id -> viewModel.decrementCartItem(id) },
                                    getCartQty = { id -> viewModel.getCartItemQuantity(id) },
                                    onQuickRideDestination = { loc -> viewModel.setDropLocation(loc) },
                                    cartItems = cartItems
                                )
                            }
                            ScreenTab.FOOD -> {
                                FoodScreen(
                                    selectedCategory = selectedFoodCategory,
                                    onCategorySelect = { cat -> viewModel.setFoodCategory(cat) },
                                    isVegOnly = isVegOnly,
                                    onToggleVegOnly = { viewModel.toggleVegOnly() },
                                    onRestaurantClick = { rest -> viewModel.openRestaurant(rest) }
                                )
                            }
                            ScreenTab.GROCERY -> {
                                GroceryScreen(
                                    selectedCategory = selectedGroceryCategory,
                                    onCategorySelect = { catId -> viewModel.setGroceryCategory(catId) },
                                    sortOption = grocerySortOption,
                                    onSortSelect = { opt -> viewModel.setGrocerySortOption(opt) },
                                    onProductClick = { prod -> viewModel.openGroceryProduct(prod) },
                                    cartItems = cartItems,
                                    onAddToCart = { item -> viewModel.addToCart(item) },
                                    onIncrementCart = { id -> viewModel.incrementCartItem(id) },
                                    onDecrementCart = { id -> viewModel.decrementCartItem(id) }
                                )
                            }
                            ScreenTab.RIDE -> {
                                RideScreen(
                                    pickupLocation = pickupLocation,
                                    onPickupChange = { viewModel.setPickupLocation(it) },
                                    dropLocation = dropLocation,
                                    onDropChange = { viewModel.setDropLocation(it) },
                                    onSwapLocations = { viewModel.swapPickupAndDrop() },
                                    selectedRideType = selectedRideType,
                                    onSelectRideType = { viewModel.selectRideType(it) },
                                    onBookRide = { viewModel.bookRide() },
                                    recentLocations = FoodieMockData.recentRideLocations,
                                    onSelectRecentLocation = { viewModel.setDropLocation(it) }
                                )
                            }
                        }
                    }

                    ActiveScreen.PROFILE -> {
                        ProfileScreen(
                            userProfile = userProfile,
                            onBackClick = { viewModel.handleBack() },
                            onOpenAuthModal = { viewModel.showAuthModal(true) },
                            onSignOut = { viewModel.signOut() },
                            onViewOrders = { viewModel.navigateTo(ActiveScreen.ORDERS_LIST) },
                            onViewRides = {
                                viewModel.selectTab(ScreenTab.RIDE)
                            },
                            onViewAddresses = { viewModel.toggleLocationPicker(true) },
                            ordersCount = ordersList.size,
                            ridesCount = rideHistory.size
                        )
                    }

                    ActiveScreen.SEARCH -> {
                        SearchScreen(
                            query = searchQuery,
                            onQueryChange = { viewModel.updateSearchQuery(it) },
                            onBackClick = { viewModel.handleBack() },
                            onRestaurantClick = { rest -> viewModel.openRestaurant(rest) },
                            onProductClick = { prod -> viewModel.openGroceryProduct(prod) },
                            onRideDestination = { dest ->
                                viewModel.setDropLocation(dest)
                                viewModel.selectTab(ScreenTab.RIDE)
                            },
                            onAddToCart = { item -> viewModel.addToCart(item) }
                        )
                    }

                    ActiveScreen.RESTAURANT_DETAIL -> {
                        selectedRestaurant?.let { rest ->
                            RestaurantDetailScreen(
                                restaurant = rest,
                                onBackClick = { viewModel.handleBack() },
                                cartItems = cartItems,
                                onAddToCart = { item -> viewModel.addToCart(item) },
                                onIncrementCart = { id -> viewModel.incrementCartItem(id) },
                                onDecrementCart = { id -> viewModel.decrementCartItem(id) }
                            )
                        }
                    }

                    ActiveScreen.PRODUCT_DETAIL -> {
                        selectedProduct?.let { prod ->
                            GroceryProductDetailScreen(
                                product = prod,
                                onBackClick = { viewModel.handleBack() },
                                cartItems = cartItems,
                                onAddToCart = { item -> viewModel.addToCart(item) },
                                onIncrementCart = { id -> viewModel.incrementCartItem(id) },
                                onDecrementCart = { id -> viewModel.decrementCartItem(id) }
                            )
                        }
                    }

                    ActiveScreen.CART -> {
                        CartScreen(
                            cartItems = cartItems,
                            onIncrementItem = { id -> viewModel.incrementCartItem(id) },
                            onDecrementItem = { id -> viewModel.decrementCartItem(id) },
                            subtotal = viewModel.getCartSubtotal(),
                            deliveryFee = viewModel.getDeliveryFee(),
                            taxes = viewModel.getTaxes(),
                            discount = viewModel.getDiscount(),
                            total = viewModel.getCartTotal(),
                            appliedCoupon = appliedCoupon,
                            onApplyCoupon = { code -> viewModel.applyCoupon(code) },
                            onRemoveCoupon = { viewModel.removeCoupon() },
                            onProceedToCheckout = { viewModel.navigateTo(ActiveScreen.CHECKOUT) },
                            onBackClick = { viewModel.handleBack() },
                            onExploreServices = {
                                viewModel.selectTab(ScreenTab.HOME)
                                viewModel.handleBack()
                            }
                        )
                    }

                    ActiveScreen.CHECKOUT -> {
                        CheckoutScreen(
                            currentLocation = currentLocation,
                            totalAmount = viewModel.getCartTotal(),
                            onPlaceOrder = { address, paymentMethod ->
                                viewModel.placeOrder(address, paymentMethod)
                            },
                            onBackClick = { viewModel.handleBack() }
                        )
                    }

                    ActiveScreen.ORDER_CONFIRMATION -> {
                        currentOrder?.let { order ->
                            OrderSuccessScreen(
                                order = order,
                                onTrackOrder = { viewModel.navigateTo(ActiveScreen.ORDER_TRACKING) },
                                onBackHome = {
                                    viewModel.selectTab(ScreenTab.HOME)
                                }
                            )
                        }
                    }

                    ActiveScreen.ORDER_TRACKING -> {
                        currentOrder?.let { order ->
                            OrderTrackingScreen(
                                order = order,
                                onBackClick = { viewModel.handleBack() }
                            )
                        }
                    }

                    ActiveScreen.ACTIVE_RIDE -> {
                        activeRide?.let { ride ->
                            ActiveRideScreen(
                                rideInfo = ride,
                                onBackClick = { viewModel.handleBack() },
                                onCancelClick = { viewModel.showCancelRideDialog(true) }
                            )
                        }
                    }

                    ActiveScreen.ORDERS_LIST -> {
                        OrdersListScreen(
                            orders = ordersList,
                            onBackClick = { viewModel.handleBack() }
                        )
                    }

                    else -> {}
                }
            }
        }

        // Location Picker Sheet
        LocationPickerSheet(
            isOpen = isLocationPickerVisible,
            onDismiss = { viewModel.toggleLocationPicker(false) },
            currentLocation = currentLocation,
            onSelectAddress = { addr -> viewModel.setLocation(addr) }
        )

        // Auth Dialog (Google, Apple, Phone OTP as requested)
        FoodieAuthDialog(
            isOpen = isAuthModalVisible,
            onDismiss = { viewModel.showAuthModal(false) },
            onGoogleLogin = { viewModel.loginWithGoogle() },
            onAppleLogin = { viewModel.loginWithApple() },
            phoneInput = phoneInput,
            onPhoneChange = { viewModel.updatePhoneInput(it) },
            otpInput = otpInput,
            onOtpChange = { viewModel.updateOtpInput(it) },
            onPhoneSubmit = { viewModel.loginWithPhone() }
        )

        // Cancel Ride Confirmation Dialog
        if (isCancelRideDialogVisible) {
            AlertDialog(
                onDismissRequest = { viewModel.showCancelRideDialog(false) },
                title = { Text("Cancel Ride?", fontWeight = FontWeight.Bold) },
                text = { Text("Are you sure you want to cancel? Your captain is already on the way.") },
                confirmButton = {
                    Button(
                        onClick = { viewModel.confirmCancelRide() },
                        colors = ButtonDefaults.buttonColors(containerColor = FoodieError)
                    ) {
                        Text("Yes, Cancel", color = Color.White)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { viewModel.showCancelRideDialog(false) }) {
                        Text("No, Keep Ride", color = FoodieTextPrimary)
                    }
                },
                containerColor = Color.White,
                shape = RoundedCornerShape(16.dp)
            )
        }

        // Animated In-App Toast
        AnimatedVisibility(
            visible = toastMessage != null,
            enter = fadeIn() + slideInVertically(initialOffsetY = { -it }),
            exit = fadeOut() + slideOutVertically(targetOffsetY = { -it }),
            modifier = Modifier
                .align(Alignment.TopCenter)
                .statusBarsPadding()
                .padding(top = 8.dp, start = 16.dp, end = 16.dp)
        ) {
            toastMessage?.let { msg ->
                Surface(
                    shape = RoundedCornerShape(24.dp),
                    color = FoodieTextPrimary,
                    shadowElevation = 8.dp,
                    modifier = Modifier.testTag("app_toast")
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = null,
                            tint = FoodiePrimary,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = msg,
                            color = Color.White,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}
