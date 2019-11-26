package redhat.org.ipark.models;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BookItem {

    private Date date;
    private String price;
    private boolean valid;
    private boolean selected;

    public BookItem(Date date, String price, boolean valid) {
        this.date = date;
        this.price = price;
        this.valid = valid;
        this.selected = false;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPrice() {
        if (valid)
            return price;
        else
            return "SOLD OUT";
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public String getStrDay() {
        SimpleDateFormat format = new SimpleDateFormat("E");
        return format.format(date);
    }

    public String getStrDate() {
        SimpleDateFormat format = new SimpleDateFormat("d");
        return format.format(date);
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
