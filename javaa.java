// Поставете вашия API ключ тук
const GEMINI_API_KEY = "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"; 

async function handleUserMessage() {
    let inp = document.getElementById('chat-input-text');
    let text = inp.value.trim();
    if (!text) return;

    // 1. Добавяме съобщението на потребителя
    appendMsg(text, 'user');
    inp.value = '';

    completeMission(3, 40);

    // 2. Показваме индикатор за зареждане
    let loadingDiv = document.createElement('div');
    loadingDiv.className = 'msg-bubble bot';
    loadingDiv.id = 'ai-loading';
    loadingDiv.innerText = '🤔 ИИ мисли...';
    document.getElementById('chat-box').appendChild(loadingDiv);

    try {
        // 3. Извикваме Gemini API
        let reply = await fetchRealAIResponse(text);
        
        document.getElementById('ai-loading')?.remove();
        appendMsg(reply, 'bot');
    } catch (error) {
        console.error("Gemini API Error:", error);
        document.getElementById('ai-loading')?.remove();
        appendMsg(`⚠️ Грешка при връзката: ${error.message}`, 'bot');
    }
}

async function fetchRealAIResponse(userQuery) {
    // Проверка за валиден ключ
    if (!GEMINI_API_KEY || GEMINI_API_KEY.includes("AQ.Ab8RN6JImcKVKtaRdNh910SJVpX8MQagYf4Hb5AxxviIikzU-w")) {
        throw new Error("Не сте въвели валиден API ключ в константата GEMINI_API_KEY!");
    }

    const systemContext = `Ти си FinStart AI Green Advisor - интелигентен финансов и екологичен ментор за младежи (18-25г.).
Данни за потребителя:
- Град: ${appState.cityName}
- Доход: ${appState.income} €
- Фиксирани разходи: ${appState.totalFixed} €
- Спестявания: ${appState.savings} €
- Свободен баланс: ${appState.freeBalance} €

Отговаряй кратко (до 2-3 изречения), приятелски, на български език, с емоджита.`;

    // Използваме стабилната v1beta версия на gemini-1.5-flash
    const url = `https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=${GEMINI_API_KEY}`;

    const response = await fetch(url, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            contents: [
                {
                    role: "user",
                    parts: [
                        { text: `${systemContext}\n\nВъпрос: ${userQuery}` }
                    ]
                }
            ]
        })
    });

    if (!response.ok) {
        const errorData = await response.json();
        throw new Error(errorData.error?.message || `HTTP грешка ${response.status}`);
    }

    const data = await response.json();
    
    if (data.candidates && data.candidates[0]?.content?.parts[0]?.text) {
        return data.candidates[0].content.parts[0].text;
    } else {
        throw new Error("Няма върнат текст от модела.");
    }
}