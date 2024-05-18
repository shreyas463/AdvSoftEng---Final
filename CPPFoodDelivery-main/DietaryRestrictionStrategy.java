import java.util.List;

public interface DietaryRestrictionStrategy {
    void applyRestriction(Customer customer, List<String> carbs, List<String> proteins, List<String> fats);
}

