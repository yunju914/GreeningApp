package com.example.greeningapp;

import java.io.Serializable;
import java.util.ArrayList;

public class MyPoint {
    private String userName;
    private String donationName;
    private int donationPoint;
    private String donationDate;

    public MyPoint() {

    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDonationName() {
        return donationName;
    }

    public void setDonationName(String donationName) {
        this.donationName = donationName;
    }

    public int getDonationPoint() {
        return donationPoint;
    }

    public void setDonationPoint(int donationPoint) {
        this.donationPoint = donationPoint;
    }

    public String getDonationDate() {
        return donationDate;
    }

    public void setDonationDate(String donationDate) {
        this.donationDate = donationDate;
    }

    public static class MyOrder  implements Serializable {
        private String address;
        private String orderId;
        private String eachOrderedId;
        private int overTotalPrice;
        private String phone;
        private int productId;
        private String productName;
        private String productPrice;
        private int totalPrice;
        private String totalQuantity;
        private String userName;
        private String orderDate;
        private String doReview;

        private  String orderImg;
        private  String dataId;

        private String postcode;

        private ArrayList<com.example.greeningapp.MyOrder> childModelArrayList;

        public MyOrder() {

        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getEachOrderedId() {
            return eachOrderedId;
        }

        public void setEachOrderedId(String eachOrderedId) {
            this.eachOrderedId = eachOrderedId;
        }

        public String getPostcode() {
            return postcode;
        }

        public void setPostcode(String postcode) {
            this.postcode = postcode;
        }


        public int getOverTotalPrice() {
            return overTotalPrice;
        }

        public void setOverTotalPrice(int overTotalPrice) {
            this.overTotalPrice = overTotalPrice;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public int getProductId() {
            return productId;
        }

        public void setProductId(int productId) {
            this.productId = productId;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getProductPrice() {
            return productPrice;
        }

        public void setProductPrice(String productPrice) {
            this.productPrice = productPrice;
        }

        public int getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(int totalPrice) {
            this.totalPrice = totalPrice;
        }

        public String getTotalQuantity() {
            return totalQuantity;
        }

        public void setTotalQuantity(String totalQuantity) {
            this.totalQuantity = totalQuantity;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getOrderDate() {
            return orderDate;
        }

        public void setOrderDate(String orderDate) {
            this.orderDate = orderDate;
        }

        public String getOrderImg() {
            return orderImg;
        }

        public void setOrderImg(String orderImg) {
            this.orderImg = orderImg;
        }

        public String getDataId() {
            return dataId;
        }

        public void setDataId(String dataId) {
            this.dataId = dataId;
        }

        public String getDoReview() {
            return doReview;
        }

        public void setDoReview(String doReview) {
            this.doReview = doReview;
        }

        public ArrayList<com.example.greeningapp.MyOrder> getChildModelArrayList() {
            return childModelArrayList;
        }

        public void setChildModelArrayList(ArrayList<com.example.greeningapp.MyOrder> childModelArrayList) {
            this.childModelArrayList = childModelArrayList;
        }
    }
}

