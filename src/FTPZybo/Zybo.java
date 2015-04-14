package FTPZybo;

import java.util.ArrayList;
import java.util.Random;

public class Zybo {
	
	private int[]	  sensorsValues = new int[16];
	private boolean[] sensorsActive = new boolean[16];
	
	public Zybo()
	{
		for (int i = 0 ; i < sensorsValues.length; i++)
		{
			writeToSensor(i, new Random().nextInt(1));
		}
	}
	
	public int readFromSensor(int sensorNumber)
	{
		int result = -1;
		if ((sensorsActive[sensorNumber]) && (sensorNumber >= 0) && (sensorNumber <= 15))
		{
			result = sensorsValues[sensorNumber];
		}
		
		return result;
	}
	
	public void writeToSensor(int sensorNumber, int value)
	{
		if ((sensorsActive[sensorNumber]) && (sensorNumber >= 0) && (sensorNumber <= 15))
		{
			sensorsValues[sensorNumber] = value;
		}
	}
	
	public void setSensorStatus(int sensorNumber, boolean state)
	{
		if ((sensorNumber >= 0) && (sensorNumber <= 15))
		{
			sensorsActive[sensorNumber] = state;
		}
	}
	
	public ArrayList<Integer> listAllSensors()
	{
		ArrayList<Integer> sensorsList = new ArrayList<Integer>();
		
		for (int i = 0 ; i < sensorsValues.length; i++)
		{
			if (sensorsActive[i])
			{
				sensorsList.add(i);
			}
		}
		
		return sensorsList;
	}
	
}
