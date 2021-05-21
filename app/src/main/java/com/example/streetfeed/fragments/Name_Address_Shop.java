package com.example.streetfeed.fragments;

public class Name_Address_Shop
{
    String shopName,address,purl;
    Name_Address_Shop()
    {
            //empty constructor needed
    }
    public Name_Address_Shop(String shopName,String address,String purl)
    {
        this.shopName=shopName;
        this.address=address;
        this.purl=purl;
    }

    public String getName() {
        return shopName;
    }

    public void setName(String name_of_shop) {
        this.shopName = name_of_shop;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPurl() {
        return purl;
    }

    public void setPurl(String purl) {
        this.purl = purl;
    }
}
