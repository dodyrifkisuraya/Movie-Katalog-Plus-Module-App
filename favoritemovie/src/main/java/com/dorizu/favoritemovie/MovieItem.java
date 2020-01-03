package com.dorizu.favoritemovie;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.dorizu.favoritemovie.helper.DatabaseContract;

import org.json.JSONObject;

import static com.dorizu.favoritemovie.helper.DatabaseContract.NoteCulumns.*;

public class MovieItem implements Parcelable {

    private int id;
    private String judul;
    private String tanggalRilis;
    private String overview;
    private String bahasa;
    private String thunail;
    private String banner;
    private String rate;
    private String rateCount;

    public MovieItem() {
    }

    protected MovieItem(Parcel in) {
        id = in.readInt();
        judul = in.readString();
        tanggalRilis = in.readString();
        overview = in.readString();
        bahasa = in.readString();
        thunail = in.readString();
        banner = in.readString();
        rate = in.readString();
        rateCount = in.readString();
    }

    public static final Creator<MovieItem> CREATOR = new Creator<MovieItem>() {
        @Override
        public MovieItem createFromParcel(Parcel in) {
            return new MovieItem(in);
        }

        @Override
        public MovieItem[] newArray(int size) {
            return new MovieItem[size];
        }
    };

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getTanggalRilis() {
        return tanggalRilis;
    }

    public void setTanggalRilis(String tanggalRilis) {
        this.tanggalRilis = tanggalRilis;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getBahasa() {
        return bahasa;
    }

    public void setBahasa(String bahasa) {
        this.bahasa = bahasa;
    }

    public String getThunail() {
        return thunail;
    }

    public void setThunail(String thunail) {
        this.thunail = thunail;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getRateCount() {
        return rateCount;
    }

    public void setRateCount(String rateCount) {
        this.rateCount = rateCount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(judul);
        dest.writeString(tanggalRilis);
        dest.writeString(overview);
        dest.writeString(bahasa);
        dest.writeString(thunail);
        dest.writeString(banner);
        dest.writeString(rate);
        dest.writeString(rateCount);
    }

    public MovieItem(JSONObject object){
        try {
            int id = object.getInt("id");
            String judul = object.getString("title");
            String ori_lang = object.getString("original_language");
            String date = object.getString("release_date");
            String rate = object.getString("vote_average");
            String rateCount = object.getString("vote_count");
            String description = object.getString("overview");
            String banner = BuildConfig.API_URL_PHOTO + object.getString("backdrop_path");
            String thumnail = BuildConfig.API_URL_PHOTO + object.getString("poster_path");

            this.id = id;
            this.judul = judul;
            this.bahasa = ori_lang;
            this.tanggalRilis = date;
            this.rate = rate;
            this.rateCount = rateCount;
            this.overview = description;
            this.thunail = thumnail;
            this.banner = banner;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public MovieItem(Cursor cursor){
        int id = cursor.getColumnIndex(_ID);
        int judul = cursor.getColumnIndex(TITLE);
        int ori_lang = cursor.getColumnIndex(LANGUAGE);
        int date = cursor.getColumnIndex(DATE);
        int rate = cursor.getColumnIndex(RATE);
        int rateCount = cursor.getColumnIndex(RATE_COUNT);
        int description = cursor.getColumnIndex(DESCRIPTION);
        int thumnail = cursor.getColumnIndex(POSTER_PATH);
        int banner = cursor.getColumnIndex(BANNER);

        this.id = cursor.getInt(id);
        this.judul = cursor.getString(judul);
        this.bahasa = cursor.getString(ori_lang);
        this.tanggalRilis = cursor.getString(date);
        this.rate =cursor.getString(rate);
        this.rateCount = cursor.getString(rateCount);
        this.overview = cursor.getString(description);
        this.thunail = cursor.getString(thumnail);
        this.banner = cursor.getString(banner);
    }
}
