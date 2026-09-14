import java.util.Locale;
import java.util.LinkedHashMap;
import java.util.Map;
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
		appendCategoryBreakdown(chart);
		chart.append('\n');
		appendSection(chart, "INCOME", expectedIncome, actualIncome, largestValue);
		chart.append('\n');
		chart.append(String.format(Locale.US, "Expense difference: %+.2f (budget - actual)%n",
				getExpenseVariance()));
		chart.append(String.format(Locale.US, "Income difference:  %+.2f (actual - budget)%n",
				getIncomeVariance()));
		return chart.toString();
	}

	private void appendCategoryBreakdown(StringBuilder chart) {
		Map<String, Double> budgetedByCategory = toCategoryMap(
				budgetExpenses.getOtherExpenseSources(), budgetExpenses.getAmounts());
		Map<String, Double> actualByCategory = toCategoryMap(
				expenses.getExpensesCategories(), expenses.getAmounts());
		Map<String, Double> categories = new LinkedHashMap<>(budgetedByCategory);
		actualByCategory.keySet().forEach(category -> categories.putIfAbsent(category, 0.0));

		chart.append("Expenses by category\n");
		chart.append(String.format(Locale.US, "  %-18s %12s %12s %12s%n",
				"Category", "Budgeted", "Actual", "Difference"));
		if (categories.isEmpty()) {
			chart.append("  None\n");
			return;
		}

		for (String category : categories.keySet()) {
			double budgeted = budgetedByCategory.getOrDefault(category, 0.0);
			double actual = actualByCategory.getOrDefault(category, 0.0);
			chart.append(String.format(Locale.US, "  %-18s %12.2f %12.2f %12.2f%n",
					category, budgeted, actual, budgeted - actual));
		}
	}

	private Map<String, Double> toCategoryMap(java.util.ArrayList<String> categories,
			java.util.ArrayList<Double> amounts) {
		Map<String, Double> result = new LinkedHashMap<>();
		for (int index = 0; index < categories.size(); index++) {
			result.put(categories.get(index), amounts.get(index));
		}
		return result;
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
