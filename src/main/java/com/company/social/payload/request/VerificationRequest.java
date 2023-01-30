package com.company.social.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class VerificationRequest {
 
	private String username;

	private String password; 

	
    private String zipCode;
    
}
