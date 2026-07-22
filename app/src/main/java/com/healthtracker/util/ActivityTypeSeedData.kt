package com.healthtracker.util

import com.healthtracker.data.local.entity.ActivityTypeEntity

object ActivityTypeSeedData {

    val activityTypes = listOf(
        ActivityTypeEntity(
            name = "Đi bộ",
            metValue = 3.5,
            iconName = "activity_walking"
        ),
        ActivityTypeEntity(
            name = "Chạy bộ",
            metValue = 9.8,
            iconName = "activity_running"
        ),
        ActivityTypeEntity(
            name = "Đạp xe",
            metValue = 7.5,
            iconName = "activity_cycling"
        ),
        ActivityTypeEntity(
            name = "Bơi lội",
            metValue = 8.0,
            iconName = "activity_swimming"
        ),
        ActivityTypeEntity(
            name = "Yoga",
            metValue = 2.5,
            iconName = "activity_yoga"
        ),
        ActivityTypeEntity(
            name = "Gym / Tập tạ",
            metValue = 6.0,
            iconName = "activity_gym"
        ),
        ActivityTypeEntity(
            name = "Leo cầu thang",
            metValue = 8.8,
            iconName = "activity_stairs"
        ),
        ActivityTypeEntity(
            name = "Nhảy dây",
            metValue = 11.0,
            iconName = "activity_jumprope"
        ),
        ActivityTypeEntity(
            name = "Cầu lông",
            metValue = 5.5,
            iconName = "activity_badminton"
        ),
        ActivityTypeEntity(
            name = "Bóng đá",
            metValue = 7.0,
            iconName = "activity_football"
        ),
        ActivityTypeEntity(
            name = "Bóng rổ",
            metValue = 6.5,
            iconName = "activity_basketball"
        ),
        ActivityTypeEntity(
            name = "Bóng chuyền",
            metValue = 4.0,
            iconName = "activity_volleyball"
        ),
        ActivityTypeEntity(
            name = "Bơi tự do (thư giãn)",
            metValue = 6.0,
            iconName = "activity_swimming_light"
        ),
        ActivityTypeEntity(
            name = "Đi bộ nhanh",
            metValue = 4.5,
            iconName = "activity_walking_fast"
        ),
        ActivityTypeEntity(
            name = "Aerobic",
            metValue = 6.5,
            iconName = "activity_aerobic"
        ),
        ActivityTypeEntity(
            name = "Nhảy Zumba",
            metValue = 6.5,
            iconName = "activity_zumba"
        ),
        ActivityTypeEntity(
            name = "Đá cầu",
            metValue = 3.0,
            iconName = "activity_shuttlecock"
        ),
        ActivityTypeEntity(
            name = "Việc nhà (lau dọn, quét nhà)",
            metValue = 3.3,
            iconName = "activity_housework"
        )
    )
}