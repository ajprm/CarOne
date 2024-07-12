import java.util.Scanner;

public class Cadastro {
    public static void fazerCadastro(Scanner entrada) {
        entrada.nextLine(); // Consumir nova linha pendente

        System.out.print("Digite seu nome: ");
        String nome = entrada.nextLine();

        String email;
        while (true) {
            System.out.print("Digite seu email: ");
            email = entrada.nextLine();
            if (validarEmail(email)) {
                break;
            } else {
                System.out.println("Email inválido. Tente novamente.");
            }
        }

        String telefone;
        while (true) {
            System.out.print("Digite seu telefone (11 números): ");
            telefone = entrada.nextLine();
            if (validarTelefone(telefone)) {
                break;
            } else {
                System.out.println("Telefone inválido. Tente novamente.");
            }
        }

        System.out.print("Digite seu endereço: ");
        String endereco = entrada.nextLine();

        String senha;
        while (true) {
            System.out.print("Digite sua senha (mínimo 6 caracteres numéricos): ");
            senha = entrada.nextLine();
            if (validarSenha(senha)) {
                break;
            } else {
                System.out.println("Senha inválida. Tente novamente.");
            }
        }

        System.out.println("\n [ 1 ] MOTORISTA \n [ 2 ] PASSAGEIRO ");
        System.out.print("Sua escolha: ");
        int escolha = entrada.nextInt();

        if (escolha == 1) {
            Motorista novoMotorista = new Motorista(nome, email, telefone, endereco, senha);
            Usuario.adicionarUsuario(novoMotorista);
            System.out.println("Cadastro de motorista realizado com sucesso! Seu username é: " + novoMotorista.getUsername());
        } else if (escolha == 2) {
            Passageiro novoPassageiro = new Passageiro(nome, email, telefone, endereco, senha);
            Usuario.adicionarUsuario(novoPassageiro);
            System.out.println("Cadastro de passageiro realizado com sucesso! Seu username é: " + novoPassageiro.getUsername());
        } else {
            System.out.println("Escolha inválida. Tente novamente.");
        }
    }

    public static boolean validarEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        int atPosition = email.indexOf('@');
        int dotPosition = email.lastIndexOf('.');
        return atPosition > 0 && dotPosition > atPosition && dotPosition < email.length() - 1;
    }

    public static boolean validarTelefone(String telefone) {
        if (telefone == null || telefone.length() != 11) {
            return false;
        }
        for (char c : telefone.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    public static boolean validarSenha(String senha) {
        if (senha == null || senha.length() < 6) {
            return false;
        }
        for (char c : senha.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }
}