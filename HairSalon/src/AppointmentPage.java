import org.jdesktop.swingx.JXDatePicker;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ArrayList;

public class AppointmentPage extends JPanel implements ActionListener {
    private JComboBox cmbStaff;
    private JXDatePicker datePicker;


    private JButton btnAddAppointment;
    private JButton btnEditAppointment;
    private JButton btnDeleteAppointment;
    private JLabel lblDayType;

    private ArrayList<Staff> allStaff;

    private JPanel pnlTableWrap;
    private JScrollPane scpScrollPane;
    private JTable table;

    private ListSelectionModel listSelectionModel;
    private ArrayList<Appointment> appointments;

    public AppointmentPage(Staff loggedStaff) {

        super();
        setLayout(null);
        String strDate = LocalDate.now().toString();
        lblDayType = new JLabel();

        createFilter(strDate, loggedStaff);

        createButtons();

        createTable();

        refreshTable(true);

    }

    private void createFilter(String strDate, Staff loggedStaff) {
        JPanel pnlFilter = new JPanel();
        pnlFilter.setLayout(new GridLayout(2, 2));
        pnlFilter.setLocation(20, 10);
        pnlFilter.setSize(760, 50);

        JLabel lblStaff = new JLabel(Language.get("AP_LBL_STAFF"));
        pnlFilter.add(lblStaff);

        allStaff = DbManager.getAllStaff();
        cmbStaff = new JComboBox(allStaff.toArray());

        for (int i = 0; i < allStaff.size(); i++) {
            if (loggedStaff == cmbStaff.getSelectedItem()) {
                cmbStaff.setSelectedIndex(i);
                break;
            }
        }

        cmbStaff.addActionListener(this);
        pnlFilter.add(cmbStaff);


        JLabel lblDate = new JLabel(Language.get("AP_LBL_DATE"));
        pnlFilter.add(lblDate);

        datePicker = new JXDatePicker();
        datePicker.setDate(Date.from(LocalDate.parse(strDate).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        datePicker.addActionListener(this);
        pnlFilter.add(datePicker);


        add(pnlFilter);
    }

    private void createButtons() {
        JPanel pnlBtns = new JPanel();
        pnlBtns.setLayout(new GridLayout(1, 5, 15, 15));
        pnlBtns.setLocation(20, 65);
        pnlBtns.setSize(760, 25);


        btnAddAppointment = new JButton(Language.get("AP_BTN_ADD"));
        btnAddAppointment.addActionListener(this);
        pnlBtns.add(btnAddAppointment);

        btnEditAppointment = new JButton(Language.get("AP_BTN_EDIT"));
        btnEditAppointment.addActionListener(this);
        pnlBtns.add(btnEditAppointment);

        btnDeleteAppointment = new JButton(Language.get("AP_BTN_DELETE"));
        btnDeleteAppointment.addActionListener(this);
        pnlBtns.add(btnDeleteAppointment);


        add(pnlBtns);
    }

    public void refreshPage() {
        LocalDate selectedDate = datePicker.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        lblDayType.setText(Language.get("AP_WEEKDAY_LBL") + Language.get(Utils.getWorkdayStr(selectedDate.toString())));

        refreshTable(false);
        pnlTableWrap.revalidate();
        revalidate();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == datePicker) {
            refreshPage();
        }

        if (e.getSource() == btnAddAppointment) {
            LocalDate selectedDate = datePicker.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            AddEditAppointmentPage addEditAppointmentPage = new AddEditAppointmentPage(-1, selectedDate.toString(), (Staff) cmbStaff.getSelectedItem(), this);
            addEditAppointmentPage.setVisible(true);
        }

        if (e.getSource() == btnEditAppointment) {
            if (listSelectionModel.getSelectedIndices().length == 0)
                return;
            int selectedIndex = listSelectionModel.getSelectedIndices()[0];

            AddEditAppointmentPage addEditAppointmentPage = new AddEditAppointmentPage(appointments.get(selectedIndex).getIdAppointment(), null, null, this);
            addEditAppointmentPage.setVisible(true);
        }

        if (e.getSource() == btnDeleteAppointment) {
            if (listSelectionModel.getSelectedIndices().length == 0)
                return;
            int selectedIndex = listSelectionModel.getSelectedIndices()[0];
            boolean response = DbManager.deleteAppointment(appointments.get(selectedIndex));

            if (response)
                JOptionPane.showMessageDialog(this, Language.get("AP_MSG_DELETE_SUCCES"));
            else
                JOptionPane.showMessageDialog(this, Language.get("AP_MSG_DELETE_FAIL"));

            refreshTable(true);

        }
    }

    void createTable() {
        pnlTableWrap = new JPanel();
        pnlTableWrap.setLocation(20, 100);
        pnlTableWrap.setSize(760, 480);
        add(pnlTableWrap);

        class NonEditableTableModel extends DefaultTableModel {
            NonEditableTableModel(Object[] objects, int lines) {
                super(objects, lines);
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }
        }

        table = new JTable(new NonEditableTableModel(new Object[]{Language.get("AP_TBL_HEAD_CUSERNAME"),Language.get("AP_TBL_HEAD_SUSERNAME"), Language.get("AP_TBL_HEAD_SERVICE"), Language.get("AP_TBL_HEAD_TIME"), Language.get("AP_TBL_HEAD_PRICE"), Language.get("AP_TBL_HEAD_DATE"),Language.get("AP_TBL_HEAD_NOTE")}, 0));
        table.setFillsViewportHeight(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listSelectionModel = table.getSelectionModel();

        scpScrollPane = new JScrollPane(table);
        scpScrollPane.setPreferredSize(new Dimension(760, 460));
        scpScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        pnlTableWrap.add(scpScrollPane);
        add(pnlTableWrap);
    }


    public void refreshTable(boolean loadAll) {
        LocalDate selectedDate = null;

        if (!loadAll) {
            selectedDate = (datePicker.getDate() != null)
                    ? datePicker.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
                    : null;
        }

        appointments = (selectedDate != null)
                ? DbManager.getAppointmentUserDate(cmbStaff.getSelectedItem().toString(), java.sql.Date.valueOf(selectedDate))
                : DbManager.getAllAppointments();

        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        for (Appointment appointment : appointments) {
            Customer customer = DbManager.getCustomerById(appointment.getIdCustomer());

            Staff staff = DbManager.getStaffById(appointment.getIdStaff());
            Service service = DbManager.getServiceById(appointment.getIdService());

            String customerUsername = (customer != null) ? customer.getUsername() : "Unknown";
            String staffUsername = (staff != null) ? staff.getUsername() : "Unknown";

            model.addRow(new Object[]{
                    customerUsername,
                    staffUsername,
                    service.getName(),
                    appointment.getTimeFrame(),
                    appointment.getPrice(),
                    appointment.getDate_app(),
                    appointment.getInfo()
            });
        }
    }
}