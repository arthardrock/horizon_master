package horizont.com.pmart.horizon;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.toolbox.ImageLoader;

import java.util.List;

import horizont.com.pmart.horizon.model.ClSlideUnit;

public class AdViewPagerAdapter extends PagerAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    private List<ClSlideUnit>slidImg;
    private ImageLoader imageLoader;
    //private Integer[] image = {R.drawable.comming, R.drawable.comming, R.drawable.comming};

    public AdViewPagerAdapter(List<ClSlideUnit>slidImg, Context context) {
        this.slidImg = slidImg;
        this.context = context;
    }

    @Override
    public int getCount() {
        return slidImg.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.ly_adpage_header, null);

        ClSlideUnit unit = slidImg.get(position);


        ImageView imageView = (ImageView) view.findViewById(R.id.img_ad);
        //imageView.setImageResource(image[position]);

        imageLoader = ClPageSlide.getInstance(context).getImageLoader();
        imageLoader.get(unit.getSliderImgUrl(),ImageLoader.getImageListener(imageView, R.drawable.icon1,android.R.drawable.ic_dialog_alert));


        ViewPager vp = (ViewPager) container;
        vp.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);
    }
}
