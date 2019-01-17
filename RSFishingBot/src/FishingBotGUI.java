import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JList;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Color;
import javax.swing.JCheckBox;
import java.awt.Toolkit;

public class FishingBotGUI extends JFrame {

	private JPanel contentPane;
	private FishingBot ctx;
	private String Equipment;
	private String Location;
	private boolean BankingValue;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FishingBotGUI frame = new FishingBotGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FishingBotGUI() {
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\James\\Desktop\\RS_Icons_Fishing2.jpg"));
		setTitle("ProgressiveFishingBot");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 392, 225);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton StartButton = new JButton("Start");
		StartButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ctx.setEquipmentValue(Equipment);
				ctx.setLocationValue(Location);
				ctx.setBanking(BankingValue);
				ctx.setStartScript(true);
			}
		});
		StartButton.setBounds(86, 149, 89, 23);
		contentPane.add(StartButton);
		
		JComboBox LocationBox = new JComboBox();
		LocationBox.setModel(new DefaultComboBoxModel(new String[] {"Draynor Village", "Seers Village", "Barbarian Village"}));
		LocationBox.setBounds(140, 71, 124, 20);
		contentPane.add(LocationBox);
		Location = LocationBox.getSelectedItem().toString();
		
		JLabel lblNewLabel = new JLabel("Please pick from the following options below");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(47, 11, 263, 23);
		contentPane.add(lblNewLabel);
		
		JComboBox EquipmentBox = new JComboBox();
		EquipmentBox.setModel(new DefaultComboBoxModel(new String[] {"Small fishing net", "Fishing rod"}));
		EquipmentBox.setBounds(140, 102, 124, 20);
		contentPane.add(EquipmentBox);
		Equipment = EquipmentBox.getSelectedItem().toString();
		
		JLabel lblNewLabel_1 = new JLabel("Fishing Location:");
		lblNewLabel_1.setForeground(Color.BLUE);
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(20, 74, 110, 14);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblEquipment = new JLabel("Fishing Equipment:");
		lblEquipment.setHorizontalAlignment(SwingConstants.CENTER);
		lblEquipment.setForeground(Color.BLUE);
		lblEquipment.setBounds(20, 105, 111, 14);
		contentPane.add(lblEquipment);
		
		JButton btnAboutreportError = new JButton("Feedback");
		btnAboutreportError.setBounds(210, 149, 90, 23);
		contentPane.add(btnAboutreportError);
		
		JLabel label = new JLabel("");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setForeground(Color.BLUE);
		label.setBounds(10, 124, 95, 14);
		contentPane.add(label);
		
		JCheckBox BankingBox = new JCheckBox("Banking");
		BankingBox.setHorizontalAlignment(SwingConstants.CENTER);
		BankingBox.setBounds(57, 41, 226, 23);
		contentPane.add(BankingBox);
		BankingValue = BankingBox.isSelected();
	}
}
