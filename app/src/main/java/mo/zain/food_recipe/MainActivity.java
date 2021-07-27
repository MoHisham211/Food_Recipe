package mo.zain.food_recipe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import mo.zain.food_recipe.adapter.CategoryAdapter;
import mo.zain.food_recipe.authentication.LoginActivity;
import mo.zain.food_recipe.model.CategoryModel;

public class MainActivity extends AppCompatActivity {


    private EditText searchView;
    private List<CategoryModel> categoryModelList=new ArrayList<>();
    private RecyclerView recyclerView;
    private CategoryAdapter categoryAdapter;
    private ProgressBar progressBar;
    private TextView logOut;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth=FirebaseAuth.getInstance();
        progressBar=findViewById(R.id.progress);
        logOut=findViewById(R.id.logOut);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
            }
        });
        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        getAllCategory();

        searchView=findViewById(R.id.search);
        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                getAllCategory();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchCategory(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                searchCategory(s.toString());
            }
        });

    }
    private void getAllCategory() {
        progressBar.setVisibility(View.VISIBLE);
        DatabaseReference reference= FirebaseDatabase.getInstance()
                .getReference("Category");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                categoryModelList.clear();
                for(DataSnapshot ds:snapshot.getChildren())
                {
                    CategoryModel categoryModel=ds.getValue(CategoryModel.class);
                    assert categoryModel !=null;
                    categoryModelList.add(categoryModel);
                }
                progressBar.setVisibility(View.GONE);
                categoryAdapter =new CategoryAdapter(MainActivity.this,categoryModelList);
                recyclerView.setAdapter(categoryAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void searchCategory(String searchKey)
    {

        DatabaseReference reference= FirebaseDatabase.getInstance()
                .getReference("Category");
        //get all data
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                categoryModelList.clear();
                for (DataSnapshot ds:snapshot.getChildren())
                {
                    CategoryModel categoryModel=ds.getValue(CategoryModel.class);
                    if (categoryModel.getName().toLowerCase()
                            .contains(searchKey.toLowerCase())){
                        categoryModelList.add(categoryModel);
                    }
                    categoryAdapter=new CategoryAdapter(getApplicationContext(),categoryModelList);
                    recyclerView.setAdapter(categoryAdapter);
                   // progressBar.setVisibility(View.GONE);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
                //FancyToast.makeText(getApplicationContext(),""+error.getMessage(), FancyToast.LENGTH_LONG,FancyToast.ERROR,false).show();
                //progressBar.setVisibility(View.GONE);
                //progressBar.setVisibility(View.GONE);
            }
        });
    }
}
//    private void setData(){
//        DatabaseReference databaseReference;
//        databaseReference = FirebaseDatabase.getInstance()
//                .getReference("Category")
//                .child(""+System.currentTimeMillis());
//        HashMap<String , String> hashMap = new HashMap<>();
//        hashMap.put("Name","");
//        databaseReference.setValue(hashMap);
//    }