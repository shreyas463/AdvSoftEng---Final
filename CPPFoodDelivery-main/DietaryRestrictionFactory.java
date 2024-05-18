public class DietaryRestrictionFactory {
    public static DietaryRestrictionStrategy createDietaryRestriction(String restrictionType) {
        switch (restrictionType) {
            case "NoRestriction":
                return new NoRestriction();
            case "NutAllergy":
                return new NutAllergy();
            case "Paleo":
                return new Paleo();
            case "Vegan":
                return new Vegan();
            default:
                throw new IllegalArgumentException("Unknown restriction type");
        }
    }
}