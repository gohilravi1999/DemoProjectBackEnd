package com.myProject.inputValidation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 Class that check validation of input
 */
public class InputValidation {
	/*
	 	Method that will check given name is valid or not.
	 	@param entity must not be null
	 	@return boolean value
	 */
	public static final boolean isValidName(String name)
	{
		if(name==null)
		{
			return false;
		}
		else if(name.length() > 1)
		{
			for(char characterOfName : name.toCharArray()){
		        if(Character.isDigit(characterOfName)){
		            return false;
		        }
			}
		return true;
		}
		else if(name.length() < 2)
		{
			return false;
		}
		else 
		{
			return true;
		}	
	}
	
	/*
	 Method which will check validation of Email
	 
	 @param must not be null
	 @return boolean value if input is not valid
	 */
	public static final boolean isValidEmail(String email) {
		
		String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher match = pattern.matcher(email);
		
		if(email == null)
		{
			return false;
		}	
		
        if(!match.matches()) {
			return false;
		}
        else {
        	return true;
        }
	}
	
	public static final boolean isValidPassword(String password) {
		
		if(password==null) {
			return false;
		}
		else if(password.length()>=8)
		{
		        Pattern lowerCase = Pattern.compile("[a-z]");
		        Pattern upperCase = Pattern.compile("[A-Z]");
		        Pattern digit = Pattern.compile("[0-9]");
		        Pattern special = Pattern.compile ("[!@#$%&*()_+=|<>?{}\\[\\]~-]");

		        Matcher hasLower = lowerCase.matcher(password);
		        Matcher hasUpper = upperCase.matcher(password);
		        Matcher hasDigit = digit.matcher(password);
		        Matcher hasSpecial = special.matcher(password);

		        return hasLower.find() && hasUpper.find() && hasDigit.find() && hasSpecial.find();

		}
		else {
			return false;
		}
	}	
}