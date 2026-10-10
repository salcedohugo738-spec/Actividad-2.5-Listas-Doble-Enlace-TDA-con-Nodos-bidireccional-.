/*
Morales Escobar Juan Adrian
Salcedo Alvarez Hugo Emmanuel
 */
package back_end;

import java.time.LocalTime;

/**
 * Capa 1 - Modelo de dominio: entidad inmutable que representa una página
 * visitada en el historial de navegación.
 */
public record Pagina(String url, String titulo, LocalTime hora) {

    public Pagina {
        if (url == null || url.isBlank()) {
            throw new IllegalArgumentException("La URL no puede estar vacía.");
        }
        if (titulo == null || titulo.isBlank()) {
            throw new IllegalArgumentException("El título no puede estar vacío.");
        }
        if (hora == null) {
            throw new IllegalArgumentException("La hora no puede ser nula.");
        }
        url = url.trim();
        titulo = titulo.trim();
    }
}
