package horizont.com.pmart.horizon.model;

public class ClDataItem {

    private String item_name;
    private String item_image;
    private String price;
    private String promo_price;
    private String item_promodesc;
    private String item_image_2;

    public ClDataItem(String item_name, String item_image, String price, String promo_price, String item_promodesc,String item_image_2 ) {
        this.item_name = item_name;
        this.item_image = item_image;
        this.price = price;
        this.promo_price = promo_price;
        this.item_promodesc = item_promodesc;
        this.item_image_2 = item_image_2;

    }

    public String getPromo_price() {
        return promo_price;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getItem_image() {
        return item_image;
    }

    public void setItem_image(String item_image) {
        this.item_image = item_image;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getItem_promodesc() {
        return item_promodesc;
    }

    public void setItem_promodesc(String item_promodesc) {
        this.item_promodesc = item_promodesc;
    }

    public String getItem_image_2() {
        return item_image_2;
    }

    public void setItem_image_2(String item_image_2) {
        this.item_image_2 = item_image_2;
    }
}
