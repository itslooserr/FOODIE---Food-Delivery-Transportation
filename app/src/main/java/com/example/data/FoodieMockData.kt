package com.example.data

import com.example.model.*

object FoodieMockData {

    val savedAddresses = listOf(
        SavedAddress(
            id = "addr_1",
            label = "Home",
            addressLine = "Flat 402, Sunshine Heights, Outer Ring Road",
            landmark = "Near Metro Station, New Delhi",
            isDefault = true
        ),
        SavedAddress(
            id = "addr_2",
            label = "Work",
            addressLine = "Tower B, Cyber City Tech Park, DLF Phase 2",
            landmark = "Opposite Gate 3, Gurugram",
            isDefault = false
        ),
        SavedAddress(
            id = "addr_3",
            label = "Gym",
            addressLine = "Fitness First, Sector 29",
            landmark = "Behind Galleria Market",
            isDefault = false
        )
    )

    val foodCategories = listOf(
        "All",
        "Biryani",
        "Pizza",
        "Burgers",
        "South Indian",
        "North Indian",
        "Chinese",
        "Desserts"
    )

    val restaurants = listOf(
        Restaurant(
            id = "rest_1",
            name = "Biryani by Kilo",
            cuisine = "Biryani, Mughlai, Kebabs",
            rating = 4.7,
            ratingCount = 2840,
            deliveryTimeMins = 32,
            deliveryFee = 29,
            priceForTwo = 600,
            imageUrl = "https://images.unsplash.com/photo-1563379091339-03b21ab4a4f8?w=700&auto=format&fit=crop&q=80",
            isPromoted = true,
            discountTag = "60% OFF up to ₹120"
        ),
        Restaurant(
            id = "rest_2",
            name = "Pizza Hub & Italian Bakes",
            cuisine = "Pizza, Pasta, Garlic Breads",
            rating = 4.5,
            ratingCount = 1920,
            deliveryTimeMins = 25,
            deliveryFee = 0,
            priceForTwo = 450,
            imageUrl = "https://images.unsplash.com/photo-1513104890138-7c749659a591?w=700&auto=format&fit=crop&q=80",
            discountTag = "Free Delivery"
        ),
        Restaurant(
            id = "rest_3",
            name = "Haldiram's Sweets & Thali",
            cuisine = "North Indian, Street Food, Mithai",
            rating = 4.8,
            ratingCount = 5410,
            deliveryTimeMins = 20,
            deliveryFee = 19,
            priceForTwo = 350,
            imageUrl = "https://images.unsplash.com/photo-1601050690597-df0568f70950?w=700&auto=format&fit=crop&q=80",
            isPromoted = true,
            discountTag = "Flat ₹50 OFF"
        ),
        Restaurant(
            id = "rest_4",
            name = "Wow! Momo & Chinese Wok",
            cuisine = "Momos, Noodles, Tibetan",
            rating = 4.4,
            ratingCount = 1430,
            deliveryTimeMins = 28,
            deliveryFee = 25,
            priceForTwo = 300,
            imageUrl = "https://images.unsplash.com/photo-1534422298391-e4f8c172dddb?w=700&auto=format&fit=crop&q=80",
            discountTag = "Buy 1 Get 1 Free"
        ),
        Restaurant(
            id = "rest_5",
            name = "Sagar Ratna South Indian",
            cuisine = "Dosa, Idli, Filter Coffee, Vada",
            rating = 4.6,
            ratingCount = 3120,
            deliveryTimeMins = 22,
            deliveryFee = 15,
            priceForTwo = 400,
            imageUrl = "https://images.unsplash.com/photo-1668236543090-82eba5ee5976?w=700&auto=format&fit=crop&q=80",
            discountTag = "20% OFF on Orders > ₹299"
        ),
        Restaurant(
            id = "rest_6",
            name = "Burger Club & Fries",
            cuisine = "Gourmet Burgers, Shakes, Crispy Fries",
            rating = 4.3,
            ratingCount = 890,
            deliveryTimeMins = 24,
            deliveryFee = 0,
            priceForTwo = 380,
            imageUrl = "https://images.unsplash.com/photo-1568901346375-23c9450c58cd?w=700&auto=format&fit=crop&q=80",
            discountTag = "Free Delivery"
        ),
        Restaurant(
            id = "rest_7",
            name = "Bakingo Artisan Cakes",
            cuisine = "Desserts, Cheesecakes, Pastries",
            rating = 4.9,
            ratingCount = 4210,
            deliveryTimeMins = 30,
            deliveryFee = 35,
            priceForTwo = 500,
            imageUrl = "https://images.unsplash.com/photo-1578985545062-69928b1d9587?w=700&auto=format&fit=crop&q=80",
            discountTag = "Special Treats 15% OFF"
        )
    )

    val menuItems = listOf(
        // Biryani by Kilo
        MenuItem(
            id = "menu_101",
            restaurantId = "rest_1",
            name = "Hyderabadi Dum Biryani (Chicken)",
            description = "Slow cooked aromatic basmati rice layered with tender marinated chicken and secret whole spices in a handi. Served with salan and raita.",
            price = 380,
            isVeg = false,
            rating = 4.8,
            imageUrl = "https://images.unsplash.com/photo-1563379091339-03b21ab4a4f8?w=600&auto=format&fit=crop&q=80",
            category = "Biryani",
            isBestseller = true
        ),
        MenuItem(
            id = "menu_102",
            restaurantId = "rest_1",
            name = "Royal Paneer Tikka Dum Biryani",
            description = "Smoky charcoal tandoori paneer cubes layered with aromatic saffron rice, golden fried onions and fresh mint.",
            price = 340,
            isVeg = true,
            rating = 4.7,
            imageUrl = "https://images.unsplash.com/photo-1633945274405-b6c8069047b0?w=600&auto=format&fit=crop&q=80",
            category = "Biryani",
            isBestseller = true
        ),
        MenuItem(
            id = "menu_103",
            restaurantId = "rest_1",
            name = "Galouti Kebab (4 pcs)",
            description = "Melt-in-mouth spiced kebabs with saffron ulte tawa ka paratha.",
            price = 280,
            isVeg = false,
            rating = 4.6,
            imageUrl = "https://images.unsplash.com/photo-1544025162-d76694265947?w=600&auto=format&fit=crop&q=80",
            category = "Starters"
        ),
        MenuItem(
            id = "menu_104",
            restaurantId = "rest_1",
            name = "Shahi Tukda & Rabri",
            description = "Crispy golden toast soaked in fragrant cardamom saffron syrup, topped with thick rabri and pistachios.",
            price = 160,
            isVeg = true,
            rating = 4.9,
            imageUrl = "https://images.unsplash.com/photo-1589301760014-d929f3979dbc?w=600&auto=format&fit=crop&q=80",
            category = "Desserts"
        ),

        // Pizza Hub
        MenuItem(
            id = "menu_201",
            restaurantId = "rest_2",
            name = "Farmhouse Margherita Deluxe",
            description = "San Marzano tomato sauce, double mozzarella, fresh basil, bell peppers, button mushrooms, and sweet corn.",
            price = 299,
            isVeg = true,
            rating = 4.7,
            imageUrl = "https://images.unsplash.com/photo-1513104890138-7c749659a591?w=600&auto=format&fit=crop&q=80",
            category = "Pizza",
            isBestseller = true
        ),
        MenuItem(
            id = "menu_202",
            restaurantId = "rest_2",
            name = "BBQ Chicken Feast Pizza",
            description = "Loaded with barbecue roasted chicken, smoked red onions, jalapenos, and melted gouda cheese.",
            price = 379,
            isVeg = false,
            rating = 4.8,
            imageUrl = "https://images.unsplash.com/photo-1565299624946-b28f40a0ae38?w=600&auto=format&fit=crop&q=80",
            category = "Pizza",
            isBestseller = true
        ),
        MenuItem(
            id = "menu_203",
            restaurantId = "rest_2",
            name = "Cheesy Garlic Breadsticks",
            description = "Freshly baked artisan bread brushed with herb butter and stuffed with molten mozzarella.",
            price = 149,
            isVeg = true,
            rating = 4.6,
            imageUrl = "https://images.unsplash.com/photo-1619881589139-4d6934c568ec?w=600&auto=format&fit=crop&q=80",
            category = "Sides"
        ),

        // Haldiram's
        MenuItem(
            id = "menu_301",
            restaurantId = "rest_3",
            name = "Special North Indian Deluxe Thali",
            description = "Dal Makhani, Shahi Paneer, Mix Veg, Pulao, 2 Butter Naan, Boondi Raita, Gulab Jamun, and Papad.",
            price = 320,
            isVeg = true,
            rating = 4.9,
            imageUrl = "https://images.unsplash.com/photo-1626777552726-4a6b54c97e46?w=600&auto=format&fit=crop&q=80",
            category = "Thali",
            isBestseller = true
        ),
        MenuItem(
            id = "menu_302",
            restaurantId = "rest_3",
            name = "Dahi Bhalla Papdi Chaat",
            description = "Soft lentil dumplings drenched in sweetened whipped yogurt, tangy tamarind chutney, and crisp papdi.",
            price = 130,
            isVeg = true,
            rating = 4.8,
            imageUrl = "https://images.unsplash.com/photo-1601050690597-df0568f70950?w=600&auto=format&fit=crop&q=80",
            category = "Chaat"
        ),
        MenuItem(
            id = "menu_303",
            restaurantId = "rest_3",
            name = "Hot Chole Bhature (2 pcs)",
            description = "Spicy Amritsari chickpeas cooked in dark roast spices, served with fluffy hot bhature and pickle.",
            price = 180,
            isVeg = true,
            rating = 4.8,
            imageUrl = "https://images.unsplash.com/photo-1626132647523-66f5bf380027?w=600&auto=format&fit=crop&q=80",
            category = "North Indian",
            isBestseller = true
        ),

        // Sagar Ratna
        MenuItem(
            id = "menu_401",
            restaurantId = "rest_5",
            name = "Ghee Roast Masala Dosa",
            description = "Crispy golden crepe roasted in pure Desi ghee, stuffed with spiced potato masala, served with 3 chutneys & sambar.",
            price = 195,
            isVeg = true,
            rating = 4.9,
            imageUrl = "https://images.unsplash.com/photo-1668236543090-82eba5ee5976?w=600&auto=format&fit=crop&q=80",
            category = "South Indian",
            isBestseller = true
        ),
        MenuItem(
            id = "menu_402",
            restaurantId = "rest_5",
            name = "Steamed Idli & Crispy Medu Vada Combo",
            description = "Two fluffy steamed rice cakes with one crunchy cumin-pepper lentil fritter and piping hot drumstick sambar.",
            price = 150,
            isVeg = true,
            rating = 4.7,
            imageUrl = "https://images.unsplash.com/photo-1589301760014-d929f3979dbc?w=600&auto=format&fit=crop&q=80",
            category = "South Indian"
        ),
        MenuItem(
            id = "menu_403",
            restaurantId = "rest_5",
            name = "Authentic Degree Filter Coffee",
            description = "Traditional South Indian chicory blend freshly brewed with frothed full-cream milk.",
            price = 70,
            isVeg = true,
            rating = 4.9,
            imageUrl = "https://images.unsplash.com/photo-1514432324607-a09d9b4aefdd?w=600&auto=format&fit=crop&q=80",
            category = "Beverages"
        )
    )

    val groceryCategories = listOf(
        GroceryCategory("cat_all", "All", "all", 48),
        GroceryCategory("cat_fv", "Fruits & Veg", "fruits", 16),
        GroceryCategory("cat_dairy", "Dairy & Eggs", "dairy", 12),
        GroceryCategory("cat_snacks", "Snacks & Munchies", "snacks", 14),
        GroceryCategory("cat_bev", "Beverages", "beverages", 10),
        GroceryCategory("cat_staples", "Atta, Dal & Rice", "staples", 18),
        GroceryCategory("cat_care", "Personal Care", "care", 8)
    )

    val groceryProducts = listOf(
        GroceryProduct(
            id = "groc_1",
            name = "Farm Fresh Red Tomatoes (Hybrid)",
            unit = "1 kg",
            price = 38,
            originalPrice = 55,
            discountPercent = 30,
            categoryId = "cat_fv",
            imageUrl = "https://images.unsplash.com/photo-1592924357228-91a4daadcfea?w=500&auto=format&fit=crop&q=80",
            deliveryTimeMins = 10,
            rating = 4.8
        ),
        GroceryProduct(
            id = "groc_2",
            name = "Fresh Cavendish Bananas",
            unit = "1 kg (approx 6-8 pcs)",
            price = 52,
            originalPrice = 70,
            discountPercent = 25,
            categoryId = "cat_fv",
            imageUrl = "https://images.unsplash.com/photo-1571771894821-ce9b6c11b08e?w=500&auto=format&fit=crop&q=80",
            deliveryTimeMins = 11,
            rating = 4.9
        ),
        GroceryProduct(
            id = "groc_3",
            name = "Amul Taaza Homogenised Toned Milk",
            unit = "500 ml pouch",
            price = 27,
            originalPrice = 28,
            discountPercent = 4,
            categoryId = "cat_dairy",
            imageUrl = "https://images.unsplash.com/photo-1550583724-b2692b85b150?w=500&auto=format&fit=crop&q=80",
            deliveryTimeMins = 9,
            rating = 4.9
        ),
        GroceryProduct(
            id = "groc_4",
            name = "Amul Salted Butter Block",
            unit = "100 g",
            price = 58,
            originalPrice = 60,
            discountPercent = 3,
            categoryId = "cat_dairy",
            imageUrl = "https://images.unsplash.com/photo-1589985270826-4b7bb135bc9d?w=500&auto=format&fit=crop&q=80",
            deliveryTimeMins = 9,
            rating = 4.9
        ),
        GroceryProduct(
            id = "groc_5",
            name = "Fresh Paneer Malai Block",
            unit = "200 g",
            price = 89,
            originalPrice = 105,
            discountPercent = 15,
            categoryId = "cat_dairy",
            imageUrl = "https://images.unsplash.com/photo-1631452180519-c014fe946bc7?w=500&auto=format&fit=crop&q=80",
            deliveryTimeMins = 10,
            rating = 4.7
        ),
        GroceryProduct(
            id = "groc_6",
            name = "Lay's India's Magic Masala Chips",
            unit = "52 g pouch",
            price = 20,
            originalPrice = 20,
            discountPercent = 0,
            categoryId = "cat_snacks",
            imageUrl = "https://images.unsplash.com/photo-1566478989037-eec170784d0b?w=500&auto=format&fit=crop&q=80",
            deliveryTimeMins = 12,
            rating = 4.8
        ),
        GroceryProduct(
            id = "groc_7",
            name = "Haldiram's Bhujia Sev Namkeen",
            unit = "400 g pack",
            price = 115,
            originalPrice = 135,
            discountPercent = 15,
            categoryId = "cat_snacks",
            imageUrl = "https://images.unsplash.com/photo-1621996346565-e3d5d6281691?w=500&auto=format&fit=crop&q=80",
            deliveryTimeMins = 12,
            rating = 4.8
        ),
        GroceryProduct(
            id = "groc_8",
            name = "Coca-Cola Zero Sugar Can",
            unit = "300 ml",
            price = 40,
            originalPrice = 40,
            discountPercent = 0,
            categoryId = "cat_bev",
            imageUrl = "https://images.unsplash.com/photo-1622483767028-3f66f32aef97?w=500&auto=format&fit=crop&q=80",
            deliveryTimeMins = 8,
            rating = 4.7
        ),
        GroceryProduct(
            id = "groc_9",
            name = "Tata Tea Gold Leaf Pack",
            unit = "500 g",
            price = 285,
            originalPrice = 330,
            discountPercent = 14,
            categoryId = "cat_bev",
            imageUrl = "https://images.unsplash.com/photo-1576092768241-dec231879fc3?w=500&auto=format&fit=crop&q=80",
            deliveryTimeMins = 10,
            rating = 4.9
        ),
        GroceryProduct(
            id = "groc_10",
            name = "Aashirvaad Shudh Chakki Atta",
            unit = "5 kg bag",
            price = 245,
            originalPrice = 290,
            discountPercent = 15,
            categoryId = "cat_staples",
            imageUrl = "https://images.unsplash.com/photo-1509440159596-0249088772ff?w=500&auto=format&fit=crop&q=80",
            deliveryTimeMins = 15,
            rating = 4.9
        ),
        GroceryProduct(
            id = "groc_11",
            name = "Fortune Sunlite Refined Sunflower Oil",
            unit = "1 Litre pouch",
            price = 139,
            originalPrice = 165,
            discountPercent = 16,
            categoryId = "cat_staples",
            imageUrl = "https://images.unsplash.com/photo-1474979266404-7eaacbcd87c5?w=500&auto=format&fit=crop&q=80",
            deliveryTimeMins = 12,
            rating = 4.8
        ),
        GroceryProduct(
            id = "groc_12",
            name = "Dettol Original Germ Protection Soap",
            unit = "125 g x 3 bars",
            price = 135,
            originalPrice = 160,
            discountPercent = 15,
            categoryId = "cat_care",
            imageUrl = "https://images.unsplash.com/photo-1607006314594-ee8028ff55dc?w=500&auto=format&fit=crop&q=80",
            deliveryTimeMins = 10,
            rating = 4.9
        )
    )

    fun getRideOptions(distanceKm: Double = 5.2): List<RideOption> {
        return listOf(
            RideOption(
                type = RideVehicleType.BIKE,
                title = "Bike Taxi",
                subtitle = "Beat traffic fast • Helmet provided",
                price = (35 + distanceKm * 8.5).toInt(),
                etaMinutes = 3,
                distanceKm = distanceKm,
                capacity = "1 Person",
                tag = "FASTEST"
            ),
            RideOption(
                type = RideVehicleType.AUTO,
                title = "Auto Rickshaw",
                subtitle = "Doorstep pickup • Metred & safe",
                price = (55 + distanceKm * 12.0).toInt(),
                etaMinutes = 5,
                distanceKm = distanceKm,
                capacity = "3 Persons",
                tag = "MOST POPULAR"
            ),
            RideOption(
                type = RideVehicleType.CAB_ECONOMY,
                title = "Cab Mini / Hatchback",
                subtitle = "AC Ride • Affordable compact car",
                price = (120 + distanceKm * 18.0).toInt(),
                etaMinutes = 7,
                distanceKm = distanceKm,
                capacity = "4 Persons"
            ),
            RideOption(
                type = RideVehicleType.CAB_PREMIUM,
                title = "Cab Prime Sedan",
                subtitle = "Top rated drivers • Extra legroom",
                price = (180 + distanceKm * 24.0).toInt(),
                etaMinutes = 6,
                distanceKm = distanceKm,
                capacity = "4 Persons",
                tag = "PREMIUM"
            )
        )
    }

    val recentRideLocations = listOf(
        "Rajiv Chowk Metro Gate 5, Connaught Place",
        "Indira Gandhi International Airport, Terminal 3",
        "Cyber Hub, DLF Cyber City, Gurugram",
        "Select CITYWALK Mall, Saket",
        "New Delhi Railway Station, Paharganj Side"
    )
}
