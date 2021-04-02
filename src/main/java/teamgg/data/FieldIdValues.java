package teamgg.data;

public class FieldIdValues {

	private FieldIdsEnum fieldId;
	private Object value;

	public FieldIdValues(FieldIdsEnum fieldId, Object value) {
		setFieldId(fieldId);
		setValue(value);
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public FieldIdsEnum getFieldId() {
		return fieldId;
	}

	public void setFieldId(FieldIdsEnum fieldId) {
		this.fieldId = fieldId;
	}

}
