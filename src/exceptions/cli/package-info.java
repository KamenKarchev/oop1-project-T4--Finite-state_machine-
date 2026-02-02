/**
 * <h2>CLI изключения</h2>
 *
 * <p>
 * Изключенията в този пакет описват грешки на ниво конзолен интерфейс: непознати команди,
 * невалидни аргументи и други проблеми, които са свързани с парсване/валидация на входа.
 * </p>
 *
 * <p>
 * Базовият тип е {@link exceptions.cli.CliException}. Конкретни подтипове като
 * {@link exceptions.cli.UnknownCommandTokenException} и
 * {@link exceptions.cli.InvalidCommandArgumentsException} позволяват на приложението да
 * различава причината за грешката и да показва по-точни съобщения.
 * </p>
 */
package exceptions.cli;

