package abs;

import java.util.List;

public abstract class EntityListResult <T extends TeelaEntity> {

    private List<T> result;

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }

}
