package com.healthtracker.util

import com.healthtracker.data.local.entity.FoodEntity
import com.healthtracker.data.local.entity.FoodUnitType

object FoodSeedData {

    val foods = listOf(
        // ===== Tinh bột =====
        FoodEntity(
            name = "Cơm trắng",
            caloriesPerUnit = 130,
            unitType = FoodUnitType.PER_100G,
            unitLabel = "100g",
            imageResName = "food_com_trang"
        ),
        FoodEntity(
            name = "Bánh mì",
            caloriesPerUnit = 265,
            unitType = FoodUnitType.PER_SERVING,
            unitLabel = "ổ",
            imageResName = "food_banh_mi"
        ),
        FoodEntity(
            name = "Xôi mặn",
            caloriesPerUnit = 400,
            unitType = FoodUnitType.PER_SERVING,
            unitLabel = "phần",
            imageResName = "food_xoi_man"
        ),
        FoodEntity(
            name = "Mì gói",
            caloriesPerUnit = 350,
            unitType = FoodUnitType.PER_SERVING,
            unitLabel = "gói",
            imageResName = "food_mi_goi"
        ),
        FoodEntity(
            name = "Khoai lang luộc",
            caloriesPerUnit = 86,
            unitType = FoodUnitType.PER_100G,
            unitLabel = "100g",
            imageResName = "food_khoai_lang"
        ),
        FoodEntity(
            name = "Bắp luộc",
            caloriesPerUnit = 96,
            unitType = FoodUnitType.PER_SERVING,
            unitLabel = "trái",
            imageResName = "food_bap_luoc"
        ),

        // ===== Món chính / đạm =====
        FoodEntity(
            name = "Trứng gà luộc",
            caloriesPerUnit = 78,
            unitType = FoodUnitType.PER_SERVING,
            unitLabel = "quả",
            imageResName = "food_trung_ga"
        ),
        FoodEntity(
            name = "Thịt heo luộc",
            caloriesPerUnit = 242,
            unitType = FoodUnitType.PER_100G,
            unitLabel = "100g",
            imageResName = "food_thit_heo_luoc"
        ),
        FoodEntity(
            name = "Thịt bò xào",
            caloriesPerUnit = 250,
            unitType = FoodUnitType.PER_100G,
            unitLabel = "100g",
            imageResName = "food_thit_bo_xao"
        ),
        FoodEntity(
            name = "Ức gà luộc",
            caloriesPerUnit = 165,
            unitType = FoodUnitType.PER_100G,
            unitLabel = "100g",
            imageResName = "food_uc_ga"
        ),
        FoodEntity(
            name = "Cá basa chiên",
            caloriesPerUnit = 200,
            unitType = FoodUnitType.PER_100G,
            unitLabel = "100g",
            imageResName = "food_ca_basa"
        ),
        FoodEntity(
            name = "Tôm luộc",
            caloriesPerUnit = 99,
            unitType = FoodUnitType.PER_100G,
            unitLabel = "100g",
            imageResName = "food_tom_luoc"
        ),
        FoodEntity(
            name = "Đậu hũ chiên",
            caloriesPerUnit = 150,
            unitType = FoodUnitType.PER_100G,
            unitLabel = "100g",
            imageResName = "food_dau_hu_chien"
        ),
        FoodEntity(
            name = "Chả giò",
            caloriesPerUnit = 90,
            unitType = FoodUnitType.PER_SERVING,
            unitLabel = "cái",
            imageResName = "food_cha_gio"
        ),
        FoodEntity(
            name = "Nem nướng",
            caloriesPerUnit = 60,
            unitType = FoodUnitType.PER_SERVING,
            unitLabel = "cái",
            imageResName = "food_nem_nuong"
        ),

        // ===== Rau / canh =====
        FoodEntity(
            name = "Rau muống xào",
            caloriesPerUnit = 60,
            unitType = FoodUnitType.PER_100G,
            unitLabel = "100g",
            imageResName = "food_rau_muong_xao"
        ),
        FoodEntity(
            name = "Canh chua",
            caloriesPerUnit = 80,
            unitType = FoodUnitType.PER_SERVING,
            unitLabel = "tô",
            imageResName = "food_canh_chua"
        ),

        // ===== Món nước / đặc sản =====
        FoodEntity(
            name = "Phở bò",
            caloriesPerUnit = 350,
            unitType = FoodUnitType.PER_SERVING,
            unitLabel = "tô nhỏ",
            imageResName = "food_pho_bo"
        ),
        FoodEntity(
            name = "Bún bò Huế",
            caloriesPerUnit = 400,
            unitType = FoodUnitType.PER_SERVING,
            unitLabel = "tô",
            imageResName = "food_bun_bo_hue"
        ),
        FoodEntity(
            name = "Bún chả",
            caloriesPerUnit = 380,
            unitType = FoodUnitType.PER_SERVING,
            unitLabel = "phần",
            imageResName = "food_bun_cha"
        ),
        FoodEntity(
            name = "Bún đậu mắm tôm",
            caloriesPerUnit = 450,
            unitType = FoodUnitType.PER_SERVING,
            unitLabel = "phần",
            imageResName = "food_bun_dau_mam_tom"
        ),
        FoodEntity(
            name = "Cơm tấm sườn",
            caloriesPerUnit = 550,
            unitType = FoodUnitType.PER_SERVING,
            unitLabel = "phần",
            imageResName = "food_com_tam_suon"
        ),
        FoodEntity(
            name = "Bánh cuốn",
            caloriesPerUnit = 220,
            unitType = FoodUnitType.PER_SERVING,
            unitLabel = "phần",
            imageResName = "food_banh_cuon"
        ),
        FoodEntity(
            name = "Bánh tráng trộn",
            caloriesPerUnit = 320,
            unitType = FoodUnitType.PER_SERVING,
            unitLabel = "phần",
            imageResName = "food_banh_trang_tron"
        ),

        // ===== Trái cây =====
        FoodEntity(
            name = "Chuối",
            caloriesPerUnit = 89,
            unitType = FoodUnitType.PER_SERVING,
            unitLabel = "quả",
            imageResName = "food_chuoi"
        ),
        FoodEntity(
            name = "Táo",
            caloriesPerUnit = 52,
            unitType = FoodUnitType.PER_100G,
            unitLabel = "100g",
            imageResName = "food_tao"
        ),
        FoodEntity(
            name = "Cam",
            caloriesPerUnit = 47,
            unitType = FoodUnitType.PER_100G,
            unitLabel = "100g",
            imageResName = "food_cam"
        ),
        FoodEntity(
            name = "Bơ",
            caloriesPerUnit = 160,
            unitType = FoodUnitType.PER_100G,
            unitLabel = "100g",
            imageResName = "food_bo_trai"
        ),

        // ===== Đồ uống / ăn vặt =====
        FoodEntity(
            name = "Sữa chua",
            caloriesPerUnit = 100,
            unitType = FoodUnitType.PER_SERVING,
            unitLabel = "hũ",
            imageResName = "food_sua_chua"
        ),
        FoodEntity(
            name = "Sữa tươi",
            caloriesPerUnit = 150,
            unitType = FoodUnitType.PER_SERVING,
            unitLabel = "ly 250ml",
            imageResName = "food_sua_tuoi"
        ),
        FoodEntity(
            name = "Trà sữa",
            caloriesPerUnit = 350,
            unitType = FoodUnitType.PER_SERVING,
            unitLabel = "ly",
            imageResName = "food_tra_sua"
        ),
        FoodEntity(
            name = "Cà phê sữa",
            caloriesPerUnit = 120,
            unitType = FoodUnitType.PER_SERVING,
            unitLabel = "ly",
            imageResName = "food_ca_phe_sua"
        )
    )
}