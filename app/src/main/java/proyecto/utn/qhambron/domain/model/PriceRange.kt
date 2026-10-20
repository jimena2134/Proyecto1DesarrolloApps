package proyecto.utn.qhambron.domain.model


/**
 * Represents the price range of a restaurant with minimum and maximum price values.
 * Used for pricing details and categorization.
 */
data class PriceRange (
    val minimumPrice: Double,
    val maximumPrice: Double
)
