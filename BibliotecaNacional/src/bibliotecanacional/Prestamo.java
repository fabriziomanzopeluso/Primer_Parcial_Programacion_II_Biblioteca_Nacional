package bibliotecanacional;

import java.io.Serializable;
import java.time.LocalDate;

public class Prestamo implements Serializable
{
    private LocalDate fechaDelPrestamo;
    private Libro libroDelPrestamo;

    public Prestamo(LocalDate fechaDelPrestamo, Libro libroDelPrestamo)
    {
        setFechaDelPrestamo(fechaDelPrestamo);
        setLibroDelPrestamo(libroDelPrestamo);
        mostrar();
    }

    public LocalDate getFechaDelPrestamo()
    {
        return fechaDelPrestamo;
    }

    public void setFechaDelPrestamo(LocalDate fechaDelPrestamo)
    {
        this.fechaDelPrestamo = fechaDelPrestamo;
    }

    public Libro getLibroDelPrestamo()
    {
        return libroDelPrestamo;
    }

    public void setLibroDelPrestamo(Libro libroDelPrestamo)
    {
        this.libroDelPrestamo = libroDelPrestamo;
    }
    
    public void mostrar()
    {
        EntradaSalida.mostrarMensajePorConsola("== PRESTAMO ==\nLibro prestado: " + libroDelPrestamo.getNombre() + "\nFecha del prestamo: " + fechaDelPrestamo + "\n");
    }

    public boolean coincideLibroConElLibroDelPrestamo(String nombre)
    {
        return this.getLibroDelPrestamo().getNombre().equals(nombre);
    }

    
}
