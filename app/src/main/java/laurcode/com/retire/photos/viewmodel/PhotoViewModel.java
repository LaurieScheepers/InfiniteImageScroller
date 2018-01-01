package laurcode.com.retire.photos.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import java.util.List;

import laurcode.com.retire.BR;
import laurcode.com.retire.models.Image;
import laurcode.com.retire.models.User;

/**
 * The view model for a single photo in the feed
 * <p>
 * Created by lauriescheepers on 2017/11/23.
 */

public class PhotoViewModel extends BaseObservable {

    @Bindable
    private String name;

    @Bindable
    private String description;

    @Bindable
    private int timesViewed;

    @Bindable
    private double rating;

    @Bindable
    private String createdAt;

    @Bindable
    private String location;

    @Bindable
    private int votesCount;

    @Bindable
    private int favoritesCount;

    @Bindable
    private int commentsCount;

    @Bindable
    private boolean nsfw;

    @Bindable
    private int salesCount;

    @Bindable
    private double highestRating;

    @Bindable
    private int licenseType;

    @Bindable
    private List<Image> images;

    @Bindable
    private User user;

    @Bindable
    private boolean isFreePhoto;

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    @Bindable
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        notifyPropertyChanged(BR.description);
    }

    @Bindable
    public int getTimesViewed() {
        return timesViewed;
    }

    public void setTimesViewed(int timesViewed) {
        this.timesViewed = timesViewed;
        notifyPropertyChanged(BR.timesViewed);
    }

    @Bindable
    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
        notifyPropertyChanged(BR.rating);
    }

    @Bindable
    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
        notifyPropertyChanged(BR.createdAt);
    }

    @Bindable
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
        notifyPropertyChanged(BR.location);
    }

    @Bindable
    public int getVotesCount() {
        return votesCount;
    }

    public void setVotesCount(int votesCount) {
        this.votesCount = votesCount;
        notifyPropertyChanged(BR.votesCount);
    }

    @Bindable
    public int getFavoritesCount() {
        return favoritesCount;
    }

    public void setFavoritesCount(int favoritesCount) {
        this.favoritesCount = favoritesCount;
        notifyPropertyChanged(BR.favoritesCount);
    }

    @Bindable
    public int getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(int commentsCount) {
        this.commentsCount = commentsCount;
        notifyPropertyChanged(BR.commentsCount);
    }

    @Bindable
    public boolean isNsfw() {
        return nsfw;
    }

    public void setNsfw(boolean nsfw) {
        this.nsfw = nsfw;
        notifyPropertyChanged(BR.nsfw);
    }

    @Bindable
    public int getSalesCount() {
        return salesCount;
    }

    public void setSalesCount(int salesCount) {
        this.salesCount = salesCount;
        notifyPropertyChanged(BR.salesCount);
    }

    @Bindable
    public double getHighestRating() {
        return highestRating;
    }

    public void setHighestRating(double highestRating) {
        this.highestRating = highestRating;
        notifyPropertyChanged(BR.highestRating);
    }

    @Bindable
    public int getLicenseType() {
        return licenseType;
    }

    public void setLicenseType(int licenseType) {
        this.licenseType = licenseType;
        notifyPropertyChanged(BR.licenseType);
    }

    @Bindable
    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
        notifyPropertyChanged(BR.images);
    }

    @Bindable
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        notifyPropertyChanged(BR.user);
    }

    @Bindable
    public boolean isFreePhoto() {
        return isFreePhoto;
    }

    public void setIsFreePhoto(boolean freePhoto) {
        isFreePhoto = freePhoto;
        notifyPropertyChanged(BR.isFreePhoto);
    }
}
