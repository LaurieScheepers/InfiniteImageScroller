package laurcode.com.infiniteimagescroller.models;

import com.google.gson.annotations.Expose;

import java.util.List;

import io.realm.RealmList;

/**
 * Model class representing a Photo object
 *
 * @see <a href="https://github.com/500px/api-documentation/blob/master/basics/formats_and_terms.md#short-format">Photo Short Format</a>
 * <p>
 * Created by lauriescheepers on 2017/11/08.
 */

@SuppressWarnings("WeakerAccess")
public class Photo {

    @Expose
    public int id;

    @Expose
    public int userId;

    @Expose
    public String name;

    @Expose
    public String description;

    @Expose
    public String camera;

    @Expose
    public String lens;

    @Expose
    public String focalLength;

    @Expose
    public String iso;

    @Expose
    public String shutterSpeed;

    @Expose
    public String aperture;

    @Expose
    public int timesViewed;

    @Expose
    public double rating;

    @Expose
    public int status;

    @Expose
    public String createdAt;

    @Expose
    public int category;

    @Expose
    public String location;

    @Expose
    public Double latitude;

    @Expose
    public Double longitude;

    @Expose
    public String takenAt;

    @Expose
    public int hiResUploaded;

    @Expose
    public boolean forSale;

    @Expose
    public int width;

    @Expose
    public int height;

    @Expose
    public int votesCount;

    @Expose
    public int favoritesCount;

    @Expose
    public int commentsCount;

    @Expose
    public boolean nsfw;

    @Expose
    public int salesCount;

    @Expose
    public double highestRating;

    @Expose
    public String highestRatingDate;

    @Expose
    public int licenseType;

    @Expose
    public int converted; // Apparently deprecated

    @Expose
    public int collectionsCount;

    @Expose
    public boolean privacy;

    @Expose
    public String imageUrl; // Apparently deprecated

    @Expose
    public RealmList<Image> images;

    @Expose
    public User user;

    @Expose
    public boolean licensingRequested;

    @Expose
    public boolean licensingSuggested;

    @Expose
    public boolean isFreePhoto;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCamera() {
        return camera;
    }

    public void setCamera(String camera) {
        this.camera = camera;
    }

    public String getLens() {
        return lens;
    }

    public void setLens(String lens) {
        this.lens = lens;
    }

    public String getFocalLength() {
        return focalLength;
    }

    public void setFocalLength(String focalLength) {
        this.focalLength = focalLength;
    }

    public String getIso() {
        return iso;
    }

    public void setIso(String iso) {
        this.iso = iso;
    }

    public String getShutterSpeed() {
        return shutterSpeed;
    }

    public void setShutterSpeed(String shutterSpeed) {
        this.shutterSpeed = shutterSpeed;
    }

    public String getAperture() {
        return aperture;
    }

    public void setAperture(String aperture) {
        this.aperture = aperture;
    }

    public int getTimesViewed() {
        return timesViewed;
    }

    public void setTimesViewed(int timesViewed) {
        this.timesViewed = timesViewed;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public Object getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Object getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Object getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getTakenAt() {
        return takenAt;
    }

    public void setTakenAt(String takenAt) {
        this.takenAt = takenAt;
    }

    public int getHiResUploaded() {
        return hiResUploaded;
    }

    public void setHiResUploaded(int hiResUploaded) {
        this.hiResUploaded = hiResUploaded;
    }

    public boolean isForSale() {
        return forSale;
    }

    public void setForSale(boolean forSale) {
        this.forSale = forSale;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getVotesCount() {
        return votesCount;
    }

    public void setVotesCount(int votesCount) {
        this.votesCount = votesCount;
    }

    public int getFavoritesCount() {
        return favoritesCount;
    }

    public void setFavoritesCount(int favoritesCount) {
        this.favoritesCount = favoritesCount;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(int commentsCount) {
        this.commentsCount = commentsCount;
    }

    public boolean isNsfw() {
        return nsfw;
    }

    public void setNsfw(boolean nsfw) {
        this.nsfw = nsfw;
    }

    public int getSalesCount() {
        return salesCount;
    }

    public void setSalesCount(int salesCount) {
        this.salesCount = salesCount;
    }

    public double getHighestRating() {
        return highestRating;
    }

    public void setHighestRating(double highestRating) {
        this.highestRating = highestRating;
    }

    public String getHighestRatingDate() {
        return highestRatingDate;
    }

    public void setHighestRatingDate(String highestRatingDate) {
        this.highestRatingDate = highestRatingDate;
    }

    public int getLicenseType() {
        return licenseType;
    }

    public void setLicenseType(int licenseType) {
        this.licenseType = licenseType;
    }

    public int getConverted() {
        return converted;
    }

    public void setConverted(int converted) {
        this.converted = converted;
    }

    public int getCollectionsCount() {
        return collectionsCount;
    }

    public void setCollectionsCount(int collectionsCount) {
        this.collectionsCount = collectionsCount;
    }

    public boolean isPrivacy() {
        return privacy;
    }

    public void setPrivacy(boolean privacy) {
        this.privacy = privacy;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(RealmList<Image> images) {
        this.images = images;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isLicensingRequested() {
        return licensingRequested;
    }

    public void setLicensingRequested(boolean licensingRequested) {
        this.licensingRequested = licensingRequested;
    }

    public boolean isLicensingSuggested() {
        return licensingSuggested;
    }

    public void setLicensingSuggested(boolean licensingSuggested) {
        this.licensingSuggested = licensingSuggested;
    }

    public boolean isIsFreePhoto() {
        return isFreePhoto;
    }

    public void setIsFreePhoto(boolean isFreePhoto) {
        this.isFreePhoto = isFreePhoto;
    }
}
