package au.com.digitalspider.biblegame.service;

import java.util.List;

import org.springframework.stereotype.Service;

import au.com.digitalspider.biblegame.model.Question;
import au.com.digitalspider.biblegame.model.User;
import au.com.digitalspider.biblegame.repo.QuestionRepository;
import au.com.digitalspider.biblegame.service.base.BaseLongNamedService;

@Service
public class QuestionService extends BaseLongNamedService<Question> {

	public QuestionService() {
		super(Question.class);
	}

	@Override
	public QuestionRepository getRepository() {
		return getRepository();
	}

	public List<Question> findByName(String name) {
		return findByName(name);
	}

	public List<Question> findByCategory(String category) {
		return getRepository().findByCategoryOrderBySort(category);
	}

	public List<Question> findByReference(String reference) {
		return getRepository().findByReferenceOrderBySort(reference);
	}

	public List<Question> findByLevel(int level) {
		return getRepository().findByLevelOrderBySort(level);
	}

	public List<Question> findTop5ByLevelLessThan(int level) {
		return getRepository().findTop5ByLevelLessThanOrEqualToOrderBySort(level);
	}

	public List<Question> findRandomForUser(User user) {
		int level = user.getLevel();
		// TODO: Remove questions the user has already answered!
		return findTop5ByLevelLessThan(level);
	}

}
