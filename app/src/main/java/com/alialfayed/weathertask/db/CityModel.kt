package com.alialfayed.weathertask.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by ( Eng Ali Al Fayed)
 * Class do :
 * Date 9/14/2021 - 3:20 PM
 */

@Entity(tableName = "city_table")
data class CityModel(

    @ColumnInfo(name = "name")
    val name: String? = null,

    @ColumnInfo(name = "temp")
    val temp: Double? = null ,

    @ColumnInfo(name = "icon")
    val icon: String? = null,

    ){
    // PrimaryKey annotation to set idItem is unique [if you want that id autoGenerate set @field:PrimaryKey(autoGenerate = true)]
    @field:PrimaryKey(autoGenerate = true)
    var id : Long  = 0
}

