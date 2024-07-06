package bibliotecanacional;

public class Main
{

    public static void main(String[] args)
    {
        Control c = new Control();
        try
        {
            c.ejecutar();
        }
        catch (NullPointerException e) //ocurre cuando intenta trabajar con objetos no inicializados y con métodos o atributos NULL.
        {         
            EntradaSalida.mostrarMensajeDeError("ERROR!");
        }
    }
}

    
    
    
    
    
    
    
    
    
    
  