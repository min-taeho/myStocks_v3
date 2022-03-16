package com.nuritech.stock.mystock.dto.scrap;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class IexCloudStockPriceDto {
    //private List<RealtimeIdDto> data;
    private String symbol;
    private String companyName;
    private String primaryExchange;
    private String calculationPrice;
    private BigDecimal open;
    //private Date openTime;
    private String openSource;
    private BigDecimal close;
    //private Date closeTime;
    private String closeSource;
    private BigDecimal high;
    //private double highTime;
    private String highSource;
    private BigDecimal low;
    ///private double lowTime;
    private String lowSource;
    private BigDecimal latestPrice;
    private String latestSource;
    private String latestTime;
    //private double latestUpdate;
    private int latestVolume;
    private BigDecimal iexRealtimePrice;
    private String iexRealtimeSize;
    private String iexLastUpdated;
    private BigDecimal delayedPrice;
    //private Date delayedPriceTime;
    private BigDecimal oddLotDelayedPrice;
    //private Date oddLotDelayedPriceTime;
    private BigDecimal extendedPrice;
    private BigDecimal extendedChange;
    private BigDecimal extendedChangePercent;
    //private Date extendedPriceTime;
    private BigDecimal previousClose;
    private int previousVolume;
    private BigDecimal change;
    private BigDecimal changePercent;
    private int volume;
    private BigDecimal iexMarketPercent;
    private int iexVolume;
    private int avgTotalVolume;
    private BigDecimal iexBidPrice;
    private String iexBidSize;
    private BigDecimal iexAskPrice;
    private String iexAskSize;
    private BigDecimal iexOpen;
    //private double iexOpenTime;
    private BigDecimal iexClose;
    //private double iexCloseTime;
    private BigDecimal marketCap;
    private BigDecimal peRatio;
    private BigDecimal week52High;
    private BigDecimal week52Low;
    private BigDecimal ytdChange;
    //private double lastTradeTime;
    private Boolean isUSMarketOpen;
}
