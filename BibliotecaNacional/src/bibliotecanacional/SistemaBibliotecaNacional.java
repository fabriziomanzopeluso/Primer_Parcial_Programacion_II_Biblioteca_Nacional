package bibliotecanacional;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


public class SistemaBibliotecaNacional implements Serializable
{
        
    private ArrayList<Libro> libros;
    private ArrayList<Libro> librosEnReparacion;
    private ArrayList<Persona> personas;
    private ArrayList<String> solicitudesDePrestamosDeLibros;
    private String[] categoriasDeLibros;
    public static LocalDate fechaDeHoy;

    public SistemaBibliotecaNacional()
    {
        
        categoriasDeLibros = new String[]{"Novela", "Teatro", "Poesia", "Ensayo"};
        libros = new ArrayList<>();
        librosEnReparacion = new ArrayList<>();
        personas = new ArrayList<>();
        solicitudesDePrestamosDeLibros = new ArrayList<>();
        //fechaDeHoy = getFechaActual(); //esto es si queremos que se ejecute con la fecha del dia de hoy. En ese caso, comentar la linea de abajo.
        fechaDeHoy = EntradaSalida.pedirYValidarFecha("Ingrese la fecha manualmente:"); //ésta se comenta si se descomenta la de arriba.
        mensajeDeBienvenida();
    }
    
    private void mensajeDeBienvenida()
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String fechaFormateada = fechaDeHoy.format(formatter);
        EntradaSalida.mostrarMensajePorConsola("Bienvenido a la Biblioteca Nacional\nHoy es: " + fechaFormateada);
    }
    
    
    public static LocalDate getFechaActual()
    {
        return LocalDate.now();
    }
    
    
    public SistemaBibliotecaNacional deSerializar(String a) throws IOException, ClassNotFoundException
    {
        FileInputStream f = new FileInputStream(a);
        ObjectInputStream o = new ObjectInputStream(f);
        SistemaBibliotecaNacional s = (SistemaBibliotecaNacional) o.readObject();
        o.close();
        f.close();
        return s;
    }

    public void serializar(String a) throws IOException
    {
        FileOutputStream f = new FileOutputStream(a);
        ObjectOutputStream o = new ObjectOutputStream(f);
        o.writeObject(this);
        o.close();
        f.close();
    }
    
    
    
    
    public Persona iniciarSesionDeUsuario()
    {
        String usuario = EntradaSalida.leerYValidar("Ingrese el usuario:");
        String password = EntradaSalida.leerYValidar("Ingrese la password:");

        Persona p = this.buscarPersonaPorTodosSusDatos(usuario + ":" + password);

        return p;
    }
    
    
    
    public ArrayList<Persona> getPersonas()
    {
        return personas;
    }

    public void setPersonas(ArrayList<Persona> personas)
    {
        this.personas = personas;
    }
    
    void agregarPersonas(Persona p)
    {
        this.personas.add(p);
    }
    
    
    
    public ArrayList<Libro> getLibros()
    {
        return libros;
    }

    public void setLibros(ArrayList<Libro> libros)
    {
        this.libros = libros;
    }
    
    
    public ArrayList<Libro> getLibrosEnReparacion()
    {
        return librosEnReparacion;
    }

    public void setLibrosEnReparacion(ArrayList<Libro> librosEnReparacion)
    {
        this.librosEnReparacion = librosEnReparacion;
    }

    public ArrayList<String> getSolicitudesDePrestamosDeLibros()
    {
        return solicitudesDePrestamosDeLibros;
    }

    public void setSolicitudesDePrestamosDeLibros(ArrayList<String> solicitudesDePrestamosDeLibros)
    {
        this.solicitudesDePrestamosDeLibros = solicitudesDePrestamosDeLibros;
    }

    public String[] getCategoriasDeLibros()
    {
        return categoriasDeLibros;
    }

    public void setCategoriasDeLibros(String[] categoriasDeLibros)
    {
        this.categoriasDeLibros = categoriasDeLibros;
    }
    
   
    //MÉTODOS PARA AGREGAR O ELIMINAR:
    
    public void quitarUsuarioDelSistema(Persona persona)
    {
        personas.remove(persona); 
    }
    
    
    public void agregarSolicitudDePrestamoDeLibro(String mensajeDeSolicitud)
    {
        this.solicitudesDePrestamosDeLibros.add(mensajeDeSolicitud);
    }

    public void agregarLibro(Libro li)
    {
        libros.add(li);
    }
    
    
    
    
    
    //MÉTODOS PARA BUSCAR:
    
    public Persona buscarPersonaPorTodosSusDatos(String datos)
    {
        for(Persona p : personas)
        {
          if(p.coincidenUsuarioYContrasenia(datos))
          {
               return p;
          }
        }
          return null;
    }
    
    
    public Persona buscarPersonaPorNombre(String usuario)
    { 
        for(Persona p : personas)
        {
          if(p.coincideUsuario(usuario))
          {
               return p;
          }
        }
          return null;
    }
    
    
    
    
     public Libro buscarLibroPorTodosSusDatos(String datos, Autor autor)
     {
        for(Libro li: libros)
        {
          if(li.coincidenLibroConAlgunoExistente(datos, autor))
          {
               return li;
          }
        }
          return null;
     }
    
     
     public Libro buscarLibroPorIdentificadorEnBiblioteca(String identificador)
     {
         for(Libro li: libros)
        {
          if(li.coincideElIdentificador(identificador))           //si el libro esta en la biblioteca
          {
               return li;
          }
        }
        return null;
     }
     
     public Libro buscarLibroPorIdentificador(String identificador)
     {
        for(Libro li: libros)
        {
          if(li.coincideElIdentificador(identificador))           //si el libro esta en la biblioteca
          {
               return li;
          }
        }
        
        for(Libro liRe: librosEnReparacion)
        {
            
            if(liRe.coincideElIdentificador(identificador))             //si el libro esta en reparación, tampoco se puede repetir el identificador
            {
                 return liRe;
            }
        }
        
        ArrayList<Persona> vectorDeUsuarios = getPersonas();
        for(int i=0; i < vectorDeUsuarios.size(); i++)
        {
            if(vectorDeUsuarios.get(i).getClass().equals(Lector.class))
            {
                Lector le = (Lector)vectorDeUsuarios.get(i); //casteo para trabajar con un metodo del lector
                                                                                                                                //si el libro lo tiene ALGÚN lector
                ArrayList<Libro> vectorDeLibrosEnPrestamo = le.getLibrosQueDispones();
                
                for(int j=0; j < vectorDeLibrosEnPrestamo.size() ; j++)
                {
                    if(vectorDeLibrosEnPrestamo.get(j).coincideElIdentificador(identificador))
                    {
                        return vectorDeLibrosEnPrestamo.get(j);
                    }
                }
            }
        }
        
        return null;

     }
     
     
     
     
     
     //MÉTODOS PARA MOSTRAR:
     
     public void mostrarUsuarios()   //no hace falta preguntar si el vector vacío porque siempre hay mínimo un administrador inscrito en el sistema
     {  
         for (Persona p : personas)       
         {
            p.mostrar();
         }
     }
     
     
     public void mostrarUsuarios(String tipoUsuario)
     {
        for (Persona persona : personas)
        {
            if (persona.esTipoDeUsuario(tipoUsuario))
            {
                persona.mostrar();
            }
        }
     }

     
     
     public void mostrarLibros()
     {
         if(libros.isEmpty()) //si está vacío el array de libros que estan DISPONIBLES ACTUALMENTE en la biblioteca, no cuentan los que están en préstamo o en reparación
         {
             EntradaSalida.mostrarMensajeDeError("No hay libros en la biblioteca.\n");
         }
         
         for(Libro li : libros)
         {
             li.mostrar();
         }
     }
     
     public void mostrarLibrosEnReparación()
     {
         
         if(librosEnReparacion.isEmpty())
         {
             EntradaSalida.mostrarMensajeDeError("No hay libros en reparación.\n");
         }
         for(Libro liRe : librosEnReparacion)
         {
             liRe.mostrar();
         }
     }
    
     
     public void mostrarLibrosSegunCategoria(String categoriaBuscada)
     {
         if(libros.isEmpty())
         {
             EntradaSalida.mostrarMensajeDeError("No hay libros en la biblioteca.\n");
         }
         
         for(Libro li : libros)
         {
             if(li.coincideElLibroConLaCategoria(categoriaBuscada))
             {
                 li.mostrar();
             }
         }
     }

    public void mostrarLibrosEnPrestamoALectores()
    {                                                                                       
        for(Persona p : personas)
        {
            if(p.esTipoDeUsuario("Lector"))
            {
                Lector le = (Lector)p;
                for(Libro li : le.getLibrosQueDispones())
                {
                    EntradaSalida.mostrarMensajePorConsola("Lector: " + le.getUsuario());
                    li.mostrar();
                }
            }
        }
        
    }
    
    
    
    
    
    //VALIDACIONES:

    public String validarCategoriaDeLibro(String msj)
    {
    String categoria;
    boolean esValida = false;
    
    while (!esValida)
    {
        categoria = EntradaSalida.leerYValidar(msj);
        for (String c : categoriasDeLibros)
        {
            if (c.equals(categoria))
            {
                return categoria;                                                       
            }
        }
        EntradaSalida.mostrarMensajePorConsola("Categoría invalida. Intente nuevamente.");
    }
    return null;
    } 

    
    public void sacarLibroDeBiblioteca(Libro libro)
    {
        this.getLibros().remove(libro);
    }
    
}
