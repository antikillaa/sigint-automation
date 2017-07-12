package model;


import java.util.ArrayList;
import java.util.List;

public class CBEntities {

    private List<CBEntity> from = new ArrayList<>();
    private List<CBEntity> to = new ArrayList<>();
    private List<CBEntity> cc = new ArrayList<>();

    public List<CBEntity> getFrom() {
        return from;
    }

    public void setFrom(List<CBEntity> from) {
        this.from = from;
    }

    public List<CBEntity> getTo() {
        return to;
    }

    public void setTo(List<CBEntity> to) {
        this.to = to;
    }

    public List<CBEntity> getCc() {
        return cc;
    }

    public void setCc(List<CBEntity> cc) {
        this.cc = cc;
    }
}
