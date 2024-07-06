package bibliotecanacional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;


public class EntradaSalida
{
    public static String leerString(String msj)
    {
        EntradaSalida.mostrarMensajePorConsola(msj);
        Scanner sc = new Scanner(System.in);
        String str = sc.nextLine();
        return str;
    }
    
    public static String leerString()
    {
        Scanner sc = new Scanner(System.in);
        String str = sc.nextLine();
        return str;
    }
    
    
   public static int leerEntero(String msj) throws NumberFormatException
   {
        try
        {
            String entrada = leerString(msj);
            return Integer.parseInt(entrada); //lo lee como String y lo convierte a entero
        }
        catch (NumberFormatException e)
        {
            throw new NumberFormatException("La entrada no es un número entero válido");
        }
    }
    
    
     public static boolean leerBooleano(String msj) {
        Scanner scanner = new Scanner(System.in);
        boolean res = false;
        boolean inputValido = false;

        while (!inputValido) {
            try {
                mostrarMensajePorConsola(msj);
                res = scanner.nextBoolean();
                inputValido = true; //La entrada es válida
            } catch (InputMismatchException e) {
                System.out.println("Valor booleano no valido.");
                scanner.next(); //Descarta la entrada no válida
            }
        }

        return res;
    }
    
    public static boolean leerBooleano()
    {
        Scanner scanner = new Scanner(System.in);
        boolean res = false;
        boolean inputValido = false;

        while (!inputValido) {
            try {
                EntradaSalida.mostrarMensajePorConsola("Ingrese un valor booleano [True/False]:");
                res = scanner.nextBoolean();
                inputValido = true; //La entrada es valida
            } catch (InputMismatchException e) {
                System.out.println("Valor booleano no valido.");
                scanner.next(); //Descarta la entrada no valida
            }
        }

        return res;
    }
    
    public static char leerCaracter(String msj)
    {
        EntradaSalida.mostrarMensajePorConsola(msj);
        Scanner scanner = new Scanner(System.in);
        char result = scanner.next().charAt(0);
        return result;
    }
          
    public static char leerCaracter()
    {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese un caracter:");
        char result = scanner.next().charAt(0);
        return result;
    }
    
    public static void mostrarMensajePorConsola(String mensaje)
    {
        System.out.println(mensaje);
    }
    
    public static void mostrarMensajeDeError(String mensaje)
    {
        System.err.println(mensaje);
    }
          
    public static boolean esTextoValido(String cadena)
    {
        return(!cadena.equals("")); //retorna true si es valido, false si es invalido
    }
    
    public static String leerYValidar(String msj)
    {
        String entrada;
        do
        {
            entrada = EntradaSalida.leerString(msj);
            if (!esTextoValido(entrada))
            {
                EntradaSalida.mostrarMensajeDeError("ERROR: Input invalido, por favor intente nuevamente.");
            }
        } while (!esTextoValido(entrada));
        return entrada;
    }
    
        
    public static boolean esNumeroValido(int numero)
    {
        return(!(numero == 0)); //retorna true si es valido, false si es invalido. En este caso, como int no puede ser null porque es primitivo, su valor vacio sería 0
    }
    
    
   public static int leerYValidarEntero(String msj)
   {
    int entrada = 0;
    boolean esValido = false;

    do
    {
        try
        {
            entrada = EntradaSalida.leerEntero(msj);
            if (esNumeroValido(entrada))
            {
                esValido = true;
            }
            else
            {
                EntradaSalida.mostrarMensajePorConsola("Error, ese valor no es un entero.");
            }
        }
        catch (NumberFormatException e)
        {
            EntradaSalida.mostrarMensajePorConsola("Error, ese valor no es un entero.");
        }
    } while (!esValido);

    return entrada;
}


    
    public static LocalDate pedirYValidarFecha(String msj)
    {
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate fecha = null;
        boolean fechaValida = false;

        while (!fechaValida)
        {
            String fechaString = EntradaSalida.leerYValidar(msj);

            try
            {
                fecha = LocalDate.parse(fechaString, formatoFecha);
                fechaValida = true;
            } catch (DateTimeParseException e)
            {
                EntradaSalida.mostrarMensajePorConsola("Fecha inválida. Por favor, ingrese una fecha con el formato dd/MM/yyyy.");
            }
        }

        return fecha;
    }

    
    
    
}
