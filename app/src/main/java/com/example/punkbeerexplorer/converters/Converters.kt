package com.example.punkbeerexplorer.converters

import androidx.room.TypeConverter

class Converters {

    @TypeConverter
    fun fromArray(strings: ArrayList<String>): String {
        var string = ""
        for ((index, s) in strings.withIndex()) {
            string += s
            if (index < strings.size - 1) {
                string += ", "
            }
        }
        return string
    }

    @TypeConverter
    fun toArray(concatenatedStrings: String): ArrayList<String> {
        val myStrings = ArrayList<String>()
        for (s in concatenatedStrings.split(",")) {
            myStrings.add(s)
        }
        return myStrings
    }

}