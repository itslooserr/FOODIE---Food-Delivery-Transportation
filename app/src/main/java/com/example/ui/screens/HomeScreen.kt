package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.R
import com.example.data.FoodieMockData
import com.example.model.*
import com.example.ui.components.GroceryProductCard
import com.example.ui.components.RestaurantCard
import com.example.ui.theme.*

@Composable
fun HomeScreen(
    onNavigateToTab: (ScreenTab) -> Unit,
    onRestaurantClick: (Restaurant) -> Unit,
    onGroceryClick: (GroceryProduct) -> Unit,
    onAddToCart: (CartItem) -> Unit,
    onIncrementCart: (String) -> Unit,
    onDecrementCart: (String) -> Unit,
    getCartQty: (String) -> Unit,
    onQuickRideDestination: (String) -> Unit,
    cartItems: List<CartItem>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White),
        contentPadding = PaddingValues(bottom = 80.dp)
    ) {
        // Minimal Hero Promo Banner (Inspired by user's reference image_5de4421d.png & image_79bfe4cb.png)
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = FoodieSecondary),
                border = BorderStroke(1.dp, FoodiePrimaryLight)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = FoodiePrimary
                        ) {
                            Text(
                                text = "FOODIE 50% OFF",
                                color = Color.White,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Craving something good?",
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            color = FoodieTextPrimary
                        )

                        Text(
                            text = "Food, groceries & rides delivered in minutes",
                            fontSize = 12.sp,
                            color = FoodieTextSecondary
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Button(
                            onClick = { onNavigateToTab(ScreenTab.FOOD) },
                            colors = ButtonDefaults.buttonColors(containerColor = FoodiePrimary),
                            shape = RoundedCornerShape(20.dp),
                            contentPadding = PaddingValues(horizontal = 14.dp, vertical = 4.dp),
                            modifier = Modifier.height(32.dp)
                        ) {
                            Text("Order Now ↗", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color.White)
                        }
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Box(
                        modifier = Modifier
                            .size(85.dp)
                            .clip(RoundedCornerShape(16.dp))
                    ) {
                        AsyncImage(
                            model = "https://images.unsplash.com/photo-1565299624946-b28f40a0ae38?w=400&auto=format&fit=crop&q=80",
                            contentDescription = "Promo food",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }
        }

        // Clean 3 Service Tiles (Minimal, elegant, uncluttered)
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)) {
                Text(
                    text = "SERVICES",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = FoodieTextMuted,
                    letterSpacing = 1.sp
                )

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    MinimalServiceTile(
                        title = "Food",
                        subtitle = "Restaurants",
                        icon = Icons.Default.Restaurant,
                        accentColor = FoodiePrimary,
                        onClick = { onNavigateToTab(ScreenTab.FOOD) },
                        modifier = Modifier.weight(1f)
                    )
                    MinimalServiceTile(
                        title = "Grocery",
                        subtitle = "10 Mins",
                        icon = Icons.Default.ShoppingBag,
                        accentColor = Color(0xFF0D9488),
                        onClick = { onNavigateToTab(ScreenTab.GROCERY) },
                        modifier = Modifier.weight(1f)
                    )
                    MinimalServiceTile(
                        title = "Ride",
                        subtitle = "Bike & Cab",
                        icon = Icons.Default.TwoWheeler,
                        accentColor = Color(0xFF2563EB),
                        onClick = { onNavigateToTab(ScreenTab.RIDE) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        // Quick Category Icons (Clean circular tiles, inspired by image_dc4de27d.png)
        item {
            Column(modifier = Modifier.padding(vertical = 8.dp)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Categories",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = FoodieTextPrimary
                    )
                    Text(
                        text = "See All",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = FoodiePrimary,
                        modifier = Modifier.clickable { onNavigateToTab(ScreenTab.FOOD) }
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                val quickCategories = listOf(
                    Triple("Biryani", Icons.Default.DinnerDining, ScreenTab.FOOD),
                    Triple("Pizza", Icons.Default.LocalPizza, ScreenTab.FOOD),
                    Triple("Vegetables", Icons.Default.Eco, ScreenTab.GROCERY),
                    Triple("Dairy", Icons.Default.Egg, ScreenTab.GROCERY),
                    Triple("Bike Taxi", Icons.Default.TwoWheeler, ScreenTab.RIDE),
                    Triple("Auto", Icons.Default.ElectricRickshaw, ScreenTab.RIDE)
                )

                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    items(quickCategories) { (name, icon, tab) ->
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .clickable { onNavigateToTab(tab) }
                                .padding(vertical = 4.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(52.dp)
                                    .background(FoodieSecondary, CircleShape)
                                    .border(1.dp, FoodieBorder, CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = icon,
                                    contentDescription = name,
                                    tint = FoodiePrimary,
                                    modifier = Modifier.size(22.dp)
                                )
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = name,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Medium,
                                color = FoodieTextPrimary
                            )
                        }
                    }
                }
            }
        }

        // Instant Groceries Shelf (Minimal clean cards)
        item {
            Column(modifier = Modifier.padding(vertical = 10.dp)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Daily Essentials",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = FoodieTextPrimary
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "⚡ 10 mins",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = FoodieWarning
                        )
                    }

                    Text(
                        text = "View All",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = FoodiePrimary,
                        modifier = Modifier.clickable { onNavigateToTab(ScreenTab.GROCERY) }
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(FoodieMockData.groceryProducts.take(5)) { product ->
                        val qty = cartItems.find { it.id == product.id }?.quantity ?: 0
                        Box(modifier = Modifier.width(145.dp)) {
                            GroceryProductCard(
                                product = product,
                                quantityInCart = qty,
                                onAddToCart = {
                                    onAddToCart(
                                        CartItem(
                                            id = product.id,
                                            serviceType = ServiceType.GROCERY,
                                            title = product.name,
                                            subtitle = product.unit,
                                            price = product.price,
                                            quantity = 1,
                                            imageUrl = product.imageUrl
                                        )
                                    )
                                },
                                onIncrement = { onIncrementCart(product.id) },
                                onDecrement = { onDecrementCart(product.id) },
                                onClick = { onGroceryClick(product) }
                            )
                        }
                    }
                }
            }
        }

        // Popular Restaurants Header
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Popular Restaurants",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = FoodieTextPrimary
                    )

                    Text(
                        text = "See All",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = FoodiePrimary,
                        modifier = Modifier.clickable { onNavigateToTab(ScreenTab.FOOD) }
                    )
                }
            }
        }

        // Restaurant Cards with clean padding
        items(FoodieMockData.restaurants) { rest ->
            Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)) {
                RestaurantCard(
                    restaurant = rest,
                    onClick = { onRestaurantClick(rest) }
                )
            }
        }
    }
}

@Composable
fun MinimalServiceTile(
    title: String,
    subtitle: String,
    icon: ImageVector,
    accentColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        color = Color(0xFFFAFAFA),
        border = BorderStroke(1.dp, FoodieBorder)
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 14.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .background(accentColor.copy(alpha = 0.1f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = accentColor,
                    modifier = Modifier.size(22.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = title,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = FoodieTextPrimary
            )
            Text(
                text = subtitle,
                fontSize = 10.sp,
                color = FoodieTextSecondary
            )
        }
    }
}
