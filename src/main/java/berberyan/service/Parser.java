package berberyan.service;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
@FunctionalInterface
public interface Parser {
	List<String> extractData(String entry);
}
