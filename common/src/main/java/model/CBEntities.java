package model;


import org.codehaus.jackson.annotate.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class CBEntities {

    private List<CBEntity> from = new ArrayList<>();
    private List<CBEntity> to = new ArrayList<>();
    private List<CBEntity> cc = new ArrayList<>();
    private List<CBEntity> bcc = new ArrayList<>();
    @JsonProperty("Ticket")
    private List<CBEntity> ticket = new ArrayList<>();
    @JsonProperty("Identity")
    private List<CBEntity> identity = new ArrayList<>();
    @JsonProperty("Payment")
    private List<CBEntity> payment = new ArrayList<>();
    @JsonProperty("Reservation")
    private List<CBEntity> reservation = new ArrayList<>();
    private List<CBEntity> room = new ArrayList<>();
    private List<CBEntity> unknown = new ArrayList<>();

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

    public List<CBEntity> getBcc() {
        return bcc;
    }

    public void setBcc(List<CBEntity> bcc) {
        this.bcc = bcc;
    }

    public List<CBEntity> getTicket() {
        return ticket;
    }

    public void setTicket(List<CBEntity> ticket) {
        this.ticket = ticket;
    }

    public List<CBEntity> getIdentity() {
        return identity;
    }

    public void setIdentity(List<CBEntity> identity) {
        this.identity = identity;
    }

    public List<CBEntity> getPayment() {
        return payment;
    }

    public void setPayment(List<CBEntity> payment) {
        this.payment = payment;
    }

    public List<CBEntity> getReservation() {
        return reservation;
    }

    public void setReservation(List<CBEntity> reservation) {
        this.reservation = reservation;
    }

    public List<CBEntity> getUnknown() {
        return unknown;
    }

    public void setUnknown(List<CBEntity> unknown) {
        this.unknown = unknown;
    }

    public List<CBEntity> getRoom() {
        return room;
    }

    public void setRoom(List<CBEntity> room) {
        this.room = room;
    }
}
