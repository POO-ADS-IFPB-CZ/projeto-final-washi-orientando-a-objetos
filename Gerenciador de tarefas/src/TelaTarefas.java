import javax.swing.*;
import java.awt.*;

public class TelaTarefas extends JFrame {

    private TarefaController tc;
    private UsuarioController uc;
    private CategoriaController cc;
    private DefaultListModel<Tarefa> listaModel;

    public TelaTarefas(TarefaController tc, UsuarioController uc, CategoriaController cc) {
        this.tc = tc;
        this.uc = uc;
        this.cc = cc;

        setTitle("Gerenciar Tarefas");
        setSize(500, 350);
        setLocationRelativeTo(null);

        JPanel painel = new JPanel(new BorderLayout());

        listaModel = new DefaultListModel<>();
        JList<Tarefa> listaTarefas = new JList<>(listaModel);
        atualizarLista();
        painel.add(new JScrollPane(listaTarefas), BorderLayout.CENTER);

        JPanel botoes = new JPanel(new GridLayout(1, 3, 5, 5));
        JButton btnAdd = new JButton("Adicionar");
        JButton btnEditar = new JButton("Editar");
        JButton btnRemover = new JButton("Remover");

        botoes.add(btnAdd);
        botoes.add(btnEditar);
        botoes.add(btnRemover);
        painel.add(botoes, BorderLayout.SOUTH);

        add(painel);

        btnAdd.addActionListener(e -> {
            String titulo = JOptionPane.showInputDialog(this, "Título:");
            String descricao = JOptionPane.showInputDialog(this, "Descrição:");
            String prioridade = JOptionPane.showInputDialog(this, "Prioridade:");

            Usuario[] usuarios = uc.listarUsuarios().toArray(new Usuario[0]);
            Categoria[] categorias = cc.listarCategorias().toArray(new Categoria[0]);

            Usuario usuario = (Usuario) JOptionPane.showInputDialog(this, "Escolha o usuário:",
                    "Usuário", JOptionPane.PLAIN_MESSAGE, null, usuarios, usuarios.length > 0 ? usuarios[0] : null);
            Categoria categoria = (Categoria) JOptionPane.showInputDialog(this, "Escolha a categoria:",
                    "Categoria", JOptionPane.PLAIN_MESSAGE, null, categorias, categorias.length > 0 ? categorias[0] : null);

            if (titulo != null && descricao != null && prioridade != null && usuario != null && categoria != null) {
                int id = tc.listarTarefas().size() + 1;
                tc.adicionarTarefa(new Tarefa(id, titulo, descricao, prioridade, usuario, categoria));
                atualizarLista();
            }
        });

        btnEditar.addActionListener(e -> {
            Tarefa selecionada = listaTarefas.getSelectedValue();
            if (selecionada != null) {
                String titulo = JOptionPane.showInputDialog(this, "Título:", selecionada.getTitulo());
                String descricao = JOptionPane.showInputDialog(this, "Descrição:", selecionada.getDescricao());
                String prioridade = JOptionPane.showInputDialog(this, "Prioridade:", selecionada.getPrioridade());

                if (titulo != null && descricao != null && prioridade != null) {
                    tc.atualizarTarefa(selecionada.getId(), titulo, descricao, prioridade, selecionada.isConcluida());
                    atualizarLista();
                }
            }
        });

        btnRemover.addActionListener(e -> {
            Tarefa selecionada = listaTarefas.getSelectedValue();
            if (selecionada != null) {
                tc.removerTarefa(selecionada.getId());
                atualizarLista();
            }
        });
    }

    private void atualizarLista() {
        listaModel.clear();
        for (Tarefa t : tc.listarTarefas()) {
            listaModel.addElement(t);
        }
    }
}