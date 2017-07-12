package steps;

import error_reporter.ErrorReporter;
import model.FileMeta;
import model.FileMetaFilter;
import model.FolderMeta;
import model.Source;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import services.UploadFilesService;
import services.UploadFoldersService;

import java.util.List;

import static http.requests.UploadFoldersRequest.buildSearchFilter;
import static http.requests.UploadFoldersRequest.searchByPath;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static utils.StringUtils.stripQuotes;

public class APISearchFilesAndFoldersSteps extends APISteps {

  private UploadFoldersService foldersService = new UploadFoldersService();
  private UploadFilesService filesService = new UploadFilesService();

  private boolean isFileType(String type) {
    return type.toLowerCase().contains("files");
  }

  private void executeSearch(String type, FileMetaFilter filter) {
    context.put("uploadSearchFilter", filter);

    if (isFileType(type)) {
      List<FileMeta> fileMetaList = filesService.searchDataFiles(filter);
      context.put("filesList", fileMetaList);
    } else {
      List<FolderMeta> folderMetaList = foldersService.searchFolders(filter);
      context.put("foldersList", folderMetaList);
    }
  }

  @When("I search data $type by source with date pattern $pattern")
  public void searchDataFoldersBySource(String type, String pattern) {
    Source source = context.get("source", Source.class);

    FileMetaFilter filter = buildSearchFilter(source, stripQuotes(pattern));
    executeSearch(type, filter);
  }

  @When("I search data $type with date pattern $pattern")
  public void searchData(String type, String pattern) {
    FileMetaFilter filter = buildSearchFilter(stripQuotes(pattern));
    executeSearch(type, filter);
  }

  @When("I search data files by path")
  public void searchFilesByPath() {
    List<FolderMeta> foldersList = context.get("foldersList", List.class);

    String filePath = foldersList.get(0).getName();
    FileMetaFilter filter = searchByPath(filePath);
    List<FileMeta> fileMetaList = filesService.searchDataFiles(filter);

    context.put("uploadSearchFilter", filter);
    context.put("filesList", fileMetaList);
  }

  @Then("Found $type list size is more than $size")
  public void filesListSizeIsMoreThan(String type, String size) {
    int actualSize;
    int minSize = Integer.valueOf(size);

    if (isFileType(type)) {
      actualSize = context.get("filesList", List.class).size();
    } else {
      actualSize = context.get("foldersList", List.class).size();
    }
    assertTrue(type + " list size is too low: " + actualSize, actualSize > minSize);
  }

  @When("I send count found $type request")
  public void sendCountFiles(String type) {
    int count;
    FileMetaFilter filter = context.get("uploadSearchFilter", FileMetaFilter.class);

    if (isFileType(type)) {
      count = filesService.countFiles(filter).getEntity();
      context.put("filesCount", count);
    } else {
      count = foldersService.countFolders(filter).getEntity();
      context.put("foldersCount", count);
    }
  }

  @Then("$type count result is equal to appropriate list size")
  public void compareCountAndSearchResults(String type) {
    int expectedCount, actualCount;

    if (isFileType(type)) {
      expectedCount = context.get("filesCount", Integer.class);
      actualCount = context.get("filesList", List.class).size();
    } else {
      expectedCount = context.get("foldersCount", Integer.class);
      actualCount = context.get("foldersList", List.class).size();
    }
    assertEquals(type + " count returns incorrect result", expectedCount, actualCount);
  }

  @Then("Original data file is searchable within the system")
  public void originalFilesAreSearchable() {
    FileMeta uploadedFileMeta = context.get("meta", FileMeta.class);

    FileMetaFilter filter = searchByPath(uploadedFileMeta.getPath());
    List<FileMeta> fileMetaList = filesService.searchDataFiles(filter);

    for (FileMeta fileMeta : fileMetaList) {
      if (uploadedFileMeta.getEtag().equals(fileMeta.getEtag())) {
        assertEquals(uploadedFileMeta.getPath(), fileMeta.getPath());
        assertEquals(uploadedFileMeta.getOwner(), fileMeta.getOwner());
        assertEquals(uploadedFileMeta.getName(), fileMeta.getName());
        assertEquals(uploadedFileMeta.getFile(), fileMeta.getFile());
        assertEquals(uploadedFileMeta.getExtension(), fileMeta.getExtension());
        assertEquals(uploadedFileMeta.getType(), fileMeta.getType());
        assertEquals(uploadedFileMeta.getLength(), fileMeta.getLength());
        assertEquals(uploadedFileMeta.getBucket(), fileMeta.getBucket());
        assertEquals(uploadedFileMeta.getSourceId(), fileMeta.getSourceId());
        return;
      }
    }
    ErrorReporter.reportAndRaiseError("Can't find uploaded " + uploadedFileMeta.getPath());
  }

}
