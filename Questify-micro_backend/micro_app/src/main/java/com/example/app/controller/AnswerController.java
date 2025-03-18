package com.example.app.controller;

import com.example.app.models.BetweenModels.AnswerImages;
import com.example.app.models.BetweenModels.AnswerVotes;
import com.example.app.models.Requests.*;
import com.example.app.models.SimpleModels.Answer;
import com.example.app.models.SimpleModels.Images;
import com.example.app.models.SimpleModels.Question;
import com.example.app.services.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.text.html.Option;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    public ResponseEntity<List<Long>> getAnswersForQuestionId(@RequestParam(name = "questionId") Long questionId) {
        List<Answer> answers = answerService.getAnswersByQuestionId(questionId);

        answers.sort(Comparator.comparing((Answer answer) -> answerVotesService.countByUpDownTrueByAnswerId(answer.getId()) -
                answerVotesService.countByUpDownFalseByAnswerId(answer.getId())).reversed());

        List<Long> answerIdList = new ArrayList<Long>();

        for(Answer answer : answers) {
            answerIdList.add(answer.getId());
        }

        return ResponseEntity.ok(answerIdList);
    }

    @GetMapping("getByAnswerId")
    public ResponseEntity<Object> getAnswerById(@RequestParam(name = "answerId") Long answerId) {
        Optional<Answer> answer = answerService.getAnswerById(answerId);

        return answer.<ResponseEntity<Object>>map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest().body("Answer not found"));

    }

    @PostMapping("/addForQuestionId")
    public ResponseEntity<Object> addAnswerForQuestionId(@RequestBody AnswerAddRequest answerAddRequest) throws IOException {
        Long userId = answerAddRequest.getUserId();
        Long questionId = answerAddRequest.getQuestionId();
        String text = answerAddRequest.getText();
        List<String> images = answerAddRequest.getImages();

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
                LocalDateTime.now()
        );

        boolean status = answerService.addAnswerForQuestionId(answer);

        if(!status){
            return ResponseEntity.badRequest().body("Error storing response text");
        }

        if(images != null) {
            for (String imageUrl : images) {
                Images image = new Images(imageUrl);

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

        questionService.setQuestionStatusById(answer.getId(), 1);

        return ResponseEntity.ok(answer);
    }

    @GetMapping("/getAnswerImagesById")
    public ResponseEntity<List<String>> getAnswerImagesById(@RequestParam(name = "answerId") Long answerId) {
        List<AnswerImages> answerImages = answerImagesService.getAllImagesId(answerId);
        List<String> images = new ArrayList<String>();

        for(AnswerImages ai : answerImages) {
            Long imageId = ai.getImageId();
            Optional<Images> searchForImage = imagesService.findByImageId(imageId);

            searchForImage.ifPresent(value -> images.add(value.getImageData()));
        }

        return ResponseEntity.ok(images);
    }

    @PutMapping("/editById")
    public ResponseEntity<String> editAnswerById(@RequestBody AnswerEditRequest answerEditRequest) throws IOException {

        Long answerId = answerEditRequest.getAnswerId();
        String text = answerEditRequest.getText();
        List<String> images = answerEditRequest.getImages();

        Optional<Answer> answer = answerService.getAnswerById(answerId);

        if(answer.isEmpty()){
            return ResponseEntity.badRequest().body("Error finding answer");
        }

        if(text.isEmpty()){
            return ResponseEntity.badRequest().body("Error empty text");
        }

        Answer updatedAnswer = answer.get();

        updatedAnswer.setText(text);
        answerService.addAnswerForQuestionId(updatedAnswer);

        List<AnswerImages> imagesList = answerImagesService.getAllImagesId(updatedAnswer.getId());
        answerImagesService.deleteAllById(updatedAnswer.getId());

        for (AnswerImages answerImages : imagesList) {
            imagesService.deleteById(answerImages.getImageId());
        }

        if(images != null) {
            for (String imageUrl : images) {
                Images image = new Images(imageUrl);

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
    public ResponseEntity<String> voteAnswerUp(@RequestBody AnswerVoteRequest answerVoteRequest) {
        Long answerId = answerVoteRequest.getAnswerId();
        Long userId = answerVoteRequest.getUserId();

        Optional<AnswerVotes> answerVoteOpt = answerVotesService.findAnswerVote(answerId, userId);

        if (answerVoteOpt.isEmpty()) {
            AnswerVotes newAnswerVote = new AnswerVotes(userId, answerId, true);
            boolean saveStatus = answerVotesService.addAnswerVote(newAnswerVote);

            if (!saveStatus) {
                return ResponseEntity.badRequest().body("Error voting up");
            }
            return ResponseEntity.ok("Voted up");
        }

        AnswerVotes existingVote = answerVoteOpt.get();

        if (existingVote.getUpDown()) {
            boolean saveStatus = answerVotesService.deleteAnswerVote(existingVote);

            if (!saveStatus) {
                return ResponseEntity.badRequest().body("Error removing vote up");
            }
            return ResponseEntity.ok("Vote up removed");
        } else {
            return ResponseEntity.ok("Already voted");
        }
    }


    @PutMapping("/voteDownById")
    public ResponseEntity<String> voteAnswerDown(@RequestBody AnswerVoteRequest answerVoteRequest) {
        Long answerId = answerVoteRequest.getAnswerId();
        Long userId = answerVoteRequest.getUserId();

        Optional<AnswerVotes> answerVoteOpt = answerVotesService.findAnswerVote(answerId, userId);

        if (answerVoteOpt.isEmpty()) {
            AnswerVotes newAnswerVote = new AnswerVotes(userId, answerId, false);
            boolean saveStatus = answerVotesService.addAnswerVote(newAnswerVote);

            if (!saveStatus) {
                return ResponseEntity.badRequest().body("Error voting down");
            }
            return ResponseEntity.ok("Voted down");
        }

        AnswerVotes existingVote = answerVoteOpt.get();

        if (!existingVote.getUpDown()) {
            boolean saveStatus = answerVotesService.deleteAnswerVote(existingVote);

            if (!saveStatus) {
                return ResponseEntity.badRequest().body("Error removing vote down");
            }
            return ResponseEntity.ok("Vote down removed");
        } else {
            return ResponseEntity.ok("Already voted");
        }
    }

    @GetMapping("/getAnswerScoreById")
    public ResponseEntity<Double> getAnswerScore(@RequestParam(name = "userId") Long userId) {
        double finalScore = 0.0;

        List<Answer> answerListByUser = answerService.getAnswerByUserId(userId);

        for(Answer answer : answerListByUser) {
            finalScore += 5 * answerVotesService.countByUpDownTrueByAnswerId(answer.getId());
            finalScore -= 2.5 * answerVotesService.countByUpDownFalseByAnswerId(answer.getId());
        }

        long numberOfAnswerVotesDown = answerVotesService.countByUpDownFalseByUserId(userId);
        finalScore -= 1.5 * numberOfAnswerVotesDown;

        return ResponseEntity.ok(finalScore);
    }

    @GetMapping("/getAnswerScoreByAnswerId")
    public ResponseEntity<Double> getAnswerScoreByAnswerId(@RequestParam(name = "answerId") Long answerId) {
        double finalScore = answerVotesService.countByUpDownTrueByAnswerId(answerId) -
                answerVotesService.countByUpDownFalseByAnswerId(answerId);

        return ResponseEntity.ok(finalScore);
    }
}
