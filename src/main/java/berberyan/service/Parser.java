package berberyan.service;

import java.util.List;

@FunctionalInterface
public interface Parser {
	List<String> extractData(String entry);
}
