package wuxiang.miku.scorpio.paperactivate.bean;

import cn.bmob.v3.BmobObject;

public class Note extends BmobObject {
    private String note;
    private String username;

    public Note(String note, String username) {
        this.note = note;
        this.username = username;
    }

    public String getNote() {
        return note;
    }

    public String getUsername() {
        return username;
    }


}
