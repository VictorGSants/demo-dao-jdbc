package Model.entities.dao;

import Model.entities.Department;

import java.util.List;

public interface DepartmentDao {

    // responsavel por inserir no banco de dados
    void insert(Department obj);
    void update(Department obj);
    void deleteById(Integer id);
    // responsavel por consultar no banco um obj com este id
    Department findById(Integer id);
    List<Department> findAll();


}
