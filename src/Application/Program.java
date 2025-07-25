package Application;

import Model.entities.Department;
import Model.entities.Seller;
import Model.entities.dao.DaoFactory;
import Model.entities.dao.SellerDao;

import java.util.Date;
import java.util.List;
import java.util.Scanner;


public class Program {
    public static void main(String[] args) {

        SellerDao sellerDao = DaoFactory.createSellerDao();
        Scanner sc = new Scanner(System.in);

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

        System.out.println("");


        System.out.println("=== TESTE 4: INSERT ===");

        System.out.print("Insert of Name: ");
        sc.nextLine(); // limpa o buffer antes de ler a próxima linha
        String name = sc.nextLine();

        System.out.print("Insert of Email: ");
        String email = sc.nextLine();

        System.out.print("Insert of baseSalary: ");
        double baseSalary = sc.nextDouble();

        System.out.print("Insert of DepartmentId: ");
        int id = sc.nextInt();


        Seller seller = new Seller(null, name, email, new Date(), baseSalary, department);
        sellerDao.insert(seller);
        System.out.println("Inserted new id = " + seller.getId());

        System.out.println("");


        System.out.println("=== TESTE 5: UPDATE ===");
        System.out.println("Insira o id do funcionario que voce deseja mudar o salario; ");
        int idFuncionario = sc.nextInt();
        System.out.println("Insira o novo salario desse funcionario: ");
        double newSalary = sc.nextDouble();
        seller.setBaseSalary(newSalary);
        seller = sellerDao.findById(idFuncionario);
        sellerDao.update(seller);






        sc.close();


    }
}