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
import com.example.ui.components.VegNonVegBadge
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    query: String,
    onQueryChange: (String) -> Unit,
    onBackClick: () -> Unit,
    onRestaurantClick: (Restaurant) -> Unit,
    onProductClick: (GroceryProduct) -> Unit,
    onRideDestination: (String) -> Unit,
    onAddToCart: (CartItem) -> Unit,
    modifier: Modifier = Modifier
) {
    var searchFilter by remember { mutableStateOf("All") }
    val filterTabs = listOf("All", "Food", "Groceries", "Rides")

    val matchingRestaurants = remember(query, searchFilter) {
        if (searchFilter == "Groceries" || searchFilter == "Rides") emptyList()
        else if (query.isBlank()) FoodieMockData.restaurants.take(3)
        else FoodieMockData.restaurants.filter {
            it.name.contains(query, ignoreCase = true) || it.cuisine.contains(query, ignoreCase = true)
        }
    }

    val matchingDishes = remember(query, searchFilter) {
        if (searchFilter == "Groceries" || searchFilter == "Rides") emptyList()
        else if (query.isBlank()) FoodieMockData.menuItems.take(4)
        else FoodieMockData.menuItems.filter {
            it.name.contains(query, ignoreCase = true) || it.category.contains(query, ignoreCase = true)
        }
    }

    val matchingGroceries = remember(query, searchFilter) {
        if (searchFilter == "Food" || searchFilter == "Rides") emptyList()
        else if (query.isBlank()) FoodieMockData.groceryProducts.take(4)
        else FoodieMockData.groceryProducts.filter {
            it.name.contains(query, ignoreCase = true)
        }
    }

    val matchingLocations = remember(query, searchFilter) {
        if (searchFilter == "Food" || searchFilter == "Groceries") emptyList()
        else if (query.isBlank()) FoodieMockData.recentRideLocations.take(3)
        else FoodieMockData.recentRideLocations.filter {
            it.contains(query, ignoreCase = true)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    OutlinedTextField(
                        value = query,
                        onValueChange = onQueryChange,
                        placeholder = { Text("Search biryani, milk, cabs...", fontSize = 14.sp, color = FoodieTextMuted) },
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                        leadingIcon = {
                            Icon(Icons.Default.Search, contentDescription = null, tint = FoodiePrimary)
                        },
                        trailingIcon = {
                            if (query.isNotEmpty()) {
                                IconButton(onClick = { onQueryChange("") }) {
                                    Icon(Icons.Default.Close, contentDescription = "Clear", tint = FoodieTextMuted)
                                }
                            }
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = FoodieTextPrimary,
                            unfocusedTextColor = FoodieTextPrimary,
                            focusedBorderColor = FoodiePrimary,
                            unfocusedBorderColor = FoodieBorder
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .testTag("search_input_field")
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick, modifier = Modifier.testTag("search_back_btn")) {
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
            // Category Filter Rail
            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(filterTabs) { tab ->
                        val isSelected = searchFilter == tab
                        FilterChip(
                            selected = isSelected,
                            onClick = { searchFilter = tab },
                            label = { Text(tab, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium) },
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
                            shape = RoundedCornerShape(20.dp)
                        )
                    }
                }
            }

            // Restaurants Section
            if (matchingRestaurants.isNotEmpty()) {
                item {
                    Text(
                        text = "RESTAURANTS",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = FoodieTextMuted,
                        letterSpacing = 1.sp
                    )
                }

                items(matchingRestaurants) { rest ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onRestaurantClick(rest) },
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        border = BorderStroke(1.dp, FoodieBorder)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(50.dp)
                                    .clip(RoundedCornerShape(8.dp))
                            ) {
                                AsyncImage(
                                    model = rest.imageUrl,
                                    contentDescription = rest.name,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = rest.name,
                                    fontWeight = FontWeight.Bold,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = FoodieTextPrimary
                                )
                                Text(
                                    text = "${rest.cuisine} • ${rest.deliveryTimeMins} mins",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = FoodieTextSecondary
                                )
                            }
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = FoodieSuccess
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text("${rest.rating}", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                    Spacer(modifier = Modifier.width(2.dp))
                                    Icon(Icons.Default.Star, contentDescription = null, tint = Color.White, modifier = Modifier.size(10.dp))
                                }
                            }
                        }
                    }
                }
            }

            // Dishes Section
            if (matchingDishes.isNotEmpty()) {
                item {
                    Text(
                        text = "POPULAR DISHES",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = FoodieTextMuted,
                        letterSpacing = 1.sp
                    )
                }

                items(matchingDishes) { dish ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        border = BorderStroke(1.dp, FoodieBorder)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            VegNonVegBadge(isVeg = dish.isVeg)
                            Spacer(modifier = Modifier.width(10.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = dish.name,
                                    fontWeight = FontWeight.Bold,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = FoodieTextPrimary
                                )
                                Text(
                                    text = "₹${dish.price}",
                                    style = MaterialTheme.typography.bodySmall,
                                    fontWeight = FontWeight.SemiBold,
                                    color = FoodieTextSecondary
                                )
                            }
                            OutlinedButton(
                                onClick = {
                                    onAddToCart(
                                        CartItem(
                                            id = dish.id,
                                            serviceType = ServiceType.FOOD,
                                            title = dish.name,
                                            subtitle = "Dishes",
                                            price = dish.price,
                                            quantity = 1,
                                            imageUrl = dish.imageUrl,
                                            isVeg = dish.isVeg
                                        )
                                    )
                                },
                                shape = RoundedCornerShape(8.dp),
                                colors = ButtonDefaults.outlinedButtonColors(
                                    containerColor = FoodiePrimaryLight,
                                    contentColor = FoodiePrimary
                                ),
                                border = BorderStroke(1.dp, FoodiePrimary),
                                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 2.dp),
                                modifier = Modifier.height(30.dp)
                            ) {
                                Text("ADD", fontWeight = FontWeight.Bold, fontSize = 11.sp)
                            }
                        }
                    }
                }
            }

            // Groceries Section
            if (matchingGroceries.isNotEmpty()) {
                item {
                    Text(
                        text = "INSTANT GROCERIES (10 MINS)",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = FoodieTextMuted,
                        letterSpacing = 1.sp
                    )
                }

                items(matchingGroceries) { groc ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onProductClick(groc) },
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        border = BorderStroke(1.dp, FoodieBorder)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(46.dp)
                                    .clip(RoundedCornerShape(8.dp))
                            ) {
                                AsyncImage(
                                    model = groc.imageUrl,
                                    contentDescription = groc.name,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = groc.name,
                                    fontWeight = FontWeight.SemiBold,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = FoodieTextPrimary,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Text(
                                    text = "${groc.unit} • ₹${groc.price}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = FoodieTextSecondary
                                )
                            }
                            OutlinedButton(
                                onClick = {
                                    onAddToCart(
                                        CartItem(
                                            id = groc.id,
                                            serviceType = ServiceType.GROCERY,
                                            title = groc.name,
                                            subtitle = groc.unit,
                                            price = groc.price,
                                            quantity = 1,
                                            imageUrl = groc.imageUrl
                                        )
                                    )
                                },
                                shape = RoundedCornerShape(8.dp),
                                colors = ButtonDefaults.outlinedButtonColors(
                                    containerColor = FoodiePrimaryLight,
                                    contentColor = FoodiePrimary
                                ),
                                border = BorderStroke(1.dp, FoodiePrimary),
                                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 2.dp),
                                modifier = Modifier.height(30.dp)
                            ) {
                                Text("ADD", fontWeight = FontWeight.Bold, fontSize = 11.sp)
                            }
                        }
                    }
                }
            }

            // Ride Destinations
            if (matchingLocations.isNotEmpty()) {
                item {
                    Text(
                        text = "RIDE DESTINATIONS",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = FoodieTextMuted,
                        letterSpacing = 1.sp
                    )
                }

                items(matchingLocations) { dest ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onRideDestination(dest) },
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        border = BorderStroke(1.dp, FoodieBorder)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.Navigation, contentDescription = null, tint = FoodiePrimary, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(12.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = dest,
                                    fontWeight = FontWeight.SemiBold,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = FoodieTextPrimary
                                )
                                Text(
                                    text = "Book Bike, Auto or Cab",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = FoodieTextSecondary
                                )
                            }
                            Icon(Icons.Default.ChevronRight, contentDescription = null, tint = FoodieTextMuted)
                        }
                    }
                }
            }
        }
    }
}
