package com.smu.residencemanagement;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.view.LayoutInflater;

import java.util.*;


public class UpcomingBookingsAdapter extends BaseAdapter {

    private final Context mContext;
    private final ArrayList<Bookings> bookings;

    // 1
    public UpcomingBookingsAdapter(Context context, ArrayList<Bookings> bookings) {
        this.mContext = context;
        this.bookings = bookings;
    }

    // 2
    @Override
    public int getCount() {
        return bookings.size();
    }

    // 3
    @Override
    public long getItemId(int position) {
        return 0;
    }

    // 4
    @Override
    public Object getItem(int position) {
        return null;
    }

    // 5
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 1
        final Bookings booking = bookings.get(position);

        // 2
        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.linearlayout_bookings, null);
        }

        // 3
        final TextView facilityTextView = (TextView)convertView.findViewById(R.id.textview_bookings_facility);
        final TextView bookingTimeTextView = (TextView)convertView.findViewById(R.id.textview_bookings_bookingTime);

        // 4

        facilityTextView.setText(mContext.getString(booking.getFacility()));
        bookingTimeTextView.setText(mContext.getString(booking.getbookingTime()));

        return convertView;
    }

}