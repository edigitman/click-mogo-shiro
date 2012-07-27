package ro.shiro.custom;

import org.apache.click.control.Option;
import org.apache.click.control.Select;

public class TitleSelect extends Select {

	public TitleSelect(String string) {
		super(string);
	}

	private static final long serialVersionUID = 6839206003101860416L;

	{
		Option def = new Option("-", "choose");
		setDefaultOption(def);
		add(def);
		add(new Option("mr", "Mr."));
		add(new Option("ms", "Ms."));
		add(new Option("mis", "Miss."));
	}

}
