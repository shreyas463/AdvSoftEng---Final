import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class CPPFoodDeliveryTest {

    private CPPFoodDelivery cppFoodDelivery;
    private Restaurant mexicanFiesta;
    private Restaurant chineseGarden;
    private Customer customer1;
    private Customer customer2;
    private Driver driver1;

    @Before
    public void setUp() {
        // Initialize the singleton instance
        cppFoodDelivery = CPPFoodDelivery.getInstance();

        // Registering a restaurant
        Map<String, Double> menu1 = new HashMap<>();
        menu1.put("Cheese", 3.0);
        menu1.put("Chicken", 5.0);
        menu1.put("Avocado", 2.5);
        Map<String, Double> toppings1 = new HashMap<>();
        toppings1.put("Salsa", 1.0);
        toppings1.put("Cheese", 1.5);
        toppings1.put("Guacamole", 2.0);
        mexicanFiesta = new Restaurant("Mexican Fiesta", "123 Main St", "LA County", "8AM - 4PM", "Mexican", menu1, toppings1, 8, 16);
        cppFoodDelivery.registerRestaurant(mexicanFiesta);

        Map<String, Double> menu2 = new HashMap<>();
        menu2.put("Lentils", 3.5);
        menu2.put("Fish", 5.5);
        menu2.put("Tuna", 4.0);
        Map<String, Double> toppings2 = new HashMap<>();
        toppings2.put("Soy Sauce", 0.5);
        toppings2.put("Ginger", 0.5);
        toppings2.put("Scallions", 0.5);
        chineseGarden = new Restaurant("Chinese Garden", "789 Pine St", "San Bernardino County", "12AM - 8AM", "Chinese", menu2, toppings2, 0, 8);
        cppFoodDelivery.registerRestaurant(chineseGarden);

        // Registering a customer
        customer1 = new Customer("Customer 1", "Customer Address 1", "LA County");
        customer1.setDietaryRestrictionStrategy(DietaryRestrictionFactory.createDietaryRestriction("NoRestriction"));
        cppFoodDelivery.registerCustomer(customer1);

        customer2 = new Customer("Customer 2", "Customer Address 2", "San Bernardino County");
        customer2.setDietaryRestrictionStrategy(DietaryRestrictionFactory.createDietaryRestriction("NoRestriction"));
        cppFoodDelivery.registerCustomer(customer2);

        // Registering a driver
        driver1 = new Driver("Walter White", "Driver Address 1", "LA County", "1st shift", 8, 16);
        cppFoodDelivery.registerDriver(driver1);
    }

    @Test
    public void testRegisterRestaurant() {
        List<Restaurant> registeredRestaurants = cppFoodDelivery.getRegisteredRestaurants();
        assertTrue(registeredRestaurants.contains(mexicanFiesta));
        assertTrue(registeredRestaurants.contains(chineseGarden));
    }

    @Test
    public void testRegisterCustomer() {
        List<Customer> registeredCustomers = cppFoodDelivery.getRegisteredCustomers();
        assertTrue(registeredCustomers.contains(customer1));
        assertTrue(registeredCustomers.contains(customer2));
    }

    @Test
    public void testRegisterDriver() {
        List<Driver> registeredDrivers = cppFoodDelivery.getRegisteredDrivers();
        assertTrue(registeredDrivers.contains(driver1));
    }

    @Test
    public void testPlaceOrder() {
        // Customer 1 places an order from Mexican Fiesta within operating hours
        Date orderCreationTime = new GregorianCalendar(2024, Calendar.MAY, 1, 10, 0).getTime(); // 10AM
        boolean isOpen = mexicanFiesta.isOpenDuring(orderCreationTime);
        assertTrue(isOpen);

        CustomerOrder customerOrder = new CustomerOrder(customer1);
        customerOrder.addFoodItem(new BasicFoodItem("Cheese", 3.0));
        customerOrder.addFoodItem(new BasicFoodItem("Chicken", 5.0));

        double totalCost = customerOrder.calculateTotalCost();
        assertEquals(8.0, totalCost, 0.01);

        List<Driver> availableDrivers = cppFoodDelivery.getAvailableDrivers(mexicanFiesta.getCounty(), orderCreationTime);
        assertTrue(availableDrivers.contains(driver1));

        Order order = new Order(mexicanFiesta, customer1, customer1.getDietaryRestrictionStrategy().getClass().getSimpleName(), customerOrder.getFoodItems(), driver1, orderCreationTime);
        CustomerOrderObserver observer = new CustomerOrderObserver(customer1.getName());
        order.addObserver(observer);

        // Simulate order pickup and delivery times
        Date orderPickUpTime = new Date(orderCreationTime.getTime() + 3600000); // Adding 1 hour
        order.setOrderPickUpTime(orderPickUpTime);

        Date orderDeliveredTime = new Date(orderPickUpTime.getTime() + 7200000); // Adding 2 hours
        order.setOrderDeliveredTime(orderDeliveredTime);

        assertEquals(orderPickUpTime, order.getOrderPickUpTime());
        assertEquals(orderDeliveredTime, order.getOrderDeliveredTime());
    }

    @Test
    public void testDietaryRestriction() {
        // Customer 2 (NoRestriction) orders from Mexican Fiesta
        List<String> availableCarbs = new ArrayList<>(Arrays.asList("Cheese", "Bread", "Lentils", "Pistachio"));
        List<String> availableProteins = new ArrayList<>(Arrays.asList("Fish", "Chicken", "Beef", "Tofu"));
        List<String> availableFats = new ArrayList<>(Arrays.asList("Avocado", "Sour cream", "Tuna", "Peanuts"));

        customer2.applyDietaryRestriction(availableCarbs, availableProteins, availableFats);

        // Checking that nothing gets removed
        assertTrue(availableCarbs.contains("Cheese"));
        assertTrue(availableProteins.contains("Chicken"));
        assertTrue(availableFats.contains("Sour cream"));
    }
}
