package Model.entities.dao.impl;

import Model.entities.Department;
import Model.entities.dao.DepartmentDao;
import db.DB;
import db.DbExeptions;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDaoJDBC implements DepartmentDao {
    private Connection connection;

    public DepartmentDaoJDBC(Connection connection) {
        this.connection = connection;
    }


    @Override
    public void insert(Department obj) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(
                    "INSERT INTO department " +
                            "(Name) " +
                            "VALUES " +
                            "(?)",
                    Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, obj.getName());

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                resultSet = statement.getGeneratedKeys();
                if (resultSet.next()) {
                    int id = resultSet.getInt(1);
                    obj.setId(id);
                }
            }
            else{
                throw new DbExeptions("Unexpected error! No rows affected!");
            }


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
    public void update(Department obj) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(
                    "UPDATE department " +
                            "SET Name = ? " +
                            "WHERE Id = ?");

            statement.setString(1, obj.getName());
            statement.setInt(2, obj.getId());

           statement.executeUpdate();

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
    public void deleteById(Integer id) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(
                    "DELETE FROM department WHERE Id = ?");

            statement.setString(1, String.valueOf(id));

            statement.executeUpdate();

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
    public Department findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = connection.prepareStatement(
                    "SELECT * FROM department WHERE Id = ?");
            st.setInt(1, id);
            rs = st.executeQuery();
            if (rs.next()) {
                Department obj = new Department();
                obj.setId(rs.getInt("Id"));
                obj.setName(rs.getString("Name"));
                return obj;
            }
            return null;
        }
        catch (SQLException e) {
            throw new DbExeptions(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
            DB.closeResultset(rs);
        }
    }


    @Override
    public List<Department> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = connection.prepareStatement(
                    "SELECT * FROM department ORDER BY Name");
            rs = st.executeQuery();

            List<Department> list = new ArrayList<>();

            while (rs.next()) {
                Department obj = new Department();
                obj.setId(rs.getInt("Id"));
                obj.setName(rs.getString("Name"));
                list.add(obj);
            }
            return list;
        }
        catch (SQLException e) {
            throw new DbExeptions(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
            DB.closeResultset(rs);
        }
    }
}
