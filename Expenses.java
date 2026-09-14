import java.util.ArrayList;

public class Expenses {
    private ArrayList<String> expensesCategories; // a list of other expense sources
    private ArrayList<Double> amounts; // a list of amounts corresponding to the other expense sources

    public Expenses(ArrayList<String> expensesCategories, ArrayList<Double> amounts) {
        this.expensesCategories = expensesCategories;
        this.amounts = amounts;
    }

    public ArrayList<String> getExpensesCategories() {
        return expensesCategories;
    }

    public ArrayList<Double> getAmounts() {
        return amounts;
    }

    public void setExpensesCategories(ArrayList<String> expensesCategories) {
        this.expensesCategories = expensesCategories;
    }

    public void setAmounts(ArrayList<Double> amounts) {
        this.amounts = amounts;
    }

    public double getTotalExpenses() {
        double total = 0.0;
        for (double amount : amounts) {
            total += amount;
        }
        return total;
    }

    


}
