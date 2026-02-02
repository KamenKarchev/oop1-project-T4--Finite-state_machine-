/**
 * <h2>Изключения за автомати (machine layer)</h2>
 *
 * <p>
 * Тук са дефинирани изключения, свързани с валидността и операциите върху крайни автомати:
 * </p>
 *
 * <ul>
 *   <li>{@link exceptions.machine.InvalidAlphabetException} — невалидна азбука \(\\Sigma\).</li>
 *   <li>{@link exceptions.machine.InvalidSymbolException} — символ извън азбуката.</li>
 *   <li>{@link exceptions.machine.InvalidStateException} — невалидно/несъществуващо състояние.</li>
 * </ul>
 *
 * <p>
 * Базов тип: {@link exceptions.machine.MachineException}.
 * </p>
 */
package exceptions.machine;

