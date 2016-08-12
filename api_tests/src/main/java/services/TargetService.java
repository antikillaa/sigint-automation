package services;

import abs.EntityList;
import abs.SearchFilter;
import conditions.Conditions;
import conditions.Verify;
import errors.NullReturnException;
import http.requests.targets.TargetRequest;
import json.JsonCoverter;
import json.RsClient;
import model.*;
import model.targetGroup.TargetGroupSearchResult;
import org.apache.commons.lang.NotImplementedException;
import org.apache.log4j.Logger;
import utils.Parser;
import data_generator.TargetFile;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.util.List;

public class TargetService implements EntityService<Target> {

    private static RsClient rsClient = new RsClient();
    private static AppContext context = AppContext.getContext();
    private Logger log = Logger.getLogger(TargetService.class);
    private final String sigintHost = context.environment().getSigintHost();


    public int add(Target entity) {
        log.info("Creating new target");
        log.debug(Parser.entityToString(entity));

        TargetRequest request = new TargetRequest();
        Response response = rsClient.put(sigintHost + request.getURI(), entity, request.getCookie());
        Target target = JsonCoverter.readEntityFromResponse(response, Target.class, "id");
        if (target != null) {
            context.entities().getTargets().addOrUpdateEntity(target);
        } else {
            log.error("Add new target process was failed");
            throw new AssertionError("Add new target process was failed");
        }
        return response.getStatus();
    }

    public int upload(List<Target> targets) {
        log.info("Writing Targets to file..");
        File file = new TargetFile().write(targets);

        log.info("Upload file with " + targets.size() + " targets..");
        TargetRequest request = new TargetRequest().upload();
        request.addBodyFile("file", file, MediaType.APPLICATION_JSON_TYPE);
        file.deleteOnExit();

        Entity payload = Entity.entity(request.getBody(), request.getMediaType());
        log.debug("Sending request to " + sigintHost + request.getURI());
        Response response = rsClient.client()
                .target(sigintHost + request.getURI())
                .request(MediaType.APPLICATION_JSON_TYPE)
                .cookie(request.getCookie())
                .post(payload);

        UploadResult uploadResult = JsonCoverter.readEntityFromResponse(response, UploadResult.class, "result");
        if (uploadResult != null) {
            context.put("uploadResult", uploadResult);
        }
        return response.getStatus();
    }

    public int remove(Target entity) {
        log.info("Deleting target id:" + entity.getId());

        TargetRequest request = new TargetRequest().delete(entity.getId());
        Response response = rsClient.delete(sigintHost + request.getURI(), request.getCookie());

        if (response.getStatus() == 200) {
            try {
                context.entities().getTargets().removeEntity(entity);
            } catch (NullReturnException e) {
                log.warn("Was unable to remove entity with id:" + entity.getId() + " as it doesn't in the list");
            }
        }
        return response.getStatus();
    }

    public EntityList<Target> list(SearchFilter filter) {
        log.debug(filter);
        TargetRequest request = new TargetRequest().search();
        Response response = rsClient.post(sigintHost + request.getURI(), filter, request.getCookie());
        TargetSearchResults searchResults = JsonCoverter.readEntityFromResponse(response, TargetSearchResults.class, "result");
        if (searchResults == null) {
            throw new AssertionError("Unable to read search results from Targets search");
        } else {
            return new EntityList<Target>(searchResults.getContent()) {
                public Target getEntity(String param) throws NullReturnException {
                    throw new NotImplementedException();
                }
            };
        }
    }

    public int update(Target entity) {
        log.info("Updating target id: " + entity.getId());
        log.debug(Parser.entityToString(entity));

        TargetRequest request = new TargetRequest();
        Response response = rsClient.post(sigintHost + request.getURI(), entity, request.getCookie());

        Result result = JsonCoverter.fromJsonToObject(response.readEntity(String.class), Result.class);
        if (result != null) {
            Verify.isTrue(Conditions.equals.elements(result.getResult(), "ok"));
            context.entities().getTargets().addOrUpdateEntity(entity);
        } else {
            log.error("Error! Update target process was failed");
            throw new AssertionError("Error! Update target process was failed");
        }
        return response.getStatus();
    }

    public Target view(String id) {
        log.info("View target entry id:" + id);

        TargetRequest request = new TargetRequest().get(id);
        Response response = rsClient.get(sigintHost + request.getURI(), request.getCookie());

        Target resultTarget = JsonCoverter.readEntityFromResponse(response, Target.class, "result");

        return resultTarget;
    }

    public List<TargetGroup> getTargetGroups(String id) {
        log.info("Get targetGroups of target id:" + id);

        TargetRequest request = new TargetRequest().findTargetGroups(id);
        Response response = rsClient.get(sigintHost + request.getURI(), request.getCookie());

        TargetGroupSearchResult result = JsonCoverter.fromJsonToObject(response.readEntity(String.class), TargetGroupSearchResult.class);
        context.put("code", response.getStatus());
        if (result != null) {
            log.debug("Count of found groups: " + result.getResult().size());
            return result.getResult();
        } else {
            throw new AssertionError("Unable to get list of target groups");
        }
    }

}
