package Application;

import Model.entities.Department;
import Model.entities.Seller;
import Model.entities.dao.DaoFactory;
import Model.entities.dao.SellerDao;

import java.util.List;


public class Program {
    public static void main(String[] args) {

        SellerDao sellerDao = DaoFactory.createSellerDao();

        System.out.println("=== TESTE 1: SELLER FINDBYID ===");
        System.out.println(sellerDao.findById(4));
        System.out.println("");

        System.out.println("=== TESTE 2: SELLER FINDBYDEPARTMENTID ===");
        Department department = new Department(2, null);
        List<Seller> list = sellerDao.findByDepartment(department);

        for (Seller seller : list){
            System.out.println(seller);
        }

        System.out.println("");

        System.out.println("=== TESTE 3: SELLER FINDAll ===");
        list = sellerDao.findAll();

        for (Seller seller : list){
            System.out.println(seller);
        }


    }
}