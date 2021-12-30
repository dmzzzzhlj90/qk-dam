package groovy

import java.text.SimpleDateFormat

import static java.util.Calendar.DATE

/**
 * 交易日期处理
 */
class TradeDate {

    def tradeDay(String tradeDay, int n) {
        Date date
        String newDate

        date = new SimpleDateFormat("yyyyMMdd").parse(tradeDay)

        Calendar cal = Calendar.getInstance();
        cal.setTime(date)
        cal.add(DATE, -n);

        newDate = new SimpleDateFormat("yyyyMMdd").format(cal.getTime())
        return newDate
    }

}