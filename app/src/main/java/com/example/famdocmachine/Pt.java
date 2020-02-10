package com.example.famdocmachine;

import java.io.Serializable;

public class Pt {
    private int pid;
    private String name, phone,age;

    private int did, mid, paid;
    private String date;

    private int price;
    private String dis, md, qtt;

    public Pt(int pid, String name, String phone, String age) {
        this.pid = pid;
        this.name = name;
        this.phone = phone;
        this.age = age;
    }
    public Pt(String md, String qtt) {
        this.md = md;
        this.qtt = qtt;
    }
    public Pt(int pid, String name, String phone, String age, String date, String dis, String md, String qtt, int price, int paid) {
        this.pid = pid;
        this.name = name;
        this.phone = phone;
        this.age = age;
        this.date = date;
        this.dis = dis;
        this.md = md;
        this.qtt = qtt;
        this.price = price;
        this.paid = paid;
    }

    public Pt(int pid, int did, String date, int mid, int paid) {
        this.pid = pid;
        this.did = did;
        this.date = date;
        this.mid = mid;
        this.paid = paid;
    }

    public Pt(int mid, String age, String dis, String md, String qtt, int price) {
        this.mid = mid;
        this.age = age;
        this.dis = dis;
        this.md = md;
        this.qtt = qtt;
        this.price = price;
    }


    /*-------------------------------------------------*/
    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    /*public String getPage() {
        return page;
    }*/

/*-----------------------------------------------------------------*/

    public int getPid() {
        return pid;
    }

    public int getDid() {
        return did;
    }

    public String getDate() {
        return date;
    }

    public int getPaid() {
        return paid;
    }

/*-----------------------------------------------*/

    public int getMid() {
        return mid;
    }

    public String getAge() {
        return age;
    }

    public String getDis() {
        return dis;
    }

    public String getMd() {
        return md;
    }

    public String getQtt() {
        return qtt;
    }

    public int getPrice() {
        return price;
    }
}
