package Model.entities.dao.impl;

import Model.entities.Department;
import Model.entities.Seller;
import Model.entities.dao.DaoFactory;
import Model.entities.dao.SellerDao;
import db.DB;
import db.DbExeptions;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public class SellerDaoJDBC implements SellerDao {

    private Connection connection;

    public SellerDaoJDBC(Connection connection) {
        this.connection = connection;
    }


    public SellerDaoJDBC() {



    }

    @Override
    public void insert(Seller obj) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Seller seller = new Seller();
        try {
            statement = connection.prepareStatement(
                    "INSERT INTO seller\n" +
                            "(Name, Email, BirthDate, BaseSalary, DepartmentId)\n" +
                            "VALUES\n" +
                            "(?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );

            statement.setString(1, obj.getName());
            statement.setString(2, obj.getEmail());
            statement.setDate(3, new java.sql.Date(obj.getBirthday().getTime()));
            statement.setDouble(4, obj.getBaseSalary());
            statement.setInt(5, obj.getDepartment().getId());

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0){
                resultSet = statement.getGeneratedKeys();
                if (resultSet.next()){
                    int id = resultSet.getInt(1);
                    obj.setId(id);
                }
            }


        } catch (SQLException e) {
            throw new DbExeptions(e.getMessage());
        }
        finally {
            DB.closeStatement(statement);
            DB.closeResultset(resultSet);
        }
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
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(
                    "SELECT seller.*,department.Name as DepName "+
                    "FROM seller INNER JOIN department "+
                    "ON seller.DepartmentId = department.Id "+
                    "ORDER BY Name "
            );
            resultSet = statement.executeQuery();

            List<Seller> list = new ArrayList<>();

            Map<Integer, Department> map = new HashMap<>();

            while (resultSet.next()){
                Department department = map.get(resultSet.getInt("DepartmentId"));

                if (department == null){
                    department = instantiateDepartmant(resultSet);
                    map.put(resultSet.getInt("DepartmentId"), department);
                }
                Seller obj = instantiateSeller(resultSet, department);
                list.add(obj);
            }
            return list;
        }
        catch (SQLException e){
            throw new DbExeptions(e.getMessage());
        }
        finally {
            DB.closeStatement(statement);
            DB.closeResultset(resultSet);
        }


    }

    @Override
    public List<Seller> findByDepartment(Department department) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement(
                    "SELECT seller.*,department.Name as DepName "+
                    "FROM seller INNER JOIN department "+
                    "ON seller.DepartmentId = department.Id "+
                    "WHERE DepartmentId = ? "+
                    "ORDER BY Name");

            statement.setInt(1, department.getId());
            resultSet = statement.executeQuery();

// list e while pois tem mais de um resultado:

            List<Seller> list = new ArrayList<>();
            // cria um map vazio para depois guardar qualquer departamento que instanciar
            Map<Integer, Department> map = new HashMap<>();


            while (resultSet.next()){

                department = map.get(resultSet.getInt("DepartmentId"));
                // se o department que passou no DepartmentId for nulo significa que ele nao existe ainda, faz isso para nao se instanciar o mesmo departamento varias vezes
                if (department == null) {
                    department = instantiateDepartmant(resultSet);
                    map.put(resultSet.getInt("DepartmentId"), department);
                }
                Seller obj = instantiateSeller(resultSet, department);
                list.add(obj);
            }
            return list;

        }
        catch (SQLException e){
            throw  new DbExeptions(e.getMessage());
        }
        finally {
            DB.closeStatement(statement);
            DB.closeResultset(resultSet);
        }


    }


}
