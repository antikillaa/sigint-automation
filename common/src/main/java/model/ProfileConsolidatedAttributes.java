package model;

import data_for_entity.annotations.DataIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ProfileConsolidatedAttributes {

    @DataIgnore
    private ArrayList<String> image = new ArrayList<>();
    @DataIgnore
    private ArrayList<String> gender = new ArrayList<>();
    @DataIgnore
    private ArrayList<String> nationality = new ArrayList<>();
    @DataIgnore
    private ArrayList<String> document = new ArrayList<>();
    @DataIgnore
    private ArrayList<String> name = new ArrayList<>();
    @DataIgnore
    private ArrayList<String> dateOfBirth = new ArrayList<>();
    @DataIgnore
    private ArrayList<String> image_large = new ArrayList<>();

    public ArrayList<String> getImage() {
        return image;
    }

    public void setImage(ArrayList<String> image) {
        this.image = image;
    }

    public ArrayList<String> getGender() {
        return gender;
    }

    public void setGender(ArrayList<String> gender) {
        this.gender = gender;
    }

    public ArrayList<String> getNationality() {
        return nationality;
    }

    public void setNationality(ArrayList<String> nationality) {
        this.nationality = nationality;
    }

    public ArrayList<String> getDocument() {
        return document;
    }

    public void setDocument(ArrayList<String> document) {
        this.document = document;
    }

    public ArrayList<String> getName() {
        return name;
    }

    public void setName(ArrayList<String> name) {
        this.name = name;
    }

    public ArrayList<String> getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(ArrayList<String> dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public ArrayList<String> getImage_large() {
        return image_large;
    }

    public void setImage_large(ArrayList<String> image_large) {
        this.image_large = image_large;
    }

}
