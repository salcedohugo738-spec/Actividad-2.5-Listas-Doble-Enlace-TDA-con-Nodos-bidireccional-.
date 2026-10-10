/*
Morales Escobar Juan Adrian
Salcedo Alvarez Hugo Emmanuel
 */
package Nodos;

import back_end.Pagina;

/**
 * Capa 2 - Nodo de la lista bidireccional: guarda el record de datos y dos
 * enlaces: {@code siguiente} (nodo posterior) y {@code anterior} (nodo previo).
 */
public class Nodo {

    private final Pagina dato;
    private Nodo siguiente;
    private Nodo anterior;

    public Nodo(Pagina dato) {
        if (dato == null) {
            throw new IllegalArgumentException("El dato del nodo no puede ser nulo.");
        }
        this.dato = dato;
        this.siguiente = null;
        this.anterior = null;
    }

    public Pagina getDato() {
        return dato;
    }

    public Nodo getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(Nodo siguiente) {
        this.siguiente = siguiente;
    }

    public Nodo getAnterior() {
        return anterior;
    }

    public void setAnterior(Nodo anterior) {
        this.anterior = anterior;
    }
}
