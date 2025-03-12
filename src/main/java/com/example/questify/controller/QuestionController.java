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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    public List<Question> getQuestions(@RequestParam(name = "limit") int limit, @RequestParam(name = "pageNumber") int pageNumber) {
        return questionService.getRecentNQuestions(pageNumber, limit);
    }

    @GetMapping("/getFiltered")
    public List<Question> getFilteredQuestions(
            @RequestParam(name = "option") int option,
            @RequestParam(name = "title", required = false) String title,
            @RequestParam(name = "tag", required = false) String tag,
            @RequestParam(name = "username", required = false) String username,
            @RequestParam(name = "userId", required = false) Long userId) {

        switch (option) {
            case 0 : {
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
                return questionService.filterBySubtitle(title);
            }

            case 2 : {
                List<Users> findUsers = userService.findAllUsersByUsername(username);

                List<Question> questionsList = new ArrayList<Question>();

                for(int i = 0; i < findUsers.size(); i++) {
                    List<Question> tempListForUserId = questionService.filterByUserId(findUsers.get(i).getId());
                    questionsList.addAll(tempListForUserId);
                }

                return questionsList;
            }

            default : {
                return questionService.filterByUserId(userId);
            }
        }
    }

    @PostMapping("/add")
    public ResponseEntity<String> addQuestion(
            @RequestParam("userId") Long userId,
            @RequestParam("title") String title,
            @RequestParam("text") String text,
            @RequestParam(value = "images", required = false) List<MultipartFile> images,
            @RequestParam(value = "tags", required = false) List<String> tags) throws IOException {

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

        if(images != null) {
            for (MultipartFile image64base : images) {
                byte[] imageConvertedToByte = image64base.getBytes();

                Images image = new Images(imageConvertedToByte);

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
        }

        if(tags != null) {
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
        }

        return ResponseEntity.ok("Success");
    }

    @PutMapping("/editById")
    public ResponseEntity<String> editQuestion(
            @RequestParam("id") Long questionId,
            @RequestParam("title") String title,
            @RequestParam("text") String text,
            @RequestParam("status") int status,
            @RequestParam(value = "images", required = false) List<MultipartFile> images,
            @RequestParam(value = "tags", required = false) List<String> tags) throws IOException {

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

        if(images != null) {
            for (MultipartFile image64base : images) {
                byte[] imageConvertedToByte = image64base.getBytes();

                Images image = new Images(imageConvertedToByte);
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
        }

        List<QuestionTags> questionTags = questionTagsService.findAllQuestionTagsByQuestionId(questionId);
        questionTagsService.deleteAllByQuestionId(questionId);

        if(tags != null) {
            for(String tagName : tags) {
                Tags tag = new Tags(tagName);

                Optional<Tags> searchForTag = tagsService.searchForTag(tagName);

                if(searchForTag.isEmpty()) {
                    tagsService.addTag(tag);
                }

                QuestionTags questionTagsToAdd = new QuestionTags(tag.getId(), questionId);
                boolean saveStatus = questionTagsService.addQuestionTags(questionTagsToAdd);

                if(!saveStatus) {
                    return ResponseEntity.badRequest().body("Error editing tags");
                }
            }
        }

        return ResponseEntity.ok("Success");
    }

    @DeleteMapping("/deleteById")
    public ResponseEntity<String> deleteQuestionById(@RequestParam(name = "questionId") Long questionId) {
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

    @PutMapping("/voteUpById")
    public ResponseEntity<String> voteQuestionUp(@RequestParam(name = "questionId") Long questionId, @RequestParam(name = "userId") Long userId) {
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

    @PutMapping("/voteDownById")
    public ResponseEntity<String> voteQuestionDown(@RequestParam(name = "questionId") Long questionId, @RequestParam(name = "userId") Long userId) {
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
