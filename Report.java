import java.util.Locale;
import java.util.Objects;

public class Report {
	private static final int BAR_WIDTH = 40;

	private final BudgetExpenses budgetExpenses;
	private final BudgetIncome budgetIncome;
	private final Expenses expenses;
	private final Income income;

	public Report(BudgetExpenses budgetExpenses, BudgetIncome budgetIncome,
				  Expenses expenses, Income income) {
		this.budgetExpenses = Objects.requireNonNull(budgetExpenses, "budgetExpenses");
		this.budgetIncome = Objects.requireNonNull(budgetIncome, "budgetIncome");
		this.expenses = Objects.requireNonNull(expenses, "expenses");
		this.income = Objects.requireNonNull(income, "income");
	}

	public double getExpectedExpenses() {
		return budgetExpenses.totalExpenses();
	}

	public double getActualExpenses() {
		return expenses.getTotalExpenses();
	}

	public double getExpectedIncome() {
		return budgetIncome.totalIncomeAfterTax();
	}

	public double getActualIncome() {
		return income.calculateTotalIncome();
	}

	public double getExpenseVariance() {
		return getExpectedExpenses() - getActualExpenses();
	}

	public double getIncomeVariance() {
		return getActualIncome() - getExpectedIncome();
	}

	public String generateChart() {
		double expectedExpenses = getExpectedExpenses();
		double actualExpenses = getActualExpenses();
		double expectedIncome = getExpectedIncome();
		double actualIncome = getActualIncome();
		double largestValue = Math.max(Math.max(expectedExpenses, actualExpenses),
				Math.max(expectedIncome, actualIncome));

		StringBuilder chart = new StringBuilder();
		chart.append("BUDGET REPORT\n");
		chart.append("=============\n");
		appendSection(chart, "EXPENSES", expectedExpenses, actualExpenses, largestValue);
		chart.append('\n');
		appendSection(chart, "INCOME", expectedIncome, actualIncome, largestValue);
		chart.append('\n');
		chart.append(String.format(Locale.US, "Expense difference: %+.2f (budget - actual)%n",
				getExpenseVariance()));
		chart.append(String.format(Locale.US, "Income difference:  %+.2f (actual - budget)%n",
				getIncomeVariance()));
		return chart.toString();
	}

	public void printChart() {
		System.out.print(generateChart());
	}

	private void appendSection(StringBuilder chart, String title, double expected,
							   double actual, double largestValue) {
		chart.append(title).append('\n');
		appendBar(chart, "Expected", expected, largestValue);
		appendBar(chart, "Actual", actual, largestValue);
	}

	private void appendBar(StringBuilder chart, String label, double value, double largestValue) {
		int barLength = largestValue <= 0 ? 0 : (int) Math.round(value / largestValue * BAR_WIDTH);
		chart.append(String.format(Locale.US, "%-8s %10.2f | %s%n", label, value,
				"#".repeat(Math.max(0, barLength))));
	}
}
