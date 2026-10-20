package proyecto.utn.qhambron.domain.model

/**
 * Represents the geographical location of a restaurant with coordinates and a name.
 * Used for mapping and location-based features.
 */
data class RestaurantLocation(
    val name: String,
    val latitude: Double,
    val longitude: Double
)
