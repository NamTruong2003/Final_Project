package com.healthtracker.model

enum class ActivityLevel(val description: String, val frequency: String) {
    SEDENTARY(
        description = "Ít vận động",
        frequency = "Ngồi nhiều, không tập"
    ),
    LIGHT(
        description = "Vận động nhẹ",
        frequency = "1-3 buổi/tuần"
    ),
    MODERATE(
        description = "Vận động vừa",
        frequency = "3-5 buổi/tuần"
    ),
    ACTIVE(
        description = "Vận động nhiều",
        frequency = "6-7 buổi/tuần"
    ),
    VERY_ACTIVE(
        description = "Vận động rất nhiều",
        frequency = "Công việc thể chất nặng"
    );
}
