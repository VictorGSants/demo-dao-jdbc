package Model.entities.dao.impl;

import Model.entities.Department;
import Model.entities.Seller;
import Model.entities.dao.DaoFactory;
import Model.entities.dao.SellerDao;
import db.DB;
import db.DbExeptions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class SellerDaoJDBC implements SellerDao {

    private Connection connection;

    public SellerDaoJDBC(Connection connection) {
        this.connection = connection;
    }


    public SellerDaoJDBC() {



    }

    @Override
    public void insert(Seller obj) {

    }

    @Override
    public void update(Seller obj) {

    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public Seller findById(Integer id) {

        // para buscar o vendedor "Seller" pelo ID
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement(
                            "SELECT seller.*,department.Name as DepName\n" +
                            "FROM seller INNER JOIN department\n" +
                            "ON seller.DepartmentId = department.Id\n" +
                            "WHERE seller.Id = ?");

            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            // Se o "resultSet" retornar 1 (Verdadeiro) significa que encontrou um vendedor com este id na tabela:
           if (resultSet.next()){
                // intanciamos um departamento e setamos os valores dele ele

               Department department = instantiateDepartmant(resultSet);

               Seller seller = instantiateSeller(resultSet, department);

               // para dizer que esta ligado com department:

               seller.setDepartment(department);

               return seller;

           }// Se o "resultSet" retornar 0 (Nulo) significa que nao encontrou nenhum vendedor com este id:
           return null;
        }
        catch (SQLException e){
            throw  new DbExeptions(e.getMessage());
        }
        finally {
            DB.closeStatement(statement);
            DB.closeResultset(resultSet);
        }
    }

    private Seller instantiateSeller(ResultSet resultSet, Department department) throws SQLException{
        Seller seller = new Seller();
        seller.setName(resultSet.getString("Name"));
        seller.setId(resultSet.getInt("Id"));
        seller.setBaseSalary(resultSet.getDouble("BaseSalary"));
        seller.setBirthday(resultSet.getDate("BirthDate"));
        seller.setEmail(resultSet.getString("email"));
        return seller;

    }

    private Department instantiateDepartmant(ResultSet resultSet) throws SQLException {
        Department department = new Department();
        department.setId(resultSet.getInt("DepartmentId"));
        department.setName(resultSet.getString("DepName"));
        return department;
    }

    @Override
    public List<Seller> findAll() {
        return null;
    }


}
