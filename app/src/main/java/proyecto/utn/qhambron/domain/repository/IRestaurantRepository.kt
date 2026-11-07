package proyecto.utn.qhambron.domain.repository

import proyecto.utn.qhambron.domain.model.Restaurant
import proyecto.utn.qhambron.domain.model.RestaurantFilter

interface IRestaurantRepository {
    fun getRestaurants(): List<Restaurant>
    fun getById(id: String): Restaurant?
    fun addRestaurant(restaurant: Restaurant)
    fun updateRestaurant(restaurant: Restaurant)
    fun deleteRestaurant(id: String)
    fun filterRestaurants(filter: RestaurantFilter): List<Restaurant>
}