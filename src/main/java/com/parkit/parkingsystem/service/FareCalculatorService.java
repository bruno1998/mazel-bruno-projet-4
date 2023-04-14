package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.model.Ticket;

public class FareCalculatorService {
	
	private String freeParkingLimitTime = 1800000;

    public void calculateFare(Ticket ticket){
        if( (ticket.getOutTime() == null) || (ticket.getOutTime().before(ticket.getInTime())) ){
            throw new IllegalArgumentException("Out time provided is incorrect:"+ticket.getOutTime().toString());
        }

        int inHour = ticket.getInTime().getTime();
        int outHour = ticket.getOutTime().getTime();

        //TODO: Some tests are failing here. Need to check if this logic is correct
        int duration = outHour - inHour;

        switch (ticket.getParkingSpot().getParkingType()){
            case CAR: {
            	if (duration < freeParkingLimitTime) {
            		ticket.setPrice(0);
            	}else {
            		ticket.setPrice(duration * Fare.CAR_RATE_PER_HOUR);
            	}
                break;
            }
            case BIKE: {
            	if (duration < freeParkingLimitTime) {
            		ticket.setPrice(0);
            	}else {
            		ticket.setPrice(duration * Fare.BIKE_RATE_PER_HOUR);
            	}
                break;
            }
            default: throw new IllegalArgumentException("Unkown Parking Type");
        }
    }
}