package graph.base;

import exceptions.graph.GraphException;

import java.util.Collection;
import java.util.List;

/**
 * <h2>Граф като колекция от възли</h2>
 *
 * <p>
 * Интерфейс за граф, който поддържа операции за добавяне/премахване на възли и ребра, както и
 * проверки за наличие и броене.
 * </p>
 *
 * <p>
 * В проекта графът се използва като основна структура за представяне на множеството състояния \(S\)
 * и преходите \(T\) на автомата.
 * </p>
 *
 * @param <N> тип на възлите
 * @param <E> тип на ребрата
 */
public interface Graph<N extends Node<E>, E extends Edge<N>> {
    /**
     * @return списък с всички възли в графа (канонични инстанции)
     */
    List<N> getNodes();

    /**
     * Добавя възел в графа.
     *
     * @param node възел
     * @throws GraphException ако възелът е невалиден или вече съществува
     */
    void putNode(N node) throws GraphException;

    /**
     * Премахва възел от графа.
     *
     * @param node възел
     * @throws GraphException ако възелът липсва или операцията е невалидна
     */
    void removeNode(N node) throws GraphException;

    /**
     * @param node възел
     * @return {@code true} ако възелът съществува в графа
     * @throws GraphException при невалиден възел
     */
    boolean hasNode(N node) throws GraphException;

    /**
     * Добавя ребро като изходящо ребро на даден възел.
     *
     * @param from изходен възел
     * @param edge ребро (обикновено с {@code from == from})
     * @throws GraphException при липсващ възел или невалидно/дублирано ребро
     */
    void addEdge(N from, E edge) throws GraphException;

    /**
     * Премахва ребро от даден възел.
     *
     * @param from изходен възел
     * @param edge ребро за премахване
     * @throws GraphException ако възелът/реброто липсва
     */
    void removeEdge(N from, E edge) throws GraphException;

    /**
     * @param from изходен възел
     * @param edge ребро
     * @return {@code true} ако реброто съществува като изходящо ребро на {@code from}
     * @throws GraphException при липсващ възел
     */
    boolean hasEdge(N from, E edge) throws GraphException;

    /**
     * Операция „cut“: премахва {@code node1} и го заменя с {@code node2}, като пренасочва входящите
     * ребра и прехвърля изходящите.
     *
     * @param node1 възел за премахване
     * @param node2 възел за заместване (може да е „alien“ и да се добави)
     * @throws GraphException при невалидни/липсващи възли
     */
    void cut(N node1, N node2) throws GraphException;

    /**
     * @return брой възли в графа
     */
    int getNodeCount();

    /**
     * @return общ брой ребра в графа
     */
    int getEdgesCount();

    /**
     * @param node възел
     * @return брой изходящи ребра от даден възел
     * @throws GraphException при липсващ възел
     */
    int getEdgesCount(N node) throws GraphException;
}
