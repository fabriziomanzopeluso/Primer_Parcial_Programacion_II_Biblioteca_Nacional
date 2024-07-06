package bibliotecanacional;

import java.io.IOException;

public class Control
{

	public void ejecutar()
        {

		SistemaBibliotecaNacional sistema = new SistemaBibliotecaNacional();
		boolean sigueElPrograma;
		try //intenta serializar y rescatar los datos guardados de un anterior uso del sistema
                {
			sistema = sistema.deSerializar("biblioteca_nacional.txt");
                        EntradaSalida.mostrarMensajePorConsola("= = = SISTEMA DE LA BIBLIOTECA NACIONAL = = =\n");
			sigueElPrograma = EntradaSalida.leerBooleano("Desea ingresar al sistema? [True/False]"); //ante un False, se corta el programa
		}
                catch (Exception e) //entra si no hay nada por serializar (es decir, no hay datos guardados, por ende es el primer arranque del sistema)
                {
			EntradaSalida.mostrarMensajePorConsola("PRIMER ARRANQUE DEL SISTEMA\n");
			String usuario = EntradaSalida.leerYValidar("Ingrese su nombre completo:");
                        String contrasenia = EntradaSalida.leerYValidar("Ingrese su contrasenia:");
			Administrador admin = new Administrador(usuario, contrasenia); //primer administrador, el original
			sistema.getPersonas().add(admin);

			try
                        {
				sistema.serializar("biblioteca_nacional.txt"); //serializa el primer administrador del sistema, el del primer arranque
				EntradaSalida.mostrarMensajePorConsola("Primer arranque realizado con EXITO. Reinicie el sistema.");
			}
                        catch (IOException ex)
                        {
				ex.printStackTrace();
			}
                        
			sigueElPrograma = false; //siempre después del primer arranque, se corta el programa
		}

		while (sigueElPrograma) //cuando le decimos 'true' (que SÍ queremos entrar al sistema) al leerBooleano, ingresa en este while, que es la ejecución del sistema mientras ese
                {                       //booleano sea true. Cuando sea false, se corta el sistema.                              
                        Persona p = sistema.iniciarSesionDeUsuario();
			if (p == null) //no se encontró ningún usuario con esos datos
                        {
				EntradaSalida.mostrarMensajePorConsola("No existe ningun usuario con esa combinacion.");
			}
                        else
                        {
				sigueElPrograma = p.menu(sistema); //te manda al submenu del tipo de usuario que ingresamos recién
			}
		}
	}
}