package weather.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;
import java.util.Scanner;

import tk.plogitech.darksky.forecast.GeoCoordinates;
import tk.plogitech.darksky.forecast.model.Latitude;
import tk.plogitech.darksky.forecast.model.Longitude;
import weather.ctrl.MyExecption;
import weather.ctrl.WeatherController;

public class UserInterface 
{

	private WeatherController ctrl = new WeatherController();

	public void getWeatherForCity1(){
		Longitude longitude = new Longitude(44.3918);
		Latitude latitude = new Latitude(33.3303);
		GeoCoordinates location = new GeoCoordinates(longitude, latitude);
		try {
			ctrl.process(location);
		} catch (MyExecption e) {
			System.out.println(e.getMessage());
		}
	}

	public void getWeatherForCity2(){
		GeoCoordinates location = new GeoCoordinates(new Longitude(45.3523), new Latitude(35.6023));
		try {
			ctrl.process(location);
		} catch (MyExecption e) {
			System.out.println(e.getMessage());
		}
	}

	public void getWeatherForCity3(){
		GeoCoordinates location = new GeoCoordinates(new Longitude(44.1871),new Latitude(36.316));
		try {
			ctrl.process(location);
		} catch (MyExecption e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void getWeatherByCoordinates() {
		Scanner scanner = new Scanner(System.in).useLocale(Locale.US);
		System.out.print("Longitude: ");
		Double longitude = scanner.nextDouble();
		System.out.print("Latitude: ");
		Double latitude = scanner.nextDouble();
		GeoCoordinates location = new GeoCoordinates(new Longitude(longitude),new Latitude(latitude));
		try {
			ctrl.process(location);

		} catch (MyExecption e) {
			System.out.println(e.getMessage());
		}
	}

	public void downLoadWeatherByCoordinates() {
		Scanner scanner = new Scanner(System.in).useLocale(Locale.US);
		System.out.print("Choose number of cities: ");
		int numberOfCities = scanner.nextInt();
		for(int i = 0; i < numberOfCities; i++){

		}
		System.out.print("Longitude: ");
		Double longitude = scanner.nextDouble();
		System.out.print("Latitude: ");
		Double latitude = scanner.nextDouble();
		GeoCoordinates location = new GeoCoordinates(new Longitude(longitude),new Latitude(latitude));
		try {
			ctrl.process(location);
		} catch (MyExecption e) {
			System.out.println(e.getMessage());
		}
	}

	public void start() {
		Menu<Runnable> menu = new Menu<>("Weather Infos");
		menu.setTitel("Wählen Sie eine Stadt aus:");
		menu.insert("a", "Bagdad", this::getWeatherForCity1);
		menu.insert("b", "Sulaymaniyah", this::getWeatherForCity2);
		menu.insert("c", "Erbil", this::getWeatherForCity3);
		menu.insert("d", "City via Coordinates:",this::getWeatherByCoordinates);
		menu.insert("k", "Download Tickers", this::downLoadWeatherByCoordinates);
		menu.insert("q", "Quit", null);
		Runnable choice;
		while ((choice = menu.exec()) != null) {
			 choice.run();
		}
		System.out.println("Program finished");
	}


	protected String readLine() 
	{
		String value = "\0";
		BufferedReader inReader = new BufferedReader(new InputStreamReader(System.in));
		try {
			value = inReader.readLine();
		} catch (IOException e) {
		}
		return value.trim();
	}

	protected Double readDouble(int lowerlimit, int upperlimit) 
	{
		Double number = null;
		while(number == null) {
			String str = this.readLine();
			try {
				number = Double.parseDouble(str);
			}catch(NumberFormatException e) {
				number=null;
				System.out.println("Please enter a valid number:");
				continue;
			}
			if(number<lowerlimit) {
				System.out.println("Please enter a higher number:");
				number=null;
			}else if(number>upperlimit) {
				System.out.println("Please enter a lower number:");
				number=null;
			}
		}
		return number;
	}
}
