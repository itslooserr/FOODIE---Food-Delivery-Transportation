package com.example.model

enum class ScreenTab {
    HOME,
    FOOD,
    GROCERY,
    RIDE
}

enum class ActiveScreen {
    MAIN_TAB,
    SEARCH,
    PROFILE,
    RESTAURANT_DETAIL,
    PRODUCT_DETAIL,
    CART,
    CHECKOUT,
    ORDER_CONFIRMATION,
    ORDER_TRACKING,
    ACTIVE_RIDE,
    ORDERS_LIST,
    SAVED_ADDRESSES
}

enum class ServiceType {
    FOOD,
    GROCERY,
    RIDE
}

data class Restaurant(
    val id: String,
    val name: String,
    val cuisine: String,
    val rating: Double,
    val ratingCount: Int,
    val deliveryTimeMins: Int,
    val deliveryFee: Int,
    val priceForTwo: Int,
    val imageUrl: String,
    val isPromoted: Boolean = false,
    val discountTag: String? = null,
    val address: String = "Connaught Place, New Delhi"
)

data class MenuItem(
    val id: String,
    val restaurantId: String,
    val name: String,
    val description: String,
    val price: Int,
    val isVeg: Boolean,
    val rating: Double,
    val imageUrl: String,
    val category: String,
    val isBestseller: Boolean = false
)

data class GroceryCategory(
    val id: String,
    val name: String,
    val iconName: String,
    val itemCount: Int
)

data class GroceryProduct(
    val id: String,
    val name: String,
    val unit: String,
    val price: Int,
    val originalPrice: Int,
    val discountPercent: Int,
    val categoryId: String,
    val imageUrl: String,
    val deliveryTimeMins: Int = 12,
    val rating: Double = 4.8,
    val description: String = "Freshly sourced daily and delivered in safe, insulated packaging."
)

enum class RideVehicleType {
    BIKE,
    AUTO,
    CAB_ECONOMY,
    CAB_PREMIUM
}

data class RideOption(
    val type: RideVehicleType,
    val title: String,
    val subtitle: String,
    val price: Int,
    val etaMinutes: Int,
    val distanceKm: Double,
    val capacity: String,
    val tag: String? = null
)

enum class RideStatus {
    CONFIRMING,
    DRIVER_ASSIGNED,
    ARRIVING,
    IN_TRANSIT,
    COMPLETED,
    CANCELLED
}

data class ActiveRideInfo(
    val rideId: String,
    val pickup: String,
    val destination: String,
    val vehicleType: RideVehicleType,
    val vehicleName: String,
    val vehicleNumber: String,
    val driverName: String,
    val driverRating: Double,
    val driverPhone: String,
    val otp: String,
    val fare: Int,
    val etaMins: Int,
    val distanceKm: Double,
    val status: RideStatus
)

data class CartItem(
    val id: String,
    val serviceType: ServiceType,
    val title: String,
    val subtitle: String,
    val price: Int,
    var quantity: Int,
    val imageUrl: String,
    val isVeg: Boolean? = null
)

enum class OrderStatus {
    PLACED,
    PREPARING,
    OUT_FOR_DELIVERY,
    DELIVERED
}

data class OrderReceipt(
    val orderId: String,
    val serviceType: ServiceType,
    val items: List<CartItem>,
    val subtotal: Int,
    val deliveryFee: Int,
    val taxes: Int,
    val discount: Int,
    val totalAmount: Int,
    val address: String,
    val paymentMethod: String,
    val orderTime: String,
    val estimatedArrival: String,
    val status: OrderStatus,
    val barcodeNumber: String
)

data class SavedAddress(
    val id: String,
    val label: String, // "Home", "Work", "Other"
    val addressLine: String,
    val landmark: String,
    val isDefault: Boolean = false
)

data class UserProfile(
    val name: String = "Laxmikant N.",
    val phone: String = "+91 98765 43210",
    val email: String = "n.laxmikant1418@gmail.com",
    val isPro: Boolean = true,
    val isLoggedIn: Boolean = true
)
