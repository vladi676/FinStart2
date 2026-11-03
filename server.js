
import express from "express";
import "dotenv/config";
import { GoogleGenAI } from "@google/genai";

const app = express();

app.use(express.json());
app.use(express.static("."));

const ai = new GoogleGenAI({
    apiKey: process.env.GEMINI_API_KEY
});

app.post("/chat", async (req, res) => {
    try {
        const { message, context } = req.body;

        if (typeof message !== "string" || !message.trim()) {
            return res.status(400).json({
                error: "Невалидно съобщение."
            });
        }

        const systemContext = `
Ти си FinStart AI Green Advisor.
Ти си финансов и екологичен ментор за младежи на 18-25 години.

Данни за потребителя:
- Град: ${context?.city ?? "Неизвестен"}
- Доход: ${context?.income ?? 0} €
- Фиксирани разходи: ${context?.fixedExpenses ?? 0} €
- Спестявания: ${context?.savings ?? 0} €
- Свободен баланс: ${context?.freeBalance ?? 0} €

Отговаряй кратко (до 2-3 изречения),
приятелски, на български език и с емоджита.
`;

        const result = await ai.models.generateContent({
            model: "gemini-2.5-flash",
            contents: `${systemContext}\n\nВъпрос: ${message}`
        });

        res.json({
            reply: result.text
        });

    } catch (error) {
        console.error("Gemini error:", error.message);

        res.status(500).json({
            error: "Възникна грешка при връзката с Gemini."
        });
    }
});

app.listen(3000, () => {
    console.log("FinStart AI работи на http://localhost:3000");
});
