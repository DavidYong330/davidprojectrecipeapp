package com.example.recipeapp.Adapter;

import android.content.Context;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.recipeapp.Object.RecipeObject;
import com.example.recipeapp.R;
import java.util.List;

public class RecipeSelectionAdapter extends RecyclerView.Adapter<RecipeSelectionAdapter.MyViewHolder>{
    private Context context;
    private RecipeSelectionAdapterListener recipeSelectionAdapterListener;
    private List<RecipeObject> listOfRecipe;
    private long mLastClickTime = 0;


    public RecipeSelectionAdapter(Context context,  List<RecipeObject> recipeList, RecipeSelectionAdapterListener recipeSelectionAdapterListener ) {
        this.context = context;
        this.listOfRecipe = recipeList;
        this.recipeSelectionAdapterListener = recipeSelectionAdapterListener;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvRecipeName, tvCompletionTime, tvLevel;
        private LinearLayout llRecipeImage, llActionButtons;
        private LinearLayout llRecipeContainer;
        private ImageView ivEdit, ivDelete;
        private View vDivider;


        public MyViewHolder(View view) {
            super(view);
            tvRecipeName = view.findViewById(R.id.tvRecipeName);
            llRecipeImage = view.findViewById(R.id.llRecipeImage);
            llRecipeContainer = view.findViewById(R.id.llRecipeContainer);
            llActionButtons = view.findViewById(R.id.llActionButtons);
            tvCompletionTime = view.findViewById(R.id.tvCompletionTime);
            tvLevel = view.findViewById(R.id.tvLevel);
            ivEdit = view.findViewById(R.id.ivEdit);
            ivDelete = view.findViewById(R.id.ivDelete);
            vDivider = view.findViewById(R.id.vDivider);
        }
    }

    @NonNull
    @Override
    public RecipeSelectionAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_options_selection, parent, false);
        return new RecipeSelectionAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeSelectionAdapter.MyViewHolder holder, int position) {

        RecipeObject recipeList = listOfRecipe.get(position);
        setHolderValues(holder, recipeList, position);
    }


    private void setHolderValues(@NonNull final RecipeSelectionAdapter.MyViewHolder holder, final RecipeObject recipeList, int position){

        // Only the self created recipe can be edited or delete.
        holder.llActionButtons.setVisibility(recipeList.getSelfCreatedIndicator().equals("yes")
                ? View.VISIBLE : View.INVISIBLE );
        //Hide the last item divider
        holder.vDivider.setVisibility(listOfRecipe.size()-1 == position? View.GONE : View.VISIBLE);

        holder.tvRecipeName.setText(recipeList.getRecipeName());
        holder.llRecipeImage.setBackground(recipeList.getRecipeImage());
        holder.tvCompletionTime.setText(recipeList.getTimeComplete());
        holder.tvLevel.setText(recipeList.getLevel());
        holder.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recipeSelectionAdapterListener.onEditeBtnClick(recipeList);
            }
        });
        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recipeSelectionAdapterListener.onDeleteBtnClick(recipeList);
            }
        });
        holder.llRecipeContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                    return;
                }
                recipeSelectionAdapterListener.onDetailBtnClick(recipeList, holder.getAdapterPosition());
                mLastClickTime = SystemClock.elapsedRealtime();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listOfRecipe.size();
    }




    public interface RecipeSelectionAdapterListener{
        void onDetailBtnClick(RecipeObject recipeList, int position);
        void onDeleteBtnClick(RecipeObject recipeObject);
        void onEditeBtnClick(RecipeObject recipeObject);
    }

}

