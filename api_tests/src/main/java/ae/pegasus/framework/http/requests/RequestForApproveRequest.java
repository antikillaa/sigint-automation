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
}
