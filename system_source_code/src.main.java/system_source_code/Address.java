package system_source_code;

public class Address {

	private int houseNumber;
	private String streetName;
	private int zipCode;
	
	
	Address createAddress(String address)
	{
		Object[] addressElements =  address.split(" ");
		Address newaddress =  new Address();
		newaddress.setHouseNumber(Integer.parseInt((String) addressElements[0]));
		newaddress.setStreetName(getString(addressElements));
		newaddress.setZipCode(Integer.parseInt((String) addressElements[addressElements.length-1]));
		
		return newaddress;
		
	}

	private String getString(Object[] addressElements) {
		String returnString = new String();
		for (int i = 1; i < addressElements.length-1; i++) {
			returnString = returnString.concat(" "+(String)addressElements[i]);
		}
		return returnString;
	}

	public int getHouseNumber() {
		return houseNumber;
	}

	public void setHouseNumber(int houseNumber) {
		this.houseNumber = houseNumber;
	}

	public String getStreetName() {
		return streetName;
	}

	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}

	public int getZipCode() {
		return zipCode;
	}

	public void setZipCode(int zipCode) {
		this.zipCode = zipCode;
	}

}
