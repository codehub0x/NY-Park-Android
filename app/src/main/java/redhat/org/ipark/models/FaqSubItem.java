package redhat.org.ipark.models;

public class FaqSubItem {

    public enum ItemType {
        Collapsed, Expanded
    }

    private String question;
    private String answer;
    private ItemType itemType;

    public FaqSubItem(String question, String answer) {
        this.question = question;
        this.answer = answer;
        this.itemType = ItemType.Collapsed;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }
}
