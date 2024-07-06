package bibliotecanacional;

import java.io.Serializable;



public abstract class Persona implements Serializable
{
    private String usuario;
    private String contrasenia;
    
    public boolean setearValoresDePersona(SistemaBibliotecaNacional sistema)
    {
        String usuario = EntradaSalida.leerYValidar("Ingrese su nombre de usuario:");
        String contrasenia = EntradaSalida.leerYValidar("Ingrese su contrasenia:");       
        
        Persona p = sistema.buscarPersonaPorNombre(usuario);
        
        if(p == null)
        {
            setUsuario(usuario);
            setContrasenia(contrasenia);

            return true; //devuelve true si pudo verificar que NO hay un usuario de igual nombre previamente en el sistema
        }
        
        return false; //si devuelve false es porque ya hay un usuario pre existente en el sistema con el mismo nombre
    }
    
    public String getUsuario()
    {
        return usuario;
    }

    public void setUsuario(String usuario)
    {
        this.usuario = usuario;
    }
    
    
    public String getContrasenia()
    {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia)
    {
        this.contrasenia = contrasenia;
    }
    

    
    public boolean coincidenUsuarioYContrasenia(String datos)
    {
        return datos.equals(usuario + ":" + contrasenia);
    }
    
    public boolean coincideUsuario(String usuarioDato)
    {
        return usuarioDato.equals(usuario);
    }
    
    public boolean validacionUsuario(String nombreUsuario, String contrasenia)
    {
        if (nombreUsuario == null) return false;
        if (contrasenia == null) return false;
        return true;  //devuelve true si los dos strings son válidos y devuelve false si alguno de los dos es NULL
    }
    
    public boolean esTipoDeUsuario(String tipoDeUsuario)
    {
        return this.getClass().getSimpleName().equals(tipoDeUsuario);
    }
    
    

    public abstract boolean menu(SistemaBibliotecaNacional sistema);

    public abstract void mostrar();
    
}
