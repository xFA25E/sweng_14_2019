package it.project.project14;

public interface IForecastManager{
	
	//varibili
	private ArrayList<Event> forecast24H;
	
	//metodi da implementare
	public ArrayList<Event> getPrevisioni24H();
	public void setForecast24H(ArrayList<Event> forecast24H);
	public generateForecast();
	
}