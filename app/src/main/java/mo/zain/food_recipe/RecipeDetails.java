package mo.zain.food_recipe;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RecipeDetails {

    @SerializedName("recipe")
    @Expose
    private Recipe recipe;

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

}


class Recipe {

    @SerializedName("publisher")
    @Expose
    private String publisher;
    @SerializedName("ingredients")
    @Expose
    private List<String> ingredients = null;
    @SerializedName("source_url")
    @Expose
    private String sourceUrl;
    @SerializedName("recipe_id")
    @Expose
    private String recipeId;
    @SerializedName("image_url")
    @Expose
    private String imageUrl;
    @SerializedName("social_rank")
    @Expose
    private Double socialRank;
    @SerializedName("publisher_url")
    @Expose
    private String publisherUrl;
    @SerializedName("title")
    @Expose
    private String title;

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public String getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(String recipeId) {
        this.recipeId = recipeId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Double getSocialRank() {
        return socialRank;
    }

    public void setSocialRank(Double socialRank) {
        this.socialRank = socialRank;
    }

    public String getPublisherUrl() {
        return publisherUrl;
    }

    public void setPublisherUrl(String publisherUrl) {
        this.publisherUrl = publisherUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}