package horizont.com.pmart.horizon;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.ybq.android.spinkit.SpinKitView;

import java.util.ArrayList;
import java.util.List;

import horizont.com.pmart.horizon.activity.ClDataItem;
import horizont.com.pmart.horizon.activity.ClItemDetail;

public class ClCustomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private OnLoadMoreListener onLoadMoreListener;
    private boolean isLoading;
    private Activity activity;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    ValueFilter valueFilter;
    ViewPager viewAdPager;
    LinearLayout linearLayoutHead;
    private ImageView iconfav;

    private Context context;
    private List<ClDataItem> my_data;

    public ClCustomAdapter(RecyclerView recyclerView, Context context, List<ClDataItem> my_data, Activity activity) {
        this.context = context;
        this.my_data = my_data;
        this.activity = activity;

        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
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

        if (viewType == VIEW_TYPE_ITEM) {
            View itemView = LayoutInflater.from(activity).inflate(R.layout.ly_cardview, parent, false);

            return new UserViewHolder(itemView);
        } else if (viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(activity).inflate(R.layout.ly_loading, parent, false);
            return new LoadingViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@Nullable RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof UserViewHolder) {
            //
            final ClDataItem dataItem = my_data.get(position);
            UserViewHolder viewHolder = (UserViewHolder) holder;
            viewHolder.item.setText(dataItem.getItem_name());
            viewHolder.price.setText(dataItem.getPrice());
            viewHolder.promo_price.setText(dataItem.getPromo_price());
            Glide.with(context).load(dataItem.getItem_image()).into(viewHolder.image);

            viewHolder.mCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //การส่งค่า ไปยัง activity ClItemDetail
                    Intent intent = new Intent(context, ClItemDetail.class);
                    intent.putExtra("item", dataItem.getItem_name());
                    intent.putExtra("image", dataItem.getItem_image());
                    intent.putExtra("price", dataItem.getPrice());
                    context.startActivity(intent);
                    Log.d("", "TEXT" + dataItem.getItem_name());
                    Log.d("", "TEXT" + dataItem.getItem_image());
                }
            });

        } else if (holder instanceof LoadingViewHolder) {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemCount() {
        return (my_data == null ? 0 : my_data.size());
    }

    public void setLoaded() {
        isLoading = false;
    }

    @Override
    public Filter getFilter() {
        if (valueFilter == null) {
            valueFilter = new ValueFilter();
        }
        return valueFilter;
    }

    private class ValueFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if (constraint != null && constraint.length() > 0) {
                List<ClDataItem> filterList = new ArrayList<>();
                for (int i = 0; i < my_data.size(); i++) {
                    if ((my_data.get(i).toString().toUpperCase()).contains(constraint.toString().toUpperCase())) {
                        filterList.add(my_data.get(i));
                    }
                }
                results.count = filterList.size();
                results.values = filterList;
            } else {
                results.count = my_data.size();
                results.values = my_data;
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            my_data = (List<ClDataItem>) results.values;
            notifyDataSetChanged();
        }
    }

    public class LoadingViewHolder extends RecyclerView.ViewHolder {
        public SpinKitView progressBar;

        public LoadingViewHolder(View view) {
            super(view);
            progressBar = (SpinKitView) view.findViewById(R.id.progressBarData);
        }
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        public TextView item;
        public ImageView image;
        public TextView price;
        public CardView mCardView;
        public TextView promo_price;

        public UserViewHolder(View itemView) {
            super(itemView);
            mCardView = (CardView) itemView.findViewById(R.id.card_view);
            item = (TextView) itemView.findViewById(R.id.txt_item);
            image = (ImageView) itemView.findViewById(R.id.img_item);
            price = (TextView) itemView.findViewById(R.id.txt_price);
            promo_price = (TextView) itemView.findViewById(R.id.txt_pro_price);
            price.setPaintFlags(price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
    }
}
