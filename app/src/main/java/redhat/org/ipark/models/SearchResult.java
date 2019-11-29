package redhat.org.ipark.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

import java.util.Date;

public class SearchResult implements Parcelable {

    private int type;
    private double latitude;
    private double longitude;
    private String startDate;
    private String endDate;

    // Constructor
    public SearchResult(int type, LatLng latLng, String startDate, String endDate) {
        this.type = type;
        this.latitude = latLng.latitude;
        this.longitude = latLng.longitude;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public SearchResult(int type, LatLng latLng, String startDate) {
        this.type = type;
        this.latitude = latLng.latitude;
        this.longitude = latLng.longitude;
        this.startDate = startDate;
        this.endDate = startDate;
    }

    // Getter and setter methods
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    // Parcelling part
    public SearchResult(Parcel in) {
        type = in.readInt();
        latitude = in.readDouble();
        longitude = in.readDouble();
        startDate = in.readString();
        endDate = in.readString();
    }

    public static final Creator<SearchResult> CREATOR = new Creator<SearchResult>() {
        @Override
        public SearchResult createFromParcel(Parcel in) {
            return new SearchResult(in);
        }

        @Override
        public SearchResult[] newArray(int size) {
            return new SearchResult[size];
        }
    };

    public static Creator<SearchResult> getCreator() {
        return CREATOR;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(type);
        parcel.writeDouble(latitude);
        parcel.writeDouble(longitude);
        parcel.writeString(startDate);
        parcel.writeString(endDate);
    }
}
