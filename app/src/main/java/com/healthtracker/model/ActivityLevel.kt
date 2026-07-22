package com.healthtracker.model

enum class ActivityLevel(val factor: Double, val description: String, val frequency: String) {
    SEDENTARY(
        factor = 1.2,
        description = "Ít vận động",
        frequency = "Ngồi nhiều, không tập"
    ),
    LIGHT(
        factor = 1.375,
        description = "Vận động nhẹ",
        frequency = "1-3 buổi/tuần"
    ),
    MODERATE(
        factor = 1.55,
        description = "Vận động vừa",
        frequency = "3-5 buổi/tuần"
    ),
    ACTIVE(
        factor = 1.725,
        description = "Vận động nhiều",
        frequency = "6-7 buổi/tuần"
    ),
    VERY_ACTIVE(
        factor = 1.9,
        description = "Vận động rất nhiều",
        frequency = "Công việc thể chất nặng"
    );
}
