package proyecto.utn.qhambron.viewmodel

import androidx.lifecycle.ViewModel
import proyecto.utn.qhambron.domain.model.Restaurant
import proyecto.utn.qhambron.domain.model.RestaurantFilter
import proyecto.utn.qhambron.domain.repository.IRestaurantRepository

class RestaurantViewModel(
    private val repository: IRestaurantRepository
): ViewModel() {
    fun getRestaurants(): List<Restaurant> {
        return repository.getRestaurants()
    }

    fun getRestaurantById(id: String): Restaurant? {
        return repository.getById(id)
    }

    fun saveRestaurant(restaurant: Restaurant) {
        repository.addRestaurant(restaurant)
    }

    fun updateRestaurant(restaurant: Restaurant) {
        repository.updateRestaurant(restaurant)
    }

    fun removeRestaurant(id: String) {
        repository.deleteRestaurant(id)
    }

    fun filterRestaurants(filter: RestaurantFilter): List<Restaurant> {
        return repository.filterRestaurants(filter)
    }
}