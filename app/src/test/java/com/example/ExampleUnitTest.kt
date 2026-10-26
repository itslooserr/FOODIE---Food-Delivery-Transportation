package com.example

import com.example.data.FoodieMockData
import com.example.model.CartItem
import com.example.model.RideVehicleType
import com.example.model.ServiceType
import com.example.viewmodel.FoodieViewModel
import org.junit.Assert.*
import org.junit.Test

class ExampleUnitTest {

    @Test
    fun testCartCalculationsAndCoupon() {
        val vm = FoodieViewModel()
        vm.clearCart()

        assertEquals(0, vm.cartItems.value.size)
        assertEquals(0, vm.getCartSubtotal())

        val biryani = CartItem(
            id = "menu_101",
            serviceType = ServiceType.FOOD,
            title = "Hyderabadi Dum Biryani",
            subtitle = "Biryani by Kilo",
            price = 380,
            quantity = 1,
            imageUrl = ""
        )

        vm.addToCart(biryani)
        assertEquals(1, vm.cartItems.value.size)
        assertEquals(380, vm.getCartSubtotal())

        // Subtotal > 299 -> delivery fee 0
        assertEquals(0, vm.getDeliveryFee())

        // Coupon FOODIE50 gives ₹50 discount
        vm.applyCoupon("FOODIE50")
        assertEquals(50, vm.getDiscount())

        val total = vm.getCartTotal()
        // 380 subtotal + 0 delivery + 19 taxes (5%) - 50 discount = 349
        assertEquals(349, total)

        // Increment quantity
        vm.incrementCartItem("menu_101")
        assertEquals(2, vm.getCartItemQuantity("menu_101"))
        assertEquals(760, vm.getCartSubtotal())

        // Decrement quantity
        vm.decrementCartItem("menu_101")
        assertEquals(1, vm.getCartItemQuantity("menu_101"))

        vm.decrementCartItem("menu_101")
        assertEquals(0, vm.cartItems.value.size)
    }

    @Test
    fun testRideOptionsAndBooking() {
        val vm = FoodieViewModel()
        val options = FoodieMockData.getRideOptions(distanceKm = 5.0)

        assertTrue(options.isNotEmpty())
        val bike = options.find { it.type == RideVehicleType.BIKE }
        assertNotNull(bike)
        assertTrue(bike!!.price > 0)

        vm.selectRideType(RideVehicleType.BIKE)
        vm.bookRide()

        val active = vm.activeRide.value
        assertNotNull(active)
        assertEquals(RideVehicleType.BIKE, active!!.vehicleType)
        assertEquals(4, active.otp.length)
        assertTrue(active.driverName.isNotBlank())
    }

    @Test
    fun testOrderPlacement() {
        val vm = FoodieViewModel()
        vm.clearCart()

        val milk = CartItem(
            id = "groc_3",
            serviceType = ServiceType.GROCERY,
            title = "Amul Taaza Milk",
            subtitle = "500 ml",
            price = 27,
            quantity = 2,
            imageUrl = ""
        )
        vm.addToCart(milk)

        vm.placeOrder(
            address = "Flat 402, Sunshine Heights",
            paymentMethod = "Google Pay UPI"
        )

        val currentOrder = vm.currentOrder.value
        assertNotNull(currentOrder)
        assertTrue(currentOrder!!.orderId.startsWith("FD-"))
        assertEquals("Google Pay UPI", currentOrder.paymentMethod)
        assertEquals(0, vm.cartItems.value.size) // Cart emptied after placement
    }
}
