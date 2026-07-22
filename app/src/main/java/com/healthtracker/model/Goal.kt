package com.healthtracker.model

enum class Goal(val adjustment:Int,val description: String) {
    LOSE_WEIGHT(adjustment = -500,description = "Giảm cân"),
    MAINTAIN_WEIGHT(adjustment = 0,description = "Giữ cân"),
    GAIN_WEIGHT(adjustment = 500,description = "Tăng cân");
}
