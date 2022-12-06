package com.binatebits.food_reccomendation_system.data.entities

import com.google.gson.annotations.SerializedName
import java.io.Serializable

//data class TotalItemsResponseItem(@SerializedName("Menu Items") var menuItems:String="",
//                                  @SerializedName("Menu Category") var menuCategories:String="",
//                                  @SerializedName("Protein (g)") var protein:Double=0.0,
//                                  @SerializedName("Sat Fat (g)") var satFat:Double=0.0,
//                                  @SerializedName("Saturated Fat (% Daily Value)") var satFatDaily:Double=0.0,
//                                  @SerializedName("Sodium (% Daily Value)") var sodiumDaily:Double=0.0,
//                                  @SerializedName("Sodium (mg)") var sodiumn:Double=0.0,
//                                  @SerializedName("Total Fat (% Daily Value)") var totalFatDaily:Double=0.0,
//                       @SerializedName("Calories from Fat") var caloriesFromFat:Double=0.0,
//                       @SerializedName("Total carbohydrate (g)") var totalCarbohydrate:Double=0.0,
//                       @SerializedName("Total fat (g)") var totalFat:Double=0.0,
//                       @SerializedName("Trans fat (g)") var transFat:Double=0.0,
//                       @SerializedName("Vitamin A (% Daily Value)") var vitaminADaily:Double=0.0,
//                       @SerializedName("Vitamin C (% Daily Value)") var vitaminCDaily:Double=0.0)

data class TotalItemsResponseItem(@SerializedName("Menu Items") var menuItems:String="",
                                  @SerializedName("Menu Category") var menuCategories:String="",
                                  @SerializedName("Cholesterols (mg)") var Cholesterols:String="",
                                  @SerializedName("Total Sugars (g)") var totalSugar:String="",
                                  @SerializedName("Protein (g)") var protein:String="0.0",
                                  @SerializedName("Dietary Fiber (g)") var dietaryFiber:String="",
                                  @SerializedName("Total carbohydrate (g)") var totalCarbohydrate:String="0.0",
                                  @SerializedName("Total fat (g)") var totalFatDaily:String="0.0",
                                  @SerializedName("isSelected") var isSelected:Boolean=false,
                                  @SerializedName("Energy (kCal)") var caloriesFromFat:String="0.0") : Serializable

data class MenuCategory(var menuCat:String="",
         var itemsList : ArrayList<TotalItemsResponseItem>) : Serializable