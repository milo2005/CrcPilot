package vult.crc.database.models;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

import java.util.Date;

@DataObject(generateConverter = true)
public class Report {

    private String image;
    private String description;
    private long timestamp;

    public Report(JsonObject json) {
        ReportConverter.fromJson(json, this);
    }

    public Report(String image, String description, Long timestamp) {
        this.image = image;
        this.description = description;
        this.timestamp = timestamp;
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        ReportConverter.toJson(this, json);
        return json;
    }



    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description= description;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
