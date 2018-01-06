package com.foodlabrinth.darpal.demo5.Bookmark_restaurant;

import android.view.View;

import java.util.ArrayList;

/**
 * Simple POJO model for example
 */
public class Item {

    private String cuisine;
    private String pledgePrice;
    private String fromAddress;
    private String toAddress;
    private int requestsCount;
    private String timeDisp;
    private String ambience;
    private String email;
    private String contact;
    private String resid;
    private String avgrate;

    private View.OnClickListener requestBtnClickListener;

    public Item() {
    }

    public Item(String cuisine, String pledgePrice, String fromAddress, String toAddress, String ambience, String avgrate) {
        this.cuisine = cuisine;
        this.pledgePrice = pledgePrice;
        this.fromAddress = fromAddress;
        this.toAddress = toAddress;
        //this.requestsCount = requestsCount;
        //this.timeDisp = date;
        this.ambience = ambience;
        this.avgrate = avgrate;
    }

    public String getAvgrate() {
        return avgrate;
    }

    public void setAvgrate(String avgrate) {
        this.avgrate = avgrate;
    }

    public String getCuisine() {
        return cuisine;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }

    public String getPledgePrice() {
        return pledgePrice;
    }

    public void setPledgePrice(String pledgePrice) {
        this.pledgePrice = pledgePrice;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public String getToAddress() {
        return toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    /*public int getRequestsCount() {
        return requestsCount;
    }*/

    /*public void setRequestsCount(int requestsCount) {
        this.requestsCount = requestsCount;
    }*/

    public String getResid() {
        return resid;
    }

    public void setResid(String resid) {
        this.resid = resid;
    }

    public String getDate() {
        return timeDisp;
    }

    public void setDate(String date) {
        this.timeDisp = date;
    }

    public String getAmbience() {
        return ambience;
    }

    public void setAmbience(String ambience) {
        this.ambience = ambience;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getContact() {
        return contact;
    }

    public View.OnClickListener getRequestBtnClickListener() {
        return requestBtnClickListener;
    }

    public void setRequestBtnClickListener(View.OnClickListener requestBtnClickListener) {
        this.requestBtnClickListener = requestBtnClickListener;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Item item = (Item) o;

        if (cuisine != null ? !cuisine.equals(item.cuisine) : item.cuisine != null) return false;
        if (pledgePrice != null ? !pledgePrice.equals(item.pledgePrice) : item.pledgePrice != null)
            return false;
        if (fromAddress != null ? !fromAddress.equals(item.fromAddress) : item.fromAddress != null)
            return false;
        if (toAddress != null ? !toAddress.equals(item.toAddress) : item.toAddress != null)
            return false;
        //if (date != null ? !date.equals(item.date) : item.date != null) return false;
        return !(ambience != null ? !ambience.equals(item.ambience) : item.ambience != null);

    }

    @Override
    public int hashCode() {
        int result = pledgePrice != null ? pledgePrice.hashCode() : 0;

        result = 31 * result + (fromAddress != null ? fromAddress.hashCode() : 0);
        result = 31 * result + (toAddress != null ? toAddress.hashCode() : 0);
        //result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (ambience != null ? ambience.hashCode() : 0);
        result = 31 * result + (cuisine != null ? cuisine.hashCode() : 0);
        return result;
    }

    /**
     * @return List of elements prepared for tests
     */
    public static ArrayList<Item> getTestingList() {
        ArrayList<Item> arrayList = new ArrayList<>();
        //arrayList.add(new Item("Italian","Rs.500", "Restaurant Name", "Restaurant Address","luxury"));

        return arrayList;

    }


}
