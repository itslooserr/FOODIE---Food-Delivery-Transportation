package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.FoodieMockData
import com.example.model.*
import com.example.ui.components.GroceryProductCard
import com.example.ui.theme.*

@Composable
fun GroceryScreen(
    selectedCategory: String,
    onCategorySelect: (String) -> Unit,
    sortOption: String,
    onSortSelect: (String) -> Unit,
    onProductClick: (GroceryProduct) -> Unit,
    cartItems: List<CartItem>,
    onAddToCart: (CartItem) -> Unit,
    onIncrementCart: (String) -> Unit,
    onDecrementCart: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val filteredProducts = remember(selectedCategory, sortOption) {
        val list = if (selectedCategory == "cat_all") {
            FoodieMockData.groceryProducts
        } else {
            FoodieMockData.groceryProducts.filter { it.categoryId == selectedCategory }
        }

        when (sortOption) {
            "Price: Low to High" -> list.sortedBy { it.price }
            "Price: High to Low" -> list.sortedByDescending { it.price }
            else -> list
        }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(FoodieBackground),
        contentPadding = PaddingValues(bottom = 90.dp)
    ) {
        // Instant Commerce Banner
        item {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                shape = RoundedCornerShape(16.dp),
                color = Color(0xFF0F766E)
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Bolt,
                                contentDescription = null,
                                tint = FoodieWarning,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "INSTANT DELIVERY",
                                fontWeight = FontWeight.Black,
                                fontSize = 12.sp,
                                color = Color.White
                            )
                        }
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "Delivering in 10-15 Minutes",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = "Chilled packaging • Freshness guaranteed",
                            fontSize = 11.sp,
                            color = Color.White.copy(alpha = 0.8f)
                        )
                    }

                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .background(Color.White.copy(alpha = 0.2f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.ElectricMoped,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(26.dp)
                        )
                    }
                }
            }
        }

        // Category Rail
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(vertical = 10.dp)
            ) {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(FoodieMockData.groceryCategories) { cat ->
                        val isSelected = selectedCategory == cat.id
                        FilterChip(
                            selected = isSelected,
                            onClick = { onCategorySelect(cat.id) },
                            label = {
                                Text(
                                    text = "${cat.name} (${cat.itemCount})",
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
                            modifier = Modifier.testTag("grocery_chip_${cat.id}")
                        )
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))

                // Sort row
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "${filteredProducts.size} Products",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = FoodieTextMuted
                    )

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Sort: ",
                            style = MaterialTheme.typography.labelSmall,
                            color = FoodieTextSecondary
                        )
                        val sortOptions = listOf("Popular", "Price: Low to High", "Price: High to Low")
                        var expanded by remember { mutableStateOf(false) }
                        Box {
                            TextButton(
                                onClick = { expanded = true },
                                contentPadding = PaddingValues(horizontal = 4.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = sortOption,
                                    fontWeight = FontWeight.Bold,
                                    color = FoodiePrimary,
                                    fontSize = 12.sp
                                )
                                Icon(
                                    imageVector = Icons.Default.ArrowDropDown,
                                    contentDescription = null,
                                    tint = FoodiePrimary,
                                    modifier = Modifier.size(16.dp)
                                )
                            }

                            DropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false },
                                modifier = Modifier.background(Color.White)
                            ) {
                                sortOptions.forEach { opt ->
                                    DropdownMenuItem(
                                        text = { Text(opt, fontSize = 13.sp) },
                                        onClick = {
                                            onSortSelect(opt)
                                            expanded = false
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // 2-Column Product Grid in chunked rows for smooth scrolling inside LazyColumn
        val chunkedProducts = filteredProducts.chunked(2)
        items(chunkedProducts) { rowItems ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                for (product in rowItems) {
                    val qty = cartItems.find { it.id == product.id }?.quantity ?: 0
                    Box(modifier = Modifier.weight(1f)) {
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
                            onClick = { onProductClick(product) }
                        )
                    }
                }
                // Handle odd count in last row
                if (rowItems.size == 1) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroceryProductDetailScreen(
    product: GroceryProduct,
    onBackClick: () -> Unit,
    cartItems: List<CartItem>,
    onAddToCart: (CartItem) -> Unit,
    onIncrementCart: (String) -> Unit,
    onDecrementCart: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val qty = cartItems.find { it.id == product.id }?.quantity ?: 0

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Product Details", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = FoodieTextPrimary) },
                navigationIcon = {
                    IconButton(onClick = onBackClick, modifier = Modifier.testTag("product_back_btn")) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
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
                            text = "₹${product.price}",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = FoodieTextPrimary
                        )
                        Text(
                            text = "Inclusive of all taxes",
                            style = MaterialTheme.typography.labelSmall,
                            color = FoodieTextMuted
                        )
                    }

                    if (qty == 0) {
                        Button(
                            onClick = {
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
                            colors = ButtonDefaults.buttonColors(containerColor = FoodiePrimary),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.height(48.dp)
                        ) {
                            Text("Add to Cart", fontWeight = FontWeight.Bold)
                        }
                    } else {
                        Row(
                            modifier = Modifier
                                .height(44.dp)
                                .background(FoodiePrimary, RoundedCornerShape(12.dp))
                                .padding(horizontal = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(onClick = { onDecrementCart(product.id) }) {
                                Text("-", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                            }
                            Text(
                                text = "$qty in Cart",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 8.dp)
                            )
                            IconButton(onClick = { onIncrementCart(product.id) }) {
                                Text("+", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                            }
                        }
                    }
                }
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.White)
        ) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(260.dp)
                        .background(Color(0xFFFBFBFB)),
                    contentAlignment = Alignment.Center
                ) {
                    AsyncImage(
                        model = product.imageUrl,
                        contentDescription = product.name,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(24.dp)
                    )
                }
            }

            item {
                Column(modifier = Modifier.padding(20.dp)) {
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = FoodieWarningLight
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Bolt,
                                contentDescription = null,
                                tint = FoodieWarning,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "DELIVERING IN ${product.deliveryTimeMins} MINS",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = FoodieTextPrimary
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = product.name,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = FoodieTextPrimary
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = product.unit,
                        style = MaterialTheme.typography.bodyMedium,
                        color = FoodieTextSecondary
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "₹${product.price}",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color = FoodieTextPrimary
                        )
                        if (product.originalPrice > product.price) {
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "₹${product.originalPrice}",
                                style = MaterialTheme.typography.titleMedium,
                                color = FoodieTextMuted,
                                textDecoration = TextDecoration.LineThrough
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Surface(
                                shape = RoundedCornerShape(4.dp),
                                color = FoodiePrimaryLight
                            ) {
                                Text(
                                    text = "${product.discountPercent}% OFF",
                                    color = FoodiePrimary,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 11.sp,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))
                    HorizontalDivider(color = FoodieBorder)
                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Why Shop on FOODIE Grocery?",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = FoodieTextPrimary
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = product.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = FoodieTextSecondary
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Trust Badges
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        TrustBadge(icon = Icons.Default.Verified, title = "100% Quality")
                        TrustBadge(icon = Icons.Default.Speed, title = "10 Min Delivery")
                        TrustBadge(icon = Icons.Default.Autorenew, title = "Easy Return")
                    }
                }
            }
        }
    }
}

@Composable
fun TrustBadge(icon: androidx.compose.ui.graphics.vector.ImageVector, title: String) {
    Surface(
        shape = RoundedCornerShape(10.dp),
        color = FoodieBackground,
        border = BorderStroke(1.dp, FoodieBorder)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(imageVector = icon, contentDescription = null, tint = FoodieSuccess, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text(text = title, fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = FoodieTextPrimary)
        }
    }
}
