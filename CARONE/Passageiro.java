import java.util.ArrayList;
import java.util.Scanner;

public class Passageiro extends Usuario {
    /*Atributos: Lista de Minhas viagens; de Viagens Disponiveis  */
    private ArrayList<Viagem> minhasViagens;
    private static ArrayList<Viagem> viagensDisponiveis = new ArrayList<>();
    private String localDestino;

    public Passageiro(String nome, String email, String telefone, String endereco, String senha) {
        super(nome, email, telefone, endereco, senha, false, true);
        this.minhasViagens = new ArrayList<>();
    }

    public static void adicionarViagemDisponivel(Viagem viagem) {
        viagensDisponiveis.add(viagem);
    }

    public static ArrayList<Viagem> getViagensDisponiveis() {
        return viagensDisponiveis;
    }

    public String getLocalDestino() {
        return localDestino;
    }

    public void setLocalDestino(String localDestino) {
        this.localDestino = localDestino;
    }

    public void confirmarDesembarque() {
        System.out.println("Passageiro " + getNome() + " desembarcou com sucesso.");
    }

    public void menuPassageiro(Scanner entrada) {
        boolean sair = false;
        while (!sair) {
            System.out.println("\n** Menu Passageiro ** ");
            System.out.println(" [ 1 ] Buscar caronas ");
            System.out.println(" [ 2 ] Confirmar descida "); /*Mudança de "Carona" p/ "descida" para evitar confusão ao usuário.*/
            System.out.println(" [ 3 ] Avaliar uma viagem ");
            System.out.println(" [ 4 ] Visualizar avaliações ");
            System.out.println(" [ 5 ] Sair. ");

            System.out.print(" Digite a sua opção: ");
            int opcao = lerInteiro(entrada);
            switch (opcao) {
                case 1:
                    buscarCaronas(entrada);
                    break;
                case 2:
                    confirmarCarona(entrada);
                    break;
                case 3:
                    avaliarViagemMenu(entrada);
                    break;
                case 4:
                    Avaliacao.visualizarAvaliacoes();
                    break;
                case 5:
                    System.out.println("\nSaindo...");
                    sair = true;
                    break;
                default:
                    System.out.println("Digite uma opção válida, de 1 a 5.");
            }
        }
    }

    private void buscarCaronas(Scanner entrada) {
        entrada.nextLine(); // Consumir nova linha pendente
        System.out.print("Digite a localização de partida desejada: ");
        String partidaDesejada = entrada.nextLine();
        System.out.print("Digite a localização de destino desejada: ");
        String destinoDesejado = entrada.nextLine();

        ArrayList<Viagem> viagensEncontradas = Viagem.buscarViagensDisponiveis(partidaDesejada, destinoDesejado);

        if (!viagensEncontradas.isEmpty()) {
            exibirViagensEncontradas(viagensEncontradas);
            int escolha = lerEscolhaViagem(entrada, viagensEncontradas.size());
            if (escolha > 0 && escolha <= viagensEncontradas.size()) {
                Viagem viagemSelecionada = viagensEncontradas.get(escolha - 1);
                if (viagemSelecionada.solicitarVaga(this)) {
                    this.localDestino = destinoDesejado;
                    minhasViagens.add(viagemSelecionada);
                    System.out.println("Vaga solicitada com sucesso na viagem: " + viagemSelecionada.getId());
                    viagemSelecionada.notificarMotoristaSobreNovaParada(partidaDesejada, destinoDesejado, 1, 0);
                } else {
                    System.out.println("A viagem selecionada está lotada.");
                }
            } else {
                System.out.println("Escolha inválida.");
            }
        } else {
            System.out.println("Nenhuma viagem encontrada para os locais desejados.");
        }
    }

    private void exibirViagensEncontradas(ArrayList<Viagem> viagensEncontradas) {
        System.out.println("\nViagens encontradas:");
        for (int i = 0; i < viagensEncontradas.size(); i++) {
            System.out.println("[" + (i + 1) + "] " + viagensEncontradas.get(i));
        }
    }

    private int lerEscolhaViagem(Scanner entrada, int tamanhoLista) {
        System.out.print("Escolha o número da viagem desejada: ");
        return lerInteiro(entrada, 1, tamanhoLista);
    }

    private void confirmarCarona(Scanner entrada) {
        if (minhasViagens.isEmpty()) {
            System.out.println("Você não tem caronas para confirmar.");
            return;
        }

        System.out.println("\nMinhas caronas pendentes de confirmação:");
        exibirMinhasViagens(minhasViagens);

        int escolha = lerEscolhaViagem(entrada, minhasViagens.size());
        if (escolha > 0 && escolha <= minhasViagens.size()) {
            Viagem viagemConfirmada = minhasViagens.get(escolha - 1);
            confirmarCaronaViagem(viagemConfirmada);
        } else {
            System.out.println("Escolha inválida.");
        }
    }

    private void exibirMinhasViagens(ArrayList<Viagem> minhasViagens) {
        for (int i = 0; i < minhasViagens.size(); i++) {
            System.out.println("[" + (i + 1) + "] " + minhasViagens.get(i));
        }
    }

    private void confirmarCaronaViagem(Viagem viagemConfirmada) {
        System.out.println("Carona confirmada para a viagem: " + viagemConfirmada.getId());
    }

    private void avaliarViagemMenu(Scanner entrada) {
        if (minhasViagens.isEmpty()) {
            System.out.println("Você não tem viagens para avaliar.");
            return;
        }

        System.out.println("\nMinhas viagens:");
        exibirMinhasViagens(minhasViagens);

        int escolha = lerEscolhaViagem(entrada, minhasViagens.size());
        if (escolha > 0 && escolha <= minhasViagens.size()) {
            Viagem viagemParaAvaliar = minhasViagens.get(escolha - 1);
            avaliarViagem(entrada, viagemParaAvaliar);
        } else {
            System.out.println("Escolha inválida.");
        }
    }

    public void avaliarViagem(Scanner entrada, Viagem viagemParaAvaliar) {
        System.out.print("Digite sua avaliação (1-5): ");
        int avaliacao = lerInteiro(entrada, 1, 5);
        entrada.nextLine(); // Consumir nova linha
        System.out.print("Digite seu comentário (opcional): ");
        String comentario = entrada.nextLine();
        Avaliacao.criarAvaliacao(viagemParaAvaliar.getId(), this.getNome(), avaliacao, comentario);
        System.out.println("Você avaliou a viagem " + viagemParaAvaliar.getId() + " com " + avaliacao + " estrelas.");
        minhasViagens.remove(viagemParaAvaliar); // Remove a viagem da lista de viagens do passageiro após a avaliação
    }

    private int lerInteiro(Scanner entrada) {
        while (!entrada.hasNextInt()) {
            System.out.println("Entrada inválida. Por favor, digite um número inteiro.");
            entrada.nextLine(); // Limpar o buffer
        }
        return entrada.nextInt();
    }

    private int lerInteiro(Scanner entrada, int min, int max) {
        int valor;
        while (true) {
            valor = lerInteiro(entrada);
            if (valor >= min && valor <= max) {
                break;
            } else {
                System.out.println("Por favor, digite um número entre " + min + " e " + max +".");
            }
        }
        return valor;
    }
}