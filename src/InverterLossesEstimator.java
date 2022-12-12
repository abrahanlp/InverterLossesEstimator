/**
 * Except as represented in this agreement,
 * all work product by FaultyProject is provided ​“AS IS”.
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
	private float ron, tdon, tr, tdoff, tf, crss, vth, vgg, gm, rg;
	//Grid and inverter parameter
	private float vg, f_grid, vdc, f_sw, i_modulation, power;
	
	//Discrete interval
	private int analysis_points;
	private float delta;
	private int deltas_tdon, deltas_tr, deltas_tdoff, deltas_tf;
	
	//Energy losses
	float eon, eron, eoff;

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
		float t_grid = 1/f_grid;
		float w_grid = (float) (2 * Math.PI * f_grid);
		float i_rms = power/vg;
		float i_p = (float) (i_rms * Math.sqrt(2));
		
		float t_sw = 1/f_sw;
		
		delta = t_sw/analysis_points;
		deltas_tdon = (int) Math.round(tdon / delta);
		deltas_tdoff = (int) Math.round(tdoff / delta);
		deltas_tr = (int) Math.round(tr / delta);
		deltas_tf = (int) Math.round(tf / delta);
		
		int n = (int) (t_grid/delta);
		
		float t = 0;
		float[] i_t = new float[n];
		float[] modula = new float[n];
		float[] carrier = new float[n];
		boolean[] trigger = new boolean[n];
		float slope_carrier = 4/t_sw;
		
		for (int i = 0; i < n; i++) {
			//Generate current signal
			i_t[i] = (float) (i_p * Math.sin(w_grid*t));
			//Generate modulator signal
			modula[i] = (float) (i_modulation * Math.sin(w_grid*t));
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
		
		float i_t_abs = 0.0F; //Temporal variable for absolute current value
		
		do {
			if( (trigger[i] == true) && (sw_on == false) ) {		//Switch on losses
				i = (i + deltas_tdon + deltas_tr);
				//Current Rise
				i_t_abs = Math.abs(i_t[i]);
				eon += i_t_abs*vdc*tr/2;
				//Voltage Fall
				float tfv = (i_t_abs*ron - vdc) / ( (vgg - ((i_t_abs/gm) + vth))/(-crss*rg) );
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
				float trv = (i_t_abs*ron - vdc) / ((-rg*i_t_abs)/(rg*crss));
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
	public float getRon() {
		return ron;
	}
	public void setRon(float ron) {
		this.ron = ron;
	}
	public float getTdon() {
		return tdon;
	}
	public void setTdon(float tdon) {
		this.tdon = tdon;
	}
	public float getTr() {
		return tr;
	}
	public void setTr(float tr) {
		this.tr = tr;
	}
	public float getTdoff() {
		return tdoff;
	}
	public void setTdoff(float tdoff) {
		this.tdoff = tdoff;
	}
	public float getTf() {
		return tf;
	}
	public void setTf(float tf) {
		this.tf = tf;
	}
	public float getCrss() {
		return crss;
	}
	public void setCrss(float crss) {
		this.crss = crss;
	}
	public float getVth() {
		return vth;
	}
	public void setVth(float vth) {
		this.vth = vth;
	}
	public float getVgg() {
		return vgg;
	}
	public void setVgg(float vgg) {
		this.vgg = vgg;
	}
	public float getGm() {
		return gm;
	}
	public void setGm(float gm) {
		this.gm = gm;
	}
	public float getRg() {
		return rg;
	}
	public void setRg(float rg) {
		this.rg = rg;
	}
	public float getVg() {
		return vg;
	}
	public void setVg(float vg) {
		this.vg = vg;
	}
	public float getF_grid() {
		return f_grid;
	}
	public void setF_grid(float f_grid) {
		this.f_grid = f_grid;
	}
	public float getVdc() {
		return vdc;
	}
	public void setVdc(float vdc) {
		this.vdc = vdc;
	}
	public float getF_sw() {
		return f_sw;
	}
	public void setF_sw(float f_sw) {
		this.f_sw = f_sw;
	}

	public float getPower() {
		return power;
	}

	public void setPower(float power) {
		this.power = power;
	}

	public int getAnalysis_points() {
		return analysis_points;
	}

	public void setAnalysis_points(int analysis_points) {
		this.analysis_points = analysis_points;
	}

	public float getI_modulation() {
		return i_modulation;
	}

	public void setI_modulation(float i_modulation) {
		this.i_modulation = i_modulation;
	}

	public float getEon() {
		return eon;
	}

	public float getEron() {
		return eron;
	}

	public float getEoff() {
		return eoff;
	}
}
