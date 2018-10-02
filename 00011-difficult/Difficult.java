import java.text.DateFormatSymbols;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;
import java.text.SimpleDateFormat;

/**
 * Dailyprogrammer: 11 - difficult
 * https://www.reddit.com/r/dailyprogrammer/comments/pwpdz/2192012_challenge_11_difficult/
 */
public class Difficult {

    private static final boolean DEBUG = true;

    private static final int[] MONTHS = {
        Calendar.JANUARY,
        Calendar.FEBRUARY,
        Calendar.MARCH,
        Calendar.APRIL,
        Calendar.MAY,
        Calendar.JUNE,
        Calendar.JULY,
        Calendar.AUGUST,
        Calendar.SEPTEMBER,
        Calendar.OCTOBER,
        Calendar.NOVEMBER,
        Calendar.DECEMBER
    };

    private static final int[] DAYS = {
        Calendar.MONDAY,
        Calendar.TUESDAY, 
        Calendar.WEDNESDAY,
        Calendar.THURSDAY,
        Calendar.FRIDAY,
        Calendar.SATURDAY,
        Calendar.SUNDAY
    };

    public static void main(String[] args) {
        CalendarOptions options = new CalendarOptions();
        //options.month = 6;
        options.adjacentMonths = 0;
        System.out.print(generateCalendar(options));
    }

    private static String generateCalendar(CalendarOptions options) {
        int year = options.year;
        if (year == Integer.MAX_VALUE) {
            year = Calendar.getInstance().get(Calendar.YEAR);
        }

        int startMonth;
        int endMonth;
        MonthHeaderStyle monthHeaderStyle;

        if (options.month != Integer.MAX_VALUE) {
            startMonth = options.month;
            endMonth = options.month;
            if (options.adjacentMonths > 0) {
                startMonth = (MONTHS.length + startMonth - options.adjacentMonths) % MONTHS.length;
                endMonth = (endMonth + options.adjacentMonths) % MONTHS.length;
            }

            monthHeaderStyle = MonthHeaderStyle.MONTH_YEAR;
        } else {
            startMonth = 0;
            endMonth = MONTHS.length - 1;
            monthHeaderStyle = MonthHeaderStyle.MONTH_ONLY;
        }

        Locale locale = options.locale;
        if (locale == null) {
            locale = Locale.getDefault();
        }

        int row = 0;
        int col = 0;
        int cols = Math.min((endMonth - startMonth + 1), options.cols);
        int rows = (int) Math.ceil((endMonth - startMonth + 1) / (double) options.cols);

        if (DEBUG) {
            System.out.println("year=" + year);
            System.out.println("startMonth=" + startMonth);
            System.out.println("endMonth=" + endMonth);
            System.out.println("cols=" + cols);
            System.out.println("rows=" + rows);
            System.out.println("locale=" + locale);
        }

        MonthFormatter[][] formatters = new MonthFormatter[rows][cols];
        for (int month = startMonth; month <= endMonth; month++) {
            MonthFormatter monthFormatter = new MonthFormatter(MONTHS[month], year, locale);
            monthFormatter.setHeaderStyle(monthHeaderStyle);
            monthFormatter.setColumnSpacing(options.monthColumnSpacing);
            monthFormatter.setColumnWidth(options.monthColumnWidth);

            formatters[row][col] = monthFormatter;
            col++;
            if (col >= cols) {
                col = 0;
                row++;
            }
        }

        int monthLineWidth = formatters[0][0].getLineWidth();
        int lineWidth = monthLineWidth * cols + options.columnSpacing * (cols - 1);
        if (DEBUG) {
            System.out.println("monthLineWidth=" + monthLineWidth);
            System.out.println("lineWidth=" + lineWidth);
            System.out.println();
        }

        StringBuilder sb = new StringBuilder();
        String rowSpacingLine = padTrim("", ' ', lineWidth, Padding.LEFT);
        String colSpacingLine = padTrim("", ' ', options.columnSpacing, Padding.LEFT);
        String emptyMonthLine = padTrim("", ' ', monthLineWidth, Padding.LEFT);

        if (monthHeaderStyle != MonthHeaderStyle.MONTH_YEAR) {
            if (DEBUG) sb.append("'");
            sb.append(padTrim(Integer.toString(year), ' ', lineWidth, Padding.CENTER));
            if (DEBUG) sb.append("'");
            sb.append("\n");
            if (options.rowSpacing > 0) {
                for (int i = 0; i < options.rowSpacing; i++) {
                    if (DEBUG) sb.append("'");
                    sb.append(rowSpacingLine);
                    if (DEBUG) sb.append("'");
                    sb.append('\n');
                }
            }
        }

        for (row = 0; row < rows; row++) {
            boolean emptyRow = false;
            while (!emptyRow) {
                String line = "";
                emptyRow = true;

                for (col = 0; col < cols; col++) {
                    String monthLine = null;
                    MonthFormatter formatter = formatters[row][col];
                    if (formatter != null) {
                        monthLine = formatter.nextLine();
                    }

                    if (monthLine != null) {
                        emptyRow = false; 
                        line += monthLine;
                    } else {
                        line += emptyMonthLine;
                    }

                    if (col != cols - 1) {
                        line += colSpacingLine;
                    }
                }

                if (!emptyRow) {
                    if (DEBUG) sb.append("'");
                    sb.append(line);
                    if (DEBUG) sb.append("'");
                    sb.append('\n');
                }
            }

            if (options.rowSpacing > 0 && row != rows - 1) {
                for (int i = 0; i < options.rowSpacing; i++) {
                    if (DEBUG) sb.append("'");
                    sb.append(rowSpacingLine);
                    if (DEBUG) sb.append("'");
                    sb.append('\n');
                }
            }
        }

        return sb.toString();
    }

    private static class CalendarOptions {

        int month = Integer.MAX_VALUE;
        int year = Integer.MAX_VALUE;

        int cols = 3;
        int columnSpacing = 2;
        int rowSpacing = 1;

        int adjacentMonths = 0;
        int monthColumnWidth = 2;
        int monthColumnSpacing = 1;

        boolean highlightCurrentDate = true;

        Locale locale = Locale.getDefault();
    }

    private enum MonthHeaderStyle { MONTH_ONLY, MONTH_YEAR };

    private static class MonthFormatter {

        private int columnWidth = 2;
        private int columnSpacing = 1;
        private MonthHeaderStyle headerStyle = MonthHeaderStyle.MONTH_ONLY;

        private int firstDayOfWeek;
        private int days;
        private int month;
        private int year;

        private String monthName;
        private String[] dayNames;

        private int startFromDayOfWeek;
        private int daysLeft;
        private int line;

        // month - zero-based
        MonthFormatter(int month, int year, Locale locale) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.YEAR, year);

            this.year = year;
            this.month = month;
            this.monthName = new SimpleDateFormat("MMMM", Locale.getDefault()).format(calendar.getTime());

            DateFormatSymbols symbols = DateFormatSymbols.getInstance(locale);
            this.dayNames = symbols.getShortWeekdays();

            for (int d = 0; d < DAYS.length; d++) {
                if (DAYS[d] == calendar.getFirstDayOfWeek()) {
                    this.firstDayOfWeek = d;
                }

                if (DAYS[d] == calendar.get(Calendar.DAY_OF_WEEK)) {
                    this.startFromDayOfWeek = d;
                }
            }

            calendar.add(Calendar.MONTH, 1);
            calendar.add(Calendar.DAY_OF_MONTH, -1);
            this.days = calendar.get(Calendar.DAY_OF_MONTH);
            this.daysLeft = this.days;

            if (DEBUG) {
                System.out.println();
                System.out.println("--MONTH--");
                System.out.println("firstDayOfWeek=" + firstDayOfWeek);
                System.out.println("startFromDayOfWeek=" + startFromDayOfWeek);
                System.out.println("days=" + days);
                System.out.println("daysNames=" + Arrays.toString(dayNames));
                System.out.println("daysLeft=" + daysLeft);
                System.out.println("month=" + month);
                System.out.println("monthName=" + monthName);
                System.out.println("year=" + year);
                System.out.println("---------");
                System.out.println();
            }
        }

        void setColumnWidth(int width) {
            this.columnWidth = width;
        }

        void setColumnSpacing(int spacing) {
            this.columnSpacing = spacing;
        }

        void setHeaderStyle(MonthHeaderStyle style) {
            this.headerStyle = style;
        }

        int getLineWidth() {
            return 7 * columnWidth + 6 * columnSpacing;
        }

        String nextLine() {
            if (daysLeft == 0) {
                return null;
            }

            line++;

            StringBuilder sb = new StringBuilder();
            if (line == 1) {
                String header;
                if (headerStyle == MonthHeaderStyle.MONTH_ONLY) {
                    header = monthName;
                } else if (headerStyle == MonthHeaderStyle.MONTH_YEAR) {
                    header = monthName + " " + year;
                } else {
                    throw new IllegalStateException("headerStyle=" + headerStyle);
                }

                sb.append(padTrim(header, ' ', getLineWidth(), Padding.CENTER));
            } else if (line == 2) {
                String spacing = padTrim("", ' ', columnSpacing, Padding.LEFT);

                int day = firstDayOfWeek;
                do {
                    sb.append(padTrim(dayNames[DAYS[day]], ' ', columnWidth, Padding.LEFT));

                    day = nextDay(day);
                    if (day != firstDayOfWeek) {
                        sb.append(spacing);
                    }
                } while (day != firstDayOfWeek);
            } else {
                String spacing = padTrim("", ' ', columnSpacing, Padding.LEFT);

                int day = firstDayOfWeek;
                while (day != startFromDayOfWeek) {
                    sb.append(padTrim("", ' ', columnWidth, Padding.LEFT));
                    sb.append(spacing);
                    day = nextDay(day);
                }

                do {
                    sb.append(padTrim(Integer.toString(days - daysLeft + 1), ' ', columnWidth, Padding.LEFT));
                    daysLeft--;

                    day = nextDay(day);
                    if (day != firstDayOfWeek) {
                        sb.append(spacing);
                    }
                } while (day != firstDayOfWeek && daysLeft > 0);    

                startFromDayOfWeek = firstDayOfWeek;
            }

            String result = sb.toString();
            result = padTrim(result, ' ', getLineWidth(), Padding.RIGHT);
            return result;
        }

        private int nextDay(int day) {
            return (day + 1) % DAYS.length;
        }
    }

    private enum Padding { LEFT, RIGHT, CENTER };

    private static String padTrim(String str, char pad, int width, Padding padding) {
        if (str.length() == width) {
            return str;
        } else if (str.length() > width) {
            return str.substring(0, width);
        } else {
            int padCount = width - str.length();

            int padLeft;
            int padRight;
            if (padding == Padding.LEFT) {
                padLeft = padCount;
                padRight = 0;
            } else if (padding == Padding.RIGHT) {
                padLeft = 0;
                padRight = padCount;
            } else if (padding == Padding.CENTER) {
                padLeft = padCount / 2;
                padRight = padCount - padLeft;
            } else {
                throw new IllegalArgumentException("padding=" + padding);
            }

            while (padLeft > 0) {
                str = pad + str;
                padLeft--;
            }

            while (padRight > 0) {
                str += pad;
                padRight--;
            }

            return str;
        }
    }
}