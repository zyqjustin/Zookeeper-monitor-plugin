package com.zk.monitor.metrics;

import java.util.Arrays;
import java.util.HashMap;

public class MetricData {

	private String name;
	
	private int count;
	
	private float value;
	
	private float minValue;
	
	private float maxValue;

	private float sumOfSquares;
	
	public MetricData(String name, Number value) {
		this(name, 1, value.floatValue(), value.floatValue(), value.floatValue(), value.floatValue() * value.floatValue());
	}

	public MetricData(String name, int count, Number value, Number minValue, Number maxValue, Number sumOfSquares) {
		this.name = name;
		this.count = count;
		this.value = value.floatValue();
		this.minValue = minValue.floatValue();
		this.maxValue = maxValue.floatValue();
		this.sumOfSquares = sumOfSquares.floatValue();
		
		convertValues();
	}
	
	public void aggregateWith(MetricData other) {
		count += other.count;
		value += other.value;
		minValue = Math.min(minValue, other.minValue);
		maxValue = Math.max(maxValue, other.maxValue);
		sumOfSquares += other.sumOfSquares;
		
		convertValues();
	}
	
    private void convertValues() {
        convertInfiniteValues();
        convertNaNValues();
    }
    
    private void convertInfiniteValues() {
        if (Float.POSITIVE_INFINITY == value) { value = Float.MAX_VALUE; }
        if (Float.POSITIVE_INFINITY == maxValue) { maxValue = Float.MAX_VALUE; }
        if (Float.POSITIVE_INFINITY == minValue) { minValue = Float.MAX_VALUE; }
        if (Float.POSITIVE_INFINITY == sumOfSquares) { sumOfSquares = Float.MAX_VALUE; }
        
        if (Float.NEGATIVE_INFINITY == value) { value = Float.MIN_VALUE; }
        if (Float.NEGATIVE_INFINITY == maxValue) { maxValue = Float.MIN_VALUE; }
        if (Float.NEGATIVE_INFINITY == minValue) { minValue = Float.MIN_VALUE; }
        if (Float.NEGATIVE_INFINITY == sumOfSquares) { sumOfSquares = Float.MIN_VALUE; }
    }
    
    private void convertNaNValues() {
        if (Float.isNaN(value)) { value = 0.0f; }
        if (Float.isNaN(maxValue)) { maxValue = 0.0f; }
        if (Float.isNaN(minValue)) { minValue = 0.0f; }
        if (Float.isNaN(sumOfSquares)) { sumOfSquares = 0.0f; }
    }
    
    public void serialize(HashMap<String, Object> data) {
    	data.put(name, Arrays.<Number>asList(value, count, minValue, maxValue, sumOfSquares));
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (null == obj) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		MetricData other = (MetricData) obj;
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		return true;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Metric: ").append(name).append(", ");
		sb.append("count: ").append(count).append(", ");
		sb.append("value: ").append(value).append(", ");
		sb.append("minValue: ").append(minValue).append(", ");
		sb.append("maxValue: ").append(maxValue).append(", ");
		sb.append("sumOfSquares: ").append(sumOfSquares);
		return sb.toString();
	};

}
