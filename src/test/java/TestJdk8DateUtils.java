import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

public class TestJdk8DateUtils {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

         LocalDate today = LocalDate.now();
         System.out.println("today:" + today);
         System.out.println(today.format(DateTimeFormatter.ofPattern("yyyy年MM月dd日")));
         System.out.println(today.format(DateTimeFormatter.BASIC_ISO_DATE));
         
         LocalDateTime now = LocalDateTime.now();
         System.out.println("now:" + now);
         System.out.println(now.format(DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:SS")));
         System.out.println(now.format(DateTimeFormatter.BASIC_ISO_DATE));
         
         Instant timestamp = Instant.now();
         //default format
         System.out.println("Default format of Instant="+timestamp);
         
         System.out.println("Year "+today.getYear()+" is Leap Year? "+today.isLeapYear());
         
         System.out.println("Today is before 01/01/2015? "+today.isBefore(LocalDate.of(2015,1,1)));
         
         System.out.println("Current Time="+today.atTime(LocalTime.now())); 
         
         System.out.println("10 days after today will be "+today.plusDays(10));
         
         System.out.println("3 weeks after today will be "+today.plusWeeks(3));
         
         System.out.println("20 months after today will be "+today.plusMonths(20));
         System.out.println("10 days before today will be "+today.minusDays(10));
         
         System.out.println("3 weeks before today will be "+today.minusWeeks(3));
         System.out.println("20 months before today will be "+today.minusMonths(20));
         System.out.println("First date of this month= "+today.with(TemporalAdjusters.firstDayOfMonth()));
         
         LocalDate lastDayOfYear = today.with(TemporalAdjusters.lastDayOfYear());
         
         System.out.println("Last date of this year= "+lastDayOfYear);
         Period period = today.until(lastDayOfYear);
         System.out.println("Period Format= "+period);
         System.out.println("Months remaining in the year= "+period.getMonths());
         
         LocalDate today1 = LocalDate.of(2016, 05, 28);
         LocalDate today2 = LocalDate.of(2016, 06, 03);
         System.out.println("today1 Format= "+today1.format(DateTimeFormatter.BASIC_ISO_DATE));
         Period pe = Period.between(today1, today2);
         today1.toEpochDay();
         pe.getDays();
         System.out.println("Period today1= "+today1.toEpochDay());
         System.out.println("Period today2= "+today2.toEpochDay());
         System.out.println("Period today2-today1= "+(today2.toEpochDay()-today1.toEpochDay()));
         String aa = "20160803";
         System.out.println("today1 ee= "+Integer.parseInt(aa.substring(0, 4)));
         
         
	}
	
	
	

}
