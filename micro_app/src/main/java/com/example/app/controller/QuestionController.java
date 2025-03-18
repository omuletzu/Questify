package com.example.app.controller;

import com.example.app.models.BetweenModels.QuestionImages;
import com.example.app.models.BetweenModels.QuestionTags;
import com.example.app.models.BetweenModels.QuestionVotes;
import com.example.app.models.Requests.*;
import com.example.app.models.SimpleModels.Images;
import com.example.app.models.SimpleModels.Question;
import com.example.app.models.SimpleModels.Tags;
import com.example.app.services.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/question")
public class QuestionController {
    private QuestionService questionService;
    private ImagesService imagesService;
    private QuestionImagesService questionImagesService;
    private TagsService tagsService;
    private QuestionVotesService questionVotesService;
    private QuestionTagsService questionTagsService;
    private RestTemplate restTemplate;

    @Autowired
    public QuestionController(QuestionService questionService,
                              ImagesService imagesService,
                              QuestionImagesService questionImagesService,
                              TagsService tagsService,
                              QuestionVotesService questionVotesService,
                              QuestionTagsService questionTagsService,
                              RestTemplate restTemplate) {
        this.questionService = questionService;
        this.imagesService = imagesService;
        this.questionImagesService = questionImagesService;
        this.tagsService = tagsService;
        this.questionVotesService = questionVotesService;
        this.questionTagsService = questionTagsService;
        this.restTemplate = restTemplate;
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Question>> getQuestions() {
        return ResponseEntity.ok(questionService.getAllQuestions());
    }

    @GetMapping("/getRecent")
    public ResponseEntity<List<Long>> getQuestions(@RequestParam(name = "limit") int limit, @RequestParam(name = "pageNumber") int pageNumber) {
        List<Long> questionIdList = new ArrayList<Long>();
        List<Question> questionList = questionService.getRecentNQuestions(pageNumber, limit);

        for(Question question : questionList) {
            questionIdList.add(question.getId());
        }

        return ResponseEntity.ok(questionIdList);
    }

    @GetMapping("/getFiltered")
    public ResponseEntity<List<Long>> getFilteredQuestions(@RequestParam(name = "option") int option,
                                                               @RequestParam(name = "tag", required = false) String tag,
                                                               @RequestParam(name = "title", required = false) String title,
                                                               @RequestParam(name = "username", required = false) String username,
                                                               @RequestParam(name = "userId", required = false) Long userId) {

        List<Long> questionIdList = new ArrayList<Long>();

        switch (option) {
            case 0 : {
                Optional<Tags> findTag = tagsService.searchForTag(tag);

                if(findTag.isEmpty()) {
                    return ResponseEntity.ok(Collections.emptyList());
                }

                List<QuestionTags> questionTags = questionTagsService.findAllQuestionTagsByTagId(findTag.get().getId());

                for (QuestionTags questionTag : questionTags) {
                    Optional<Question> question = questionService.getQuestionById(questionTag.getQuestionId());
                    question.ifPresent(value -> questionIdList.add(value.getId()));
                }

                return ResponseEntity.ok(questionIdList);
            }

            case 1 : {
                List<Question> questionList = questionService.filterBySubtitle(title);

                for(Question question : questionList) {
                    questionIdList.add(question.getId());
                }

                return ResponseEntity.ok(questionIdList);
            }

            case 2 : {
                String url = "http://localhost:8079/users/getUserIdByUsername?username=" + username;
                ResponseEntity<List<Long>> response = restTemplate.exchange(
                        url,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<Long>>() {}
                );

                List<Long> userIds = response.getBody();

                List<Question> questionsList = new ArrayList<Question>();

                for (Long id : userIds) {
                    List<Question> tempListForUserId = questionService.filterByUserId(id);
                    questionsList.addAll(tempListForUserId);
                }

                for(Question question : questionsList) {
                    questionIdList.add(question.getId());
                }

                return ResponseEntity.ok(questionIdList);
            }

            case 3 : {
                List<Question> questionList = questionService.filterByUserId(userId);

                for(Question question : questionList) {
                    questionIdList.add(question.getId());
                }

                return ResponseEntity.ok(questionIdList);
            }

            default : {
                return ResponseEntity.badRequest().body(Collections.emptyList());
            }
        }
    }

    @GetMapping("getImagesByQuestionId")
    public ResponseEntity<List<String>> getImagesByQuestionId(@RequestParam(name = "questionId") Long questionId) {
        List<QuestionImages> questionImagesList = questionImagesService.findAllQuestionImagesByQuestionId(questionId);

        List<String> imagesContent = new ArrayList<String>();

        for(QuestionImages questionImages : questionImagesList) {
            Optional<Images> image = imagesService.findByImageId(questionImages.getImageId());

            image.ifPresent(images -> imagesContent.add(images.getImageData()));
        }

        return ResponseEntity.ok(imagesContent);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addQuestion(
            @RequestBody QuestionAddRequest questionAddRequest) throws IOException {

        Long userId = questionAddRequest.getUserId();
        String title = questionAddRequest.getTitle();
        String text = questionAddRequest.getText();
        List<String> images = questionAddRequest.getImages();
        List<String> tags = questionAddRequest.getTags();

        Question question = new Question(
                userId,
                title,
                text,
                0,
                LocalDateTime.now()
        );

        boolean saveStatus = questionService.addQuestion(question);

        if (!saveStatus){
            return ResponseEntity.badRequest().body("Error storing question");
        }

        if(images != null) {
            for (String imageUrl : images) {
                Images image = new Images(imageUrl);

                saveStatus = imagesService.addImage(image);

                if (!saveStatus) {
                    return ResponseEntity.badRequest().body("Error storing images");
                }

                QuestionImages questionImages = new QuestionImages(
                        question.getId(),
                        image.getId()
                );

                saveStatus = questionImagesService.addQuestionImage(questionImages);

                if (!saveStatus) {
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
                        Tags newTag = tagsService.addTag(tag);

                        QuestionTags questionTags = new QuestionTags(newTag.getId(), question.getId());
                        questionTagsService.addQuestionTags(questionTags);
                    }
                    else{
                        QuestionTags questionTags = new QuestionTags(searchForTag.get().getId(), question.getId());
                        questionTagsService.addQuestionTags(questionTags);
                    }
                }
            }
        }

        return ResponseEntity.ok("Success");
    }

    @PutMapping("/editById")
    public ResponseEntity<String> editQuestion(
            @RequestBody QuestionEditRequest questionEditRequest) throws IOException {

        Long questionId = questionEditRequest.getId();
        String title = questionEditRequest.getTitle();
        String text = questionEditRequest.getText();
        List<String> images = questionEditRequest.getImages();
        List<String> tags = questionEditRequest.getTags();

        Logger logger = LoggerFactory.getLogger(QuestionController.class);

        Optional<Question> question = questionService.getQuestionById(questionId);

        if(question.isPresent()) {
            if(question.get().getStatus() == 2) {
                return ResponseEntity.badRequest().body("Question is solved");
            }
        }
        else{
            return ResponseEntity.badRequest().body("Error finding question by id");
        }

        question.get().setTitle(title);
        question.get().setText(text);

        List<QuestionImages> questionImages = questionImagesService.findAllQuestionImagesByQuestionId(questionId);
        questionImagesService.deleteAllQuestionImagesByQuestionId(questionId);

        for (QuestionImages questionImage : questionImages) {
            Long imageId = questionImage.getImageId();
            imagesService.deleteById(imageId);
        }

        if(images != null) {
            for (String imageUrl : images) {
                Images image = new Images(imageUrl);
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
                Optional<Tags> searchForTag = tagsService.searchForTag(tagName);

                Tags tag = searchForTag.orElseGet(() -> tagsService.addTag(new Tags(tagName)));

                QuestionTags questionTagsToAdd = new QuestionTags(tag.getId(), questionId);
                boolean saveStatus = questionTagsService.addQuestionTags(questionTagsToAdd);

                if(!saveStatus) {
                    return ResponseEntity.badRequest().body("Error editing tags");
                }
            }
        }

        for(QuestionTags qt : questionTags) {
            List<QuestionTags> searchForEmptyTags = questionTagsService.findAllQuestionTagsByTagId(qt.getTagId());

            if(searchForEmptyTags.isEmpty()) {
                tagsService.deleteTagById(qt.getTagId());
            }
        }

        return ResponseEntity.ok("Success");
    }

    @DeleteMapping("/deleteById")
    public ResponseEntity<String> deleteQuestionById(@RequestParam(name = "questionId") Long questionId) {
        Logger logger = LoggerFactory.getLogger(QuestionController.class);
        logger.info(String.valueOf(questionId));

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
    public ResponseEntity<String> voteQuestionUp(@RequestBody QuestionVoteRequest questionVoteRequest) {
        Long questionId = questionVoteRequest.getQuestionId();
        Long userId = questionVoteRequest.getUserId();

        Optional<QuestionVotes> questionVotesOpt = questionVotesService.findQuestionVote(questionId, userId);

        if (questionVotesOpt.isEmpty()) {
            QuestionVotes newQuestionVote = new QuestionVotes(userId, questionId, true);
            boolean saveStatus = questionVotesService.addQuestionVote(newQuestionVote);

            if (!saveStatus) {
                return ResponseEntity.badRequest().body("Error voting up");
            }
            return ResponseEntity.ok("Voted up");
        }

        QuestionVotes existingVote = questionVotesOpt.get();

        if (existingVote.getUpDown()) {
            boolean saveStatus = questionVotesService.deleteQuestionVote(existingVote);

            if (!saveStatus) {
                return ResponseEntity.badRequest().body("Error removing vote up");
            }
            return ResponseEntity.ok("Vote up removed");
        } else {
            return ResponseEntity.ok("Already voted");
        }
    }


    @PutMapping("/voteDownById")
    public ResponseEntity<String> voteQuestionDown(@RequestBody QuestionVoteRequest questionVoteRequest) {
        Long questionId = questionVoteRequest.getQuestionId();
        Long userId = questionVoteRequest.getUserId();

        Optional<QuestionVotes> questionVotesOpt = questionVotesService.findQuestionVote(questionId, userId);

        if (questionVotesOpt.isEmpty()) {
            QuestionVotes newQuestionVote = new QuestionVotes(userId, questionId, false);
            boolean saveStatus = questionVotesService.addQuestionVote(newQuestionVote);

            if (!saveStatus) {
                return ResponseEntity.badRequest().body("Error voting down");
            }
            return ResponseEntity.ok("Voted down");
        }

        QuestionVotes existingVote = questionVotesOpt.get();

        if (!existingVote.getUpDown()) {
            boolean saveStatus = questionVotesService.deleteQuestionVote(existingVote);

            if (!saveStatus) {
                return ResponseEntity.badRequest().body("Error removing vote down");
            }
            return ResponseEntity.ok("Vote down removed");
        } else {
            return ResponseEntity.ok("Already voted");
        }
    }


    @GetMapping("/getQuestionScoreById")
    public ResponseEntity<Double> getQuestionScore(@RequestParam(name = "userId") Long userId) {
        List<Question> questionListByUser = questionService.getQuestionByUserId(userId);

        double finalScore = 0.0;

        for (Question question : questionListByUser) {
            finalScore += 2.5 * questionVotesService.countByUpDownTrueByQuestionId(question.getId());
            finalScore -= 1.5 * questionVotesService.countByUpDownFalseByQuestionId(question.getId());
        }

        return ResponseEntity.ok(finalScore);
    }

    @GetMapping("/getQuestionScoreByQuestionId")
    public ResponseEntity<Double> getQuestionScoreByQuestionId(@RequestParam(name = "questionId") Long questionId) {
        double finalScore = questionVotesService.countByUpDownTrueByQuestionId(questionId) -
                questionVotesService.countByUpDownFalseByQuestionId(questionId);

        return ResponseEntity.ok(finalScore);
    }

    @GetMapping("/getTagsByQuestionId")
    public ResponseEntity<List<String>> getTagsByQuestionId(@RequestParam(name = "questionId") Long questionId) {
        List<String> tagList = new ArrayList<String>();

        List<QuestionTags> questionTags = questionTagsService.findAllQuestionTagsByQuestionId(questionId);

        for(QuestionTags qt : questionTags) {
            Long tagId = qt.getTagId();
            Optional<Tags> searchForTag = tagsService.findTagNameByTagId(tagId);

            searchForTag.ifPresent(tags -> tagList.add(tags.getTagName()));
        }

        return ResponseEntity.ok(tagList);
    }

    @GetMapping("/getQuestionById")
    public ResponseEntity<Object> getQuestionById(@RequestParam(name = "questionId") Long questionId) {
        Optional<Question> question = questionService.getQuestionById(questionId);

        return question.<ResponseEntity<Object>>map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest().body("Question not found"));
    }

    @PutMapping("/closeQuestionById")
    public ResponseEntity<String> closeQuestionById(@RequestBody QuestionGetDeleteRequest questionGetDeleteRequest) {
        Long questionId = questionGetDeleteRequest.getId();

        Optional<Question> question = questionService.getQuestionById(questionId);

        if(question.isEmpty()) {
            return ResponseEntity.badRequest().body("Question not found");
        }

        if(question.get().getStatus() == 0) {
            return ResponseEntity.badRequest().body("Question has no answers");
        }

        questionService.setQuestionStatusById(questionId, 2);
        return ResponseEntity.ok("Success");
    }
}
