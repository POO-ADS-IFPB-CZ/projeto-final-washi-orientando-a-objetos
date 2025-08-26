import java.util.List;

public class UsuarioController {
    private List<Usuario> usuarios;
    private final String arquivo = "usuarios.dat";

    public UsuarioController() {
        usuarios = ArquivoDAO.carregar(arquivo);
    }

    public void adicionarUsuario(Usuario u) {
        usuarios.add(u);
        ArquivoDAO.salvar(usuarios, arquivo);
    }

    public List<Usuario> listarUsuarios() {
        return usuarios;
    }

    public boolean atualizarUsuario(int id, String nome, String email) {
        for (Usuario u : usuarios) {
            if (u.getId() == id) {
                u.setNome(nome);
                u.setEmail(email);
                ArquivoDAO.salvar(usuarios, arquivo);
                return true;
            }
        }
        return false;
    }

    public boolean removerUsuario(int id) {
        boolean removed = usuarios.removeIf(u -> u.getId() == id);
        if (removed) ArquivoDAO.salvar(usuarios, arquivo);
        return removed;
    }
}