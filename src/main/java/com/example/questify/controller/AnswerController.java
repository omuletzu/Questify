package com.example.questify.controller;

import com.example.questify.models.BetweenModels.AnswerImages;
import com.example.questify.models.SimpleModels.Answer;
import com.example.questify.models.SimpleModels.Images;
import com.example.questify.services.AnswerImagesService;
import com.example.questify.services.AnswerService;
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

    @Autowired
    public AnswerController(AnswerService answerService,
                            ImagesService imagesService,
                            AnswerImagesService answerImagesService){
        this.answerService = answerService;
        this.imagesService = imagesService;
        this.answerImagesService = answerImagesService;
    }

    @GetMapping("/getForQuestion")
    public List<Answer> getAnswersForQuestionId(@RequestParam(name = "id") Long id) {
        List<Answer> answers = answerService.getAnswersByQuestionId(id);
        answers.sort(Comparator.comparing(Answer::getTimestamp).reversed());
        return answers;
    }

    @PostMapping("/addForQuestion")
    public ResponseEntity<String> addAnswerForQuestionId(@RequestParam(name = "questionId") Long questionId,
                                                         @RequestParam(name = "userId") Long userId,
                                                         @RequestParam(name = "text") String text,
                                                         @RequestParam(name = "images") List<byte[]> images) {
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
            for(int i = 0; i < images.size(); i++) {
                Images image = new Images(images.get(i));

                status = imagesService.addImage(image);

                if(!status){
                    return ResponseEntity.badRequest().body("Error storing image");
                }

                AnswerImages answerImages = new AnswerImages(answer.getId(), image.getId());

                status = answerImagesService.addAnswerImages(answerImages);

                if(!status) {
                    return ResponseEntity.badRequest().body("Error storing relation image answer");
                }
            }
        }

        return ResponseEntity.ok("Success");
    }

    @PutMapping("/editById")
    public ResponseEntity<String> editAnswerById(@RequestParam(name = "id") Long id,
                                                 @RequestParam(name = "text") String text,
                                                 @RequestParam(name = "images") List<byte[]> images) {
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
}
