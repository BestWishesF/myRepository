package cn.hotol.app.common.util;

import cn.hotol.app.bean.dto.gem.GemDto;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * Created by Administrator on 2017-04-12.
 */
public class RandomPrizeUtil {

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
