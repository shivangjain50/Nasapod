package au.common.nasapod.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "apod_table")
public class APODModel implements Parcelable {

    @SerializedName("copyright")
    @Expose
    private String copyright;
    @SerializedName("date")
    @Expose
    @PrimaryKey
    @NonNull
    private String date;
    @SerializedName("explanation")
    @Expose
    private String explanation;
    @SerializedName("media_type")
    @Expose
    private String mediaType;
    @SerializedName("service_version")
    @Expose
    private String serviceVersion;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("url")
    @Expose
    private String url;

    public APODModel() {
    }

    protected APODModel(Parcel in) {
        copyright = in.readString();
        date = in.readString();
        explanation = in.readString();
        mediaType = in.readString();
        serviceVersion = in.readString();
        title = in.readString();
        url = in.readString();
    }

    public static final Creator<APODModel> CREATOR = new Creator<APODModel>() {
        @Override
        public APODModel createFromParcel(Parcel in) {
            return new APODModel(in);
        }

        @Override
        public APODModel[] newArray(int size) {
            return new APODModel[size];
        }
    };

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public String getServiceVersion() {
        return serviceVersion;
    }

    public void setServiceVersion(String serviceVersion) {
        this.serviceVersion = serviceVersion;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(copyright);
        dest.writeString(date);
        dest.writeString(explanation);
        dest.writeString(mediaType);
        dest.writeString(serviceVersion);
        dest.writeString(title);
        dest.writeString(url);
    }
}