import java.util.*;

public class Setup{
	//Attributes
	private double income;
	private double expenses;
	private String[] categoriesExpenses;
	private String[] accountsIncome;
	private int categories;
	private int accounts;
	
	//Constructor to set everything to default
	public Setup(int categories, int accounts){
		income = 0;
		expenses  = 0;
	}
	
	public void setIncome(double income){
		this.income = income;
	}
	
	public double getIncome(){
		return income;
	}
	
	public void setCategories(int categories){
		this.categories = categories;
		categoriesExpenses = new String[categories];
	}
	
	public int getCategories(){
		return categories;
	}
	
	public void setAccounts(int accounts){
		this.accounts = accounts;
		accountsIncome = new String[accounts];
	}
	
	public int getAccounts(){
		return accounts;
	}
	
	public void setCategoryName(int index, String name){
		categoriesExpenses[index] = name;
	}
	
	public String getCategoryName (int index){
		return categoriesExpenses[index];
	}
	
	public void setAccountName(int index, String name){
		accountsIncome[index] = name;
	}
	
	public String getAccountName(int index){
		return accountsIncome[index];
	}
	
}