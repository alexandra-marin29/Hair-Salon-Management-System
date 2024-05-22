import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;

public class DbManager {
    private static String connectionUrl = "jdbc:sqlserver://DESKTOP-BF75L3H;databaseName=dbHairSalon;user=sa1;password=Admin123!;encrypt=false;";
    private static Connection connection = null;

    private static void createConn() {
        try {
            connection = DriverManager.getConnection(connectionUrl);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static Connection getConn() {
        if (connection == null) {
            createConn();
        }

        return connection;
    }

    static public Staff login(String username, String password) {
        try {
            PreparedStatement pstmt = DbManager.getConn().prepareStatement("{call dbo.loginStaff(?, ?)}");
            pstmt.setString(1, username);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int idRole = rs.getInt("id_role");
                Staff loggedStaff = new Staff(
                        rs.getInt("id_staff"),
                        rs.getString("first_Name"),
                        rs.getString("last_name"),
                        rs.getString("tel"),
                        rs.getString("info"),
                        rs.getBoolean("active"),
                        rs.getString("username"),
                        idRole
                );

                return loggedStaff;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    static public ArrayList<Appointment> getAppointmentsIntersectTimeframe(Appointment appointment) {
        ArrayList<Appointment> arr = new ArrayList<>();
        try {
            PreparedStatement pstmt = DbManager.getConn().prepareStatement("{call dbo.getAppointmentsIntersectTimeframe(?,?,?,?,?,?)}");
            pstmt.setInt(1, appointment.getIdStaff());
            pstmt.setDate(2, appointment.getDate_app());
            pstmt.setInt(3, appointment.getStartHour());
            pstmt.setInt(4, appointment.getStartMin());
            pstmt.setInt(5, appointment.getEndHour());
            pstmt.setInt(6, appointment.getEndMin());

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Appointment appt = new Appointment(
                        rs.getInt("id_appointment"),
                        rs.getInt("id_customer"),
                        rs.getInt("id_staff"),
                        rs.getInt("id_service"),
                        rs.getDate("date_app"),
                        rs.getInt("start_hour"),
                        rs.getInt("start_min"),
                        rs.getInt("end_hour"),
                        rs.getInt("end_min"),
                        rs.getString("info"),
                        rs.getDouble("price")
                );
                arr.add(appt);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return arr;
    }

    static public boolean insertOrUpdateAppointment(Appointment appointment) {
        try {
            PreparedStatement pstmt = DbManager.getConn().prepareStatement("{call dbo.addOrUpdateAppointment(?,?,?,?,?,?,?,?,?,?,?,?)}");
            pstmt.setInt(1, appointment.getIdAppointment());
            pstmt.setInt(2, appointment.getIdCustomer());
            pstmt.setInt(3, appointment.getIdStaff());
            pstmt.setInt(4, appointment.getIdService());
            pstmt.setDate(5, appointment.getDate_app());
            pstmt.setInt(6, appointment.getStartHour());
            pstmt.setInt(7, appointment.getStartMin());
            pstmt.setInt(8, appointment.getEndHour());
            pstmt.setInt(9, appointment.getEndMin());
            pstmt.setString(10, appointment.getInfo());
            pstmt.setDouble(11, appointment.getPrice());
            pstmt.setBoolean(12, appointment.getActive());

            int noRows = pstmt.executeUpdate();
            if (noRows == 0) {
                System.out.println("No rows affected. Appointment not added or updated.");
                return false;
            } else {
                System.out.println("Appointment added/updated successfully.");
                return true;
            }
        } catch (SQLException ex) {
            System.err.println("SQL Exception: " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }

    static public boolean insertOrUpdateCustomer(Customer customer) {
        try {
            PreparedStatement pstmt = DbManager.getConn().prepareStatement("{call dbo.addOrUpdateCustomer(?,?,?,?,?,?,?)};");
            pstmt.setInt(1, customer.getId());
            pstmt.setString(2, customer.getUsername());
            pstmt.setString(3, customer.getFirstName());
            pstmt.setString(4, customer.getLastName());
            pstmt.setString(5, customer.getTel());
            pstmt.setString(6, customer.getInfo());
            pstmt.setBoolean(7, customer.getActive());

            int noRows = pstmt.executeUpdate();
            return noRows == 1;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    static public boolean insertOrUpdateService(Service service) {
        try {
            PreparedStatement pstmt = DbManager.getConn().prepareStatement("{call dbo.addOrUpdateService(?,?,?,?,?)};");
            pstmt.setInt(1, service.getId_service());
            pstmt.setString(2, service.getName());
            pstmt.setDouble(3, service.getPrice());
            pstmt.setInt(4, service.getMinutes());
            pstmt.setBoolean(5, service.getActive());

            int noRows = pstmt.executeUpdate();
            return noRows == 1;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    static public boolean insertOrUpdateStaff(Staff staff) {
        try {
            PreparedStatement pstmt = DbManager.getConn().prepareStatement("{call dbo.addOrUpdateStaff(?,?,?,?,?,?,?,?,?)};");
            pstmt.setInt(1, staff.getId());
            pstmt.setString(2, staff.getFirstName());
            pstmt.setString(3, staff.getLastName());
            pstmt.setString(4, staff.getTel());
            pstmt.setString(5, staff.getInfo());
            pstmt.setString(6, staff.getUsername());
            pstmt.setString(7, staff.getPassword());
            pstmt.setBoolean(8, staff.getActive());
            pstmt.setInt(9, staff.getIdRole());


            int noRows = pstmt.executeUpdate();
            return noRows == 1;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }


    static public ArrayList<Customer> searchCustomer(String[] keywords) {
        class ComparatorCustomers implements Comparator<Customer> {

            @Override
            public int compare(Customer o1, Customer o2) {
                return Integer.compare(o1.getId(), o2.getId());
            }
        }
        ArrayList<Customer> finalResult = new ArrayList<>();

        for (String keyword : keywords) {
            ArrayList<Customer> partial = searchCustomerByOneKeyword(keyword);
            finalResult.addAll(partial);
        }

        if (finalResult.size() == 0)
            return finalResult;

        finalResult.sort(new ComparatorCustomers());
        ArrayList<Customer> listResult = new ArrayList<>();
        listResult.add(finalResult.get(0));

        for (int i = 1; i < finalResult.size(); i++) {
            if (finalResult.get(i).getId() != listResult.get(listResult.size() - 1).getId())
                listResult.add(finalResult.get(i));
        }

        return listResult;
    }

    static public ArrayList<Staff> searchStaff(String[] keywords) {
        class ComparatorCustomers implements Comparator<Staff> {

            @Override
            public int compare(Staff o1, Staff o2) {
                return Integer.compare(o1.getId(), o2.getId());
            }
        }
        ArrayList<Staff> finalResult = new ArrayList<>();

        for (String keyword : keywords) {
            ArrayList<Staff> partial = searchStaffByOneKeyword(keyword);
            finalResult.addAll(partial);
        }

        if (finalResult.size() == 0)
            return finalResult;

        finalResult.sort(new ComparatorCustomers());
        ArrayList<Staff> listResult = new ArrayList<>();
        listResult.add(finalResult.get(0));

        for (int i = 1; i < finalResult.size(); i++) {
            if (finalResult.get(i).getId() != listResult.get(listResult.size() - 1).getId())
                listResult.add(finalResult.get(i));
        }

        return listResult;
    }

    static public ArrayList<Staff> getAllStaff() {
        ArrayList<Staff> arr = new ArrayList<>();
        try {
            PreparedStatement pstmt = DbManager.getConn().prepareStatement("{call dbo.getAllStaff()}");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Staff staff = new Staff(
                        rs.getInt("id_staff"),
                        rs.getString("first_Name"),
                        rs.getString("last_name"),
                        rs.getString("tel"),
                        rs.getString("info"),
                        rs.getBoolean("active"),
                        rs.getString("username"),
                        rs.getInt("id_role")

                );
                arr.add(staff);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return arr;
    }


    static public ArrayList<Appointment> getAppointmentUserDate(String username, java.sql.Date date) {
        ArrayList<Appointment> arr = new ArrayList<>();
        try {
            PreparedStatement pstmt = DbManager.getConn().prepareStatement("{call dbo.getAppointmentUserDate(?,?)}");
            pstmt.setString(1, username);
            pstmt.setDate(2, date);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Appointment appt = new Appointment(
                        rs.getInt("id_appointment"),
                        rs.getInt("id_customer"),
                        rs.getInt("id_staff"),
                        rs.getInt("id_service"),
                        rs.getDate("date_app"),
                        rs.getInt("start_hour"),
                        rs.getInt("start_min"),
                        rs.getInt("end_hour"),
                        rs.getInt("end_min"),
                        rs.getString("info"),
                        rs.getDouble("price")
                );
                arr.add(appt);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return arr;
    }

    static public ArrayList<Customer> getAllCustomer() {
        ArrayList<Customer> customers = new ArrayList<>();
        try {
            PreparedStatement pstmt = DbManager.getConn().prepareStatement("{call dbo.getAllCustomer()}");

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Customer customer = new Customer(
                        rs.getInt("id_customer"),
                        rs.getString("username"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("tel"),
                        rs.getString("info"),
                        rs.getBoolean("active")
                );
                customers.add(customer);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return customers;
    }

    static public ArrayList<Service> getAllServices() {
        ArrayList<Service> services = new ArrayList<>();
        try {
            PreparedStatement pstmt = DbManager.getConn().prepareStatement("{call dbo.getAllService()}");

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Service Service = new Service(
                        rs.getInt("id_service"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getInt("minutes"),
                        rs.getBoolean("active"));
                services.add(Service);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return services;
    }


    static public Staff getStaffById(int id) {
        Staff staff = null;
        try {
            PreparedStatement pstmt = DbManager.getConn().prepareStatement("{call dbo.getStaffById(?)}");
            pstmt.setInt(1, id);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                staff = new Staff(rs.getInt("id_staff"),
                        rs.getString("first_Name"),
                        rs.getString("last_name"),
                        rs.getString("tel"),
                        rs.getString("info"),
                        rs.getBoolean("active"),
                        rs.getString("username"),
                        rs.getInt("id_role")
                );
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return staff;
    }

    static public Customer getCustomerById(int id) {
        Customer customer = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = DbManager.getConn().prepareStatement("{call dbo.getCustomerById(?)}");
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                customer = new Customer(
                        rs.getInt("id_customer"),
                        rs.getString("username"),
                        rs.getString("first_Name"),
                        rs.getString("last_name"),
                        rs.getString("tel"),
                        rs.getString("info"),
                        rs.getBoolean("active")
                );
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return customer;
    }


    static public Customer getCustomerByUsername(String username) {
        Customer customer = null;
        try {
            PreparedStatement pstmt = DbManager.getConn().prepareStatement("{call dbo.getCustomerByUsername(?)}");
            pstmt.setString(1, username);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                customer = new Customer(rs.getInt("id_customer"),
                        rs.getString("username"),
                        rs.getString("first_Name"),
                        rs.getString("last_name"),
                        rs.getString("tel"),
                        rs.getString("info"),
                        rs.getBoolean("active"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return customer;
    }

    static public Staff getStaffByUsername(String username) {
        Staff staff = null;
        try {
            PreparedStatement pstmt = DbManager.getConn().prepareStatement("{call dbo.getCustomerByUsername(?)}");
            pstmt.setString(1, username);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                staff = new Staff(rs.getInt("id_customer"),
                        rs.getString("first_Name"),
                        rs.getString("last_name"),
                        rs.getString("tel"),
                        rs.getString("info"),
                        rs.getBoolean("active"),
                        rs.getString("username"),
                        rs.getInt("id_role")
                );
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return staff;
    }

    static public Service getServiceById(int id) {
        Service Service = null;
        try {
            PreparedStatement pstmt = DbManager.getConn().prepareStatement("{call dbo.getServiceById(?)}");
            pstmt.setInt(1, id);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Service = new Service(rs.getInt("id_service"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getInt("minutes"),
                        rs.getBoolean("active"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return Service;
    }

    static public Service getServiceByName(String name) {
        Service Service = null;
        try {
            PreparedStatement pstmt = DbManager.getConn().prepareStatement("{call dbo.getServiceByName(?)}");
            pstmt.setString(1, name);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Service = new Service(rs.getInt("id_service"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getInt("minutes"),
                        rs.getBoolean("active"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return Service;
    }

    static public ArrayList<Appointment> getAllAppointments() {
        ArrayList<Appointment> appointments = new ArrayList<>();
        try {
            PreparedStatement pstmt = DbManager.getConn().prepareStatement("{call dbo.getAllAppointments()}");

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Appointment appointment = new Appointment(
                        rs.getInt("id_appointment"),
                        rs.getInt("id_customer"),
                        rs.getInt("id_staff"),
                        rs.getInt("id_service"),
                        rs.getDate("date_app"),
                        rs.getInt("start_hour"),
                        rs.getInt("start_min"),
                        rs.getInt("end_hour"),
                        rs.getInt("end_min"),
                        rs.getString("info"),
                        rs.getDouble("price")
                );
                appointments.add(appointment);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return appointments;
    }

    static public Appointment getAppointmentById(int id) {
        Appointment appointment = null;
        try {
            PreparedStatement pstmt = DbManager.getConn().prepareStatement("{call dbo.getAppointmentById(?)}");
            pstmt.setInt(1, id);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                appointment = new Appointment(
                        rs.getInt("id_appointment"),
                        rs.getInt("id_customer"),
                        rs.getInt("id_staff"),
                        rs.getInt("id_service"),
                        rs.getDate("date_app"),
                        rs.getInt("start_hour"),
                        rs.getInt("start_min"),
                        rs.getInt("end_hour"),
                        rs.getInt("end_min"),
                        rs.getString("info"),
                        rs.getDouble("price")
                );
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return appointment;
    }

    static public boolean deleteAppointment(Appointment appointment) {
        try {
            PreparedStatement pstmt = DbManager.getConn().prepareStatement("{call dbo.deleteAppointment(?)}");
            pstmt.setInt(1, appointment.getIdAppointment());

            int noRows = pstmt.executeUpdate();
            return noRows == 1;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    static public boolean deleteCustomer(Customer customer) {
        try {
            PreparedStatement pstmt = DbManager.getConn().prepareStatement("{call dbo.deleteCustomer(?)}");
            pstmt.setInt(1, customer.getId());

            int noRows = pstmt.executeUpdate();
            return noRows == 1;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    static public boolean deleteService(Service service) {
        try {
            PreparedStatement pstmt = DbManager.getConn().prepareStatement("{call dbo.deleteService(?)}");
            pstmt.setInt(1, service.getId_service());

            int noRows = pstmt.executeUpdate();
            return noRows == 1;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    static public boolean deleteStaff(Staff staff) {
        try {
            PreparedStatement pstmt = DbManager.getConn().prepareStatement("{call dbo.deleteStaff(?)}");
            pstmt.setInt(1, staff.getId());

            int noRows = pstmt.executeUpdate();
            return noRows == 1;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }


    static private ArrayList<Customer> searchCustomerByOneKeyword(String keyword) {
        keyword = "%" + keyword + "%";
        ArrayList<Customer> customers = new ArrayList<>();
        try {
            PreparedStatement pstmt = DbManager.getConn().prepareStatement("{call dbo.searchCustomers(?)}");
            pstmt.setString(1, keyword);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Customer customer = new Customer(
                        rs.getInt("id_customer"),
                        rs.getString("username"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("tel"),
                        rs.getString("info"),
                        rs.getBoolean("active")
                );
                customers.add(customer);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return customers;
    }

    static private ArrayList<Staff> searchStaffByOneKeyword(String keyword) {
        keyword = "%" + keyword + "%";
        ArrayList<Staff> staffs = new ArrayList<>();
        try {
            PreparedStatement pstmt = DbManager.getConn().prepareStatement("{call dbo.searchStaff(?)}");
            pstmt.setString(1, keyword);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int idRole = rs.getInt("id_role");
                Staff staff = new Staff(
                        rs.getInt("id_staff"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("tel"),
                        rs.getString("info"),
                        rs.getBoolean("active"),
                        rs.getString("username"),
                        idRole
                );
                staffs.add(staff);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return staffs;
    }

    public static ArrayList<Role> getAllActiveRoles() {
        ArrayList<Role> roles = new ArrayList<>();
        try {
            PreparedStatement pstmt = DbManager.getConn().prepareStatement("{call dbo.getAllRoles()}");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Role role = new Role(
                        rs.getInt("id_role"),
                        rs.getString("name"),
                        rs.getBoolean("active")
                );
                roles.add(role);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return roles;
    }
    static public boolean deleteCustomerAndAppointments(Customer customer) {
        try {
            Connection conn = getConn();
            conn.setAutoCommit(false); // Începe o tranzacție

            PreparedStatement pstmtAppointments = conn.prepareStatement("{call dbo.deleteCustomer(?)}");
            pstmtAppointments.setInt(1, customer.getId());
            pstmtAppointments.executeUpdate();

            PreparedStatement pstmtCustomer = conn.prepareStatement("{call dbo.deleteAppointment(?)}");
            pstmtCustomer.setInt(1, customer.getId());
            pstmtCustomer.executeUpdate();

            conn.commit();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();

        }
        return false;
    }

}