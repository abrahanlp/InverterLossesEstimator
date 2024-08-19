/**
 * Except as represented in this agreement,
 * all work product by FaultyProject is provided â€‹â€œAS ISâ€�.
 * Other than as provided in this agreement, FaultyProject
 * makes no other warranties, express or implied, and hereby
 * disclaims all implied warranties, including any warranty of
 * merchantability and warranty of fitness for a particular purpose.
 */

import java.lang.Math;

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
public class InverterLossesEstimator {
	//Transistor variables
	private double ron, tdon, tr, tdoff, tf, crss, vth, vgg, gm, rg;
	//Grid and inverter parameter
	private double vg, f_grid, vdc, f_sw, i_modulation, power;
	
	//Discrete interval
	private int analysis_points;
	private double delta;
	private int deltas_tdon, deltas_tr, deltas_tdoff, deltas_tf;
	
	//Energy losses
	double eon, eron, eoff;

	/**
	 * Class constructor
	 * @param analysis_points Number of discrete analysis points between switching periods
	 */
	public InverterLossesEstimator(int analysis_points) {
		this.analysis_points = analysis_points;
	}
	
	/**
	 * Energy Losses Calculate
	 */
	public void calculateLosses() {
		double t_grid = 1/f_grid;
		double w_grid = (double) (2 * Math.PI * f_grid);
		double i_rms = power/vg;
		double i_p = (double) (i_rms * Math.sqrt(2));
		
		double t_sw = 1/f_sw;
		
		delta = t_sw/analysis_points;
		deltas_tdon = (int) Math.round(tdon / delta);
		deltas_tdoff = (int) Math.round(tdoff / delta);
		deltas_tr = (int) Math.round(tr / delta);
		deltas_tf = (int) Math.round(tf / delta);
		
		int n = (int) (t_grid/delta);
		
		double t = 0;
		double[] i_t = new double[n];
		double[] modula = new double[n];
		double[] carrier = new double[n];
		boolean[] trigger = new boolean[n];
		double slope_carrier = 4/t_sw;
		
		for (int i = 0; i < n; i++) {
			//Generate current signal
			i_t[i] = (double) (i_p * Math.sin(w_grid*t));
			//Generate modulator signal
			modula[i] = (double) (i_modulation * Math.sin(w_grid*t));
			//Generate carrier signal
			carrier[i] = slope_carrier * ( (t_sw/2) - Math.abs(t % (2*(t_sw/2)) - (t_sw/2)) ) - 1;
			
			if(modula[i] > carrier[i]) {
				trigger[i] = true;
			}else {
				trigger[i] = false;
			}
			t += delta;
		}
		
		eon = 0.0F;
		eron = 0.0F;
		eoff = 0.0F;
		boolean sw_on = false;
		int i = 0;
		
		double i_t_abs = 0.0F; //Temporal variable for absolute current value
		
		do {
			if( (trigger[i] == true) && (sw_on == false) ) {		//Switch on losses
				i = (i + deltas_tdon + deltas_tr);
				//Current Rise
				i_t_abs = Math.abs(i_t[i]);
				eon += i_t_abs*vdc*tr/2;
				//Voltage Fall
				double tfv = (i_t_abs*ron - vdc) / ( (vgg - ((i_t_abs/gm) + vth))/(-crss*rg) );
				eon += i_t_abs*vdc*tfv/2;
				int deltas_tfv = (int)Math.round(tfv/delta);
				i = (i + deltas_tfv);
				sw_on = true;
			}else if( (trigger[i] == true) && (sw_on == true) ){		//Conduction losses
				eron += (i_t[i]*i_t[i])*ron*delta;
				i++;
			}else if( (trigger[i] == false) && (sw_on == true) ) {
				i = (i + deltas_tdoff);
				i_t_abs = Math.abs(i_t[i]);
				eoff += (i_t_abs*i_t_abs)*ron*tdoff;
				//%Voltage Rise
				double trv = (i_t_abs*ron - vdc) / ((-rg*i_t_abs)/(rg*crss));
				eoff += i_t_abs*vdc*trv/2;
				int deltas_trv = (int)Math.round(trv/delta);
				i = (i + deltas_trv);
				//Current Fall
				eoff += Math.abs(i_t[i]*vdc*tf/2);
				i = (i + deltas_tf);
				sw_on = false;
			}
			else {
				i++; //When no case concurrence
			}
			
		}while(i < n);
		eon = eon * f_grid;
		eoff = eoff * f_grid;
		eron = eron * f_grid;
	}
	
	/*
	 * Getters and Setters
	 */
	public double getRon() {
		return ron;
	}
	public void setRon(double ron) {
		this.ron = ron;
	}
	public double getTdon() {
		return tdon;
	}
	public void setTdon(double tdon) {
		this.tdon = tdon;
	}
	public double getTr() {
		return tr;
	}
	public void setTr(double tr) {
		this.tr = tr;
	}
	public double getTdoff() {
		return tdoff;
	}
	public void setTdoff(double tdoff) {
		this.tdoff = tdoff;
	}
	public double getTf() {
		return tf;
	}
	public void setTf(double tf) {
		this.tf = tf;
	}
	public double getCrss() {
		return crss;
	}
	public void setCrss(double crss) {
		this.crss = crss;
	}
	public double getVth() {
		return vth;
	}
	public void setVth(double vth) {
		this.vth = vth;
	}
	public double getVgg() {
		return vgg;
	}
	public void setVgg(double vgg) {
		this.vgg = vgg;
	}
	public double getGm() {
		return gm;
	}
	public void setGm(double gm) {
		this.gm = gm;
	}
	public double getRg() {
		return rg;
	}
	public void setRg(double rg) {
		this.rg = rg;
	}
	public double getVg() {
		return vg;
	}
	public void setVg(double vg) {
		this.vg = vg;
	}
	public double getF_grid() {
		return f_grid;
	}
	public void setF_grid(double f_grid) {
		this.f_grid = f_grid;
	}
	public double getVdc() {
		return vdc;
	}
	public void setVdc(double vdc) {
		this.vdc = vdc;
	}
	public double getF_sw() {
		return f_sw;
	}
	public void setF_sw(double f_sw) {
		this.f_sw = f_sw;
	}

	public double getPower() {
		return power;
	}

	public void setPower(double power) {
		this.power = power;
	}

	public int getAnalysis_points() {
		return analysis_points;
	}

	public void setAnalysis_points(int analysis_points) {
		this.analysis_points = analysis_points;
	}

	public double getI_modulation() {
		return i_modulation;
	}

	public void setI_modulation(double i_modulation) {
		this.i_modulation = i_modulation;
	}

	public double getEon() {
		return eon;
	}

	public double getEron() {
		return eron;
	}

	public double getEoff() {
		return eoff;
	}
}
