package mo.zain.food_recipe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import mo.zain.food_recipe.network.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataActivity extends AppCompatActivity {

    private List<RecipesResponse> recipesResponseList =new ArrayList<>();
    private RecyclerView recyclerView;
    private RecipesAdapter recipesAdapter;
    private ProgressBar progressBar;
    private TextView recipeName;
    private ImageView backImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        Intent intent=getIntent();
        recipeName=findViewById(R.id.recipeName);
        String str=intent.getStringExtra("Name");
        recyclerView=findViewById(R.id.rv);
        recyclerView.setHasFixedSize(true);
        recyclerView
                .setLayoutManager(new GridLayoutManager(DataActivity.this,2));

        progressBar=findViewById(R.id.progress);
        recipeName.setText(str);
        backImage=findViewById(R.id.backImage);
        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getAllData(str);


       // Toast.makeText(this, ""+str, Toast.LENGTH_SHORT).show();
    }

    public void getAllData(String q)
    {
        progressBar.setVisibility(View.VISIBLE);
        Call<RecipesResponse> listCall= ApiClient.getApiInterface().getData(q);
        listCall.enqueue(new Callback<RecipesResponse>() {
            @Override
            public void onResponse(Call<RecipesResponse> call, Response<RecipesResponse> response) {

                if (response.isSuccessful())
                {
                    RecipesResponse recipesResponse =response.body();
                    List<recipes>recipes=recipesResponse.getrecipe();
                    recipesAdapter=new RecipesAdapter(DataActivity.this, recipes);
                    recyclerView.setAdapter(recipesAdapter);
                    recipesAdapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);
                }else
                {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(DataActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RecipesResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(DataActivity.this, ""+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}