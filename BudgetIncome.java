import java.util.ArrayList;
import java.util.Date;

public class BudgetIncome {
    private double totalIncomeExpected;
    private double taxRateProjected;
    private ArrayList<String> otherIncomeSourcesExpected; // a list of other income sources
    private ArrayList<Double> amountsExpected; // a list of amounts corresponding to the other income sources
    private String endDateExpected;
    private Date dateExpected;

    public BudgetIncome(double totalIncomeExpected, double taxRateProjected, ArrayList<String> otherIncomeSourcesExpected, ArrayList<Double> amountsExpected, String endDateExpected, Date dateExpected) {
        this.totalIncomeExpected = totalIncomeExpected;
        this.taxRateProjected = taxRateProjected;
        this.otherIncomeSourcesExpected = otherIncomeSourcesExpected;
        this.amountsExpected = amountsExpected;
        this.endDateExpected = endDateExpected;
        this.dateExpected = dateExpected;
    }

    public double getTaxRateProjected() {
        return taxRateProjected;
    }

    public ArrayList<String> getOtherIncomeSourcesExpected() {
        return otherIncomeSourcesExpected;
    }

    public ArrayList<Double> getAmountsExpected() {
        return amountsExpected;
    }

    public String getEndDateExpected() {
        return endDateExpected;
    }

    public Date getDateExpected() {
        return dateExpected;
    }

    public void setTaxRateProjected(double taxRateProjected) {
        this.taxRateProjected = taxRateProjected;
    }

    public void setOtherIncomeSourcesExpected(ArrayList<String> otherIncomeSourcesExpected) {
        this.otherIncomeSourcesExpected = otherIncomeSourcesExpected;
    }

    public void setAmountsExpected(ArrayList<Double> amountsExpected) {
        this.amountsExpected = amountsExpected;
    }

    public void setEndDateExpected(String endDateExpected) {
        this.endDateExpected = endDateExpected;
    }

    public void setDateExpected(Date dateExpected) {
        this.dateExpected = dateExpected;
    }

    public double getTotalIncome() {
        return totalIncomeExpected;
    }

    public void setTotalIncome(double totalIncomeExpected) {
        this.totalIncomeExpected = totalIncomeExpected;
    }

    public double getTotalIncomeExpected() {
        return totalIncomeExpected;
    }

    public void setTotalIncomeExpected(double totalIncomeExpected) {
        this.totalIncomeExpected = totalIncomeExpected;
    }
    
    public double totalIncomeAfterTax() {
        return totalIncomeExpected * (1 - taxRateProjected) + calculateOtherIncome();
    }

    private double calculateOtherIncome() {
        double total = 0;
        for (Double amount : amountsExpected) {
            total += amount;
        }
        return total;
    }

}
