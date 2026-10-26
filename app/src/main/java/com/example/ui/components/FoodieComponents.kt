package com.example.ui.components

import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.example.R
import com.example.data.FoodieMockData
import com.example.model.*
import com.example.ui.theme.*

/**
 * Clean, minimal top header with Location, Notification, and User Avatar for profile navigation
 */
@Composable
fun FoodieTopHeader(
    currentLocation: String,
    onLocationClick: () -> Unit,
    onSearchClick: () -> Unit,
    onNotificationClick: () -> Unit,
    onProfileClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(horizontal = 16.dp, vertical = 10.dp)
    ) {
        // Top row: Location selector on left, Profile & Notification on right
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Location click block
            Row(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(10.dp))
                    .clickable { onLocationClick() }
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .background(FoodiePrimaryLight, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "Location",
                        tint = FoodiePrimary,
                        modifier = Modifier.size(20.dp)
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))

                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Delivering to",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium,
                            color = FoodieTextSecondary
                        )
                        Spacer(modifier = Modifier.width(2.dp))
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowDown,
                            contentDescription = "Select Location",
                            tint = FoodiePrimary,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                    Text(
                        text = currentLocation,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = FoodieTextPrimary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Action icons: Notification & User Avatar
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                IconButton(
                    onClick = onNotificationClick,
                    modifier = Modifier
                        .size(38.dp)
                        .background(Color(0xFFF7F7F7), CircleShape)
                        .testTag("notif_button")
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Notifications,
                        contentDescription = "Notifications",
                        tint = FoodieTextPrimary,
                        modifier = Modifier.size(20.dp)
                    )
                }

                // Profile Avatar with ring (opens Profile screen)
                val avatarRing = Brush.linearGradient(
                    listOf(FoodiePrimary, Color(0xFFFF7A00))
                )
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .clip(CircleShape)
                        .border(1.5.dp, avatarRing, CircleShape)
                        .padding(2.dp)
                        .background(Color.White, CircleShape)
                        .clickable { onProfileClick() }
                        .testTag("profile_avatar_header"),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_foodie_logo),
                        contentDescription = "Profile",
                        modifier = Modifier.size(22.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Clean, rounded search trigger
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(14.dp))
                .clickable { onSearchClick() }
                .testTag("search_trigger_bar"),
            shape = RoundedCornerShape(14.dp),
            color = Color(0xFFF7F7F8),
            border = BorderStroke(1.dp, FoodieBorder)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp, vertical = 11.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = FoodiePrimary,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "Food, groceries, or rides...",
                    fontSize = 14.sp,
                    color = FoodieTextMuted
                )
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    imageVector = Icons.Outlined.Tune,
                    contentDescription = "Filters",
                    tint = FoodieTextSecondary,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}

/**
 * Exactly 4-Item Minimal Bottom Navigation Bar: Home, Food, Grocery, Ride
 */
@Composable
fun FoodieBottomBar(
    currentTab: ScreenTab,
    onTabSelected: (ScreenTab) -> Unit,
    cartCount: Int,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .navigationBarsPadding(),
        color = Color.White,
        shadowElevation = 10.dp,
        border = BorderStroke(1.dp, Color(0xFFF0F0F0))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(62.dp)
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            val tabs = listOf(
                Triple(ScreenTab.HOME, "Home", Icons.Default.Home to Icons.Outlined.Home),
                Triple(ScreenTab.FOOD, "Food", Icons.Default.Restaurant to Icons.Outlined.Restaurant),
                Triple(ScreenTab.GROCERY, "Grocery", Icons.Default.ShoppingBag to Icons.Outlined.ShoppingBag),
                Triple(ScreenTab.RIDE, "Ride", Icons.Default.TwoWheeler to Icons.Outlined.TwoWheeler)
            )

            tabs.forEach { (tab, label, icons) ->
                val isSelected = currentTab == tab

                Column(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .clickable { onTabSelected(tab) }
                        .padding(horizontal = 14.dp, vertical = 6.dp)
                        .testTag("tab_${label.lowercase()}"),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Box {
                        Icon(
                            imageVector = if (isSelected) icons.first else icons.second,
                            contentDescription = label,
                            tint = if (isSelected) FoodiePrimary else FoodieTextSecondary,
                            modifier = Modifier.size(22.dp)
                        )

                        // Grocery tab badge
                        if (tab == ScreenTab.GROCERY && cartCount > 0) {
                            Box(
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .offset(x = 6.dp, y = (-3).dp)
                                    .size(8.dp)
                                    .background(FoodiePrimary, CircleShape)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(3.dp))

                    Text(
                        text = label,
                        fontSize = 11.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                        color = if (isSelected) FoodiePrimary else FoodieTextSecondary
                    )
                }
            }
        }
    }
}

/**
 * Floating Cart Banner (Pill-style, appearing gracefully above bottom nav)
 */
@Composable
fun FloatingCartBanner(
    cartCount: Int,
    cartTotal: Int,
    onViewCartClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (cartCount <= 0) return

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .clickable { onViewCartClick() }
            .testTag("floating_cart_banner"),
        shape = RoundedCornerShape(16.dp),
        color = FoodiePrimary,
        shadowElevation = 6.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 11.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .background(Color.White.copy(alpha = 0.25f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "$cartCount",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = "$cartCount items in cart",
                        color = Color.White.copy(alpha = 0.9f),
                        fontSize = 11.sp
                    )
                    Text(
                        text = "₹$cartTotal",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )
                }
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "View Cart",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp
                )
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(14.dp)
                )
            }
        }
    }
}

@Composable
fun VegNonVegBadge(isVeg: Boolean, modifier: Modifier = Modifier) {
    val color = if (isVeg) FoodieVegGreen else FoodieNonVegRed
    Box(
        modifier = modifier
            .size(15.dp)
            .border(1.5.dp, color, RoundedCornerShape(3.dp))
            .padding(2.5.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color, CircleShape)
        )
    }
}

/**
 * Minimal & Clean Restaurant Card (Spacious, breathing room, no clutter)
 */
@Composable
fun RestaurantCard(
    restaurant: Restaurant,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .testTag("restaurant_card_${restaurant.id}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            contentColor = FoodieTextPrimary
        ),
        border = BorderStroke(1.dp, FoodieBorder)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(145.dp)
            ) {
                AsyncImage(
                    model = restaurant.imageUrl,
                    contentDescription = restaurant.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                // Discount Pill on bottom-left
                if (!restaurant.discountTag.isNullOrBlank()) {
                    Surface(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(10.dp),
                        shape = RoundedCornerShape(6.dp),
                        color = FoodiePrimary
                    ) {
                        Text(
                            text = restaurant.discountTag,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.sp,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                        )
                    }
                }

                // Delivery Time on bottom-right
                Surface(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(10.dp),
                    shape = RoundedCornerShape(20.dp),
                    color = Color.White
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Timer,
                            contentDescription = null,
                            tint = FoodieTextPrimary,
                            modifier = Modifier.size(12.dp)
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(
                            text = "${restaurant.deliveryTimeMins} mins",
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.sp,
                            color = FoodieTextPrimary
                        )
                    }
                }
            }

            Column(modifier = Modifier.padding(14.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = restaurant.name,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = FoodieTextPrimary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = FoodieSuccess
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "${restaurant.rating}",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.sp
                            )
                            Spacer(modifier = Modifier.width(2.dp))
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(11.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(3.dp))

                Text(
                    text = restaurant.cuisine,
                    fontSize = 12.sp,
                    color = FoodieTextSecondary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = if (restaurant.deliveryFee == 0) "Free Delivery" else "₹${restaurant.deliveryFee} fee",
                        fontSize = 11.sp,
                        color = if (restaurant.deliveryFee == 0) FoodieSuccess else FoodieTextSecondary,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text("•", color = FoodieBorder, fontSize = 12.sp)
                    Text(
                        text = "₹${restaurant.priceForTwo} for two",
                        fontSize = 11.sp,
                        color = FoodieTextSecondary
                    )
                }
            }
        }
    }
}

/**
 * Clean & minimal Menu Item Card
 */
@Composable
fun MenuItemCard(
    item: MenuItem,
    quantityInCart: Int,
    onAddToCart: () -> Unit,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("menu_item_${item.id}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            contentColor = FoodieTextPrimary
        ),
        border = BorderStroke(1.dp, FoodieBorder)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 12.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    VegNonVegBadge(isVeg = item.isVeg)
                    if (item.isBestseller) {
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "BESTSELLER",
                            color = FoodieWarning,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = item.name,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = FoodieTextPrimary
                )

                Text(
                    text = "₹${item.price}",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = FoodieTextPrimary
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = item.description,
                    fontSize = 12.sp,
                    color = FoodieTextSecondary,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(86.dp)
                        .clip(RoundedCornerShape(12.dp))
                ) {
                    AsyncImage(
                        model = item.imageUrl,
                        contentDescription = item.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                if (quantityInCart == 0) {
                    Button(
                        onClick = onAddToCart,
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = FoodiePrimaryLight,
                            contentColor = FoodiePrimary
                        ),
                        contentPadding = PaddingValues(horizontal = 14.dp, vertical = 2.dp),
                        modifier = Modifier.height(30.dp).testTag("add_btn_${item.id}")
                    ) {
                        Text(
                            text = "+ ADD",
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.sp,
                            color = FoodiePrimary
                        )
                    }
                } else {
                    Row(
                        modifier = Modifier
                            .height(30.dp)
                            .background(FoodiePrimary, RoundedCornerShape(8.dp))
                            .padding(horizontal = 2.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = onDecrement, modifier = Modifier.size(24.dp)) {
                            Text("-", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                        }
                        Text(
                            text = "$quantityInCart",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(horizontal = 4.dp)
                        )
                        IconButton(onClick = onIncrement, modifier = Modifier.size(24.dp)) {
                            Text("+", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                        }
                    }
                }
            }
        }
    }
}

/**
 * Minimal & Modern Grocery Product Card (Inspired by Nike & Grocery reference card designs)
 */
@Composable
fun GroceryProductCard(
    product: GroceryProduct,
    quantityInCart: Int,
    onAddToCart: () -> Unit,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .testTag("grocery_card_${product.id}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            contentColor = FoodieTextPrimary
        ),
        border = BorderStroke(1.dp, FoodieBorder)
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(105.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFFF8F8F9))
            ) {
                AsyncImage(
                    model = product.imageUrl,
                    contentDescription = product.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                // 10 MINS tag pill
                Surface(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(6.dp),
                    shape = RoundedCornerShape(4.dp),
                    color = Color.White
                ) {
                    Text(
                        text = "⚡ ${product.deliveryTimeMins}m",
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        color = FoodieTextPrimary,
                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                    )
                }

                if (product.discountPercent > 0) {
                    Surface(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(6.dp),
                        shape = RoundedCornerShape(4.dp),
                        color = FoodiePrimary
                    ) {
                        Text(
                            text = "${product.discountPercent}% OFF",
                            color = Color.White,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = product.name,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = FoodieTextPrimary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = product.unit,
                fontSize = 11.sp,
                color = FoodieTextSecondary
            )

            Spacer(modifier = Modifier.height(6.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "₹${product.price}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = FoodieTextPrimary
                    )
                    if (product.originalPrice > product.price) {
                        Text(
                            text = "₹${product.originalPrice}",
                            fontSize = 10.sp,
                            color = FoodieTextMuted,
                            textDecoration = TextDecoration.LineThrough
                        )
                    }
                }

                if (quantityInCart == 0) {
                    Button(
                        onClick = onAddToCart,
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = FoodiePrimaryLight,
                            contentColor = FoodiePrimary
                        ),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 2.dp),
                        modifier = Modifier.height(28.dp).testTag("grocery_add_${product.id}")
                    ) {
                        Text("ADD", fontWeight = FontWeight.Bold, fontSize = 11.sp, color = FoodiePrimary)
                    }
                } else {
                    Row(
                        modifier = Modifier
                            .height(28.dp)
                            .background(FoodiePrimary, RoundedCornerShape(8.dp))
                            .padding(horizontal = 2.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = onDecrement, modifier = Modifier.size(22.dp)) {
                            Text("-", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        }
                        Text(
                            text = "$quantityInCart",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.sp,
                            modifier = Modifier.padding(horizontal = 4.dp)
                        )
                        IconButton(onClick = onIncrement, modifier = Modifier.size(22.dp)) {
                            Text("+", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        }
                    }
                }
            }
        }
    }
}

/**
 * Clean Ticket Receipt Card (image_670a7ecd.png reference)
 */
@Composable
fun TicketReceiptCard(
    order: OrderReceipt,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("ticket_receipt_card"),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White, contentColor = FoodieTextPrimary),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        border = BorderStroke(1.dp, FoodieBorder)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(54.dp)
                    .background(FoodiePrimaryLight, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "🎉", fontSize = 26.sp)
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Thank you!",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = FoodieTextPrimary
            )

            Text(
                text = "Your order has been placed successfully",
                fontSize = 13.sp,
                color = FoodieTextSecondary,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(18.dp))
            HorizontalDivider(color = FoodieBorder)
            Spacer(modifier = Modifier.height(18.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(text = "ORDER ID", fontSize = 11.sp, color = FoodieTextMuted, fontWeight = FontWeight.SemiBold)
                    Text(text = order.orderId, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = FoodieTextPrimary)
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(text = "AMOUNT", fontSize = 11.sp, color = FoodieTextMuted, fontWeight = FontWeight.SemiBold)
                    Text(text = "₹${order.totalAmount}", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = FoodiePrimary)
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(text = "DATE & TIME", fontSize = 11.sp, color = FoodieTextMuted, fontWeight = FontWeight.SemiBold)
                    Text(text = order.orderTime, fontSize = 13.sp, color = FoodieTextPrimary)
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(text = "ESTIMATED ARRIVAL", fontSize = 11.sp, color = FoodieTextMuted, fontWeight = FontWeight.SemiBold)
                    Text(text = order.estimatedArrival, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = FoodieSuccess)
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Payment method
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                color = Color(0xFFF9F9FA),
                border = BorderStroke(1.dp, FoodieBorder)
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.Payment, contentDescription = null, tint = FoodiePrimary, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = order.paymentMethod, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = FoodieTextPrimary)
                    Spacer(modifier = Modifier.weight(1f))
                    Text(text = "Verified", fontSize = 11.sp, color = FoodieSuccess, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Barcode
            Row(
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .height(44.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val barWidths = listOf(2, 4, 1, 3, 5, 2, 1, 4, 2, 6, 1, 3, 4, 2, 5, 1, 3, 2, 4, 1, 5, 2, 3)
                barWidths.forEach { width ->
                    Box(modifier = Modifier.width(width.dp).fillMaxHeight().background(FoodieTextPrimary))
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = order.barcodeNumber, fontSize = 10.sp, color = FoodieTextMuted, letterSpacing = 2.sp)
        }
    }
}

/**
 * Authentication dialog with Google, Apple, and Phone options
 */
@Composable
fun FoodieAuthDialog(
    isOpen: Boolean,
    onDismiss: () -> Unit,
    onGoogleLogin: () -> Unit,
    onAppleLogin: () -> Unit,
    phoneInput: String,
    onPhoneChange: (String) -> Unit,
    otpInput: String,
    onOtpChange: (String) -> Unit,
    onPhoneSubmit: () -> Unit
) {
    if (!isOpen) return

    var isOtpStep by remember { mutableStateOf(false) }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White, contentColor = FoodieTextPrimary),
            modifier = Modifier.fillMaxWidth().testTag("auth_dialog")
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(22.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "FOODIE Sign In", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = FoodieTextPrimary)
                    IconButton(onClick = onDismiss, modifier = Modifier.size(24.dp)) {
                        Icon(Icons.Default.Close, contentDescription = "Close", tint = FoodieTextSecondary)
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                if (!isOtpStep) {
                    Button(
                        onClick = onGoogleLogin,
                        modifier = Modifier.fillMaxWidth().height(46.dp).testTag("google_login_btn"),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                        border = BorderStroke(1.dp, FoodieBorder)
                    ) {
                        Text("G  Continue with Google", color = FoodieTextPrimary, fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Button(
                        onClick = onAppleLogin,
                        modifier = Modifier.fillMaxWidth().height(46.dp).testTag("apple_login_btn"),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
                    ) {
                        Text("  Continue with Apple", color = Color.White, fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(text = "— OR WITH PHONE —", fontSize = 10.sp, color = FoodieTextMuted, fontWeight = FontWeight.Bold)

                    Spacer(modifier = Modifier.height(14.dp))

                    OutlinedTextField(
                        value = phoneInput,
                        onValueChange = onPhoneChange,
                        placeholder = { Text("10-digit mobile number", fontSize = 13.sp) },
                        leadingIcon = { Text(" +91 ", fontWeight = FontWeight.Bold, color = FoodieTextPrimary, fontSize = 13.sp) },
                        singleLine = true,
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth().testTag("phone_input")
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = {
                            if (phoneInput.length >= 5) isOtpStep = true else onPhoneSubmit()
                        },
                        modifier = Modifier.fillMaxWidth().height(46.dp).testTag("get_otp_btn"),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = FoodiePrimary)
                    ) {
                        Text(
                            text = if (phoneInput.length >= 5) "Get OTP" else "Continue with Demo Phone",
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            fontSize = 13.sp
                        )
                    }
                } else {
                    Text(text = "Enter OTP sent to +91 $phoneInput", fontSize = 12.sp, color = FoodieTextSecondary)
                    Spacer(modifier = Modifier.height(14.dp))
                    OutlinedTextField(
                        value = otpInput,
                        onValueChange = onOtpChange,
                        placeholder = { Text("4-digit OTP", fontSize = 13.sp) },
                        singleLine = true,
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth().testTag("otp_input")
                    )
                    Spacer(modifier = Modifier.height(14.dp))
                    Button(
                        onClick = {
                            onPhoneSubmit()
                            isOtpStep = false
                        },
                        modifier = Modifier.fillMaxWidth().height(46.dp).testTag("verify_otp_btn"),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = FoodiePrimary)
                    ) {
                        Text("Verify & Continue", fontWeight = FontWeight.Bold, color = Color.White, fontSize = 13.sp)
                    }
                }
            }
        }
    }
}

/**
 * Location picker bottom sheet
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationPickerSheet(
    isOpen: Boolean,
    onDismiss: () -> Unit,
    currentLocation: String,
    onSelectAddress: (String) -> Unit
) {
    if (!isOpen) return

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = Color.White,
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 10.dp)
        ) {
            Text(
                text = "Select Delivery Address",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = FoodieTextPrimary
            )

            Spacer(modifier = Modifier.height(14.dp))

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onSelectAddress("Current Location: Connaught Place, New Delhi") },
                shape = RoundedCornerShape(12.dp),
                color = FoodiePrimaryLight
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.MyLocation, contentDescription = null, tint = FoodiePrimary, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text("Use Current Location", fontWeight = FontWeight.Bold, color = FoodiePrimary, fontSize = 14.sp)
                        Text("Using GPS", fontSize = 11.sp, color = FoodieTextSecondary)
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            FoodieMockData.savedAddresses.forEach { addr ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onSelectAddress("${addr.label}: ${addr.addressLine}") }
                        .padding(vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(34.dp)
                            .background(Color(0xFFF5F5F5), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = when (addr.label) {
                                "Home" -> Icons.Default.Home
                                "Work" -> Icons.Default.Business
                                else -> Icons.Default.LocationOn
                            },
                            contentDescription = addr.label,
                            tint = FoodieTextPrimary,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = addr.label, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = FoodieTextPrimary)
                        Text(text = addr.addressLine, fontSize = 11.sp, color = FoodieTextSecondary, maxLines = 1, overflow = TextOverflow.Ellipsis)
                    }
                    if (currentLocation.contains(addr.label)) {
                        Icon(Icons.Default.CheckCircle, contentDescription = "Selected", tint = FoodiePrimary, modifier = Modifier.size(18.dp))
                    }
                }
                HorizontalDivider(color = FoodieBorder)
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
