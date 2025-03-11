package com.example.questify.controller;

import com.example.questify.models.BetweenModels.QuestionImages;
import com.example.questify.models.BetweenModels.QuestionTags;
import com.example.questify.models.BetweenModels.QuestionVotes;
import com.example.questify.models.Requests.*;
import com.example.questify.models.SimpleModels.*;
import com.example.questify.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/question")
public class QuestionController {
    private QuestionService questionService;
    private ImagesService imagesService;
    private QuestionImagesService questionImagesService;
    private TagsService tagsService;
    private QuestionVotesService questionVotesService;
    private QuestionTagsService questionTagsService;
    private UserService userService;

    @Autowired
    public QuestionController(QuestionService questionService,
                              ImagesService imagesService,
                              QuestionImagesService questionImagesService,
                              TagsService tagsService,
                              QuestionVotesService questionVotesService,
                              QuestionTagsService questionTagsService,
                              UserService userService) {
        this.questionService = questionService;
        this.imagesService = imagesService;
        this.questionImagesService = questionImagesService;
        this.tagsService = tagsService;
        this.questionVotesService = questionVotesService;
        this.questionTagsService = questionTagsService;
        this.userService = userService;
    }

    @GetMapping("/getRecent")
    public List<Question> getQuestions(@RequestBody QuestionGetRecentRequest questionGetRecentRequest) {
        int limit = questionGetRecentRequest.getLimit();
        return questionService.getRecentNQuestions(limit);
    }

    @GetMapping("/getFiltered")
    public List<Question> getFilteredQuestions(@RequestBody QuestionFilterRequest questionFilterRequest) {
        int option = questionFilterRequest.getOption();

        switch (option) {
            case 0 : {
                String tag = questionFilterRequest.getTag();

                Optional<Tags> findTag = tagsService.searchForTag(tag);

                if(findTag.isEmpty()){
                    return Collections.emptyList();
                }

                List<QuestionTags> questionTags = questionTagsService.findAllQuestionTagsByTagId(findTag.get().getId());

                List<Question> questionList = new ArrayList<Question>();

                for (QuestionTags questionTag : questionTags) {
                    Optional<Question> question = questionService.getQuestionById(questionTag.getQuestionId());
                    question.ifPresent(questionList::add);
                }

                return questionList;
            }

            case 1 : {
                String title = questionFilterRequest.getTitle();
                return questionService.filterBySubtitle(title);
            }

            case 2 : {
                String username = questionFilterRequest.getUsername();

                List<Users> findUsers = userService.findAllUsersByUsername(username);

                List<Question> questionsList = new ArrayList<Question>();

                for(int i = 0; i < findUsers.size(); i++) {
                    List<Question> tempListForUserId = questionService.filterByUserId(findUsers.get(i).getId());
                    questionsList.addAll(tempListForUserId);
                }

                return questionsList;
            }

            default : {
                Long userId = questionFilterRequest.getUserId();
                return questionService.filterByUserId(userId);
            }
        }
    }

    @PostMapping("/add")
    public ResponseEntity<String> addQuestion(@RequestBody QuestionAddRequest questionAddRequest) {
        Long userId = questionAddRequest.getUserId();
        String title = questionAddRequest.getTitle();
        String text = questionAddRequest.getText();
        List<byte[]> images = questionAddRequest.getImages();
        List<String> tags = questionAddRequest.getTags();

        Question question = new Question(
                userId,
                title,
                text,
                0,
                LocalDateTime.now(),
                0,
                0
        );

        boolean saveStatus = questionService.addQuestion(question);

        if (!saveStatus){
            return ResponseEntity.badRequest().body("Error storing question");
        }

        for (byte[] bytes : images) {
            Images image = new Images(bytes);

            saveStatus = imagesService.addImage(image);

            if(!saveStatus) {
                return ResponseEntity.badRequest().body("Error storing images");
            }

            QuestionImages questionImages = new QuestionImages(
                    question.getId(),
                    image.getId()
            );

            saveStatus = questionImagesService.addQuestionImage(questionImages);

            if(!saveStatus) {
                return ResponseEntity.badRequest().body("Error storing relation images question");
            }
        }

        for (String string : tags) {
            if(!string.isEmpty()) {
                Tags tag = new Tags(string);

                Optional<Tags> searchForTag = tagsService.searchForTag(string);

                if(searchForTag.isEmpty()){
                    saveStatus = tagsService.addTag(tag);

                    if(!saveStatus) {
                        return ResponseEntity.badRequest().body("Error storing tag");
                    }
                }
            }
        }

        return ResponseEntity.ok("Success");
    }

    @PutMapping("/edit")
    public ResponseEntity<String> editQuestion(@RequestBody QuestionEditRequest questionEditRequest) {
        Long questionId = questionEditRequest.getId();
        String title = questionEditRequest.getTitle();
        String text = questionEditRequest.getText();
        int status = questionEditRequest.getStatus();   // status 0 - received, 1 - progess, 2 - solved
        List<byte[]> images = questionEditRequest.getImages();
        List<Tags> tags = questionEditRequest.getTags();

        if(status == 2) {
            return ResponseEntity.badRequest().body("Question is solved");
        }

        Optional<Question> question = questionService.getQuestionById(questionId);

        if(question.isEmpty()) {
            return ResponseEntity.badRequest().body("Error finding question by id");
        }

        question.get().setTitle(title);
        question.get().setText(text);
        question.get().setStatus(status);

        List<QuestionImages> questionImages = questionImagesService.findAllQuestionImagesByQuestionId(questionId);

        questionImagesService.deleteAllQuestionImagesByQuestionId(questionId);

        for (QuestionImages questionImage : questionImages) {
            Long imageId = questionImage.getImageId();
            imagesService.deleteById(imageId);
        }

        for (byte[] bytes : images) {
            Images image = new Images(bytes);
            boolean saveStatus = imagesService.addImage(image);

            if(!saveStatus){
                return ResponseEntity.badRequest().body("Error storing the new images");
            }

            QuestionImages questionImagesToAdd = new QuestionImages(
                    questionId,
                    image.getId()
            );

            saveStatus = questionImagesService.addQuestionImage(questionImagesToAdd);

            if(!saveStatus) {
                return ResponseEntity.badRequest().body("Error storing the relation image question");
            }
        }

        return ResponseEntity.ok("Success");
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteQuestionById(@RequestBody QuestionDeleteRequest questionDeleteRequest) {
        Long questionId = questionDeleteRequest.getId();

        List<QuestionImages> questionImages = questionImagesService.findAllQuestionImagesByQuestionId(questionId);

        for (QuestionImages questionImage : questionImages) {
            imagesService.deleteById(questionImage.getImageId());
        }

        List<QuestionTags> questionTags = questionTagsService.findAllQuestionTagsByQuestionId(questionId);

        for (QuestionTags questionTag : questionTags) {
            Long tagId = questionTag.getTagId();
            List<QuestionTags> anyQuestionTagsLeft = questionTagsService.findAllQuestionTagsByTagId(tagId);

            if (anyQuestionTagsLeft.isEmpty()) {
                tagsService.deleteTagById(tagId);
            }
        }

        boolean deleteStatus = questionService.deleteQuestionById(questionId);

        if(!deleteStatus) {
            return ResponseEntity.badRequest().body("Error deleting question");
        }

        return ResponseEntity.ok("Success");
    }

    @PutMapping("/voteUp")
    public ResponseEntity<String> voteQuestionUp(@RequestBody QuestionVoteRequest questionVoteRequest) {
        Long questionId = questionVoteRequest.getQuestionId();
        Long userId = questionVoteRequest.getUserId();

        Optional<QuestionVotes> questionVotes = questionVotesService.findQuestionVote(questionId, userId);

        if(questionVotes.isEmpty()) {
            QuestionVotes questionVoteToAdd = new QuestionVotes(
                    userId,
                    questionId,
                    true
            );

            boolean saveStatus = questionVotesService.addQuestionVote(questionVoteToAdd);

            if(!saveStatus) {
                return ResponseEntity.badRequest().body("Error voting up");
            }

            return ResponseEntity.ok("Voted up");
        }
        else{
            boolean saveStatus = questionVotesService.deleteQuestionVote(questionVotes.get());

            if(!saveStatus) {
                return ResponseEntity.badRequest().body("Error removing vote up");
            }

            return ResponseEntity.ok("Vote up removed");
        }
    }

    @PutMapping("/voteDown")
    public ResponseEntity<String> voteQuestionDown(@RequestBody QuestionVoteRequest questionVoteRequest) {
        Long questionId = questionVoteRequest.getQuestionId();
        Long userId = questionVoteRequest.getUserId();

        Optional<QuestionVotes> questionVotes = questionVotesService.findQuestionVote(questionId, userId);

        if(questionVotes.isEmpty()) {
            QuestionVotes questionVoteToAdd = new QuestionVotes(
                    userId,
                    questionId,
                    false
            );

            boolean saveStatus = questionVotesService.addQuestionVote(questionVoteToAdd);

            if(!saveStatus) {
                return ResponseEntity.badRequest().body("Error voting down");
            }

            return ResponseEntity.ok("Voted down");
        }
        else{
            boolean saveStatus = questionVotesService.deleteQuestionVote(questionVotes.get());

            if(!saveStatus) {
                return ResponseEntity.badRequest().body("Error removing vote down");
            }

            return ResponseEntity.ok("Vote down removed");
        }
    }
}
