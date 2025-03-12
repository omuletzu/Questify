package com.example.questify.controller;

import com.example.questify.models.BetweenModels.AnswerImages;
import com.example.questify.models.BetweenModels.AnswerVotes;
import com.example.questify.models.Requests.AnswerDeleteRequest;
import com.example.questify.models.Requests.AnswerEditRequest;
import com.example.questify.models.Requests.AnswerVoteRequest;
import com.example.questify.models.SimpleModels.*;
import com.example.questify.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
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
    private QuestionService questionService;

    @Autowired
    public AnswerController(AnswerService answerService,
                            ImagesService imagesService,
                            AnswerImagesService answerImagesService,
                            AnswerVotesService answerVotesService,
                            QuestionService questionService){
        this.answerService = answerService;
        this.imagesService = imagesService;
        this.answerImagesService = answerImagesService;
        this.answerVotesService = answerVotesService;
        this.questionService = questionService;
    }

    @GetMapping("/getByQuestionId")
    public List<Answer> getAnswersForQuestionId(@RequestParam(name = "questionId") Long questionId) {
        List<Answer> answers = answerService.getAnswersByQuestionId(questionId);
        answers.sort(Comparator.comparing(Answer::getScore).reversed());
        return answers;
    }

    @PostMapping("/addForQuestionId")
    public ResponseEntity<String> addAnswerForQuestionId(@RequestParam(name = "userId") Long userId,
                                                         @RequestParam(name = "questionId") Long questionId,
                                                         @RequestParam(name = "text") String text,
                                                         @RequestParam(name = "images", required = false) List<MultipartFile> images) throws IOException {
        Optional<Question> question = questionService.getQuestionById(questionId);

        if(question.isEmpty()){
            return ResponseEntity.badRequest().body("Error getting question by id");
        }

        int questionStatus = question.get().getStatus();

        if(questionStatus == 2) {
            return ResponseEntity.badRequest().body("Error caused by question solved");
        }

        if(questionStatus == 0) {
            question.get().setStatus(1);
            boolean saveStatus = questionService.editQuestion(question.get());

            if(!saveStatus) {
                return ResponseEntity.badRequest().body("Error modifying question status");
            }
        }

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
            for (MultipartFile image64base : images) {
                byte[] imageConvertedToByte = image64base.getBytes();

                Images image = new Images(imageConvertedToByte);

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
    public ResponseEntity<String> editAnswerById(@RequestParam(name = "id") Long id,
                                                 @RequestParam(name = "text") String text,
                                                 @RequestParam(name = "images", required = false) List<MultipartFile> images) throws IOException {
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

        if(images != null) {
            for (MultipartFile multipartFile : images) {
                byte[] imageConvertedToByte = multipartFile.getBytes();

                Images image = new Images(imageConvertedToByte);

                boolean status = imagesService.addImage(image);

                if (!status) {
                    return ResponseEntity.badRequest().body("Error storing image");
                }

                AnswerImages answerImages = new AnswerImages(answer.get().getId(), image.getId());

                status = answerImagesService.addAnswerImages(answerImages);

                if (!status) {
                    return ResponseEntity.badRequest().body("Error storing relation image answer");
                }
            }
        }

        return ResponseEntity.ok("Success");
    }

    @DeleteMapping("/deleteById")
    public ResponseEntity<String> deleteAnswerById(@RequestParam(name = "answerId") Long answerId) {
        List<AnswerImages> answerImages = answerImagesService.getAllImagesId(answerId);

        for (AnswerImages answerImage : answerImages) {
            imagesService.deleteById(answerImage.getImageId());
        }

        boolean deleteStatus = answerService.deleteAnswerById(answerId);

        if(!deleteStatus) {
            return ResponseEntity.badRequest().body("Error deleting answer");
        }

        return ResponseEntity.ok("Success");
    }

    @PutMapping("/voteUpById")
    public ResponseEntity<String> voteQuestionUp(@RequestParam(name = "answerId") Long answerId,
                                                 @RequestParam(name = "userId") Long userId) {
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

    @PutMapping("/voteDownById")
    public ResponseEntity<String> voteQuestionDown(@RequestParam(name = "answerId") Long answerId,
                                                   @RequestParam(name = "userId") Long userId) {
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
