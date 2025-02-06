package kr.or.iei.weather.model.vo;

public class Weather {
	private String regId, tmFc, tmEf, mod, ne, stn, c, manId, manFc, w1, t, w2, ta, st, sky, prep, wf;
	

    public Weather(String regId, String tmFc, String tmEf, String mod, String ne, String stn, String c, String manId, String manFc, String w1, String t, String w2, String ta, String st, String sky, String prep, String wf) {
        this.regId = regId;
        this.tmFc = tmFc;
        this.tmEf = tmEf;
        this.mod = mod;
        this.ne = ne;
        this.stn = stn;
        this.c = c;
        this.manId = manId;
        this.manFc = manFc;
        this.w1 = w1;
        this.t = t;
        this.w2 = w2;
        this.ta = ta;
        this.st = st;
        this.sky = sky;
        this.prep = prep;
        this.wf = wf;
    }

    
    
    public String getRegId() {
		return regId;
	}



	public void setRegId(String regId) {
		this.regId = regId;
	}



	public String getTmFc() {
		return tmFc;
	}



	public void setTmFc(String tmFc) {
		this.tmFc = tmFc;
	}



	public String getTmEf() {
		return tmEf;
	}



	public void setTmEf(String tmEf) {
		this.tmEf = tmEf;
	}



	public String getMod() {
		return mod;
	}



	public void setMod(String mod) {
		this.mod = mod;
	}



	public String getNe() {
		return ne;
	}



	public void setNe(String ne) {
		this.ne = ne;
	}



	public String getStn() {
		return stn;
	}



	public void setStn(String stn) {
		this.stn = stn;
	}



	public String getC() {
		return c;
	}



	public void setC(String c) {
		this.c = c;
	}



	public String getManId() {
		return manId;
	}



	public void setManId(String manId) {
		this.manId = manId;
	}



	public String getManFc() {
		return manFc;
	}



	public void setManFc(String manFc) {
		this.manFc = manFc;
	}



	public String getW1() {
		return w1;
	}



	public void setW1(String w1) {
		this.w1 = w1;
	}



	public String getT() {
		return t;
	}



	public void setT(String t) {
		this.t = t;
	}



	public String getW2() {
		return w2;
	}



	public void setW2(String w2) {
		this.w2 = w2;
	}



	public String getTa() {
		return ta;
	}



	public void setTa(String ta) {
		this.ta = ta;
	}



	public String getSt() {
		return st;
	}



	public void setSt(String st) {
		this.st = st;
	}



	public String getSky() {
		return sky;
	}



	public void setSky(String sky) {
		this.sky = sky;
	}



	public String getPrep() {
		return prep;
	}



	public void setPrep(String prep) {
		this.prep = prep;
	}



	public String getWf() {
		return wf;
	}



	public void setWf(String wf) {
		this.wf = wf;
	}



	@Override
    public String toString() {
        return "Weather{" +
                "regId='" + regId + '\'' +
                ", tmFc='" + tmFc + '\'' +
                ", tmEf='" + tmEf + '\'' +
                ", mod='" + mod + '\'' +
                ", ne='" + ne + '\'' +
                ", stn='" + stn + '\'' +
                ", c='" + c + '\'' +
                ", manId='" + manId + '\'' +
                ", manFc='" + manFc + '\'' +
                ", w1='" + w1 + '\'' +
                ", t='" + t + '\'' +
                ", w2='" + w2 + '\'' +
                ", ta='" + ta + '\'' +
                ", st='" + st + '\'' +
                ", sky='" + sky + '\'' +
                ", prep='" + prep + '\'' +
                ", wf='" + wf + '\'' +
                '}';
    }
}
