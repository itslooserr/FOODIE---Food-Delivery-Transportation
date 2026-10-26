package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.FoodieMockData
import com.example.model.*
import com.example.ui.theme.*

@Composable
fun RideScreen(
    pickupLocation: String,
    onPickupChange: (String) -> Unit,
    dropLocation: String,
    onDropChange: (String) -> Unit,
    onSwapLocations: () -> Unit,
    selectedRideType: RideVehicleType,
    onSelectRideType: (RideVehicleType) -> Unit,
    onBookRide: () -> Unit,
    recentLocations: List<String>,
    onSelectRecentLocation: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val rideOptions = remember { FoodieMockData.getRideOptions(distanceKm = 5.2) }
    val selectedOption = remember(selectedRideType, rideOptions) {
        rideOptions.find { it.type == selectedRideType } ?: rideOptions.first()
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(FoodieBackground),
        contentPadding = PaddingValues(bottom = 90.dp)
    ) {
        // Location inputs Card
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Where are you going?",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = FoodieTextPrimary
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Visual Route dots
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(vertical = 4.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(12.dp)
                                    .background(FoodieSuccess, CircleShape)
                            )
                            Box(
                                modifier = Modifier
                                    .width(2.dp)
                                    .height(36.dp)
                                    .background(FoodieBorder)
                            )
                            Box(
                                modifier = Modifier
                                    .size(12.dp)
                                    .background(FoodiePrimary, CircleShape)
                            )
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            // Pickup Field
                            OutlinedTextField(
                                value = pickupLocation,
                                onValueChange = onPickupChange,
                                placeholder = { Text("Enter pickup location", color = FoodieTextMuted) },
                                singleLine = true,
                                shape = RoundedCornerShape(10.dp),
                                textStyle = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium, color = FoodieTextPrimary),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedTextColor = FoodieTextPrimary,
                                    unfocusedTextColor = FoodieTextPrimary,
                                    focusedBorderColor = FoodiePrimary,
                                    unfocusedBorderColor = FoodieBorder
                                ),
                                modifier = Modifier.fillMaxWidth().testTag("pickup_input")
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            // Destination Field
                            OutlinedTextField(
                                value = dropLocation,
                                onValueChange = onDropChange,
                                placeholder = { Text("Enter destination", color = FoodieTextMuted) },
                                singleLine = true,
                                shape = RoundedCornerShape(10.dp),
                                textStyle = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium, color = FoodieTextPrimary),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedTextColor = FoodieTextPrimary,
                                    unfocusedTextColor = FoodieTextPrimary,
                                    focusedBorderColor = FoodiePrimary,
                                    unfocusedBorderColor = FoodieBorder
                                ),
                                modifier = Modifier.fillMaxWidth().testTag("drop_input")
                            )
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        // Swap button
                        IconButton(
                            onClick = onSwapLocations,
                            modifier = Modifier
                                .size(40.dp)
                                .background(FoodiePrimaryLight, CircleShape)
                                .testTag("swap_locations_btn")
                        ) {
                            Icon(
                                imageVector = Icons.Default.SwapVert,
                                contentDescription = "Swap Locations",
                                tint = FoodiePrimary
                            )
                        }
                    }
                }
            }
        }

        // Illustrative Route Preview Map Card
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(1.dp, FoodieBorder)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.DirectionsCar,
                                contentDescription = null,
                                tint = FoodiePrimary,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Route Preview • 5.2 km",
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.titleSmall,
                                color = FoodieTextPrimary
                            )
                        }
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = FoodiePrimaryLight
                        ) {
                            Text(
                                text = "Est. 16 mins",
                                color = FoodiePrimary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.sp,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Minimal Canvas Route Graphic
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(90.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFFF1F5F9))
                    ) {
                        Canvas(modifier = Modifier.fillMaxSize()) {
                            // Street grid background
                            drawLine(Color(0xFFE2E8F0), Offset(0f, size.height * 0.3f), Offset(size.width, size.height * 0.3f), strokeWidth = 8f)
                            drawLine(Color(0xFFE2E8F0), Offset(0f, size.height * 0.7f), Offset(size.width, size.height * 0.7f), strokeWidth = 8f)
                            drawLine(Color(0xFFE2E8F0), Offset(size.width * 0.25f, 0f), Offset(size.width * 0.25f, size.height), strokeWidth = 8f)
                            drawLine(Color(0xFFE2E8F0), Offset(size.width * 0.7f, 0f), Offset(size.width * 0.7f, size.height), strokeWidth = 8f)

                            // Route path line (highlighted)
                            val pathEffect = PathEffect.dashPathEffect(floatArrayOf(15f, 10f), 0f)
                            drawLine(
                                color = Color(0xFFEF3900),
                                start = Offset(size.width * 0.15f, size.height * 0.65f),
                                end = Offset(size.width * 0.82f, size.height * 0.35f),
                                strokeWidth = 6f
                            )

                            // Pickup circle
                            drawCircle(color = Color(0xFF16A34A), radius = 12f, center = Offset(size.width * 0.15f, size.height * 0.65f))
                            drawCircle(color = Color.White, radius = 5f, center = Offset(size.width * 0.15f, size.height * 0.65f))

                            // Drop pin
                            drawCircle(color = Color(0xFFEF3900), radius = 12f, center = Offset(size.width * 0.82f, size.height * 0.35f))
                            drawCircle(color = Color.White, radius = 5f, center = Offset(size.width * 0.82f, size.height * 0.35f))
                        }

                        Text(
                            text = "LIVE TRAFFIC NORMAL",
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF64748B),
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(8.dp)
                        )
                    }
                }
            }
        }

        // Available Ride Types Header
        item {
            PaddingValues(horizontal = 16.dp, vertical = 8.dp).let {
                Text(
                    text = "SELECT RIDE TYPE",
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    color = FoodieTextMuted,
                    letterSpacing = 1.sp,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }
        }

        // Ride Option Cards
        items(rideOptions) { option ->
            val isSelected = option.type == selectedRideType
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp)
                    .clickable { onSelectRideType(option.type) }
                    .testTag("ride_option_${option.type.name.lowercase()}"),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (isSelected) FoodiePrimaryLight else Color.White
                ),
                border = BorderStroke(
                    1.5.dp,
                    if (isSelected) FoodiePrimary else FoodieBorder
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .background(
                                if (isSelected) FoodiePrimary else Color(0xFFF3F4F6),
                                CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = when (option.type) {
                                RideVehicleType.BIKE -> Icons.Default.TwoWheeler
                                RideVehicleType.AUTO -> Icons.Default.ElectricRickshaw
                                RideVehicleType.CAB_ECONOMY -> Icons.Default.DirectionsCar
                                RideVehicleType.CAB_PREMIUM -> Icons.Default.AirportShuttle
                            },
                            contentDescription = option.title,
                            tint = if (isSelected) Color.White else FoodieTextPrimary,
                            modifier = Modifier.size(26.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(14.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = option.title,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = FoodieTextPrimary
                            )
                            if (!option.tag.isNullOrBlank()) {
                                Spacer(modifier = Modifier.width(6.dp))
                                Surface(
                                    shape = RoundedCornerShape(4.dp),
                                    color = if (option.tag == "FASTEST") FoodieSuccessLight else FoodieWarningLight
                                ) {
                                    Text(
                                        text = option.tag,
                                        color = if (option.tag == "FASTEST") FoodieSuccess else FoodieWarning,
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(2.dp))

                        Text(
                            text = "${option.etaMinutes} mins away • ${option.capacity}",
                            style = MaterialTheme.typography.bodySmall,
                            color = FoodieTextSecondary
                        )

                        Text(
                            text = option.subtitle,
                            fontSize = 11.sp,
                            color = FoodieTextMuted
                        )
                    }

                    Column(horizontalAlignment = Alignment.End) {
                        Text(
                            text = "₹${option.price}",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = if (isSelected) FoodiePrimary else FoodieTextPrimary
                        )
                        Text(
                            text = "Guaranteed fare",
                            style = MaterialTheme.typography.labelSmall,
                            color = FoodieTextMuted,
                            fontSize = 9.sp
                        )
                    }
                }
            }
        }

        // Book Ride CTA Button
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Button(
                    onClick = onBookRide,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp)
                        .testTag("book_ride_btn"),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = FoodiePrimary)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Book ${selectedOption.title} • ₹${selectedOption.price}",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }
    }
}

/**
 * Live Active Ride Tracking Screen with Captain / Driver details & arrival timer
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActiveRideScreen(
    rideInfo: ActiveRideInfo,
    onBackClick: () -> Unit,
    onCancelClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Ride in Progress", fontWeight = FontWeight.Bold, color = FoodieTextPrimary) },
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
            // OTP Highlight Card
            item {
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = FoodiePrimary,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(
                                text = "START RIDE OTP",
                                color = Color.White.copy(alpha = 0.8f),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = rideInfo.otp,
                                color = Color.White,
                                fontSize = 32.sp,
                                fontWeight = FontWeight.Black,
                                letterSpacing = 4.sp
                            )
                        }

                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = Color.White.copy(alpha = 0.2f)
                        ) {
                            Text(
                                text = "Share with captain only upon arrival",
                                color = Color.White,
                                fontSize = 10.sp,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp)
                            )
                        }
                    }
                }
            }

            // Status Timeline Card
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = when (rideInfo.status) {
                                    RideStatus.DRIVER_ASSIGNED -> "Captain is on the way"
                                    RideStatus.ARRIVING -> "Captain has arrived!"
                                    RideStatus.IN_TRANSIT -> "Trip in progress"
                                    RideStatus.COMPLETED -> "Trip completed"
                                    else -> "Booking Confirmed"
                                },
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = FoodieTextPrimary
                            )

                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = FoodieSuccessLight
                            ) {
                                Text(
                                    text = if (rideInfo.status == RideStatus.ARRIVING) "Arrived" else "${rideInfo.etaMins} mins",
                                    color = FoodieSuccess,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))
                        LinearProgressIndicator(
                            progress = {
                                when (rideInfo.status) {
                                    RideStatus.DRIVER_ASSIGNED -> 0.35f
                                    RideStatus.ARRIVING -> 0.65f
                                    RideStatus.IN_TRANSIT -> 0.9f
                                    else -> 1.0f
                                }
                            },
                            modifier = Modifier.fillMaxWidth().height(6.dp).clip(RoundedCornerShape(3.dp)),
                            color = FoodiePrimary,
                            trackColor = FoodieBorder
                        )
                    }
                }
            }

            // Driver & Vehicle Card
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(54.dp)
                                    .background(FoodiePrimaryLight, CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Person,
                                    contentDescription = null,
                                    tint = FoodiePrimary,
                                    modifier = Modifier.size(32.dp)
                                )
                            }

                            Spacer(modifier = Modifier.width(14.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = rideInfo.driverName,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = FoodieTextPrimary
                                )
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.Star,
                                        contentDescription = null,
                                        tint = FoodieWarning,
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Spacer(modifier = Modifier.width(3.dp))
                                    Text(
                                        text = "${rideInfo.driverRating} • 1,420+ rides",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = FoodieTextSecondary
                                    )
                                }
                            }

                            Row {
                                IconButton(
                                    onClick = { /* Call driver */ },
                                    modifier = Modifier
                                        .size(40.dp)
                                        .background(FoodieSuccessLight, CircleShape)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Call,
                                        contentDescription = "Call Driver",
                                        tint = FoodieSuccess
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))
                        HorizontalDivider(color = FoodieBorder)
                        Spacer(modifier = Modifier.height(14.dp))

                        // Vehicle Info
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = rideInfo.vehicleName,
                                    fontWeight = FontWeight.Bold,
                                    color = FoodieTextPrimary
                                )
                                Text(
                                    text = "Helmet & Sanitized • Verified Driver",
                                    fontSize = 11.sp,
                                    color = FoodieTextSecondary
                                )
                            }

                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = Color(0xFFFEF08A),
                                border = BorderStroke(1.dp, Color(0xFFEAB308))
                            ) {
                                Text(
                                    text = rideInfo.vehicleNumber,
                                    fontWeight = FontWeight.Black,
                                    fontSize = 13.sp,
                                    color = Color(0xFF713F12),
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                        }
                    }
                }
            }

            // Fare & Destination summary
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = BorderStroke(1.dp, FoodieBorder),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Total Fare (Cash/UPI)", color = FoodieTextSecondary, fontSize = 13.sp)
                            Text("₹${rideInfo.fare}", fontWeight = FontWeight.Bold, color = FoodieTextPrimary, fontSize = 15.sp)
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Drop Destination", color = FoodieTextSecondary, fontSize = 13.sp)
                            Text(
                                text = rideInfo.destination,
                                fontWeight = FontWeight.Medium,
                                color = FoodieTextPrimary,
                                fontSize = 13.sp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.weight(1f, fill = false).padding(start = 12.dp)
                            )
                        }
                    }
                }
            }

            // Cancel Ride Button
            item {
                OutlinedButton(
                    onClick = onCancelClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("cancel_ride_btn"),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = FoodieError),
                    border = BorderStroke(1.dp, FoodieError.copy(alpha = 0.5f))
                ) {
                    Text("Cancel Ride", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
