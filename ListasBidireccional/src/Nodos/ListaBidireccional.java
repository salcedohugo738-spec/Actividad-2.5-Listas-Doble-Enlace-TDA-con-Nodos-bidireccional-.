/*
Morales Escobar Juan Adrian
Salcedo Alvarez Hugo Emmanuel
 */
package Nodos;

import back_end.Pagina;

/**
 * Capa 2 - TDA Lista Bidireccional (doblemente enlazada) basada en nodos.
 * Mantiene las referencias {@code primero} y {@code ultimo}; cada nodo enlaza
 * con {@code siguiente} y {@code anterior}.
 * Los índices son base 0: la posición 0 corresponde a {@code primero}.
 */
public class ListaBidireccional {

    /** Resultado de una búsqueda: record encontrado y su posición. */
    public record Resultado(Pagina pagina, int posicion) { }

    private Nodo primero;
    private Nodo ultimo;
    private int tamano;

    public ListaBidireccional() {
        this.primero = null;
        this.ultimo = null;
        this.tamano = 0;
    }

    // ---------------------------------------------------------- Inserción

    public void insertarAlPrincipio(Pagina pagina) {
        Nodo nuevo = new Nodo(pagina);
        if (primero == null) {
            primero = nuevo;
            ultimo = nuevo;
        } else {
            nuevo.setSiguiente(primero);
            primero.setAnterior(nuevo);
            primero = nuevo;
        }
        tamano++;
    }

    public void insertarAlFinal(Pagina pagina) {
        Nodo nuevo = new Nodo(pagina);
        if (ultimo == null) {
            primero = nuevo;
            ultimo = nuevo;
        } else {
            nuevo.setAnterior(ultimo);
            ultimo.setSiguiente(nuevo);
            ultimo = nuevo;
        }
        tamano++;
    }

    /** Inserta de modo que el nuevo elemento quede en la posición indicada (0..tamano). */
    public void insertarEnPosicion(Pagina pagina, int posicion) {
        if (posicion < 0 || posicion > tamano) {
            throw new IndexOutOfBoundsException(
                    "Posición inválida: " + posicion + ". Rango permitido: 0 a " + tamano + ".");
        }
        if (posicion == 0) {
            insertarAlPrincipio(pagina);
            return;
        }
        if (posicion == tamano) {
            insertarAlFinal(pagina);
            return;
        }
        Nodo nuevo = new Nodo(pagina);
        Nodo sucesor = nodoEn(posicion);          // quedará después del nuevo
        Nodo predecesor = sucesor.getAnterior();  // quedará antes del nuevo
        nuevo.setAnterior(predecesor);
        nuevo.setSiguiente(sucesor);
        predecesor.setSiguiente(nuevo);
        sucesor.setAnterior(nuevo);
        tamano++;
    }

    // ----------------------------------------------------------- Remoción

    public Pagina removerAlPrincipio() {
        validarNoVacia();
        Nodo removido = primero;
        primero = removido.getSiguiente();
        if (primero == null) {
            ultimo = null;
        } else {
            primero.setAnterior(null);
        }
        removido.setSiguiente(null);
        tamano--;
        return removido.getDato();
    }

    public Pagina removerAlFinal() {
        validarNoVacia();
        Nodo removido = ultimo;
        ultimo = removido.getAnterior();
        if (ultimo == null) {
            primero = null;
        } else {
            ultimo.setSiguiente(null);
        }
        removido.setAnterior(null);
        tamano--;
        return removido.getDato();
    }

    public Pagina removerEnPosicion(int posicion) {
        validarNoVacia();
        validarPosicion(posicion);
        if (posicion == 0) {
            return removerAlPrincipio();
        }
        if (posicion == tamano - 1) {
            return removerAlFinal();
        }
        Nodo removido = nodoEn(posicion);
        Nodo anterior = removido.getAnterior();
        Nodo siguiente = removido.getSiguiente();
        anterior.setSiguiente(siguiente);
        siguiente.setAnterior(anterior);
        removido.setAnterior(null);
        removido.setSiguiente(null);
        tamano--;
        return removido.getDato();
    }

    // ------------------------------------------------------ Visualización

    public Pagina mostrarAlPrincipio() {
        validarNoVacia();
        return primero.getDato();
    }

    public Pagina mostrarAlFinal() {
        validarNoVacia();
        return ultimo.getDato();
    }

    public Pagina mostrarEnPosicion(int posicion) {
        validarNoVacia();
        validarPosicion(posicion);
        return nodoEn(posicion).getDato();
    }

    // ----------------------------------------------------------- Búsqueda

    /** Busca por valor clave (la URL, sin distinguir mayúsculas). Retorna null si no existe. */
    public Resultado buscarPorValor(String url) {
        if (url == null) {
            return null;
        }
        String clave = url.trim();
        Nodo actual = primero;
        int indice = 0;
        while (actual != null) {
            if (actual.getDato().url().equalsIgnoreCase(clave)) {
                return new Resultado(actual.getDato(), indice);
            }
            actual = actual.getSiguiente();
            indice++;
        }
        return null;
    }

    /** Busca por posición recorriendo desde primero o desde ultimo, según la cercanía. */
    public Resultado buscarPorPosicion(int posicion) {
        validarNoVacia();
        validarPosicion(posicion);
        return new Resultado(nodoEn(posicion).getDato(), posicion);
    }

    // ------------------------------------------------------ Recorridos

    /** Secuencia completa desde primero hasta ultimo (una línea por elemento). */
    public String recorrerAdelante() {
        StringBuilder sb = new StringBuilder();
        Nodo actual = primero;
        int i = 0;
        while (actual != null) {
            sb.append(i).append(": ").append(actual.getDato().url()).append('\n');
            actual = actual.getSiguiente();
            i++;
        }
        return sb.toString();
    }

    /** Secuencia completa en sentido inverso, desde ultimo hasta primero. */
    public String recorrerAtras() {
        StringBuilder sb = new StringBuilder();
        Nodo actual = ultimo;
        int i = tamano - 1;
        while (actual != null) {
            sb.append(i).append(": ").append(actual.getDato().url()).append('\n');
            actual = actual.getAnterior();
            i--;
        }
        return sb.toString();
    }

    // ---------------------------------------------------------- Auxiliares

    public void vaciar() {
        primero = null;
        ultimo = null;
        tamano = 0;
    }

    public boolean estaVacia() {
        return primero == null;
    }

    public int obtenerTamano() {
        return tamano;
    }

    public Nodo getPrimero() {
        return primero;
    }

    public Nodo getUltimo() {
        return ultimo;
    }

    // ------------------------------------------------------------ Privados

    private void validarNoVacia() {
        if (estaVacia()) {
            throw new IllegalStateException("La lista está vacía.");
        }
    }

    private void validarPosicion(int posicion) {
        if (posicion < 0 || posicion >= tamano) {
            throw new IndexOutOfBoundsException(
                    "Posición inválida: " + posicion + ". Rango permitido: 0 a " + (tamano - 1) + ".");
        }
    }

    /** Ubica el nodo recorriendo desde el extremo más cercano al índice. */
    private Nodo nodoEn(int posicion) {
        Nodo actual;
        if (posicion <= tamano / 2) {
            actual = primero;
            for (int i = 0; i < posicion; i++) {
                actual = actual.getSiguiente();
            }
        } else {
            actual = ultimo;
            for (int i = tamano - 1; i > posicion; i--) {
                actual = actual.getAnterior();
            }
        }
        return actual;
    }
}
