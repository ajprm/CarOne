import java.util.Scanner;

public class Login {
    public static Usuario fazerLogin(Scanner entrada) {
        entrada.nextLine(); 

        System.out.print("Digite seu username: ");
        String username = entrada.nextLine();

        System.out.print("Digite sua senha: ");
        String senha = entrada.nextLine();

        for (Usuario usuario : Usuario.getUsuarios()) {
            if (usuario.getUsername().equals(username) && usuario.getSenha().equals(senha)) {
                return usuario;
            }
        }

        System.out.println("Usuário ou senha incorretos. Tente novamente.");
        return null;
    }
}