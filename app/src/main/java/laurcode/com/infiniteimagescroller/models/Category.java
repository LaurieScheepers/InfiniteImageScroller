package laurcode.com.infiniteimagescroller.models;

import android.annotation.SuppressLint;

import java.util.HashMap;

/**
 * Different types of categories a photo can belong to.
 * <p>
 *
 * Created by lauriescheepers on 2017/11/08.
 *
 * @see <a href="https://github.com/500px/api-documentation/blob/master/basics/formats_and_terms.md#categories">Categories</a>
 */

public enum Category {

    UNCATEGORIZED(0),
    ABSTRACT(10),
    AERIAL(29),
    ANIMALS(11),
    BLACK_AND_WHITE(5),
    CELEBRITIES(1),
    CITY_AND_ARCHITECTURE(9),
    COMMERCIAL(15),
    CONCERT(16),
    FAMILY(20),
    FASHION(14),
    FILM(2),
    FINE_ART(24),
    FOOD(23),
    JOURNALISM(3),
    LANDSCAPES(8),
    MACRO(12),
    NATURE(18),
    NIGHT(30),
    NUDE(4),
    PEOPLE(7),
    PERFORMING_ARTS(19),
    SPORT(17),
    STILL_LIFE(6),
    STREET(21),
    TRANSPORTATION(26),
    TRAVEL(13),
    UNDERWATER(22),
    URBAN_EXPLORATION(27),
    WEDDING(25);

    public int categoryNumber;

    Category(int categoryNumber) {
        this.categoryNumber = categoryNumber;
    }

    // A map of the categories (key is the unique category number, and value is the Category object itself)
    @SuppressLint("UseSparseArrays")
    private static HashMap<Integer, Category> map = new HashMap<>();

    @SuppressLint("UseSparseArrays")
    public static void addCategoriesToMap() {
        if (map == null) {
            map = new HashMap<>();
        }

        for (Category category : Category.values()) {
            map.put(category.categoryNumber, category);
        }
    }

    public static Category categoryFromNumber(int categoryNumber) {
        for (Category category : Category.values()) {
            if (category.categoryNumber == categoryNumber) {
                return category;
            }
        }

        return UNCATEGORIZED;
    }
}
