package proyecto.utn.qhambron.domain.model

/**
 * Represents filtering criteria used to search or filter restaurants.
 * Allows filtering by status, province, and food type.
 */
data class RestaurantFilter(

    val status: RestaurantStatus? = null,
    val province: Province? = null,
    val foodType: String? = null,
)
