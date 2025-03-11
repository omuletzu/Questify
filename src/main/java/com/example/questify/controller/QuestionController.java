package com.example.questify.controller;

import com.example.questify.models.BetweenModels.QuestionImages;
import com.example.questify.models.BetweenModels.QuestionVotes;
import com.example.questify.models.SimpleModels.*;
import com.example.questify.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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

    @Autowired
    public QuestionController(QuestionService questionService,
                              ImagesService imagesService,
                              QuestionImagesService questionImagesService,
                              TagsService tagsService,
                              QuestionVotesService questionVotesService) {
        this.questionService = questionService;
        this.imagesService = imagesService;
        this.questionImagesService = questionImagesService;
        this.tagsService = tagsService;
    }

    @GetMapping("/getRecent")
    public List<Question> getQuestions(@RequestParam(name = "limit") int limit) {
        return questionService.getRecentNQuestions(limit);
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

                List<Tags> searchForTag = tagsService.searchForTag(string);

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
