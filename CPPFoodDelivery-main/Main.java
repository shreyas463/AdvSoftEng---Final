import java.util.*;

public class Main {
    public static void main(String[] args) {
        // Singleton instance of CPPFoodDelivery
        CPPFoodDelivery cppFoodDelivery = CPPFoodDelivery.getInstance();

        // Registering restaurants with specific names
        List<Restaurant> restaurants = new ArrayList<>();
        Map<String, Double> menu1 = new HashMap<>();
        menu1.put("Cheese", 3.0);
        menu1.put("Chicken", 5.0);
        menu1.put("Avocado", 2.5);
        Map<String, Double> toppings1 = new HashMap<>();
        toppings1.put("Salsa", 1.0);
        toppings1.put("Cheese", 1.5);
        toppings1.put("Guacamole", 2.0);
        restaurants.add(new Restaurant("Mexican Fiesta", "123 Main St", "LA County", "8AM - 4PM", "Mexican", menu1, toppings1, 8, 16));

        Map<String, Double> menu2 = new HashMap<>();
        menu2.put("Bread", 2.0);
        menu2.put("Beef", 6.0);
        menu2.put("Sour cream", 1.5);
        Map<String, Double> toppings2 = new HashMap<>();
        toppings2.put("Olives", 1.0);
        toppings2.put("Parmesan", 1.5);
        toppings2.put("Basil", 1.0);
        restaurants.add(new Restaurant("Italian Delight", "456 Oak St", "Orange County", "4PM - 12AM", "Italian", menu2, toppings2, 16, 24));

        Map<String, Double> menu3 = new HashMap<>();
        menu3.put("Lentils", 3.5);
        menu3.put("Fish", 5.5);
        menu3.put("Tuna", 4.0);
        Map<String, Double> toppings3 = new HashMap<>();
        toppings3.put("Soy Sauce", 0.5);
        toppings3.put("Ginger", 0.5);
        toppings3.put("Scallions", 0.5);
        restaurants.add(new Restaurant("Chinese Garden", "789 Pine St", "San Bernardino County", "12AM - 8AM", "Chinese", menu3, toppings3, 0, 8));

        Map<String, Double> menu4 = new HashMap<>();
        menu4.put("Pistachio", 4.0);
        menu4.put("Tofu", 3.0);
        menu4.put("Peanuts", 2.0);
        Map<String, Double> toppings4 = new HashMap<>();
        toppings4.put("Cilantro", 0.5);
        toppings4.put("Yogurt", 0.5);
        toppings4.put("Chutney", 1.0);
        restaurants.add(new Restaurant("Indian Spice", "101 Maple St", "LA County", "8AM - 4PM", "Indian", menu4, toppings4, 8, 16));

        for (Restaurant restaurant : restaurants) {
            cppFoodDelivery.registerRestaurant(restaurant);
        }

        // Registering drivers with specific names
        List<Driver> drivers = new ArrayList<>();
        drivers.add(new Driver("Walter White", "Driver Address 1", "LA County", "1st shift", 8, 16));
        drivers.add(new Driver("Jesse Pinkman", "Driver Address 2", "Orange County", "2nd shift", 16, 24));
        drivers.add(new Driver("Hank", "Driver Address 3", "San Bernardino County", "3rd shift", 0, 8));
        drivers.add(new Driver("Gus Fring", "Driver Address 4", "LA County", "1st shift", 8, 16));
        drivers.add(new Driver("Tuco", "Driver Address 5", "Orange County", "2nd shift", 16, 24));
        drivers.add(new Driver("Saul Goodman", "Driver Address 6", "San Bernardino County", "3rd shift", 0, 8));
        drivers.add(new Driver("Mike Ehrmantraut", "Driver Address 7", "LA County", "1st shift", 8, 16));
        drivers.add(new Driver("Skinny Pete", "Driver Address 8", "Orange County", "2nd shift", 16, 24));

        for (Driver driver : drivers) {
            cppFoodDelivery.registerDriver(driver);
        }

        // Registering customers with specific names
        List<Customer> customers = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            Customer customer = new Customer("Customer " + i, "Customer Address " + i, "County " + ((i % 3) + 1));
            // Randomly assign a dietary restriction
            switch (i % 4) {
                case 0:
                    customer.setDietaryRestrictionStrategy(DietaryRestrictionFactory.createDietaryRestriction("NoRestriction"));
                    break;
                case 1:
                    customer.setDietaryRestrictionStrategy(DietaryRestrictionFactory.createDietaryRestriction("Paleo"));
                    break;
                case 2:
                    customer.setDietaryRestrictionStrategy(DietaryRestrictionFactory.createDietaryRestriction("Vegan"));
                    break;
                case 3:
                    customer.setDietaryRestrictionStrategy(DietaryRestrictionFactory.createDietaryRestriction("NutAllergy"));
                    break;
            }
            cppFoodDelivery.registerCustomer(customer);
            customers.add(customer);
        }

        // Macronutrient Food Options
        List<String> carbs = new ArrayList<>(Arrays.asList("Cheese", "Bread", "Lentils", "Pistachio"));
        List<String> proteins = new ArrayList<>(Arrays.asList("Fish", "Chicken", "Beef", "Tofu"));
        List<String> fats = new ArrayList<>(Arrays.asList("Avocado", "Sour cream", "Tuna", "Peanuts"));

        //  customer ordering
        Random random = new Random();
        for (int i = 0; i < customers.size(); i++) {
            Customer customer = customers.get(i);
            Restaurant restaurant;
            Date orderCreationTime;

            if (i < 3) {
                restaurant = restaurants.stream().filter(r -> r.getName().equals("Indian Spice")).findFirst().get();
                orderCreationTime = new GregorianCalendar(2024, Calendar.MAY, 1, 9, 0).getTime(); // 9AM
            } else if (i < 6) {
                restaurant = restaurants.stream().filter(r -> r.getName().equals("Chinese Garden")).findFirst().get();
                orderCreationTime = new GregorianCalendar(2024, Calendar.MAY, 1, 1, 0).getTime(); // 1AM
            } else if (i < 8) {
                restaurant = restaurants.stream().filter(r -> r.getName().equals("Mexican Fiesta")).findFirst().get();
                orderCreationTime = new GregorianCalendar(2024, Calendar.MAY, 1, 10, 0).getTime(); // 10AM
            } else {
                restaurant = restaurants.stream().filter(r -> r.getName().equals("Chinese Garden")).findFirst().get();
                orderCreationTime = new GregorianCalendar(2024, Calendar.MAY, 1, 9, 0).getTime(); // 9AM for closed restaurant scenario
            }

            System.out.println("========================================================");
            System.out.println(customer.getName() + " is attempting to place an order at " + restaurant.getName());

            // Check if restaurant is open
            if (!restaurant.isOpenDuring(orderCreationTime)) {
                System.out.println("Restaurant " + restaurant.getName() + " is closed. Cannot place an order.");
                System.out.println("========================================================");
                continue;
            } else {
                System.out.println("Restaurant " + restaurant.getName() + " is open.");
            }

            // Apply dietary restrictions
            List<String> availableCarbs = new ArrayList<>(carbs);
            List<String> availableProteins = new ArrayList<>(proteins);
            List<String> availableFats = new ArrayList<>(fats);
            customer.applyDietaryRestriction(availableCarbs, availableProteins, availableFats);

            // Create Customer Order
            CustomerOrder customerOrder = new CustomerOrder(customer);

            // Select food items from the restaurant's menu
            List<String> menuItems = new ArrayList<>(restaurant.getMenu().keySet());
            String selectedCarb = menuItems.stream().filter(availableCarbs::contains).findAny().orElse(null);
            String selectedProtein = menuItems.stream().filter(availableProteins::contains).findAny().orElse(null);
            String selectedFat = menuItems.stream().filter(availableFats::contains).findAny().orElse(null);

            // Add at least one main food item to the order
            boolean mainFoodItemAdded = false;
            if (selectedCarb != null) {
                customerOrder.addFoodItem(new BasicFoodItem(selectedCarb, restaurant.getMenu().get(selectedCarb)));
                mainFoodItemAdded = true;
            }
            if (selectedProtein != null) {
                customerOrder.addFoodItem(new BasicFoodItem(selectedProtein, restaurant.getMenu().get(selectedProtein)));
                mainFoodItemAdded = true;
            }
            if (selectedFat != null) {
                customerOrder.addFoodItem(new BasicFoodItem(selectedFat, restaurant.getMenu().get(selectedFat)));
                mainFoodItemAdded = true;
            }

            if (!mainFoodItemAdded) {
                System.out.println("No suitable main food items available for customer " + customer.getName() + " based on their dietary restrictions.");
                continue;
            }

            // Add toppings randomly
            List<String> toppingItems = new ArrayList<>(restaurant.getToppings().keySet());
            for (String topping : toppingItems) {
                if (random.nextBoolean()) {
                    customerOrder.addFoodItem(new ToppingDecorator(new BasicFoodItem("Topping", 0), topping, restaurant.getToppings().get(topping)));
                }
            }

            // Calculate total cost
            double totalCost = customerOrder.calculateTotalCost();

            // Find an available driver
            List<Driver> availableDrivers = cppFoodDelivery.getAvailableDrivers(restaurant.getCounty(), orderCreationTime);
            if (!availableDrivers.isEmpty()) {
                Driver driver = availableDrivers.get(random.nextInt(availableDrivers.size()));
                Order order = new Order(restaurant, customer, customer.getDietaryRestrictionStrategy().getClass().getSimpleName(), customerOrder.getFoodItems(), driver, orderCreationTime);
                CustomerOrderObserver observer = new CustomerOrderObserver(customer.getName());
                order.addObserver(observer);

                // Simulate order pickup time
                Date orderPickUpTime = new Date(orderCreationTime.getTime() + random.nextInt(3600000)); // Adding random time between 0 to 1 hour
                order.setOrderPickUpTime(orderPickUpTime);

                // Simulate order delivered time
                Date orderDeliveredTime = new Date(orderPickUpTime.getTime() + random.nextInt(7200000)); // Adding random time between 0 to 2 hours
                order.setOrderDeliveredTime(orderDeliveredTime);

                // Print order details
                System.out.println("--------------------------------------------------------");
                System.out.println("Order Details:");
                System.out.println("Restaurant: " + restaurant.getName());
                System.out.println("Customer: " + customer.getName());
                System.out.println("Dietary Restriction: " + customer.getDietaryRestrictionStrategy().getClass().getSimpleName());
                System.out.println("Food Items:");
                for (FoodItem item : customerOrder.getFoodItems()) {
                    System.out.println(" - " + item.getDescription() + ": $" + item.getCost());
                }
                System.out.println("Total Cost: $" + totalCost);
                System.out.println("Driver: " + driver.getName() + " (" + driver.getShift() + ": " + driver.getShiftStartHour() + ":00 - " + driver.getShiftEndHour() + ":00)");
                System.out.println("Order Creation Time: " + order.getOrderCreationTime());
                System.out.println("Order Pick Up Time: " + order.getOrderPickUpTime());
                System.out.println("Order Delivered Time: " + order.getOrderDeliveredTime());
                System.out.println("========================================================");

                // Notify customer about order status
                order.notifyObservers();
            } else {
                System.out.println("No available driver for customer " + customer.getName() + " at this time.");
                System.out.println("========================================================");
            }
        }
    }
}
