package com.javamentor.qa.platform.service.impl;

import com.javamentor.qa.platform.models.entity.Badge;
import com.javamentor.qa.platform.models.entity.Comment;
import com.javamentor.qa.platform.models.entity.CommentType;
import com.javamentor.qa.platform.models.entity.question.*;
import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import com.javamentor.qa.platform.models.entity.question.answer.AnswerVote;
import com.javamentor.qa.platform.models.entity.question.answer.CommentAnswer;
import com.javamentor.qa.platform.models.entity.user.*;
import com.javamentor.qa.platform.service.abstracts.model.*;
import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Service
public class  TestDataInitService {

    final UserService userService;
    final QuestionService questionService;
    final CommentService commentService;
    final ReputationService reputationService;
    final UserBadgesService userBadgesService;
    final TagService tagService;
    final UserFavoriteQuestionService userFavoriteQuestionService;
    final BadgeService badgeService;
    final RelatedTagService relatedTagService;
    final CommentQuestionService commentQuestionService;
    final CommentAnswerService commentAnswerService;
    final AnswerService answerService;
    final AnswerVoteService answerVoteService;
    final VoteQuestionService voteQuestionService;
    final RoleService roleService;
    final IgnoredTagService ignoredTagService;
    final TrackedTagService trackedTagService;

    int numberOfUsers = 50;
    List<Tag> tagList = new ArrayList<>();
    Role USER_ROLE = Role.builder().name("USER").build();
    Role ADMIN_ROLE = Role.builder().name("ADMIN").build();


    public TestDataInitService(UserService userService, BadgeService badgeService, QuestionService questionService,
                               CommentService commentService, ReputationService reputationService, UserBadgesService userBadgesService,
                               TagService tagService, UserFavoriteQuestionService userFavoriteQuestionService,
                               RelatedTagService relatedTagService, CommentQuestionService commentQuestionService,
                               CommentAnswerService commentAnswerService, AnswerService answerService,
                               AnswerVoteService answerVoteService, VoteQuestionService voteQuestionService, RoleService roleService,
                               IgnoredTagService ignoredTagService, TrackedTagService trackedTagService) {
        this.userService = userService;
        this.badgeService = badgeService;
        this.questionService = questionService;
        this.commentService = commentService;
        this.reputationService = reputationService;
        this.userBadgesService = userBadgesService;
        this.tagService = tagService;
        this.userFavoriteQuestionService = userFavoriteQuestionService;
        this.relatedTagService = relatedTagService;
        this.commentQuestionService = commentQuestionService;
        this.commentAnswerService = commentAnswerService;
        this.answerService = answerService;
        this.answerVoteService = answerVoteService;
        this.voteQuestionService = voteQuestionService;
        this.roleService = roleService;
        this.ignoredTagService = ignoredTagService;
        this.trackedTagService = trackedTagService;
    }


    public void createTagEntity() {
        for (int i = 0; i < numberOfUsers; i++) {
            Tag childTag = Tag.builder().name("Child" + i).description("DescriptionChildTag").build();
            Tag tag = new Tag();
            tag.setName("Tag Name" + i);
            tag.setDescription("Tag Description " + i);
            tagService.persist(tag);
            tagService.persist(childTag);

            RelatedTag relatedTag = new RelatedTag();
            relatedTag.setChildTag(childTag);
            relatedTag.setMainTag(tag);
            relatedTagService.persist(relatedTag);

            tagList.add(tag);
        }
    }

    @Transactional
    public void createEntity() {
        createTagEntity();
        roleService.persist(USER_ROLE);
        roleService.persist(ADMIN_ROLE);
        for (int i = 0; i < numberOfUsers; i++) {
            User user = new User();
            user.setEmail("ivanov@mail.com" + i);
            user.setPassword("password" + i);
            user.setFullName("Ivanov Ivan" + i);
            user.setIsEnabled(true);
            user.setReputationCount(0);
            user.setCity("Moscow" + i);
            user.setLinkSite("http://google.com" + i);
            user.setLinkGitHub("http://github.com");
            user.setLinkVk("http://vk.com");
            user.setAbout("very good man");
            user.setImageLink("https://pbs.twimg.com/profile_images/1182694005408186375/i5xT6juJ_400x400.jpg");
            user.setReputationCount(1);
            if (i == 0) user.setRole(ADMIN_ROLE);
            else user.setRole(USER_ROLE);
            userService.persist(user);

            Reputation reputation = new Reputation();
            reputation.setUser(user);
            reputation.setCount(1);
            reputationService.persist(reputation);

            Question question = new Question();
            question.setTitle("Question Title" + i);
            question.setViewCount(i*5);
            question.setDescription("Question Description" + i);
            question.setUser(user);
            question.setTags(tagList.stream().limit(5).collect(Collectors.toList()));
            question.setIsDeleted(false);
            questionService.persist(question);

            Question questionNoAnswer = new Question();
            questionNoAnswer.setTitle("Question NoAnswer " + i);
            questionNoAnswer.setViewCount(i*2);
            questionNoAnswer.setDescription("Question NoAnswer Description" + i);
            questionNoAnswer.setUser(user);
            questionNoAnswer.setTags(tagList.stream().limit(5).collect(Collectors.toList()));
            questionNoAnswer.setIsDeleted(false);
            questionService.persist(questionNoAnswer);

            UserFavoriteQuestion userFavoriteQuestion = new UserFavoriteQuestion();
            userFavoriteQuestion.setUser(user);
            userFavoriteQuestion.setQuestion(question);
            userFavoriteQuestionService.persist(userFavoriteQuestion);

            VoteQuestion voteQuestion = new VoteQuestion();
            voteQuestion.setUser(user);
            voteQuestion.setQuestion(question);
            voteQuestion.setVote(1);
            voteQuestionService.persist(voteQuestion);

            Answer answer = new Answer();
            answer.setUser(user);
            answer.setQuestion(question);
            answer.setHtmlBody("<HtmlBody>" + i);
            answer.setIsHelpful(true);
            answer.setIsDeleted(false);
            answerService.persist(answer);

            CommentQuestion commentQuestion = new CommentQuestion();
            commentQuestion.setQuestion(question);
            commentQuestion.setComment(Comment.builder().text("Comment Text" + i)
                    .user(user).commentType(CommentType.QUESTION).build());
            commentQuestionService.persist(commentQuestion);

            CommentAnswer commentAnswer = new CommentAnswer();
            commentAnswer.setAnswer(answer);
            commentAnswer.setComment(Comment.builder().text("Comment Text" + i)
                    .user(user).commentType(CommentType.ANSWER).build());
            commentAnswerService.persist(commentAnswer);

            Badge badge = new Badge();
            badge.setBadgeName("Super Badge" + i);
            badge.setReputationForMerit(1);
            badge.setDescription("Badge Description" + i);
            badgeService.persist(badge);

            UserBadges userBadges = new UserBadges();
            userBadges.setReady(true);
            userBadges.setUser(user);
            userBadges.setBadge(badge);
            userBadgesService.persist(userBadges);

            AnswerVote answerVote = new AnswerVote();
            answerVote.setUser(user);
            answerVote.setAnswer(answer);
            answerVote.setVote(1);
            answerVoteService.persist(answerVote);

            IgnoredTag ignoredTag = new IgnoredTag();
            ignoredTag.setUser(user);
            ignoredTag.setIgnoredTag(tagList.get(1));
            ignoredTagService.persist(ignoredTag);

            TrackedTag trackedTag = new TrackedTag();
            trackedTag.setUser(user);
            trackedTag.setTrackedTag(tagList.get(2));
            trackedTagService.persist(trackedTag);
        }
    }
}
