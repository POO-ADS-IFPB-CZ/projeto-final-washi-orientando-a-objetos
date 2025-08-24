import java.util.ArrayList;
import java.util.List;

public class CategoriaController {
    private List<Categoria> categorias;

    public CategoriaController() {
        categorias = new ArrayList<>();
    }

    public void adicionarCategoria(Categoria c) {
        categorias.add(c);
    }

    public List<Categoria> listarCategorias() {
        return categorias;
    }

    public boolean atualizarCategoria(int id, String nome, String cor) {
        for (Categoria c : categorias) {
            if (c.getId() == id) {
                c.setNome(nome);
                c.setCor(cor);
                return true;
            }
        }
        return false;
    }

    public boolean removerCategoria(int id) {
        return categorias.removeIf(c -> c.getId() == id);
    }
}