package redhat.org.ipark.models;

import java.util.List;

public class FaqItem {

    private String title;
    private List<FaqSubItem> items;

    public FaqItem(String title, List<FaqSubItem> items) {
        this.title = title;
        this.items = items;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<FaqSubItem> getItems() {
        return items;
    }

    public void setItems(List<FaqSubItem> items) {
        this.items = items;
    }

}
