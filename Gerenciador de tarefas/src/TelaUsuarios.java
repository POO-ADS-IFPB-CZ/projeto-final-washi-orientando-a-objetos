import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TelaUsuarios extends JFrame {
    private final UsuarioController uc;
    private JTable tabela;
    private DefaultTableModel modelo;
    private JTextField txtNome;
    private JTextField txtEmail;

    public TelaUsuarios(UsuarioController uc) {
        this.uc = uc;

        setTitle("Gerenciar Usuários");
        setSize(600, 420);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // form
        JPanel topo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topo.add(new JLabel("Nome:"));
        txtNome = new JTextField(15);
        topo.add(txtNome);
        topo.add(new JLabel("Email:"));
        txtEmail = new JTextField(18);
        topo.add(txtEmail);

        JButton btnAdicionar = new JButton("Adicionar");
        JButton btnEditar = new JButton("Editar");
        JButton btnRemover = new JButton("Remover");
        topo.add(btnAdicionar);
        topo.add(btnEditar);
        topo.add(btnRemover);

        add(topo, BorderLayout.NORTH);

        // tabela
        modelo = new DefaultTableModel(new Object[]{"ID", "Nome", "Email"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
            @Override public Class<?> getColumnClass(int c) { return c == 0 ? Integer.class : String.class; }
        };
        tabela = new JTable(modelo);
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        // eventos
        btnAdicionar.addActionListener(e -> {
            String nome = txtNome.getText().trim();
            String email = txtEmail.getText().trim();
            if (nome.isEmpty() || email.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha nome e email.");
                return;
            }
            int id = proximoIdUsuario();
            Usuario u = new Usuario(id, nome, email);
            uc.adicionarUsuario(u);
            limparCampos();
            atualizarTabela();
        });

        btnEditar.addActionListener(e -> {
            int row = tabela.getSelectedRow();
            if (row < 0) { JOptionPane.showMessageDialog(this, "Selecione um usuário."); return; }
            int id = (Integer) modelo.getValueAt(row, 0);
            String novoNome = JOptionPane.showInputDialog(this, "Nome:", modelo.getValueAt(row,1));
            if (novoNome == null) return;
            String novoEmail = JOptionPane.showInputDialog(this, "Email:", modelToString(modelo.getValueAt(row,2)));
            if (novoEmail == null) return;
            uc.atualizarUsuario(id, novoNome.trim(), novoEmail.trim());
            atualizarTabela();
        });

        btnRemover.addActionListener(e -> {
            int row = tabela.getSelectedRow();
            if (row < 0) { JOptionPane.showMessageDialog(this, "Selecione um usuário."); return; }
            int id = (Integer) modelo.getValueAt(row, 0);
            uc.removerUsuario(id);
            atualizarTabela();
        });

        atualizarTabela();
    }

    private String modelToString(Object o){ return o==null? "": o.toString(); }

    private int proximoIdUsuario() {
        int max = 0;
        for (Usuario u : uc.listarUsuarios()) if (u.getId() > max) max = u.getId();
        return max + 1;
    }

    private void limparCampos() { txtNome.setText(""); txtEmail.setText(""); }

    private void atualizarTabela() {
        modelo.setRowCount(0);
        for (Usuario u : uc.listarUsuarios()) {
            modelo.addRow(new Object[]{u.getId(), u.getNome(), u.getEmail()});
        }
    }
}