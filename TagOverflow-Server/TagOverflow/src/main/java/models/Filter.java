package models;

import java.util.List;

/**
 * Created by Apoorv Singh on 4/12/2015.
 */
public class Filter {
    private List<String> included_fields;
    private String filter_type;
    private String filter;

    public List<String> getIncluded_fields() {
        return included_fields;
    }

    public void setIncluded_fields(List<String> included_fields) {
        this.included_fields = included_fields;
    }

    public String getFilter_type() {
        return filter_type;
    }

    public void setFilter_type(String filter_type) {
        this.filter_type = filter_type;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }
}
