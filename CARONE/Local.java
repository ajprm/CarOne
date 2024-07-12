import java.util.ArrayList;

public class Local {
    private String origem;
    private String destino;
    private ArrayList<String> rota;

    public Local(String origem, String destino, ArrayList<String> rota) {
        this.origem = origem;
        this.destino = destino;
        this.rota = rota;
    }

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public ArrayList<String> getRota() {
        return rota;
    }

    public void adicionarParada(String parada) {
        rota.add(parada);
    }

    public void editarParada(int index, String novaParada) {
        if (index >= 0 && index < rota.size()) {
            rota.set(index, novaParada);
        } else {
            System.out.println("Índice inválido.");
        }
    }

    @Override
    public String toString() {
        return "Local{" +
                "origem='" + origem + '\'' +
                ", destino='" + destino + '\'' +
                ", rota=" + rota +
                '}';
    }
}