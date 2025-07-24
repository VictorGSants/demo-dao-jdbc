package Application;

import Model.entities.Department;
import Model.entities.Seller;
import Model.entities.dao.DaoFactory;
import Model.entities.dao.SellerDao;

import java.util.Date;


public class Program {
    public static void main(String[] args) {

        SellerDao sellerDao = DaoFactory.createSellerDao();

        System.out.println("=== TESTE 1: SELLER FINDBYID ===");
        System.out.println("Testando git");
        System.out.println(sellerDao.findById(4));

    }
}