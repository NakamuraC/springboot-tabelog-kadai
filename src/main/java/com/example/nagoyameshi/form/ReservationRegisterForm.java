package com.example.nagoyameshi.form;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReservationRegisterForm {
	private Integer shopId;
    
    private Integer userId;   
    
    private Integer numberOfPeople;
        
    private String reservationDate;    
        
    private String reservationTime;    

}
