package bibliotecanacional;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;



public class Bibliotecario extends Persona implements Serializable
{
    public Bibliotecario()
    {
    }

    public Bibliotecario(String nombre, String contraseña)
    {
        setUsuario(nombre);
        setContrasenia(contraseña);
        mostrar();
    }
    
    
    
    @Override
    public void mostrar()
    {
        EntradaSalida.mostrarMensajePorConsola("BIBLIOTECARIO\nNombre: " + this.getUsuario() + "\nCONTRASENIA: " + this.getContrasenia() + "\n\n");  
    }
    
    @Override
    public boolean menu(SistemaBibliotecaNacional sistema)
    {
        boolean sigueElPrograma = true;
        char opcionElegida;
        
        do
        {
            opcionElegida = EntradaSalida.leerCaracter("""
                                                       MENU DE BIBLIOTECARIO
                                                       [1] Ver todos los libros
                                                       [2] Ver libros según una categoría
                                                       [3] Mandar un libro a reparación
                                                       [4] Devolver libro de reparación a la biblioteca
                                                       [5] Dar un libro a préstamo
                                                       [6] Ver los libros en reparación
                                                       [7] Ver los libros a prestamo
                                                       [8] Ver las solicitudes de libros de los lectores
                                                       [9] Eliminar una solicitud de libro de un lector
                                                       [A] Eliminar todas las solicitudes de libros de los lectores
                                                       [B] Salir del menu
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
                    String categoriaPedida = EntradaSalida.leerString("Ingrese la categoría que quiere ver:");
                    sistema.mostrarLibrosSegunCategoria(categoriaPedida);
                    break;
                }

                case '3' ->
                {
                    String identificadorPedido = EntradaSalida.leerString("Ingrese el identificador del libro que quiere enviar a reparación: ");
                    mandarLibroAReparacion(sistema, identificadorPedido);
                    break;
                }

                case '4' ->
                {
                    String identificadorPedido = EntradaSalida.leerString("Ingrese el identificador del libro que quiere devolver de la reparación a la biblioteca: ");
                    devolverLibroDeReparacionABiblioteca(sistema, identificadorPedido);
                    break;
                }

                case '5' ->
                {
                    String nombreDeUsuario = EntradaSalida.leerString("Ingrese el nombre de usuario al que le prestará un libro:");
                    String identificadorPedido = EntradaSalida.leerString("Ingrese el identificador del libro que le quiere prestar:");
                    mandarLibroAPrestamo(sistema, identificadorPedido, nombreDeUsuario);
                    break;
                }
                case '6' ->
                {
                    sistema.mostrarLibrosEnReparación();
                    break;
                }
                
                case '7' ->
                {
                    sistema.mostrarLibrosEnPrestamoALectores();
                    break;
                }

                case '8' ->
                {
                    mostrarSolicitudesDeLibrosDeLectores(sistema);
                    break;
                }
                
                case '9' ->
                {
                    eliminarSolicitudesDeLibrosDeLectores(sistema);
                    break;
                }
                
                case 'A' ->
                {
                    eliminarTodasLasSolicitudesDeLibrosDeLectores(sistema); //funciona como un limpiar correo electrónico
                    break;
                }
                
                case 'B' ->
                {
                    break;
                }

                default ->
                {
                    EntradaSalida.mostrarMensajePorConsola("ERROR: Opcion invalida");
                    opcionElegida = '*';     
                }
            }

            if (opcionElegida >= '1' && opcionElegida <= '9')
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
            
            
        } while(opcionElegida != 'B');
        
            
        
            return sigueElPrograma;
   }

    
    private void mandarLibroAReparacion(SistemaBibliotecaNacional sistema, String identificadorPedido)
    {
        ArrayList<Libro> vectorDeLibros = sistema.getLibros();
        ArrayList<Libro> vectorDeLibrosEnReparacion = sistema.getLibrosEnReparacion();
        for (int i = 0; i < vectorDeLibros.size(); i++)
        {
            if(vectorDeLibros.get(i).coincideElIdentificador(identificadorPedido))
            {
              vectorDeLibrosEnReparacion.add(vectorDeLibros.get(i));
              vectorDeLibros.remove(i);
            }
        }
    }

    private void devolverLibroDeReparacionABiblioteca(SistemaBibliotecaNacional sistema, String identificadorPedido)
    {
        ArrayList<Libro> vectorDeLibros = sistema.getLibros();
        ArrayList<Libro> vectorDeLibrosEnReparacion = sistema.getLibrosEnReparacion();
        
        if(vectorDeLibrosEnReparacion.size() == 0)
        {
            EntradaSalida.mostrarMensajePorConsola("No hay libros en reparación.");
        }
        
        for (int i = 0; i < vectorDeLibrosEnReparacion.size(); i++)
        {
            if(vectorDeLibrosEnReparacion.get(i).coincideElIdentificador(identificadorPedido))
            {
            vectorDeLibros.add(vectorDeLibrosEnReparacion.get(i));
            vectorDeLibrosEnReparacion.remove(i);
            }
        }
    }
    
    
   private void mandarLibroAPrestamo(SistemaBibliotecaNacional sistema, String identificadorPedido, String nombreDeUsuario)
   {
        Libro libroEncontrado = sistema.buscarLibroPorIdentificadorEnBiblioteca(identificadorPedido);  
        if (libroEncontrado == null) //Si el libro no se encontró
        {
            EntradaSalida.mostrarMensajePorConsola("ERROR: Libro no encontrado.");
            return;
        }

        Persona personaEncontrada = sistema.buscarPersonaPorNombre(nombreDeUsuario); //buscarPersonaPorNombre para encontrar al usuario segun su nombre que es único
        if (personaEncontrada == null || !(personaEncontrada instanceof Lector)) //Si el usuario no se encontró o no es un lector, muestra el mensaje que indica el error
        {
            EntradaSalida.mostrarMensajePorConsola("ERROR: Lector no encontrado.");
            return;
        }

        Lector lectorEncontrado = (Lector) personaEncontrada; //Castea a Lector ya que sabemos que es un lector
        if(lectorEncontrado.getDiasSinPoderSolicitarUnLibro() == 0)
        {
            if(lectorEncontrado.comprobarCantidadDeLibrosQueDispones() == Constante.CANTIDAD_MAX_DE_LIBROS_POSIBLES_LECTOR) //si el lector en cuestión tiene 3 libros
            {
                EntradaSalida.mostrarMensajePorConsola("El lector " + lectorEncontrado.getUsuario() + " no puede adquirir mas libros en este momento, ya que ya tiene " + Constante.CANTIDAD_MAX_DE_LIBROS_POSIBLES_LECTOR + " libros, y es el maximo posible para cada lector.");
            }
            else
            { 
            
            lectorEncontrado.agregarLibroASuDisponibilidad(libroEncontrado);
            sistema.sacarLibroDeBiblioteca(libroEncontrado);   
            Prestamo prestamoDeLibro = new Prestamo(SistemaBibliotecaNacional.fechaDeHoy, libroEncontrado);
            lectorEncontrado.agregarPrestamo(prestamoDeLibro);
            EntradaSalida.mostrarMensajePorConsola("Libro prestado correctamente a " + nombreDeUsuario);
            }
            
        }
        else
        {
            EntradaSalida.mostrarMensajePorConsola("El lector " + lectorEncontrado.getUsuario() + " DEBE un libro. No puede adquirir libros por " + lectorEncontrado.getDiasSinPoderSolicitarUnLibro() + " dias.");
        }
  }

   
   
   
   
   //SOLICITUDES DE LIBROS DE LECTORES:
   
   
    private void mostrarSolicitudesDeLibrosDeLectores(SistemaBibliotecaNacional sistema)
    {
        ArrayList<String> vectorDeSolicitudes = sistema.getSolicitudesDePrestamosDeLibros();
        
        if(vectorDeSolicitudes.isEmpty())
        {
            EntradaSalida.mostrarMensajePorConsola("No tienes solicitudes de libros de ningun lector.\n");
        }
        
        for(int i=0; i < vectorDeSolicitudes.size(); i++)
        {
            EntradaSalida.mostrarMensajePorConsola("[" + i + "]: " + vectorDeSolicitudes.get(i));
        }
    }
    
    

    private void eliminarSolicitudesDeLibrosDeLectores(SistemaBibliotecaNacional sistema)
    {
        mostrarSolicitudesDeLibrosDeLectores(sistema);
        int solicitudAEliminar = EntradaSalida.leerEntero("Ingrese el numero de solicitud que desea eliminar:");
        
        ArrayList<String> vectorDeSolicitudes = sistema.getSolicitudesDePrestamosDeLibros();
        
        if(vectorDeSolicitudes.isEmpty())
        {
            EntradaSalida.mostrarMensajePorConsola("No tienes solicitudes de libros de ningun lector.\n");
        }
        
        vectorDeSolicitudes.remove(solicitudAEliminar);
    }

    
    
    private void eliminarTodasLasSolicitudesDeLibrosDeLectores(SistemaBibliotecaNacional sistema)
    {
        ArrayList<String> vectorDeSolicitudes = sistema.getSolicitudesDePrestamosDeLibros();

        if(vectorDeSolicitudes.isEmpty())
        {
            EntradaSalida.mostrarMensajePorConsola("No tienes solicitudes de libros de ningun lector.\n");
        }
        for(int i=0; i < vectorDeSolicitudes.size(); i++)
        {
        vectorDeSolicitudes.remove(i);   
        }
       
    }

    
}


    
    
  



    