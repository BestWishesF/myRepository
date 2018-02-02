package cn.hotol.app.bean;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by LuBin
 * Date 2016-12-22.
 */
public class TdHtMemberInvite {

    private int invite_id;//主键id 自增长
    private int memb_invite_id;//邀请人id
    private int memb_cover_invite_id;//被邀请人id
    private BigDecimal invite_reward;//首单奖励
    private Timestamp invite_time;//邀请成功时间
    private Timestamp invite_rreward_time;//被邀请人完成首单时间

    public int getInvite_id() {
        return invite_id;
    }

    public void setInvite_id(int invite_id) {
        this.invite_id = invite_id;
    }

    public int getMemb_invite_id() {
        return memb_invite_id;
    }

    public void setMemb_invite_id(int memb_invite_id) {
        this.memb_invite_id = memb_invite_id;
    }

    public int getMemb_cover_invite_id() {
        return memb_cover_invite_id;
    }

    public void setMemb_cover_invite_id(int memb_cover_invite_id) {
        this.memb_cover_invite_id = memb_cover_invite_id;
    }

    public BigDecimal getInvite_reward() {
        return invite_reward;
    }

    public void setInvite_reward(BigDecimal invite_reward) {
        this.invite_reward = invite_reward;
    }

    public Timestamp getInvite_time() {
        return invite_time;
    }

    public void setInvite_time(Timestamp invite_time) {
        this.invite_time = invite_time;
    }

    public Timestamp getInvite_rreward_time() {
        return invite_rreward_time;
    }

    public void setInvite_rreward_time(Timestamp invite_rreward_time) {
        this.invite_rreward_time = invite_rreward_time;
    }
}
