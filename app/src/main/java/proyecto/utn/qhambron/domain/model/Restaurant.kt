package proyecto.utn.qhambron.domain.model


/**
 * Represents a restaurant entity with its details, location, status, and metadata.
 * Serves as the core domain model for restaurant information in the application.
 */
data class Restaurant (
    val id: String,
    val name: String,
    val foodType: String,
    val province: Province,
    val description: String,
    val priceRange: PriceRange,
    val rating: Float,
    val status: RestaurantStatus,
    val photoUris: List<String>,
    val location: RestaurantLocation

)