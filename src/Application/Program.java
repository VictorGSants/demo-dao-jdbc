package Application;

import Model.entities.Department;
import Model.entities.Seller;
import Model.entities.dao.DaoFactory;
import Model.entities.dao.SellerDao;

import java.util.Date;
import java.util.List;


public class Program {
    public static void main(String[] args) {

        SellerDao sellerDao = DaoFactory.createSellerDao();

        System.out.println("=== TESTE 1: SELLER FINDBYID ===");
        System.out.println(sellerDao.findById(4));
        System.out.println("");
        System.out.println("=== TESTE 2: SELLER FINDBYDEPARTMENTID ===");
        List<Seller> list = sellerDao.findByDepartment(new Department(2, null));

        for (Seller seller : list){
            System.out.println(seller);
        }


    }
}