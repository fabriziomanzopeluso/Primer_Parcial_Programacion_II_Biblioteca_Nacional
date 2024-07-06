package bibliotecanacional;

public class FactoriaDePersonas
{
    public void crearPersona(SistemaBibliotecaNacional sistema, String tipoDeUsuario)
    {
        try
        {
            String nombrePackage = this.getClass().getPackage().getName();
            Persona p = (Persona) Class.forName(nombrePackage + "." + tipoDeUsuario).getDeclaredConstructor().newInstance(); //reconoce el tipo de clase gracias al string que pasamos como tipoDeUsuario, y lo crea con newInstance()
            boolean esPersonaValida = p.setearValoresDePersona(sistema); //devuelve true si se pudo crear la persona a la perfección
            
            if(esPersonaValida)
            {
                sistema.agregarPersonas(p); //Mete al usuario en el sistema, sea Lector, Bibliotecario, o Administrador
                EntradaSalida.mostrarMensajeDeError("SE HA AGREGADO CON ÉXITO EL USUARIO AL SISTEMA");    
            }
            else
            {
                EntradaSalida.mostrarMensajePorConsola("Ya hay un nombre de usuario identico, por ende debes intentar con crear otro nombre.\n");
            }
        }
        catch (Exception ex) //ante cualquier error, como no encontrar el paquete (nombrePackage), por ejemplo
        {
            System.err.println(ex);
        }
    }
}
