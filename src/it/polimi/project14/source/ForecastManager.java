package it.project.project14;

import java.util.ArrayList;
import java.util.Timer;

public class ForecastManager implements IForecastManager{
	
	private ArrayList<Event> forecast24H;
	private Timer timer;
	
	public ForecastManager(){
		this.forecast24H = new ArrayList<Event> forecast24H;
		this.timer = timer;
	}
	
	/*public ForecastManager(ArrayList<Event> forecast24H, Timer timer){
		this.forecast24H = forecast24H;
		this.timer = timer;
	}*/
	
	public Timer getTimer(){
		return timer;
	}
	
	public void setTimer(Timer timer){
		this.timer = timer;
	}
	
	public ArrayList<Event> getForecast24H() {
		return forecast24H;
	}
	public void setForecast24H(ArrayList<Event> forecast24H) {
		this.forecast24H = forecast24H;
	}
	
	public ArrayList<Event> generateForecast(){
		return forecast24H;
	}
	
	private void computeDifferences(){
	}
	
}