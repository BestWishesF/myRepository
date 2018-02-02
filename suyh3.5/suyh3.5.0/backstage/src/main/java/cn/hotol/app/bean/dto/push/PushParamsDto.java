package cn.hotol.app.bean.dto.push;

/**
 * Created by Administrator on 2017-04-14.
 */
public class PushParamsDto {

    private String title;//推送标题
    private String text;//推送内容
    private String open_id;//推送功能标识
    private String cid;//推送token
    private String sound;//声音文件

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getOpen_id() {
        return open_id;
    }

    public void setOpen_id(String open_id) {
        this.open_id = open_id;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getSound() {
        return sound;
    }

    public void setSound(String sound) {
        this.sound = sound;
    }
}
