package horizont.com.pmart.horizon;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.ybq.android.spinkit.SpinKitView;

import java.util.List;

public class ClCustomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private OnLoadMoreListener onLoadMoreListener;
    private boolean isLoading ;
    private Activity activity;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private GridLayoutManager gridLayoutManager;

    private CardView mCardView;


    private Context context;
    private List<ClDataItem> my_data;

    public ClCustomAdapter(RecyclerView recyclerView ,Context context, List<ClDataItem>my_data,Activity activity){
        this.context = context;
        this.my_data = my_data;
        this.activity = activity;

    final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener(){
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            totalItemCount = linearLayoutManager.getItemCount();
            lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
            if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                if (onLoadMoreListener != null) {
                    onLoadMoreListener.onLoadMore();
                }
                isLoading = true;
            }
        }
    });
    }
    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.onLoadMoreListener = mOnLoadMoreListener;
    }
    @Override
    public int getItemViewType(int position) {
        return my_data.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == VIEW_TYPE_ITEM){
            View itemView = LayoutInflater.from(activity).inflate(R.layout.ly_cardview,parent,false);
            return new UserViewHolder(itemView);
        }
        else if (viewType == VIEW_TYPE_LOADING){
            View view = LayoutInflater.from(activity).inflate(R.layout.item_loading, parent, false);
            return  new LoadingViewHolder(view);
        }
        return null;

    }

    @Override
    public void onBindViewHolder(@Nullable RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof UserViewHolder){
            //
            final ClDataItem dataItem = my_data.get(position);
            UserViewHolder viewHolder = (UserViewHolder)holder;
            viewHolder.item.setText(dataItem.getItem_name());
            viewHolder.price.setText(dataItem.getPrice());
            Glide.with(context).load(dataItem.getItem_image()).into(viewHolder.image);

            viewHolder.mCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //การส่งค่า ไปยัง activity
                    Intent intent = new Intent(context, ClItemDetail.class);
                    intent.putExtra("item",dataItem.getItem_name());
                    intent.putExtra("image",dataItem.getItem_image());
                    context.startActivity(intent);
                    Log.d("","TEXT"+ dataItem.getItem_name());
                    Log.d("","TEXT"+ dataItem.getItem_image());
                }
            });

        } else if (holder instanceof LoadingViewHolder) {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemCount() {
        return ( my_data == null ? 0 : my_data.size());
    }
    public void setLoaded() {
        isLoading = false;
    }

    public class LoadingViewHolder extends RecyclerView.ViewHolder {
        public SpinKitView progressBar;

        public LoadingViewHolder(View view) {
            super(view);
            progressBar = (SpinKitView) view.findViewById(R.id.progressBarData);
//            progressBar.setVisibility(View.GONE);
        }
    }

    public class UserViewHolder extends RecyclerView.ViewHolder{
        public TextView item;
        public ImageView image;
        public TextView price;
        public CardView mCardView;

            public UserViewHolder(View itemView){
                super(itemView);
                mCardView = (CardView)itemView.findViewById(R.id.card_view);
                item = (TextView)itemView.findViewById(R.id.txt_item);
                image = (ImageView)itemView.findViewById(R.id.img_item);
                price = (TextView)itemView.findViewById(R.id.txt_price);
            }
    }
}
