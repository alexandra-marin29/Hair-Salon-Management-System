import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
public class MainPage extends JFrame implements ActionListener {
    protected JMenuItem mitViewApp, mitAddApp;
    protected JMenuItem mitViewCustomer, mitAddCustomer;
    protected JMenuItem mitViewService, mitAddService;
    protected JMenuItem mitViewStaff, mitAddStaff;

    protected JMenu mnuServices;
    protected JMenu mnuStaffs;

    protected Staff currentStaff;
    protected JPanel pnlMain;

    public MainPage(Staff currentStaff) {

        super("Hair Salon Schedule");
        this.currentStaff = currentStaff;
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(813, 620);
        setLocationRelativeTo(null);
        setResizable(false);

        ImageIcon imgBackground = new ImageIcon(Language.get("LP_BACKGROUND_PATH"));
        Image image = imgBackground.getImage().getScaledInstance(820, 580, Image.SCALE_SMOOTH);

        pnlMain = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(image, 0, 0, this);
            }
        };
        pnlMain.setLayout(new BorderLayout());
        setContentPane(pnlMain);

        createMenuBar();
    }

    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        menuBar.setPreferredSize(new Dimension(menuBar.getWidth(), 35));

        Font menuFont = new Font("Segoe UI", Font.BOLD, 16);
        menuBar.setFont(menuFont);
        menuBar.setBackground(new Color(192, 192, 192));
        menuBar.setForeground(Color.WHITE);

        UIManager.put("Menu.margin", new Insets(5, 6, 5, 6));
        UIManager.put("MenuItem.border", BorderFactory.createEmptyBorder(5, 5, 5, 5));

        if (currentStaff.getIdRole() == 1) {
            //Appointments
            JMenu mnuAppointment = new JMenu("Appointments");
            mitViewApp = new JMenuItem("View Appointments");
            mitViewApp.addActionListener(this);
            mitAddApp = new JMenuItem("Add new appointment");
            mitAddApp.addActionListener(this);
            mnuAppointment.add(mitViewApp);
            mnuAppointment.add(mitAddApp);
            menuBar.add(mnuAppointment);

            //Customers
            JMenu mnuCustomers = new JMenu("Customers");
            mitViewCustomer = new JMenuItem("View Customers");
            mitViewCustomer.addActionListener(this);
            mitAddCustomer = new JMenuItem("Add new Customer");
            mitAddCustomer.addActionListener(this);
            mnuCustomers.add(mitViewCustomer);
            mnuCustomers.add(mitAddCustomer);
            menuBar.add(mnuCustomers);


            //Services
            mnuServices = new JMenu("Services");
            mitViewService = new JMenuItem("View Services");
            mitViewService.addActionListener(this);
            mitAddService = new JMenuItem("Add new Service");
            mitAddService.addActionListener(this);
            mnuServices.add(mitViewService);
            mnuServices.add(mitAddService);
            menuBar.add(mnuServices);

            //Staff
            mnuStaffs = new JMenu("Staffs");
            mitViewStaff = new JMenuItem("View Staffs");
            mitViewStaff.addActionListener(this);
            mitAddStaff = new JMenuItem("Add new Staff");
            mitAddStaff.addActionListener(this);
            mnuStaffs.add(mitViewStaff);
            mnuStaffs.add(mitAddStaff);
            menuBar.add(mnuStaffs);
        } else {
            //Appointments
            JMenu mnuAppointment = new JMenu("Appointments");
            mitViewApp = new JMenuItem("View Appointments");
            mitViewApp.addActionListener(this);
            mitAddApp = new JMenuItem("Add new appointment");
            mitAddApp.addActionListener(this);
            mnuAppointment.add(mitViewApp);
            mnuAppointment.add(mitAddApp);
            menuBar.add(mnuAppointment);

            //Customers
            JMenu mnuCustomers = new JMenu("Customers");
            mitViewCustomer = new JMenuItem("View Customers");
            mitViewCustomer.addActionListener(this);
            mitAddCustomer = new JMenuItem("Add new Customer");
            mitAddCustomer.addActionListener(this);
            mnuCustomers.add(mitViewCustomer);
            mnuCustomers.add(mitAddCustomer);
            menuBar.add(mnuCustomers);


        }

        setJMenuBar(menuBar);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == mitViewApp) {
            pnlMain.removeAll();
            pnlMain.add(new AppointmentPage(currentStaff));
            setContentPane(pnlMain);
            revalidate();
            repaint();
        }

        if (e.getSource() == mitAddApp) {
            JFrame window = new AddEditAppointmentPage(-1,LocalDate.now().toString(), null,null);
            window.setVisible(true);
        }

        if (e.getSource() == mitViewCustomer) {
            pnlMain.removeAll();

            pnlMain.add(new CustomerPage());
            setContentPane(pnlMain);
        }

        if (e.getSource() == mitAddCustomer) {
            JFrame window = new AddEditCustomerPage(-1);
          window.setVisible(true);
        }

        if (e.getSource() == mitViewService) {
            pnlMain.removeAll();
            pnlMain.add(new ServicePage());
            setContentPane(pnlMain);
        }

        if (e.getSource() == mitAddService) {
            JFrame window = new AddEditServicePage(-1);
            window.setVisible(true);
        }

        if (e.getSource() == mitViewStaff) {
            pnlMain.removeAll();
            pnlMain.add(new StaffPage());
            setContentPane(pnlMain);
        }

        if (e.getSource() == mitAddStaff) {
            JFrame window = new AddEditStaffPage(-1);
            window.setVisible(true);
        }

        revalidate();
        repaint();
    }
}

