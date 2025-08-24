import java.util.ArrayList;
import java.util.List;

public class TarefaController {
    private List<Tarefa> tarefas;

    public TarefaController() {
        tarefas = new ArrayList<>();
    }

    public void adicionarTarefa(Tarefa t) {
        tarefas.add(t);
    }

    public List<Tarefa> listarTarefas() {
        return tarefas;
    }

    public boolean atualizarTarefa(int id, String titulo, String descricao, String prioridade, boolean concluida) {
        for (Tarefa t : tarefas) {
            if (t.getId() == id) {
                t.setTitulo(titulo);
                t.setDescricao(descricao);
                t.setPrioridade(prioridade);
                t.setConcluida(concluida);
                return true;
            }
        }
        return false;
    }

    public boolean removerTarefa(int id) {
        return tarefas.removeIf(t -> t.getId() == id);
    }
}