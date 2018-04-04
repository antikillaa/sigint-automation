package ae.pegasus.framework.model;


import org.codehaus.jackson.annotate.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class CBEntities {

    private List<SearchRecord> from = new ArrayList<>();
    private List<SearchRecord> root = new ArrayList<>();
    private List<SearchRecord> to = new ArrayList<>();
    private List<SearchRecord> cc = new ArrayList<>();
    private List<SearchRecord> bcc = new ArrayList<>();
    @JsonProperty("Ticket")
    private List<SearchRecord> ticket = new ArrayList<>();
    @JsonProperty("Identity")
    private List<SearchRecord> identity = new ArrayList<>();
    @JsonProperty("Payment")
    private List<SearchRecord> payment = new ArrayList<>();
    @JsonProperty("Reservation")
    private List<SearchRecord> reservation = new ArrayList<>();
    private List<SearchRecord> room = new ArrayList<>();
    private List<SearchRecord> forum = new ArrayList<>();
    @JsonProperty("Email")
    private List<SearchRecord> email = new ArrayList<>();
    @JsonProperty("Visa")
    private List<SearchRecord> visa = new ArrayList<>();
    @JsonProperty("PhoneNumber")
    private List<SearchRecord> phoneNumber = new ArrayList<>();
    @JsonProperty("Membership")
    private List<SearchRecord> membership = new ArrayList<>();
    @JsonProperty("Passport")
    private List<SearchRecord> passport = new ArrayList<>();
    private List<SearchRecord> unknown = new ArrayList<>();

    public List<SearchRecord> getFrom() {
        return from;
    }

    public void setFrom(List<SearchRecord> from) {
        this.from = from;
    }

    public List<SearchRecord> getTo() {
        return to;
    }

    public void setTo(List<SearchRecord> to) {
        this.to = to;
    }

    public List<SearchRecord> getCc() {
        return cc;
    }

    public void setCc(List<SearchRecord> cc) {
        this.cc = cc;
    }

    public List<SearchRecord> getBcc() {
        return bcc;
    }

    public void setBcc(List<SearchRecord> bcc) {
        this.bcc = bcc;
    }

    public List<SearchRecord> getTicket() {
        return ticket;
    }

    public void setTicket(List<SearchRecord> ticket) {
        this.ticket = ticket;
    }

    public List<SearchRecord> getIdentity() {
        return identity;
    }

    public void setIdentity(List<SearchRecord> identity) {
        this.identity = identity;
    }

    public List<SearchRecord> getPayment() {
        return payment;
    }

    public void setPayment(List<SearchRecord> payment) {
        this.payment = payment;
    }

    public List<SearchRecord> getReservation() {
        return reservation;
    }

    public void setReservation(List<SearchRecord> reservation) {
        this.reservation = reservation;
    }

    public List<SearchRecord> getUnknown() {
        return unknown;
    }

    public void setUnknown(List<SearchRecord> unknown) {
        this.unknown = unknown;
    }

    public List<SearchRecord> getRoom() {
        return room;
    }

    public void setRoom(List<SearchRecord> room) {
        this.room = room;
    }

    public List<SearchRecord> getForum() {
        return forum;
    }

    public void setForum(List<SearchRecord> forum) {
        this.forum = forum;
    }

    public List<SearchRecord> getEmail() {
        return email;
    }

    public void setEmail(List<SearchRecord> email) {
        this.email = email;
    }

    public List<SearchRecord> getVisa() {
        return visa;
    }

    public void setVisa(List<SearchRecord> visa) {
        this.visa = visa;
    }

    public List<SearchRecord> getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(List<SearchRecord> phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<SearchRecord> getMembership() {
        return membership;
    }

    public void setMembership(List<SearchRecord> membership) {
        this.membership = membership;
    }

    public List<SearchRecord> getPassport() {
        return passport;
    }

    public void setPassport(List<SearchRecord> passport) {
        this.passport = passport;
    }

    public List<SearchRecord> getRoot() {
        return root;
    }

    public void setRoot(List<SearchRecord> root) {
        this.root = root;
    }
}
