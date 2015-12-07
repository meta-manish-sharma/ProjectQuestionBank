package com.metacube.QuestionBank.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.metacube.QuestionBank.dao.AnswerDaoImplTest;
import com.metacube.QuestionBank.service.AnswerService;
import com.metacube.QuestionBank.service.UserService;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	com.metacube.QuestionBank.dao.UserDaoImplTest.class,
	com.metacube.QuestionBank.dao.TagDaoImplTest.class,
	com.metacube.QuestionBank.dao.QuestionVotesDaoImplTest.class,
	com.metacube.QuestionBank.dao.QuestionDetailDoaImpTest.class,
	com.metacube.QuestionBank.dao.QuestionDaoImplTest.class,
	com.metacube.QuestionBank.dao.PopularityDaoImplTest.class,
	com.metacube.QuestionBank.dao.AnswerVotesDaoImplTest.class,
	com.metacube.QuestionBank.dao.AnswerDaoImplTest.class,
	com.metacube.QuestionBank.dao.AnswerDaoImplTest.class,
	com.metacube.QuestionBank.service.UserService.class,
	com.metacube.QuestionBank.service.TagServiceImplTest.class,
	com.metacube.QuestionBank.service.QuestionServiceTest.class,
	com.metacube.QuestionBank.service.AnswerService.class

})
public class JUnitTestSuite {
}
