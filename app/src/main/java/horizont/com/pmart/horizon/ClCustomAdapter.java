package horizont.com.pmart.horizon;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

class ClCustomAdapter extends RecyclerView.Adapter<ClCustomAdapter.ViewHolder>{

    private Context context;
    private List<ClDataItem> my_data;


    public ClCustomAdapter(Context context, List<ClDataItem>my_data){
        this.context = context;
        this.my_data = my_data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.ly_cardview,parent,false);
        return new ViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.item.setText(my_data.get(position).getItem_name());
        holder.price.setText(my_data.get(position).getPrice());
        Glide.with(context).load(my_data.get(position).getItem_image()).into(holder.image);

        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //การส่งค่า ไปยัง activity
                Intent intent = new Intent(context, ClItemDetail.class);
                intent.putExtra("item",my_data.get(holder.getAdapterPosition()).getItem_name());
                intent.putExtra("image",my_data.get(holder.getAdapterPosition()).getItem_image());
                context.startActivity(intent);
                Log.d("","TEXT"+ my_data.get(holder.getAdapterPosition()).getItem_name());
                Log.d("","TEXT"+ my_data.get(holder.getAdapterPosition()).getItem_image());
            }
        });

    }

    @Override
    public int getItemCount() {
        return my_data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView item;
        public ImageView image;
        public TextView price;
        public CardView mCardView;

            public ViewHolder(View itemView){
                super(itemView);
                mCardView = (CardView)itemView.findViewById(R.id.card_view);
                item = (TextView)itemView.findViewById(R.id.txt_item);
                image = (ImageView)itemView.findViewById(R.id.img_item);
                price = (TextView)itemView.findViewById(R.id.txt_price);
            }

    }
}
