package ae.pegasus.framework.http.requests;

import ae.pegasus.framework.http.HttpMethod;
import ae.pegasus.framework.model.RequestForApprove;
import ae.pegasus.framework.model.RequestForApprovePayload;

public class RequestForApproveRequest extends HttpRequest {
    private final static String URI = "/api/reports/workflows/";

    public RequestForApproveRequest() {
        super(URI);
    }

    public RequestForApproveRequest generateNumber() {
        this
                .setURI(URI + "4/generate-number/")
                .setHttpMethod(HttpMethod.POST)
                .setPayload(null);
        return this;
    }

    public RequestForApproveRequest add(RequestForApprove requestForApprove) {
        RequestForApprovePayload requestForApprovePayload = new RequestForApprovePayload();
        requestForApprovePayload.setData(requestForApprove);
        this
                .setURI(URI + "4/perform-action/1/")
                .setHttpMethod(HttpMethod.POST)
                .setPayload(requestForApprovePayload);
        return this;
    }

    public RequestForApproveRequest view(String id) {
        this
                .setURI(URI + "4/model/" + id + "?userAction=true")
                .setHttpMethod(HttpMethod.GET);
        return this;
    }

    public RequestForApproveRequest remove(RequestForApprove requestForApprove) {
        RequestForApprovePayload requestForApprovePayload = new RequestForApprovePayload();
        requestForApprovePayload.setData(requestForApprove);
        this
                .setURI(URI + "4/perform-action/3/")
                .setHttpMethod(HttpMethod.POST)
                .setPayload(requestForApprovePayload);
        return this;
    }

    public RequestForApproveRequest possibleOwnersTeams(RequestForApprove requestForApprove) {
        this
                .setURI(URI + "4/possible-owners/2/")
                .setHttpMethod(HttpMethod.POST)
                .setPayload(requestForApprove);
        return this;
    }

    public RequestForApproveRequest sendForApprove(RequestForApprove requestForApprove) {
        RequestForApprovePayload requestForApprovePayload = new RequestForApprovePayload();
        requestForApprovePayload.setData(requestForApprove);
        this
                .setURI(URI + "4/perform-action/2/")
                .setHttpMethod(HttpMethod.POST)
                .setPayload(requestForApprovePayload);
        return this;
    }

    public RequestForApproveRequest cancel(RequestForApprove requestForApprove) {
        RequestForApprovePayload requestForApprovePayload = new RequestForApprovePayload();
        requestForApprovePayload.setData(requestForApprove);
        this
                .setURI(URI + "4/perform-action/4/")
                .setHttpMethod(HttpMethod.POST)
                .setPayload(requestForApprovePayload);
        return this;
    }

    public RequestForApproveRequest takeOwnership(RequestForApprove requestForApprove) {
        RequestForApprovePayload requestForApprovePayload = new RequestForApprovePayload();
        requestForApprovePayload.setData(requestForApprove);
        this
                .setURI(URI + "4/perform-action/5/")
                .setHttpMethod(HttpMethod.POST)
                .setPayload(requestForApprovePayload);
        return this;
    }

    public RequestForApproveRequest removeOwnership(RequestForApprove requestForApprove) {
        RequestForApprovePayload requestForApprovePayload = new RequestForApprovePayload();
        requestForApprovePayload.setData(requestForApprove);
        this
                .setURI(URI + "4/perform-action/9/")
                .setHttpMethod(HttpMethod.POST)
                .setPayload(requestForApprovePayload);
        return this;
    }

    public RequestForApproveRequest reject(RequestForApprove requestForApprove) {
        RequestForApprovePayload requestForApprovePayload = new RequestForApprovePayload();
        requestForApprovePayload.setData(requestForApprove);
        this
                .setURI(URI + "4/perform-action/7/")
                .setHttpMethod(HttpMethod.POST)
                .setPayload(requestForApprovePayload);
        return this;
    }

    public RequestForApproveRequest approve(RequestForApprove requestForApprove) {
        RequestForApprovePayload requestForApprovePayload = new RequestForApprovePayload();
        requestForApprovePayload.setData(requestForApprove);
        this
                .setURI(URI + "4/perform-action/6/")
                .setHttpMethod(HttpMethod.POST)
                .setPayload(requestForApprovePayload);
        return this;
    }

    public RequestForApproveRequest getAudioContent(String id) {
        this
                .setURI("/api/upload-sigint/files/" + id + "/content")
                .setHttpMethod(HttpMethod.GET);
        return this;
    }
}
