package lk.ijse.blood.dao;

import lk.ijse.blood.dao.Custom.Impl.*;


public class DAOFactory {
    private static DAOFactory daoFactory;
    private DAOFactory(){}

    public static DAOFactory getDaoFactory(){
        return daoFactory==null? new DAOFactory():daoFactory;
    }

    public enum DAOTypes{
        ADMIN,ATTENDANCE,BLOODINVENTORY,DONATION,DONOR,EMPLOYEE,INVENTORY,NEEDER,NEEDERREQUEST,ORDERDETAILS,REQUESTDETAILS,SALARY,SUPPLIER,SUPPLIERORDER

    }

    public SuperDAO getDAO(DAOTypes daoTypes){
        switch (daoTypes){
            case ADMIN:
                return new AdminDAOImpl();
            case ATTENDANCE:
                return new AttendanceDAOImpl();
            case BLOODINVENTORY:
                return new BloodInventoryDAOImpl();
            case DONATION:
                return new DonationDAOImpl();
            case DONOR:
                return new DonorDAOImpl();
            case EMPLOYEE:
                return new EmployeeDAOImpl();
            case INVENTORY:
                return new InventoryDAOImpl();
            case NEEDER:
                return new NeederDAOImpl();
            case NEEDERREQUEST:
                return new NeederReuestDAOImpl();
            case ORDERDETAILS:
                return new OrderDetailsDAOImpl();
            case REQUESTDETAILS:
                return new RequestDetailsDAOImpl();
            case SALARY:
                return new SalaryDAOImpl();
            case SUPPLIER:
                return new SupplierDAOImpl();
            case SUPPLIERORDER:
                return new SupplierOrderDAOImpl();
            default:
                return null;
        }
    }
}
