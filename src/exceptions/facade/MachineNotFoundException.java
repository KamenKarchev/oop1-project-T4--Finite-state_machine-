package exceptions.facade;

/**
 * <h2>Липсваща машина по ID</h2>
 *
 * <p>
 * Хвърля се когато се поиска автомат по идентификатор, който не съществува в
 * {@link machine.MachineRegistry}.
 * </p>
 *
 * <p>
 * Това е {@link RuntimeException}, защото обикновено означава логическа грешка или невалиден
 * потребителски вход, който не може да бъде „възстановен“ без нова команда/параметри.
 * </p>
 */
public class MachineNotFoundException extends RuntimeException {
    /**
     * @param message описание на проблема
     */
    public MachineNotFoundException(String message) {
        super(message);
    }
}

