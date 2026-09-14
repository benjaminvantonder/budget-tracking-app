import java.util.ArrayList;
import java.util.Date;

public class BudgetExpenses {
    private double totalExpensesExpected;
    private ArrayList<String> otherExpenseSourcesExpected; // a list of other expense sources
    private ArrayList<Double> amountsExpected; // a list of amounts corresponding to the other expense sources
    private Date dateExpected;
    private String endDateExpected;

    public BudgetExpenses(double totalExpensesExpected, ArrayList<String> otherExpenseSourcesExpected, ArrayList<Double> amountsExpected, Date dateExpected, String endDateExpected) {
        this.totalExpensesExpected = totalExpensesExpected;
        this.otherExpenseSourcesExpected = otherExpenseSourcesExpected;
        this.amountsExpected = amountsExpected;
        this.dateExpected = dateExpected;
        this.endDateExpected = endDateExpected;
    }

    public double getTotalExpenses() {
        return totalExpensesExpected;
    }

    public void setTotalExpenses(double totalExpensesExpected) {
        this.totalExpensesExpected = totalExpensesExpected;
    }

    public ArrayList<String> getOtherExpenseSources() {
        return otherExpenseSourcesExpected;
    }

    public void setOtherExpenseSources(ArrayList<String> otherExpenseSourcesExpected) {
        this.otherExpenseSourcesExpected = otherExpenseSourcesExpected;
    }

    public ArrayList<Double> getAmounts() {
        return amountsExpected;
    }

    public void setAmounts(ArrayList<Double> amountsExpected) {
        this.amountsExpected = amountsExpected;
    }

    public Date getDate() {
        return dateExpected;
    }

    public void setDate(Date dateExpected) {
        this.dateExpected = dateExpected;
    }

    public String getEndDate() {
        return endDateExpected;
    }

    public void setEndDate(String endDateExpected) {
        this.endDateExpected = endDateExpected;
    }

    public double totalExpenses() {
        double total = 0.0;
        for (double amount : amountsExpected) {
            total += amount;
        }
        return total;
    }
}
