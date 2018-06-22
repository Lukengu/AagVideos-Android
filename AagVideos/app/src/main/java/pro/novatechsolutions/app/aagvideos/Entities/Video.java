package pro.novatechsolutions.app.aagvideos.Entities;

import java.io.Serializable;

public class Video implements Serializable {

    private String title;
    private String description;
    private int id;
    private String asset_url;
    private String poster_url;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAsset_url() {
        return asset_url;
    }

    public void setAsset_url(String asset_url) {
        this.asset_url = asset_url;
    }

    public String getPoster_url() {
        return poster_url;
    }

    public void setPoster_url(String poster_url) {
        this.poster_url = poster_url;
    }
}
