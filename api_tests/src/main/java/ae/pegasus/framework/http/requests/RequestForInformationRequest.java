package ae.pegasus.framework.http.requests;

import ae.pegasus.framework.http.HttpMethod;
import ae.pegasus.framework.model.RequestForInformation;
import ae.pegasus.framework.model.RequestForInformationPayload;

public class RequestForInformationRequest extends HttpRequest {

    private final static String URI = "/api/reports/workflows/";

    public RequestForInformationRequest() {
        super(URI);
    }

    public RequestForInformationRequest generateNumber() {
        this
                .setURI(URI + "2/generate-number/")
                .setHttpMethod(HttpMethod.POST)
                .setPayload(null);
        return this;
    }

    public RequestForInformationRequest add(RequestForInformation requestForInformation) {
        RequestForInformationPayload requestForInformationPayload = new RequestForInformationPayload();
        requestForInformationPayload.setData(requestForInformation);
        this
                .setURI(URI + "2/perform-action/1/")
                .setHttpMethod(HttpMethod.POST)
                .setPayload(requestForInformationPayload);
        return this;
    }

    public RequestForInformationRequest view(String id) {
        this
                .setURI(URI + "2/model/" + id + "?userAction=true")
                .setHttpMethod(HttpMethod.GET);
        return this;
    }

    public RequestForInformationRequest submit(RequestForInformation requestForInformation) {
        RequestForInformationPayload requestForInformationPayload = new RequestForInformationPayload();
        requestForInformationPayload.setData(requestForInformation);
        this
                .setURI(URI + "2/perform-action/2/")
                .setHttpMethod(HttpMethod.POST)
                .setPayload(requestForInformationPayload);
        return this;
    }

    public RequestForInformationRequest remove(RequestForInformation requestForInformation) {
        RequestForInformationPayload requestForInformationPayload = new RequestForInformationPayload();
        requestForInformationPayload.setData(requestForInformation);
        this
                .setURI(URI + "1/perform-action/3")
                .setHttpMethod(HttpMethod.POST)
                .setPayload(requestForInformationPayload);
        return this;
    }

    public RequestForInformationRequest cancel(RequestForInformation requestForInformation) {
        RequestForInformationPayload requestForInformationPayload = new RequestForInformationPayload();
        requestForInformationPayload.setData(requestForInformation);
        this
                .setURI(URI + "2/perform-action/8")
                .setHttpMethod(HttpMethod.POST)
                .setPayload(requestForInformationPayload);
        return this;
    }

    public RequestForInformationRequest approve(RequestForInformation requestForInformation) {
        RequestForInformationPayload requestForInformationPayload = new RequestForInformationPayload();
        requestForInformationPayload.setData(requestForInformation);
        this
                .setURI(URI + "2/perform-action/5/")
                .setHttpMethod(HttpMethod.POST)
                .setPayload(requestForInformationPayload);
        return this;
    }

    public RequestForInformationRequest possibleOwners(RequestForInformation requestForInformation) {
        this
                .setURI(URI + "2/possible-owners/2")
                .setHttpMethod(HttpMethod.POST)
                .setPayload(requestForInformation);
        return this;
    }
}
