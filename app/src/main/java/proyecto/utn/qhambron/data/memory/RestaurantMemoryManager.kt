package proyecto.utn.qhambron.data.memory

import proyecto.utn.qhambron.domain.model.Restaurant
import proyecto.utn.qhambron.domain.model.RestaurantFilter
import proyecto.utn.qhambron.domain.repository.IRestaurantRepository

class RestaurantMemoryManager: IRestaurantRepository {
    private val restaurants = mutableListOf<Restaurant>()

    override fun addRestaurant(restaurant: Restaurant) {
        restaurants.add(restaurant)
    }

    override fun deleteRestaurant(id: String) {
        restaurants.removeIf { it.id.trim() == id.trim() }
    }

    override fun updateRestaurant(restaurant: Restaurant) {
        val index = restaurants.indexOfFirst { it.id.trim() == restaurant.id.trim() }
        if (index != -1) {
            restaurants[index] = restaurant
        }
    }

    override fun getRestaurants() = restaurants

    override fun getById(id: String): Restaurant? {
        return restaurants.find { it.id.trim() == id.trim() }
    }

    override fun filterRestaurants(filter: RestaurantFilter): List<Restaurant> {
        return restaurants.filter { restaurant ->
            val matchesFoodType = filter.foodType.isNullOrBlank() ||
                    restaurant.foodType.equals(filter.foodType.trim(), ignoreCase = true)
            val matchesProvince = filter.province == null || restaurant.province == filter.province
            val matchesStatus = filter.status == null || restaurant.status == filter.status
            matchesFoodType && matchesProvince && matchesStatus
        }
    }
}