package cars_center_management.MQ;

import java.sql.Date;

public class Message {
    private String senderemail;
    private String receiveremail;
    private String content;
    private Date date;

    public Message() {
    }

    public Message(String senderemail, String receiveremail, String content, Date date) {
        this.senderemail = senderemail;
        this.receiveremail = receiveremail;
        this.content = content;
        this.date = date;
    }

    public String getSenderemail() {
        return senderemail;
    }

    public void setSenderemail(String senderemail) {
        this.senderemail = senderemail;
    }

    public String getReceiveremail() {
        return receiveremail;
    }

    public void setReceiveremail(String receiveremail) {
        this.receiveremail = receiveremail;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Message{" +
                "senderemail='" + senderemail + '\'' +
                ", receiveremail='" + receiveremail + '\'' +
                ", content='" + content + '\'' +
                ", date=" + date +
                '}';
    }
}
