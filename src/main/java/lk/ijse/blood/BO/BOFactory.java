package lk.ijse.blood.bo;

import lk.ijse.blood.bo.Custom.Impl.*;



public class BOFactory {
    private static BOFactory boFactory;
    private BOFactory(){

    }

    public static BOFactory getInstance(){
        return (boFactory == null) ? boFactory = new BOFactory() : boFactory;
    }

    public enum BOTypes{
        ADMIN,ATTENDANCE,BLOODINVENTORY,DONATION,DONOR,EMPLOYEE,INVENTORY,NEEDER,NEEDERREQUEST,ORDERDETAILS,REQUESTDETAILS,SALARY,SUPPLIER,SUPPLIERORDER
    }

    public SuperBO getBO(BOTypes boTypes){
        switch (boTypes){
            case ADMIN:
                return new AdminBOImpl();
            case ATTENDANCE:
                return new AttendanceBOImpl();
            case BLOODINVENTORY:
                return new BloodInventoryBOImpl();
            case DONATION:
                return new DonationBOImpl();
            case DONOR:
                return new DonorBOImpl();
            case EMPLOYEE:
                return new EmployeeBOImpl();
            case INVENTORY:
                return new InventoryBOImpl();
            case NEEDER:
                return new NeederBOImpl();
            case NEEDERREQUEST:
                return new NeederRequestBOImpl();
            case ORDERDETAILS:
                return new OrderDetailsBOImpl();
            case REQUESTDETAILS:
                return new RequestDetailsBOImpl();
            case SALARY:
                return new SalaryBOImpl();
            case SUPPLIER:
                return new SupplierBOImpl();
            case SUPPLIERORDER:
                return new SupplierOrderBOImpl();
            default:
                return null;
        }
    }
}
