package laurcode.com.infiniteimagescroller.models;

import com.google.gson.annotations.Expose;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Model class representing freshest photos response
 * <p>
 * Created by lauriescheepers on 2017/11/08.
 */

@SuppressWarnings("WeakerAccess")
public class FreshestPhotos extends RealmObject {

    @PrimaryKey
    private int id;

    @Expose
    private int currentPage;

    @Expose
    private int totalPages;

    @Expose
    private int totalItems;

    @Expose
    private RealmList<Photo> photos;

    @Expose
    private PhotoFilters filters;

    @Expose
    private String feature;

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(RealmList<Photo> photos) {
        this.photos = photos;
    }

    public PhotoFilters getFilters() {
        return filters;
    }

    public void setPhotoFilters(PhotoFilters filters) {
        this.filters = filters;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
