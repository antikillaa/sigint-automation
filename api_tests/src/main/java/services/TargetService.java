package services;

import abs.EntityList;
import abs.SearchFilter;
import app_context.RunContext;
import app_context.entities.Entities;
import conditions.Conditions;
import conditions.Verify;
import errors.NullReturnException;
import file_generator.TargetFile;
import http.G4HttpClient;
import http.G4Response;
import http.requests.targets.TargetRequest;
import json.JsonCoverter;
import model.*;
import model.targetGroup.TargetGroupSearchResult;
import org.apache.commons.lang.NotImplementedException;
import org.apache.log4j.Logger;

import java.util.List;

public class TargetService implements EntityService<Target> {

    private static G4HttpClient g4HttpClient = new G4HttpClient();
    private Logger log = Logger.getLogger(TargetService.class);
    private RunContext context = RunContext.get();


    public int add(Target entity) {
        log.info("Creating new target");

        TargetRequest request = new TargetRequest().add(entity);
        G4Response response = g4HttpClient.sendRequest(request);

        Target target = JsonCoverter.readEntityFromResponse(response, Target.class, "id");
        if (target != null) {
            Entities.getTargets().addOrUpdateEntity(target);
        } else {
            log.error("Add new target process was failed");
            throw new AssertionError("Add new target process was failed");
        }
        return response.getStatus();
    }

    public int upload(List<Target> targets) {
        log.info("Writing Targets to file..");
        G4File file = new TargetFile().write(targets);

        log.info("Upload file with " + targets.size() + " targets..");
        TargetRequest request = new TargetRequest().upload(file);
        G4Response response = g4HttpClient.sendRequest(request);

        UploadResult uploadResult = JsonCoverter.readEntityFromResponse(response, UploadResult.class, "result");
        if (uploadResult != null) {
            context.put("uploadResult", uploadResult);
        }
        return response.getStatus();
    }

    public int remove(Target entity) {
        log.info("Deleting target id:" + entity.getId());

        TargetRequest request = new TargetRequest().delete(entity.getId());
        G4Response response = g4HttpClient.sendRequest(request);

        if (response.getStatus() == 200) {
            try {
                Entities.getTargets().removeEntity(entity);
            } catch (NullReturnException e) {
                log.warn("Was unable to remove entity with id:" + entity.getId() + " as it doesn't in the list");
            }
        }
        return response.getStatus();
    }

    public EntityList<Target> list(SearchFilter filter) {
        TargetRequest request = new TargetRequest().search(filter);
        G4Response response = g4HttpClient.sendRequest(request);

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
        TargetRequest request = new TargetRequest().update(entity);
        G4Response response = g4HttpClient.sendRequest(request);

        Result result = JsonCoverter.readEntityFromResponse(response, Result.class);
        if (result != null) {
            Verify.isTrue(Conditions.equals.elements(result.getResult(), "ok"));
            Entities.getTargets().addOrUpdateEntity(entity);
        } else {
            log.error("Error! Update target process was failed");
            throw new AssertionError("Error! Update target process was failed");
        }
        return response.getStatus();
    }

    public Target view(String id) {
        log.info("View target entry id:" + id);

        TargetRequest request = new TargetRequest().get(id);
        G4Response response = g4HttpClient.sendRequest(request);

        return JsonCoverter.readEntityFromResponse(response, Target.class, "result");
    }

    public List<TargetGroup> getTargetGroups(String id) {
        log.info("Get targetGroups of target id:" + id);

        TargetRequest request = new TargetRequest().findTargetGroups(id);
        G4Response response = g4HttpClient.sendRequest(request);

        TargetGroupSearchResult result = JsonCoverter.readEntityFromResponse(response, TargetGroupSearchResult.class);
        context.put("code", response.getStatus());
        if (result != null) {
            log.debug("Count of found groups: " + result.getResult().size());
            return result.getResult();
        } else {
            throw new AssertionError("Unable to get list of target groups");
        }
    }

}
