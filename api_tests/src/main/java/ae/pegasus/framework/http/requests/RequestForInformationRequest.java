package ae.pegasus.framework.http.requests;

import ae.pegasus.framework.http.HttpMethod;
import ae.pegasus.framework.model.RequestForInformation;
import ae.pegasus.framework.model.RequestForInformationPayload;

public class RequestForInformationRequest extends HttpRequest {

    private final static String URI = "/api/reports/workflows/2/";

    public RequestForInformationRequest() {
        super(URI);
    }

    public RequestForInformationRequest generateNumber() {
        this
                .setURI(URI + "generate-number/")
                .setHttpMethod(HttpMethod.POST)
                .setPayload(null);
        return this;
    }

    public RequestForInformationRequest add(RequestForInformation requestForInformation) {
        RequestForInformationPayload requestForInformationPayload = new RequestForInformationPayload();
        requestForInformationPayload.setData(requestForInformation);
        this
                .setURI(URI + "perform-action/1/")
                .setHttpMethod(HttpMethod.POST)
                .setPayload(requestForInformationPayload);
        return this;
    }
}
