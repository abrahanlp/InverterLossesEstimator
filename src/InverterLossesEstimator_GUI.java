/**
 * Except as represented in this agreement,
 * all work product by FaultyProject is provided â€‹â€œAS ISâ€�.
 * Other than as provided in this agreement, FaultyProject
 * makes no other warranties, express or implied, and hereby
 * disclaims all implied warranties, including any warranty of
 * merchantability and warranty of fitness for a particular purpose.
 */

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JSeparator;
import java.awt.SystemColor;
import java.awt.Toolkit;

/**
 * InverterLossesEstimator Java Class.
 * After set transistor and inverter parameters call calculateLosses() method
 * get results on eon, eoff and eron getters. That are for one transistor, unipolar modulation
 * and grid connected inverter.
 * 
 * @author alemos(FaultyProject)
 * @version 1.0
 * @see <a href=
 * 	"https://faultyproject.es/electronica/potencia/calculo-de-perdidas-en-inversores-monofasicos"/>
 * 	Cálculo de pérdidas en inversores monofásicos (FaultyProject) </a>
 *
 */
public class InverterLossesEstimator_GUI {

	private JFrame frmInverterLossesEstimator;
	private JTextField txtVgrid;
	private JTextField txtFgrid;
	private JTextField txtVdc;
	private JTextField txtFsw;
	private JTextField txtRon;
	private JTextField txtTdoff;
	private JTextField txtTr;
	private JTextField txtTf;
	private JTextField txtTdon;
	private JTextField txtCrss;
	private JTextField txtVth;
	private JTextField txtGm;
	private JTextField txtVgg;
	private JTextField txtRg;
	private JTextField txtPower;

	private InverterLossesEstimator estimatedLosses = new InverterLossesEstimator(20000);
	private JTextField txtImodulation;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InverterLossesEstimator_GUI window = new InverterLossesEstimator_GUI();
					window.frmInverterLossesEstimator.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public InverterLossesEstimator_GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmInverterLossesEstimator = new JFrame();
		frmInverterLossesEstimator.setIconImage(Toolkit.getDefaultToolkit().getImage(InverterLossesEstimator_GUI.class.getResource("/images/FP_128x128.png")));
		frmInverterLossesEstimator.setResizable(false);
		
		frmInverterLossesEstimator.setFont(new Font("Lucida Console", Font.PLAIN, 12));
		frmInverterLossesEstimator.setTitle("Inverter Losses Estimator");
		frmInverterLossesEstimator.setBounds(100, 100, 450, 320);
		frmInverterLossesEstimator.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmInverterLossesEstimator.getContentPane().setLayout(null);
		
		JLabel lblGridInverter = new JLabel("Grid and Inverter parameters");
		lblGridInverter.setFont(new Font("Lucida Console", Font.BOLD, 11));
		lblGridInverter.setHorizontalAlignment(SwingConstants.LEFT);
		lblGridInverter.setBounds(10, 10, 220, 14);
		frmInverterLossesEstimator.getContentPane().add(lblGridInverter);
		
		JLabel lblVgrid = new JLabel("V grid");
		lblVgrid.setFont(new Font("Lucida Console", Font.PLAIN, 11));
		lblVgrid.setBounds(15, 40, 70, 14);
		frmInverterLossesEstimator.getContentPane().add(lblVgrid);
		
		JLabel lblFgrid = new JLabel("Freq. grid");
		lblFgrid.setFont(new Font("Lucida Console", Font.PLAIN, 11));
		lblFgrid.setBounds(15, 65, 70, 14);
		frmInverterLossesEstimator.getContentPane().add(lblFgrid);
		
		txtVgrid = new JTextField();
		txtVgrid.setHorizontalAlignment(SwingConstants.CENTER);
		txtVgrid.setText("230");
		txtVgrid.setFont(new Font("Lucida Console", Font.PLAIN, 11));
		txtVgrid.setBounds(90, 36, 50, 20);
		frmInverterLossesEstimator.getContentPane().add(txtVgrid);
		txtVgrid.setColumns(10);
		
		txtFgrid = new JTextField();
		txtFgrid.setHorizontalAlignment(SwingConstants.CENTER);
		txtFgrid.setText("50");
		txtFgrid.setFont(new Font("Lucida Console", Font.PLAIN, 11));
		txtFgrid.setColumns(10);
		txtFgrid.setBounds(90, 61, 50, 20);
		frmInverterLossesEstimator.getContentPane().add(txtFgrid);
		
		JLabel lblVdc = new JLabel("Vdc");
		lblVdc.setFont(new Font("Lucida Console", Font.PLAIN, 11));
		lblVdc.setBounds(155, 40, 30, 14);
		frmInverterLossesEstimator.getContentPane().add(lblVdc);
		
		txtVdc = new JTextField();
		txtVdc.setHorizontalAlignment(SwingConstants.CENTER);
		txtVdc.setText("400");
		txtVdc.setFont(new Font("Lucida Console", Font.PLAIN, 11));
		txtVdc.setColumns(10);
		txtVdc.setBounds(190, 36, 50, 20);
		frmInverterLossesEstimator.getContentPane().add(txtVdc);
		
		JLabel lblPower = new JLabel("Power");
		lblPower.setFont(new Font("Lucida Console", Font.PLAIN, 11));
		lblPower.setBounds(260, 40, 40, 14);
		frmInverterLossesEstimator.getContentPane().add(lblPower);
		
		txtPower = new JTextField();
		txtPower.setHorizontalAlignment(SwingConstants.CENTER);
		txtPower.setText("2300");
		txtPower.setFont(new Font("Lucida Console", Font.PLAIN, 11));
		txtPower.setColumns(10);
		txtPower.setBounds(300, 36, 50, 20);
		frmInverterLossesEstimator.getContentPane().add(txtPower);
		
		JLabel lblFsw = new JLabel("F sw");
		lblFsw.setFont(new Font("Lucida Console", Font.PLAIN, 11));
		lblFsw.setBounds(155, 65, 30, 14);
		frmInverterLossesEstimator.getContentPane().add(lblFsw);
		
		txtFsw = new JTextField();
		txtFsw.setHorizontalAlignment(SwingConstants.CENTER);
		txtFsw.setText("20000");
		txtFsw.setFont(new Font("Lucida Console", Font.PLAIN, 11));
		txtFsw.setColumns(10);
		txtFsw.setBounds(190, 61, 50, 20);
		frmInverterLossesEstimator.getContentPane().add(txtFsw);
		
		JLabel lblTransistorParameter = new JLabel("Transistor parameters");
		lblTransistorParameter.setHorizontalAlignment(SwingConstants.LEFT);
		lblTransistorParameter.setFont(new Font("Lucida Console", Font.BOLD, 11));
		lblTransistorParameter.setBounds(10, 93, 220, 14);
		frmInverterLossesEstimator.getContentPane().add(lblTransistorParameter);
		
		JLabel lblRon = new JLabel("Ron");
		lblRon.setFont(new Font("Lucida Console", Font.PLAIN, 11));
		lblRon.setBounds(185, 115, 30, 14);
		frmInverterLossesEstimator.getContentPane().add(lblRon);
		
		txtRon = new JTextField();
		txtRon.setText("100");
		txtRon.setFont(new Font("Lucida Console", Font.PLAIN, 11));
		txtRon.setColumns(10);
		txtRon.setBounds(220, 111, 40, 20);
		frmInverterLossesEstimator.getContentPane().add(txtRon);
		
		JLabel lblTdoff = new JLabel("tdoff");
		lblTdoff.setFont(new Font("Lucida Console", Font.PLAIN, 11));
		lblTdoff.setBounds(15, 140, 35, 14);
		frmInverterLossesEstimator.getContentPane().add(lblTdoff);
		
		txtTdoff = new JTextField();
		txtTdoff.setText("59");
		txtTdoff.setFont(new Font("Lucida Console", Font.PLAIN, 11));
		txtTdoff.setColumns(10);
		txtTdoff.setBounds(55, 136, 40, 20);
		frmInverterLossesEstimator.getContentPane().add(txtTdoff);
		
		txtTr = new JTextField();
		txtTr.setText("13");
		txtTr.setFont(new Font("Lucida Console", Font.PLAIN, 11));
		txtTr.setColumns(10);
		txtTr.setBounds(130, 111, 40, 20);
		frmInverterLossesEstimator.getContentPane().add(txtTr);
		
		JLabel lblTr = new JLabel("tr");
		lblTr.setFont(new Font("Lucida Console", Font.PLAIN, 11));
		lblTr.setBounds(110, 115, 20, 14);
		frmInverterLossesEstimator.getContentPane().add(lblTr);
		
		txtTf = new JTextField();
		txtTf.setText("11");
		txtTf.setFont(new Font("Lucida Console", Font.PLAIN, 11));
		txtTf.setColumns(10);
		txtTf.setBounds(130, 136, 40, 20);
		frmInverterLossesEstimator.getContentPane().add(txtTf);
		
		JLabel lblTf = new JLabel("tf");
		lblTf.setFont(new Font("Lucida Console", Font.PLAIN, 11));
		lblTf.setBounds(110, 140, 20, 14);
		frmInverterLossesEstimator.getContentPane().add(lblTf);
		
		JLabel lblTdon = new JLabel("tdon");
		lblTdon.setFont(new Font("Lucida Console", Font.PLAIN, 11));
		lblTdon.setBounds(15, 115, 30, 14);
		frmInverterLossesEstimator.getContentPane().add(lblTdon);
		
		txtTdon = new JTextField();
		txtTdon.setText("18");
		txtTdon.setFont(new Font("Lucida Console", Font.PLAIN, 11));
		txtTdon.setColumns(10);
		txtTdon.setBounds(55, 111, 40, 20);
		frmInverterLossesEstimator.getContentPane().add(txtTdon);
		
		JLabel lblCrss = new JLabel("Crss");
		lblCrss.setFont(new Font("Lucida Console", Font.PLAIN, 11));
		lblCrss.setBounds(185, 140, 30, 14);
		frmInverterLossesEstimator.getContentPane().add(lblCrss);
		
		txtCrss = new JTextField();
		txtCrss.setText("2.5");
		txtCrss.setFont(new Font("Lucida Console", Font.PLAIN, 11));
		txtCrss.setColumns(10);
		txtCrss.setBounds(220, 137, 40, 20);
		frmInverterLossesEstimator.getContentPane().add(txtCrss);
		
		JLabel lblVth = new JLabel("Vth");
		lblVth.setFont(new Font("Lucida Console", Font.PLAIN, 11));
		lblVth.setBounds(15, 165, 30, 14);
		frmInverterLossesEstimator.getContentPane().add(lblVth);
		
		txtVth = new JTextField();
		txtVth.setText("5.5");
		txtVth.setFont(new Font("Lucida Console", Font.PLAIN, 11));
		txtVth.setColumns(10);
		txtVth.setBounds(55, 161, 40, 20);
		frmInverterLossesEstimator.getContentPane().add(txtVth);
		
		JLabel lblGm = new JLabel("Gm");
		lblGm.setFont(new Font("Lucida Console", Font.PLAIN, 11));
		lblGm.setBounds(110, 165, 20, 14);
		frmInverterLossesEstimator.getContentPane().add(lblGm);
		
		txtGm = new JTextField();
		txtGm.setText("25");
		txtGm.setFont(new Font("Lucida Console", Font.PLAIN, 11));
		txtGm.setColumns(10);
		txtGm.setBounds(130, 161, 40, 20);
		frmInverterLossesEstimator.getContentPane().add(txtGm);
		
		JLabel lblVgg = new JLabel("Vgg");
		lblVgg.setFont(new Font("Lucida Console", Font.PLAIN, 11));
		lblVgg.setBounds(20, 215, 30, 14);
		frmInverterLossesEstimator.getContentPane().add(lblVgg);
		
		txtVgg = new JTextField();
		txtVgg.setText("8");
		txtVgg.setFont(new Font("Lucida Console", Font.PLAIN, 11));
		txtVgg.setColumns(10);
		txtVgg.setBounds(55, 211, 40, 20);
		frmInverterLossesEstimator.getContentPane().add(txtVgg);
		
		JLabel lblDriverParameter = new JLabel("Driver parameters");
		lblDriverParameter.setHorizontalAlignment(SwingConstants.LEFT);
		lblDriverParameter.setFont(new Font("Lucida Console", Font.BOLD, 11));
		lblDriverParameter.setBounds(10, 190, 220, 14);
		frmInverterLossesEstimator.getContentPane().add(lblDriverParameter);
		
		txtRg = new JTextField();
		txtRg.setText("10");
		txtRg.setFont(new Font("Lucida Console", Font.PLAIN, 11));
		txtRg.setColumns(10);
		txtRg.setBounds(130, 211, 40, 20);
		frmInverterLossesEstimator.getContentPane().add(txtRg);
		
		JLabel lblRg = new JLabel("Rg");
		lblRg.setFont(new Font("Lucida Console", Font.PLAIN, 11));
		lblRg.setBounds(110, 215, 20, 14);
		frmInverterLossesEstimator.getContentPane().add(lblRg);
		
		JLabel lblPon = new JLabel("");
		lblPon.setForeground(Color.RED);
		lblPon.setHorizontalAlignment(SwingConstants.LEFT);
		lblPon.setFont(new Font("Lucida Console", Font.BOLD, 11));
		lblPon.setBounds(285, 107, 130, 14);
		frmInverterLossesEstimator.getContentPane().add(lblPon);
		
		JLabel lblPoff = new JLabel("");
		lblPoff.setHorizontalAlignment(SwingConstants.LEFT);
		lblPoff.setForeground(Color.RED);
		lblPoff.setFont(new Font("Lucida Console", Font.BOLD, 11));
		lblPoff.setBounds(285, 127, 130, 14);
		frmInverterLossesEstimator.getContentPane().add(lblPoff);
		
		JLabel lblPron = new JLabel("");
		lblPron.setHorizontalAlignment(SwingConstants.LEFT);
		lblPron.setForeground(Color.RED);
		lblPron.setFont(new Font("Lucida Console", Font.BOLD, 11));
		lblPron.setBounds(285, 147, 130, 14);
		frmInverterLossesEstimator.getContentPane().add(lblPron);
		
		JLabel lblPone = new JLabel("");
		lblPone.setHorizontalAlignment(SwingConstants.LEFT);
		lblPone.setForeground(Color.RED);
		lblPone.setFont(new Font("Lucida Console", Font.BOLD, 11));
		lblPone.setBounds(285, 167, 130, 14);
		frmInverterLossesEstimator.getContentPane().add(lblPone);
		
		JLabel lblPTotal = new JLabel("");
		lblPTotal.setHorizontalAlignment(SwingConstants.CENTER);
		lblPTotal.setForeground(Color.RED);
		lblPTotal.setFont(new Font("Lucida Console", Font.BOLD, 11));
		lblPTotal.setBounds(286, 190, 130, 14);
		frmInverterLossesEstimator.getContentPane().add(lblPTotal);
		
		JButton btnCalculate = new JButton("Calculate");
		btnCalculate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				long t_start = System.currentTimeMillis();
				estimatedLosses.setVg(Double.parseDouble(txtVgrid.getText()));
				estimatedLosses.setF_grid(Double.parseDouble(txtFgrid.getText()));
				estimatedLosses.setVdc(Double.parseDouble(txtVdc.getText()));
				estimatedLosses.setF_sw(Double.parseDouble(txtFsw.getText()));
				estimatedLosses.setPower(Double.parseDouble(txtPower.getText()));
				estimatedLosses.setI_modulation(Double.parseDouble(txtImodulation.getText()));
				
				estimatedLosses.setRon(Double.parseDouble(txtRon.getText())/1000.0F);
				estimatedLosses.setTdon(Double.parseDouble(txtTdon.getText())/1000000000.0F);
				estimatedLosses.setTr(Double.parseDouble(txtTr.getText())/1000000000.0F);
				estimatedLosses.setTdoff(Double.parseDouble(txtTdoff.getText())/1000000000.0F);
				estimatedLosses.setTf(Double.parseDouble(txtTf.getText())/1000000000.0F);
				estimatedLosses.setCrss(Double.parseDouble(txtCrss.getText())/1000000000000.0F);
				estimatedLosses.setGm(Double.parseDouble(txtGm.getText()));
				estimatedLosses.setVth(Double.parseDouble(txtVth.getText()));
				estimatedLosses.setVgg(Double.parseDouble(txtVgg.getText()));
				estimatedLosses.setRg(Double.parseDouble(txtRg.getText()));
				
				estimatedLosses.calculateLosses();
		        
				double eon = estimatedLosses.getEon();
				double eoff = estimatedLosses.getEoff();
				double eron = estimatedLosses.getEron();
				double eIndividual = eon + eoff + eron;
				double eTotal = eIndividual*4;
				lblPon.setText("Pon: " + String.format("%.03f", eon*4) + " W");
				lblPoff.setText("Poff: " + String.format("%.03f", eoff*4) + " W");
				lblPron.setText("Pron: " + String.format("%.03f", eron*4) + " W");
				lblPone.setText("Pone: " + String.format("%.03f", eIndividual) + " W");
				lblPTotal.setText("P.Tot: " + String.format("%.03f", eTotal) + " W");
//				long t_end = System.currentTimeMillis();
//		        double timeElapsed = (double) ((t_end - t_start));
//		        System.out.println(timeElapsed +" millis");
			}
		});
		
		btnCalculate.setFont(new Font("Lucida Console", Font.PLAIN, 11));
		btnCalculate.setBounds(295, 212, 110, 23);
		frmInverterLossesEstimator.getContentPane().add(btnCalculate);
		
		JLabel lblImodulation = new JLabel("I. Mod");
		lblImodulation.setFont(new Font("Lucida Console", Font.PLAIN, 11));
		lblImodulation.setBounds(260, 65, 50, 14);
		frmInverterLossesEstimator.getContentPane().add(lblImodulation);
		
		txtImodulation = new JTextField();
		txtImodulation.setHorizontalAlignment(SwingConstants.CENTER);
		txtImodulation.setText("0.6");
		txtImodulation.setFont(new Font("Lucida Console", Font.PLAIN, 11));
		txtImodulation.setColumns(10);
		txtImodulation.setBounds(310, 61, 40, 20);
		frmInverterLossesEstimator.getContentPane().add(txtImodulation);
		
		JSeparator separator = new JSeparator();
		separator.setForeground(new Color(160, 160, 160));
		separator.setBackground(new Color(255, 255, 255));
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBounds(270, 100, 5, 135);
		frmInverterLossesEstimator.getContentPane().add(separator);
			
		JSeparator separator_1 = new JSeparator();
		separator_1.setForeground(SystemColor.controlShadow);
		separator_1.setBackground(Color.WHITE);
		separator_1.setBounds(270, 100, 150, 5);
		frmInverterLossesEstimator.getContentPane().add(separator_1);
		
		JSeparator by_separator = new JSeparator();
		by_separator.setBounds(0, 240, 436, 5);
		frmInverterLossesEstimator.getContentPane().add(by_separator);
		
		JLabel lblCredits = new JLabel("by Abraham Lemos (FaultyProject)");
		lblCredits.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCredits.setFont(new Font("Lucida Console", Font.ITALIC, 10));
		lblCredits.setBounds(0, 247, 415, 13);
		frmInverterLossesEstimator.getContentPane().add(lblCredits);
		
	}
}