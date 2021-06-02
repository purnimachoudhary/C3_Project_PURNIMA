import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;


class RestaurantServiceTest {

    RestaurantService service;
    Restaurant restaurant;
    LocalTime openingTime;
    LocalTime closingTime;
    //REFACTOR ALL THE REPEATED LINES OF CODE


    //>>>>>>>>>>>>>>>>>>>>>>SEARCHING<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void searching_for_existing_restaurant_should_return_expected_restaurant_object() throws restaurantNotFoundException {
        //WRITE UNIT TEST CASE HERE'
        restaurantData();
        restaurant = Mockito.spy(restaurant);
        Mockito.when(restaurant.getName()).thenReturn("Amelie's cafe");
        assertThat(service.findRestaurantByName("Amelie's cafe").getName(),equalTo("Amelie's cafe"));
        assertNotNull(restaurant);
    }
    @Test
    public void searching_for_non_existing_restaurant_should_throw_exception() throws restaurantNotFoundException {
        //WRITE UNIT TEST CASE HERE
        restaurantData();

        assertThrows(restaurantNotFoundException.class, () -> {
            service.findRestaurantByName("some invalid name");
        });

    }
    //<<<<<<<<<<<<<<<<<<<<SEARCHING>>>>>>>>>>>>>>>>>>>>>>>>>>




    //>>>>>>>>>>>>>>>>>>>>>>ADMIN: ADDING & REMOVING RESTAURANTS<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void remove_restaurant_should_reduce_list_of_restaurants_size_by_1() throws restaurantNotFoundException {
        restaurantData();

        int initialNumberOfRestaurants = service.getRestaurants().size();
        service.removeRestaurant("Amelie's cafe");
        assertEquals(initialNumberOfRestaurants-1, service.getRestaurants().size());
    }



    @Test
    public void removing_restaurant_that_does_not_exist_should_throw_exception() throws restaurantNotFoundException {
        restaurantData();

        assertThrows(restaurantNotFoundException.class,()->service.removeRestaurant("Pantry d'or"));
    }

    @Test
    public void add_restaurant_should_increase_list_of_restaurants_size_by_1(){
        restaurantData();

        int initialNumberOfRestaurants = service.getRestaurants().size();
        service.addRestaurant("Pumpkin Tales","Chennai",LocalTime.parse("12:00:00"),LocalTime.parse("23:00:00"));
        assertEquals(initialNumberOfRestaurants + 1,service.getRestaurants().size());
    }
    //<<<<<<<<<<<<<<<<<<<<ADMIN: ADDING & REMOVING RESTAURANTS>>>>>>>>>>>>>>>>>>>>>>>>>>

@Test
public void Is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time() {
    restaurantData();
    Restaurant restaurant = Mockito.mock(Restaurant.class);
    LocalTime checkTime = LocalTime.parse("18:30:00");
    boolean checkRestaurantOpen = closingTime.compareTo(checkTime) > 0 && (checkTime.compareTo(openingTime) > 0);
    Mockito.when(restaurant.isRestaurantOpen()).thenReturn(checkRestaurantOpen);

    assertTrue(checkRestaurantOpen);

}

@Test
public void Is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time() {
    restaurantData();
    Restaurant restaurant = Mockito.mock(Restaurant.class);
    LocalTime checkTime = LocalTime.parse("23:30:00");
    boolean checkRestaurantOpen = closingTime.compareTo(checkTime) > 0 && (checkTime.compareTo(openingTime) > 0);
    Mockito.when(restaurant.isRestaurantOpen()).thenReturn(checkRestaurantOpen);

    assertFalse(checkRestaurantOpen);

}
    private void restaurantData() {
        service = new RestaurantService();

        openingTime = LocalTime.parse("10:30:00");
        closingTime = LocalTime.parse("22:00:00");

        restaurant = service.addRestaurant("Amelie's cafe", "Chennai", openingTime, closingTime);
        restaurant.addToMenu("Sweet corn soup", 119);
        restaurant.addToMenu("Vegetable lasagne", 269);

    }
}