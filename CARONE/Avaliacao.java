import java.util.ArrayList;
import java.util.List;

public class Avaliacao {
    private String viagem;
    private String avaliador; // Nome do usuário que fez a avaliação
    private int nota;
    private String comentario;
    private static List<Avaliacao> avaliacoes = new ArrayList<>();

    public Avaliacao(String viagem, String avaliador, int nota, String comentario) {
        this.viagem = viagem;
        this.avaliador = avaliador;
        this.nota = nota;
        this.comentario = comentario;
    }

    // Métodos getters
    public String getViagem() {
        return viagem;
    }

    public String getAvaliador() {
        return avaliador;
    }

    public int getNota() {
        return nota;
    }

    public String getComentario() {
        return comentario;
    }

    @Override
    public String toString() {
        return "Avaliacao{" +
                "viagem='" + viagem + '\'' +
                ", avaliador='" + avaliador + '\'' +
                ", nota=" + nota +
                ", comentario='" + comentario + '\'' +
                '}';
    }

    // Método para criar uma nova avaliação
    public static void criarAvaliacao(String viagem, String avaliador, int nota, String comentario) {
        if (nota < 1 || nota > 5) {
            System.out.println("A nota deve ser entre 1 e 5.");
            return;
        }
        if (jaAvaliada(viagem, avaliador)) {
            System.out.println("Você já avaliou esta viagem.");
            return;
        }
        Avaliacao novaAvaliacao = new Avaliacao(viagem, avaliador, nota, comentario);
        avaliacoes.add(novaAvaliacao);
        System.out.println("Avaliação criada com sucesso!");
    }

    // Método para verificar se uma viagem já foi avaliada pelo passageiro
    public static boolean jaAvaliada(String viagem, String avaliador) {
        for (Avaliacao avaliacao : avaliacoes) {
            if (avaliacao.getViagem().equals(viagem) && avaliacao.getAvaliador().equals(avaliador)) {
                return true;
            }
        }
        return false;
    }

    // Método para visualizar todas as avaliações
    public static void visualizarAvaliacoes() {
        if (avaliacoes.isEmpty()) {
            System.out.println("Nenhuma avaliação disponível.");
        } else {
            for (Avaliacao avaliacao : avaliacoes) {
                System.out.println(avaliacao);
            }
        }
    }

    // Método para excluir uma avaliação por índice
    public static void excluirAvaliacao(int index) {
        if (index >= 0 && index < avaliacoes.size()) {
            avaliacoes.remove(index);
            System.out.println("Avaliação excluída com sucesso!");
        } else {
            System.out.println("Índice inválido. Nenhuma avaliação foi excluída.");
        }
    }

    // Método para obter todas as avaliações
    public static List<Avaliacao> getAvaliacoes() {
        return avaliacoes;
    }
}