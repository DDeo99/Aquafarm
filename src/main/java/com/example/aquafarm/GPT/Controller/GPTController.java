package com.example.aquafarm.GPT.Controller;

import com.example.aquafarm.GPT.Service.GPTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/gpt")
public class GPTController {
    private final GPTService gptService;

    @Autowired
    public GPTController(GPTService gptService) {
        this.gptService = gptService;
    }

    @PostMapping("/chat/completions")
    public String askGPT(@RequestBody String question) {
        return gptService.askGPT(question);
    }
}