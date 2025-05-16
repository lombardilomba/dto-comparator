package dev.llombardi.comparator.comparison;

public class ChangedField {

	private boolean monitored;

	private String displayName;

	private String path;

	private Object previousValue;

	private Object newValue;

	public ChangedField(String path, Object previousValue, Object newValue, boolean monitored, String displayName) {
        this.path = path;
        this.previousValue = previousValue;
        this.newValue = newValue;
        this.monitored = monitored;
        this.displayName = displayName;
    }

	public boolean isMonitored() {
		return monitored;
	}

	public void setMonitored(boolean monitored) {
		this.monitored = monitored;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Object getPreviousValue() {
		return previousValue;
	}

	public void setPreviousValue(Object previousValue) {
		this.previousValue = previousValue;
	}

	public Object getNewValue() {
		return newValue;
	}

	public void setNewValue(Object newValue) {
		this.newValue = newValue;
	}

	@Override
	public String toString() {
		String name = (displayName != null ? displayName : path);
		return String.format("%s: '%s' -> '%s' => (monitored=%b)", name, previousValue, newValue, monitored);
	}
}
