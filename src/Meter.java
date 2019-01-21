
public class Meter implements Comparable<Meter> {
	private String sequenceId;
	private String meterId;
	private String date;
	private String status;

	public Meter(String sequenceId, String meterId, String date, String status) {
		super();
		this.sequenceId = sequenceId;
		this.meterId = meterId;
		this.date = date;
		this.status = status;
	}

	public String getSequenceId() {
		return this.sequenceId;
	}

	public void setSequenceId(String sequenceId) {
		this.sequenceId = sequenceId;
	}

	public String getMeterId() {
		return this.meterId;
	}

	public void setMeterId(String meterId) {
		this.meterId = meterId;
	}

	public String getDate() {
		return this.date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public int compareTo(Meter meter) {
		return this.meterId.compareTo(meter.getMeterId());
	}

	@Override
	public String toString() {
		return "Meter [sequenceId=" + this.sequenceId + ", meterId=" + this.meterId + ", date=" + this.date
				+ ", status=" + this.status + "]";
	}
	
}
