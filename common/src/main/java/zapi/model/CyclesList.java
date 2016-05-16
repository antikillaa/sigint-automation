package zapi.model;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import zapi.deserializer.CyclesDeserialize;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dm on 4/6/16.
 */

@JsonDeserialize(using= CyclesDeserialize.class)
public class CyclesList {

    private List<Cycle> cycles= new ArrayList();

    public List<Cycle> getCycles() {
        return cycles;
    }

    public void addtoCycle(Cycle cycle) {
        cycles.add(cycle);
    }

    public Cycle getCycle(String name) {
        for (Cycle cycle:cycles) {
            if (cycle.getName().toLowerCase().equals(name.toLowerCase())) {
                return cycle;
            }
        }
        return null;
    }
}
