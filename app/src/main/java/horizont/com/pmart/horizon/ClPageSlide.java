package horizont.com.pmart.horizon;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.ImageLoader;

public class ClPageSlide {
    private static ClPageSlide clPageSlide;
    private static Context context;
    private RequestQueue requestQueue;
    private ImageLoader imageLoader;

    public ClPageSlide(Context context) {
        this.context = context;
        this.requestQueue = getResquestQueue();

        imageLoader = new ImageLoader(requestQueue, new ImageLoader.ImageCache() {
            private final LruCache<String,Bitmap> cache = new LruCache<String,Bitmap>(20);
            @Override
            public Bitmap getBitmap(String url) {
                return cache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                cache.put(url, bitmap);
            }
        });
    }
    public static synchronized ClPageSlide getInstance(Context context){
        if(clPageSlide == null){
            clPageSlide = new ClPageSlide(context);
        }
        return clPageSlide;
    }

    public RequestQueue getResquestQueue() {
        if (requestQueue==null){
            Cache cache = new DiskBasedCache(context.getCacheDir(),10*1024*1024);
            Network network = new BasicNetwork(new HurlStack());
            requestQueue = new RequestQueue(cache,network);
            requestQueue.start();
        }
        return requestQueue;
    }
        public ImageLoader getImageLoader(){

            return imageLoader;
        }
}
