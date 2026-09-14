import java.util.ArrayList;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        // Create an instance of Income
        ArrayList<String> incomeSources = new ArrayList<>();
        incomeSources.add("Salary");
        incomeSources.add("Freelance");
        ArrayList<Double> incomeAmounts = new ArrayList<>();
        incomeAmounts.add(5000.0);
        incomeAmounts.add(1500.0);
        Income income = new Income(6500.0, new Date(), "2024-12-31", 0.2, incomeSources, incomeAmounts);

        // Create an instance of Expenses
        ArrayList<String> expenseCategories = new ArrayList<>();
        expenseCategories.add("Rent");
        expenseCategories.add("Utilities");
        ArrayList<Double> expenseAmounts = new ArrayList<>();
        expenseAmounts.add(1200.0);
        expenseAmounts.add(300.0);
        Expenses expenses = new Expenses(expenseCategories, expenseAmounts);

        // Display total income and expenses
        System.out.println("Total Income: " + income.getTotalIncome());
        System.out.println("Total Expenses: " + expenses.getTotalExpenses());
    }

}
