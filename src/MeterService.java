import java.io.File;
import java.io.FileInputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class MeterService {
	public static void main(String[] args) throws Exception {
		Scanner sc = new Scanner(new FileInputStream(new File(new Scanner(System.in).nextLine())));
		sc.nextLine();
		List<Meter> meters = new ArrayList<>();
		Map<String, TreeMap<LocalDateTime, String>> meterWithDateStatusMap = new HashMap<>();

		while (sc.hasNext()) {
			String input = sc.nextLine();
			String inputsArray[] = input.split(",");
			meters.add(new Meter(inputsArray[0], inputsArray[1], inputsArray[2], inputsArray[3]));
		}
		meterWithDateStatusMap = meters.stream()
				.collect(Collectors.groupingBy(Meter::getMeterId, Collectors.toMap(
						meter -> LocalDateTime.parse(meter.getDate(), DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")),
						Meter::getStatus, (e1, e2) -> e1, TreeMap::new)));
		Map<String, List<LocalDate>> disconnectedMeterMap = new HashMap<>();
		for (Entry<String, TreeMap<LocalDateTime, String>> dateStatusEntry : meterWithDateStatusMap.entrySet()) {
			TreeMap<LocalDateTime, String> dateStatusMap = dateStatusEntry.getValue();
			Map<LocalDate, Map<LocalTime, String>> temp2 = dateStatusMap.entrySet().stream()
					.collect(Collectors.groupingBy(entry -> entry.getKey().toLocalDate(), TreeMap::new,
							Collectors.toMap(entry -> entry.getKey().toLocalTime(), entry -> entry.getValue(),
									(e1, e2) -> e1, TreeMap::new)));
			List<LocalDate> dates = new ArrayList<>();
			List<Entry<LocalDate, Map<LocalTime, String>>> dummyList = new ArrayList<>(temp2.entrySet());
			for (int i = 0; i < dummyList.size() - 1; i++) {
				Entry<LocalDate, Map<LocalTime, String>> firstNode = dummyList.get(i);
				Entry<LocalDate, Map<LocalTime, String>> secondNode = dummyList.get(i + 1);
				for (int j = 1; j <= Period.between(firstNode.getKey(), secondNode.getKey()).getDays(); j++) {
					Entry<LocalTime, String> e = new ArrayList<>(firstNode.getValue().entrySet())
							.get(firstNode.getValue().size() - 1);
					if (e.getValue().equalsIgnoreCase("Disconnected")) {
						if (!temp2.containsKey(firstNode.getKey().plusDays(j))) {
							dates.add(firstNode.getKey().plusDays(j));
						} else {
							if (!temp2.get(firstNode.getKey().plusDays(j)).entrySet().stream().map(Entry::getValue)
									.anyMatch(status -> status.equalsIgnoreCase("Connected"))) {
								dates.add(firstNode.getKey().plusDays(j));
							}
						}
					}
				}
			}
			disconnectedMeterMap.put(dateStatusEntry.getKey(), dates);
		}
		for (Entry<String, List<LocalDate>> disconnectedMeter : disconnectedMeterMap.entrySet()) {
			if (disconnectedMeter.getValue() != null && !disconnectedMeter.getValue().isEmpty()){
				System.out.print("Meter Id :" + disconnectedMeter.getKey() + " disconnected for Dates : ");
				System.out.println(disconnectedMeter.getValue());
			}
		}
	}
}
