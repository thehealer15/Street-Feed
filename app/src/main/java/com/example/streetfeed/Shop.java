package com.example.streetfeed;

import static com.example.streetfeed.Customer_bottom_nav.user_latitude;
import static com.example.streetfeed.Customer_bottom_nav.user_longitude;

public class Shop {
    public    String shopName,ownerName,shopAddress,shopLandmark,shopAdharNumber,shopCategory, imageUri, postalCode,mobile_no;

    public Double shopLatitude, shopLongitude;
//    Uri imageId;

    public Shop(){

    }

    public Shop(String shopName,String ownerName, String shopAddress, String shopLandmark,
                String shopAdharNumber, String shopCategory, Double shopLatitude, Double shopLongitude, String imageUri,String postalCode,String mobile_no) {
        this.shopName = shopName;
        this.ownerName=ownerName;
        this.shopAddress = shopAddress;
        this.shopLandmark = shopLandmark;
        this.shopAdharNumber = shopAdharNumber;
        this.shopCategory = shopCategory;
        this.shopLatitude = shopLatitude;
        this.shopLongitude = shopLongitude;
        this.imageUri=imageUri;
        this.postalCode=postalCode;
        this.mobile_no=mobile_no;

    }


    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    public String getShopLandmark() {
        return shopLandmark;
    }

    public void setShopLandmark(String shopLandmark) {
        this.shopLandmark = shopLandmark;
    }

    public String getShopAdharNumber() {
        return shopAdharNumber;
    }

    public void setShopAdharNumber(String shopAdharNumber) {
        this.shopAdharNumber = shopAdharNumber;
    }

    public String getShopCategory() {
        return shopCategory;
    }

    public void setShopCategory(String shopCategory) {
        this.shopCategory = shopCategory;
    }

    public Double getShopLatitude() {
        return shopLatitude;
    }

    public void setShopLatitude(Double shopLatitude) {
        this.shopLatitude = shopLatitude;
    }

    public Double getShopLongitude() {
        return shopLongitude;
    }

    public void setShopLongitude(Double shopLongitude) {
        this.shopLongitude = shopLongitude;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }
}

