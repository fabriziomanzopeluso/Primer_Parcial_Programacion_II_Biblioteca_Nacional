package bibliotecanacional;

import java.io.Serializable;
import java.time.LocalDate;



public class Autor implements Serializable
{
    public String nombre;
    public LocalDate fechaDeNacimiento;
    public String nacionalidad;

    public Autor(String nombre, LocalDate fechaDeNacimiento, String nacionalidad)
    {
        setNombre(nombre);
        setFechaDeNacimiento(fechaDeNacimiento);
        setNacionalidad(nacionalidad);
        mostrar();
    }

    public String getNombre()
    {
        return nombre;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    public LocalDate getFechaDeNacimiento()
    {
        return fechaDeNacimiento;
    }

    public void setFechaDeNacimiento(LocalDate fechaDeNacimiento)
    {
        this.fechaDeNacimiento = fechaDeNacimiento;
    }

    public String getNacionalidad()
    {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad)
    {
        this.nacionalidad = nacionalidad;
    }
    
    
    
    
    private void mostrar()
    {
        EntradaSalida.mostrarMensajePorConsola("AUTOR --- Nombre: " + this.nombre + " --- Fecha de nacimiento: " + this.fechaDeNacimiento + " --- Nacionalidad: " + this.nacionalidad);
    }
}
