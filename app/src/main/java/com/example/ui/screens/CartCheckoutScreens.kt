package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.FoodieMockData
import com.example.model.*
import com.example.ui.components.TicketReceiptCard
import com.example.ui.components.VegNonVegBadge
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    cartItems: List<CartItem>,
    onIncrementItem: (String) -> Unit,
    onDecrementItem: (String) -> Unit,
    subtotal: Int,
    deliveryFee: Int,
    taxes: Int,
    discount: Int,
    total: Int,
    appliedCoupon: String?,
    onApplyCoupon: (String) -> Unit,
    onRemoveCoupon: () -> Unit,
    onProceedToCheckout: () -> Unit,
    onBackClick: () -> Unit,
    onExploreServices: () -> Unit,
    modifier: Modifier = Modifier
) {
    var couponInput by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Your Cart (${cartItems.sumOf { it.quantity }})", fontWeight = FontWeight.Bold, color = FoodieTextPrimary) },
                navigationIcon = {
                    IconButton(onClick = onBackClick, modifier = Modifier.testTag("cart_back_btn")) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = FoodieTextPrimary)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        bottomBar = {
            if (cartItems.isNotEmpty()) {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .navigationBarsPadding(),
                    color = Color.White,
                    tonalElevation = 8.dp,
                    border = BorderStroke(1.dp, FoodieBorder)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "₹$total",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = FoodieTextPrimary
                            )
                            Text(
                                text = "View detailed bill below",
                                style = MaterialTheme.typography.labelSmall,
                                color = FoodieTextSecondary
                            )
                        }

                        Button(
                            onClick = onProceedToCheckout,
                            colors = ButtonDefaults.buttonColors(containerColor = FoodiePrimary),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .height(48.dp)
                                .testTag("checkout_btn")
                        ) {
                            Text("Proceed to Pay", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                            Spacer(modifier = Modifier.width(6.dp))
                            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, modifier = Modifier.size(16.dp))
                        }
                    }
                }
            }
        }
    ) { paddingValues ->
        if (cartItems.isEmpty()) {
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(32.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .background(FoodiePrimaryLight, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
                            contentDescription = null,
                            tint = FoodiePrimary,
                            modifier = Modifier.size(40.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Your cart is empty",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = FoodieTextPrimary
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Good food and fresh groceries are just a tap away!",
                        style = MaterialTheme.typography.bodyMedium,
                        color = FoodieTextSecondary,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = onExploreServices,
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = FoodiePrimary)
                    ) {
                        Text("Explore Food & Groceries", fontWeight = FontWeight.Bold)
                    }
                }
            }
        } else {
            LazyColumn(
                modifier = modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(FoodieBackground),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                // Cart Items Group Card
                item {
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "Order Items",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = FoodieTextPrimary
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            cartItems.forEachIndexed { index, item ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    if (item.isVeg != null) {
                                        VegNonVegBadge(isVeg = item.isVeg)
                                        Spacer(modifier = Modifier.width(8.dp))
                                    }

                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = item.title,
                                            fontWeight = FontWeight.SemiBold,
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = FoodieTextPrimary
                                        )
                                        Text(
                                            text = "₹${item.price} each",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = FoodieTextSecondary
                                        )
                                    }

                                    // Stepper
                                    Row(
                                        modifier = Modifier
                                            .background(FoodiePrimaryLight, RoundedCornerShape(8.dp))
                                            .border(1.dp, FoodiePrimary, RoundedCornerShape(8.dp))
                                            .padding(horizontal = 4.dp, vertical = 2.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        IconButton(
                                            onClick = { onDecrementItem(item.id) },
                                            modifier = Modifier.size(24.dp)
                                        ) {
                                            Text("-", color = FoodiePrimary, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                                        }
                                        Text(
                                            text = "${item.quantity}",
                                            fontWeight = FontWeight.Bold,
                                            color = FoodiePrimary,
                                            fontSize = 13.sp,
                                            modifier = Modifier.padding(horizontal = 6.dp)
                                        )
                                        IconButton(
                                            onClick = { onIncrementItem(item.id) },
                                            modifier = Modifier.size(24.dp)
                                        ) {
                                            Text("+", color = FoodiePrimary, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                                        }
                                    }

                                    Spacer(modifier = Modifier.width(12.dp))

                                    Text(
                                        text = "₹${item.price * item.quantity}",
                                        fontWeight = FontWeight.Bold,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = FoodieTextPrimary
                                    )
                                }

                                if (index < cartItems.size - 1) {
                                    HorizontalDivider(color = FoodieBorder)
                                }
                            }
                        }
                    }
                }

                // Coupon Card
                item {
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        border = BorderStroke(1.dp, FoodieBorder)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.LocalOffer,
                                        contentDescription = null,
                                        tint = FoodiePrimary,
                                        modifier = Modifier.size(20.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "Apply Coupons",
                                        fontWeight = FontWeight.Bold,
                                        color = FoodieTextPrimary
                                    )
                                }

                                if (appliedCoupon != null) {
                                    TextButton(onClick = onRemoveCoupon) {
                                        Text("Remove", color = FoodieError, fontSize = 12.sp)
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            if (appliedCoupon != null) {
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = FoodieSuccessLight,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Row(
                                        modifier = Modifier.padding(12.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(Icons.Default.CheckCircle, contentDescription = null, tint = FoodieSuccess, modifier = Modifier.size(18.dp))
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = "'$appliedCoupon' applied! Saved ₹$discount",
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 12.sp,
                                            color = FoodieSuccess
                                        )
                                    }
                                }
                            } else {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    OutlinedTextField(
                                        value = couponInput,
                                        onValueChange = { couponInput = it },
                                        placeholder = { Text("Enter code (e.g. FOODIE50)", fontSize = 12.sp) },
                                        singleLine = true,
                                        shape = RoundedCornerShape(8.dp),
                                        modifier = Modifier.weight(1f).height(48.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Button(
                                        onClick = { onApplyCoupon(couponInput) },
                                        colors = ButtonDefaults.buttonColors(containerColor = FoodiePrimary),
                                        shape = RoundedCornerShape(8.dp),
                                        modifier = Modifier.height(48.dp)
                                    ) {
                                        Text("APPLY", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                                    }
                                }
                            }
                        }
                    }
                }

                // Bill Details Card
                item {
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "Bill Details",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = FoodieTextPrimary
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            BillRow(label = "Items Subtotal", amount = "₹$subtotal")
                            BillRow(
                                label = "Delivery Fee",
                                amount = if (deliveryFee == 0) "FREE" else "₹$deliveryFee",
                                isFree = deliveryFee == 0
                            )
                            BillRow(label = "Taxes & Packaging (5% GST)", amount = "₹$taxes")

                            if (discount > 0) {
                                BillRow(
                                    label = "Coupon Savings",
                                    amount = "-₹$discount",
                                    isDiscount = true
                                )
                            }

                            Spacer(modifier = Modifier.height(8.dp))
                            HorizontalDivider(color = FoodieBorder)
                            Spacer(modifier = Modifier.height(10.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "To Pay",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = FoodieTextPrimary
                                )
                                Text(
                                    text = "₹$total",
                                    style = MaterialTheme.typography.headlineSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = FoodiePrimary
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BillRow(label: String, amount: String, isFree: Boolean = false, isDiscount: Boolean = false) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, style = MaterialTheme.typography.bodySmall, color = FoodieTextSecondary)
        Text(
            text = amount,
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.SemiBold,
            color = when {
                isFree || isDiscount -> FoodieSuccess
                else -> FoodieTextPrimary
            }
        )
    }
}

/**
 * Checkout Screen with Address selection & Payment method selector
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutScreen(
    currentLocation: String,
    totalAmount: Int,
    onPlaceOrder: (address: String, paymentMethod: String) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedAddress by remember { mutableStateOf(currentLocation) }
    var selectedPayment by remember { mutableStateOf("Google Pay UPI") }
    var deliveryInstructions by remember { mutableStateOf("Leave at door with security") }

    val paymentOptions = listOf(
        Pair("Google Pay UPI", "Fastest • Zero fee"),
        Pair("PhonePe / Paytm UPI", "Direct bank debit"),
        Pair("Credit or Debit Card", "Visa, Mastercard, RuPay"),
        Pair("Cash on Delivery (COD)", "Pay cash at doorstep")
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Checkout & Payment", fontWeight = FontWeight.Bold, color = FoodieTextPrimary) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = FoodieTextPrimary)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        bottomBar = {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding(),
                color = Color.White,
                tonalElevation = 8.dp,
                border = BorderStroke(1.dp, FoodieBorder)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "₹$totalAmount",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = FoodieTextPrimary
                        )
                        Text(
                            text = selectedPayment,
                            style = MaterialTheme.typography.labelSmall,
                            color = FoodieSuccess
                        )
                    }

                    Button(
                        onClick = { onPlaceOrder(selectedAddress, selectedPayment) },
                        colors = ButtonDefaults.buttonColors(containerColor = FoodiePrimary),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .height(50.dp)
                            .testTag("place_order_btn")
                    ) {
                        Text("Place Order", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    }
                }
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(FoodieBackground),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Delivery Address Selector
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Delivery Address",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = FoodieTextPrimary
                            )
                            Icon(Icons.Default.LocationOn, contentDescription = null, tint = FoodiePrimary)
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        FoodieMockData.savedAddresses.forEach { addr ->
                            val fullAddr = "${addr.label}: ${addr.addressLine}"
                            val isSelected = selectedAddress.contains(addr.label)
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { selectedAddress = fullAddr }
                                    .padding(vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    selected = isSelected,
                                    onClick = { selectedAddress = fullAddr },
                                    colors = RadioButtonDefaults.colors(selectedColor = FoodiePrimary)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Column {
                                    Text(
                                        text = addr.label,
                                        fontWeight = FontWeight.Bold,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = FoodieTextPrimary
                                    )
                                    Text(
                                        text = addr.addressLine,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = FoodieTextSecondary
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Payment Options Card
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Select Payment Method",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = FoodieTextPrimary
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        paymentOptions.forEach { (method, subtitle) ->
                            val isSelected = selectedPayment == method
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { selectedPayment = method }
                                    .padding(vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    selected = isSelected,
                                    onClick = { selectedPayment = method },
                                    colors = RadioButtonDefaults.colors(selectedColor = FoodiePrimary)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Column {
                                    Text(
                                        text = method,
                                        fontWeight = FontWeight.SemiBold,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = FoodieTextPrimary
                                    )
                                    Text(
                                        text = subtitle,
                                        style = MaterialTheme.typography.labelSmall,
                                        color = FoodieTextSecondary
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Instructions note
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = BorderStroke(1.dp, FoodieBorder)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Delivery Instructions",
                            fontWeight = FontWeight.Bold,
                            color = FoodieTextPrimary
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = deliveryInstructions,
                            onValueChange = { deliveryInstructions = it },
                            placeholder = { Text("e.g. Ring bell, leave with guard") },
                            singleLine = true,
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}

/**
 * Order Success Screen displaying the TicketReceiptCard
 */
@Composable
fun OrderSuccessScreen(
    order: OrderReceipt,
    onTrackOrder: () -> Unit,
    onBackHome: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        bottomBar = {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding(),
                color = Color.White,
                tonalElevation = 8.dp,
                border = BorderStroke(1.dp, FoodieBorder)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedButton(
                        onClick = onBackHome,
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                            .testTag("home_after_order_btn"),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = FoodieTextPrimary)
                    ) {
                        Text("Back to Home", fontWeight = FontWeight.Bold)
                    }

                    Button(
                        onClick = onTrackOrder,
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                            .testTag("track_order_btn"),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = FoodiePrimary)
                    ) {
                        Text("Track Order ⚡", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(FoodieBackground),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                TicketReceiptCard(order = order)
            }
        }
    }
}

/**
 * Live Order Tracking Screen
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderTrackingScreen(
    order: OrderReceipt,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Live Order Status", fontWeight = FontWeight.Bold, color = FoodieTextPrimary) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = FoodieTextPrimary)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(FoodieBackground),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Live Status Card
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "Arriving in ${order.estimatedArrival}",
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = FoodiePrimary
                                )
                                Text(
                                    text = "Order #${order.orderId}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = FoodieTextSecondary
                                )
                            }

                            Box(
                                modifier = Modifier
                                    .size(48.dp)
                                    .background(FoodiePrimaryLight, CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.DeliveryDining,
                                    contentDescription = null,
                                    tint = FoodiePrimary,
                                    modifier = Modifier.size(28.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        // Steps Progress
                        val currentStepIndex = when (order.status) {
                            OrderStatus.PLACED -> 1
                            OrderStatus.PREPARING -> 2
                            OrderStatus.OUT_FOR_DELIVERY -> 3
                            OrderStatus.DELIVERED -> 4
                        }

                        val steps = listOf(
                            "Order Confirmed" to "Restaurant accepted your order",
                            "Preparing Food" to "Chef is cooking your order",
                            "Out for Delivery" to "Rider is heading your way",
                            "Delivered" to "Delivered to your doorstep"
                        )

                        steps.forEachIndexed { index, (stepTitle, stepDesc) ->
                            val isCompleted = index + 1 <= currentStepIndex
                            val isCurrent = index + 1 == currentStepIndex

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.Top
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Box(
                                        modifier = Modifier
                                            .size(24.dp)
                                            .background(
                                                if (isCompleted) FoodieSuccess else Color(0xFFE5E7EB),
                                                CircleShape
                                            ),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        if (isCompleted) {
                                            Icon(
                                                imageVector = Icons.Default.Check,
                                                contentDescription = null,
                                                tint = Color.White,
                                                modifier = Modifier.size(14.dp)
                                            )
                                        }
                                    }
                                    if (index < steps.size - 1) {
                                        Box(
                                            modifier = Modifier
                                                .width(2.dp)
                                                .height(30.dp)
                                                .background(if (index + 1 < currentStepIndex) FoodieSuccess else FoodieBorder)
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.width(12.dp))

                                Column {
                                    Text(
                                        text = stepTitle,
                                        fontWeight = if (isCurrent) FontWeight.Bold else FontWeight.Medium,
                                        color = if (isCompleted) FoodieTextPrimary else FoodieTextMuted,
                                        fontSize = 14.sp
                                    )
                                    Text(
                                        text = stepDesc,
                                        fontSize = 11.sp,
                                        color = FoodieTextSecondary
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Rider Info Card
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = BorderStroke(1.dp, FoodieBorder)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .background(FoodiePrimaryLight, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.Person, contentDescription = null, tint = FoodiePrimary)
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Raju Sharma",
                                fontWeight = FontWeight.Bold,
                                color = FoodieTextPrimary
                            )
                            Text(
                                text = "Vaccinated • 4.9 ★ (1,840 ratings)",
                                fontSize = 11.sp,
                                color = FoodieTextSecondary
                            )
                        }

                        IconButton(
                            onClick = { /* Call rider */ },
                            modifier = Modifier.background(FoodieSuccessLight, CircleShape)
                        ) {
                            Icon(Icons.Default.Call, contentDescription = "Call", tint = FoodieSuccess)
                        }
                    }
                }
            }
        }
    }
}
