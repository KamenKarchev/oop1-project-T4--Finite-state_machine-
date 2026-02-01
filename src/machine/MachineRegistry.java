package machine;

import exceptions.facade.MachineNotFoundException;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * In-memory registry for machines for the current session.
 * Owns machine storage and id generation.
 */
public final class MachineRegistry {
    private final Map<Integer, MultiedgeFinateLogicMachine> machines = new HashMap<>();
    private int nextId = 1;

    public int register(MultiedgeFinateLogicMachine machine) {
        Objects.requireNonNull(machine, "machine");
        int id = nextId++;
        machines.put(id, machine);
        return id;
    }

    public void put(int id, MultiedgeFinateLogicMachine machine) {
        Objects.requireNonNull(machine, "machine");
        machines.put(id, machine);
        if (id >= nextId) {
            nextId = id + 1;
        }
    }

    public void clear() {
        machines.clear();
        nextId = 1;
    }

    public void resetNextIdAfterLoad() {
        int max = 0;
        for (Integer id : machines.keySet()) {
            if (id != null && id > max) {
                max = id;
            }
        }
        nextId = max + 1;
    }

    public MultiedgeFinateLogicMachine get(int id) {
        MultiedgeFinateLogicMachine m = machines.get(id);
        if (m == null) {
            throw new MachineNotFoundException("Machine not found: " + id);
        }
        return m;
    }

    public Set<Integer> ids() {
        return new HashSet<>(machines.keySet());
    }
}

