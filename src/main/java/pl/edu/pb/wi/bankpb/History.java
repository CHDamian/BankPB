package pl.edu.pb.wi.bankpb;

public class History {
    private final String type;
    private final String title;

    public History(String type, String title) {
        this.type = type;
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }
}
