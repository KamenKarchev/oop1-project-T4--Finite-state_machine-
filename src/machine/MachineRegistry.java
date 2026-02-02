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

    /**
     * Регистрира нова машина и ѝ присвоява следващото свободно ID.
     *
     * @param machine машина (не {@code null})
     * @return ново ID
     */
    public int register(MultiedgeFinateLogicMachine machine) {
        Objects.requireNonNull(machine, "machine");
        int id = nextId++;
        machines.put(id, machine);
        return id;
    }

    /**
     * Поставя машина под конкретно ID (използва се при зареждане от файл).
     *
     * <p>
     * Ако {@code id} е по-голямо/равно на текущия брояч, броячът се премества напред, за да не се
     * генерират колизии при следващи {@link #register(MultiedgeFinateLogicMachine)} операции.
     * </p>
     *
     * @param id идентификатор
     * @param machine машина (не {@code null})
     */
    public void put(int id, MultiedgeFinateLogicMachine machine) {
        Objects.requireNonNull(machine, "machine");
        machines.put(id, machine);
        if (id >= nextId) {
            nextId = id + 1;
        }
    }

    /**
     * Изчиства регистъра и връща брояча за ID-та в начално състояние.
     */
    public void clear() {
        machines.clear();
        nextId = 1;
    }

    /**
     * Преизчислява следващото ID на база максималното вече заредено ID.
     *
     * <p>
     * Полезно след {@code open}, когато зареждаме регистър от файл и искаме следващите регистрирани
     * машини да получат ID > max(existing).
     * </p>
     */
    public void resetNextIdAfterLoad() {
        int max = 0;
        for (Integer id : machines.keySet()) {
            if (id != null && id > max) {
                max = id;
            }
        }
        nextId = max + 1;
    }

    /**
     * Връща машина по ID.
     *
     * @param id идентификатор
     * @return машината
     * @throws MachineNotFoundException ако няма машина с това ID
     */
    public MultiedgeFinateLogicMachine get(int id) {
        MultiedgeFinateLogicMachine m = machines.get(id);
        if (m == null) {
            throw new MachineNotFoundException("Machine not found: " + id);
        }
        return m;
    }

    /**
     * @return множество от всички налични ID-та в регистъра
     */
    public Set<Integer> ids() {
        return new HashSet<>(machines.keySet());
    }
}

