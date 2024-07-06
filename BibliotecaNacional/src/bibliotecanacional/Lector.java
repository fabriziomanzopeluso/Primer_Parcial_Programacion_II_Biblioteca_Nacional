package bibliotecanacional;

import java.io.IOException;
import java.io.Serializable;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Iterator;



public class Lector extends Persona implements Serializable
{   

    private ArrayList<Libro> librosQueDispones;
    private ArrayList<Prestamo> prestamos;
    private int diasSinPoderSolicitarUnLibro;
    
    public Lector()
    {
        librosQueDispones = new ArrayList<>();
        prestamos = new ArrayList<>();
        setLibrosQueDispones(librosQueDispones);
        setPrestamos(prestamos);
    }

    public Lector(String nombre, String contraseña)
    {
        librosQueDispones = new ArrayList<>();
        prestamos = new ArrayList<>();
        setUsuario(nombre);
        setContrasenia(contraseña);
        setLibrosQueDispones(librosQueDispones);
        setPrestamos(prestamos);
        mostrar();
    }
    
    
    @Override
    public void mostrar()
    {
        EntradaSalida.mostrarMensajePorConsola("LECTOR\nNombre: " + this.getUsuario() + "\nCONTRASEÑA: " + this.getContrasenia() + "\n\n");  
    }

    
    @Override
    public boolean menu(SistemaBibliotecaNacional sistema)
    {
        boolean sigueElPrograma = true;
        char opcionElegida;
        
        do
        {
        opcionElegida = EntradaSalida.leerCaracter("""
                                                        MENU DE LECTOR
                                                        [1] Ver todos los libros de la biblioteca
                                                        [2] Ver los libros segun categoria
                                                        [3] Ver los libros que dispones
                                                        [4] Devolver un libro a la biblioteca
                                                        [5] Solicitar un libro a la biblioteca
                                                        [6] Salir del menu
                                                        """);
            switch(opcionElegida)
            {

                case '1' ->
                {
                    sistema.mostrarLibros();
                    break;
                }

                case '2' ->
                {
                    String categoriaBuscada = sistema.validarCategoriaDeLibro("Ingrese la categoria que desea buscar");
                    sistema.mostrarLibrosSegunCategoria(categoriaBuscada);
                    break;
                }
                
                case '3' ->
                {
                    mostrarLibrosQueDispones();
                    break;
                }
                
                case '4' ->
                {
                    String identificadorLibro = EntradaSalida.leerYValidar("Ingrese el identificador del libro que desea devolver a la biblioteca:");
                    devolverLibroABiblioteca(sistema, identificadorLibro);
                }

                case '5' ->
                {
                    String mensajeDeSolicitudDeLibro = EntradaSalida.leerYValidar("Mensaje de solicitud:");
                    solicitarLibroABiblioteca(sistema, mensajeDeSolicitudDeLibro);
                    break;
                }
                
                case '6' ->
                {
                    break;
                }
                
                default ->
                {
                    EntradaSalida.mostrarMensajePorConsola("ERROR: Opcion invalida");
                    opcionElegida = '*';     
                }
            }
            
            
            if (opcionElegida >= '1' && opcionElegida <= '5')
            {
                try
                {
                    sistema.serializar("biblioteca_nacional.txt");
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            
        } while (opcionElegida != '6');
        
        return sigueElPrograma;
    }

    
    public ArrayList<Libro> getLibrosQueDispones()
    {
        return librosQueDispones;
    }

    public void setLibrosQueDispones(ArrayList<Libro> librosQueDispones)
    {
        this.librosQueDispones = librosQueDispones;
    }
    
    
    
    public ArrayList<Prestamo> getPrestamos()
    {
        return prestamos;
    }

    public void setPrestamos(ArrayList<Prestamo> prestamos)
    {
        this.prestamos = prestamos;
    }
    
    public void agregarPrestamo(Prestamo prestamo)
    {
     prestamos.add(prestamo);
    }
    
    
    
    public int getDiasSinPoderSolicitarUnLibro()
    {
        return formulaDeMulta();
    }


    
    
    //DESARROLLO DE LA MULTA
    
   private int cantidadDeDiasDelPrestamoAHoy(Prestamo prestamo)
   {
        System.out.println((int)ChronoUnit.DAYS.between(prestamo.getFechaDelPrestamo(), SistemaBibliotecaNacional.fechaDeHoy));
        return (int)ChronoUnit.DAYS.between(prestamo.getFechaDelPrestamo(), SistemaBibliotecaNacional.fechaDeHoy); //fechaDeHoy es static, para poder usarlo en cualquier parte del código sin tener que traer siempre a una instancia de SistemaBibliotecaNacional.
   }
    
    
    private int formulaDeMulta()
    {
        int diasTotalesDeMulta = 0;
        for(Prestamo pre : prestamos)
        {
            int cantDiasPasadosDelPrestamo = cantidadDeDiasDelPrestamoAHoy(pre);
            if(cantDiasPasadosDelPrestamo > Constante.NUMERO_MAX_DE_DIAS_DE_PRESTAMO) //si esto da true, es que hay deuda con respecto a este prestamo (ya que habrían pasado más de 30 días del prestamo)
            {
                diasTotalesDeMulta += (cantDiasPasadosDelPrestamo - Constante.NUMERO_MAX_DE_DIAS_DE_PRESTAMO) * Constante.DIAS_INHABILITADOS_DE_SOLICITUD_POR_DIA_DE_DEUDA;
            }
        }
        
        return diasTotalesDeMulta;
    }
    
    
    
    
    
    
    
    
    
    private void mostrarLibrosQueDispones()
    {
        ArrayList<Libro> vectorDeLibrosQueDispones = getLibrosQueDispones();
        if(vectorDeLibrosQueDispones.isEmpty())
        {
            EntradaSalida.mostrarMensajeDeError("No tienes libros de la biblioteca.\n");
        }
        else
        {
            for (int i = 0; i < vectorDeLibrosQueDispones.size(); i++)
            {
                vectorDeLibrosQueDispones.get(i).mostrar();
            }
        }
    }

    private void devolverLibroABiblioteca(SistemaBibliotecaNacional sistema, String identificadorLibro)
    {
        Libro li = sistema.buscarLibroPorIdentificador(identificadorLibro);
        if(li != null)
        {
            sistema.agregarLibro(li);
            sacarLibroDeMiDisponibilidad(li);
            
            quitarRegistroDelPrestamo(li); //aclarar que el libro ya está devuelto, eliminar ese registro de pedido que indicaba la deuda
            EntradaSalida.mostrarMensajePorConsola("Libro (" + li.getNombre() + ") devuelto con exito!");
            
            
        }
        else
        {
            EntradaSalida.mostrarMensajePorConsola("No dispones de un libro con ese identificador.");
        }
    }

    private void solicitarLibroABiblioteca(SistemaBibliotecaNacional sistema, String mensajeDeSolicitudDeLibro)
    {
        sistema.agregarSolicitudDePrestamoDeLibro("LECTOR " + this.getUsuario() + ": " + mensajeDeSolicitudDeLibro);
    }

    public int comprobarCantidadDeLibrosQueDispones()
    {
        return(librosQueDispones.size());
    }

    private void quitarRegistroDelPrestamo(Libro li)
    {
        Iterator<Prestamo> iterator = prestamos.iterator();
        while (iterator.hasNext())
        {                                                                               
            Prestamo pre = iterator.next();
            if (pre.coincideLibroConElLibroDelPrestamo(li.getNombre())) //busca el libro en los prestamos
            {
                iterator.remove();  //lo saca
            }
        }
    }

    public void sacarLibroDeMiDisponibilidad(Libro li)
    {
        this.getLibrosQueDispones().remove(li);
    }

    void agregarLibroASuDisponibilidad(Libro li)
    {
        this.librosQueDispones.add(li);
    }
    
    

   
    
}
