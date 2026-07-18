package com.healthtracker.data.local

import androidx.room.TypeConverter
import com.healthtracker.data.local.entity.FoodUnitType
import com.healthtracker.model.Gender
import java.time.LocalDate

class Converters {
    @TypeConverter
    fun fromLocalDate(date: LocalDate?): String? = date?.toString()

    @TypeConverter
    fun toLocalDate(value: String?): LocalDate? = value?.let { LocalDate.parse(it) }

    @TypeConverter
    fun fromGender(gender: Gender): String = gender.name

    @TypeConverter
    fun toGender(value: String): Gender = Gender.valueOf(value)

    @TypeConverter
    fun fromFoodUnitType(type: FoodUnitType): String = type.name

    @TypeConverter
    fun toFoodUnitType(value: String): FoodUnitType = FoodUnitType.valueOf(value)

}