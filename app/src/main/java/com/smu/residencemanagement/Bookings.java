package com.smu.residencemanagement;


public class Bookings {
    private final int facility;
    private final int bookingTime;

    public Bookings(int facility,int bookingTime) {
        this.facility = facility;
        this.bookingTime = bookingTime;
    }

    public int getFacility() {
        return facility;
    }

    public int getbookingTime() {
       return bookingTime;
    }

}
