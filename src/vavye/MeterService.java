package vavye;

import java.io.File;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.TreeSet;

public class MeterService {
	public static void main(String[] args) throws Exception {
		RandomAccessFile raf = new RandomAccessFile(new File("F:\\code\\vavye\\ConnectionList(1-50Meters).csv"), "rw");
		TreeSet<Meter> treeSet = new TreeSet<>();
		Meter meter = null;
		raf.readLine();
		HashMap<String, TreeMap<Date, String>> map = new HashMap<>();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		while (raf.getFilePointer() < raf.length()) {
			String input = raf.readLine();
			String arr[] = input.split(",");
			meter = new Meter(arr[0], arr[1], arr[2], arr[3]);
			if (map.containsKey(meter.getMeterId())) {
				map.get(meter.getMeterId()).put(sdf.parse(meter.getDate()), meter.getStatus());
			} else {
				TreeMap<Date, String> m = new TreeMap<>();
				m.put(sdf.parse(meter.getDate()), meter.getStatus());
				map.put(meter.getMeterId(), m);
			}
		}
		raf.close();
		Iterator<Entry<String, TreeMap<Date, String>>> iterator = map.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, TreeMap<Date, String>> gg = iterator.next();
			System.out.println(gg.getKey() + " " + gg.getValue());
			break;
		}
	}
}
