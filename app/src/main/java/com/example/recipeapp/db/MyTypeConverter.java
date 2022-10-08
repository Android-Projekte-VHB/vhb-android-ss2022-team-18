package com.example.recipeapp.db;

import androidx.room.TypeConverter;

import java.util.ArrayList;

public class MyTypeConverter {

    @TypeConverter
    public static String toString(ArrayList<String> strings){
        String string = "";
        for(String s : strings){
            string += (s + "|");
        }
        return string;
    }

    @TypeConverter
    public static ArrayList<String> toStringArray(String string){
        ArrayList<String> stringArray = new ArrayList<>();
        for(String s : string.split("|")){
            stringArray.add(s);
        }
        return stringArray;
    }

}
