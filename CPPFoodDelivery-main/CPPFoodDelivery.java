import java.util.*;

public class CPPFoodDelivery {
    private static CPPFoodDelivery instance;
    private List<Customer> customers = new ArrayList<>();
    private List<Restaurant> restaurants = new ArrayList<>();
    private List<Driver> drivers = new ArrayList<>();

    private CPPFoodDelivery() {}

    public static CPPFoodDelivery getInstance() {
        if (instance == null) {
            instance = new CPPFoodDelivery();
        }
        return instance;
    }

    public void registerCustomer(Customer customer) {
        customers.add(customer);
        System.out.println(customer.getName() + " has been registered with CPPFoodDelivery.");
    }

    public void registerRestaurant(Restaurant restaurant) {
        restaurants.add(restaurant);
        System.out.println(restaurant.getName() + " has been registered with CPPFoodDelivery.");
    }

    public void registerDriver(Driver driver) {
        drivers.add(driver);
        System.out.println(driver.getName() + " has been registered with CPPFoodDelivery.");
    }

    public List<Customer> getRegisteredCustomers() {
        return customers;
    }

    public List<Restaurant> getRegisteredRestaurants() {
        return restaurants;
    }

    public List<Driver> getRegisteredDrivers() {
        return drivers;
    }

    public List<Driver> getAvailableDrivers(String county, Date time) {
        List<Driver> availableDrivers = new ArrayList<>();
        for (Driver driver : drivers) {
            if (driver.getCounty().equals(county) && driver.isAvailableDuring(time)) {
                availableDrivers.add(driver);
            }
        }
        return availableDrivers;
    }
}
