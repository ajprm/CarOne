import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Usuario {
    /*Atributos; A classe usuario é uma classe base para as classes "Motorista" e "Passageiro" que herdam seus atributos*/
    private String nome;
    private String username;
    private String email;
    private String telefone;
    private String endereco;
    private String senha;
    /* Apesar de ter outras formas de tratar isso, trabalhar com boolean tem mais nexo do que com String. Então, deixem assim.*/
    private boolean motorista; 
    private boolean passageiro;
    private static List<Usuario> usuarios = new ArrayList<>();

    /*Construtor */
    public Usuario(String nome, String email, String telefone, String endereco, String senha, boolean motorista, boolean passageiro) {
        this.nome = nome;
        this.username = generateRandomUsername();  
        this.email = email;
        this.telefone = telefone;
        this.endereco = endereco;
        this.senha = senha;
        this.motorista = motorista;
        this.passageiro = passageiro;
        usuarios.add(this);
    }

    public static void adicionarUsuario(Usuario usuario) {
        if (usuario != null && !usuarios.contains(usuario)) {
            usuarios.add(usuario);
        }
    }
    /*getters e setters */
    public static List<Usuario> getUsuarios() {
        return usuarios;
    }

    public String getNome() {
        return nome;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public String getSenha() {
        return senha;
    }

    public boolean isMotorista() {
        return motorista;
    }

    public boolean isPassageiro() {
        return passageiro;
    }
    /* Há comunicação entre ArrayLists. Demorou, mas funcionou by Anna */
    public static List<Usuario> getMotoristas() {
        List<Usuario> motoristas = new ArrayList<>();
        for (Usuario usuario : usuarios) {
            if (usuario.isMotorista()) {
                motoristas.add(usuario);
            }
        }
        return motoristas;
    }

    public static List<Usuario> getPassageiros() {
        List<Usuario> passageiros = new ArrayList<>();
        for (Usuario usuario : usuarios) {
            if (usuario.isPassageiro()) {
                passageiros.add(usuario);
            }
        }
        return passageiros;
    }

    public static boolean validarLogin(String username, String senha) {
        for (Usuario usuario : usuarios) {
            if (usuario.username.equals(username) && usuario.senha.equals(senha)) {
                return true;
            }
        }
        return false;
    }
    /*Criação de username deixa de ser uma responsabilidade do usuário externo inserir por Scanner 
    e passa a ser gerado automaticamente. */
    private String generateRandomUsername() {
        String caracteresValidos = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder(8); /*gera username de 8 caracteres */
        for (int i = 0; i < 8; i++) {
            int indiceAleatorio = random.nextInt(caracteresValidos.length());
            sb.append(caracteresValidos.charAt(indiceAleatorio));
        }
        return sb.toString();
    }
}