import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class ProdutosDAO {
    
    Connection conn;
    PreparedStatement prep;
    ResultSet resultset;
    
    public void cadastrarProduto(ProdutosDTO produto) {
        conn = new conectaDAO().connectDB();
        String sql = "INSERT INTO produtos (nome, valor, status) VALUES (?, ?, ?)";
        
        try {
            prep = conn.prepareStatement(sql);
            prep.setString(1, produto.getNome());
            prep.setInt(2, produto.getValor());
            prep.setString(3, produto.getStatus());
            
            prep.executeUpdate();
            JOptionPane.showMessageDialog(null, "Cadastro realizado com sucesso!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar: " + e.getMessage());
        }
    }
    
    public ArrayList<ProdutosDTO> listarProdutos() {
        conn = new conectaDAO().connectDB();
        String sql = "SELECT * FROM produtos";
        ArrayList<ProdutosDTO> lista = new ArrayList<>();
        
        try {
            prep = conn.prepareStatement(sql);
            resultset = prep.executeQuery();
            
            while (resultset.next()) {
                ProdutosDTO produto = new ProdutosDTO();
                produto.setId(resultset.getInt("id"));
                produto.setNome(resultset.getString("nome"));
                produto.setValor(resultset.getInt("valor"));
                produto.setStatus(resultset.getString("status"));
                lista.add(produto);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar: " + e.getMessage());
        }
        return lista;
    }

    public void venderProduto(int id) {
        String sql = "UPDATE produtos SET status = ? WHERE id = ?";
        conn = new conectaDAO().connectDB();
        
        try {
            prep = conn.prepareStatement(sql);
            prep.setString(1, "Vendido");
            prep.setInt(2, id);
            
            prep.executeUpdate();
            JOptionPane.showMessageDialog(null, "Status do produto " + id + " atualizado para Vendido!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao vender produto: " + e.getMessage());
        }
    }

    public ArrayList<ProdutosDTO> listarProdutosVendidos() {
        conn = new conectaDAO().connectDB();
        String sql = "SELECT * FROM produtos WHERE status = 'Vendido'";
        ArrayList<ProdutosDTO> lista = new ArrayList<>();
        
        try {
            prep = conn.prepareStatement(sql);
            resultset = prep.executeQuery();
            
            while (resultset.next()) {
                ProdutosDTO produto = new ProdutosDTO();
                produto.setId(resultset.getInt("id"));
                produto.setNome(resultset.getString("nome"));
                produto.setValor(resultset.getInt("valor"));
                produto.setStatus(resultset.getString("status"));
                lista.add(produto);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar vendidos: " + e.getMessage());
        }
        return lista;
    }
}