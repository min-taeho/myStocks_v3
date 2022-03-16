package com.nuritech.stock.mystock.common.util;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.nuritech.stock.mystock.common.constant.ReferenceSiteConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 엔티티를 업데이트 할때 업데이트 항목 이외 항목의 값을 채워주는 클래스
 */
@Slf4j
@Component
public class ScrapUtils {

    /**
     * IEX Cloud에 HTTP Connection으로 연결하고 결과를 스크랩한다.
     *
     * @param ticker
     * @return 스크랩 결과
     */
    public static StringBuilder getStockPriceFromIexCloud(String ticker) {
        StringBuilder sb = new StringBuilder();
        try {
            URL url = new URL(makeUrl4StockInfoFromIexCloud(ticker));
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("User-Agent", ReferenceSiteConstants._USER_AGENT);
            con.setConnectTimeout(5000);
            con.setReadTimeout(5000);
            con.setDoOutput(false);

            if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
                // Stream을 처리해줘야 하는 귀찮음이 있음.
                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
                String inputLine;
                while ((inputLine = br.readLine()) != null) {
                    sb.append(inputLine).append("\n");
                }
                br.close();
            } else {
                log.info(con.getResponseMessage());
            }
            con.disconnect();

        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return sb;
    }

    /**
     * Yahoo Finance에 HTTP Connection으로 연결하고 결과를 스크랩한다.
     *
     * @param ticker
     * @return 스크랩 결과
     */
    public static Map getStockInfoFromYahooFnnance(String ticker) {
        Map<String, Object> stockInfo = new HashMap<>();
        try {
            URL url = new URL(makeUrl4StockInfoFromYahooFinance(ticker));
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("User-Agent", ReferenceSiteConstants._USER_AGENT);
            con.setConnectTimeout(5000);
            con.setReadTimeout(5000);
            con.setDoOutput(false);

            if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
                // Stream을 처리해줘야 하는 귀찮음이 있음.
                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
                String inputLine;
                String prefixStr = "root.App.main = ";
                while ((inputLine = br.readLine()) != null) {
                    if ( inputLine.startsWith(prefixStr) ) {

                        String jsonStr = inputLine.replaceAll(prefixStr, "");
                        jsonStr = inputLine.substring(inputLine.indexOf("QuoteSummaryStore")+19, inputLine.indexOf("FinanceConfigStore")-2);
                        jsonStr = jsonStr.replaceAll("\"", "'");

                        Gson gson = new Gson();
                        Map<String, Object> map = gson.fromJson(jsonStr, Map.class);
                        stockInfo.put("stockNm", (String)((LinkedTreeMap)map.get("price")).get("longName"));
                        stockInfo.put("currentPrice", toBigDecimal( ((LinkedTreeMap)((LinkedTreeMap)map.get("price")).get("regularMarketPrice")).get("raw")) );
                        stockInfo.put("highestPrice", toBigDecimal( ((LinkedTreeMap)((LinkedTreeMap)map.get("summaryDetail")).get("fiftyTwoWeekHigh")).get("raw")) );
                        stockInfo.put("lowerPrice", toBigDecimal( ((LinkedTreeMap)((LinkedTreeMap)map.get("summaryDetail")).get("fiftyTwoWeekLow")).get("raw")) );

                        stockInfo.put("annualPayout", null);
                        if ( ((LinkedTreeMap) ((LinkedTreeMap) map.get("summaryDetail")).get("dividendRate")).get("raw") != null ) {
                            stockInfo.put("annualPayout", toBigDecimal(((LinkedTreeMap) ((LinkedTreeMap) map.get("summaryDetail")).get("dividendRate")).get("raw")));
                        }

                        stockInfo.put("divYield", null);
                        if ( ((LinkedTreeMap) ((LinkedTreeMap) map.get("summaryDetail")).get("dividendYield")).get("raw") != null ) {
                            stockInfo.put("divYield", toBigDecimal(((Double) ((LinkedTreeMap) ((LinkedTreeMap) map.get("summaryDetail")).get("dividendYield")).get("raw")) * 100).setScale(2, RoundingMode.HALF_EVEN));
                        }

                        stockInfo.put("payoutRatio", null);
                        if ( ((LinkedTreeMap) ((LinkedTreeMap) map.get("summaryDetail")).get("payoutRatio")).get("raw") != null ) {
                            stockInfo.put("payoutRatio", toBigDecimal(((Double) ((LinkedTreeMap) ((LinkedTreeMap) map.get("summaryDetail")).get("payoutRatio")).get("raw")) * 100).setScale(2, RoundingMode.HALF_EVEN));
                        }

                        stockInfo.put("fiveYearAvgDividendYield", null);
                        if ( ((LinkedTreeMap) ((LinkedTreeMap) map.get("summaryDetail")).get("fiveYearAvgDividendYield")).get("raw") != null ) {
                            stockInfo.put("fiveYearAvgDividendYield", toBigDecimal(((LinkedTreeMap) ((LinkedTreeMap) map.get("summaryDetail")).get("fiveYearAvgDividendYield")).get("raw")));
                        }
                    }

                }
                br.close();
            } else {
                log.info(con.getResponseMessage());
            }
            con.disconnect();

        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return stockInfo;
    }

    /**
     * IEX Cloud 에서 가격 정보를 조회하기 위해 URI를 생성한다.
     *
     * @param ticker
     * @return IEX Cloud 주식 가격정보 조회 URI (ticker, 토큰 포함)
     */
    private static String makeUrl4StockInfoFromIexCloud(String ticker) {
        return new StringBuilder()
                .append(ReferenceSiteConstants._PRICE_INFO_URL)
                .append(ticker)
                .append("/quote?token=")
                .append(ReferenceSiteConstants._IEXCLOUD_API_TOKEN).toString();
    }

    /**
     * Yahoo Finance 에서 주식 정보를 조회하기 위해 URI를 생성한다.
     *
     * @param ticker
     * @return
     */
    private static String makeUrl4StockInfoFromYahooFinance(String ticker) {
        return new StringBuilder()
                .append(ReferenceSiteConstants._YAHOO_FINANCE_URL)
                .append(ticker)
                .append("/key-statistics?p=")
                .append(ticker).toString();
    }

    private static BigDecimal toBigDecimal(Object obj) {
        if (obj instanceof BigInteger) {
            return new BigDecimal(obj.toString());
        } else if (obj instanceof Integer) {
            return new BigDecimal(obj.toString());
        } else if (obj instanceof Double) {
            return new BigDecimal(obj.toString());
        } else {
            return (BigDecimal) obj;
        }
    }

}
