import java.util.ArrayList;
import java.util.Date;

public class Expenses {
    private ArrayList<String> expensesCategories; // a list of other expense sources
    private ArrayList<Double> amounts; // a list of amounts corresponding to the other expense sources
    private Date beginDate;
    private String endDate;

    public Expenses(ArrayList<String> expensesCategories, ArrayList<Double> amounts, Date beginDate, String endDate) {
        this.expensesCategories = expensesCategories;
        this.amounts = amounts;
        this.beginDate = beginDate;
        this.endDate = endDate;
    }


    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Date getBeginDate() {
        return beginDate;
    }   

    public String getEndDate() {
        return endDate;
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
