package laurcode.com.retire.models;

import com.google.gson.annotations.Expose;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Model object representing the "filters" object contained in the base FreshestPhotos response
 * <p>
 * Created by lauriescheepers on 2017/11/08.
 */

public class PhotoFilters extends RealmObject {

    @PrimaryKey
    private int id;

    @Expose
    private boolean category;

    @Expose
    private RealmList<Integer> exclude;

    public boolean isCategory() {
        return category;
    }

    public void setCategory(boolean category) {
        this.category = category;
    }

    public List<Integer> getExclude() {
        return exclude;
    }

    public void setExclude(RealmList<Integer> exclude) {
        this.exclude = exclude;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
