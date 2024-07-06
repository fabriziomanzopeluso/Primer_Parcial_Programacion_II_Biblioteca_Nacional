package bibliotecanacional;

import java.io.Serializable;


public class Libro implements Serializable
{
    private String identificador;
    private String nombre;
    private int anio;
    private String categoria;
    private String editorial;
    private Autor autor;

    public Libro(String identificador, String nombre, int anio, Autor autor, String categoria, String editorial)
    {
        setIdentificador(identificador);
        setNombre(nombre);
        setAnio(anio);
        setAutor(autor);
        setCategoria(categoria);
        setEditorial(editorial);
        mostrar();
    }

    public String getIdentificador()
    {
        return identificador;
    }

    public void setIdentificador(String identificador)
    {
        this.identificador = identificador;
    }
    
    public Autor getAutor()
    {
        return autor;
    }

    public void setAutor(Autor autor)
    {
        this.autor = autor;
    }
    
    
    public String getNombre()
    {
        return nombre;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }
    
    
    public int getAnio()
    {
        return anio;
    }

    public void setAnio(int anio)
    {
        this.anio = anio;
    }
    
    
    public String getCategoria()
    {
        return categoria;
    }

    public void setCategoria(String categoria)
    {
        this.categoria = categoria;
    }
    
    
    public String getEditorial()
    {
        return editorial;
    }

    public void setEditorial(String editorial)
    {
        this.editorial = editorial;
    }
    
    
    public boolean coincidenLibroConAlgunoExistente(String datos, Autor autor)
    {
        return datos.equals(identificador + ":" + nombre + ":" + anio + ":" + autor.nombre + ":" + autor.fechaDeNacimiento + ":" + autor.nacionalidad + ":" + categoria + ":" + editorial);
    }

    
    public boolean coincideElLibroConLaCategoria(String categoriaBuscada)
    {
        return categoria.equals(categoriaBuscada);
    }
    
    public boolean coincideElIdentificador(String identificadorBuscado)
    {
        return identificador.equals(identificadorBuscado);
    }
    
    
    public void mostrar()
    {
        EntradaSalida.mostrarMensajePorConsola("LIBRO\nIdentificador: " + identificador + "\nNombre: " + nombre + "\nAño: " + anio + "\nCategoria: " + categoria + "\nEditorial: " + editorial + "\nNombre del autor: " + autor.nombre + "\n");
        
    }

    
    
    
    
}
