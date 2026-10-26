package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.FoodieMockData
import com.example.model.*
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    userProfile: UserProfile,
    onBackClick: () -> Unit,
    onOpenAuthModal: () -> Unit,
    onSignOut: () -> Unit,
    onViewOrders: () -> Unit,
    onViewRides: () -> Unit,
    onViewAddresses: () -> Unit,
    ordersCount: Int,
    ridesCount: Int,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "My Profile",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = FoodieTextPrimary
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = FoodieTextPrimary
                        )
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
                .background(Color.White),
            contentPadding = PaddingValues(bottom = 40.dp)
        ) {
            // User Profile Header Card (Inspired by image_823b766b.png)
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = FoodieSecondary,
                        contentColor = FoodieTextPrimary
                    ),
                    border = BorderStroke(1.dp, FoodiePrimaryLight)
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            val gradientBrush = Brush.linearGradient(
                                listOf(FoodiePrimary, Color(0xFFFF7A00))
                            )
                            Box(
                                modifier = Modifier
                                    .size(60.dp)
                                    .clip(CircleShape)
                                    .border(2.5.dp, gradientBrush, CircleShape)
                                    .padding(3.dp)
                                    .background(Color.White, CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.ic_foodie_logo),
                                    contentDescription = "Avatar",
                                    modifier = Modifier.size(34.dp)
                                )
                            }

                            Spacer(modifier = Modifier.width(16.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = if (userProfile.isLoggedIn) userProfile.name else "Welcome to FOODIE",
                                        fontSize = 17.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = FoodieTextPrimary
                                    )
                                    if (userProfile.isLoggedIn && userProfile.isPro) {
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Surface(
                                            shape = RoundedCornerShape(6.dp),
                                            color = FoodiePrimary
                                        ) {
                                            Text(
                                                text = "PRO",
                                                color = Color.White,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 9.sp,
                                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                            )
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.height(3.dp))

                                Text(
                                    text = if (userProfile.isLoggedIn) userProfile.phone else "Sign in to manage orders",
                                    fontSize = 12.sp,
                                    color = FoodieTextSecondary
                                )

                                if (userProfile.isLoggedIn) {
                                    Text(
                                        text = userProfile.email,
                                        fontSize = 11.sp,
                                        color = FoodieTextMuted
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        if (!userProfile.isLoggedIn) {
                            Button(
                                onClick = onOpenAuthModal,
                                colors = ButtonDefaults.buttonColors(containerColor = FoodiePrimary),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(44.dp)
                                    .testTag("profile_signin_btn")
                            ) {
                                Text("Sign In / Sign Up", fontWeight = FontWeight.Bold, color = Color.White)
                            }
                        } else {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                OutlinedButton(
                                    onClick = onOpenAuthModal,
                                    shape = RoundedCornerShape(10.dp),
                                    border = BorderStroke(1.dp, FoodieBorder),
                                    modifier = Modifier.weight(1f).height(38.dp)
                                ) {
                                    Text("Switch Account", fontSize = 12.sp, color = FoodieTextPrimary)
                                }
                                OutlinedButton(
                                    onClick = onSignOut,
                                    shape = RoundedCornerShape(10.dp),
                                    border = BorderStroke(1.dp, FoodieBorder),
                                    modifier = Modifier.weight(1f).height(38.dp)
                                ) {
                                    Text("Sign Out", fontSize = 12.sp, color = FoodieError)
                                }
                            }
                        }
                    }
                }
            }

            // Orders & Rides quick counters
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .clickable { onViewOrders() },
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        border = BorderStroke(1.dp, FoodieBorder)
                    ) {
                        Row(
                            modifier = Modifier.padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(38.dp)
                                    .background(FoodiePrimaryLight, CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.ReceiptLong, contentDescription = null, tint = FoodiePrimary, modifier = Modifier.size(18.dp))
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text("My Orders", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = FoodieTextPrimary)
                                Text("$ordersCount orders", fontSize = 11.sp, color = FoodieTextSecondary)
                            }
                        }
                    }

                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .clickable { onViewRides() },
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        border = BorderStroke(1.dp, FoodieBorder)
                    ) {
                        Row(
                            modifier = Modifier.padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(38.dp)
                                    .background(Color(0xFFEFF6FF), CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.TwoWheeler, contentDescription = null, tint = Color(0xFF2563EB), modifier = Modifier.size(18.dp))
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text("My Rides", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = FoodieTextPrimary)
                                Text("$ridesCount rides", fontSize = 11.sp, color = FoodieTextSecondary)
                            }
                        }
                    }
                }
            }

            // Menu items list
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = BorderStroke(1.dp, FoodieBorder)
                ) {
                    Column(modifier = Modifier.padding(vertical = 4.dp)) {
                        ProfileMenuRow(
                            icon = Icons.Outlined.LocationOn,
                            title = "Saved Addresses",
                            badge = "${FoodieMockData.savedAddresses.size}",
                            onClick = onViewAddresses
                        )
                        HorizontalDivider(color = FoodieBorder, modifier = Modifier.padding(horizontal = 16.dp))

                        ProfileMenuRow(
                            icon = Icons.Outlined.Payment,
                            title = "Payment Methods",
                            subtitle = "Google Pay UPI, Cards",
                            onClick = { }
                        )
                        HorizontalDivider(color = FoodieBorder, modifier = Modifier.padding(horizontal = 16.dp))

                        ProfileMenuRow(
                            icon = Icons.Outlined.Headphones,
                            title = "Help & 24x7 Support",
                            onClick = { }
                        )
                        HorizontalDivider(color = FoodieBorder, modifier = Modifier.padding(horizontal = 16.dp))

                        ProfileMenuRow(
                            icon = Icons.Outlined.Info,
                            title = "About FOODIE",
                            subtitle = "v2.5.0 Minimal Edition",
                            onClick = { }
                        )
                    }
                }
            }

            // Auth options banner
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFAFAFA)),
                    border = BorderStroke(1.dp, FoodieBorder)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Instant Sign In Options",
                            fontWeight = FontWeight.Bold,
                            color = FoodieTextPrimary,
                            fontSize = 14.sp
                        )
                        Text(
                            text = "Connect with Google, Apple, or mobile number OTP.",
                            fontSize = 12.sp,
                            color = FoodieTextSecondary
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Button(
                                onClick = onOpenAuthModal,
                                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                                border = BorderStroke(1.dp, FoodieBorder),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.weight(1f).height(36.dp)
                            ) {
                                Text("G Google", color = FoodieTextPrimary, fontWeight = FontWeight.Bold, fontSize = 11.sp)
                            }

                            Button(
                                onClick = onOpenAuthModal,
                                colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.weight(1f).height(36.dp)
                            ) {
                                Text(" Apple", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 11.sp)
                            }

                            Button(
                                onClick = onOpenAuthModal,
                                colors = ButtonDefaults.buttonColors(containerColor = FoodiePrimary),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.weight(1f).height(36.dp)
                            ) {
                                Text("Phone", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 11.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ProfileMenuRow(
    icon: ImageVector,
    title: String,
    subtitle: String? = null,
    badge: String? = null,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = FoodieTextPrimary,
            modifier = Modifier.size(20.dp)
        )

        Spacer(modifier = Modifier.width(14.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = FoodieTextPrimary
            )
            if (!subtitle.isNullOrBlank()) {
                Text(
                    text = subtitle,
                    fontSize = 11.sp,
                    color = FoodieTextSecondary
                )
            }
        }

        if (!badge.isNullOrBlank()) {
            Surface(
                shape = RoundedCornerShape(6.dp),
                color = Color(0xFFF3F4F6)
            ) {
                Text(
                    text = badge,
                    color = FoodieTextSecondary,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                )
            }
            Spacer(modifier = Modifier.width(6.dp))
        }

        Icon(
            imageVector = Icons.Default.ChevronRight,
            contentDescription = null,
            tint = FoodieTextMuted,
            modifier = Modifier.size(18.dp)
        )
    }
}

/**
 * Screen showing order history
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrdersListScreen(
    orders: List<OrderReceipt>,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Orders", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = FoodieTextPrimary) },
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
                .background(Color.White),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(orders) { order ->
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White, contentColor = FoodieTextPrimary),
                    border = BorderStroke(1.dp, FoodieBorder)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Order #${order.orderId}",
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                color = FoodieTextPrimary
                            )

                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = FoodieSuccessLight
                            ) {
                                Text(
                                    text = "DELIVERED",
                                    color = FoodieSuccess,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 10.sp,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = order.orderTime, fontSize = 11.sp, color = FoodieTextMuted)

                        Spacer(modifier = Modifier.height(10.dp))
                        HorizontalDivider(color = FoodieBorder)
                        Spacer(modifier = Modifier.height(10.dp))

                        order.items.forEach { itm ->
                            Text(
                                text = "${itm.quantity}x ${itm.title}",
                                fontSize = 13.sp,
                                color = FoodieTextSecondary
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Paid via ${order.paymentMethod}",
                                fontSize = 11.sp,
                                color = FoodieTextSecondary
                            )
                            Text(
                                text = "₹${order.totalAmount}",
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                color = FoodiePrimary
                            )
                        }
                    }
                }
            }
        }
    }
}
