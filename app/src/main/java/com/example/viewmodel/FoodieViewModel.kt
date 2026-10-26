package com.example.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.FoodieMockData
import com.example.model.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class FoodieViewModel : ViewModel() {

    // Navigation State
    private val _currentTab = MutableStateFlow(ScreenTab.HOME)
    val currentTab: StateFlow<ScreenTab> = _currentTab.asStateFlow()

    private val _activeScreen = MutableStateFlow(ActiveScreen.MAIN_TAB)
    val activeScreen: StateFlow<ActiveScreen> = _activeScreen.asStateFlow()

    private val screenBackStack = mutableListOf<ActiveScreen>()

    // Current Delivery Location
    private val _currentLocation = MutableStateFlow("Sunshine Heights, Ring Road, Delhi")
    val currentLocation: StateFlow<String> = _currentLocation.asStateFlow()

    private val _isLocationPickerVisible = MutableStateFlow(false)
    val isLocationPickerVisible: StateFlow<Boolean> = _isLocationPickerVisible.asStateFlow()

    // Search
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    // Filters
    private val _selectedFoodCategory = MutableStateFlow("All")
    val selectedFoodCategory: StateFlow<String> = _selectedFoodCategory.asStateFlow()

    private val _isVegOnly = MutableStateFlow(false)
    val isVegOnly: StateFlow<Boolean> = _isVegOnly.asStateFlow()

    private val _selectedGroceryCategory = MutableStateFlow("cat_all")
    val selectedGroceryCategory: StateFlow<String> = _selectedGroceryCategory.asStateFlow()

    private val _grocerySortOption = MutableStateFlow("Popular")
    val grocerySortOption: StateFlow<String> = _grocerySortOption.asStateFlow()

    // Selected Details
    private val _selectedRestaurant = MutableStateFlow<Restaurant?>(null)
    val selectedRestaurant: StateFlow<Restaurant?> = _selectedRestaurant.asStateFlow()

    private val _selectedProduct = MutableStateFlow<GroceryProduct?>(null)
    val selectedProduct: StateFlow<GroceryProduct?> = _selectedProduct.asStateFlow()

    // Cart Management
    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems.asStateFlow()

    private val _appliedCoupon = MutableStateFlow<String?>("FOODIE50")
    val appliedCoupon: StateFlow<String?> = _appliedCoupon.asStateFlow()

    // Ride Booking State
    private val _pickupLocation = MutableStateFlow("Sunshine Heights, Ring Road")
    val pickupLocation: StateFlow<String> = _pickupLocation.asStateFlow()

    private val _dropLocation = MutableStateFlow("Cyber Hub, DLF Phase 2, Gurugram")
    val dropLocation: StateFlow<String> = _dropLocation.asStateFlow()

    private val _selectedRideType = MutableStateFlow(RideVehicleType.BIKE)
    val selectedRideType: StateFlow<RideVehicleType> = _selectedRideType.asStateFlow()

    private val _activeRide = MutableStateFlow<ActiveRideInfo?>(null)
    val activeRide: StateFlow<ActiveRideInfo?> = _activeRide.asStateFlow()

    private val _rideHistory = MutableStateFlow<List<ActiveRideInfo>>(emptyList())
    val rideHistory: StateFlow<List<ActiveRideInfo>> = _rideHistory.asStateFlow()

    private val _isCancelRideDialogVisible = MutableStateFlow(false)
    val isCancelRideDialogVisible: StateFlow<Boolean> = _isCancelRideDialogVisible.asStateFlow()

    // Orders & Tracking
    private val _ordersList = MutableStateFlow<List<OrderReceipt>>(emptyList())
    val ordersList: StateFlow<List<OrderReceipt>> = _ordersList.asStateFlow()

    private val _currentOrder = MutableStateFlow<OrderReceipt?>(null)
    val currentOrder: StateFlow<OrderReceipt?> = _currentOrder.asStateFlow()

    // User Profile & Auth
    private val _userProfile = MutableStateFlow(UserProfile())
    val userProfile: StateFlow<UserProfile> = _userProfile.asStateFlow()

    private val _isAuthModalVisible = MutableStateFlow(false)
    val isAuthModalVisible: StateFlow<Boolean> = _isAuthModalVisible.asStateFlow()

    private val _authMode = MutableStateFlow("OPTIONS") // OPTIONS, PHONE_OTP
    val authMode: StateFlow<String> = _authMode.asStateFlow()

    private val _phoneNumberInput = MutableStateFlow("")
    val phoneNumberInput: StateFlow<String> = _phoneNumberInput.asStateFlow()

    private val _otpInput = MutableStateFlow("")
    val otpInput: StateFlow<String> = _otpInput.asStateFlow()

    // Toast / Feedback
    private val _toastMessage = MutableStateFlow<String?>(null)
    val toastMessage: StateFlow<String?> = _toastMessage.asStateFlow()

    init {
        // Pre-populate demo initial order in order history
        val initialOrder = OrderReceipt(
            orderId = "FD-92041",
            serviceType = ServiceType.FOOD,
            items = listOf(
                CartItem(
                    id = "menu_101",
                    serviceType = ServiceType.FOOD,
                    title = "Hyderabadi Dum Biryani (Chicken)",
                    subtitle = "Biryani by Kilo",
                    price = 380,
                    quantity = 1,
                    imageUrl = "https://images.unsplash.com/photo-1563379091339-03b21ab4a4f8?w=600&auto=format&fit=crop&q=80",
                    isVeg = false
                )
            ),
            subtotal = 380,
            deliveryFee = 29,
            taxes = 19,
            discount = 50,
            totalAmount = 378,
            address = "Flat 402, Sunshine Heights, Outer Ring Road, New Delhi",
            paymentMethod = "Google Pay UPI",
            orderTime = "Yesterday, 8:42 PM",
            estimatedArrival = "Delivered in 28 mins",
            status = OrderStatus.DELIVERED,
            barcodeNumber = "0120034399434"
        )
        _ordersList.value = listOf(initialOrder)

        // Pre-populate sample ride in history
        val pastRide = ActiveRideInfo(
            rideId = "RD-4921",
            pickup = "Sunshine Heights, Ring Road",
            destination = "Rajiv Chowk Metro Gate 5",
            vehicleType = RideVehicleType.AUTO,
            vehicleName = "Bajaj RE Auto",
            vehicleNumber = "DL 1R 7734",
            driverName = "Suresh Verma",
            driverRating = 4.9,
            driverPhone = "+91 98112 34567",
            otp = "8912",
            fare = 85,
            etaMins = 0,
            distanceKm = 4.8,
            status = RideStatus.COMPLETED
        )
        _rideHistory.value = listOf(pastRide)
    }

    // Navigation Methods
    fun selectTab(tab: ScreenTab) {
        _currentTab.value = tab
        _activeScreen.value = ActiveScreen.MAIN_TAB
        screenBackStack.clear()
    }

    fun navigateTo(screen: ActiveScreen) {
        if (_activeScreen.value != screen) {
            screenBackStack.add(_activeScreen.value)
            _activeScreen.value = screen
        }
    }

    fun handleBack(): Boolean {
        if (_isLocationPickerVisible.value) {
            _isLocationPickerVisible.value = false
            return true
        }
        if (_isAuthModalVisible.value) {
            _isAuthModalVisible.value = false
            return true
        }
        if (_isCancelRideDialogVisible.value) {
            _isCancelRideDialogVisible.value = false
            return true
        }
        if (screenBackStack.isNotEmpty()) {
            _activeScreen.value = screenBackStack.removeAt(screenBackStack.size - 1)
            return true
        } else if (_activeScreen.value != ActiveScreen.MAIN_TAB) {
            _activeScreen.value = ActiveScreen.MAIN_TAB
            return true
        } else if (_currentTab.value != ScreenTab.HOME) {
            _currentTab.value = ScreenTab.HOME
            return true
        }
        return false
    }

    // Location
    fun setLocation(location: String) {
        _currentLocation.value = location
        _pickupLocation.value = location
        _isLocationPickerVisible.value = false
        showToast("Delivery address updated: $location")
    }

    fun toggleLocationPicker(show: Boolean) {
        _isLocationPickerVisible.value = show
    }

    // Search & Filter
    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun setFoodCategory(category: String) {
        _selectedFoodCategory.value = category
    }

    fun toggleVegOnly() {
        _isVegOnly.value = !_isVegOnly.value
    }

    fun setGroceryCategory(catId: String) {
        _selectedGroceryCategory.value = catId
    }

    fun setGrocerySortOption(option: String) {
        _grocerySortOption.value = option
    }

    // Detail Navigation
    fun openRestaurant(restaurant: Restaurant) {
        _selectedRestaurant.value = restaurant
        navigateTo(ActiveScreen.RESTAURANT_DETAIL)
    }

    fun openGroceryProduct(product: GroceryProduct) {
        _selectedProduct.value = product
        navigateTo(ActiveScreen.PRODUCT_DETAIL)
    }

    // Cart Actions
    fun addToCart(item: CartItem) {
        val currentList = _cartItems.value.toMutableList()
        val existingIndex = currentList.indexOfFirst { it.id == item.id }
        if (existingIndex != -1) {
            val existing = currentList[existingIndex]
            currentList[existingIndex] = existing.copy(quantity = existing.quantity + 1)
        } else {
            currentList.add(item.copy(quantity = 1))
        }
        _cartItems.value = currentList
        showToast("Added '${item.title}' to cart")
    }

    fun incrementCartItem(itemId: String) {
        val currentList = _cartItems.value.toMutableList()
        val index = currentList.indexOfFirst { it.id == itemId }
        if (index != -1) {
            val item = currentList[index]
            currentList[index] = item.copy(quantity = item.quantity + 1)
            _cartItems.value = currentList
        }
    }

    fun decrementCartItem(itemId: String) {
        val currentList = _cartItems.value.toMutableList()
        val index = currentList.indexOfFirst { it.id == itemId }
        if (index != -1) {
            val item = currentList[index]
            if (item.quantity > 1) {
                currentList[index] = item.copy(quantity = item.quantity - 1)
            } else {
                currentList.removeAt(index)
                showToast("Removed from cart")
            }
            _cartItems.value = currentList
        }
    }

    fun getCartItemQuantity(itemId: String): Int {
        return _cartItems.value.find { it.id == itemId }?.quantity ?: 0
    }

    fun clearCart() {
        _cartItems.value = emptyList()
    }

    fun applyCoupon(code: String) {
        if (code.equals("FOODIE50", ignoreCase = true)) {
            _appliedCoupon.value = "FOODIE50"
            showToast("Coupon FOODIE50 applied! Saved ₹50")
        } else if (code.equals("WELCOME", ignoreCase = true)) {
            _appliedCoupon.value = "WELCOME"
            showToast("Coupon WELCOME applied! Saved ₹100")
        } else {
            showToast("Invalid coupon code")
        }
    }

    fun removeCoupon() {
        _appliedCoupon.value = null
        showToast("Coupon removed")
    }

    // Calculations
    fun getCartSubtotal(): Int = _cartItems.value.sumOf { it.price * it.quantity }

    fun getDeliveryFee(): Int {
        val subtotal = getCartSubtotal()
        return if (subtotal >= 299 || subtotal == 0) 0 else 25
    }

    fun getTaxes(): Int {
        val subtotal = getCartSubtotal()
        return (subtotal * 0.05).toInt()
    }

    fun getDiscount(): Int {
        return when (_appliedCoupon.value) {
            "FOODIE50" -> if (getCartSubtotal() > 100) 50 else 0
            "WELCOME" -> if (getCartSubtotal() > 200) 100 else 0
            else -> 0
        }
    }

    fun getCartTotal(): Int {
        val total = getCartSubtotal() + getDeliveryFee() + getTaxes() - getDiscount()
        return total.coerceAtLeast(0)
    }

    // Checkout & Order Placement
    fun placeOrder(address: String, paymentMethod: String) {
        if (_cartItems.value.isEmpty()) {
            showToast("Cart is empty")
            return
        }

        val primaryService = _cartItems.value.firstOrNull()?.serviceType ?: ServiceType.FOOD
        val timeFormat = SimpleDateFormat("dd MMM yyyy • HH:mm", Locale.getDefault())
        val orderTimeStr = timeFormat.format(Date())
        val mockOrderId = "FD-" + (10000..99999).random()
        val mockBarcode = "0120" + (100000000..999999999).random()

        val receipt = OrderReceipt(
            orderId = mockOrderId,
            serviceType = primaryService,
            items = _cartItems.value.toList(),
            subtotal = getCartSubtotal(),
            deliveryFee = getDeliveryFee(),
            taxes = getTaxes(),
            discount = getDiscount(),
            totalAmount = getCartTotal(),
            address = address,
            paymentMethod = paymentMethod,
            orderTime = orderTimeStr,
            estimatedArrival = if (primaryService == ServiceType.GROCERY) "12-15 mins" else "25-30 mins",
            status = OrderStatus.PLACED,
            barcodeNumber = mockBarcode
        )

        _currentOrder.value = receipt
        _ordersList.value = listOf(receipt) + _ordersList.value
        _cartItems.value = emptyList()

        navigateTo(ActiveScreen.ORDER_CONFIRMATION)

        // Simulate order preparation and tracking
        viewModelScope.launch {
            delay(4000)
            if (_currentOrder.value?.orderId == receipt.orderId) {
                _currentOrder.value = _currentOrder.value?.copy(status = OrderStatus.PREPARING)
            }
            delay(5000)
            if (_currentOrder.value?.orderId == receipt.orderId) {
                _currentOrder.value = _currentOrder.value?.copy(status = OrderStatus.OUT_FOR_DELIVERY)
            }
        }
    }

    // Ride Booking
    fun setPickupLocation(location: String) {
        _pickupLocation.value = location
    }

    fun setDropLocation(location: String) {
        _dropLocation.value = location
    }

    fun swapPickupAndDrop() {
        val temp = _pickupLocation.value
        _pickupLocation.value = _dropLocation.value
        _dropLocation.value = temp
        showToast("Locations swapped")
    }

    fun selectRideType(type: RideVehicleType) {
        _selectedRideType.value = type
    }

    fun bookRide() {
        val rideType = _selectedRideType.value
        val options = FoodieMockData.getRideOptions()
        val selectedOption = options.find { it.type == rideType } ?: options.first()

        val driverNames = listOf("Ramesh Kumar", "Vikram Singh", "Amit Sharma", "Deepak Verma")
        val driverName = driverNames.random()
        val randomOtp = (1000..9999).random().toString()

        val (vehName, vehNumber) = when (rideType) {
            RideVehicleType.BIKE -> Pair("Bajaj Pulsar 150 (Black)", "DL 03 CJ 4819")
            RideVehicleType.AUTO -> Pair("Bajaj RE Compact Auto", "DL 1R 8832")
            RideVehicleType.CAB_ECONOMY -> Pair("Maruti Suzuki WagonR", "DL 2C AU 3918")
            RideVehicleType.CAB_PREMIUM -> Pair("Honda City (White)", "DL 1C PK 7291")
        }

        val active = ActiveRideInfo(
            rideId = "RD-" + (2000..9999).random(),
            pickup = _pickupLocation.value,
            destination = _dropLocation.value,
            vehicleType = rideType,
            vehicleName = vehName,
            vehicleNumber = vehNumber,
            driverName = driverName,
            driverRating = 4.9,
            driverPhone = "+91 98" + (10000000..99999999).random(),
            otp = randomOtp,
            fare = selectedOption.price,
            etaMins = selectedOption.etaMinutes,
            distanceKm = selectedOption.distanceKm,
            status = RideStatus.DRIVER_ASSIGNED
        )

        _activeRide.value = active
        _rideHistory.value = listOf(active) + _rideHistory.value
        navigateTo(ActiveScreen.ACTIVE_RIDE)
        showToast("Captain accepted! OTP is $randomOtp")

        // Simulate arrival progression
        viewModelScope.launch {
            delay(4000)
            if (_activeRide.value?.rideId == active.rideId) {
                _activeRide.value = _activeRide.value?.copy(status = RideStatus.ARRIVING, etaMins = 2)
            }
            delay(5000)
            if (_activeRide.value?.rideId == active.rideId) {
                _activeRide.value = _activeRide.value?.copy(status = RideStatus.IN_TRANSIT, etaMins = 14)
            }
        }
    }

    fun showCancelRideDialog(show: Boolean) {
        _isCancelRideDialogVisible.value = show
    }

    fun confirmCancelRide() {
        _isCancelRideDialogVisible.value = false
        val active = _activeRide.value
        if (active != null) {
            _activeRide.value = active.copy(status = RideStatus.CANCELLED)
            showToast("Ride cancelled")
            _activeRide.value = null
            _activeScreen.value = ActiveScreen.MAIN_TAB
            _currentTab.value = ScreenTab.RIDE
        }
    }

    // Profile & Auth
    fun showAuthModal(show: Boolean) {
        _isAuthModalVisible.value = show
        _authMode.value = "OPTIONS"
    }

    fun setAuthMode(mode: String) {
        _authMode.value = mode
    }

    fun updatePhoneInput(phone: String) {
        _phoneNumberInput.value = phone
    }

    fun updateOtpInput(otp: String) {
        _otpInput.value = otp
    }

    fun loginWithGoogle() {
        _userProfile.value = UserProfile(
            name = "Laxmikant N.",
            phone = "+91 98765 43210",
            email = "n.laxmikant1418@gmail.com",
            isPro = true,
            isLoggedIn = true
        )
        _isAuthModalVisible.value = false
        showToast("Signed in as Laxmikant N. with Google")
    }

    fun loginWithApple() {
        _userProfile.value = UserProfile(
            name = "Reginald Apple",
            phone = "+91 98123 45678",
            email = "reginald@icloud.com",
            isPro = true,
            isLoggedIn = true
        )
        _isAuthModalVisible.value = false
        showToast("Signed in with Apple")
    }

    fun loginWithPhone() {
        val phone = _phoneNumberInput.value.ifBlank { "+91 98765 43210" }
        _userProfile.value = UserProfile(
            name = "Foodie User",
            phone = phone,
            email = "user@foodie.in",
            isPro = true,
            isLoggedIn = true
        )
        _isAuthModalVisible.value = false
        _phoneNumberInput.value = ""
        _otpInput.value = ""
        showToast("Logged in with phone $phone")
    }

    fun signOut() {
        _userProfile.value = _userProfile.value.copy(isLoggedIn = false)
        showToast("Signed out successfully")
    }

    fun showToast(msg: String) {
        _toastMessage.value = msg
        viewModelScope.launch {
            delay(3000)
            if (_toastMessage.value == msg) {
                _toastMessage.value = null
            }
        }
    }

    fun dismissToast() {
        _toastMessage.value = null
    }
}
