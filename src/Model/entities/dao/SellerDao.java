package Model.entities.dao;


import Model.entities.Department;
import Model.entities.Seller;

import java.util.List;

public interface SellerDao {
    // responsavel por inserir no banco de dados
    void insert(Seller obj);
    void update(Seller obj);
    void deleteById(Integer id);
    
    // responsavel por consultar no banco um obj com este id
    Seller findById(Integer id);
    List<Seller> findAll();
    List<Seller> findByDepartment(Department department);

}
