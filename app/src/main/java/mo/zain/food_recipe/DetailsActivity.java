package mo.zain.food_recipe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import mo.zain.food_recipe.network.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextView title,integrats;
    private Button source;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Intent intent=getIntent();
        String str=intent.getStringExtra("ID");
        imageView=findViewById(R.id.app_bar_image);
        title=findViewById(R.id.title);
        integrats=findViewById(R.id.integrats);
        source=findViewById(R.id.source);
        progressBar=findViewById(R.id.progress);

        getAllData(str);
    }
    public void getAllData(String q)
    {
        progressBar.setVisibility(View.VISIBLE);
        Call<RecipeDetails> recipeCall= ApiClient.getApiInterface().getDetails(q);
        recipeCall.enqueue(new Callback<RecipeDetails>() {
            @Override
            public void onResponse(Call<RecipeDetails> call, Response<RecipeDetails> response) {
                if (response.isSuccessful())
                {
                    RecipeDetails recipeDetails=response.body();
                    Recipe recipe=recipeDetails.getRecipe();

                    title.setText(recipe.getTitle());
                    String integrat="";
                    for (String ins :recipe.getIngredients())
                    {
                        integrat+=ins+"\n";
                    }
                    integrats.setText(integrat);
                    source.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse(recipe.getSourceUrl()));
                            startActivity(i);
                        }
                    });
                    if (recipe.getImageUrl()!=null
                            && !recipe.getImageUrl().isEmpty())
                        Picasso
                                .get()
                                .load(recipe.getImageUrl())
                                .into(imageView);
                    else
                        imageView.setImageResource(R.drawable.ic_baseline_image_not_supported_24);

                progressBar.setVisibility(View.GONE);
                }else
                {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(DetailsActivity.this, "Error..", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<RecipeDetails> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(DetailsActivity.this, ""+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}