package com.kafka.streams.maximumtradedvalue.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;


public class DailyStockTradeData implements Serializable {

    @JsonProperty("SYMBOL")
    private String symbol;

    @JsonProperty("SERIES")
    private String series;

    @JsonProperty("OPEN")
    private Double open;

    @JsonProperty("HIGH")
    private Double high;

    @JsonProperty("LOW")
    private Double low;

    @JsonProperty("CLOSE")
    private Double close;

    @JsonProperty("LAST")
    private Double last;

    @JsonProperty("PREVCLOSE")
    private Double prevClose;

    @JsonProperty("TOTTRDQTY")
    private Integer totTrdQty;

    @JsonProperty("TOTTRDVAL")
    private Double totTrdVal;

    @JsonProperty("TIMESTAMP")
    private String timestamp;

    @JsonProperty("TOTALTRADES")
    private Integer totalTrades;

    @JsonProperty("ISIN")
    private String isin;

    public DailyStockTradeData() {
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public Double getOpen() {
        return open;
    }

    public void setOpen(Double open) {
        this.open = open;
    }

    public Double getHigh() {
        return high;
    }

    public void setHigh(Double high) {
        this.high = high;
    }

    public Double getLow() {
        return low;
    }

    public void setLow(Double low) {
        this.low = low;
    }

    public Double getClose() {
        return close;
    }

    public void setClose(Double close) {
        this.close = close;
    }

    public Double getLast() {
        return last;
    }

    public void setLast(Double last) {
        this.last = last;
    }

    public Double getPrevClose() {
        return prevClose;
    }

    public void setPrevClose(Double prevClose) {
        this.prevClose = prevClose;
    }

    public Integer getTotTrdQty() {
        return totTrdQty;
    }

    public void setTotTrdQty(Integer totTrdQty) {
        this.totTrdQty = totTrdQty;
    }

    public Double getTotTrdVal() {
        return totTrdVal;
    }

    public void setTotTrdVal(Double totTrdVal) {
        this.totTrdVal = totTrdVal;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getTotalTrades() {
        return totalTrades;
    }

    public void setTotalTrades(Integer totalTrades) {
        this.totalTrades = totalTrades;
    }

    public String getIsin() {
        return isin;
    }

    public void setIsin(String isin) {
        this.isin = isin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DailyStockTradeData)) return false;

        DailyStockTradeData that = (DailyStockTradeData) o;

        if (symbol != null ? !symbol.equals(that.symbol) : that.symbol != null) return false;
        if (series != null ? !series.equals(that.series) : that.series != null) return false;
        if (open != null ? !open.equals(that.open) : that.open != null) return false;
        if (high != null ? !high.equals(that.high) : that.high != null) return false;
        if (low != null ? !low.equals(that.low) : that.low != null) return false;
        if (close != null ? !close.equals(that.close) : that.close != null) return false;
        if (last != null ? !last.equals(that.last) : that.last != null) return false;
        if (prevClose != null ? !prevClose.equals(that.prevClose) : that.prevClose != null) return false;
        if (totTrdQty != null ? !totTrdQty.equals(that.totTrdQty) : that.totTrdQty != null) return false;
        if (totTrdVal != null ? !totTrdVal.equals(that.totTrdVal) : that.totTrdVal != null) return false;
        if (timestamp != null ? !timestamp.equals(that.timestamp) : that.timestamp != null) return false;
        if (totalTrades != null ? !totalTrades.equals(that.totalTrades) : that.totalTrades != null) return false;
        return isin != null ? isin.equals(that.isin) : that.isin == null;
    }

    @Override
    public int hashCode() {
        int result = symbol != null ? symbol.hashCode() : 0;
        result = 31 * result + (series != null ? series.hashCode() : 0);
        result = 31 * result + (open != null ? open.hashCode() : 0);
        result = 31 * result + (high != null ? high.hashCode() : 0);
        result = 31 * result + (low != null ? low.hashCode() : 0);
        result = 31 * result + (close != null ? close.hashCode() : 0);
        result = 31 * result + (last != null ? last.hashCode() : 0);
        result = 31 * result + (prevClose != null ? prevClose.hashCode() : 0);
        result = 31 * result + (totTrdQty != null ? totTrdQty.hashCode() : 0);
        result = 31 * result + (totTrdVal != null ? totTrdVal.hashCode() : 0);
        result = 31 * result + (timestamp != null ? timestamp.hashCode() : 0);
        result = 31 * result + (totalTrades != null ? totalTrades.hashCode() : 0);
        result = 31 * result + (isin != null ? isin.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "DailyStockTradeData{" +
                "symbol='" + symbol + '\'' +
                ", series='" + series + '\'' +
                ", open=" + open +
                ", high=" + high +
                ", low=" + low +
                ", close=" + close +
                ", last=" + last +
                ", prevClose=" + prevClose +
                ", totTrdQty=" + totTrdQty +
                ", totTrdVal=" + totTrdVal +
                ", timestamp=" + timestamp +
                ", totalTrades=" + totalTrades +
                ", isin='" + isin + '\'' +
                '}';
    }
}
