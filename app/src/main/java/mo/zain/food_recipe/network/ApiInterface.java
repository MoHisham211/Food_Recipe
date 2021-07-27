package mo.zain.food_recipe.network;

import mo.zain.food_recipe.RecipeDetails;
import mo.zain.food_recipe.RecipesResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("/api/search")
    Call<RecipesResponse> getData(@Query("q") String category);

    @GET("/api/get")
    Call<RecipeDetails> getDetails(@Query("rId") String id);
}
