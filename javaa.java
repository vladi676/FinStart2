import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@RestController
@CrossOrigin(origins = "*") // Позволява връзка от HTML фронтенда
public class FinStartBackend {

    public static void main(String[] args) {
        SpringApplication.run(FinStartBackend.class, args);
    }

    @GetMapping("/api/finance/calculate")
    public Map<String, Object> calculateRealValue(
            @RequestParam double balance,
            @RequestParam double rate) {
        
        // Пресмятане на загубата на покупателна способност от инфлация
        double realValue = balance - (balance * (rate / 100));
        
        Map<String, Object> response = new HashMap<>();
        response.put("originalBalance", balance);
        response.put("realValue", Math.round(realValue));
        
        // AI Модул за поведение и съвети
        String advice;
        if (balance > 1500) {
            advice = "AI Препоръка: Покрил си основните разходи (наем/транспорт). Време е да прехвърлиш 100€ към спестовната касичка!";
        } else {
            advice = "AI Препоръка: Разходите за транспорт надвишават средното. Използвай виртуална карта с лимит!";
        }
        
        response.put("aiMessage", advice);
        return response;
    }
}