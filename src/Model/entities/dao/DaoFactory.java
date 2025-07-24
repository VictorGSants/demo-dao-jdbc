package Model.entities.dao;

import Model.entities.dao.impl.SellerDaoJDBC;
import db.DB;

public class DaoFactory {
    // classe ("FABRICA") responsavel por instanciar os DAOS



    public static SellerDao createSellerDao(){
        return new SellerDaoJDBC(DB.getCon());
    }
}
