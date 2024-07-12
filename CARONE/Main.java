import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);

        /*O sistema cria usuários iniciais, para que já haja cadastros. */
        ArrayList<Motorista> motoristas = criarMotoristasDeTeste();
        criarViagensDeTeste(motoristas);
        /*Loop while para inicio do sistema */
        boolean sair = false;
        while (!sair) {
            /*Esse é o menu inicial, o qual faz mais sentido do que o de exemplo no Notion, 
            senão não teria nexo a criação de um cadastro sem um login*/
            System.out.println("\n**  CarONE-M  ** ");
            System.out.println(" [ 1 ] LOGIN ");
            System.out.println(" [ 2 ] CADASTRAR-SE");
            System.out.println(" [ 3 ] Sair. ");

            System.out.print(" Digite a sua opção: ");
            int opcao = entrada.nextInt();
            switch (opcao) {
                case 1:
                    Usuario usuarioLogado = Login.fazerLogin(entrada);
                    if (usuarioLogado != null) {
                        System.out.println("Login bem-sucedido para o usuário: " + usuarioLogado.getNome());
                        if (usuarioLogado.isMotorista()) {
                            Motorista motoristaLogado = (Motorista) usuarioLogado;
                            motoristaLogado.menuMotorista(entrada);
                        } else if (usuarioLogado.isPassageiro()) {
                            Passageiro passageiroLogado = (Passageiro) usuarioLogado;
                            passageiroLogado.menuPassageiro(entrada);
                        } else {
                            System.out.println("Usuário não reconhecido.");
                        }
                    } else {
                        System.out.println("Falha ao fazer login. Verifique suas credenciais.");
                    }
                    break;
                case 2:
                    Cadastro.fazerCadastro(entrada);
                    System.out.println("Cadastro realizado com sucesso! Por favor, faça login.");
                    break;
                case 3:
                    System.out.println("\n Saindo...");
                    sair = true;
                    break;
                default:
                    System.out.println("Digite uma opção válida, de 1 a 3.");
            }
        }
        entrada.close();
    }
    /*Métodos de criação de usuários e viagens pré cadastradas, facilita a apresentação e 
    também ajuda no teste de funcionalidade do sistema. (getters)*/
    public static ArrayList<Passageiro> criarPassageirosDeTeste() {
        ArrayList<Passageiro> passageiros = new ArrayList<>();
        passageiros.add(new Passageiro("Ana", "ana@example.com", "1122334455", "Rua C, 789", "senha789"));
        passageiros.add(new Passageiro("Carlos", "carlos@example.com", "5566778899", "Rua D, 101", "senha101"));
        passageiros.add(new Passageiro("Bia", "bia@example.com", "123456789", "Rua E, 202", "senha202"));
        passageiros.add(new Passageiro("David", "david@example.com", "987654321", "Rua F, 303", "senha303"));
        passageiros.add(new Passageiro("Eva", "eva@example.com", "456789123", "Rua G, 404", "senha404"));
        passageiros.add(new Passageiro("Felipe", "felipe@example.com", "789123456", "Rua H, 505", "senha505"));

        for (Passageiro passageiro : passageiros) {
            Usuario.adicionarUsuario(passageiro);
        }

        return passageiros;
    }

    public static ArrayList<Motorista> criarMotoristasDeTeste() {
        ArrayList<Motorista> motoristas = new ArrayList<>();
        motoristas.add(new Motorista("João", "joao@example.com", "123456789", "Rua A, 123", "senha123"));
        motoristas.add(new Motorista("Maria", "maria@example.com", "987654321", "Rua B, 456", "senha456"));

        for (Motorista motorista : motoristas) {
            Usuario.adicionarUsuario(motorista);
        }

        return motoristas;
    }

    public static void criarViagensDeTeste(ArrayList<Motorista> motoristas) {
        Motorista motorista1 = motoristas.get(0);
        Motorista motorista2 = motoristas.get(1);

        ArrayList<String> rota1 = new ArrayList<>();
        rota1.add("Rua A");
        rota1.add("Rua B");
        rota1.add("Rua C");
        rota1.add("Rua D");
        Viagem viagem1 = Viagem.criarViagem(new Local("Rua A", "Rua D", rota1), motorista1, 3);

        ArrayList<String> rota2 = new ArrayList<>();
        rota2.add("Avenida 1");
        rota2.add("Avenida 2");
        rota2.add("Avenida 3");
        rota2.add("Avenida 4");
        Viagem viagem2 = Viagem.criarViagem(new Local("Avenida 1", "Avenida 4", rota2), motorista1, 2);

        ArrayList<String> rota3 = new ArrayList<>();
        rota3.add("Rua P");
        rota3.add("Rua Q");
        rota3.add("Rua R");
        rota3.add("Rua S");
        Viagem viagem3 = Viagem.criarViagem(new Local("Rua P", "Rua S", rota3), motorista2, 4);

        motorista1.getMinhasViagens().add(viagem1);
        motorista1.getMinhasViagens().add(viagem2);
        motorista2.getMinhasViagens().add(viagem3);

        Passageiro.adicionarViagemDisponivel(viagem1);
        Passageiro.adicionarViagemDisponivel(viagem2);
        Passageiro.adicionarViagemDisponivel(viagem3);
    }
}