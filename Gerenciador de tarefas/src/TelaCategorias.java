import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TelaCategorias extends JFrame {
    private final CategoriaController cc;
    private JTable tabela;
    private DefaultTableModel modelo;
    private JTextField txtNome;
    private JTextField txtCor;

    public TelaCategorias(CategoriaController cc) {
        this.cc = cc;

        setTitle("Gerenciar Categorias");
        setSize(600, 420);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel topo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topo.add(new JLabel("Nome:"));
        txtNome = new JTextField(16);
        topo.add(txtNome);
        topo.add(new JLabel("Cor:"));
        txtCor = new JTextField(10);
        topo.add(txtCor);

        JButton btnAdicionar = new JButton("Adicionar");
        JButton btnEditar = new JButton("Editar");
        JButton btnRemover = new JButton("Remover");
        topo.add(btnAdicionar);
        topo.add(btnEditar);
        topo.add(btnRemover);

        add(topo, BorderLayout.NORTH);

        modelo = new DefaultTableModel(new Object[]{"ID", "Nome", "Cor"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
            @Override public Class<?> getColumnClass(int c) { return c == 0 ? Integer.class : String.class; }
        };
        tabela = new JTable(modelo);
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        btnAdicionar.addActionListener(e -> {
            String nome = txtNome.getText().trim();
            String cor = txtCor.getText().trim();
            if (nome.isEmpty() || cor.isEmpty()) { JOptionPane.showMessageDialog(this, "Preencha nome e cor."); return; }
            int id = proximoIdCategoria();
            Categoria cat = new Categoria(id, nome, cor);
            cc.adicionarCategoria(cat);
            limparCampos();
            atualizarTabela();
        });

        btnEditar.addActionListener(e -> {
            int row = tabela.getSelectedRow();
            if (row < 0) { JOptionPane.showMessageDialog(this, "Selecione uma categoria."); return; }
            int id = (Integer) modelo.getValueAt(row, 0);
            String novoNome = JOptionPane.showInputDialog(this, "Nome:", modelo.getValueAt(row,1));
            if (novoNome == null) return;
            String novaCor = JOptionPane.showInputDialog(this, "Cor:", modelToString(modelo.getValueAt(row,2)));
            if (novaCor == null) return;
            cc.atualizarCategoria(id, novoNome.trim(), novaCor.trim());
            atualizarTabela();
        });

        btnRemover.addActionListener(e -> {
            int row = tabela.getSelectedRow();
            if (row < 0) { JOptionPane.showMessageDialog(this, "Selecione uma categoria."); return; }
            int id = (Integer) modelo.getValueAt(row, 0);
            cc.removerCategoria(id);
            atualizarTabela();
        });

        atualizarTabela();
    }

    private String modelToString(Object o){ return o==null? "": o.toString(); }

    private int proximoIdCategoria() {
        int max = 0;
        for (Categoria c : cc.listarCategorias()) if (c.getId() > max) max = c.getId();
        return max + 1;
    }

    private void limparCampos() { txtNome.setText(""); txtCor.setText(""); }

    private void atualizarTabela() {
        modelo.setRowCount(0);
        for (Categoria c : cc.listarCategorias()) {
            modelo.addRow(new Object[]{c.getId(), c.getNome(), c.getCor()});
        }
    }
}