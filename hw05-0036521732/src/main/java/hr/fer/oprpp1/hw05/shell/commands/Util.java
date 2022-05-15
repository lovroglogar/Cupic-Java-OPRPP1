package hr.fer.oprpp1.hw05.shell.commands;


public class Util {
	
	public static String[] splitArguments(String arguments) {
		String[] splitArguments;
		
		if(arguments.startsWith("\""))
			splitArguments = Util.argumentsWithQuotedPath(arguments);
		else
			splitArguments = arguments.split(" ");
		
		return splitArguments;
	}

	private static String[] argumentsWithQuotedPath(String a) {
		String[] arguments = null;
		StringBuilder sb = new StringBuilder();
		
		int j = 0;
		String[] temp = new String[a.split(" ").length];
		
		int i;
		boolean outOfQuotes = false;
		for(i = 1; i < a.length(); i++) {
			if(a.charAt(i) == '"') {
				outOfQuotes = !outOfQuotes;
				if(outOfQuotes) {
					temp[j] = sb.toString();
					j++;
					sb = new StringBuilder();
				}
				continue;
			}
			
			if(outOfQuotes && Character.isWhitespace(a.charAt(i))) {
				if(sb.isEmpty())
					continue;
				temp[j] = sb.toString();
				j++;
				sb = new StringBuilder();
				continue;
			}
			
			if(!outOfQuotes && a.charAt(i) == '\\' && (a.charAt(i + 1) == '"' || a.charAt(i + 1) == '\\')) {
				sb.append(a.charAt(i + 1));
				i++;
			} else
				sb.append(a.charAt(i));
		}
		if(!sb.isEmpty()) {
			temp[j] = sb.toString();
			j++;
		}
		arguments = new String[j];
	
		for(j = 0; j < arguments.length; j++) 
			arguments[j] = temp[j];
		
		return arguments;
	}

}
