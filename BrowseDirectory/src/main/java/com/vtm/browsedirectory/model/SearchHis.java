package com.vtm.browsedirectory.model;

import java.io.Serializable;
import java.util.Date;

public class SearchHis implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Date date;
    private String slangWord;

    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public String getSlangWord() {
        return slangWord;
    }
    public void setSlangWord(String slangWord) {
        this.slangWord = slangWord;
    }
    @Override
    public String toString() {
        return "SearchHis [date=" + date + ", slangWord=" + slangWord + "]";
    }
    public SearchHis(Date date, String slangWord) {
        this.date = date;
        this.slangWord = slangWord;
    }
    public SearchHis(){}
}
