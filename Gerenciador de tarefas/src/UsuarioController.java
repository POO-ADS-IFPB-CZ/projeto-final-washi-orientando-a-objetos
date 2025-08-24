import java.util.ArrayList;
import java.util.List;

public class UsuarioController {
    private List<Usuario> usuarios;

    public UsuarioController() {
        this.usuarios = new ArrayList<>();
    }

    // Create
    public void adicionarUsuario(Usuario u) {
        usuarios.add(u);
    }

    // Read
    public List<Usuario> listarUsuarios() {
        return usuarios;
    }

    // Update
    public boolean atualizarUsuario(int id, String nome, String email) {
        for (Usuario u : usuarios) {
            if (u.getId() == id) {
                u.setNome(nome);
                u.setEmail(email);
                return true;
            }
        }
        return false; // usuário não encontrado
    }

    // Delete
    public boolean removerUsuario(int id) {
        return usuarios.removeIf(u -> u.getId() == id);
    }
}