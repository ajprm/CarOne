import java.util.ArrayList;
import java.util.Scanner; // Adicione esta linha

public class Motorista extends Usuario {
    private ArrayList<Viagem> minhasViagens; /*Herdando a SuperClasse Usuário com extends */

    public Motorista(String nome, String email, String telefone, String endereco, String senha) {
        super(nome, email, telefone, endereco, senha, true, false);
        this.minhasViagens = new ArrayList<>();
    }

    public ArrayList<Viagem> getMinhasViagens() {
        return minhasViagens;
    }

    public void menuMotorista(Scanner entrada) {
        boolean sair = false;
        while (!sair) {
            System.out.println("\n** Menu Motorista ** ");
            System.out.println(" [ 1 ] Cadastrar viagem ");
            System.out.println(" [ 2 ] Visualizar minhas viagens ");
            System.out.println(" [ 3 ] Gerenciar viagens ");
            System.out.println(" [ 4 ] Sair. ");

            System.out.print(" Digite a sua opção: ");
            int opcao = entrada.nextInt();
            switch (opcao) {
                case 1:
                    cadastrarViagem(entrada);
                    break;
                case 2:
                    visualizarMinhasViagens();
                    break;
                case 3:
                    gerenciarViagens(entrada);
                    break;
                case 4:
                    System.out.println("\nSaindo...");
                    sair = true;
                    break;
                default:
                    System.out.println("Digite uma opção válida, de 1 a 4.");
            }
        }
    }

    private void cadastrarViagem(Scanner entrada) {
        entrada.nextLine(); // Consumir nova linha pendente

        System.out.print("Digite o local de origem: ");
        String origem = entrada.nextLine();

        System.out.print("Digite o local de destino: ");
        String destino = entrada.nextLine();

        ArrayList<String> rota = new ArrayList<>();
        System.out.println("Digite as paradas da rota (digite 'fim' para terminar): ");
        while (true) {
            String parada = entrada.nextLine();
            if (parada.equalsIgnoreCase("fim")) {
                break;
            }
            rota.add(parada);
        }

        System.out.print("Digite o número de vagas disponíveis: ");
        int vagasDisponiveis = entrada.nextInt();

        Local local = new Local(origem, destino, rota);
        Viagem viagem = Viagem.criarViagem(local, this, vagasDisponiveis);
        minhasViagens.add(viagem);
        Passageiro.adicionarViagemDisponivel(viagem);

        System.out.println("Viagem cadastrada com sucesso!");
    }

    private void visualizarMinhasViagens() {
        if (minhasViagens.isEmpty()) {
            System.out.println("Você não tem viagens cadastradas.");
            return;
        }

        System.out.println("\nMinhas viagens:");
        for (Viagem viagem : minhasViagens) {
            System.out.println(viagem);
        }
    }

    private void gerenciarViagens(Scanner entrada) {
        if (minhasViagens.isEmpty()) {
            System.out.println("Você não tem viagens para gerenciar.");
            return;
        }

        System.out.println("\nGerenciar minhas viagens:");
        for (int i = 0; i < minhasViagens.size(); i++) {
            System.out.println("[" + (i + 1) + "] " + minhasViagens.get(i));
        }

        System.out.print("Escolha o número da viagem para gerenciar: ");
        int escolha = lerEscolha(entrada, 1, minhasViagens.size());
        Viagem viagemSelecionada = minhasViagens.get(escolha - 1);
        boolean gerenciar = true;
        while (gerenciar) {
            System.out.println("\n** Gerenciar Viagem " + viagemSelecionada.getId() + " **");
            System.out.println(" [ 1 ] Visualizar paradas ");
            System.out.println(" [ 2 ] Confirmar parada ");
            System.out.println(" [ 3 ] Concluir viagem ");
            System.out.println(" [ 4 ] Voltar ");

            System.out.print("Digite a sua opção: ");
            int opcao = entrada.nextInt();
            switch (opcao) {
                case 1:
                    visualizarParadas(viagemSelecionada);
                    break;
                case 2:
                    confirmarParada(entrada, viagemSelecionada);
                    break;
                case 3:
                    concluirViagem(viagemSelecionada);
                    gerenciar = false;
                    break;
                case 4:
                    gerenciar = false;
                    break;
                default:
                    System.out.println("Digite uma opção válida, de 1 a 4.");
            }
        }
    }

    private void visualizarParadas(Viagem viagem) {
        System.out.println("Paradas da viagem " + viagem.getId() + ": " + viagem.getLocal().getRota());
    }

    private void confirmarParada(Scanner entrada, Viagem viagem) {
        System.out.print("Digite o local da parada a confirmar: ");
        entrada.nextLine(); // Consumir nova linha pendente
        String parada = entrada.nextLine();

        if (viagem.getLocal().getRota().contains(parada)) {
            int subindo = 0;
            int descendo = 0;
            ArrayList<Passageiro> passageirosParaDescer = new ArrayList<>();
            for (Passageiro passageiro : viagem.getPassageiros()) {
                if (parada.equals(passageiro.getLocalDestino())) {
                    descendo++;
                    passageirosParaDescer.add(passageiro);
                }
            }
            for (Passageiro passageiro : passageirosParaDescer) {
                viagem.liberarVaga(passageiro);
                passageiro.confirmarDesembarque();
            }
            System.out.println("Parada confirmada: " + parada + ". Subindo: " + subindo + ", Descendo: " + descendo);
        } else {
            System.out.println("Parada inválida.");
        }
    }

    private void concluirViagem(Viagem viagem) {
        viagem.concluirViagem();
        minhasViagens.remove(viagem);
        System.out.println("Viagem concluída: " + viagem.getId());
        for (Passageiro passageiro : viagem.getPassageiros()) {
            passageiro.avaliarViagem(new Scanner(System.in), viagem);
        }
        avaliarViagem(viagem);
    }

    private void avaliarViagem(Viagem viagem) {
        Scanner entrada = new Scanner(System.in);
        System.out.print("Digite sua avaliação para a viagem (1-5): ");
        int avaliacao = lerEscolha(entrada, 1, 5);
        entrada.nextLine(); // Consumir nova linha
        System.out.print("Digite seu comentário (opcional): ");
        String comentario = entrada.nextLine();
        Avaliacao.criarAvaliacao(viagem.getId(), this.getNome(), avaliacao, comentario);
        System.out.println("Você avaliou a viagem " + viagem.getId() + " com " + avaliacao + " estrelas.");
    }

    private int lerEscolha(Scanner entrada, int min, int max) {
        int valor;
        while (true) {
            valor = entrada.nextInt();
            if (valor >= min && valor <= max) {
                break;
            } else {
                System.out.println("Por favor, digite um número entre " + min + " e " + max + ".");
            }
        }
        return valor;
    }
}