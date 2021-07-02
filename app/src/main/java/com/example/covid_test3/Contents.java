package com.example.covid_test3;

// 데이터들이 들어갈 객체 클래스
public class Contents {
    private String title;
    private String contents;
    private String date;
    private String address;

    public Contents(){} // 생성자

    // Alt + insert

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Contents(String title, String contents, String date, String address){
        this.title = title;
        this.contents = contents;
        this.date = date;
        this.address = address;
    }
}