package util;

/**
 * Created by Bill on 2017/4/13.
 * Email androidBaoCP@163.com
 */

public class XmlEntry {
    private String title;
    private String author;
    private String content;

    XmlEntry(String t, String a, String c) {
        title = t;
        author = a;
        content = c;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
