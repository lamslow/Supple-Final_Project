package com.example.supple.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.supple.R;
import com.example.supple.activity.ProductDetailsActivity;
import com.example.supple.model.Products;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

import static com.example.supple.activity.LoginActivity.BASE_URL;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductHolder> {

    public static final DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
    public Context context;
    public List<Products> productList;

    public ProductAdapter(Context context, List<Products> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductAdapter.ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_item, parent, false);
        ProductHolder productHolder = new ProductHolder(view);
        return productHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ProductHolder holder, final int position) {
        holder.tvNameProduct.setText(productList.get(position).getProductName());
        holder.tvPriceProduct.setText(decimalFormat.format(productList.get(position).getPrice()));
        Picasso.get().load(BASE_URL+"/public/"+productList.get(position).getImageProduct()).into(holder.imgProduct);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle= new Bundle();
                bundle.putString("nameSP",productList.get(position).getProductName());
                bundle.putDouble("giaSP",productList.get(position).getPrice());
                bundle.putString("motaSP",productList.get(position).getDescription());
                bundle.putString("type",productList.get(position).getClassify());
                bundle.putString("imageSP",BASE_URL+"/public/"+productList.get(position).getImageProduct());
                Intent intent=new Intent(context, ProductDetailsActivity.class);
                intent.putExtra("data",bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (productList == null) return 0;
        else
            return productList.size();
    }

    public class ProductHolder extends RecyclerView.ViewHolder {
        private ImageView imgProduct;
        private TextView tvNameProduct;
        private TextView tvPriceProduct;
        private Products product;

        public ProductHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = (ImageView) itemView.findViewById(R.id.imgProduct);
            tvNameProduct = (TextView) itemView.findViewById(R.id.tvNameProduct);
            tvPriceProduct = (TextView) itemView.findViewById(R.id.tvPriceProduct);
        }
    }
}
