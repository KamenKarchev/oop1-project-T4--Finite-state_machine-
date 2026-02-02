/**
 * <h2>Вход/изход (I/O) и XML persistence</h2>
 *
 * <p>
 * Пакетът съдържа логика за запис и зареждане на автомати/регистър от XML файлове.
 * Основните компоненти са:
 * </p>
 *
 * <ul>
 *   <li>{@link io.MachineXmlStore} — сериализация/десериализация на XML формат за автомати.</li>
 *   <li>{@link io.RegistryFileSession} — държи „текущ файл“ за команди като {@code save}.</li>
 * </ul>
 *
 * <p>
 * Тези класове се използват от CLI слоя чрез {@link cli.runtime.CliContext}.
 * </p>
 */
package io;

