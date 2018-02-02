package cn.hotol.app.common.util;

import cn.hotol.app.bean.dto.gem.GemDto;
import cn.hotol.app.common.Constant;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by LuBin
 * Date 2016-12-19.
 */
public class RandomPrizeUtil {

    /** List 获取 */
    public int getGumByListOne() {
        int prize=0;
        int random = new Random().nextInt(10);
        int prizeRate = 0;// 中奖率
        Iterator<Gem> it = initGemsOne().iterator();
        while (it.hasNext()) {
            Gem gem = it.next();
            prizeRate += gem.getPriority();
            if (random < prizeRate) {
                prize = gem.getName();
                break;
            }
        }
        return prize;
    }

    public int getGumByListTwo() {
        int prize=0;
        int random = new Random().nextInt(10);
        int prizeRate = 0;// 中奖率
        Iterator<Gem> it = initGemsTwo().iterator();
        while (it.hasNext()) {
            Gem gem = it.next();
            prizeRate += gem.getPriority();
            if (random < prizeRate) {
                prize = gem.getName();
                break;
            }
        }
        return prize;
    }

    /** 初始化list */
    List<Gem> initGemsOne() {
        List<Gem> gums = new ArrayList<Gem>();
        gums.add(new Gem(Constant.COUPON_MONEY_ONE, Constant.PRIZE_ONE));
        gums.add(new Gem(Constant.COUPON_MONEY_TWO, Constant.PRIZE_TWO));
        gums.add(new Gem(Constant.COUPON_MONEY_THREE, Constant.PRIZE_THREE));
        return gums;
    }

    List<Gem> initGemsTwo() {
        List<Gem> gums = new ArrayList<Gem>();
        gums.add(new Gem(Constant.COUPON_MONEY_FOUR, Constant.PRIZE_FOUR));
        gums.add(new Gem(Constant.COUPON_MONEY_FIVE, Constant.PRIZE_FIVE));
        gums.add(new Gem(Constant.COUPON_MONEY_SIX, Constant.PRIZE_SIX));
        gums.add(new Gem(Constant.COUPON_MONEY_SEVEN, Constant.PRIZE_SEVEN));
        return gums;
    }

    /**奖品类*/
    class Gem {
        /** 奖品名称 */
        private int name;
        /** 奖品概率 */
        private int priority;

        public Gem() {
            super();
        }

        public Gem(int name, int priority) {
            super();
            this.name = name;
            this.priority = priority;
        }

        @Override
        public String toString() {
            return "Gum [name=" + name + ", priority=" + priority + "]";
        }
        public int getName() {
            return name;
        }

        public void setName(int name) {
            this.name = name;
        }
        public int getPriority() {
            return priority;
        }
        public void setPriority(int priority) {
            this.priority = priority;
        }

    }
    public static BigDecimal getGumByList(int total, List<GemDto> gemDtoList) {
        BigDecimal prize = new BigDecimal(0);
        int random = new Random().nextInt(total);
        int prizeRate = 0;// 中奖率
        Iterator<GemDto> it = gemDtoList.iterator();
        while (it.hasNext()) {
            GemDto gemDto = it.next();
            prizeRate += gemDto.getPriority();
            if (random < prizeRate) {
                prize = gemDto.getName();
                break;
            }
        }
        return prize;
    }

}
