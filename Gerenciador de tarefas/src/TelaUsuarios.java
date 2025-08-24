import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TelaUsuarios extends JFrame {

    private UsuarioController uc;
    private DefaultListModel<Usuario> listaModel;

    public TelaUsuarios(UsuarioController uc) {
        this.uc = uc;
        setTitle("Gerenciar Usuários");
        setSize(400, 300);
        setLocationRelativeTo(null);

        JPanel painel = new JPanel(new BorderLayout());

        listaModel = new DefaultListModel<>();
        JList<Usuario> listaUsuarios = new JList<>(listaModel);
        atualizarLista();
        painel.add(new JScrollPane(listaUsuarios), BorderLayout.CENTER);

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
            String email = JOptionPane.showInputDialog(this, "Email:");
            if (nome != null && email != null) {
                int id = uc.listarUsuarios().size() + 1;
                uc.adicionarUsuario(new Usuario(id, nome, email));
                atualizarLista();
            }
        });

        btnEditar.addActionListener(e -> {
            Usuario selecionado = listaUsuarios.getSelectedValue();
            if (selecionado != null) {
                String nome = JOptionPane.showInputDialog(this, "Nome:", selecionado.getNome());
                String email = JOptionPane.showInputDialog(this, "Email:", selecionado.getEmail());
                if (nome != null && email != null) {
                    uc.atualizarUsuario(selecionado.getId(), nome, email);
                    atualizarLista();
                }
            }
        });

        btnRemover.addActionListener(e -> {
            Usuario selecionado = listaUsuarios.getSelectedValue();
            if (selecionado != null) {
                uc.removerUsuario(selecionado.getId());
                atualizarLista();
            }
        });
    }

    private void atualizarLista() {
        listaModel.clear();
        for (Usuario u : uc.listarUsuarios()) {
            listaModel.addElement(u);
        }
    }
}