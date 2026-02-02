package exceptions.machine;

/**
 * <h2>Невалидна азбука (Σ)</h2>
 *
 * <p>
 * Хвърля се когато се опитаме да създадем или използваме автомат с невалидна азбука
 * (например непозволени символи според правилата на проекта).
 * </p>
 */
public class InvalidAlphabetException extends MachineException {
    /**
     * @param message описание на проблема
     */
    public InvalidAlphabetException(String message) {
        super(message);
    }
}

