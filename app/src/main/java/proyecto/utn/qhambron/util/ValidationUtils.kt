package proyecto.utn.qhambron.util


/**
 * Utility object providing validation functions for restaurant data.
 * Includes validators for ratings, price ranges, photo counts, and names.
 */
object ValidationUtils {

    fun isValidRating(rating: Float): Boolean {
        return rating in 0f..5f
    }

    fun isValidPriceRange(minimumPrice: Double, maximumPrice: Double): Boolean {
        return minimumPrice >= 0 && maximumPrice >= minimumPrice
    }

    fun isValidPhotoCount(photoCount: Int): Boolean {
        return photoCount in 0..5
    }

    fun isValidRestaurantName(name: String): Boolean {
        return name.isNotBlank()
    }

    fun isValidFoodType(foodType: String): Boolean {
        return foodType.isNotBlank()
    }
}