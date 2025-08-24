import javax.swing.*;
import java.awt.*;

public class TelaCategorias extends JFrame {

    private CategoriaController cc;
    private DefaultListModel<Categoria> listaModel;

    public TelaCategorias(CategoriaController cc) {
        this.cc = cc;
        setTitle("Gerenciar Categorias");
        setSize(400, 300);
        setLocationRelativeTo(null);

        JPanel painel = new JPanel(new BorderLayout());

        listaModel = new DefaultListModel<>();
        JList<Categoria> listaCategorias = new JList<>(listaModel);
        atualizarLista();
        painel.add(new JScrollPane(listaCategorias), BorderLayout.CENTER);

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
            String nome = JOptionPane.showInputDialog(this, "Nome:");
            String cor = JOptionPane.showInputDialog(this, "Cor:");
            if (nome != null && cor != null) {
                int id = cc.listarCategorias().size() + 1;
                cc.adicionarCategoria(new Categoria(id, nome, cor));
                atualizarLista();
            }
        });

        btnEditar.addActionListener(e -> {
            Categoria selecionado = listaCategorias.getSelectedValue();
            if (selecionado != null) {
                String nome = JOptionPane.showInputDialog(this, "Nome:", selecionado.getNome());
                String cor = JOptionPane.showInputDialog(this, "Cor:", selecionado.getCor());
                if (nome != null && cor != null) {
                    cc.atualizarCategoria(selecionado.getId(), nome, cor);
                    atualizarLista();
                }
            }
        });

        btnRemover.addActionListener(e -> {
            Categoria selecionado = listaCategorias.getSelectedValue();
            if (selecionado != null) {
                cc.removerCategoria(selecionado.getId());
                atualizarLista();
            }
        });
    }

    private void atualizarLista() {
        listaModel.clear();
        for (Categoria c : cc.listarCategorias()) {
            listaModel.addElement(c);
        }
    }
}