import java.util.ArrayList;
import java.util.Random;

public class Viagem {
    private String id;
    private Local local;
    private Motorista motorista;
    private int vagasDisponiveis;
    private ArrayList<Passageiro> passageiros;
    private static ArrayList<Viagem> todasViagens = new ArrayList<>();

    private Viagem(Local local, Motorista motorista, int vagasDisponiveis) {
        this.id = gerarIdAleatorio();
        this.local = local;
        this.motorista = motorista;
        this.vagasDisponiveis = vagasDisponiveis;
        this.passageiros = new ArrayList<>();
        todasViagens.add(this); // Adiciona a viagem à lista de todas as viagens
    }

    private String gerarIdAleatorio() {
        return "V-" + new Random().nextInt(10000);
    }

    public String getId() {
        return id;
    }

    public Local getLocal() {
        return local;
    }

    public Motorista getMotorista() {
        return motorista;
    }

    public int getVagasDisponiveis() {
        return vagasDisponiveis;
    }

    public ArrayList<Passageiro> getPassageiros() {
        return passageiros;
    }

    public boolean solicitarVaga(Passageiro passageiro) {
        if (vagasDisponiveis > 0) {
            passageiros.add(passageiro);
            vagasDisponiveis--;
            return true;
        } else {
            return false;
        }
    }

    public void liberarVaga(Passageiro passageiro) {
        if (passageiros.remove(passageiro)) {
            vagasDisponiveis++;
        }
    }

    @Override
    public String toString() {
        return "Viagem{" +
                "id='" + id + '\'' +
                ", local=" + local +
                ", motorista=" + motorista.getNome() +
                ", vagasDisponiveis=" + vagasDisponiveis +
                '}';
    }

    public static Viagem criarViagem(Local local, Motorista motorista, int vagasDisponiveis) {
        return new Viagem(local, motorista, vagasDisponiveis);
    }

    public static ArrayList<Viagem> getTodasViagens() {
        return todasViagens;
    }

    public static ArrayList<Viagem> buscarViagensDisponiveis(String partidaDesejada, String destinoDesejado) {
        ArrayList<Viagem> viagensDisponiveis = new ArrayList<>();
        for (Viagem viagem : todasViagens) {
            Local local = viagem.getLocal();
            ArrayList<String> rota = local.getRota();
            if (rota.contains(partidaDesejada) && rota.contains(destinoDesejado)) {
                int indexPartida = rota.indexOf(partidaDesejada);
                int indexDestino = rota.indexOf(destinoDesejado);
                if (indexPartida < indexDestino && viagem.getVagasDisponiveis() > 0) {
                    viagensDisponiveis.add(viagem);
                }
            }
        }
        return viagensDisponiveis;
    }

    public void notificarMotoristaSobreNovaParada(String partidaDesejada, String destinoDesejado, int subindo, int descendo) {
        System.out.println("Notificação para o motorista: Próxima parada: " + partidaDesejada + ", vai subir " + subindo + " pessoa(s) e descer " + descendo + " pessoa(s).");
    }

    public void concluirViagem() {
        System.out.println("Viagem " + id + " concluída.");
        todasViagens.remove(this);
    }
}