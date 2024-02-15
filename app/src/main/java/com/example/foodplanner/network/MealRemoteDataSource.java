package com.example.foodplanner.network;

public interface MealRemoteDataSource {
    //For Random Meals In Home Fragment
    void networkMethod(NetworkCallback networkCallback);
    void networkMethodForCategoryMeals(String categoryName, NetworkCallback networkCallback);
    void networkMethodForCategories(NetworkCallback networkCallback);
    void networkMethodForAreas(NetworkCallback networkCallback);
    void networkMethodForMealsByAreas(NetworkCallback networkCallback);

}
