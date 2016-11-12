package dateadog.dateadog.schemaobjects;

import java.util.Arrays;

import dateadog.dateadog.wrappers.StringArrayWrapper;

public class Options {
    private StringArrayWrapper[] option;

    public Options() {

    }

    public String[] getOptions() {
	String[] array = new String[option.length];
	for (int i = 0; i < option.length; i++) {
	    array[i] = option[i].toString();
	}
	return array;
    }

    public void setOptions(String[] options) {
	option = new StringArrayWrapper[options.length];
	for (int i = 0; i < options.length; i++) {
	    option[i] = new StringArrayWrapper(options[i]);
	}
    }

    @Override
    public String toString() {
	return Arrays.toString(option);
    }
}
