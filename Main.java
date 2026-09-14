import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Path DATA_FILE = Path.of("budget-data.csv");
    private static final String HEADER =
            "startDate,endDate,actualIncome,budgetedIncome,actualExpenses,budgetedExpenses";

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.println("\nBudget Tracker");
                System.out.println("1. Add and save a period");
                System.out.println("2. Create a report");
                System.out.println("3. Exit");
                System.out.print("Choose an option: ");

                String option = scanner.nextLine().trim();
                if (option.equals("1")) {
                    addPeriod(scanner);
                } else if (option.equals("2")) {
                    createReport(scanner);
                } else if (option.equals("3")) {
                    break;
                } else {
                    System.out.println("Please choose 1, 2, or 3.");
                }
            }
        }
    }

    private static void addPeriod(Scanner scanner) {
        LocalDate startDate = askDate(scanner, "Start date (YYYY-MM-DD): ");
        LocalDate endDate = askDate(scanner, "End date (YYYY-MM-DD): ");
        while (endDate.isBefore(startDate)) {
            System.out.println("The end date cannot be before the start date.");
            endDate = askDate(scanner, "End date (YYYY-MM-DD): ");
        }

        double actualIncome = askAmount(scanner, "Actual income after tax: ");
        double budgetedIncome = askAmount(scanner, "Budgeted income after tax: ");
        double actualExpenses = askAmount(scanner, "Actual expenses: ");
        double budgetedExpenses = askAmount(scanner, "Budgeted expenses: ");

        BudgetEntry entry = new BudgetEntry(startDate, endDate, actualIncome,
                budgetedIncome, actualExpenses, budgetedExpenses);
        try {
            saveEntry(entry);
            System.out.println("Saved to " + DATA_FILE.toAbsolutePath());
        } catch (IOException exception) {
            System.out.println("Could not save the entry: " + exception.getMessage());
        }
    }

    private static void createReport(Scanner scanner) {
        LocalDate reportStart = askDate(scanner, "Report start date (YYYY-MM-DD): ");
        LocalDate reportEnd = askDate(scanner, "Report end date (YYYY-MM-DD): ");
        while (reportEnd.isBefore(reportStart)) {
            System.out.println("The report end date cannot be before the start date.");
            reportEnd = askDate(scanner, "Report end date (YYYY-MM-DD): ");
        }
        LocalDate selectedReportEnd = reportEnd;

        try {
            List<BudgetEntry> entries = loadEntries();
            List<BudgetEntry> matchingEntries = entries.stream()
                    .filter(entry -> !entry.startDate.isBefore(reportStart)
                            && !entry.endDate.isAfter(selectedReportEnd))
                    .toList();

            if (matchingEntries.isEmpty()) {
                System.out.println("No saved entries were found in that time frame.");
                return;
            }

            Report report = buildReport(matchingEntries);
            report.printChart();
        } catch (IOException | IllegalArgumentException exception) {
            System.out.println("Could not create the report: " + exception.getMessage());
        }
    }

    private static Report buildReport(List<BudgetEntry> entries) {
        double actualIncome = 0;
        double budgetedIncome = 0;
        double actualExpenses = 0;
        double budgetedExpenses = 0;

        for (BudgetEntry entry : entries) {
            actualIncome += entry.actualIncome;
            budgetedIncome += entry.budgetedIncome;
            actualExpenses += entry.actualExpenses;
            budgetedExpenses += entry.budgetedExpenses;
        }

        Date startDate = toDate(entries.get(0).startDate);
        String endDate = entries.get(entries.size() - 1).endDate.toString();
        ArrayList<String> noSources = new ArrayList<>();

        BudgetExpenses budgetExpenses = new BudgetExpenses(budgetedExpenses,
                new ArrayList<>(), amounts(budgetedExpenses), startDate, endDate);
        BudgetIncome budgetIncome = new BudgetIncome(budgetedIncome, 0,
                new ArrayList<>(), new ArrayList<>(), endDate, startDate);
        Expenses expenses = new Expenses(new ArrayList<>(), amounts(actualExpenses),
                startDate, endDate);
        Income income = new Income(actualIncome, startDate, endDate, 0,
                noSources, new ArrayList<>());

        return new Report(budgetExpenses, budgetIncome, expenses, income);
    }

    private static ArrayList<Double> amounts(double amount) {
        ArrayList<Double> amounts = new ArrayList<>();
        amounts.add(amount);
        return amounts;
    }

    private static void saveEntry(BudgetEntry entry) throws IOException {
        boolean needsHeader = Files.notExists(DATA_FILE) || Files.size(DATA_FILE) == 0;
        String line = String.format(java.util.Locale.US, "%s,%s,%.2f,%.2f,%.2f,%.2f%n",
                entry.startDate, entry.endDate, entry.actualIncome, entry.budgetedIncome,
                entry.actualExpenses, entry.budgetedExpenses);
        if (needsHeader) {
            line = HEADER + System.lineSeparator() + line;
        }
        Files.writeString(DATA_FILE, line, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    }

    private static List<BudgetEntry> loadEntries() throws IOException {
        if (Files.notExists(DATA_FILE)) {
            return new ArrayList<>();
        }

        List<BudgetEntry> entries = new ArrayList<>();
        for (String line : Files.readAllLines(DATA_FILE)) {
            if (line.isBlank() || line.startsWith("startDate,")) {
                continue;
            }
            String[] values = line.split(",", -1);
            if (values.length != 6) {
                throw new IllegalArgumentException("Invalid row in " + DATA_FILE + ": " + line);
            }
            entries.add(new BudgetEntry(LocalDate.parse(values[0]), LocalDate.parse(values[1]),
                    Double.parseDouble(values[2]), Double.parseDouble(values[3]),
                    Double.parseDouble(values[4]), Double.parseDouble(values[5])));
        }
        return entries;
    }

    private static LocalDate askDate(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return LocalDate.parse(scanner.nextLine().trim());
            } catch (java.time.format.DateTimeParseException exception) {
                System.out.println("Enter the date using YYYY-MM-DD.");
            }
        }
    }

    private static double askAmount(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                double amount = Double.parseDouble(scanner.nextLine().trim());
                if (amount < 0) {
                    throw new NumberFormatException();
                }
                return amount;
            } catch (NumberFormatException exception) {
                System.out.println("Enter a non-negative number.");
            }
        }
    }

    private static Date toDate(LocalDate date) {
        return Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    private static final class BudgetEntry {
        private final LocalDate startDate;
        private final LocalDate endDate;
        private final double actualIncome;
        private final double budgetedIncome;
        private final double actualExpenses;
        private final double budgetedExpenses;

        private BudgetEntry(LocalDate startDate, LocalDate endDate, double actualIncome,
                            double budgetedIncome, double actualExpenses,
                            double budgetedExpenses) {
            this.startDate = startDate;
            this.endDate = endDate;
            this.actualIncome = actualIncome;
            this.budgetedIncome = budgetedIncome;
            this.actualExpenses = actualExpenses;
            this.budgetedExpenses = budgetedExpenses;
        }
    }
}
