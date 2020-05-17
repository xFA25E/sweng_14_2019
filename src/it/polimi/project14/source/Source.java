package it.project.project14;

import java.util.ArrayList;
import java.util.Timer;

public class Source{
	
	private int id;
	private Timer timer;
	
	public Source(int id, Timer timer){
		this.id = id;
		this.timer = timer;
	}
	
	public int getId(){
		return id;
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	public Timer getTimer(){
		return timer;
	}
	
	public void setTimer(Timer timer){
		this.timer = timer;
	}
	
	public ArrayList<Event> sendForecast(){
		
		ArrayList<Event> EventList = new ArrayList<Event>();
		return EventList;
	}
}