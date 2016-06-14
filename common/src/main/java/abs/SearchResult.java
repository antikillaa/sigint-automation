package abs;

import java.util.List;

public abstract class SearchResult <T extends TeelaEntity> {

    private List<T> content;

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

}
