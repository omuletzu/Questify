package com.example.questify.controller;

import com.example.questify.models.BetweenModels.AnswerImages;
import com.example.questify.models.BetweenModels.AnswerVotes;
import com.example.questify.models.BetweenModels.QuestionVotes;
import com.example.questify.models.SimpleModels.*;
import com.example.questify.services.AnswerImagesService;
import com.example.questify.services.AnswerService;
import com.example.questify.services.AnswerVotesService;
import com.example.questify.services.ImagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/answers")
public class AnswerController {
    private AnswerService answerService;
    private ImagesService imagesService;
    private AnswerImagesService answerImagesService;
    private AnswerVotesService answerVotesService;

    @Autowired
    public AnswerController(AnswerService answerService,
                            ImagesService imagesService,
                            AnswerImagesService answerImagesService,
                            AnswerVotesService answerVotesService){
        this.answerService = answerService;
        this.imagesService = imagesService;
        this.answerImagesService = answerImagesService;
        this.answerImagesService = answerImagesService;
    }

    @GetMapping("/getForQuestion")
    public List<Answer> getAnswersForQuestionId(@RequestParam(name = "id") Long id) {
        List<Answer> answers = answerService.getAnswersByQuestionId(id);
        answers.sort(Comparator.comparing(Answer::getTimestamp).reversed());
        return answers;
    }

    @PostMapping("/addForQuestion")
    public ResponseEntity<String> addAnswerForQuestionId(@RequestBody AnswerRequest answerRequest) {

        Long userId = answerRequest.getUserId();
        Long questionId = answerRequest.getQuestionId();
        String text = answerRequest.getText();
        List<byte[]> images = answerRequest.getImages();

        Answer answer = new Answer(
                userId,
                questionId,
                text,
                LocalDateTime.now(),
                0,
                0
        );

        boolean status = answerService.addAnswerForQuestionId(answer);

        if(!status){
            return ResponseEntity.badRequest().body("Error storing response text");
        }

        if(images != null) {
            for (byte[] bytes : images) {
                Images image = new Images(bytes);

                status = imagesService.addImage(image);

                if (!status) {
                    return ResponseEntity.badRequest().body("Error storing image");
                }

                AnswerImages answerImages = new AnswerImages(answer.getId(), image.getId());

                status = answerImagesService.addAnswerImages(answerImages);

                if (!status) {
                    return ResponseEntity.badRequest().body("Error storing relation image answer");
                }
            }
        }

        return ResponseEntity.ok("Success");
    }

    @PutMapping("/editById")
    public ResponseEntity<String> editAnswerById(@RequestBody AnswerEditRequest answerEditRequest) {

        Long id = answerEditRequest.getId();
        String text = answerEditRequest.getText();
        List<byte[]> images = answerEditRequest.getImages();

        Optional<Answer> answer = answerService.getAnswerById(id);

        if(answer.isEmpty()){
            return ResponseEntity.badRequest().body("Error finding answer");
        }

        if(text.isEmpty()){
            return ResponseEntity.badRequest().body("Error empty text");
        }

        answer.get().setText(text);
        answerService.addAnswerForQuestionId(answer.get());

        List<AnswerImages> imagesList = answerImagesService.getAllImagesId(answer.get().getId());
        answerImagesService.deleteAllById(answer.get().getId());

        for (AnswerImages answerImages : imagesList) {
            imagesService.deleteById(answerImages.getImageId());
        }

        for(int i = 0; i < images.size(); i++) {
            Images image = new Images(images.get(i));

            boolean status = imagesService.addImage(image);

            if(!status){
                return ResponseEntity.badRequest().body("Error storing image");
            }

            AnswerImages answerImages = new AnswerImages(answer.get().getId(), image.getId());

            status = answerImagesService.addAnswerImages(answerImages);

            if(!status) {
                return ResponseEntity.badRequest().body("Error storing relation image answer");
            }
        }

        return ResponseEntity.ok("Success");
    }

    @PutMapping("/voteUp")
    public ResponseEntity<String> voteQuestionUp(@RequestBody AnswerVoteRequest answerVoteRequest) {
        Long answerId = answerVoteRequest.getAnswerId();
        Long userId = answerVoteRequest.getUserId();

        Optional<AnswerVotes> answerVotes = answerVotesService.findAnswerVote(answerId, userId);

        if(answerVotes.isEmpty()) {
            AnswerVotes answerVoteToAdd = new AnswerVotes(
                    userId,
                    answerId,
                    true
            );

            boolean saveStatus = answerVotesService.addAnswerVote(answerVoteToAdd);

            if(!saveStatus) {
                return ResponseEntity.badRequest().body("Error voting up");
            }

            return ResponseEntity.ok("Voted up");
        }
        else{
            boolean saveStatus = answerVotesService.deleteAnswerVote(answerVotes.get());

            if(!saveStatus) {
                return ResponseEntity.badRequest().body("Error removing vote up");
            }

            return ResponseEntity.ok("Vote up removed");
        }
    }

    @PutMapping("/voteDown")
    public ResponseEntity<String> voteQuestionDown(@RequestBody AnswerVoteRequest answerVoteRequest) {
        Long answerId = answerVoteRequest.getAnswerId();
        Long userId = answerVoteRequest.getUserId();

        Optional<AnswerVotes> answerVotes = answerVotesService.findAnswerVote(answerId, userId);

        if(answerVotes.isEmpty()) {
            AnswerVotes answerVoteToAdd = new AnswerVotes(
                    userId,
                    answerId,
                    false
            );

            boolean saveStatus = answerVotesService.addAnswerVote(answerVoteToAdd);

            if(!saveStatus) {
                return ResponseEntity.badRequest().body("Error voting down");
            }

            return ResponseEntity.ok("Voted down");
        }
        else{
            boolean saveStatus = answerVotesService.deleteAnswerVote(answerVotes.get());

            if(!saveStatus) {
                return ResponseEntity.badRequest().body("Error removing vote down");
            }

            return ResponseEntity.ok("Vote down removed");
        }
    }
}
