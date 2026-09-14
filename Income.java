import java.util.Date;
import java.util.ArrayList;

public class Income {
    private double TotalIncome;
    private Date date;
    private String endDate;
    private double taxRate;
    private ArrayList<String> otherIncomeSources;
    private ArrayList<Double> amounts;

    public Income(double TotalIncome, Date date, String endDate, double taxRate, ArrayList<String> otherIncomeSources, ArrayList<Double> amounts) {
        this.TotalIncome = TotalIncome;
        this.date = date;
        this.endDate = endDate;
        this.taxRate = taxRate;
        this.otherIncomeSources = otherIncomeSources;
        this.amounts = amounts;
    }

    public double getTotalIncome() {
        return TotalIncome;
    }

    public ArrayList<Double> getAmounts() {
        return amounts;
    }

    public Date getDate() {
        return date;
    }

    public String getEndDate() {
        return endDate;
    }

    public double getTaxRate() {
        return taxRate;
    }

    public ArrayList<String> getOtherIncomeSources() {
        return otherIncomeSources;
    }

    public void setTotalIncome(double TotalIncome) {
        this.TotalIncome = TotalIncome;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setTaxRate(double taxRate) {
        this.taxRate = taxRate;
    }

    public void setOtherIncomeSources(ArrayList<String> otherIncomeSources) {
        this.otherIncomeSources = otherIncomeSources;
    }

    public void setAmounts(ArrayList<Double> amounts) {
        this.amounts = amounts;
    }

    public double calculateTotalIncome() {
        double total = 0;
        for (double amount : amounts) {
            total += amount;
        }
        return total * (1 - taxRate) + TotalIncome;
    }
}
