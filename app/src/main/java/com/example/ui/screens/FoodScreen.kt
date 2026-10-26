package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.FoodieMockData
import com.example.model.*
import com.example.ui.components.MenuItemCard
import com.example.ui.components.RestaurantCard
import com.example.ui.components.VegNonVegBadge
import com.example.ui.theme.*

@Composable
fun FoodScreen(
    selectedCategory: String,
    onCategorySelect: (String) -> Unit,
    isVegOnly: Boolean,
    onToggleVegOnly: () -> Unit,
    onRestaurantClick: (Restaurant) -> Unit,
    modifier: Modifier = Modifier
) {
    val filteredRestaurants = remember(selectedCategory, isVegOnly) {
        FoodieMockData.restaurants.filter { rest ->
            val matchesCategory = selectedCategory == "All" || rest.cuisine.contains(selectedCategory, ignoreCase = true)
            val matchesVeg = if (isVegOnly) rest.cuisine.contains("Veg", ignoreCase = true) || rest.name.contains("Haldiram", ignoreCase = true) || rest.name.contains("Sagar", ignoreCase = true) else true
            matchesCategory && matchesVeg
        }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(FoodieBackground),
        contentPadding = PaddingValues(bottom = 90.dp)
    ) {
        // Sticky Header or top filter bar
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(vertical = 8.dp)
            ) {
                // Cuisine horizontal chip row
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(FoodieMockData.foodCategories) { category ->
                        val isSelected = selectedCategory == category
                        FilterChip(
                            selected = isSelected,
                            onClick = { onCategorySelect(category) },
                            label = {
                                Text(
                                    text = category,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                    fontSize = 12.sp
                                )
                            },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = FoodiePrimary,
                                selectedLabelColor = Color.White,
                                containerColor = Color.White,
                                labelColor = FoodieTextPrimary
                            ),
                            border = FilterChipDefaults.filterChipBorder(
                                enabled = true,
                                selected = isSelected,
                                borderColor = if (isSelected) FoodiePrimary else FoodieBorder
                            ),
                            shape = RoundedCornerShape(20.dp),
                            modifier = Modifier.testTag("filter_chip_$category")
                        )
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                // Veg only switch row
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        VegNonVegBadge(isVeg = true)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Pure Veg Only",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = FoodieTextPrimary
                        )
                    }

                    Switch(
                        checked = isVegOnly,
                        onCheckedChange = { onToggleVegOnly() },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color.White,
                            checkedTrackColor = FoodieVegGreen,
                            uncheckedThumbColor = Color.White,
                            uncheckedTrackColor = FoodieBorder
                        ),
                        modifier = Modifier.testTag("veg_toggle")
                    )
                }
            }
        }

        // Restaurant Count info
        item {
            PaddingValues(horizontal = 16.dp, vertical = 8.dp).let {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${filteredRestaurants.size} RESTAURANTS DELIVERING",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = FoodieTextMuted,
                        letterSpacing = 1.sp
                    )
                }
            }
        }

        if (filteredRestaurants.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(40.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Default.SearchOff,
                            contentDescription = null,
                            tint = FoodieTextMuted,
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "No restaurants found",
                            fontWeight = FontWeight.Bold,
                            color = FoodieTextPrimary
                        )
                        Text(
                            text = "Try switching filters or turning off Pure Veg",
                            style = MaterialTheme.typography.bodySmall,
                            color = FoodieTextSecondary
                        )
                    }
                }
            }
        } else {
            items(filteredRestaurants) { restaurant ->
                Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)) {
                    RestaurantCard(
                        restaurant = restaurant,
                        onClick = { onRestaurantClick(restaurant) }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RestaurantDetailScreen(
    restaurant: Restaurant,
    onBackClick: () -> Unit,
    cartItems: List<CartItem>,
    onAddToCart: (CartItem) -> Unit,
    onIncrementCart: (String) -> Unit,
    onDecrementCart: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val itemsForRestaurant = remember(restaurant.id) {
        FoodieMockData.menuItems.filter { it.restaurantId == restaurant.id }
            .ifEmpty { FoodieMockData.menuItems.take(4) }
    }

    var selectedMenuCategory by remember { mutableStateOf("All") }
    val categories = remember(itemsForRestaurant) {
        listOf("All") + itemsForRestaurant.map { it.category }.distinct()
    }

    val displayItems = remember(selectedMenuCategory, itemsForRestaurant) {
        if (selectedMenuCategory == "All") itemsForRestaurant
        else itemsForRestaurant.filter { it.category == selectedMenuCategory }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = restaurant.name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = FoodieTextPrimary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick, modifier = Modifier.testTag("restaurant_back_btn")) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = FoodieTextPrimary
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* Share */ }) {
                        Icon(imageVector = Icons.Default.Share, contentDescription = "Share", tint = FoodieTextPrimary)
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
            contentPadding = PaddingValues(bottom = 90.dp)
        ) {
            // Hero banner image
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                ) {
                    AsyncImage(
                        model = restaurant.imageUrl,
                        contentDescription = restaurant.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            // Info Card
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(y = (-20).dp)
                        .padding(horizontal = 16.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = restaurant.name,
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Bold,
                                color = FoodieTextPrimary,
                                modifier = Modifier.weight(1f)
                            )
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = FoodieSuccess
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "${restaurant.rating}",
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 13.sp
                                    )
                                    Spacer(modifier = Modifier.width(3.dp))
                                    Icon(
                                        imageVector = Icons.Default.Star,
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.size(13.dp)
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = restaurant.cuisine,
                            style = MaterialTheme.typography.bodyMedium,
                            color = FoodieTextSecondary
                        )
                        Text(
                            text = restaurant.address,
                            style = MaterialTheme.typography.bodySmall,
                            color = FoodieTextMuted
                        )

                        Spacer(modifier = Modifier.height(12.dp))
                        HorizontalDivider(color = FoodieBorder)
                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = "${restaurant.deliveryTimeMins} mins",
                                    fontWeight = FontWeight.Bold,
                                    color = FoodieTextPrimary
                                )
                                Text("Delivery Time", fontSize = 11.sp, color = FoodieTextSecondary)
                            }
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = if (restaurant.deliveryFee == 0) "Free" else "₹${restaurant.deliveryFee}",
                                    fontWeight = FontWeight.Bold,
                                    color = if (restaurant.deliveryFee == 0) FoodieSuccess else FoodieTextPrimary
                                )
                                Text("Delivery Fee", fontSize = 11.sp, color = FoodieTextSecondary)
                            }
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = "₹${restaurant.priceForTwo}",
                                    fontWeight = FontWeight.Bold,
                                    color = FoodieTextPrimary
                                )
                                Text("For Two", fontSize = 11.sp, color = FoodieTextSecondary)
                            }
                        }
                    }
                }
            }

            // Menu Category Pills
            item {
                LazyRow(
                    modifier = Modifier.padding(vertical = 4.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(categories) { cat ->
                        val isSelected = selectedMenuCategory == cat
                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = if (isSelected) FoodiePrimary else Color.White,
                            border = BorderStroke(1.dp, if (isSelected) FoodiePrimary else FoodieBorder),
                            modifier = Modifier.clickable { selectedMenuCategory = cat }
                        ) {
                            Text(
                                text = cat,
                                color = if (isSelected) Color.White else FoodieTextPrimary,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                fontSize = 12.sp,
                                modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
                            )
                        }
                    }
                }
            }

            item {
                Text(
                    text = "MENU ITEMS (${displayItems.size})",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = FoodieTextMuted,
                    letterSpacing = 1.sp,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }

            // Menu Items List
            items(displayItems) { item ->
                val qty = cartItems.find { it.id == item.id }?.quantity ?: 0
                Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)) {
                    MenuItemCard(
                        item = item,
                        quantityInCart = qty,
                        onAddToCart = {
                            onAddToCart(
                                CartItem(
                                    id = item.id,
                                    serviceType = ServiceType.FOOD,
                                    title = item.name,
                                    subtitle = restaurant.name,
                                    price = item.price,
                                    quantity = 1,
                                    imageUrl = item.imageUrl,
                                    isVeg = item.isVeg
                                )
                            )
                        },
                        onIncrement = { onIncrementCart(item.id) },
                        onDecrement = { onDecrementCart(item.id) }
                    )
                }
            }
        }
    }
}
