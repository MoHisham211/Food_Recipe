package mo.zain.food_recipe;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;


public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.ViewHolder> {


    Context context;
    List<recipes> recipesResponseList;

    public RecipesAdapter(Context context, List<recipes> recipesResponseList) {
        this.context = context;
        this.recipesResponseList = recipesResponseList;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View view= LayoutInflater
                .from(context).inflate(R.layout.item_view,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {

        recipes recipesResponse = recipesResponseList.get(position);
        holder.textView.setText(recipesResponse.getTitle());


        if (recipesResponse.getImage_url()!=null
                && !recipesResponse.getImage_url().isEmpty())
            Picasso
                    .get()
                    .load(recipesResponse.getImage_url())
                    .placeholder(R.drawable.ic_baseline_image_24)
                    .into(holder.imageView);
        else
            holder.imageView.setImageResource(R.drawable.ic_baseline_image_not_supported_24);

    }

    @Override
    public int getItemCount() {
        return recipesResponseList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textView;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.imagetatatat);
            textView=itemView.findViewById(R.id.title);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recipes recipes=recipesResponseList.get(getAdapterPosition());
                    Intent intent=new Intent(context,DetailsActivity.class);
                    intent.putExtra("ID",recipes.getRecipe_id());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });
        }
    }
}
