package au.com.digitalspider.biblegame.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import au.com.digitalspider.biblegame.model.Question;
import au.com.digitalspider.biblegame.model.User;
import au.com.digitalspider.biblegame.repo.QuestionRepository;
import au.com.digitalspider.biblegame.service.base.BaseLongNamedService;

@Service
public class QuestionService extends BaseLongNamedService<Question> {

	@Autowired
	private QuestionRepository questionRepository;

	@Autowired
	private HelperService helperService;

	public QuestionService() {
		super(Question.class);
	}

	@Override
	public QuestionRepository getRepository() {
		return questionRepository;
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

	public List<Question> findTopByLevelLessThan(int level, int limit) {
		return getRepository().findTopByLevelLessThanEqualOrderByRandom(level, limit);
	}

	public Iterable<Question> findRandomForUser(User user) {
		int level = user.getLevel();
		int limit = 3;
		List<String> chapters = getRepository().findChapters();
		String chapter = getRandom(chapters);
		List<Question> questions = getRepository().findByChapter(chapter);
		List<Question> resultList = new ArrayList<>();
		if (questions.size() <= 3) {
			resultList.addAll(questions);
		} else {
			Random random = new Random();
			int index = random.nextInt(questions.size() - 3);
			resultList.add(questions.get(index));
			resultList.add(questions.get(index + 1));
			resultList.add(questions.get(index + 2));
		}
		// TODO: Remove questions the user has already answered!
		return resultList;
	}

	private String getRandom(List<String> list) {
		Random random = new Random();
		return list.get(random.nextInt(list.size()));
	}

}
