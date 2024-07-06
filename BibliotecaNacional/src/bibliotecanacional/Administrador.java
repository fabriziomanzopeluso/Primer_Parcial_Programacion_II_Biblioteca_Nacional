package bibliotecanacional;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;


public class Administrador extends Persona implements Serializable
{

    public Administrador()
    {
    }
    
   
    public Administrador(String nombre, String contraseña)
    {
        setUsuario(nombre);
        setContrasenia(contraseña);
        mostrar();
    }

    @Override
    public boolean menu(SistemaBibliotecaNacional sistema)
    {
        FactoriaDePersonas factoriaDePersonas = new FactoriaDePersonas();
        boolean sigueElPrograma = true;
        
        char opcionElegida;
        
        do
        {        
            
        opcionElegida = EntradaSalida.leerCaracter("""
                                                    MENU DE ADMINISTRADOR
                                                    [1] Dar de alta un nuevo administrador
                                                    [2] Dar de alta un nuevo bibliotecario
                                                    [3] Dar de alta un nuevo lector
                                                    [4] Mostrar a todos los usuarios
                                                    [5] Mostrar solamente a los administradores
                                                    [6] Mostrar solamente a los bibliotecarios
                                                    [7] Mostrar solamente a los lectores
                                                    [8] Cargar un libro
                                                    [9] Mostrar todos los libros
                                                    [A] Eliminar un usuario (lector o bibliotecario)
                                                    [B] Salir del menu
                                                    [C] Salir del sistema
                                                    """);
        
        switch(opcionElegida)
        {
            case '1' ->
            {
                EntradaSalida.mostrarMensajeDeError("ALTA DE ADMINISTRADOR");
                factoriaDePersonas.crearPersona(sistema, "Administrador");
                break;
            }
            
            case '2' ->
            {
                EntradaSalida.mostrarMensajeDeError("ALTA DE BIBLIOTECARIO");
                factoriaDePersonas.crearPersona(sistema, "Bibliotecario");
                break;
            }
            
            case '3' ->
            {
                EntradaSalida.mostrarMensajeDeError("ALTA DE LECTOR");
                factoriaDePersonas.crearPersona(sistema, "Lector");
                break;
            }
            
            case '4' ->
            {
                sistema.mostrarUsuarios();
                break;
            }
            
            case '5' ->
            {       
                sistema.mostrarUsuarios("Administrador");
                break;
            }
            
            case '6' ->
            {
                sistema.mostrarUsuarios("Bibliotecario");
                break;
            }
            
            case '7' ->
            {
                sistema.mostrarUsuarios("Lector");
                break;
            }
            
            case '8' ->
            {
                altaDeUnLibro(sistema);
                break;
            }
            
            case '9' ->
            {
                sistema.mostrarLibros();
                break;
            }
            
            case 'A' ->
            {
                String usuarioAEliminar = EntradaSalida.leerYValidar("Ingrese el nombre de usuario que quiere eliminar:");
                eliminarUsuario(sistema, usuarioAEliminar);
            }
            
            case 'B' ->
            {
                break;
            }    
            
            case 'C' ->
            {
                sigueElPrograma = false;
                break;
            }
            
            default ->
            {
            EntradaSalida.mostrarMensajePorConsola("ERROR: Opcion invalida");
            opcionElegida = '*';         
            }
            
            
        }                                                                      
        if (opcionElegida >= '1' && opcionElegida <= 'A')
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
       
        } while(opcionElegida != 'B' && opcionElegida != 'C');
        
           return sigueElPrograma;
    }    
    

    @Override
    public void mostrar()
    {
        EntradaSalida.mostrarMensajePorConsola("ADMINISTRADOR\nNombre: " + this.getUsuario() + "\nContraseña: " + this.getContrasenia() + "\n\n");
        
    }

    

    
    private void altaDeUnLibro(SistemaBibliotecaNacional sistema)
    {
        EntradaSalida.mostrarMensajePorConsola("ALTA DE UN LIBRO\n");
        
        String id = EntradaSalida.leerYValidar("Ingrese el identificador del libro, éste debe ser ÚNICO:");
        Libro li = sistema.buscarLibroPorIdentificador(id);
        if(li == null) //osea, si el identificador del libro no existe
        {
            String nom = EntradaSalida.leerYValidar("Ingrese el nombre del libro:");
            int anio = EntradaSalida.leerYValidarEntero("Ingrese el anio de publicacion del libro:");
            String cat = sistema.validarCategoriaDeLibro("Ingrese la categoria del libro:");
            String edi = EntradaSalida.leerYValidar("Ingrese la editorial del libro:");

            String nomAutor = EntradaSalida.leerYValidar("Ingrese el nombre del autor:");
            LocalDate fechaDeNacAutor = EntradaSalida.pedirYValidarFecha("Ingrese la fecha de nacimiento del autor:");
            String nacionalidadAutor = EntradaSalida.leerYValidar("Ingrese la nacionalidad del autor:");
            Autor aut = new Autor(nomAutor, fechaDeNacAutor, nacionalidadAutor);

            li = sistema.buscarLibroPorTodosSusDatos(id + ":" + nom + ":" + anio + ":" + cat + ":" + edi, aut);
            if (li != null)
            {
                EntradaSalida.mostrarMensajePorConsola("ERROR: El libro ya figura en el sistema");
            }
            else
            {
                li = new Libro(id,nom, anio, aut, cat, edi);
                sistema.agregarLibro(li);
                EntradaSalida.mostrarMensajePorConsola("Se ha incorporado el libro al sistema");
            }
        }
        else
        {
        EntradaSalida.mostrarMensajePorConsola("El identificador que introdujo ya se encuentra en el sistema.");
        }
    }
    
    
    
    
    
   public void eliminarUsuario(SistemaBibliotecaNacional sistema, String usuario)
   {
        Persona persona = sistema.buscarPersonaPorNombre(usuario);

        if (persona == null)
        {
            EntradaSalida.mostrarMensajePorConsola("El usuario ingresado no existe.\n");
        }
        else
        {
            if (!(persona instanceof Administrador))
            {
                boolean confirmacion = EntradaSalida.leerBooleano("¿Está seguro de que desea eliminar este usuario del sistema?");

                if (confirmacion)
                {
                    if(persona instanceof Lector)
                    {
                        Lector lector = (Lector)persona;

                        for(int i=0; i<lector.getLibrosQueDispones().size(); i++)
                        {
                            Libro li = lector.getLibrosQueDispones().get(i);
                            lector.sacarLibroDeMiDisponibilidad(li);
                            sistema.agregarLibro(li);
                        }
                    }
                    sistema.quitarUsuarioDelSistema(persona);
                    EntradaSalida.mostrarMensajePorConsola("El usuario ha sido eliminado del sistema.\n");
                }
            }
            else
            {
                EntradaSalida.mostrarMensajePorConsola("No se puede eliminar a administradores.\n");
            }
        }
    }

}
