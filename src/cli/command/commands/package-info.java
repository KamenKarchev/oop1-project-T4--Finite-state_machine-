/**
 * <h2>Реализации на команди</h2>
 *
 * <p>
 * Пакетът съдържа конкретни реализации на {@link cli.command.Command}. Всяка команда е
 * самостоятелен клас и обикновено наследява {@link cli.command.commands.AbstractArityCommand},
 * който осигурява обща проверка за правилен брой аргументи (арност).
 * </p>
 *
 * <h3>Арност (брой параметри)</h3>
 *
 * <p>
 * В CLI протокола много команди са „строго типизирани“ по брой параметри:
 * например {@code print <id>} има точно 1 параметър, а {@code union <id1> <id2>} има точно 2.
 * Общата проверка се извършва в {@link cli.command.commands.AbstractArityCommand#execute(java.util.List)}.
 * При несъответствие се хвърля {@link exceptions.cli.InvalidCommandArgumentsException}.
 * </p>
 *
 * <h3>Делегиране към операции</h3>
 *
 * <p>
 * Всяка конкретна команда съдържа стратегия {@link cli.operations.Operation} (често като ламбда),
 * която извършва реалната работа чрез {@link cli.runtime.CliContext}:
 * извиква фасадата за автомати, работи с регистъра, чете/пише XML и т.н.
 * </p>
 */
package cli.command.commands;

