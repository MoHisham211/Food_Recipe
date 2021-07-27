package mo.zain.food_recipe.model;

public class CategoryModel {
    private String Name;

    public CategoryModel(String name) {
        Name = name;
    }

    public CategoryModel() {
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
